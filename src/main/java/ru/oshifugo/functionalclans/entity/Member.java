package ru.oshifugo.functionalclans.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DatabaseTable
public class Member {

    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField
    String name;

    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true)
    Clan clan;

    @DatabaseField
    int role;

    @DatabaseField
    int kills;

    @DatabaseField
    int death;

    @DatabaseField
    int quest;

    @DatabaseField
    int rating;

}
