package ru.oshifugo.functionalclans.repository.impl;

import com.j256.ormlite.support.ConnectionSource;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.entity.Clan;
import ru.oshifugo.functionalclans.repository.BaseRepository;

public class ClanRepository extends BaseRepository<Clan, Long> {

    public ClanRepository(@NotNull ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    public @NotNull Class<Clan> getEntityClass() {
        return Clan.class;
    }

}
