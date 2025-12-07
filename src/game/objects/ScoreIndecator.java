package game.objects;

import game.collision.Sprite;
import biuoop.DrawSurface;
import game.Game;
import game.Counter;
import java.awt.Color;

/**
 * ScoreIndecator displays the current score in the game.
 * It implements the Sprite interface to be drawn on the screen.
 */
public class ScoreIndecator implements Sprite {
    private Counter score;

    /**
     * Constructs a ScoreIndecator with the given score counter.
     * @param score the score counter to display
     */
    public ScoreIndecator(Counter score) {
        this.score = score;
    }

    /**
     * this functions draws the elements.
     * @param d
     */
    public void drawOn(DrawSurface d) {
        d.setColor(Color.BLACK);
        d.drawText(360, 18, "Score: " + score.getValue(), 16);
    }
    /**
     * changes the shape after time passed.
     */
    public void timePassed() {
        // No action needed for ScoreIndecator
    }

    /**
     * adds the elements to the game.
     * @param g
     */
    public void addToGame(Game g) {
        g.addSprite(this);
    }

}
