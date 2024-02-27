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
        List<Cell> neighbours = getNeighbours();
        setNextState(false);
        setNextDiseaseState(false);

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        if (isAlive() == true) {
            for (Cell cell : neighbours) {
                if (cell instanceof Mycoplasma) {adjMyco = true;}
                if (cell instanceof Yersinia) {adjYer = true;}
                if (cell instanceof Bozium) {adjBoz = true;}
            }
            
            if ((neighbours.size() == 2 || neighbours.size() == 3)) {
                setNextState(true);
            }
            
            // Make sure nearby bozium doesn't die. This checks two cells away.
            for (Cell innerCell : neighbours) {
                if (innerCell instanceof Bozium) {
                    innerCell.setNextState(true);
                    innerCell.setNextDiseaseState(false);
                }
                List<Cell> innerCellNeighbours = innerCell.getNeighbours();
                for (Cell outerCell : innerCellNeighbours) {
                    if (outerCell instanceof Bozium) {
                        outerCell.setNextState(true);
                        outerCell.setNextDiseaseState(false);
                    }
                }
            }
        }
    }
}