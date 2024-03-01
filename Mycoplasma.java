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
            for (Cell cell : neighbours) {
                if (cell instanceof Bozium) {adjBoz = true;}
                
                List<Cell> outerCellNeighbours = cell.getLivingNeighbours();
                for (Cell outerCell : outerCellNeighbours) {
                    if (cell instanceof Bozium) {adjBoz = true;}
                }
            }

            if ((neighbours.size() == 2 || neighbours.size() == 3)) {
                setNextState(true);
            }

            if (hasDisease()) {
                for (Cell innerCell : neighbours) {
                    if (innerCell instanceof Mycoplasma || innerCell instanceof Yersinia) {
                        innerCell.setNextDiseaseState(true);
                    }
                    List<Cell> innerCellNeighbours = innerCell.getLivingNeighbours();
                    for (Cell outerCell : innerCellNeighbours) {
                        if (outerCell instanceof Mycoplasma || outerCell instanceof Yersinia) {
                            outerCell.setNextDiseaseState(true);
                        }
                    }
                }
                
                if (diseaseFatalityCheck()) {setNextState(false);}
                if (healDiseaseCheck()) {setNextDiseaseState(false);}
            }
            
            if (adjBoz) {setNextDiseaseState(false);}
        }
    }
}