package ru.oshifugo.functionalclans.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.oshifugo.functionalclans.api.Clan;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClanImpl implements Clan {
  
  boolean pvp;
  
}
