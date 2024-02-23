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
    private Color infectedColor = Color.rgb(225, 234, 0);

    private final int YERSINIA_DISEASE_CHANCE = 1;
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
        if (randomNumber == YERSINIA_DISEASE_CHANCE) {
            setDiseaseState(true);
            setColor(infectedColor);
        }
        System.out.println("test");
    }

    /**
     * This is how the Yersinia decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingNeighbours(getLocation());
        setNextState(false);

        boolean adjMyco = false, adjYer = false, adjBoz = false;

        /* IMPLEMENT WITH NEW RULES! */
        /*
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
        else{
        randomDisease();
        }
        }
        }
         */

        if (isAlive() == true) {

            for (Cell cell : neighbours) {
                if(cell.hasDisease()){
                    setDiseaseState(true);
                    setColor(infectedColor);
                    System.out.println("test2");
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

            if(adjMyco && !adjBoz && (neighbours.size() == 1 || neighbours.size() == 2 || neighbours.size() == 3)){
                setNextState(true);
            }
            else if(!adjMyco && adjBoz && (neighbours.size() == 1 || neighbours.size() == 2 || neighbours.size() == 3)){
                setNextState(true);
            }
            else if (neighbours.size() == 2 || neighbours.size() == 3){
                setNextState(true);
            }
            if(hasDisease()){
                randomDie();
            }
            else{
                randomDisease();
                System.out.println("test3");
            }
        }
        /*
        if (isAlive() == false) {
        if (neighbours.size() == 3) {
        setNextState(true);
        }
        }
         */
    }
}
