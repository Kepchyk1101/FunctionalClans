package ru.oshifugo.functionalclans.command;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.FunctionalClans;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PROTECTED, makeFinal = true)
public class GUITranslatePlaceholder {
    
    @NotNull OfflinePlayer player;
    @NotNull String lang;
    @NotNull YamlConfiguration yml;

    public String get(String path) {
        String res = yml.getString(path);
        if (res == null) {
            Throwable t = new Throwable();
            StackTraceElement[] frames = t.getStackTrace();

            return String.format("§4Please inform to administrator:§f\n§3\"%s\"§f wasn't found in the language (§c%s§f) config. " +
                    "\n§c%s§f -> §9%s§f", path, lang, frames[1].getFileName(), frames[1].getClassName());
        }
        return PlaceholderAPI.setPlaceholders(player, res).replace("&", "§");
    }

    public String get(String path, boolean usePrefix) {
        if (usePrefix) {
            String prefix = FunctionalClans.getInstance().getConfig().getString("prefix");
            return prefix.replace("&", "§") + get(path);
        }
        return get(path);
    }

    public String getName(String path) {
        Object res = yml.get(path);
        if (res != null && yml.get(path + ".name") == null) {
            return null;
        }
        return get(path + ".name");

    }

    public String getLore(String path) {
        Object res = yml.get(path);
        if (res != null && yml.get(path + ".lore") == null) {
            return null;
        }
        return get(path + ".lore");
    }
    
}
