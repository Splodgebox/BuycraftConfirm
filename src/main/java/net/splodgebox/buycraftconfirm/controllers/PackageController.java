package net.splodgebox.buycraftconfirm.controllers;

import com.google.common.collect.Maps;
import lombok.Getter;
import net.splodgebox.buycraftconfirm.BuycraftConfirm;
import net.splodgebox.buycraftconfirm.data.Package;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;

public class PackageController {

    @Getter
    private HashMap<String, Package> packages = Maps.newHashMap();

    public void loadPackages() {
        getPackages().clear();
        FileConfiguration config = BuycraftConfirm.getInstance().getConfig();
        for (String aPackage : BuycraftConfirm.getInstance().getConfig().getConfigurationSection("Packages").getKeys(false)) {
            String path = "Packages." + aPackage + ".";
            Package bPackage = new Package(
                    aPackage,
                    Material.valueOf(config.getString(path + "material")),
                    config.getString(path + "name"),
                    config.getStringList(path + "commands"),
                    config.getStringList(path + "message")
            );
            getPackages().put(aPackage, bPackage);
        }
    }
}
