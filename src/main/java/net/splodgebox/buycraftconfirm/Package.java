package net.splodgebox.buycraftconfirm;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.splodgebox.buycraftconfirm.utils.ItemStackBuilder;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
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
                .addLore("&7Click item to claim this Buycraft Package")
                .addLore("")
                .addLore("&f&lNOTE: &7If you do &nNOT&7 wish to accept")
                .addLore("&7this package, please right-click it")
                .addLore("")
                .addLore("&c&lWARNING: &7By accepting this package")
                .addLore("&7you claim full responsibility for the")
                .addLore("&7payment and you will be banned if it is")
                .addLore("&7flagged as a fraudulent or if the payment")
                .addLore("&7is charged-back by PayPal")
                .addLore("")
                .addLore("&e&lPACKAGE ID: &7" + name)
                .build();
    }
}
