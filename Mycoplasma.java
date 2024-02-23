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
    private Color yerColor = Color.rgb(225, 0, 0);
    private Color infectedColor = Color.rgb(225, 234, 0);
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Mycoplasma(Field field, Location location, Color col) {
        super(field, location, col);        
    }

    /**
     * This is how the Mycoplasma decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation()); // Note that this gives out a list of living cells. Placeholder cells are DEAD.
        setNextState(false);

        if (isAlive() == true) {
            if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
                for(Cell cell: neighbours) {
                    if(cell.hasDisease()){
                        setDiseaseState(true);
                        setColor(infectedColor);
                    }
                }
                if(hasDisease()){
                    randomDie();
                }
            }
        }
    }
}