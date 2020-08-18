package net.splodgebox.buycraftconfirm.controllers;

import com.google.common.collect.ImmutableMap;
import lombok.RequiredArgsConstructor;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.Package;
import net.splodgebox.buycraftconfirm.PackageIcon;
import net.splodgebox.buycraftconfirm.utils.Chat;
import net.splodgebox.buycraftconfirm.utils.Message;
import net.splodgebox.buycraftconfirm.utils.gui.Gui;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@RequiredArgsConstructor
public class PlayerPackageController {

    private final Player player;

    public void openConfirmMenu() {
        List<String> packages = BuycraftConfirm.getInstance().getDataController().getPlayerPackages().get(player.getUniqueId());
        List<PackageIcon> packageIcons = BuycraftConfirm.getInstance().getDataController().getPlayerPackageData().get(player.getUniqueId());
        int size = 1 + (packages.size() / 9);
        Gui inventory = new Gui("Confirm Purchases", size);

        for (String value : packages) {
            Package sPackage = BuycraftConfirm.getInstance().getPackageController().getPackages().get(value);
            PackageIcon packageIcon = getPackage(packageIcons, value);
            ItemStack packageDisplay = sPackage.create();

            if (packageIcon != null) {
                ImmutableMap<String, String> placeholders = ImmutableMap
                        .of("{TRANSACTION-ID}", packageIcon.getTransaction(),
                                "{PRICE}", packageIcon.getPrice(),
                                "{DATE}", packageIcon.getDate());
                packageDisplay = BuycraftConfirm.getInstance().getItemUtils().replaceData(packageDisplay, placeholders);
            }

            inventory.addItem(packageDisplay, (clicker, event) -> {
                boolean redeemed;
                if (event.getClick() == ClickType.LEFT) {
                    sPackage.getCommands().forEach(s ->
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), s.replace("{PLAYER}", clicker.getName())));
                    sPackage.getMessage().forEach(s ->
                            Chat.msg(clicker, s));
                    redeemed = true;
                } else {
                    Chat.msg(clicker, Message.PACKAGE__DECLINE.toString().replace("{PACKAGE}", value));
                    redeemed = false;
                }
                BuycraftConfirm.getInstance().getDataController().removePlayer(clicker, value, redeemed);

                clicker.closeInventory();
            });
        }

        inventory.open(player);
    }

    public PackageIcon getPackage(List<PackageIcon> packageIcons, String sPackage) {
        for (PackageIcon packageIcon : packageIcons) {
            if (packageIcon.getAPackage().equals(sPackage)) {
                return packageIcon;
            }
        }
        return null;
    }


}
