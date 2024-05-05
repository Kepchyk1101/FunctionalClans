package ru.oshifugo.functionalclans.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.sql.ClanChest;
import ru.oshifugo.functionalclans.sql.ClanItem;
import ru.oshifugo.functionalclans.sql.ItemStackSerializer;
import ru.oshifugo.functionalclans.sql.SQLite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class ClanStorageListener implements Listener {

    public static Map<Inventory, ClanChest> clanChests = new HashMap<>();
    public static Map<Player, Integer> viewers = new HashMap<>();

    public ClanStorageListener(@NotNull Plugin plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    private void onInventoryClick(InventoryClickEvent event) {

        if (!clanChests.containsKey(event.getInventory())) return;

        int slot = event.getSlot();

        if (slot >= 45) {
            event.setCancelled(true);
            ClanChest clanChest = clanChests.get(event.getInventory());
            Player player = (Player) event.getWhoClicked();
            if (slot == 45) clanChest.prevPage(player);
            if (slot == 53) clanChest.nextPage(player);
        }

    }

    @EventHandler
    private void onInventoryClose(InventoryCloseEvent event) {

        if (!clanChests.containsKey(event.getInventory())) return;

        ClanChest clanChest = clanChests.get(event.getInventory());
        List<ClanItem> content = clanChest.getContent();

        viewers.remove(event.getPlayer());

        CompletableFuture.runAsync(() -> {
            SQLite.execute("DELETE FROM `clan_chest` WHERE `clan_id` = ?;", clanChest.getClanId());
            for (ClanItem clanItem : content) {
                SQLite.execute(
                        "INSERT INTO `clan_chest` (`clan_id`, `slot`, `page`, `itemstack`) VALUES (?,?,?,?);",
                        clanItem.getClanId(),
                        clanItem.getSlot(),
                        clanItem.getPage(),
                        ItemStackSerializer.toBase64(clanItem.getItemStack())
                );
            }
        });

    }

}
