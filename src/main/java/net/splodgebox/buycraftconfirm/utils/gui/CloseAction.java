package net.splodgebox.buycraftconfirm.utils.gui;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryCloseEvent;

public interface CloseAction {
    void close(Player var1, InventoryCloseEvent var2);
}
