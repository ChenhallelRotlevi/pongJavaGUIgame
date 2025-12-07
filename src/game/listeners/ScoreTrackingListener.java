package game.listeners;

import game.objects.Ball;
import game.objects.Block;
import game.Counter;
/**
 * ScoreTrackingListener is a HitListener that updates the score
 * whenever a block is hit by a ball.
 * It adds a specified score to the current score counter.
 */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;
    private int scoreToAdd;

    /**
     * Constructor.
     * @param scoreCounter the score counter
     * @param scoreToAdd the score to add
     */
    public ScoreTrackingListener(Counter scoreCounter, int scoreToAdd) {
        this.currentScore = scoreCounter;
        this.scoreToAdd = scoreToAdd;
    }

    /**
     * This method is called whenever a block is hit.
     * @param beingHit the block that was hit
     * @param hitter   the ball that hit it
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        currentScore.increase(scoreToAdd);
    }
}
