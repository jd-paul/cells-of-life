import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Food cell
 * 
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Microbiota extends Cell
{
    int age;
    public static final int MAX_AGE = 100;
    
    /**
     * Create a new Spawner.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Microbiota(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, false);
        
        age = 0;
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getNeighbours();
        setNextState(false);
        setNextDiseaseState(false);
        
        if (++age >= MAX_AGE) {
            setNextState(false);
        }
        else {
            setNextState(true);
        }
        
        /*
        for (Cell cell : neighbours) {
            if ()
            if 
            
            if (cell instanceof Placeholder) {
                
            }
        }
        */
        
        
        
        age++;
    }
}

