package ru.oshifugo.functionalclans.repository.impl;

import com.j256.ormlite.support.ConnectionSource;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.entity.Clan;
import ru.oshifugo.functionalclans.entity.Member;
import ru.oshifugo.functionalclans.repository.BaseRepository;

public class MemberRepository extends BaseRepository<Member, Clan> {

    public MemberRepository(@NotNull ConnectionSource connectionSource) {
        super(connectionSource);
    }

    @Override
    public @NotNull Class<Member> getEntityClass() {
        return Member.class;
    }

}
