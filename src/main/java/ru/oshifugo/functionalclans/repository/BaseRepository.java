package ru.oshifugo.functionalclans.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

@FieldDefaults(level = AccessLevel.PROTECTED ,makeFinal = true)
public abstract class BaseRepository<T, ID> {

    @NotNull ConnectionSource connectionSource;
    @NotNull Dao<T, ID> dao;

    @SneakyThrows
    public BaseRepository(@NotNull ConnectionSource connectionSource) {
        this.connectionSource = connectionSource;
        this.dao = DaoManager.createDao(connectionSource, getEntityClass());
        TableUtils.createTableIfNotExists(connectionSource, getEntityClass());
    }

    @NotNull
    public abstract Class<T> getEntityClass();

    @SneakyThrows
    public Optional<T> findById(@NotNull ID id) {
        return Optional.of(dao.queryForId(id));
    }

    @SneakyThrows
    public List<T> findAll() {
        return dao.queryForAll();
    }

    @SneakyThrows
    public void save(@NotNull T entity) {
        dao.createOrUpdate(entity);
    }

    @SneakyThrows
    public void deleteById(@NotNull ID id) {
        dao.deleteById(id);
    }

    @SneakyThrows
    public void deleteAll() {
        TableUtils.clearTable(connectionSource, getEntityClass());
    }

}
