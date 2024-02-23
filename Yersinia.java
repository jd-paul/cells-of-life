import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Write a description of class Yersinia here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Yersinia extends Cell
{
    private Color yerColor = Color.rgb(225, 0, 0);
    private Color infectedColor = Color.rgb(225, 234, 0);
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Yersinia(Field field, Location location, Color col) {
        super(field, location, col);
    }
    public void randomDisease(){
        // Generate a random number between 0 (inclusive) and 10 (exclusive)
        int randomNumber = rand.nextInt(2);

        // Check if the random number is 0 (1/10 chance) to die 
        if (randomNumber == 0) {
            setDiseaseState(true);
        }
    }
    /**
    * This is how the Yersinia decides if it's alive or not
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
                else{
                    randomDisease();
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
