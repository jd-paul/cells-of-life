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
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        
    }
}
