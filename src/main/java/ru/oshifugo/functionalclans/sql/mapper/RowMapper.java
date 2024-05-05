package ru.oshifugo.functionalclans.sql.mapper;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;

@FunctionalInterface
public interface RowMapper<T> {

    @Nullable
    T mapRow(@NotNull ResultSet resultSet);

}
