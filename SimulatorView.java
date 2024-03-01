import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.Group; 
import javafx.scene.layout.BorderPane; 
import javafx.scene.layout.HBox; 
import javafx.scene.paint.Color; 
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Insets;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import java.util.HashMap;
/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 * 
 * Added code by:
 * @author John Paul D. San Diego
 * @k-number 21190412
 * 
 * @author Jia Cheng Lim
 * @k-number 23102614
 */

public class SimulatorView extends Application {

    public static final int GRID_WIDTH = 100;
    public static final int GRID_HEIGHT = 80;    
    public static final int WIN_WIDTH = 650;
    public static final int WIN_HEIGHT = 650;  

    private static final Color EMPTY_COLOR = Color.WHITE;

    private final String GENERATION_PREFIX = "Generation: ";
    private final String POPULATION_PREFIX = "Population: ";

    private Label genLabel, population, infoLabel;

    private FieldCanvas fieldCanvas;
    private FieldStats stats;
    private Simulator simulator;
    XYChart.Series<Number, String> series = new XYChart.Series<>();
    CategoryAxis xAxis = new CategoryAxis();
    NumberAxis yAxis = new NumberAxis();
    BarChart<Number, String> barChart = new BarChart<>(yAxis, xAxis);
    /**
     * Create a view of the given width and height.
     * @param height The simulation's height.
     * @param width  The simulation's width.
     */
    @Override
    public void start(Stage stage) {
        stats = new FieldStats();
        fieldCanvas = new FieldCanvas(WIN_WIDTH - 50, WIN_HEIGHT - 50);
        fieldCanvas.setScale(GRID_HEIGHT, GRID_WIDTH); 
        simulator = new Simulator();

        Group root = new Group();

        genLabel = new Label(GENERATION_PREFIX);
        infoLabel = new Label("Simulation made by Joel and Paul. Adapted from code by \nDavid J. Barnes, Michael Kölling & Jeffery Raphael");
        population = new Label(POPULATION_PREFIX);

        // Set to bold
        genLabel.setFont(Font.font("Verdana", FontWeight.BOLD, 14));

        BorderPane bPane = new BorderPane(); 
        VBox infoPane = new VBox();
        HBox popPane = new HBox();

        infoPane.setSpacing(10);
        infoPane.getChildren().addAll(genLabel, infoLabel);
        popPane.getChildren().addAll(population);
        popPane.setLayoutX(40);
        popPane.setLayoutY(635);
        infoPane.setPadding(new Insets(10, 10, 10, 10)); // 10 pixels padding on all sides

        barChart.setLayoutX(50);
        barChart.setLayoutY(660);
        barChart.setPrefSize(550, 200);
        barChart.setStyle("-fx-background-color: #EBEBEB; -fx-font-size: 14px;");
        barChart.setLegendVisible(false);
        bPane.setTop(infoPane);
        bPane.setCenter(fieldCanvas);
        //bPane.setBottom(popPane); // Add popPane to the bottom of the BorderPane

        root.getChildren().add(bPane);
        root.getChildren().add(popPane);
        root.getChildren().add(barChart);
        Scene scene = new Scene(root, WIN_WIDTH, 880); 

        stage.setScene(scene);          
        stage.setTitle("Life Simulation: Cells of Survival");
        updateCanvas(simulator.getGeneration(), simulator.getField());
        //updateCanvas(simulator.getGeneration(), simulator.getField());
        stage.show();
    }

    public void addGraphData(int population, String name){
        series.getData().add(new XYChart.Data<>(population, "" + name));
    }
    
    public void setBarColour(){
    for (XYChart.Data<Number, String> data : series.getData()) {
            String colorStyle = "-fx-bar-fill: #FF8C00;";
            if((data.getYValue().toString()).equals("Yersinia"))
            {colorStyle = "-fx-bar-fill: #FF0000;";}
            else if((data.getYValue().toString()).equals("Mycoplasma"))
            {colorStyle = "-fx-bar-fill: #00FF00;";}
            else if((data.getYValue().toString()).equals("Bozium"))
            {colorStyle = "-fx-bar-fill: #0000FF;";}
            else if((data.getYValue().toString()).equals("Microbiota"))
            {colorStyle = "-fx-bar-fill: #FF00FF;";}
            //String colorStyle = getColorStyle(); // Define your own color logic
            data.getNode().setStyle(colorStyle);
        }
    }
    public void setBarChart(HashMap<Class, Counter> counters){
        series.getData().clear();
        barChart.getData().clear();
        for (Class key : counters.keySet()) {
            Counter info = counters.get(key);
            if(!(info.getName()).equals("Placeholder"))
            addGraphData(info.getCount(),info.getName());
        }
        barChart.getData().add(series);
        setBarColour();
    }

    /**
     * Display a short information label at the top of the window.
     */
    public void setInfoText(String text) {
        infoLabel.setText(text);
    }

    /**
     * Show the current status of the field.
     * @param generation The current generation.
     * @param field The field whose status is to be displayed.
     */
    public void updateCanvas(int generation, Field field) {
        HashMap<Class, Counter> counters = stats.saveCounters();
        genLabel.setText(GENERATION_PREFIX + generation);
        stats.reset();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);

                stats.incrementCount(cell.getClass());
                fieldCanvas.drawMark(col, row, cell.getColor());
                }
        }
        
        setBarChart(counters);
        stats.countFinished();
        population.setText(POPULATION_PREFIX + stats.getPopulationDetails(field));
    }

    /**
     * Determine whether the simulation should continue to run.
     * @return true If there is more than one species alive.
     */
    public boolean isViable(Field field) {
        return stats.isViable(field);
    }

    /**
     * Run the simulation from its current state for the given number of
     * generations. Stop before the given number of generations if the
     * simulation ceases to be viable.
     * @param numGenerations The number of generations to run for.
     */
    public void simulate(int numGenerations) {
        new Thread(() -> {

                for (int gen = 1; gen <= numGenerations; gen++) {
                    simulator.simOneGeneration();    
                    simulator.delay(750); // Used to be 500
                    Platform.runLater(() -> {
                            updateCanvas(simulator.getGeneration(), simulator.getField());
                        });
                }

            }).start();
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        simulator.reset();
        updateCanvas(simulator.getGeneration(), simulator.getField());
    }

    public static void main(String args[]){           
        launch(args);      
    } 
}
