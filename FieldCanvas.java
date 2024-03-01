import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color; 

/**
 * Provide a graphical view of the field. This is a custom node for the user interface. 
 *
 * @author Jeffery Raphael
 * @version 2024.02.03
 * 
 * Added code by:
 * @author John Paul D. San Diego
 * @k-number 21190412
 * 
 * @author Jia Cheng Lim
 * @k-number 23102614
 */

public class FieldCanvas extends Canvas {

    private static final int GRID_VIEW_SCALING_FACTOR = 6;
    private int width, height;
    private int xScale, yScale;
    GraphicsContext gc;
    
    /**
    * Create a new FieldView component.
    */
    public FieldCanvas(int height, int width) {
        super(height, width);
        gc = getGraphicsContext2D();
        this.height = height;
        this.width = width;
    }
    
    public void setScale(int gridHeight, int gridWidth) {
        xScale = width / gridWidth;
        yScale = height / gridHeight;
    
        if (xScale < 1)
            xScale = GRID_VIEW_SCALING_FACTOR;
    
        if (yScale < 1)
            yScale = GRID_VIEW_SCALING_FACTOR;
    }
  
    /**
    * Paint a rectangle of the given color on the canvas
    */
    public void drawMark(int x, int y, Color color) {
        gc.setFill(color);
        gc.fillRect((x+8) * xScale, (y-1) * yScale, xScale-1, yScale-1);//move the cells board to center
        
    }
}
