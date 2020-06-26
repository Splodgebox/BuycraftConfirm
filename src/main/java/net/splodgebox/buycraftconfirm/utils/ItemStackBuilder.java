package net.splodgebox.buycraftconfirm.utils;

import com.google.common.collect.Lists;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemStackBuilder {
    private ItemStack itemStack;

    public ItemStackBuilder(Material material) {
        this.itemStack = new ItemStack(material);
    }

    public ItemStackBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStackBuilder setAmount(int amount) {
        this.itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder setName(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.setDisplayName(Chat.color(name));
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setColor(Color color){
        LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) this.itemStack.getItemMeta();
        try {
            leatherArmorMeta.setColor(color);
        }catch (NullPointerException exception){
            leatherArmorMeta.setColor(Color.RED);
        }
        this.itemStack.setItemMeta(leatherArmorMeta);
        return this;
    }

    public ItemStackBuilder setEnchantments(List<String> list){
        for (String en : list) {
            String[] breakdown = en.split(":");
            String enchantment = breakdown[0];
            int lvl = Integer.parseInt(breakdown[1]);
            addEnchant(Enchantment.getByName(enchantment), lvl);
        }
        return this;
    }

    public ItemStackBuilder setLore(List<String> lore) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> temp = Lists.newArrayList();
        lore.forEach((s) -> {
            temp.add(Chat.color(s));
        });
        meta.setLore(temp);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addLore(String name) {
        ItemMeta meta = this.itemStack.getItemMeta();
        List<String> lore = meta.getLore();
        if (lore == null) {
            lore = Lists.newArrayList();
        }

        ((List) lore).add(Chat.color(name));
        meta.setLore((List) lore);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder addItemFlags(ItemFlag... flags) {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(flags);
        this.itemStack.setItemMeta(meta);
        return this;
    }

    public ItemStackBuilder setData(int data) {
        this.itemStack.setDurability((short) data);
        return this;
    }

    public ItemStackBuilder addEnchant(Enchantment enchantment, int level) {
        this.itemStack.addUnsafeEnchantment(enchantment, level);
        return this;
    }

    public ItemStackBuilder setEnchant(Map<Enchantment, Integer> enchants) {
        this.itemStack.addUnsafeEnchantments(enchants);
        return this;
    }

    public ItemStackBuilder setType(Material material) {
        this.itemStack.setType(material);
        return this;
    }


    public ItemStackBuilder withGlow() {
        ItemMeta meta = this.itemStack.getItemMeta();
        meta.addItemFlags(new ItemFlag[]{ItemFlag.HIDE_ENCHANTS});
        this.itemStack.setItemMeta(meta);
        this.itemStack.addUnsafeEnchantment(Enchantment.DURABILITY, 1);
        return this;
    }

    public ItemStack build() {
        return this.itemStack;
    }

}