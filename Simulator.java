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
        /**
         * Maybe it doesn't work because the newly created cell (Ex: myco placeholder)
         * aren't being appended to the cells list?
         * 
         * SimulatorView : updateCanvas USES the field for this purpose.
         */
        for (Cell cell : cells) {
            cell.updateState();
            
            if (cell.isAlive() == false) {
                cell = new Placeholder(cell.getField(), cell.getLocation(), placeholderColor);
                cell.setNextState(false);
                //cells.add(cell);
            }
            else if(cell.isAlive() == true && cell instanceof Placeholder) {
                //System.out.println(cell);
                cell = new Mycoplasma(cell.getField(), cell.getLocation(), mycoColor);
                cell.setNextState(true);
                //System.out.println(cell); // It actually does convert to a new cell.
                //cells.add(cell);
                
                
                // *** IMPLEMENT
                /**
                 * So what you're supposed to do is actually update the current cell on the
                 * board, and replace the placeholder cell with a new cell.
                 */
                
                
                
                //field.place(newCell, cell.getLocation());
                //System.out.println("New cell placed!");
                
                // field.place(cell, cell.getRow(), cell.getCol());
                
                // cells[cell.getRow()][cell.getCol()] = new Mycoplasma(cell.getField(), cell.getLocation(), mycoColor);
                // cells.add(cell);
            }
            else if(cell.isAlive() == true && cell instanceof Placeholder) {}
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
