package de.laparudi.bausuchtvirus.util;

import de.laparudi.bausuchtvirus.BausuchtVirus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class VirusUtil {
    
    private final Map<UUID, Boolean> virusStatus = new HashMap<>();
    private final Random random = new Random();
    
    public boolean isRunning(Player player) {
        if(!virusStatus.containsKey(player.getUniqueId())) {
            return false;
        }
        return virusStatus.get(player.getUniqueId());
    }
    
    private int task;

    public void setRunning(Player player, boolean running) {
        virusStatus.put(player.getUniqueId(), running);
    }
    
    public void spawn(Location location) {
        location.getWorld().spawnFallingBlock(location.subtract(1,0,0), Material.WOOL, (byte) 0);
        location.getWorld().spawnFallingBlock(location.add(1,0,0), Material.WOOL, (byte) 0);
        location.getWorld().spawnFallingBlock(location.add(0,1,0), Material.WOOL, (byte) 0);
        location.getWorld().spawnFallingBlock(location.add(0,1,0), Material.WOOL, (byte) 6);
        location.getWorld().spawnFallingBlock(location.add(1,0,0).subtract(0,2,0), Material.WOOL, (byte) 0);
    }
    
    public void startVirus(Player player) {
        if(isRunning(player)) {
            return;
        }
        
        setRunning(player, true);
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(BausuchtVirus.getPlugin(), () -> {
            if(isRunning(player)) {
                Location loc = player.getLocation();
                int randomX = random.nextInt(( (loc.getBlockX() + 25) - (loc.getBlockX() - 25) ) + 1 ) + loc.getBlockX() - 25;
                int randomZ = random.nextInt(( (loc.getBlockZ() + 25) - (loc.getBlockZ() - 25) ) + 1 ) + loc.getBlockZ() - 25;

                Location spawnLocation = new Location(player.getWorld(), randomX, 250, randomZ);
                spawn(spawnLocation);
            }
        }, 0, 20);
    }
    
    public void stopVirus(Player player) {
        setRunning(player, false);
        Bukkit.getScheduler().cancelTask(task);
    }
}
