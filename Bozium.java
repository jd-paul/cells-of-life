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

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        /* IMPLEMENT WITH NEW RULES! */
        if (isAlive() == true) {
            for (Cell cell : neighbours) {
                if(cell.hasDisease()){
                    setNextDiseaseState(true);
                    setColor(infectedColor);
                }
                if (cell instanceof Mycoplasma) {
                    adjMyco = true;
                }
                if (cell instanceof Yersinia) {
                    adjYer = true;
                }
                if (cell instanceof Bozium) {
                    adjBoz = true;
                }
            }

            if(adjMyco && !adjYer && neighbours.size() == 1){
                setNextState(true);
            }
            else if((adjMyco && adjYer) && ((neighbours.size() == 2 || neighbours.size() == 3))){
                setNextState(true);
            }
            else if (neighbours.size() == 2 || neighbours.size() == 3){
                setNextState(true);
            }

            if(hasDisease()){
                randomDie();
            }
        }
    }
}

