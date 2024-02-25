import javafx.scene.paint.Color;
import java.util.Random;
import java.util.List;

/**
 * A class representing the shared characteristics of all forms of life
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 */

public abstract class Cell { 
    private boolean alive;    
    private boolean nextAlive; // The state of the cell in the next iteration

    private boolean hasDisease;
    private boolean nextDisease;

    private boolean age;

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
        hasDisease = disease;
        nextDisease = false;
        nextAlive = false;
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

    protected boolean diseaseFatalityCheck() {
        int randomNumber = rand.nextInt(10);

        if (randomNumber == 0) {
            return true;
        }
        return false;
    }

    protected boolean catchDiseaseCheck() {
        int n = rand.nextInt(10);

        if (n == 0) { // 1 in 10 chance
            darkenColor(0.65);
            return true;
        }
        return false;
    }

    public void setDiseaseState(boolean value) {
        hasDisease = value;
    }

    public boolean hasDisease() {
        return hasDisease;
    }

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
