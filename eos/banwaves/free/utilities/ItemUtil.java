package eos.banwaves.free.utilities;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class ItemUtil {

    public static ItemStack affect(ItemStack item, String name) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.translateColor(name));
        item.setItemMeta(itemMeta);

        return item;
    }

    public static ItemStack applyLore(ItemStack stack, String name) {
        name = ChatColor.translateAlternateColorCodes('&', name).replace("ame:", "");

        ItemMeta meta = stack.getItemMeta();
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setDisplayName(name);
        stack.setItemMeta(meta);
        return stack;
    }

    public static ItemStack affect(ItemStack item, String name, String lore) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        itemMeta.setDisplayName(ColorUtil.translateColor(name));
        if ( lore.equals("") || lore.equals(" ") ) {
            itemMeta.setLore(null);
        } else {
            itemMeta.setLore(Arrays.asList(ColorUtil.translateColor(lore).replace("lore:", "").split("\\|")));
        }

        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack affect(ItemStack item, String name, String lore, Enchantment enchantment, int level, boolean hideenchant) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.translateColor(name));
        itemMeta.addEnchant(enchantment, level, true);
        if (hideenchant) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        if ( lore.equals("") || lore.equals(" ") ) {
            itemMeta.setLore(null);
        } else {
            itemMeta.setLore(Arrays.asList(ColorUtil.translateColor(lore).replace("lore:", "").split("\\|")));
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack affect(ItemStack item, String name, Enchantment enchantment, int level, boolean hideenchant) {
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(ColorUtil.translateColor(name));
        itemMeta.addEnchant(enchantment, level, true);
        if (hideenchant) {
            itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(itemMeta);
        return item;
    }

    public static ItemStack getHead(Player pl) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(pl.getName());
        skull.setItemMeta(meta);
        return skull;
    }
    public static ItemStack getHead(String name) {
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) skull.getItemMeta();
        meta.setOwner(name);
        skull.setItemMeta(meta);
        return skull;
    }

}
