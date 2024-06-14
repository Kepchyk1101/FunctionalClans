package ru.oshifugo.functionalclans;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.command.GUITranslatePlaceholder;

import java.io.File;

@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class GUITranslate {

    @Getter
    private static GUITranslate instance;
    
    @NotNull String lang;
    @NotNull File languageFile;
    @NotNull YamlConfiguration yml;
    @NotNull FunctionalClans plugin;

    protected GUITranslate(@NotNull FunctionalClans plugin, @NotNull String lang) {
        this.lang = lang;
        this.plugin = plugin;
        this.languageFile = new File(plugin.getDataFolder() + "/gui_lang_" + lang + ".yml");
        yml = YamlConfiguration.loadConfiguration(languageFile);
    }

    public static void init(@NotNull FunctionalClans plugin, @NotNull String lang) {
        instance = new GUITranslate(plugin, lang);
    }

    public static GUITranslatePlaceholder getTranslate(OfflinePlayer player) {
        return new GUITranslatePlaceholder(player, getInstance().lang, getInstance().yml);
    }
    
}
