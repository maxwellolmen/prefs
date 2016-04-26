package com.maxwellolmen.preferences;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.maxwellolmen.preferences.commands.Friends;
import com.maxwellolmen.preferences.commands.Message;
import com.maxwellolmen.preferences.commands.Prefs;
import com.maxwellolmen.preferences.listeners.InventoryClick;
import com.maxwellolmen.preferences.listeners.PlayerChat;
import com.maxwellolmen.preferences.listeners.PlayerJoin;
import com.maxwellolmen.preferences.listeners.PlayerQuit;

public class Main extends JavaPlugin {
    public void onEnable() {
        saveDefaultConfig();
        
        getCommand("prefs").setExecutor(new Prefs());
        getCommand("msg").setExecutor(new Message());
        getCommand("friends").setExecutor(new Friends());
        
        PluginManager pm = getServer().getPluginManager();
        
        pm.registerEvents(new PlayerJoin(), this);
        pm.registerEvents(new InventoryClick(), this);
        pm.registerEvents(new PlayerChat(), this);
        pm.registerEvents(new PlayerQuit(), this);
    }
    
    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            getConfig().set("prefs." + p.getUniqueId().toString() + ".lastjoin", System.currentTimeMillis());
        }
    }
    
    public static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("Prefs");
    }
    
    public static void togglePref(Player p, String pref) {
        getPlugin().getConfig().set("prefs." + p.getUniqueId().toString() + "." + pref, !getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + "." + pref));
        getPlugin().saveConfig();
    }
    
    public static boolean getPref(Player p, String pref) {
        return getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + "." + pref);
    }
    
    public static void sortStringBubble(ArrayList<String> x) {
        int j;
        boolean flag = true;
        String temp;

        while (flag) {
            flag = false;
            for (j = 0; j < x.size() - 1; j++) {
                if (x.get(j).compareToIgnoreCase(x.get(j + 1)) > 0){
                    temp = x.get(j);
                    x.set(j, x.get(j + 1));
                    x.set(j + 1, temp);
                    flag = true;
                } 
            } 
        } 
    }
    
    public static ItemStack createItem(ItemStack itemStack, String name, List<String> lore){
        ItemMeta im = itemStack.getItemMeta();
        
        if (name != null) {
            im.setDisplayName(name);
        }
        
        List<String> l;
        
        if (im.hasLore()) {
            l = im.getLore();
        } else {
            l = new ArrayList<String>();
        }
        
        if (lore != null) {
            for (String lo : lore) {
                l.add(lo);
            }
            
            im.setLore(l);
        }
        
        itemStack.setItemMeta(im);
        return itemStack;
    }
}
