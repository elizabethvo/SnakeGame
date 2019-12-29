/* Name: Elizabeth Vo-Phamhi
 * PennKey: lizvo
 * Recitation: 218
 * 
 * Execution: n/a
 * 
 * A class file to make random food instances. 
 */

public class Food {
    // food coords
    private int x;
    private int y;
    private String img;
    
    /**
     * Input: nothing
     * Output: a new food instance
     * Description: constructor for a food instance with random
     *              x- and y- coordinates, as well as a randomly
     *              selected filename for the purpose of calling
     *              PennDraw.picture() with different food images.
     */
    public Food() {
        // set random x and y coords between 10 and 195
        // 10 and 195 chosen to fit within the window dimensions of 200x200.
        x = 10 + (int) (Math.random() * ((195 - 10) + 1));
        y = 10 + (int) (Math.random() * ((195 - 10) + 1));
        
        double r = Math.random();
        if (r < 0.2) {
            img = "sushi.png";
        } else if (r >= 0.2 && r < 0.4) {
            img = "taco.png";
        } else if (r >= 0.4 && r < 0.6) {
            img = "milk.png";
        } else if (r >= 0.6 && r < 0.8) {
            img = "donut.png";
        } else if (r >= 0.8 && r < 1.0) {
            img = "popsicle.png";
        }      
    }
    
    /**
     * Input: nothing
     * Output: the x coordinate of the food instance
     * Description: getter for the x coord of the food instance
     */
    public int getX() {
        return x;
    }
    
    /**
     * Input: nothing
     * Output: the y coordinate of the food instance
     * Description: getter for the y coord of the food instance
     */
    public int getY() {
        return y;
    } 
    
    /**
     * Input: nothing
     * Output: the filename for the food picture to be drawn.
     * Description: getter for the filename for the food picture to be drawn.
     */
    public String getImg() {
        return img;
    }
}