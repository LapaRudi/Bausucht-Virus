package de.laparudi.bausuchtvirus;

import de.laparudi.bausuchtvirus.listeners.QuitListener;
import de.laparudi.bausuchtvirus.listeners.ChatListener;
import de.laparudi.bausuchtvirus.util.VirusUtil;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class BausuchtVirus extends JavaPlugin {
    
    private static BausuchtVirus plugin;
    private final VirusUtil util = new VirusUtil();
    
    @Override
    public void onEnable() {
        plugin = this;
        this.loadListeners();
        Bukkit.getLogger().info("Bausucht-Virus geladen.");
    }

    @Override
    public void onDisable() {
        Bukkit.getLogger().info("Bausucht-Virus deaktiviert.");
    }

    private void loadListeners() {
        PluginManager manager = Bukkit.getPluginManager();
        manager.registerEvents(new ChatListener(), this);
        manager.registerEvents(new QuitListener(), this);
    }
    
    public VirusUtil getUtil() {
        return util;
    }

    public static BausuchtVirus getPlugin() {
        return plugin;
    }
    
}
