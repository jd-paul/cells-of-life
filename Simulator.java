import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2024.02.03
 */

public class Simulator {

    private static final double MYCOPLASMA_ALIVE_PROB = 0.25;
    private static final double BOZIUM_ALIVE_PROB = 0.25;
    private static final double YERSINIA_ALIVE_PROB = 0.25;
    
    public static final Color mycoColor = Color.rgb(0, 255, 0);
    public static final Color bozColor = Color.rgb(0, 0, 255);
    public static final Color yerColor = Color.rgb(225, 0, 0);
    public static final Color placeholderColor = Color.rgb(224, 224, 224);

    private List<Cell> cells;
    private Field field;
    private int generation;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator() {
        this(SimulatorView.GRID_HEIGHT, SimulatorView.GRID_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width) {
        cells = new ArrayList<>();
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        generation++;
        Random rand = Randomizer.getRandom();
        
        /**
         * 1. Each cell will act();
         */
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                field.getObjectAt(location).act();
            }
        }
        
        /**
         * 2. Each cell will updateState();
         */
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                field.getObjectAt(location).updateState();
            }
        }
        
        /**
         * 3. Each cell is converted to the correct cell.
         */
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Cell currentCell = field.getObjectAt(location);
                
                /**
                 * Placeholders are converted
                 */                
                
                if (currentCell instanceof Placeholder) {
                    if (currentCell.isAlive() == true) {
                        if (currentCell.getNextCell().equals("mycoplasma")) {
                            Mycoplasma myco = new Mycoplasma(field, location, mycoColor);
                        
                            field.place(myco, location);
                            myco.setAlive();
                        }
                        else if (currentCell.getNextCell().equals("bozium")) {
                            Bozium boz = new Bozium(field, location, bozColor);
                    
                            field.place(boz, location);
                            boz.setAlive();
                            
                        }
                        else if (currentCell.getNextCell().equals("yersinia")) {
                            Yersinia yer = new Yersinia(field, location, bozColor);
                    
                            field.place(yer, location);
                            yer.setAlive();
                        }
                        else {
                            Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        
                            field.place(placeholder, location);
                            placeholder.setDead();
                        }
                    }
                    else if (currentCell.isAlive() == false) {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        
                        field.place(placeholder, location);
                        placeholder.setDead();
                    }
                }
                
                /**
                 * Mycoplasma cells are converted
                 */
                else if (currentCell instanceof Mycoplasma) {
                    if (currentCell.isAlive() == true) {
                        Mycoplasma myco = new Mycoplasma(field, location, mycoColor);
                    
                        field.place(myco, location);
                        myco.setAlive();
                    }
                    else if (currentCell.isAlive() == false) {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                    
                        field.place(placeholder, location);
                        placeholder.setDead();
                    }
                }
                
                /**
                 * Bozium cells are converted
                 */
                else if (currentCell instanceof Bozium) {
                    if (currentCell.isAlive() == true) {
                        Bozium boz = new Bozium(field, location, bozColor);
                    
                        field.place(boz, location);
                        boz.setAlive();
                    }
                    else if (currentCell.isAlive() == false) {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                    
                        field.place(placeholder, location);
                        placeholder.setDead();
                    }
                }
                
                /**
                 * Yersinia cells are converted
                 */
                else if (currentCell instanceof Mycoplasma) {
                    if (currentCell.isAlive() == true) {
                        Yersinia yer= new Yersinia(field, location, yerColor);
                    
                        field.place(yer, location);
                        yer.setAlive();
                    }
                    else if (currentCell.isAlive() == false) {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        
                        field.place(placeholder, location);
                        placeholder.setDead();
                    }
                }
            }
        }
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        cells.clear();
        populate();
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                int n = rand.nextInt(3);
                Location location = new Location(row, col);
                
                
                if (n == 0) {
                    if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                        Mycoplasma myco = new Mycoplasma(field, location, mycoColor);
                        
                        field.place(myco, location);
                        myco.setAlive();
                        
                        cells.add(myco);
                    }
                    else {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        
                        field.place(placeholder, location);
                        placeholder.setDead();
                        
                        cells.add(placeholder);
                    }
                }
                
                
                else if (n == 1) {
                    if (rand.nextDouble() <= BOZIUM_ALIVE_PROB) {
                        Bozium boz = new Bozium(field, location, bozColor);
                        
                        field.place(boz, location);
                        boz.setAlive();
                        
                        cells.add(boz);
                    }
                    else {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        
                        field.place(placeholder, location);
                        placeholder.setDead();
                        
                        cells.add(placeholder);
                    }
                } else if (n == 2) {
                    if (rand.nextDouble() <= BOZIUM_ALIVE_PROB) {
                        Yersinia yer = new Yersinia(field, location, yerColor);
                        
                        field.place(yer, location);
                        yer.setAlive();
                        
                        cells.add(yer);
                    }
                    else {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        
                        field.place(placeholder, location);
                        placeholder.setDead();
                        
                        cells.add(placeholder);
                    }
                }
            }
        }
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    public void delay(int millisec) {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }

    public Field getField() {
        return field;
    }

    public int getGeneration() {
        return generation;
    }
}
