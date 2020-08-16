package nl.itslars.playervanish;

import nl.itslars.playervanish.commands.VanishCommand;
import nl.itslars.playervanish.listeners.PlayerJoinQuitListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;

public final class PlayerVanish extends JavaPlugin {

    // The set of players that are online and vanished
    private Set<Player> vanishedPlayers = new HashSet<>();

    @Override
    public void onEnable() {
        // Register 'vanish' command and join/quit listener
        getCommand("vanish").setExecutor(new VanishCommand(this));
        Bukkit.getPluginManager().registerEvents(new PlayerJoinQuitListener(this), this);
    }

    @Override
    public void onDisable() {
        // We create a clone of the vanished players, and then make all players visible again.
        // The clone is necessary to prevent a ConcurrentModificationException
        Set<Player> vanishedPlayersClone = new HashSet<>(vanishedPlayers);
        vanishedPlayersClone.forEach(player -> {
            setPlayerVanished(player, false);
            player.sendMessage(ChatColor.GREEN + "Je bent nu weer zichtbaar voor andere spelers!");
        });
    }

    /**
     * Returns the set of all vanished players
     * @return The set of vanished players
     */
    public Set<Player> getVanishedPlayers() {
        return vanishedPlayers;
    }

    /**
     * Sets the player's state to the vanished value
     * @param player The player that should be (un)vanished
     * @param vanished True if the player should be vanished, false otherwise
     */
    public void setPlayerVanished(Player player, boolean vanished) {
        boolean change;

        if (vanished) {
            change = vanishedPlayers.add(player);
        } else {
            change = vanishedPlayers.remove(player);
        }

        if (!change) return;

        // For all online players, we set the player vanished state
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (player == onlinePlayer) continue;

            if (vanished) onlinePlayer.hidePlayer(this, player);
            else onlinePlayer.showPlayer(this, player);
        }
    }
}
