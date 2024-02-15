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
    
    private Color mycoColor = Color.rgb(255, 165, 0);
    private Color bozColor = Color.rgb(100, 55, 0);
    private Color yerColor = Color.rgb(220, 20, 60);

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

        for (Cell cell : cells) {
            cell.updateState();
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
                    Mycoplasma myco = new Mycoplasma(field, location, mycoColor);
                    if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                        cells.add(myco);
                    }
                    else {
                        myco.setDead();
                        cells.add(myco);
    
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
