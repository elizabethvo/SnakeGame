/* Name: Elizabeth Vo-Phamhi
 * PennKey: lizvo
 * Recitation: 218
 * 
 * Execution: n/a
 * 
 * A snake game class file to make snake games, which includes control of 
 * the snake based on the user's keyboard commands and methods to draw the 
 * snake at every frame. 
 * 
 * This is not the actual game file; please play the game using Play.java
 */
import java.io.*;

public class SnakeGame {
    // load up the current high scores
    private static In inStream = new In("topscores.txt");
    private static int highscore1 = inStream.readInt();
    private static int highscore2 = inStream.readInt();
    private static int highscore3 = inStream.readInt(); 
    
    //relating to the PennDraw game window
    private static final int WINDOWSIZE = 200; //for PennDraw scale
    
    //snake specs
    private static final int SNAKEUNIT = 4; //arbitrary
    private static int snakeLength;
    private boolean dead;
    private static boolean headUp = false;
    private static boolean headDown = false;  
    private static boolean headLeft = true;
    private static boolean headRight = false;
    private static int[] xvals;
    private static int[] yvals;
    
    private static int wiggleroom = 4; //arbitrary range of error for eating food
    private static int score; //initial score   
    
    // new food instance
    private static Food food;
    /**
     * Input:       none
     * Output:      new SnakeGame instance
     * Description: constructor for a brand new snake game. Uses arrays of maximum 
     *              size 2000 to keep track of the snake's x- and y- coordinates 
     * 
     */ 
    public SnakeGame() throws IOException {
        score = 0;
        snakeLength = 5; // initial size of the snake is 5 blocks long
        dead = false; //snake should initially be alive, not dead
        yvals = new int[2000]; //snake length should not exceed 2000
        xvals = new int[2000];
        
        // place every part of the snake in the middle of the screen
        int j = 0;
        while (j < snakeLength) {
            xvals[j] = WINDOWSIZE / 2;
            yvals[j] = WINDOWSIZE / 2;
            j++;
        }
        
        food = new Food();
        
        while (!dead) {
            PennDraw.enableAnimation(30);
            PennDraw.clear(PennDraw.WHITE);
            PennDraw.setPenRadius(0.04);
            PennDraw.setPenColor(PennDraw.PINK);
            PennDraw.square(WINDOWSIZE / 2, WINDOWSIZE / 2, 100); 
            
            /* check the key pressed, and change the snake's direction. 
             * if the user tries to go in the complete opposite direction
             * of the snake's current trajectory, the snake just continues
             * on its current trajectory.
             */
            if (PennDraw.hasNextKeyTyped()) {
                char key = PennDraw.nextKeyTyped();
                if (key == 's' || key == 'S') {
                    if (!headUp) {
                        headDown = true;
                        headRight = false;
                        headLeft = false;
                        headUp = false;
                    }
                } else if (key == 'w' || key == 'W') {
                    if (!headDown) {
                        headUp = true;
                        headRight = false;
                        headLeft = false;
                        headDown = false;
                    }
                } else if (key == 'a' || key == 'A') {
                    if (!headRight) {
                        headLeft = true;
                        headDown = false;
                        headUp = false;
                        headRight = false;
                    }
                }  else if (key == 'd' || key == 'D') {
                    if (!headLeft) {
                        headRight = true;
                        headDown = false;
                        headUp = false;
                        headLeft = false;
                    }
                }
            }
            //clear the hasNextKeyTyped() queue
            if (PennDraw.hasNextKeyTyped()) {
                PennDraw.nextKeyTyped();
            }
            //move the snake, applying the most up-to-date direction
            move();
            
            /* check if snake eats a food
             * if so, increase the score and snakeLength, and make
             * a new food instance.
             */
            int headX = xvals[0];
            int headY = yvals[0];
            if ((headX >= food.getX() - wiggleroom) && 
                (headX <= food.getX() + wiggleroom)) {
                if ((headY >= food.getY() - wiggleroom) && 
                    (headY <= food.getY() + wiggleroom)) {
                    StdAudio.play("munch.wav");
                    food = new Food();
                    snakeLength++;
                    score++;
                }
            }
            
            // check if the snake's head goes out of bounds
            if ((headY >= WINDOWSIZE || headX >= WINDOWSIZE) ||
                (headY <= 0 || headX <= 0)) {
                dead = true;
            }
            
            // check if the snake head hits body at any spot along its length
            int i = snakeLength;
            while (i > 1) {
                if ((headX == xvals[i]) && (headY == yvals[i])) {
                    dead = true;
                }
                i--;
            }
            draw();
            // show current score in the top left corner
            PennDraw.setPenColor(135, 50, 65);
            PennDraw.text(20, WINDOWSIZE - 10, "Score: " + score);
            
            //if the snake is dead, show the end card and ask to replay
            if (dead == true) {
                endCard();
            }
            PennDraw.advance();
        }   
    }  
    /**
     * Input:       none
     * Output:      void
     * Description: Displays the top scores as a table.
     */ 
    public static void topScoresTable() {
        PennDraw.setPenRadius(0.008);
        PennDraw.setPenColor(255, 232, 232);
        PennDraw.filledRectangle(100, 80, 50, 40);
        PennDraw.setPenColor(PennDraw.WHITE);
        PennDraw.rectangle(100, 80, 50, 40);
        PennDraw.line(50, 60, 150, 60);
        PennDraw.line(50, 80, 150, 80);
        PennDraw.line(50, 100, 150, 100);
        PennDraw.line(100, 100, 100, 40);
        PennDraw.setPenColor(158, 79, 98);
        PennDraw.setFontSize(18);
        PennDraw.text(100, 110, "Top Scores");
        PennDraw.setFontPlain();
        PennDraw.setFontSize(15);
        PennDraw.text(100, 90, "1st                       " + highscore1);
        PennDraw.text(100, 70, "2nd                       " + highscore2);
        PennDraw.text(100, 50, "3rd                       " + highscore3);
    }
    /**
     * Input:       none
     * Output:      void
     * Description: updates the top score table by comparing the current score
     *              to the existing high scores listed in the topscores.txt file.
     */  
    public static void updateScores() throws IOException {
        if (score > highscore1) {
            highscore3 = highscore2;
            highscore2 = highscore1;
            highscore1 = score;       
        }
        if (score < highscore1 && score > highscore2) {
            highscore3 = highscore2;
            highscore2 = score;
        }
        if (score < highscore2 && score > highscore3) {
            highscore3 = score;
        }
        PrintWriter out = new PrintWriter("topscores.txt");
        out.println("" + highscore1);
        out.println("" + highscore2);
        out.println("" + highscore3);
        out.close();
    }
    /**
     * Input:       none
     * Output:      void
     * Description: move the snake by shifting the body to the left (towards
     *              the head) and updating the head's x- and y- coordinates
     *              based on the direction the snake is currently heading.
     */     
    public static void move() {
        //move the body of the snake to follow the preceding x- y- coords
        int i = snakeLength;
        while (i > 0) {
            xvals[i] = xvals[i - 1];
            yvals[i] = yvals[i - 1];
            i--;
        }
        //update the head's position based on the current trajectory
        if (headUp) {
            yvals[0] += SNAKEUNIT;
        }
        if (headDown) {
            yvals[0] -= SNAKEUNIT;
        }
        if (headLeft) {
            xvals[0] -= SNAKEUNIT;
        }
        if (headRight) {
            xvals[0] += SNAKEUNIT;
        }     
    }
    /**
     * Input:       none
     * Output:      void
     * Description: Draw the food instance, draw the snake at its current
     *              position using the x- and y- coordinate arrays.
     */
    public static void draw() {
        PennDraw.picture(food.getX(), food.getY(), food.getImg()); 
        PennDraw.setPenRadius(0.004);
        //draw snake head
        int headX = xvals[0];
        int headY = yvals[0];
        PennDraw.setPenColor(184, 209, 138);
        PennDraw.filledSquare(headX, headY, SNAKEUNIT / 2);
        PennDraw.setPenColor(PennDraw.BLACK);
        PennDraw.point(headX - 1, headY); //eye
        PennDraw.point(headX + 1, headY); //eye
        //draw rest of body
        PennDraw.setPenColor(184, 209, 138);
        for (int i = 1; i < snakeLength; i++) {
            PennDraw.filledSquare(xvals[i], yvals[i], SNAKEUNIT / 2);
        }
    }
    /**
     * Input:       none
     * Output:      void
     * Description: When the snake has died and the game has ended, play
     *              a given sound effect ("wahh.wav"), display the user's
     *              final score, update and display the high score table, 
     *              and ask the user for a prompt to replay the game.
     */
    public static void endCard() throws IOException {
        StdAudio.play("wahh.wav");
        PennDraw.square(WINDOWSIZE / 2, WINDOWSIZE / 2, 100); 
        PennDraw.setPenColor(135, 50, 65);
        PennDraw.setFontSize(24);
        PennDraw.text(100, 175, "Game Over!");
        PennDraw.setFontSize(20);
        PennDraw.text(100, 155, "Final Score: " + score);
        PennDraw.setFontSize(16);
        PennDraw.text(100, 140, "Press the space bar to play again.");
        updateScores();
        topScoresTable();
    }
}
