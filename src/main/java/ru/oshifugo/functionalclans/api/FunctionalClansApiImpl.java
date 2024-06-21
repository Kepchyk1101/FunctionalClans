package ru.oshifugo.functionalclans.api;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.sql.Member;

import java.util.List;
import java.util.Optional;

public class FunctionalClansApiImpl implements FunctionalClansApi {

    @Override
    public List<String> getMembersNames(@NotNull String clanName) {
        return Member.getMembers(clanName);
    }

    @Override
    public Optional<String> getClanName(@NotNull OfflinePlayer player) {
        return Optional.ofNullable(Member.getClan(player.getName()));
    }

}
