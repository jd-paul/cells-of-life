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
    public Bozium(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, disease);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        /* IMPLEMENT WITH NEW RULES! */
        if (isAlive() == true) {
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {adjMyco = true;}
                if (cell instanceof Yersinia) {adjYer = true;}
                if (cell instanceof Bozium) {adjBoz = true;}
            }
                        
            if (adjYer & !adjMyco) {
                setNextState(false);
            }
            else if (adjYer && adjMyco && ((neighbours.size() >= 1 && neighbours.size() <= 3) || neighbours.size() == 5)) {
                setNextState(true);
            }
            else if (!adjYer && adjMyco && (neighbours.size() >= 1 && neighbours.size() <= 5)) {
                setNextState(true);
            }
            else if (neighbours.size() >= 2 && neighbours.size() <= 4) {
                setNextState(true);
            }
        }
    }
}

