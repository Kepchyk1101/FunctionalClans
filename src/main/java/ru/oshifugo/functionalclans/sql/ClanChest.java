package ru.oshifugo.functionalclans.sql;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.FunctionalClans;
import ru.oshifugo.functionalclans.Utility;
import ru.oshifugo.functionalclans.listener.ClanStorageListener;
import xyz.xenondevs.invui.item.builder.ItemBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ClanChest {

    @Getter
    int clanId;
    @NotNull List<Inventory> pages = new ArrayList<>();

    public ClanChest(int clanId, int pages, @NotNull List<ClanItem> items) {

        this.clanId = clanId;
        FileConfiguration config = FunctionalClans.getInstance().getConfig();

        for (int i = 0; i < pages; i++) {
            Inventory page = Bukkit.createInventory(null, 54, Utility.hex(config.getString("chest.title").replace("%page%", String.valueOf(i + 1))));
            addBorder(page);
            ClanStorageListener.clanChests.put(page, this);
            this.pages.add(page);
        }

        for (ClanItem item : items) {
            Inventory page = this.pages.get(item.getPage());
            if (page == null) continue;
            page.setItem(item.getSlot(), item.getItemStack());
        }

    }

    private void addBorder(@NotNull Inventory page) {
        page.setItem(45, getPageItem("prev_page"));
        for (int i = 46; i <= 52; i++) {
            page.setItem(i, new ItemBuilder(Material.BLACK_STAINED_GLASS_PANE).setDisplayName("").get());
        }
        page.setItem(53, getPageItem("next_page"));
    }

    private ItemStack getPageItem(String name) {
        ConfigurationSection item = FunctionalClans.getInstance().getConfig().getConfigurationSection("chest." + name);
        Material material = Material.valueOf(item.getString("material"));
        String displayName = Utility.hex(item.getString("name"));
        List<String> lore = item.getStringList("lore").stream().map(Utility::hex).collect(Collectors.toList());
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(lore);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public void openFor(@NotNull Player player) {
        openFor(player, (byte) 0);
    }

    public void openFor(@NotNull Player player, byte page) {
        player.openInventory(pages.get(page));
        ClanStorageListener.viewers.put(player, (int) page);
    }

    public void nextPage(@NotNull Player player) {
        int currPage = ClanStorageListener.viewers.get(player);
        if (pages.size() > currPage + 1) {
            openFor(player, (byte) ++currPage);
            ClanStorageListener.viewers.replace(player, currPage);
        }
    }

    public void prevPage(@NotNull Player player) {
        int currPage = ClanStorageListener.viewers.get(player);
        if (currPage - 1 >= 0) {
            openFor(player, (byte) --currPage);
            ClanStorageListener.viewers.replace(player, currPage);
        }
    }

    @NotNull
    public List<ClanItem> getContent() {
        List<ClanItem> content = new ArrayList<>();
        for (int i = 0; i < pages.size(); i++) {
            Inventory page = pages.get(i);
            for (int j = 0; j < 44; j++) {
                ItemStack item = page.getItem(j);
                if (item == null) continue;
                content.add(
                        ClanItem.builder()
                                .clanId(clanId)
                                .page((byte) i)
                                .slot((byte) j)
                                .itemStack(item)
                                .build()
                );
            }
        }
        return content;
    }

}
