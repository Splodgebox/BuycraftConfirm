package net.splodgebox.buycraftconfirm.controllers;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.Package;
import net.splodgebox.buycraftconfirm.utils.Chat;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class PackageController {

    @Getter
    private static HashMap<String, Package> packages = Maps.newHashMap();

    public void loadPackages() {
        packages.clear();
        FileConfiguration config = BuycraftConfirm.getInstance().getConfig();
        for (String aPackage : BuycraftConfirm.getInstance().getConfig().getConfigurationSection("Packages").getKeys(false)) {
            String path = "Packages." + aPackage + ".";
            packages.put(aPackage,
                    new Package(
                            aPackage,
                            Material.valueOf(config.getString(path + "material")),
                            config.getString(path + "name"),
                            config.getStringList(path + "commands"),
                            config.getStringList(path + "message")
                    ));
        }
    }
}
