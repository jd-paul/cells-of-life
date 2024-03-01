import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Yersinia cell is a red cell that will kill nearby Bozium. It is also the only cell that can randomly
 * contract the virus.
 *
 * Added code by:
 * @author John Paul D. San Diego
 * @k-number 21190412
 * 
 * @author Jia Cheng Lim
 * @k-number 23102614
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
        List<Cell> neighbours = getLivingNeighbours();
        setNextState(false);
        setNextDiseaseState(hasDisease());

        boolean adjMyco = false, adjBoz = false;

        if (isAlive() == true) {
            // Determine next alive
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {adjMyco = true;}
                if (cell instanceof Bozium) {adjBoz = true;}
            }

            if (adjBoz) {
                setNextState(true);
            }
            else if (adjMyco) {
                setNextState(false);
            }
            else if (!hasDisease() && (neighbours.size() >= 1 && neighbours.size() <= 5)) {
                setNextState(true);
            }
            else if (hasDisease() && (neighbours.size() == 2) || neighbours.size() == 4) {

            }

            // Determine next disease
            if (!hasDisease()){
                if (catchDiseaseCheck()) {
                    setNextDiseaseState(true);
                }
                // Copy paste

                for (Cell cell : neighbours) {
                    if (cell.hasDisease()) {
                        int n = rand.nextInt(5);
                        if (n == 0) {
                            setNextDiseaseState(true);
                        }
                    }

                    List<Cell> outerCellNeighbours = cell.getLivingNeighbours();
                    for (Cell outerCell : outerCellNeighbours) {
                        if (outerCell.hasDisease()) {
                            int n = rand.nextInt(5);
                            if (n == 0) {
                                setNextDiseaseState(true);
                            }
                        }
                    }
                }
            }
            else if (hasDisease()){
                if (diseaseFatalityCheck()) {setNextState(false);}
                if (healDiseaseCheck()) {setNextDiseaseState(false);}
            }
        }
    }
}
