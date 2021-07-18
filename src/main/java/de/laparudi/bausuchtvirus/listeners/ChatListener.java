package de.laparudi.bausuchtvirus.listeners;

import de.laparudi.bausuchtvirus.BausuchtVirus;
import de.laparudi.bausuchtvirus.util.VirusUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener {

    private final VirusUtil util = BausuchtVirus.getPlugin().getUtil();

    @EventHandler
    public void onVirusHelp(AsyncPlayerChatEvent event) {
        if(!event.getMessage().contains("#bausuchtvirus")) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        
        if(player.hasPermission("bausucht.virus")) {
            player.sendMessage("§bBausucht-Virus von §3LapaRudi §7| §bHilfe");
            player.sendMessage("");
            player.sendMessage("§7§oWenn du keinen Spieler angibst wird der Bausucht-Virus für dich gestartet.");
            player.sendMessage("§7§oWenn du '*' oder 'all' angibst wird der Bausucht-Virus für alle Spieler gestartet.");
            player.sendMessage("");
            player.sendMessage("§e#startvirus [Spieler] §8- §7Starte den Bausucht-Virus");
            player.sendMessage("§e#stopvirus [Spieler] §8- §7Stoppe den Bausucht-Virus");
            player.sendMessage("");
            player.sendMessage("§bBausucht-Virus von §3LapaRudi §7| §bHilfe");
            
        } else
            player.sendMessage("Das darfst du nicht :>");
    }
    
    @EventHandler
    public void onVirusStart(AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("#startvirus")) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();
        
        if (player.hasPermission("bausucht.virus")) {
            String message = event.getMessage();
            
            if(message.endsWith("#startvirus")) {
                if(!util.isRunning(player)) {
                    util.startVirus(player);
                    player.sendMessage("Bausucht-Virus gestartet.");
                    
                } else
                    player.sendMessage("Bausucht-Virus wurde bereits gestartet.");
                return;
            }
            String arg = message.substring(message.lastIndexOf("#startvirus") +12);

            if(arg.equals("*") || arg.equalsIgnoreCase("all")) {
                Bukkit.getOnlinePlayers().forEach(util::startVirus);
                player.sendMessage("Bausucht-Virus für alle Spieler gestartet.");
                return;
            }
            
            Player target = Bukkit.getPlayer(arg);
            if(target != null) {
                if(!util.isRunning(target)) {
                    util.startVirus(target);
                    player.sendMessage("Bausucht-Virus für " + target.getName() + " gestartet.");
                    
                } else
                    player.sendMessage("Bausucht-Virus für " + target + " wurde bereits gestartet.");
            } else
                player.sendMessage("Spieler nicht gefunden.");

        } else
            player.sendMessage("§cDu darfst den Bausucht-Virus nicht starten :o");
    }

    @EventHandler
    public void onVirusStop(AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("#stopvirus")) {
            return;
        }
        event.setCancelled(true);
        Player player = event.getPlayer();

        if (player.hasPermission("bausucht.virus")) {
            String message = event.getMessage();
            
            if(message.endsWith("#stopvirus")) {
                if(util.isRunning(player)) {
                    util.stopVirus(player);
                    player.sendMessage("Bausucht-Virus gestoppt.");
                    
                } else
                    player.sendMessage("Es wurde kein Bausucht-Virus gestartet.");
                return;
            }
            String arg = message.substring(message.lastIndexOf("#stopvirus") +11);

            if(arg.equals("*") || arg.equalsIgnoreCase("all")) {
                Bukkit.getOnlinePlayers().forEach(util::stopVirus);
                player.sendMessage("Bausucht-Virus für alle Spieler gestoppt.");
                return;
            }

            Player target = Bukkit.getPlayer(arg);
            if(target != null) {
                if(util.isRunning(target)) {
                    util.stopVirus(target);
                    player.sendMessage("Bausucht-Virus für " + target.getName() + " gestoppt.");
                    
                } else
                    player.sendMessage("Es wurde kein Bausucht-Virus für " + target.getName() + " gestartet.");
            } else
                player.sendMessage("Spieler nicht gefunden.");
        } else
            player.sendMessage("§cDu darfst den Bausucht-Virus nicht stoppen :o");
    }
}
