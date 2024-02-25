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
    public Yersinia(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, disease);
    }

    /**
     * This is how the Yersinia decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        setNextDiseaseState(false);

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        if (isAlive() == true) {
            // Determine next alive
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {adjMyco = true;}
                if (cell instanceof Yersinia) {adjYer = true;}
                if (cell instanceof Bozium) {adjBoz = true;}
            }
            
            if (adjBoz) {
                setNextState(true);
            }
            else if (neighbours.size() == 1 || neighbours.size() == 2) {
                setNextState(true);
            }
            
            // Determine next disease
            if (!hasDisease()){
                if (catchDiseaseCheck()) {setNextDiseaseState(true);}
            }
            else if (hasDisease()){
                if (diseaseFatalityCheck()) {setNextState(false);}
            }
        }
    }
}
