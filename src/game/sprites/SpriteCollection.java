package game.sprites;

import biuoop.DrawSurface;
import game.collision.Sprite;

import java.util.ArrayList;
import java.util.List;
/**
 * A collection of sprites (game elements that can be drawn and updated).
 */
public class SpriteCollection {
    private java.util.List<Sprite> sprites = new java.util.ArrayList<>();

    /**
     * Adds a sprite to the collection.
     * @param s the sprite to add
     */
    public void addSprite(Sprite s) {
        sprites.add(s);
    }

    /**
     * Calls timePassed() on all sprites.
     */
    public void notifyAllTimePassed() {
        List<Sprite> spritesCopy = new ArrayList<>(this.sprites);
        for (Sprite s : spritesCopy) {
            s.timePassed();
        }

    }

    /**
     * Draws all sprites on the given DrawSurface.
     * @param d the surface to draw on
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : sprites) {
            s.drawOn(d);
        }
    }

    /**
     * Removes a sprite from the collection.
     * @param s the sprite to remove
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }
}
