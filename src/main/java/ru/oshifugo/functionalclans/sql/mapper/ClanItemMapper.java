package ru.oshifugo.functionalclans.sql.mapper;

import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.oshifugo.functionalclans.sql.ClanItem;
import ru.oshifugo.functionalclans.sql.ItemStackSerializer;

import java.sql.ResultSet;

public class ClanItemMapper implements RowMapper<ClanItem> {

    @SneakyThrows
    @Override
    public @Nullable ClanItem mapRow(@NotNull ResultSet resultSet) {
        return ClanItem.builder()
                .id(resultSet.getInt("id"))
                .clanId(resultSet.getInt("clan_id"))
                .slot(resultSet.getByte("slot"))
                .page(resultSet.getByte("page"))
                .itemStack(ItemStackSerializer.fromBase64(resultSet.getString("itemstack")))
                .build();
    }

}
