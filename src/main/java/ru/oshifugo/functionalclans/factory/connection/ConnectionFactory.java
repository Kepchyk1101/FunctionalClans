package ru.oshifugo.functionalclans.factory.connection;

import com.j256.ormlite.support.ConnectionSource;
import org.jetbrains.annotations.NotNull;

@FunctionalInterface
public interface ConnectionFactory {

    @NotNull
    ConnectionSource createConnection();

}
