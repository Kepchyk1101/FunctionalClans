package ru.oshifugo.functionalclans.sql;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.bukkit.inventory.ItemStack;

@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ClanItem {

    int id;
    int clanId;
    byte slot;
    byte page;
    ItemStack itemStack;

}
