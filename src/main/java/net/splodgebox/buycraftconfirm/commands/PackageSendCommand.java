package net.splodgebox.buycraftconfirm.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import co.aikar.commands.bukkit.contexts.OnlinePlayer;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.utils.Chat;
import net.splodgebox.buycraftconfirm.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
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
    public void sendPackage(CommandSender sender, String player, String packageName, String transaction, String price, String date) {
        if (!BuycraftConfirm.getInstance().getPackageController().getPackages().containsKey(packageName)) {
            Message.ERROR__INVALID_PACKAGE.msg(sender);
            return;
        }

        if (!(sender instanceof ConsoleCommandSender)) {
            Message.ERROR__BUYCRAFT_CONSOLE.msg(sender);
            return;
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player);
        BuycraftConfirm.getInstance().getDataController().savePlayer(offlinePlayer.getUniqueId(), packageName, transaction, price, date);

        if (offlinePlayer.isOnline()) {
            Message.PACKAGE__SENT.msg(offlinePlayer.getPlayer());
        }
    }
}
