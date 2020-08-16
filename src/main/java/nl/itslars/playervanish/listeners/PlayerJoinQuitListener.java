package nl.itslars.playervanish.listeners;

import nl.itslars.playervanish.PlayerVanish;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerJoinQuitListener implements Listener {

    private PlayerVanish plugin;

    public PlayerJoinQuitListener(PlayerVanish plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        // We hide all vanished, online players from the newly joined player
        for (Player online : Bukkit.getOnlinePlayers()) {
            if (plugin.getVanishedPlayers().contains(online)) {
                player.hidePlayer(plugin, online);
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        // We remove the player vanished state
        plugin.setPlayerVanished(event.getPlayer(), false);
    }
}