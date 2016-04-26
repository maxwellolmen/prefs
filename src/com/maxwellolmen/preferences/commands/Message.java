package com.maxwellolmen.preferences.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.maxwellolmen.preferences.Main;

public class Message implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "That command is for players only.");
            return true;
        }
        
        Player p = (Player) sender;
        
        if (!Main.getPref(p, "messaging")) {
            p.sendMessage(ChatColor.RED + "You disabled messaging. Enable it my going into /prefs");
            return true;
        }
        
        if (args.length < 2) {
            p.sendMessage(ChatColor.RED + "Usage: /" + label + " <player> <message>");
            return true;
        }
        
        if (Bukkit.getPlayer(args[0]) == null) {
            p.sendMessage(ChatColor.RED + "That player is not online.");
            return true;
        }
        
        Player t = Bukkit.getPlayer(args[0]);
        
        if (!Main.getPref(t, "messaging")) {
            p.sendMessage(ChatColor.RED + "That player has messaging disabled.");
            return true;
        }
        
        p.sendMessage(ChatColor.GOLD + "[" + ChatColor.RED + "me " + ChatColor.GOLD + "-> " + t.getDisplayName() + ChatColor.GOLD + "]" + ChatColor.RESET + combine(args, 1));
        t.sendMessage(ChatColor.GOLD + "[" + p.getDisplayName() + ChatColor.GOLD + " -> " + ChatColor.RED + "me" + ChatColor.GOLD + "]" + ChatColor.RESET + combine(args, 1));
        
        return true;
    }
    
    public static String combine(String[] args, int startingIndex) {
        String s = "";
        
        for (int i = startingIndex; i < args.length - startingIndex + 1; i++) {
            s+=" " + args[i];
        }
        
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
