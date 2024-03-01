import javafx.scene.paint.Color; 
import java.util.List;
import java.util.Random;

/**
 * Placeholder cell is a 'dead' cell. All living cells that have turned dead are converted into this
 * new cell. This cell conversion is dealt through the simulator.
 *
 * Added code by:
 * @author John Paul D. San Diego
 * @k-number 21190412
 * 
 * @author Jia Cheng Lim
 * @k-number 23102614
 */
public class Placeholder extends Cell
{
    /**
     * Create a new placeholder cell. Their purpose is to make it easier for repopulation.
     *
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Placeholder(Field field, Location location, Color col, boolean disease) {
        super(field, location, col, false); // Placeholders will never have a disease.
    }

    /**
     * This is how the Placeholder decides if it's alive or not. It ignores the presence of Microbiota cells when
     * considering adjacency edge cases.
     */
    public void act() {
        List<Cell> neighbours = getLivingNeighbours();
        setNextState(false);
        setNextCell("placeholder");

        boolean adjMyco = false, adjYer = false, adjBoz = false, adjMic = false, nearbyDisease = false;
        Random rand = Randomizer.getRandom();

        for (Cell cell : neighbours) {
            if (cell instanceof Mycoplasma) {adjMyco = true;}
            if (cell instanceof Yersinia) {adjYer = true;}
            if (cell instanceof Bozium) {adjBoz = true;}
            if (cell instanceof Microbiota) {adjMic = true;}
            if (cell.hasDisease()) {nearbyDisease = true;}
        }

        if(!adjMic) {
            if (!adjMyco && !adjBoz && !adjYer) {
                setNextState(false);
                setNextCell("placeholder");
            }

            /**
             * Only one type of nearby cell
             */
            else if (adjMyco && !adjBoz && !adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);
                    setNextCell("mycoplasma");
                    if (nearbyDisease) {setNextDiseaseState(true);}
                }
            }
            else if (!adjMyco && adjBoz && !adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);
                    setNextCell("bozium");
                }
            }
            else if (!adjMyco && !adjBoz && adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);
                    setNextCell("yersinia");
                    if (nearbyDisease) {setNextDiseaseState(true);}
                }
            }

            /**
             * Only two types of nearby cells
             */
            else if (adjMyco && adjBoz && !adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);
                    int n = rand.nextInt(2);
                    if (n == 0) {
                        setNextCell("mycoplasma");
                    }
                    else if (n == 1) {setNextCell("bozium");}
                }
            }
            else if (!adjMyco && adjBoz && adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);

                    setNextCell("yersinia");
                    if (nearbyDisease) {
                        if (healDiseaseCheck()) {setNextDiseaseState(false);}
                        else {setNextDiseaseState(true);}
                    }
                }
            }
            else if (adjMyco && !adjBoz && adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);
                    int n = rand.nextInt(3);
                    if (n == 0 || n == 1) {
                        setNextCell("mycoplasma");
                        if (nearbyDisease) {
                            if (healDiseaseCheck()) {setNextDiseaseState(false);}
                            else {setNextDiseaseState(true);}
                        }
                    }
                    else if (n == 2) {
                        setNextCell("yersinia");
                        if (nearbyDisease) {
                            if (healDiseaseCheck()) {setNextDiseaseState(false);}
                            else {setNextDiseaseState(true);}
                        }
                    }
                }
            }

            /**
             * Only three types of nearby cells
             */
            else if (adjMyco && adjBoz && adjYer) {
                if (neighbours.size() == 3) {
                    setNextState(true);
                    int n = rand.nextInt(3);
                    if (n == 0) {
                        setNextCell("mycoplasma");
                        if (nearbyDisease) {
                            if (healDiseaseCheck()) {setNextDiseaseState(false);}
                            else {setNextDiseaseState(true);}
                        }
                    }
                    else if (n == 1) {setNextCell("bozium");}
                    else if (n == 2) {
                        setNextCell("yersinia");
                        if (nearbyDisease) {
                            if (healDiseaseCheck()) {setNextDiseaseState(false);}
                            else {setNextDiseaseState(true);}
                        }
                    }
                }
            }
        }
        else {
            /**
             * Randomly choose the new cell type to turn into next turn.
             */

            int n = rand.nextInt(8);
            if (n == 0) {
                setNextState(true);
                setNextCell("bozium");
            } else if (n == 1) {
                setNextState(false);
                setNextCell("placeholder");
            } else {
                setNextState(true);
                setNextDiseaseState(false);
                setNextCell("mycoplasma");
            }
        }
    }
}