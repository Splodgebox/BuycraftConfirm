package net.splodgebox.buycraftconfirm.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;

public enum Message {
    ERROR__INVALID_PACKAGE( "&f&l(&6&lBuycraft&e&lConfirm&f&l) &eThat is not a valid buycraft package"),
    ERROR__NO_PACKAGE( "&f&l(&6&lBuycraft&e&lConfirm&f&l) &eYou do not have any active packages!"),

    PACKAGE__SENT("&f&l(&6&lBuycraft&e&lConfirm&f&l) &eYou have a new buycraft purchase &7Do /confirm to accept the payment"),
    PACKAGE__DECLINE("&f&l(&6&lBuycraft&e&lConfirm&f&l) &eYou have declined {PACKAGE}!"),

    COMMAND__RELOAD("&f&l(&6&lBuycraft&e&lConfirm&f&l) &eSuccessfully reloaded all the configuration files and packages");

    public static SimpleDateFormat sdf;
    private static FileConfiguration LANG;
    private String path;
    private String msg;

    Message(String path, String start) {
        this.path = path;
        this.msg = start;
    }

    Message(String string) {
        this.path = this.name().replace("__", ".");
        this.msg = string;
    }

    public static void setFile(FileConfiguration configuration) {
        LANG = configuration;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, msg));
    }

    public String getDefault() {
        return this.msg;
    }

    public String getPath() {
        return this.path;
    }

    public void msg(CommandSender p, Object... args) {
        String s = toString();

        if (s.contains("\n")) {
            String[] split = s.split("\n");

            for (String inner : split) {
                sendMessage(p, inner, args);
            }

        } else {
            sendMessage(p, s, args);
        }
    }

    public void broadcast(Object... args) {
        String s = toString();

        if (s.contains("\n")) {
            String[] split = s.split("\n");

            for (String inner : split) {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    sendMessage(player, inner, args);
                }
            }
        } else {
            for (Player player : Bukkit.getOnlinePlayers()) {
                sendMessage(player, s, args);
            }
        }
    }

    private String getFinalized(String string, Object... order) {
        int current = 0;

        for (Object object : order) {
            String placeholder = "{" + current + "}";

            if (string.contains(placeholder)) {
                if (object instanceof CommandSender) {
                    string = string.replace(placeholder, ((CommandSender) object).getName());
                } else if (object instanceof OfflinePlayer) {
                    string = string.replace(placeholder, ((OfflinePlayer) object).getName());
                } else if (object instanceof Location) {
                    Location location = (Location) object;
                    String repl = location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();

                    string = string.replace(placeholder, repl);
                } else if (object instanceof Double) {
                    string = string.replace(placeholder, "" + object);
                } else if (object instanceof Integer) {
                    string = string.replace(placeholder, "" + object);
                } else if (object instanceof ItemStack) {
                    string = string.replace(placeholder, getItemStackName((ItemStack) object));
                }
            }

            current++;
        }

        return string;
    }

    private void sendMessage(CommandSender target, String string, Object... order) {
        string = getFinalized(string, order);

        target.sendMessage(string);
    }

    private String getItemStackName(ItemStack itemStack) {
        String name = itemStack.getType().toString().replace("_", " ");

        if (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName()) {
            return itemStack.getItemMeta().getDisplayName();
        }

        return name;
    }

}
