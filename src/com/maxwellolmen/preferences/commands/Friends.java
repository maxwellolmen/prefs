package com.maxwellolmen.preferences.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.maxwellolmen.preferences.Main;
import com.maxwellolmen.preferences.Time;
import com.maxwellolmen.preferences.anvilgui.AnvilGUI;
import com.maxwellolmen.preferences.anvilgui.AnvilGUI.AnvilClickEvent;
import com.maxwellolmen.preferences.anvilgui.AnvilGUI.AnvilSlot;

public class Friends implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "That command is for players only.");
            return true;
        }
        
        Player p = (Player) sender;
        
        openFriends(p, 1);
        
        return true;
    }
    
    @SuppressWarnings("deprecation")
    public static void openFriends(Player p, int page) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Friends");
        
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack rose = new ItemStack(Material.RED_ROSE);
        ItemStack tnt = new ItemStack(Material.TNT);
        ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
        
        SkullMeta headmeta = (SkullMeta) head.getItemMeta();
        ItemMeta rosemeta = rose.getItemMeta();
        ItemMeta tntmeta = tnt.getItemMeta();
        ItemMeta bookmeta = book.getItemMeta();
        
        headmeta.setOwner(p.getName());
        headmeta.setDisplayName("Friends");
        rosemeta.setDisplayName("Friend Requests");
        tntmeta.setDisplayName("Delete Friends");
        bookmeta.setDisplayName("Send Friend Request");
        
        head.setItemMeta(headmeta);
        rose.setItemMeta(rosemeta);
        tnt.setItemMeta(tntmeta);
        book.setItemMeta(bookmeta);
        
        inv.setItem(1, head);
        inv.setItem(3, rose);
        inv.setItem(5, tnt);
        inv.setItem(7, book);
        
        int slot = 18;
        
        ArrayList<String> fs = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + p.getUniqueId().toString() + ".friendlist");
        ArrayList<String> friendnames = new ArrayList<String>();
        
        ArrayList<String> friends = new ArrayList<String>();
        
        for (String friend : fs) {
            friendnames.add(Main.getPlugin().getConfig().getString("prefs." + friend + ".user"));
        }
        
        Main.sortStringBubble(friendnames);
        
        for (String friendname : friendnames) {
            String uuid = "";
            
            for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                if (key.equals("example-uuid")) {
                    continue;
                }
                
                if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(friendname)) {
                    uuid = key;
                    break;
                }
            }
            
            friends.add(uuid);
        }
        
        for (int i = (27 * (page - 1)); i < (27 * page); i++) {
            if (friends.size() < (i + 1)) {
                break;
            }
            
            String friend = friends.get(i);
            
            String username = Main.getPlugin().getConfig().getString("prefs." + friend + ".user");
            
            List<String> lore = new ArrayList<String>();
            
            lore.add(ChatColor.GRAY + "" + ChatColor.BOLD + (Bukkit.getPlayer(username) == null ? ChatColor.RED + "Offline" : ChatColor.GREEN + "Online"));
            lore.add(Time.getElapsed(Main.getPlugin().getConfig().getLong("prefs." + friend + ".lastjoin"), (Bukkit.getPlayer(username) == null ? "Last seen" : "Online since")));
            
            ItemStack f = new ItemStack(Material.SKULL_ITEM, 1, (short) 1, (byte) (Bukkit.getPlayer(username) == null ? 0 : 3));
            SkullMeta fmeta = (SkullMeta) f.getItemMeta();
            fmeta.setOwner(username);
            fmeta.setDisplayName(ChatColor.BOLD + username);
            fmeta.setLore(lore);
            f.setItemMeta(fmeta);
            
            inv.setItem(slot, f);
            
            slot++;
        }
        
        if (page > 1) {
            ItemStack back = new ItemStack(Material.SIGN);
            ItemMeta backmeta = back.getItemMeta();
            backmeta.setDisplayName("Previous Page");
            back.setItemMeta(backmeta);
            
            inv.setItem(45, back);
        }
        
        if (friends.size() > (27 * page)) {
            ItemStack next = new ItemStack(Material.SIGN);
            ItemMeta nextmeta = next.getItemMeta();
            nextmeta.setDisplayName("Next Page");
            next.setItemMeta(nextmeta);
            
            inv.setItem(53, next);
        }
        
        p.openInventory(inv);
    }
    
    public static void openRequests(Player p, int page) {
        ArrayList<String> incoming = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + p.getUniqueId().toString() + ".incoming-requests");
        ArrayList<String> outgoing = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + p.getUniqueId().toString() + ".outgoing-requests");
    
        ArrayList<String> in = new ArrayList<String>(), out = new ArrayList<String>();
        
        for (String i : incoming) {
            in.add(Main.getPlugin().getConfig().getString("prefs." + i + ".user"));
        }
        
        for (String o : outgoing) {
            out.add(Main.getPlugin().getConfig().getString("prefs." + o + ".user"));
        }
        
        for (String s : Main.getPlugin().getConfig().getStringList("open-requests")) {
            if (s.split(":")[0].equals(p.getUniqueId().toString())) {
                out.add(s);
            }
        }
        
        Main.sortStringBubble(in);
        Main.sortStringBubble(out);
        
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Friend Requests");
        
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        SkullMeta headmeta = (SkullMeta) head.getItemMeta();
        headmeta.setOwner(p.getName());
        head.setItemMeta(headmeta);
        
        inv.setItem(1, Main.createItem(head, "Friends", null));
        inv.setItem(3, Main.createItem(new ItemStack(Material.RED_ROSE), "Friend Requests", null));
        inv.setItem(5, Main.createItem(new ItemStack(Material.TNT), "Delete Friends", null));
        inv.setItem(7, Main.createItem(new ItemStack(Material.BOOK_AND_QUILL), "Send Friend Request", null));
        
        ArrayList<ItemStack> reqs = new ArrayList<ItemStack>();
        
        for (String i : in) {
            reqs.add(Main.createItem(new ItemStack(Material.PAPER), ChatColor.GRAY + "Friend request from " + ChatColor.WHITE + "" + ChatColor.BOLD + i, Arrays.asList(new String[] {"", ChatColor.GRAY + "Left click to accept friend request", ChatColor.GRAY + "Right click to decline friend request"})));
        }
        
        for (String o : out) {
            reqs.add(Main.createItem(new ItemStack(Material.ENDER_PEARL), ChatColor.GRAY + "Friend request to " + ChatColor.WHITE + "" + ChatColor.BOLD + o, Arrays.asList(new String[] {"", ChatColor.GRAY + "Click to cancel friend request"})));
        }
        
        int slot = 18;
        
        for (int i = (27 * (page - 1)); i < (27 * page); i++) {
            if (reqs.size() < (i + 1)) {
                break;
            }
            
            inv.setItem(slot, reqs.get(i));
            
            slot++;
        }
        
        if (page > 1) {
            ItemStack back = new ItemStack(Material.SIGN);
            ItemMeta backmeta = back.getItemMeta();
            backmeta.setDisplayName("Previous Page");
            back.setItemMeta(backmeta);
            
            inv.setItem(45, back);
        }
        
        if (reqs.size() > (27 * page)) {
            ItemStack next = new ItemStack(Material.SIGN);
            ItemMeta nextmeta = next.getItemMeta();
            nextmeta.setDisplayName("Next Page");
            next.setItemMeta(nextmeta);
            
            inv.setItem(53, next);
        }
        
        p.openInventory(inv);
    }
    
    @SuppressWarnings("deprecation")
    public static void deleteFriends(Player p, int page) {
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.RED + "Delete Friends");
        
        ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
        ItemStack rose = new ItemStack(Material.RED_ROSE);
        ItemStack tnt = new ItemStack(Material.TNT);
        ItemStack book = new ItemStack(Material.BOOK_AND_QUILL);
        
        SkullMeta headmeta = (SkullMeta) head.getItemMeta();
        ItemMeta rosemeta = rose.getItemMeta();
        ItemMeta tntmeta = tnt.getItemMeta();
        ItemMeta bookmeta = book.getItemMeta();
        
        headmeta.setOwner(p.getName());
        headmeta.setDisplayName("Friends");
        rosemeta.setDisplayName("Friend Requests");
        tntmeta.setDisplayName("Delete Friends");
        bookmeta.setDisplayName("Send Friend Request");
        
        head.setItemMeta(headmeta);
        rose.setItemMeta(rosemeta);
        tnt.setItemMeta(tntmeta);
        book.setItemMeta(bookmeta);
        
        inv.setItem(1, head);
        inv.setItem(3, rose);
        inv.setItem(5, tnt);
        inv.setItem(7, book);
        
        int slot = 18;
        
        ArrayList<String> fs = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + p.getUniqueId().toString() + ".friendlist");
        ArrayList<String> friendnames = new ArrayList<String>();
        
        ArrayList<String> friends = new ArrayList<String>();
        
        for (String friend : fs) {
            friendnames.add(Main.getPlugin().getConfig().getString("prefs." + friend + ".user"));
        }
        
        Main.sortStringBubble(friendnames);
        
        for (String friendname : friendnames) {
            String uuid = "";
            
            for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                if (key.equals("example-uuid")) {
                    continue;
                }
                
                if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(friendname)) {
                    uuid = key;
                    break;
                }
            }
            
            friends.add(uuid);
        }
        
        for (int i = (27 * (page - 1)); i < (27 * page); i++) {
            if (friends.size() < (i + 1)) {
                break;
            }
            
            String friend = friends.get(i);
            
            String username = Main.getPlugin().getConfig().getString("prefs." + friend + ".user");
            
            ItemStack f = new ItemStack(Material.SKULL_ITEM, 1, (short) 1, (byte) (Bukkit.getPlayer(username) == null ? 0 : 3));
            SkullMeta fmeta = (SkullMeta) f.getItemMeta();
            fmeta.setOwner(username);
            fmeta.setDisplayName(ChatColor.BOLD + username);
            fmeta.setLore(Arrays.asList(new String[] {ChatColor.GRAY + "Click me to remove this player from your friend list."}));
            f.setItemMeta(fmeta);
            
            inv.setItem(slot, f);
            
            slot++;
        }
        
        if (page > 1) {
            ItemStack back = new ItemStack(Material.SIGN);
            ItemMeta backmeta = back.getItemMeta();
            backmeta.setDisplayName("Previous Page");
            back.setItemMeta(backmeta);
            
            inv.setItem(45, back);
        }
        
        if (friends.size() > (27 * page)) {
            ItemStack next = new ItemStack(Material.SIGN);
            ItemMeta nextmeta = next.getItemMeta();
            nextmeta.setDisplayName("Next Page");
            next.setItemMeta(nextmeta);
            
            inv.setItem(53, next);
        }
        
        p.openInventory(inv);
    }
    
    public static void sendRequest(Player p, final String s) {
        AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {
            @Override
            public void onAnvilClick(AnvilClickEvent e) {
                if (e.getSlot() != AnvilSlot.OUTPUT) {
                    return;
                }
                
                if (e.getName().equals("") || e.getName().equals(s)) {
                    return;
                }
                
                e.setWillClose(true);
                e.setWillDestroy(true);
                
                String user = e.getName();
                
                String uuid = "";
                
                for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                    if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(user)) {
                        uuid = key;
                        break;
                    }
                }
                
                if (uuid.equals("")) {
                    List<String> open = Main.getPlugin().getConfig().getStringList("open-requests");
                    
                    if (open.contains(e.getPlayer().getUniqueId().toString() + ":" + user)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You already sent a request to that player.");
                        return;
                    }
                    
                    open.add(e.getPlayer().getUniqueId().toString() + ":" + user);
                    
                    Main.getPlugin().getConfig().set("open-requests", open);
                    
                    Main.getPlugin().saveConfig();
                } else {
                    List<String> incoming = Main.getPlugin().getConfig().getStringList("prefs." + uuid + ".incoming-requests");
                    
                    incoming.add(e.getPlayer().getUniqueId().toString());
                    
                    List<String> outgoing = Main.getPlugin().getConfig().getStringList("prefs." + e.getPlayer().getUniqueId().toString() + ".outgoing-requests");
                    
                    if (outgoing.contains(uuid)) {
                        e.getPlayer().sendMessage(ChatColor.RED + "You already sent a request to that player.");
                        return;
                    }
                    
                    outgoing.add(uuid);
                    
                    Main.getPlugin().getConfig().set("prefs." + uuid + ".incoming-requests", incoming);
                    Main.getPlugin().getConfig().set("prefs." + e.getPlayer().getUniqueId().toString() + ".outgoing-requests", outgoing);
                    
                    Main.getPlugin().saveConfig();
                }
                
                e.getPlayer().sendMessage(ChatColor.AQUA + "Request sent!");
            }
        });
        
        gui.setSlot(AnvilSlot.INPUT_LEFT, Main.createItem(new ItemStack(Material.NAME_TAG), s, null));
        gui.open();
    }
}
