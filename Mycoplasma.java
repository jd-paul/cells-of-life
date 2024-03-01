import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Simplest form of life.
 * Fun Fact: Mycoplasma are one of the simplest forms of life.  A type of
 * bacteria, they only have 500-1000 genes! For comparison, fruit flies have
 * about 14,000 genes.
 *
 * @author David J. Barnes, Michael Kölling & Jeffery Raphael
 * @version 2022.01.06
 * 
 * Added code by:
 * @author John Paul D. San Diego
 * @k-number 21190412
 * 
 * @author Jia Cheng Lim
 * @k-number 23102614
 */

public class Mycoplasma extends Cell {
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param color of the cells 
     * @param current disease state
     */
    public Mycoplasma(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, disease);
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getLivingNeighbours();
        setNextState(false);

        boolean adjBoz = false;

        if (isAlive() == true) {
            // Check for adjacencies and nearby disease. If there is a nearby Bozium, do not contract disease.
            // Check if cell will gain disease from adjacent cells.
            for (Cell cell : neighbours) {
                if (cell instanceof Bozium) {adjBoz = true;}
                else if (cell.hasDisease()) {setNextDiseaseState(true);}

                List<Cell> outerCellNeighbours = cell.getLivingNeighbours();
                for (Cell outerCell : outerCellNeighbours) {
                    if (outerCell instanceof Bozium) {adjBoz = true;}
                    else if (outerCell.hasDisease()) {setNextDiseaseState(true);}
                }
            }

            if ((neighbours.size() == 2 || neighbours.size() == 3)) {
                setNextState(true);
            }
            
            if (hasDisease()) {
                if (diseaseFatalityCheck()) {setNextState(false);}
                if (healDiseaseCheck()) {setNextDiseaseState(false);}
            }

            if (adjBoz) {setNextDiseaseState(false);}
        }
    }
}