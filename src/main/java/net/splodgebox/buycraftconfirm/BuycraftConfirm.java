package net.splodgebox.buycraftconfirm;

import co.aikar.commands.PaperCommandManager;
import lombok.Getter;
import net.splodgebox.buycraftconfirm.commands.PackageCommand;
import net.splodgebox.buycraftconfirm.commands.PackageSendCommand;
import net.splodgebox.buycraftconfirm.controllers.PackageController;
import net.splodgebox.buycraftconfirm.controllers.PackageDataController;
import net.splodgebox.buycraftconfirm.controllers.PlayerPackageController;
import net.splodgebox.buycraftconfirm.utils.FileManager;
import net.splodgebox.buycraftconfirm.utils.Message;
import net.splodgebox.buycraftconfirm.utils.gui.GuiListener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Arrays;

public final class BuycraftConfirm extends JavaPlugin {

    @Getter
    private static BuycraftConfirm instance;
    public FileManager data;
    public FileManager lang;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        data = new FileManager(this, "data", getDataFolder().getAbsolutePath());
        lang = new FileManager(this, "lang", getDataFolder().getAbsolutePath());

        getServer().getPluginManager().registerEvents(new GuiListener() ,this);

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new PackageCommand());
        commandManager.registerCommand(new PackageSendCommand());

        loadMessages();

        new BukkitRunnable() {
            @Override
            public void run() {
                new PackageController().loadPackages();
                new PackageDataController().loadPlayers();
            }
        }.runTaskLater(this, 20L);
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