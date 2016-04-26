package com.maxwellolmen.preferences.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;

import com.maxwellolmen.preferences.Main;
import com.maxwellolmen.preferences.commands.Friends;
import com.maxwellolmen.preferences.commands.Prefs;

public class InventoryClick implements Listener {
    @EventHandler
    public void onInventoryClickPrefs(InventoryClickEvent e) {
        if (!ChatColor.stripColor(e.getInventory().getTitle()).equals("My Preferences")) {
            return;
        }
        
        e.setCancelled(true);
        
        Player p = (Player) e.getWhoClicked();
        
        if (e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(0) || e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(0) + 9) {
            Main.togglePref(p, "stacker");
            Bukkit.dispatchCommand(p, Main.getPlugin().getConfig().getString("stacker." + (Main.getPref(p, "stacker") ? "enable" : "disable") + "-command"));
            
            Prefs.openGUI(p);
        } else if (e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(1) || e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(1) + 9) {
            Main.togglePref(p, "visibility");
            
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (Main.getPref(p, "visibility")) {
                    p.showPlayer(pl);
                } else {
                    p.hidePlayer(pl);
                }
            }
            
            Prefs.openGUI(p);
        } else if (e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(2) || e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(2) + 9) {
            Main.togglePref(p, "chat");
            
            Prefs.openGUI(p);
        } else if (e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(3) || e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(3) + 9) {
            Main.togglePref(p, "messaging");
            
            Prefs.openGUI(p);
        } else if (e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(4) || e.getSlot() == Main.getPlugin().getConfig().getIntegerList("gui.slots").get(4) + 9) {
            Main.togglePref(p, "friends");
            
            Prefs.openGUI(p);
        }
    }
    
    @EventHandler
    public void onInventoryClickFriends(InventoryClickEvent e) {
        if (!ChatColor.stripColor(e.getInventory().getTitle()).equals("Friends")) {
            return;
        }
        
        e.setCancelled(true);
        
        if (e.getSlot() == 1) {
            Friends.openFriends((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 3) {
            Friends.openRequests((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 5) {
            Friends.deleteFriends((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 7) {
            Friends.sendRequest((Player) e.getWhoClicked(), "Type the username.");
        } else if (e.getSlot() == 45 && e.getInventory().getItem(45) != null) {
            String name = ChatColor.stripColor(e.getInventory().getItem(18).getItemMeta().getDisplayName());
            
            ArrayList<String> fs = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist");
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
            
            int page = (int) Math.ceil((friendnames.indexOf(name) + 1)/27.0) - 1;
            
            Friends.openFriends((Player) e.getWhoClicked(), page);
        } else if (e.getSlot() == 53 && e.getInventory().getItem(53) != null) {
            String name = ChatColor.stripColor(e.getInventory().getItem(18).getItemMeta().getDisplayName());
            
            ArrayList<String> fs = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist");
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
            
            int page = (int) Math.ceil((friendnames.indexOf(name) + 1)/27.0) + 1;
            
            Friends.openFriends((Player) e.getWhoClicked(), page);
        }
    }
    
    @EventHandler
    public void onInventoryClickRequests(InventoryClickEvent e) {
        if (!ChatColor.stripColor(e.getInventory().getTitle()).equals("Friend Requests")) {
            return;
        }
        
        e.setCancelled(true);
        
        if (e.getSlot() == 1) {
            Friends.openFriends((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 3) {
            Friends.openRequests((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 5) {
            Friends.deleteFriends((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 7) {
            Friends.sendRequest((Player) e.getWhoClicked(), "Type the username.");
        } else if (e.getSlot() == 45 && e.getInventory().getItem(45) != null) {
            ArrayList<String> incoming = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".incoming-requests");
            ArrayList<String> outgoing = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".outgoing-requests");
        
            ArrayList<String> in = new ArrayList<String>(), out = new ArrayList<String>();
            
            for (String i : incoming) {
                in.add(Main.getPlugin().getConfig().getString("prefs." + i + ".user"));
            }
            
            for (String o : outgoing) {
                out.add(Main.getPlugin().getConfig().getString("prefs." + o + ".user"));
            }
            
            for (String s : Main.getPlugin().getConfig().getStringList("open-requests")) {
                if (s.split(":")[0].equals(e.getWhoClicked().getUniqueId().toString())) {
                    out.add(s);
                }
            }
            
            Main.sortStringBubble(in);
            Main.sortStringBubble(out);
            
            ArrayList<ItemStack> reqs = new ArrayList<ItemStack>();
            
            for (String i : in) {
                reqs.add(Main.createItem(new ItemStack(Material.PAPER), ChatColor.GRAY + "Friend request from " + ChatColor.WHITE + "" + ChatColor.BOLD + i, Arrays.asList(new String[] {"", ChatColor.GRAY + "Click to cancel friend request"})));
            }
            
            for (String o : out) {
                reqs.add(Main.createItem(new ItemStack(Material.ENDER_PEARL), ChatColor.GRAY + "Friend request to " + ChatColor.WHITE + "" + ChatColor.BOLD + o, Arrays.asList(new String[] {"", ChatColor.GRAY + "Left click to accept friend request", ChatColor.GRAY + "Right click to decline friend request"})));
            }
            
            int page = (int) Math.ceil((reqs.indexOf(e.getInventory().getItem(18)) + 1)/27.0) - 1;
            
            Friends.openRequests((Player) e.getWhoClicked(), page);
        } else if (e.getSlot() == 53 && e.getInventory().getItem(53) != null) {
            ArrayList<String> incoming = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".incoming-requests");
            ArrayList<String> outgoing = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".outgoing-requests");
        
            ArrayList<String> in = new ArrayList<String>(), out = new ArrayList<String>();
            
            for (String i : incoming) {
                in.add(Main.getPlugin().getConfig().getString("prefs." + i + ".user"));
            }
            
            for (String o : outgoing) {
                out.add(Main.getPlugin().getConfig().getString("prefs." + o + ".user"));
            }
            
            for (String s : Main.getPlugin().getConfig().getStringList("open-requests")) {
                if (s.split(":")[0].equals(e.getWhoClicked().getUniqueId().toString())) {
                    out.add(s);
                }
            }
            
            Main.sortStringBubble(in);
            Main.sortStringBubble(out);
            
            ArrayList<ItemStack> reqs = new ArrayList<ItemStack>();
            
            for (String i : in) {
                reqs.add(Main.createItem(new ItemStack(Material.PAPER), ChatColor.GRAY + "Friend request from " + ChatColor.WHITE + "" + ChatColor.BOLD + i, Arrays.asList(new String[] {"", ChatColor.GRAY + "Click to cancel friend request"})));
            }
            
            for (String o : out) {
                reqs.add(Main.createItem(new ItemStack(Material.ENDER_PEARL), ChatColor.GRAY + "Friend request to " + ChatColor.WHITE + "" + ChatColor.BOLD + o, Arrays.asList(new String[] {"", ChatColor.GRAY + "Left click to accept friend request", ChatColor.GRAY + "Right click to decline friend request"})));
            }
            
            int page = (int) Math.ceil((reqs.indexOf(e.getInventory().getItem(18)) + 1)/27.0) + 1;
            
            Friends.openRequests((Player) e.getWhoClicked(), page);
        } else if (e.getCurrentItem() != null){
            if (e.getCurrentItem().getType() == Material.ENDER_PEARL) {
                String user = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("\\s+")[3];
                
                String uuid = "";
                
                for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                    if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(user)) {
                        uuid = key;
                        break;
                    }
                }
                
                List<String> outgoing = Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".outgoing-requests");
                
                outgoing.remove(uuid);
                
                List<String> incoming = Main.getPlugin().getConfig().getStringList("prefs." + uuid + ".incoming-requests");
                
                incoming.remove(e.getWhoClicked().getUniqueId().toString());
                
                Main.getPlugin().getConfig().set("prefs." + e.getWhoClicked().getUniqueId().toString() + ".outgoing-requests", outgoing);
                Main.getPlugin().getConfig().set("prefs." + uuid + ".incoming-requests", incoming);
                
                Main.getPlugin().saveConfig();
                
                Friends.openRequests((Player) e.getWhoClicked(), 1);
            } else if (e.getCurrentItem().getType() == Material.PAPER) {
                if (e.isLeftClick()) {
                    String user = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("\\s+")[3];
                    
                    String uuid = "";
                    
                    for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                        if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(user)) {
                            uuid = key;
                            break;
                        }
                    }
                    
                    List<String> friends = Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist");
                    
                    friends.add(uuid);
                    
                    List<String> fs2 = Main.getPlugin().getConfig().getStringList("prefs." + uuid + ".friendlist");
                    
                    fs2.add(e.getWhoClicked().getUniqueId().toString());
                    
                    List<String> incoming = Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".incoming-requests");
                    
                    incoming.remove(uuid);
                    
                    List<String> outgoing = Main.getPlugin().getConfig().getStringList("prefs." + uuid + ".outgoing-requests");
                    
                    outgoing.remove(e.getWhoClicked().getUniqueId().toString());
                    
                    Main.getPlugin().getConfig().set("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist", friends);
                    Main.getPlugin().getConfig().set("prefs." + uuid + ".friendlist", fs2);
                    
                    Main.getPlugin().getConfig().set("prefs." + e.getWhoClicked().getUniqueId().toString() + ".incoming-requests", incoming);
                    Main.getPlugin().getConfig().set("prefs." + uuid + ".outgoing-requests", incoming);
                    
                    Main.getPlugin().saveConfig();
                    
                    Friends.openRequests((Player) e.getWhoClicked(), 1);
                } else if (e.isRightClick()){
                    String user = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).split("\\s+")[3];
                    
                    String uuid = "";
                    
                    for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                        if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(user)) {
                            uuid = key;
                            break;
                        }
                    }
                    
                    List<String> incoming = Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".incoming-requests");
                    
                    incoming.remove(uuid);
                    
                    List<String> outgoing = Main.getPlugin().getConfig().getStringList("prefs." + uuid + ".outgoing-requests");
                    
                    outgoing.remove(e.getWhoClicked().getUniqueId().toString());
                    
                    Main.getPlugin().getConfig().set("prefs." + e.getWhoClicked().getUniqueId().toString() + ".incoming-requests", incoming);
                    Main.getPlugin().getConfig().set("prefs." + uuid + ".outgoing-requests", outgoing);
                    
                    Main.getPlugin().saveConfig();
                    
                    Friends.openRequests((Player) e.getWhoClicked(), 1);
                }
            }
        }
    }
    
    @EventHandler
    public void onInventoryClickDeleteFriends(InventoryClickEvent e) {
        if (!ChatColor.stripColor(e.getInventory().getTitle()).equals("Delete Friends")) {
            return;
        }
        
        e.setCancelled(true);
        
        if (e.getCurrentItem() == null) {
            return;
        }
        
        if (e.getSlot() == 1) {
            Friends.openFriends((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 3) {
            Friends.openRequests((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 5) {
            Friends.deleteFriends((Player) e.getWhoClicked(), 1);
        } else if (e.getSlot() == 7) {
            Friends.sendRequest((Player) e.getWhoClicked(), "Type the username.");
        } else if (e.getSlot() == 45 && e.getCurrentItem() != null) {
            String name = ChatColor.stripColor(e.getInventory().getItem(18).getItemMeta().getDisplayName());
            
            ArrayList<String> fs = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist");
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
            
            int page = (int) Math.ceil((friendnames.indexOf(name) + 1)/27.0) - 1;
            
            Friends.deleteFriends((Player) e.getWhoClicked(), page);
        } else if (e.getSlot() == 53 && e.getCurrentItem() != null) {
            String name = ChatColor.stripColor(e.getInventory().getItem(18).getItemMeta().getDisplayName());
            
            ArrayList<String> fs = (ArrayList<String>) Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist");
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
            
            int page = (int) Math.ceil((friendnames.indexOf(name) + 1)/27.0) + 1;
            
            Friends.deleteFriends((Player) e.getWhoClicked(), page);
        } else if (e.getCurrentItem() != null) {
            String user = ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName());
            
            String uuid = "";
            
            for (String key : Main.getPlugin().getConfig().getConfigurationSection("prefs").getKeys(false)) {
                if (Main.getPlugin().getConfig().getString("prefs." + key + ".user").equals(user)) {
                    uuid = key;
                    break;
                }
            }
            
            List<String> fs1 = Main.getPlugin().getConfig().getStringList("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist");
            List<String> fs2 = Main.getPlugin().getConfig().getStringList("prefs." + uuid + ".friendlist");
            
            fs1.remove(uuid);
            fs2.remove(e.getWhoClicked().getUniqueId().toString());
            
            Main.getPlugin().getConfig().set("prefs." + e.getWhoClicked().getUniqueId().toString() + ".friendlist", fs1);
            Main.getPlugin().getConfig().set("prefs." + uuid + ".friendlist", fs2);
            
            Main.getPlugin().saveConfig();
            
            Friends.deleteFriends((Player) e.getWhoClicked(), 1);
        }
    }
}
