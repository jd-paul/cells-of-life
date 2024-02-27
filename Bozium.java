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
        List<Cell> neighbours = getNeighbours();
        setNextState(false);

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        /* IMPLEMENT WITH NEW RULES! */
        if (isAlive() == true) {
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {adjMyco = true;}
                if (cell instanceof Yersinia) {adjYer = true;}
                if (cell instanceof Bozium) {adjBoz = true;}
            }

            if (hasDisease()){
                if (neighbours.size() >= 1 && neighbours.size() <= 2) {
                    setNextState(true);
                }
                if (diseaseFatalityCheck()) {setNextState(false);}

                // Disease spreading
                for (Cell innerCell : neighbours) {
                    if (innerCell instanceof Bozium) {
                        innerCell.setNextDiseaseState(true);
                    }
                    List<Cell> innerCellNeighbours = innerCell.getNeighbours();
                    for (Cell outerCell : innerCellNeighbours) {
                        if (outerCell instanceof Bozium) {
                            outerCell.setNextDiseaseState(false);
                        }
                    }
                }
            }
            else {
                if (adjYer & !adjMyco) {
                    int n = rand.nextInt(8);
                    if (n >= 1) {setNextState(true);}
                    else if (n == 0) {setNextState(false);}

                }
                else if (adjYer && adjMyco && (neighbours.size() >= 1 && neighbours.size() <= 4)) {
                    setNextState(true);
                    setNextDiseaseState(false);
                }
                else if (!adjYer && adjMyco && (neighbours.size() >= 1 && neighbours.size() <= 6)) {
                    setNextState(true);
                    setNextDiseaseState(false);
                }
                else if (neighbours.size() >= 1 && neighbours.size() <= 4) {
                    setNextState(true);
                }
            }
        }
    }
}

