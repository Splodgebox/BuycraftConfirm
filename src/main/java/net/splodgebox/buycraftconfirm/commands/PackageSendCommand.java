package net.splodgebox.buycraftconfirm.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.utils.Chat;
import net.splodgebox.buycraftconfirm.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

@CommandAlias("bconfirm|buycraftconfirm|buycraftc")
public class PackageSendCommand extends BaseCommand {

    @Default
    @CommandPermission("buycraftconfirm.admin")
    public void sendHelpCommand(CommandSender sender) {
        Chat.msg(sender,
                "&6&lBuycraft&e&lConfirm",
                "&e/bconfirm send <player> <package>", "");
    }

    @Subcommand("send")
    @CommandPermission("buycraftconfirm.admin")
    @CommandCompletion("@players @packages")
    public void sendPackage(CommandSender sender, OnlinePlayer player, String packageName, String transaction, String price, String date) {
        if (!BuycraftConfirm.getInstance().getPackageController().getPackages().containsKey(packageName)) {
            Message.ERROR__INVALID_PACKAGE.msg(sender);
            return;
        }

        if (!(sender instanceof ConsoleCommandSender)) {
            Message.ERROR__BUYCRAFT_CONSOLE.msg(sender);
            return;
        }

        BuycraftConfirm.getInstance().getDataController().savePlayer(player.getPlayer(), packageName, transaction, price, date);
        Message.PACKAGE__SENT.msg(player.getPlayer());
    }
}
