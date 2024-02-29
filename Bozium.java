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
        List<Cell> neighbours = getLivingNeighbours();
        setNextState(false);
        setNextDiseaseState(false);

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        /* IMPLEMENT WITH NEW RULES! */
        if (isAlive() == true) {
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {adjMyco = true;}
                if (cell instanceof Yersinia) {adjYer = true;}
                if (cell instanceof Bozium) {adjBoz = true;}
            }

            if (adjYer & !adjMyco && (neighbours.size() >= 1 && neighbours.size() <= 4)) {
                int n = rand.nextInt(10);
                if (n >= 1) {setNextState(true);}
                else if (n == 0) {setNextState(false);}
            }
            else if (!adjYer && adjMyco && (neighbours.size() >= 1 && neighbours.size() <= 8)) {
                setNextState(true);
            }
            else if (neighbours.size() >= 1 && neighbours.size() <= 4) {
                setNextState(true);
            }
            
            // Don't die if there is a nearby mycoplasma.
            for (Cell innerCell : neighbours) {
                if (innerCell instanceof Mycoplasma) {
                    setNextState(true);
                }
                List<Cell> innerCellNeighbours = innerCell.getLivingNeighbours();
                for (Cell outerCell : innerCellNeighbours) {
                    if (outerCell instanceof Mycoplasma) {
                        setNextState(true);
                    }
                }
            }
        }
    }
}

