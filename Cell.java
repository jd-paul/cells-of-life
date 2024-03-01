import javafx.scene.paint.Color;
import java.util.Random;
import java.util.List;

/**
 * A class representing the shared characteristics of all forms of life
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

public abstract class Cell { 
    private boolean alive;    
    private boolean nextAlive; // The state of the cell in the next iteration

    private boolean hasDisease;
    private boolean nextDisease;

    private Field field;
    private Location location;
    private Color color = Color.WHITE;
    Random rand = new Random();

    protected String nextCell = "";

    private Color infectedColor = Color.rgb(225, 234, 0);

    /**
     * Create a new cell at location in field.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Cell(Field field, Location location, Color col, boolean disease) {
        alive = true;
        nextAlive = false;
        
        hasDisease = disease;
        nextDisease = false;
        
        this.field = field;
        setLocation(location);
        setColor(col);
    }

    /**
     * Make this cell act - that is: the cell decides it's status in the
     * next generation.
     */
    abstract public void act();

    /**
     * Check whether the cell is alive or not.
     * @return true if the cell is still alive.
     */
    protected boolean isAlive() {
        return alive;
    }

    /**
     * Indicate that the cell is no longer alive.
     */
    protected void setDead() {
        alive = false;
    }

    /**
     * Indicate that the cell is now alive.
     */
    protected void setAlive() {
        alive = true;
    }

    /**
     * Indicate that the cell will be alive or dead in the next generation.
     */
    public void setNextState(boolean value) {
        nextAlive = value;
    }
    
    public void setNextDarken() {
        darkenColor(0.6);
    }

    /**
     * Changes the state of the cell.
     */
    public void updateState() {
        alive = nextAlive;
    }

    /**
     * Changes the color of the cell
     */
    public void setColor(Color col) {
        color = col;
    }

    /**
     * Returns the cell's color
     */
    public Color getColor() {
        return color;
    }

    /**
     * Return the cell's location.
     * @return The cell's location.
     */
    protected Location getLocation() {
        return location;
    }

    /**
     * Place the cell at the new location in the given field.
     * @param location The cell's location.
     */
    protected void setLocation(Location location) {
        this.location = location;
        field.place(this, location);
    }

    /**
     * Return the cell's field.
     * @return The cell's field.
     */
    protected Field getField() {
        return field;
    }
    
    /**
     * Check if the cell should die due to the infection.
     * @return true if the cell should die, false otherwise.
     */
    protected boolean diseaseFatalityCheck() {
        int randomNumber = rand.nextInt(300);

        if (randomNumber == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Check if the cell should contract the disease.
     * @return true if the cell should contract it, false otherwise.
     */
    protected boolean catchDiseaseCheck() {
        if (hasDisease) {return false;}

        int n = rand.nextInt(500);
        
        if (n == 0) {
            return true;
        }
        return false;
    }
    
    /**
     * Check if the cell should heal from the disease.
     * @return true if the cell should heal from it, false otherwise.
     */
    protected boolean healDiseaseCheck() {
        if (!hasDisease) {return true;}

        int n = rand.nextInt(2);
        
        if (n == 0) {
            return true;
        }
        return false;
    }

    /**
     * Return the cell's disease status.
     * @return The cell's disease status.
     */
    public boolean hasDisease() {
        return hasDisease;
    }

    /**
     * Set the disease status of the cell.
     * @param value true if the cell should have the disease, false otherwise.
     */
    public void setNextDiseaseState(boolean value) {
        nextDisease = value;
    }

    public void updateDiseaseState(){
        hasDisease = nextDisease;
    }

    public void setNextCell(String s) {
        nextCell = s;
    }

    public String getNextCell() {
        return nextCell;
    }
    
    public List<Cell> getLivingNeighbours() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        return neighbours;
    }
    
    public List<Cell> getLivingAndDeadNeighbours() {
        List<Cell> neighbours = getField().getLivingAndDeadNeighbours(getLocation());
        return neighbours;
    }

    public void darkenColor(double factor) {
        Color originalColor = this.color;

        // Explanation: (1 - factor) means (1 - 0.1) = 0.9. This means
        // 90% of the original intensity is used.
        double r = originalColor.getRed() * (1 - factor);
        double g = originalColor.getGreen() * (1 - factor);
        double b = originalColor.getBlue() * (1 - factor);

        r = Math.min(1.0, Math.max(0.0, r));
        g = Math.min(1.0, Math.max(0.0, g));
        b = Math.min(1.0, Math.max(0.0, b));

        Color darkenedColor = Color.color(r, g, b);
        setColor(darkenedColor);
    }
}
