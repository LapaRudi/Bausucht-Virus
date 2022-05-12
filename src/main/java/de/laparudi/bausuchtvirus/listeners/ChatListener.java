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
    public void onVirusHelp(final AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("#bausuchtvirus")) return;
        event.setCancelled(true);
        final Player player = event.getPlayer();

        if (!player.hasPermission("bausuchtvirus.use")) {
            player.sendMessage("Das darfst du nicht :>");
            return;
        }
        
        player.sendMessage("§bBausucht-Virus von §3LapaRudi §7| §bHilfe");
        player.sendMessage("");
        player.sendMessage("§7§oWenn du keinen Spieler angibst wird der Bausucht-Virus für dich gestartet.");
        player.sendMessage("§7§oWenn du '*' oder 'all' angibst wird der Bausucht-Virus für alle Spieler gestartet.");
        player.sendMessage("");
        player.sendMessage("§e#startvirus [Spieler] §8- §7Starte den Bausucht-Virus");
        player.sendMessage("§e#stopvirus [Spieler] §8- §7Stoppe den Bausucht-Virus");
        player.sendMessage("§e#delay <Verzögerung in Ticks> §8- §7Ändere den Spawn-Delay");
        player.sendMessage("");
        player.sendMessage("§bBausucht-Virus von §3LapaRudi §7| §bHilfe");
    }

    @EventHandler
    public void onVirusStart(final AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("#startvirus")) return;
        event.setCancelled(true);
        final Player player = event.getPlayer();

        if (!player.hasPermission("bausucht.virus")) {
            player.sendMessage("§cDu darfst den Bausucht-Virus nicht starten.");
            return;
        }

        final String message = event.getMessage();

        if (message.endsWith("#startvirus")) {
            if (util.isRunning(player)) {
                player.sendMessage("Bausucht-Virus wurde bereits gestartet.");
                return;
            }

            util.startVirus(player);
            player.sendMessage("Bausucht-Virus gestartet.");
            return;
        }

        final String arg = message.substring(message.lastIndexOf("#startvirus") + 12);

        if (arg.equals("*") || arg.equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().forEach(util::startVirus);
            player.sendMessage("Bausucht-Virus für alle Spieler gestartet.");
            return;
        }

        final Player target = Bukkit.getPlayer(arg);

        if (target == null) {
            player.sendMessage("Spieler nicht gefunden.");
            return;
        }

        if (util.isRunning(target)) {
            player.sendMessage("Bausucht-Virus für " + target + " wurde bereits gestartet.");
            return;
        }

        util.startVirus(target);
        player.sendMessage("Bausucht-Virus für " + target.getName() + " gestartet.");
    }

    @EventHandler
    public void onVirusStop(final AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("#stopvirus")) return;
        event.setCancelled(true);
        final Player player = event.getPlayer();

        if (!player.hasPermission("bausucht.virus")) {
            player.sendMessage("§cDu darfst den Bausucht-Virus nicht stoppen :o");
            return;
        }
        
        final String message = event.getMessage();

        if (message.endsWith("#stopvirus")) {
            if (!util.isRunning(player)) {
                player.sendMessage("Es wurde kein Bausucht-Virus gestartet.");
                return;
            }

            util.stopVirus(player);
            player.sendMessage("Bausucht-Virus gestoppt.");
            return;
        }

        final String arg = message.substring(message.lastIndexOf("#stopvirus") + 11);

        if (arg.equals("*") || arg.equalsIgnoreCase("all")) {
            Bukkit.getOnlinePlayers().forEach(util::stopVirus);
            player.sendMessage("Bausucht-Virus für alle Spieler gestoppt.");
            return;
        }

        final Player target = Bukkit.getPlayer(arg);
        
        if (target == null) {
            player.sendMessage("Spieler nicht gefunden.");
            return;
        }
        
        if (!util.isRunning(target)) {
            player.sendMessage("Es wurde kein Bausucht-Virus für " + target.getName() + " gestartet.");
            return;
        }

        util.stopVirus(target);
        player.sendMessage("Bausucht-Virus für " + target.getName() + " gestoppt.");
    }
    
    @EventHandler
    public void onDelayChange(final AsyncPlayerChatEvent event) {
        if (!event.getMessage().contains("#delay")) return;
        event.setCancelled(true);

        final Player player = event.getPlayer();
        if (!event.getMessage().contains(" ")) {
            player.sendMessage("§7§o(20 Ticks = 1 Sekunde)");
            player.sendMessage("§7Aktueller Delay: §c" + util.getDelay() + " Ticks");
            player.sendMessage("Benutze #delay <Verzögerung in Ticks>");
            return;
        }

        if (!player.hasPermission("bausuchtvirus.delay")) {
            player.sendMessage("§cDu darfst den Spawn-Delay nicht ändern.");
            return;
        }

        final String message = event.getMessage();
        final String arg = message.substring(message.lastIndexOf("#delay") + 7);
        int newDelay;

        try {
            newDelay = Integer.parseInt(arg);
        } catch (final NumberFormatException exception) {
            player.sendMessage("§cDu musst eine Ganzzahl angeben.");
            return;
        }

        if (newDelay < 1) {
            player.sendMessage("Du musst einen Wert von 1 oder mehr angeben.");
            return;
        }

        util.setDelay(newDelay);
        Bukkit.getOnlinePlayers().forEach(onlinePlayer -> {
            if (!util.isRunning(onlinePlayer)) return;
            util.stopVirus(onlinePlayer);
            util.startVirus(onlinePlayer);
        });
        
        player.sendMessage("Spawn-Delay wurde geändert zu §c" + newDelay + " Ticks§f.");
    }
}
