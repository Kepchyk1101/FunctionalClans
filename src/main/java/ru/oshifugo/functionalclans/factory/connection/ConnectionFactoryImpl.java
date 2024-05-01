package ru.oshifugo.functionalclans.factory.connection;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Level;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.support.ConnectionSource;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class ConnectionFactoryImpl implements ConnectionFactory {

    @NotNull Plugin plugin;

    @SneakyThrows
    @Override
    public @NotNull ConnectionSource createConnection() {
        Logger.setGlobalLogLevel(Level.ERROR);
        return new JdbcConnectionSource("jdbc:sqlite:" + new File(plugin.getDataFolder(), "test.db"));
    }

}
