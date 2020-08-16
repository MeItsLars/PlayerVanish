package nl.itslars.playervanish.commands;

import nl.itslars.playervanish.PlayerVanish;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class VanishCommand implements CommandExecutor {

    private PlayerVanish plugin;

    public VanishCommand(PlayerVanish plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Je moet een speler zijn om dit commando uit te kunnen voeren!");
            return false;
        }

        Player player = (Player) sender;
        if (plugin.getVanishedPlayers().contains(player)) {
            plugin.setPlayerVanished(player, false);
            player.sendMessage(ChatColor.GREEN + "Je bent nu weer zichtbaar voor andere spelers!");
        } else {
            plugin.setPlayerVanished(player, true);
            player.sendMessage(ChatColor.GREEN + "Je bent nu verborgen voor andere spelers!");
        }
        return true;
    }
}