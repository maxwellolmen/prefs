package com.maxwellolmen.preferences;

import org.bukkit.ChatColor;

public class Time {
    public static int getSeconds(long ms) {
        return (int) (ms/1000);
    }
    
    public static double getMinutes(long ms) {
        return Math.round(ms/6000.0)/10.0;
    }
    
    public static double getHours(long ms) {
        return Math.round(ms/360000.0)/10.0;
    }
    
    public static double getDays(long ms) {
        return Math.round(ms/8640000.0)/10.0;
    }
    
    public static double getWeeks(long ms) {
        return Math.round(ms/60480000.0)/10.0;
    }
    
    public static double getMonths(long ms) {
        return Math.round(ms/259200000.0)/10.0;
    }
    
    public static double getYears(long ms) {
        return Math.round(ms/31536000000.0)/10.0;
    }
    
    public static String getElapsed(long ms, String prefix) {
        long elapsed = System.currentTimeMillis() - ms;
        
        if (elapsed >= 0 && elapsed < 60000) {
            return ChatColor.GRAY + prefix + " " + getSeconds(elapsed) + " seconds ago";
        } else if (elapsed >= 60000 && elapsed < 3600000) {
            return ChatColor.GRAY + prefix + " " + getMinutes(elapsed) + " minutes ago";
        } else if (elapsed >= 3600000 && elapsed < 86400000) {
            return ChatColor.GRAY + prefix + " " + getHours(elapsed) + " hours ago";
        } else if (elapsed >= 86400000 && elapsed < 604800000) {
            return ChatColor.GRAY + prefix + " " + getDays(elapsed) + " days ago";
        } else if (elapsed >= 604800000 && elapsed < 2592000000.0) {
            return ChatColor.GRAY + prefix + " " + getWeeks(elapsed) + " weeks ago";
        } else if (elapsed >= 2592000000.0 && elapsed < 31536000000.0) {
            return ChatColor.GRAY + prefix + " " + getMonths(elapsed) + " months ago";
        } else {
            return ChatColor.GRAY + prefix + " " + getYears(elapsed) + " years ago";
        }
    }
}
