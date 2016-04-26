package com.maxwellolmen.preferences.listeners;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import com.maxwellolmen.preferences.Main;

public class PlayerChat implements Listener{
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        ArrayList<Player> ps = new ArrayList<Player>(e.getRecipients());
        
        for (Player p : ps) {
            if (Main.getPref(p, "chat")) {
                continue;
            }
            
            if (p == e.getPlayer()) {
                e.setCancelled(true);
            } else {
                e.getRecipients().remove(p);
            }
        }
    }
}
