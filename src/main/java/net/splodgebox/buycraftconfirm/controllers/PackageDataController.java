package net.splodgebox.buycraftconfirm.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PackageDataController {

    @Getter
    private static HashMap<UUID, List<String>> playerPackages = Maps.newHashMap();

    public void savePlayer(Player player, String sPackage) {
        List<String> packages = Lists.newArrayList();
        if (playerPackages.containsKey(player.getUniqueId())) packages = playerPackages.get(player.getUniqueId());
        packages.add(sPackage);

        playerPackages.put(player.getUniqueId(), packages);
        BuycraftConfirm.getInstance().data.getConfig().set("Data." + player.getUniqueId() + ".packages", packages);
        BuycraftConfirm.getInstance().data.save();
    }

    public void removePlayer(Player player, String sPackage) {
        if (playerPackages.containsKey(player.getUniqueId())) {
            List<String> packages = playerPackages.get(player.getUniqueId());
            packages.remove(sPackage);

            playerPackages.put(player.getUniqueId(), packages);
            BuycraftConfirm.getInstance().data.getConfig().set("Data." + player.getUniqueId() + ".packages", packages);
            BuycraftConfirm.getInstance().data.save();
        }
    }

    public int getPackageAmount(Player player) {
        if (playerPackages.containsKey(player.getUniqueId())) {
            List<String> packages = playerPackages.get(player.getUniqueId());
            return packages.size();
        }
        return 0;
    }

    public void loadPlayers() {
        YamlConfiguration config = BuycraftConfirm.getInstance().data.getConfig();
        try {
            for (String data : BuycraftConfirm.getInstance().data.getConfig().getConfigurationSection("Data").getKeys(false)) {
                playerPackages.put(UUID.fromString(data), config.getStringList("Data" + data + ".packages"));
            }
        } catch (NullPointerException ignored) {}
    }
}
