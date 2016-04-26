package com.maxwellolmen.preferences.commands;

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

import com.maxwellolmen.preferences.Main;

public class Prefs implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "That command is for players only.");
            return false;
        }
        
        Player p = (Player) sender;
        
        openGUI(p);
        
        return false;
    }
    
    @SuppressWarnings("deprecation")
    public static void openGUI(Player p) {
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.RED + "My Preferences");
        
        ItemStack stacker = new ItemStack(Material.valueOf(Main.getPlugin().getConfig().getString("gui.stacker")));
        ItemStack visibility = new ItemStack(Material.valueOf(Main.getPlugin().getConfig().getString("gui.visibility")));
        ItemStack chat = new ItemStack(Material.valueOf(Main.getPlugin().getConfig().getString("gui.chat")));
        ItemStack messaging = new ItemStack(Material.valueOf(Main.getPlugin().getConfig().getString("gui.messaging")));
        ItemStack friends = new ItemStack(Material.valueOf(Main.getPlugin().getConfig().getString("gui.friends")));
        
        boolean s = Main.getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + ".stacker");
        boolean v = Main.getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + ".visibility");
        boolean c = Main.getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + ".chat");
        boolean m = Main.getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + ".messaging");
        boolean f = Main.getPlugin().getConfig().getBoolean("prefs." + p.getUniqueId().toString() + ".friends");
        
        ItemMeta smeta = stacker.getItemMeta();
        ItemMeta vmeta = stacker.getItemMeta();
        ItemMeta cmeta = stacker.getItemMeta();
        ItemMeta mmeta = stacker.getItemMeta();
        ItemMeta fmeta = stacker.getItemMeta();
        
        smeta.setDisplayName((s ? ChatColor.GREEN : ChatColor.RED) + "Player Stacker is " + (s ? "Enabled" : "Disabled"));
        vmeta.setDisplayName((v ? ChatColor.GREEN : ChatColor.RED) + "Player Visibility is " + (v ? "Enabled" : "Disabled"));
        cmeta.setDisplayName((c ? ChatColor.GREEN : ChatColor.RED) + "Player Chat is " + (c ? "Enabled" : "Disabled"));
        mmeta.setDisplayName((m ? ChatColor.GREEN : ChatColor.RED) + "Private Messaging is " + (m ? "Enabled" : "Disabled"));
        fmeta.setDisplayName((f ? ChatColor.GREEN : ChatColor.RED) + "Friend Requests are " + (f ? "Enabled" : "Disabled"));
        
        stacker.setItemMeta(smeta);
        visibility.setItemMeta(vmeta);
        chat.setItemMeta(cmeta);
        messaging.setItemMeta(mmeta);
        friends.setItemMeta(fmeta);
        
        ItemStack s1 = new ItemStack(Material.INK_SACK, 1, (short) 1, (byte) (s ? 10 : 8));
        ItemStack v1 = new ItemStack(Material.INK_SACK, 1, (short) 1, (byte) (v ? 10 : 8));
        ItemStack c1 = new ItemStack(Material.INK_SACK, 1, (short) 1, (byte) (c ? 10 : 8));
        ItemStack m1 = new ItemStack(Material.INK_SACK, 1, (short) 1, (byte) (m ? 10 : 8));
        ItemStack f1 = new ItemStack(Material.INK_SACK, 1, (short) 1, (byte) (f ? 10 : 8));
        
        ItemMeta sm = s1.getItemMeta();
        ItemMeta vm = v1.getItemMeta();
        ItemMeta cm = c1.getItemMeta();
        ItemMeta mm = m1.getItemMeta();
        ItemMeta fm = f1.getItemMeta();
        
        sm.setDisplayName(s ? ChatColor.RED + "Click to Disable" : ChatColor.GREEN + "Click to Enable");
        vm.setDisplayName(v ? ChatColor.RED + "Click to Disable" : ChatColor.GREEN + "Click to Enable");
        cm.setDisplayName(c ? ChatColor.RED + "Click to Disable" : ChatColor.GREEN + "Click to Enable");
        mm.setDisplayName(m ? ChatColor.RED + "Click to Disable" : ChatColor.GREEN + "Click to Enable");
        fm.setDisplayName(f ? ChatColor.RED + "Click to Disable" : ChatColor.GREEN + "Click to Enable");
        
        s1.setItemMeta(sm);
        v1.setItemMeta(vm);
        c1.setItemMeta(cm);
        m1.setItemMeta(mm);
        f1.setItemMeta(fm);
        
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(0), stacker);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(1), visibility);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(2), chat);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(3), messaging);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(4), friends);
        
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(0) + 9, s1);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(1) + 9, v1);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(2) + 9, c1);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(3) + 9, m1);
        inv.setItem(Main.getPlugin().getConfig().getIntegerList("gui.slots").get(4) + 9, f1);
        
        p.openInventory(inv);
    }
}
