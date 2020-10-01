package model;

import java.util.ArrayList;
import java.util.List;

/**
 * GamesManager controls every game in play, and makes sure no game has the same id.
 */
public class GamesManager {

    private static GamesManager instance = null;

    private final List<Game> games = new ArrayList<>();
    private int currentGameID = 1400;

    private GamesManager() {}

    /**
     * Creates a new Game.
     * @param host The player who's creating the game.
     * @return An initialized game.
     */
    public synchronized Game createGame(Player host) {
        Game game = new Game(generateGameID(), host);
        games.add(game);
        return game;
    }

    /**
     * Removes a game from the "known games" list.
     * @param id The game id.
     */
    public synchronized void removeGame(String id) {
        games.removeIf(g -> g.getId().equalsIgnoreCase(id));
    }

    /**
     * Finds a game by it's identifier.
     * @param id The identifier.
     * @return A game.
     */
    public synchronized Game findGameByID(String id) {
        for (Game g : games) {
            if (id.equalsIgnoreCase(g.getId())) {
                return g;
            }
        }
        return null;
    }

    private synchronized String generateGameID() {
        currentGameID += 1;
        return Integer.toString(currentGameID);
    }

    /**
     * @return The GamesManager instance.
     */
    public static synchronized GamesManager getInstance() {
        if (instance == null) {
            instance = new GamesManager();
        }
        return instance;
    }


}
