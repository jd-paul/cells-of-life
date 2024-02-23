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
    private Color yerColor = Color.rgb(225, 0, 0);
    private Color infectedColor = Color.rgb(225, 234, 0);
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Bozium(Field field, Location location, Color col) {
        super(field, location, col);
    }
    
    /**
    * This is how the Mycoplasma decides if it's alive or not
    */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);
        
        /* IMPLEMENT WITH NEW RULES! */
        if (isAlive() == true) {
            if (neighbours.size() == 2 || neighbours.size() == 3) {
                setNextState(true);
                for(Cell cell: neighbours) {
                    if(cell.getColor().equals(yerColor)){
                        setDiseaseState(true);
                        setColor(infectedColor);
                    }
                }
                if(getDiseaseState()){
                    randomDie();                    
                } 
            }
            else {
                setNextState(false);
            }
                
        }
        if (isAlive() == false) {
            if (neighbours.size() == 3) {
                setNextState(true);
            }
        }
        
    }
}
