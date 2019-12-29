/* Name: Elizabeth Vo-Phamhi
 * PennKey: lizvo
 * Recitation: 218
 * 
 * Execution: java Play
 * 
 * Sets up the window for a snake game, plays jazzy music, runs the snake game.
 */
import java.io.*;

public class Play {
    public static void main(String[] args) throws IOException {
        StdAudio.play("mii music.wav");
        PennDraw.setXscale(0, 200);
        PennDraw.setYscale(0, 200);
        PennDraw.clear(PennDraw.WHITE);
        PennDraw.setPenColor(135, 50, 65);
        PennDraw.setFontSize(24);
        PennDraw.setFontBold();
        PennDraw.text(100, 150, "Press the Space bar to Start!");
        PennDraw.setFontSize(14);
        PennDraw.setFontPlain();
        PennDraw.text(100, 140, "Controls: W = up, S = down, A = left, D = right");
        SnakeGame.topScoresTable();
        while (true) {
            if (PennDraw.hasNextKeyTyped()) {
                if (PennDraw.nextKeyTyped() == ' ') {
                    new SnakeGame(); 
                }
            } 
        }
    }
}