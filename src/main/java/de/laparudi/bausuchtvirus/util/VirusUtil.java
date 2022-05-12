package de.laparudi.bausuchtvirus.util;

import de.laparudi.bausuchtvirus.BausuchtVirus;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.*;

public class VirusUtil {
    
    private final Map<UUID, Boolean> virusStatus = new HashMap<>();
    private final Random random = new Random();
    private int delay = 20;
    private int task;
    
    public boolean isRunning(final Player player) {
        if (!virusStatus.containsKey(player.getUniqueId())) {
            return false;
        }
        
        return virusStatus.get(player.getUniqueId());
    }
    
    public void setRunning(final Player player, final boolean running) {
        virusStatus.put(player.getUniqueId(), running);
    }

    @Deprecated
    public void spawnLegacy(final Location location) {
        if (location.getWorld() == null) return;
        location.getWorld().spawnFallingBlock(location.subtract(1,0,0), Objects.requireNonNull(Material.getMaterial("WOOL")), (byte) 0);
        location.getWorld().spawnFallingBlock(location.add(1,0,0), Objects.requireNonNull(Material.getMaterial("WOOL")), (byte) 0);
        location.getWorld().spawnFallingBlock(location.add(0,1,0), Objects.requireNonNull(Material.getMaterial("WOOL")), (byte) 0);
        location.getWorld().spawnFallingBlock(location.add(0,1,0), Objects.requireNonNull(Material.getMaterial("WOOL")), (byte) 6);
        location.getWorld().spawnFallingBlock(location.add(1,0,0).subtract(0,2,0), Objects.requireNonNull(Material.getMaterial("WOOL")), (byte) 0);
    }
    
    public void spawn(final Location location) {
        if (location.getWorld() == null) return;
        location.getWorld().spawnFallingBlock(location.subtract(1, 0, 0), Material.WHITE_WOOL.createBlockData());
        location.getWorld().spawnFallingBlock(location.add(1, 0, 0), Material.WHITE_WOOL.createBlockData());
        location.getWorld().spawnFallingBlock(location.add(1, 0, 0), Material.WHITE_WOOL.createBlockData());
        location.getWorld().spawnFallingBlock(location.add(-1,1,0),Material.WHITE_WOOL.createBlockData());
        location.getWorld().spawnFallingBlock(location.add(0,1,0), Material.PINK_WOOL.createBlockData());
    }
    
    public void startVirus(final Player player) {
        if (this.isRunning(player)) {
            return;
        }

        this.setRunning(player, true);
        task = Bukkit.getScheduler().scheduleSyncRepeatingTask(BausuchtVirus.getPlugin(), () -> {
            if (!this.isRunning(player)) return;
            
            final Location loc = player.getLocation();
            final int randomX = random.nextInt(((loc.getBlockX() + 25) - (loc.getBlockX() - 25)) + 1) + loc.getBlockX() - 25;
            final int randomZ = random.nextInt(((loc.getBlockZ() + 25) - (loc.getBlockZ() - 25)) + 1) + loc.getBlockZ() - 25;
            final Location spawnLocation = new Location(player.getWorld(), randomX, 250, randomZ);
            
            if (BausuchtVirus.getPlugin().isLegacy()) {
                this.spawnLegacy(spawnLocation);
            } else {
                this.spawn(spawnLocation);
            }
        }, 0, this.delay);
    }
    
    public void stopVirus(final Player player) {
        this.setRunning(player, false);
        Bukkit.getScheduler().cancelTask(task);
    }

    public int getDelay() {
        return this.delay;
    }

    public void setDelay(final int delay) {
        this.delay = delay;
    }
}
