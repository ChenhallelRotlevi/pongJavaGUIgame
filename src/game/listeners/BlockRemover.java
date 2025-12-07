package game.listeners;

import game.Game;
import game.objects.Ball;
import game.objects.Block;
import game.Counter;

/**
 * BlockRemover is a HitListener that removes blocks from the game
 * when they are hit by a ball.
 * It also updates the count of remaining blocks in the game.
 */
public class BlockRemover implements HitListener {
    private Game game;
    private Counter remainingBlocks;

    /**
     * Constructor.
     * @param game the game
     * @param removedBlocks the number of blocks to be removed
     */
    public BlockRemover(Game game, Counter removedBlocks) {
        this.game = game;
        this.remainingBlocks = removedBlocks;
    }

    /**
     * This method is called whenever a block is hit.
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        // Only remove if ball color is different
        if (!beingHit.ballColorMatch(hitter)) {
            hitter.setColor(beingHit.getColor());
            beingHit.removeHitListener(this);
            beingHit.removeFromGame(game);
            remainingBlocks.decrease(1);
        }
    }
}
