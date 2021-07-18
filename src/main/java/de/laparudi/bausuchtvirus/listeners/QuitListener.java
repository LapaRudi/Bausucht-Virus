package de.laparudi.bausuchtvirus.listeners;

import de.laparudi.bausuchtvirus.BausuchtVirus;
import de.laparudi.bausuchtvirus.util.VirusUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final VirusUtil util = BausuchtVirus.getPlugin().getUtil();
    
    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(util.isRunning(player)) {
            util.stopVirus(player);
        }
    }
}
