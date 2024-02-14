import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Write a description of class Yersinia here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Yersinia extends Cell
{
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Yersinia(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This is how the Yersinia decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        
        System.out.println(neighbours);
        
        /* IMPLEMENT WITH NEW RULES! */
        if (isAlive() == true) {
            if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
            }
            else {
                setNextState(false);
            }
                
        }
        if (isAlive() == false) {
            if (neighbours.size() == 3) {
                setNextState(true);
            }
        }
    }
}
