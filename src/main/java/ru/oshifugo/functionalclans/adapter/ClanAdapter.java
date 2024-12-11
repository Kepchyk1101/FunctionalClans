package ru.oshifugo.functionalclans.adapter;

import lombok.experimental.UtilityClass;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.api.Clan;
import ru.oshifugo.functionalclans.entity.ClanImpl;

@UtilityClass
public class ClanAdapter {
  
  public @NotNull Clan adapt(@NotNull String[] clan) {
    return new ClanImpl(Boolean.parseBoolean(clan[20]));
  }
  
}
