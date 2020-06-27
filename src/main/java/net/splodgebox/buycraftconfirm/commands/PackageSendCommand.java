package net.splodgebox.buycraftconfirm.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.splodgebox.buycraftconfirm.controllers.PackageController;
import net.splodgebox.buycraftconfirm.controllers.PackageDataController;
import net.splodgebox.buycraftconfirm.utils.Chat;
import net.splodgebox.buycraftconfirm.utils.Message;
import org.bukkit.command.CommandSender;

@CommandAlias("bconfirm|buycraftconfirm|buycraftc")
public class PackageSendCommand extends BaseCommand {

    @Default
    @CommandPermission("buycraftconfirm.admin")
    public void sendHelpCommand(CommandSender sender) {
        Chat.msg(sender,
                "&6&lBuycraft&e&lConfirm",
                "&e/bconfirm send <player> <package>",
                "");
    }

    @Subcommand("send")
    @CommandPermission("buycraftconfirm.admin")
    @CommandCompletion("@players @packages")
    public void sendPackage(CommandSender sender, OnlinePlayer player, String packageName) {
        if (!PackageController.getPackages().containsKey(packageName)) {
            Message.ERROR__INVALID_PACKAGE.msg(sender);
            return;
        }
        new PackageDataController().savePlayer(player.getPlayer(), packageName);
        Message.PACKAGE__SENT.msg(player.getPlayer());
    }
}
