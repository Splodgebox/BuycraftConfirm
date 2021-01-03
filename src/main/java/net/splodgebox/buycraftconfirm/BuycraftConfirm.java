package net.splodgebox.buycraftconfirm;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.splodgebox.buycraftconfirm.commands.PackageCommand;
import net.splodgebox.buycraftconfirm.commands.PackageReloadCommand;
import net.splodgebox.buycraftconfirm.commands.PackageSendCommand;
import net.splodgebox.buycraftconfirm.controllers.PackageController;
import net.splodgebox.buycraftconfirm.controllers.PackageDataController;
import net.splodgebox.buycraftconfirm.utils.FileManager;
import net.splodgebox.buycraftconfirm.utils.ItemUtils;
import net.splodgebox.buycraftconfirm.utils.Message;
import net.splodgebox.buycraftconfirm.utils.gui.GuiListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public final class BuycraftConfirm extends JavaPlugin {

    @Getter
    private static BuycraftConfirm instance;

    @Getter
    public PackageDataController dataController;
    @Getter
    public PackageController packageController;
    @Getter
    public ItemUtils itemUtils;

    public FileManager data;
    public FileManager lang;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        data = new FileManager(this, "data", getDataFolder().getAbsolutePath());
        lang = new FileManager(this, "lang", getDataFolder().getAbsolutePath());

        dataController = new PackageDataController();
        packageController = new PackageController();
        itemUtils = new ItemUtils();

        getServer().getPluginManager().registerEvents(new GuiListener() ,this);

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new PackageCommand());
        commandManager.registerCommand(new PackageSendCommand());
        commandManager.registerCommand(new PackageReloadCommand());
        commandManager.getCommandCompletions().registerStaticCompletion("packages",
                getConfig().getConfigurationSection("Packages").getKeys(false));

        loadMessages();

        Bukkit.getScheduler().runTaskLater(this, () -> {
            getPackageController().loadPackages();
            getDataController().loadPlayers();
        }, 20L);
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    private void loadMessages() {
        Arrays.stream(Message.values()).filter(message -> !this.lang.getConfig().contains(message.getPath())).forEachOrdered(message ->
                this.lang.getConfig().set(message.getPath(), message.getDefault()));

        lang.save();
        Message.setFile(this.lang.getConfig());
    }
}
