package ru.oshifugo.functionalclans;

import lombok.Getter;
import net.milkbowl.vault.economy.Economy;
import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import ru.oshifugo.functionalclans.command.AdminClanCommands;
import ru.oshifugo.functionalclans.command.ClanCommands;
import ru.oshifugo.functionalclans.listener.*;
import ru.oshifugo.functionalclans.sql.SQLite;
import ru.oshifugo.functionalclans.tabcomplete.AdminTab;
import ru.oshifugo.functionalclans.tabcomplete.CommandsTab;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public final class FunctionalClans extends JavaPlugin {

    @Getter
    private static FunctionalClans instance;

    @Getter
    private GUITranslate lang;

    @Getter
    private static Economy economy = null;

    public static HashMap<String, String[]> placeholders_config = new HashMap<>();

    @Override
    public void onEnable() {

        long time = System.currentTimeMillis();

        instance = this;
        saveDefaultConfig();
        SQLite.connect();
        SQLite.getClans();

        if (!setupEconomy() ) {
            Utility.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Utility.error("Disabled due to no Vault dependency found!");
            Utility.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            Utility.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            Utility.error("Disabled due to PlaceholderAPI dependency not found!");
            Utility.error("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        if (getServer().getPluginManager().getPlugin("SalaryManager") != null) {
            getServer().getPluginManager().registerEvents(new SalaryListener(), this);
        }

        getCommand("clan").setExecutor(new ClanCommands());
        getServer().getPluginCommand("clan").setTabCompleter(new CommandsTab());
        getCommand("fc").setExecutor(new AdminClanCommands());
        getServer().getPluginCommand("fc").setTabCompleter(new AdminTab());

        Bukkit.getPluginManager().registerEvents(new PlayerDeathListener(), this);
        Bukkit.getPluginManager().registerEvents(new EntityListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerJoinListener(), this);

        if (!new File(getDataFolder(), "message.yml").exists()) {
            saveResource("message.yml", false);
        }

        if (!new File(getDataFolder(), "gui_lang_en.yml").exists()) {
            saveResource("gui_lang_en.yml", true);
        }
        if (!new File(getDataFolder(), "gui_lang_ru.yml").exists()) {
            saveResource("gui_lang_ru.yml", true);
        }
        getConfig().addDefault("gui.lang", "en");
        if (getConfig().getBoolean("gui.override-lang")) {
            saveResource("gui_lang_en.yml", true);
        }
        GUITranslate.init(this, getConfig().getString("gui.lang"));
        lang = GUITranslate.getInstance();
        saveDefaultConfig();

        new ClanStorageListener(this);

        hashConfig();
        new Metrics(this, 17919);
        new Expansion().register();

        Utility.info(Utility.hex("<#FF00FF>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
        Utility.info(Utility.hex("<#00CED1>Successfully enabled. &7(" + ChatColor.YELLOW + (System.currentTimeMillis() - time) + " ms" + ChatColor.GREEN + "&7)"));
        Utility.info(Utility.hex("<#FF00FF>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return economy != null;
    }

    public static void hashConfig() {
        placeholders_config.put("settings", new String[]{Utility.config("placeholder_space"), Utility.config("placeholder_prefix"), Utility.config("placeholder_suffix"), Utility.config("placeholder_null"), Utility.config("placeholder_null_list_symbol")});
        placeholders_config.put("placeholder_list", Utility.configList("placeholder_list"));
        placeholders_config.put("placeholder_null_list", Utility.configList("placeholder_null_list"));
    }

    @Override
    public void onDisable() {
        SQLite.disconnect();
        Utility.info(Utility.hex("<#FF00FF>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
        Utility.info(Utility.hex("<#00CED1>Plugin disabled."));
        Utility.info(Utility.hex("<#FF00FF>~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"));
        instance = null;
    }

    public int getDBVersion() {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
        int r = config.getInt("db-ver");
        return r;
    }

    public void setDBVersion(int value) throws IOException {
        File file = new File(getDataFolder(), "config.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);
        config.set("db-ver", value);
        config.save(file);
    }

}
