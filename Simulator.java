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
        for (Iterator<Cell> it = cells.iterator(); it.hasNext(); ) {
            Cell cell = it.next();
            cell.act();
        }
        
        
        /** *** FIX IMPLEMENT!
         * 
         * Update each cell first. Then determine if it needs to be converted
         * to a new cell type. Then update state again.
         * 
         * Note that updateState() uses the act() command and follows what its state
         * should be next.
         * 
         * Current LOGIC
         *      1: Each cell does act(). This means that their next state (dead / alive)
         *      is determined.
         *      
         *      2: Each cell is updated to their new state. This means that their var
         *      'alive' is set accordingly.
         *      
         *      3: Each cell is then converted to the correct cell that they should be.
         *      'Dead' cells are converted to placeholders. 'Alive' placeholders are
         *      converted to mycoplasma.
         *      
         *      4: 
         *      
         *      
         */
        for (Cell cell : cells) {
            cell.updateState();
            
            /**
             * If the current cell is NOT a placeholder, and is dead, it is converted
             * to a placeholder.
             * 
             * P: This converts dead mycoplasma into placeholder cells.
             */
            if ((cell instanceof Mycoplasma) && cell.isAlive() == false) {
                cell = new Placeholder(cell.getField(), cell.getLocation(), placeholderColor);
                // cell.setDead();
                // cell.setNextState(false); // Doesn't seem important
            }
            
            /**
             * If the current cell is a placeholder, and is alive, it is converted
             * to mycoplasma.
             * 
             * P: This converts living placeholder cells into mycoplasma.
             */
            else if(cell instanceof Placeholder && cell.isAlive() == true) {
                cell = new Mycoplasma(cell.getField(), cell.getLocation(), mycoColor);
                //cell.setNextState(true);
                //cell.setAlive();
            }
            
            else if(cell.isAlive() == true) {
                
            }
            
            /**
             * In a for-loop, you cannot modify the cell variable itself as you're iterating
             * through it...
             */
            
            /**
             * Unsure if these statements are required.
             */
            //cell.act();
            //cell.updateState();
        }
        
        // for (Cell cell : cells) {
            // cell.act();
            // cell.updateState();
        // }
        
        
        // for (Cell cell : cells) {
            // cell.updateState();
            
            // if (cell.isAlive() == false) {
                // cell = new Placeholder(cell.getField(), cell.getLocation(), placeholderColor);
                // cell.setNextState(false);
            // }
            // else if(cell.isAlive() == true && cell instanceof Placeholder) {
                // //System.out.println(cell);
                // cell = new Mycoplasma(cell.getField(), cell.getLocation(), mycoColor);
                // cell.setNextState(true);
                // cell.setAlive();
                // //System.out.println(cell); // It actually does convert to a new cell.
                
                // /**
                 // * So what you're supposed to do is actually update the current cell on the
                 // * board, and replace the placeholder cell with a new cell.
                 // */
            // }
            // else if(cell.isAlive() == true) {}
            
            // cell.act();
            // cell.updateState();
        // }
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
                int n = rand.nextInt(1);
                Location location = new Location(row, col);
                
                if (n == 0) {
                    if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                        Mycoplasma myco = new Mycoplasma(field, location, mycoColor);
                        cells.add(myco);
                    }
                    else {
                        Placeholder placeholder = new Placeholder(field, location, placeholderColor);
                        placeholder.setDead();
                        cells.add(placeholder);
                    }
                } else if (n == 1) {
                    Bozium boz = new Bozium(field, location, bozColor);
                    if (rand.nextDouble() <= BOZIUM_ALIVE_PROB) {
                        cells.add(boz);
                    }
                    else {
                        boz.setDead();
                        cells.add(boz);
                    }
                } else if (n == 2) {
                    Yersinia yer = new Yersinia(field, location, yerColor);
                    if (rand.nextDouble() <= YERSINIA_ALIVE_PROB) {
                        cells.add(yer);
                    }
                    else {
                        yer.setDead();
                        cells.add(yer);
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
