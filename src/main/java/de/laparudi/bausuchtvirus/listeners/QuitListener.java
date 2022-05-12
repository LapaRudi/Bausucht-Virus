package de.laparudi.bausuchtvirus.listeners;

import de.laparudi.bausuchtvirus.BausuchtVirus;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {
    
    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        
        if (BausuchtVirus.getPlugin().getUtil().isRunning(player)) {
            BausuchtVirus.getPlugin().getUtil().stopVirus(player);
        }
    }
}
