import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Fictional bacteria generated for this Life Simulation.
 * Fun fact: This bacteria has a unique relationship with mycoplasma.
 * By itself, bozium does not proliferate very well. However, when paired with
 * a mycoplasma, it multiplies further.
 * 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Bozium extends Cell
{
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bozium(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        
    }
}
