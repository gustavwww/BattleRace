package model;

/**
 * GameListener used when listening for game updates.
 */
public interface GameListener {

    /**
     * Notifies that a new player joined the game.
     * @param player The new player.
     */
    void playerJoined(Player player);

    /**
     * Notifies that a player left the game.
     * @param player The player that left.
     */
    void playerLeft(Player player);
}
