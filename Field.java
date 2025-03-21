import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Represent a rectangular grid of field positions.
 * Each position stores a single cell
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

public class Field {
    private static final Random rand = Randomizer.getRandom();
    private int depth, width;
    private Cell[][] field;

    /**
     * Represent a field of the given dimensions.
     * @param depth The depth of the field.
     * @param width The width of the field.
     */
    public Field(int depth, int width) {
        this.depth = depth;
        this.width = width;
        field = new Cell[depth][width];
    }

    /**
     * Empty the field.
     */
    public void clear() {
        for (int row = 0; row < depth; row++) {
            for (int col = 0; col < width; col++) {
                field[row][col] = null;
            }
        }
    }

    /**
     * Clear the given location.
     * @param location The location to clear.
     */
    public void clear(Location location) {
        field[location.getRow()][location.getCol()] = null;
    }

    /**
     * Place a cell at the given location.
     * If there is already a cell at the location it will be lost.
     * @param cell The cell to be placed.
     * @param row Row coordinate of the location.
     * @param col Column coordinate of the location.
     */
    public void place(Cell cell, int row, int col) {
        place(cell, new Location(row, col));
    }

    /**
     * Place a cell at the given location.
     * If there is already a cell at the location it will be lost.
     * @param cell The cell to be placed.
     * @param location Where to place the cell.
     */
    public void place(Cell cell, Location location) {
        field[location.getRow()][location.getCol()] = cell;
    }

    /**
     * Return the cell at the given location, if any.
     * @param location Where in the field.
     * @return The cell at the given location, or null if there is none.
     */
    public Cell getObjectAt(Location location) {
        return getObjectAt(location.getRow(), location.getCol());
    }

    /**
     * Return the cell at the given location, if any.
     * @param row The desired row.
     * @param col The desired column.
     * @return The cell at the given location, or null if there is none.
     */
    public Cell getObjectAt(int row, int col) {
        return field[row][col];
    }

    /**
     * Generate a random location that is adjacent to the
     * given location, or is the same location.
     * The returned location will be within the valid bounds
     * of the field.
     * @param location The location from which to generate an adjacency.
     * @return A valid location within the grid area.
     */
    public Location randomAdjacentLocation(Location location) {
        List<Location> adjacent = adjacentLocations(location);
        return adjacent.get(0);
    }

    /**
     * Return a shuffled list of locations adjacent to the given one.
     * The list will not include the location itself.
     * All locations will lie within the grid.
     * @param location The location from which to generate adjacencies.
     * @return A list of locations adjacent to that given.
     */
    public List<Location> adjacentLocations(Location location) {
        assert location != null : "Null location passed to adjacentLocations";
        // The list of locations to be returned.
        List<Location> locations = new LinkedList<>();
        if (location != null) {
            int row = location.getRow();
            int col = location.getCol();
            for (int roffset = -1; roffset <= 1; roffset++) {
                int nextRow = row + roffset;
                if (nextRow >= 0 && nextRow < depth) {
                    for (int coffset = -1; coffset <= 1; coffset++) {
                        int nextCol = col + coffset;
                        // Exclude invalid locations and the original location.
                        if (nextCol >= 0 && nextCol < width && (roffset != 0 || coffset != 0)) {
                            locations.add(new Location(nextRow, nextCol));
                        }
                    }
                }
            }

            // Shuffle the list. Several other methods rely on the list
            // being in a random order.
            Collections.shuffle(locations, rand);
        }
        return locations;
    }

    /**
     * Get a shuffled list of living neighbours
     * @param location Get locations adjacent to this.
     * @return A list of living neighbours
     */
    public List<Cell> getLivingNeighbours(Location location) {
      assert location != null : "Null location passed to adjacentLocations";
      List<Cell> neighbours = new LinkedList<>();

      if (location != null) {
        List<Location> adjLocations = adjacentLocations(location);

        for (Location loc : adjLocations) {
          Cell cell = field[loc.getRow()][loc.getCol()];
          if (cell.isAlive())
            neighbours.add(cell);
        }
        Collections.shuffle(neighbours, rand);
      }
      return neighbours;
    }
    
    /**
     * Get a shuffled list of dead neighbours
     * @param location Get locations adjacent to this.
     * @return A list of living neighbours
     */
    public List<Cell> getLivingAndDeadNeighbours(Location location) {
      assert location != null : "Null location passed to adjacentLocations";
      List<Cell> neighbours = new LinkedList<>();

      if (location != null) {
        List<Location> adjLocations = adjacentLocations(location);

        for (Location loc : adjLocations) {
          Cell cell = field[loc.getRow()][loc.getCol()];
          neighbours.add(cell);
        }
        Collections.shuffle(neighbours, rand);
      }
      return neighbours;
    }

    /**
     * Return the depth of the field.
     * @return The depth of the field.
     */
    public int getDepth() {
        return depth;
    }

    /**
     * Return the width of the field.
     * @return The width of the field.
     */
    public int getWidth() {
        return width;
    }
}
