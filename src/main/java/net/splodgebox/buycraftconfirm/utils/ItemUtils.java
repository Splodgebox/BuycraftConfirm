package net.splodgebox.buycraftconfirm.utils;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ItemUtils {
    public ItemStack replaceData(ItemStack itemStack, Map<String, String> replaceMap) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        boolean hasName = itemMeta.hasDisplayName();
        boolean hasLore = itemMeta.hasLore();
        List<String> newLore = new ArrayList<>();
        String name = itemStack.getType().name();
        if (hasName) name = itemMeta.getDisplayName();
        if (replaceMap != null && !replaceMap.isEmpty()) {
            if (hasName) {
                if (name != null) {
                    for (String s : replaceMap.keySet()) {
                        if (name.contains(s))
                            name = name.replace(s, replaceMap.get(s));
                    }
                }
                itemMeta.setDisplayName(Chat.color(name));
            }
            if (hasLore) {
                for (String s : itemMeta.getLore()) {
                    for (String z : replaceMap.keySet()) {
                        if (s.contains(z)) s = s.replace(z, replaceMap.get(z));
                    }
                    newLore.add(Chat.color(s));
                }
                itemMeta.setLore(newLore);
            }
        }

        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }
}
