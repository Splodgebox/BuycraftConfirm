package net.splodgebox.buycraftconfirm.controllers;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Getter;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.PackageIcon;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

public class PackageDataController {

    @Getter
    private HashMap<UUID, List<PackageIcon>> playerPackageData = Maps.newHashMap();
    @Getter
    private HashMap<UUID, List<String>> playerPackages = Maps.newHashMap();

    public void savePlayer(Player player, String sPackage, String transaction, String price, String date) {
        List<PackageIcon> packages = Lists.newArrayList();
        List<String> packageList = Lists.newArrayList();
        if (getPlayerPackages().containsKey(player.getUniqueId()))
            packageList = getPlayerPackages().get(player.getUniqueId());

        if (getPlayerPackageData().containsKey(player.getUniqueId()))
            packages = getPlayerPackageData().get(player.getUniqueId());

        packages.add(new PackageIcon(sPackage, transaction, price, date));
        packageList.add(sPackage);

        getPlayerPackageData().put(player.getUniqueId(), packages);
        getPlayerPackages().put(player.getUniqueId(), packageList);

        List<String> storageValue = Lists.newArrayList();

        for (PackageIcon aPackage : packages) {
            storageValue.add(aPackage.getAPackage() + "@@;" + aPackage.getTransaction() +  "@@;" + aPackage.getPrice() + "@@;" + aPackage.getDate());
        }

        BuycraftConfirm.getInstance().data.getConfig().set("Data." + player.getUniqueId() + ".packages", packageList);
        BuycraftConfirm.getInstance().data.getConfig().set("Data." + player.getUniqueId() + ".package-data", storageValue);
        BuycraftConfirm.getInstance().data.save();
    }

    public void removePlayer(Player player, String sPackage, boolean redeemed) {
        if (getPlayerPackages().containsKey(player.getUniqueId()) &&
                getPlayerPackageData().containsKey(player.getUniqueId())) {
            List<String> packages = getPlayerPackages().get(player.getUniqueId());
            packages.remove(sPackage);

            List<PackageIcon> packageIcons = getPlayerPackageData().get(player.getUniqueId());
            PackageIcon packageIcon = null;

            Iterator<PackageIcon> ite = packageIcons.iterator();

            while(ite.hasNext()) {
                PackageIcon value = ite.next();
                if (value.getAPackage().equals(sPackage)) {
                    packageIcon = value;
                    ite.remove();
                    break;
                }
            }

            List<String> storageValue = Lists.newArrayList();

            for (PackageIcon aPackage : packageIcons) {
                storageValue.add(aPackage.getAPackage() + "@@;" + aPackage.getTransaction() +  "@@;" + aPackage.getPrice() + "@@;" + aPackage.getDate());
            }

            getPlayerPackages().put(player.getUniqueId(), packages);
            getPlayerPackageData().put(player.getUniqueId(), packageIcons);
            BuycraftConfirm.getInstance().data.getConfig().set("Data." + player.getUniqueId() + ".packages", packages);
            BuycraftConfirm.getInstance().data.getConfig().set("Data." + player.getUniqueId() + ".package-data", storageValue);

            if (packageIcon != null) {
                BuycraftConfirm.getInstance().data.getConfig().set("Log." + player.getUniqueId() + "." + packageIcon.getTransaction() + ".name", packageIcon.getAPackage());
                BuycraftConfirm.getInstance().data.getConfig().set("Log." + player.getUniqueId() + "." + packageIcon.getTransaction() + ".date", packageIcon.getDate());
                BuycraftConfirm.getInstance().data.getConfig().set("Log." + player.getUniqueId() + "." + packageIcon.getTransaction() + ".price", packageIcon.getPrice());
                BuycraftConfirm.getInstance().data.getConfig().set("Log." + player.getUniqueId() + "." + packageIcon.getTransaction() + ".redeemed", redeemed);
            }

            BuycraftConfirm.getInstance().data.save();
            BuycraftConfirm.getInstance().data.reload();
        }
    }

    public int getPackageAmount(Player player) {
        if (getPlayerPackages().containsKey(player.getUniqueId())) {
            List<String> packages = getPlayerPackages().get(player.getUniqueId());
            return packages.size();
        }
        return 0;
    }

    public void loadPlayers() {
        YamlConfiguration config = BuycraftConfirm.getInstance().data.getConfig();
        try {
            for (String data : BuycraftConfirm.getInstance().data.getConfig().getConfigurationSection("Data").getKeys(false)) {
                getPlayerPackages().put(UUID.fromString(data), config.getStringList("Data." + data + ".packages"));
                List<PackageIcon> packageIcons = Lists.newArrayList();

                List<String> packageValues = config.getStringList("Data." + data + ".package-data");
                for (String packageIcon : packageValues) {
                    String[] split = packageIcon.split("@@;");

                    packageIcons.add(new PackageIcon(split[0], split[1], split[2], split[3]));
                }

                getPlayerPackageData().put(UUID.fromString(data), packageIcons);
            }
        } catch (NullPointerException ignored) {}
    }
}
