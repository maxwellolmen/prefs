package com.maxwellolmen.preferences.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import com.maxwellolmen.preferences.Main;

public class PlayerQuit implements Listener {
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".lastjoin", System.currentTimeMillis());
        
        Main.getPlugin().saveConfig();
    }
}
