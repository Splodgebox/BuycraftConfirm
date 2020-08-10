package net.splodgebox.buycraftconfirm.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.controllers.PackageController;
import org.bukkit.command.CommandSender;

@CommandAlias("bconfirm|buycraftconfirm|buycraftc")
public class PackageReloadCommand extends BaseCommand {

    @Subcommand("reload")
    @CommandPermission("buycraftconfirm.admin")
    public void reloadConfigs(CommandSender sender) {
        BuycraftConfirm.getInstance().reloadConfig();
        BuycraftConfirm.getInstance().getPackageController().loadPackages();

        BuycraftConfirm.getInstance().lang.reload();
    }

}
