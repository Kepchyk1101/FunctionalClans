package ru.oshifugo.functionalclans.api;

import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public interface FunctionalClansApi {

    /**
     * Returns a list of clan member names
     * @param clanName clan name
     * @return list of clan member names
     */
    List<String> getMembersNames(@NotNull String clanName);

    /**
     * Returns the name of the clan the player belongs to
     * @param player player
     * @return clan name if the player is a member of it
     */
    Optional<String> getClanName(@NotNull OfflinePlayer player);

}
