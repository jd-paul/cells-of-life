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

/**
 * A graphical view of the simulation grid. The view displays a rectangle for
 * each location. Colors for each type of life form can be defined using the
 * setColor method.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
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
        popPane.setLayoutX(120);
        popPane.setLayoutY(635);
        infoPane.setPadding(new Insets(10, 10, 10, 10)); // 10 pixels padding on all sides

        bPane.setTop(infoPane);
        bPane.setCenter(fieldCanvas);
        //bPane.setBottom(popPane); // Add popPane to the bottom of the BorderPane

        root.getChildren().add(bPane);
        root.getChildren().add(popPane);
        Scene scene = new Scene(root, WIN_WIDTH, WIN_HEIGHT); 

        stage.setScene(scene);          
        stage.setTitle("Life Simulation: Cells of Survival");
        updateCanvas(simulator.getGeneration(), simulator.getField());

        stage.show();
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
        genLabel.setText(GENERATION_PREFIX + generation);
        stats.reset();

        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Cell cell = field.getObjectAt(row, col);

                stats.incrementCount(cell.getClass());
                fieldCanvas.drawMark(col, row, cell.getColor());
            }
        }

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
                        simulator.delay(380); // Used to be 500
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
