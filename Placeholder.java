import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Write a description of class Placeholder_Cell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Placeholder extends Cell
{
    /**
     * Create a new placeholder cell. Their purpose is to make it easier for repopulation.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Placeholder(Field field, Location location, Color col) {
        super(field, location, col);        
    }

    /**
    * This is how the Placeholder decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        
        // System.out.println("neighbours of placeholder: " + neighbours);

        if (neighbours.size() == 3) {
            setNextState(true);
        }
        
    }
}