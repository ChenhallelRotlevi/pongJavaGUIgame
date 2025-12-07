package game.listeners;

import game.Game;
import game.objects.Ball;
import game.objects.Block;
import game.Counter;

/**
 * BallRemover is a HitListener that removes balls from the game
 * when they hit a specific block.
 * It also updates the count of remaining balls in the game.
 */
public class BallRemover implements HitListener {
    private Game game;
    private Counter remainingBalls;

    /**
     * Constructor.
     * @param game the game
     * @param removedBalls the number of balls to be removed
     */
    public BallRemover(Game game, Counter removedBalls) {
        this.game = game;
        this.remainingBalls = removedBalls;
    }

    /**
     * This method is called whenever a ball is hit.
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(this.game);
        this.remainingBalls.decrease(1);
    }
}
