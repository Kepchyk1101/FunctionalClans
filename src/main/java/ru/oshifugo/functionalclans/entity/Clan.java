package ru.oshifugo.functionalclans.entity;

import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@DatabaseTable
public class Clan {

    @DatabaseField(generatedId = true)
    long id;

    @DatabaseField(unique = true)
    String name;

    @DatabaseField
    String leader;

    @DatabaseField
    int cash;

    @DatabaseField
    int rating;

    @DatabaseField
    int type;

    @DatabaseField
    int tax;

    @DatabaseField
    String status;

    @DatabaseField
    String social;

    @DatabaseField
    boolean verification;

    @DatabaseField(columnName = "max_player")
    int maxPlayer;

    @DatabaseField
    String message;

    @DatabaseField
    String world;

    @DatabaseField
    int x, y, z;

    @DatabaseField
    int xcur, ycur;

    @DatabaseField
    Timestamp date;

    @DatabaseField
    String creator;

    @DatabaseField
    boolean pvp;

    @ForeignCollectionField
    ForeignCollection<Member> members;

    public void togglePvp() {
        pvp = !pvp;
    }

    public void incrementCash(int value) {
        cash += value;
    }

}
