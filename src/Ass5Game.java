import game.Game;

/**
 * This is the main class for the game.
 * It initializes and runs the game.
 */
public class Ass5Game {
    /**
     * Main method to run the game.
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}