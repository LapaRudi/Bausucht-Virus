package de.laparudi.bausuchtvirus.listeners;

import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;

public class FallingBlockDropListener implements Listener {

    @EventHandler
    public void onDrop(final EntityDropItemEvent event) {
        final Item item = event.getItemDrop();
        if (!item.getItemStack().getType().name().contains("WOOL")) return;
        event.setCancelled(true);
        item.getLocation().subtract(0,1,0).getBlock().setType(item.getItemStack().getType());
    }
}
