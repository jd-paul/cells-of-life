import javafx.scene.paint.Color; 
import java.util.List;

/**
 * Write a description of class Microbiota here.
 *
 * @author (your name)
 * @version (a version number or a date)
 */
public class Microbiota extends Cell {
    int age;
    public static final int MAX_AGE = 200;
    /**
     * Create a new Mycoplasma.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Microbiota(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, disease);
        age = 0;
    }

    /**
     * This is how the Microbiota decides if it's alive or not
     */
    public void act() {
        List<Cell> neighbours = getField().getLivingAndDeadNeighbours(getLocation());
        setNextState(false);
        setNextDiseaseState(false);
        
        if (age <= MAX_AGE) {
            setNextState(true);
        }
        
        if (isAlive() == true) {
            for (Cell cell : neighbours) {
                if (cell instanceof Placeholder) {
                    int n = rand.nextInt(3);
                    
                    if (n == 0) {
                        cell.setNextState(true);
                        cell.setNextDiseaseState(false);
                        cell.setNextCell("mycoplasma");
                    } else if (n == 1) {
                        cell.setNextState(true);
                        cell.setNextDiseaseState(false);
                        cell.setNextCell("mycoplasma");
                    } else if (n == 2) {
                        cell.setNextState(true);
                        cell.setNextDiseaseState(false);
                        cell.setNextCell("bozium");
                    }
                }
            }
        }
        
        age++;
    }
}