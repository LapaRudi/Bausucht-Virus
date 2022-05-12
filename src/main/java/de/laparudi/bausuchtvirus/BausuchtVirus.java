package de.laparudi.bausuchtvirus;

import de.laparudi.bausuchtvirus.listeners.FallingBlockDropListener;
import de.laparudi.bausuchtvirus.listeners.QuitListener;
import de.laparudi.bausuchtvirus.listeners.ChatListener;
import de.laparudi.bausuchtvirus.util.VirusUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BausuchtVirus extends JavaPlugin {
    
    private static BausuchtVirus plugin;
    private boolean legacy;
    private VirusUtil util;
    
    @Override
    public void onEnable() {
        plugin = this;
        util = new VirusUtil();
        
        this.loadVersion();
        this.loadListeners();
        Bukkit.getLogger().info("Bausucht-Virus geladen.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Bausucht-Virus deaktiviert.");
    }

    private void loadListeners() {
        final PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new QuitListener(), this);
        if (!legacy) manager.registerEvents(new FallingBlockDropListener(), this);
    }
    
    private void loadVersion() {
        final int version = Integer.parseInt(Bukkit.getBukkitVersion().split("\\.")[1]);
        legacy = version < 13;
    }
    
    public VirusUtil getUtil() {
        return util;
    }

    public boolean isLegacy() {
        return legacy;
    }
    
    public static BausuchtVirus getPlugin() {
        return plugin;
    }
}
