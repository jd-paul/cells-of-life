import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Write a description of class Placeholder_Cell here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Placeholder extends Cell
{
    /**
     * Create a new placeholder cell. Their purpose is to make it easier for repopulation.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Placeholder(Field field, Location location, Color col) {
        super(field, location, col);        
    }

    /**
    * This is how the Placeholder decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation()); // Note that this gives out a list of living cells. Placeholder cells are DEAD.
        setNextState(false);
        
        int mycoCount = 0, yerCount = 0, bozCount = 0, totalCount = neighbours.size();
        boolean adjMyco = false, adjYer = false, adjBoz = false;
        
        for (Cell cell : neighbours) {
            if (cell instanceof Mycoplasma) {
                mycoCount++;
                adjMyco = true;
            }
            if (cell instanceof Yersinia) {
                yerCount++;
                adjYer = true;
            }
            if (cell instanceof Bozium) {
                bozCount++;
                adjBoz = true;
            }
        }
        
        if (adjMyco & adjBoz) {
            if (neighbours.size() == 1 || neighbours.size() == 2) {
                setNextState(true);
            }
        }
        else if (adjMyco != adjBoz) {
            if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
            }
        }

        if (neighbours.size() == 3) {
            setNextState(true);
        }
        
    }
}