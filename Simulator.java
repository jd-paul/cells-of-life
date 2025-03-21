import javafx.scene.paint.Color; 
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

/**
 * A Life (Game of Life) simulator, first described by British mathematician
 * John Horton Conway in 1970. Conway's set of rules is used as inspiration
 * for this game, which now uses multiple new cell types:
 * Mycoplasma, Bozium, Yersinia, Microbiota, and Placeholder cell.
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

public class Simulator {

    private static final double MYCOPLASMA_ALIVE_PROB = 0.10;
    private static final double BOZIUM_ALIVE_PROB = 0.20;
    private static final double YERSINIA_ALIVE_PROB = 0.10;
    private static final double MICROBIOTA_ALIVE_PROB = 0.005;
    
    public static final Color mycColor = Color.rgb(15, 255, 15);
    public static final Color bozColor = Color.rgb(0, 100, 255);
    public static final Color yerColor = Color.rgb(225, 0, 0);
    public static final Color micColor = Color.rgb(255, 0, 255);
    public static final Color placeholderColor = Color.rgb(235, 235, 235);
    
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
        field = new Field(depth, width);
        reset();
    }

    /**
     * Run the simulation from its current state for a single generation.
     * Iterate over the whole field updating the state of each life form.
     */
    public void simOneGeneration() {
        Random rand = Randomizer.getRandom();

        /**
         * 1. Each cell will act(); This order is important as act() from certain cells
         * are prioritized over other cell types.
         */
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Cell cell = field.getObjectAt(location);
                cell.act();
            }
        }

        /**
         * 2. Each cell will updateState();
         */
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                Location location = new Location(row, col);
                Cell cell = field.getObjectAt(location);
                cell.updateState();
                cell.updateDiseaseState();
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
                            if (currentCell.hasDisease()) {addCell(location, "mycoplasma", true);}
                            else {addCell(location, "mycoplasma", false);}
                        }
                        else if (currentCell.getNextCell().equals("bozium")) {
                            if (currentCell.hasDisease()) {addCell(location, "bozium", true);}
                            else {addCell(location, "bozium", false);}
                        }
                        else if (currentCell.getNextCell().equals("yersinia")) {
                            if (currentCell.hasDisease()) {addCell(location, "yersinia", true);}
                            else {addCell(location, "yersinia", false);}
                        }
                        else {
                            addCell(location, "placeholder", false);
                        }
                    }
                    else if (currentCell.isAlive() == false) {
                        if (generation >= 300) {
                            int n = rand.nextInt(256000 - generation); //256000 is just a large number 
                            //that we choose to reduce the chance of microbiota spawning
                            if (n == 0) {addCell(location, "microbiota", false);}
                            else {addCell(location, "placeholder", false);}
                        }
                        else {
                            addCell(location, "placeholder", false);
                        }
                    }
                }

                /**
                 * Mycoplasma cells are converted to placeholders if dead.
                 */
                else if (currentCell instanceof Mycoplasma) {
                    if (currentCell.isAlive() == true) {                        
                        if (currentCell.hasDisease()) {addCell(location, "mycoplasma", true);}
                        else {addCell(location, "mycoplasma", false);}
                    }
                    else if (currentCell.isAlive() == false) {
                        addCell(location, "placeholder", false);
                    }
                }

                /**
                 * Bozium cells are converted to placeholders if dead.
                 */
                else if (currentCell instanceof Bozium) {
                    if (currentCell.isAlive() == true) {
                        if (currentCell.hasDisease()) {addCell(location, "bozium", true);}
                        else {addCell(location, "bozium", false);}
                    }
                    else if (currentCell.isAlive() == false) {
                        addCell(location, "placeholder", false);
                    }
                }

                /**
                 * Yersinia cells are converted to placeholders if dead.
                 */
                else if (currentCell instanceof Yersinia) {
                    if (currentCell.isAlive() == true) {
                        if (currentCell.hasDisease()) {addCell(location, "yersinia", true);}
                        else {addCell(location, "yersinia", false);}
                    }
                    else if (currentCell.isAlive() == false) {
                        addCell(location, "placeholder", false);
                    }
                }
                
                /**
                 * Microbiota cells are converted to placeholders if dead.
                 */
                else if (currentCell instanceof Microbiota) {
                    if (currentCell.isAlive() == true) {
                        // Leave as is.
                    }
                    else if (currentCell.isAlive() == false) {
                        addCell(location, "placeholder", false);
                    }
                }
            }
        }
        
        generation++;
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset() {
        generation = 0;
        populate();
    }

    /**
     * This method replaces cells on the board with the chosen cell. This is
     * a support function for other methods to use.
     * 
     * @param location The location that the cell will be placed in
     * @param cellType The type of cell that will be placed at the location
     * @param hasDisease The status of the disease that the cell will have
     */
    private void addCell(Location location, String cellType, boolean hasDisease) {
        if (cellType.equals("mycoplasma")) {
            Mycoplasma myco = new Mycoplasma(field, location, mycColor, hasDisease);  
            if (hasDisease) {myco.darkenColor(0.45);}
            
            field.place(myco, location);
            myco.setAlive();
        } else if (cellType.equals("bozium")) {
            Bozium boz = new Bozium(field, location, bozColor, hasDisease);
            
            field.place(boz, location);
            boz.setAlive();            
        } else if (cellType.equals("yersinia")) {
            Yersinia yer = new Yersinia(field, location, yerColor, hasDisease);
            if (hasDisease) {yer.darkenColor(0.45);}
            
            field.place(yer, location);
            yer.setAlive();
        } else if (cellType.equals("microbiota")) {
            Microbiota mic = new Microbiota(field, location, micColor, hasDisease);
            
            field.place(mic, location);
            mic.setAlive();
        } else if (cellType.equals("placeholder")) {
            Placeholder placeholder = new Placeholder(field, location, placeholderColor, hasDisease);

            field.place(placeholder, location);
            placeholder.setDead();
        }
    }

    /**
     * Randomly populate the field live/dead life forms
     */
    private void populate() {
        Random rand = Randomizer.getRandom();
        field.clear();
        
        for (int row = 0; row < field.getDepth(); row++) {
            for (int col = 0; col < field.getWidth(); col++) {
                int n = rand.nextInt(4);
                Location location = new Location(row, col);
                
                if (n == 0) {
                    if (rand.nextDouble() <= MYCOPLASMA_ALIVE_PROB) {
                        addCell(location, "mycoplasma", false);
                    }
                    else {
                        addCell(location, "placeholder", false);
                    }
                }
                else if (n == 1) {
                    if (rand.nextDouble() <= BOZIUM_ALIVE_PROB) {
                        addCell(location, "bozium", false);
                    }
                    else {
                        addCell(location, "placeholder", false);
                    }
                }
                else if (n == 2) {
                    if (rand.nextDouble() <= YERSINIA_ALIVE_PROB) {
                        addCell(location, "yersinia", false);
                    }
                    else {
                        addCell(location, "placeholder", false);
                    }
                }
                else if (n == 3) {
                    if (rand.nextDouble() <= MICROBIOTA_ALIVE_PROB) {
                        addCell(location, "microbiota", false);
                    }
                    else {
                        addCell(location, "placeholder", false);
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
    
    /**
     * Get field
     * @return field.
     */
    public Field getField() {
        return field;
    }
    
    /**
     * Get field
     * @return field.
     */
    public int getGeneration() {
        return generation;
    }
}
