package net.splodgebox.buycraftconfirm;

import com.google.common.collect.ImmutableMap;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.splodgebox.buycraftconfirm.utils.Chat;
import net.splodgebox.buycraftconfirm.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;

@RequiredArgsConstructor
public class Package {

    @Getter private final String name;
    @Getter private final Material material;
    @Getter private final String display;
    @Getter private final List<String> commands;
    @Getter private final List<String> message;

    public ItemStack create() {
        return new ItemStackBuilder(material)
                .setName(display)
                .setLore(Chat.replacePlaceholders(BuycraftConfirm.getInstance().getConfig().getStringList("Settings.lore"),
                        "{NAME}", name))
                .build();
    }
}
