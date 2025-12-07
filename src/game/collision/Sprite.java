package game.collision;

import biuoop.DrawSurface;
import game.Game;

/**
 * Sprite interface is for every element int the game.
 */
public interface Sprite {
    /**
     * this functions draws the elements.
     * @param d
     */
    void drawOn(DrawSurface d);
    /**
     * changes the shape after time passed.
     */
    void timePassed();

    /**
     * adds the elements to the game.
     * @param g
     */
    void addToGame(Game g);
}

