package ru.oshifugo.functionalclans.api;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.adapter.ClanAdapter;
import ru.oshifugo.functionalclans.sql.Member;
import ru.oshifugo.functionalclans.sql.SQLiteUtility;

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
    
    @Override
    public Optional<Clan> getClanByName(@NotNull String clanName) {
        String[] clan = SQLiteUtility.clans.get(clanName);
        if (clan == null) {
            return Optional.empty();
        }
        
        return Optional.of(ClanAdapter.adapt(clan));
    }
    
    @Override
    public Optional<Clan> getClanByPlayer(@NotNull OfflinePlayer player) {
        String clan = Member.getClan(player.getName());
        if (clan == null) {
            return Optional.empty();
        }
        
        return Optional.of(ClanAdapter.adapt(SQLiteUtility.getClanByName(clan)));
    }
    
}
