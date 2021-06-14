package net.splodgebox.buycraftconfirm.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Default;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.controllers.PlayerPackageController;
import net.splodgebox.buycraftconfirm.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandAlias("confirm")
public class PackageCommand extends BaseCommand {
    @Default
    public void openPackageMenu(CommandSender sender) {
        Player player = (Player) sender;
        if (BuycraftConfirm.getInstance().getDataController().getPackageAmount(player.getUniqueId()) == 0) {
            Message.ERROR__NO_PACKAGE.msg(player);
            return;
        }
        new PlayerPackageController(player).openConfirmMenu();
    }
}
