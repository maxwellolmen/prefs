package com.maxwellolmen.preferences.listeners;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import com.maxwellolmen.preferences.Main;

public class PlayerJoin implements Listener {
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!Main.getPlugin().getConfig().contains("prefs." + e.getPlayer().getUniqueId().toString())) {
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".stacker", false);
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".visibility", true);
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".chat", true);
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".messaging", true);
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".friends", true);
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".friendlist", new ArrayList<String>());
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".incoming-requests", new ArrayList<String>());
            Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".outgoing-requests", new ArrayList<String>());
        }
        
        Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".lastjoin", System.currentTimeMillis());
        Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".user", e.getPlayer().getName());
        
        Main.getPlugin().saveConfig();
        
        if (!Main.getPref(e.getPlayer(), "visibility")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                e.getPlayer().hidePlayer(p);
            }
        }
    }
}
