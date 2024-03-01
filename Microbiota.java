import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Microbiota is a pink cell that will spawn bozium and mycoplasma nearby.
 * It is the only cell affected by age, and has a very rare spawn rate. It is immune to disease.
 * It will die of old age.
 *
 * Added code by:
 * @author John Paul D. San Diego
 * @k-number 21190412
 * 
 * @author Jia Cheng Lim
 * @k-number 23102614
 */
public class Microbiota extends Cell {
    int age;
    public static final int MAX_AGE = 200;
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param color of the cells 
     * @param current disease state
     */
    public Microbiota(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, disease);
        age = 0;
    }

    /**
     * This is how the Microbiota decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingAndDeadNeighbours(getLocation());
        setNextState(false);
        setNextDiseaseState(false);
        
        if (age <= MAX_AGE) {
            setNextState(true);
        }
        
        age++;
    }
}