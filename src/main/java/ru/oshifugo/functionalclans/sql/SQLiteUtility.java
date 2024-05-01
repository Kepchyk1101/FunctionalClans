package ru.oshifugo.functionalclans.sql;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.oshifugo.functionalclans.FunctionalClans;
import ru.oshifugo.functionalclans.Utility;
import ru.oshifugo.functionalclans.entity.Clan;
import ru.oshifugo.functionalclans.entity.Member;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

public class SQLiteUtility {

    public static HashMap<String, String[]> clans = new HashMap();
    public static HashMap<String, String[]> members = new HashMap<>();
    public static HashMap<String, String> member_clan = new HashMap<>();
    public static HashMap<String, String[]> clan_role = new HashMap<>();
    public static HashMap<String, String[]> clan_alliance = new HashMap<>();
    public static Map<String, Clan> clanCache = new HashMap<>();

    public static String[] getClanByPlayerNamee(String player) {
        String[] q = {members.get(player)[0].toLowerCase(), members.get(player)[2]};
        Utility.debug("getClanByName -> memberName: " + q[0] + " clanName: " + q[1]);
        return q;
    }

    public static void create(String clanName, String leader) {
        SimpleDateFormat formater = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        Date date = new Date();
        String d = formater.format(date.getTime());
        String uid;
        YamlConfiguration config = (YamlConfiguration) FunctionalClans.getInstance().getConfig();
        if (Clann.createUID() != null) {
            uid = Clann.createUID();
        } else return;
        String[] c = {clanName, leader, Utility.config("cash"), Utility.config("rating"), Utility.config("type"), Utility.config("tax"), "&7╰(*´︶`*)╯&c♡", "null", String.valueOf(Utility.configBoolean("verification")), Utility.config("max_player"), "null", "null", String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0), String.valueOf(0.0F), String.valueOf(0.0F), d, uid, leader, config.getString("default-pvp")};
        String[] m = {leader, "5", clanName, "0", "0", "0", "0"};
        String[] role_1 = {"1", Utility.config("role_1"), String.valueOf(Utility.configBoolean("kick_default")), String.valueOf(Utility.configBoolean("invite_default")), String.valueOf(Utility.configBoolean("cash_add_default")), String.valueOf(Utility.configBoolean("cash_remove_default")), String.valueOf(Utility.configBoolean("rmanage_default")), String.valueOf(Utility.configBoolean("chat_default")), String.valueOf(Utility.configBoolean("msg_default")), String.valueOf(Utility.configBoolean("alliance_add_default")), String.valueOf(Utility.configBoolean("alliance_remove_default"))};
        String[] role_2 = {"2", Utility.config("role_2"), String.valueOf(Utility.configBoolean("kick_default")), String.valueOf(Utility.configBoolean("invite_default")), String.valueOf(Utility.configBoolean("cash_add_default")), String.valueOf(Utility.configBoolean("cash_remove_default")), String.valueOf(Utility.configBoolean("rmanage_default")), String.valueOf(Utility.configBoolean("chat_default")), String.valueOf(Utility.configBoolean("msg_default")), String.valueOf(Utility.configBoolean("alliance_add_default")), String.valueOf(Utility.configBoolean("alliance_remove_default"))};
        String[] role_3 = {"3", Utility.config("role_3"), String.valueOf(Utility.configBoolean("kick_default")), String.valueOf(Utility.configBoolean("invite_default")), String.valueOf(Utility.configBoolean("cash_add_default")), String.valueOf(Utility.configBoolean("cash_remove_default")), String.valueOf(Utility.configBoolean("rmanage_default")), String.valueOf(Utility.configBoolean("chat_default")), String.valueOf(Utility.configBoolean("msg_default")), String.valueOf(Utility.configBoolean("alliance_add_default")), String.valueOf(Utility.configBoolean("alliance_remove_default"))};
        String[] role_4 = {"4", Utility.config("role_4"), String.valueOf(Utility.configBoolean("kick_default")), String.valueOf(Utility.configBoolean("invite_default")), String.valueOf(Utility.configBoolean("cash_add_default")), String.valueOf(Utility.configBoolean("cash_remove_default")), String.valueOf(Utility.configBoolean("rmanage_default")), String.valueOf(Utility.configBoolean("chat_default")), String.valueOf(Utility.configBoolean("msg_default")), String.valueOf(Utility.configBoolean("alliance_add_default")), String.valueOf(Utility.configBoolean("alliance_remove_default"))};
        clans.put(clanName, c);
        members.put(leader, m);
        member_clan.put(leader, clanName);
        clan_role.put(clanName + "_1", role_1);
        clan_role.put(clanName + "_2", role_2);
        clan_role.put(clanName + "_3", role_3);
        clan_role.put(clanName + "_4", role_4);
        SQLite.execute("INSERT INTO clan_list (id, name, leader, cash, rating, type, tax, status, social, verification, max_player, message, world, x, y, z, xcur, ycur, date, uid, creator, pvp) VALUES ('" + uid + "', '" + clanName + "', '" + leader + "', '" + Utility.config("cash") + "', '" + Utility.config("rating") + "', '" + Utility.config("type") + "', '" + Utility.config("tax") + "', '" + "&7╰(*´︶`*)╯&c♡" + "', 'null', '" + Utility.configBoolean("verification") + "', '" + Utility.config("max_player") + "', 'null', 'null', '0', '0', '0', '0', '0', '" + d + "', '" + uid + "', '" + leader + "', '" + config.getInt("default-pvp") + "')");
        SQLite.execute("INSERT INTO clan_members (id, name, role, clan, kills, death, quest, rating) VALUES ('" + uid + "', '" + leader + "', '5', '" + clanName + "', '0', '0', '0', '0')");
        SQLite.execute("INSERT INTO clan_permissions (id, clan, role, role_name, kick, invite, cash_add, cash_remove, rmanage, chat, msg, alliance_add, alliance_remove) VALUES ('" + uid + "', '" + clanName +"', '1', '" + Utility.config("role_1") +"', " + Utility.configBoolean("kick_default") +", " + Utility.configBoolean("invite_default") +", " + Utility.configBoolean("cash_add_default") +", " + Utility.configBoolean("cash_remove_default") +", " + Utility.configBoolean("rmanage_default") +", " + Utility.configBoolean("chat_default") +", " + Utility.configBoolean("msg_default") +", " + Utility.configBoolean("alliance_add_default") +", " + Utility.configBoolean("alliance_remove_default") +")");
        SQLite.execute("INSERT INTO clan_permissions (id, clan, role, role_name, kick, invite, cash_add, cash_remove, rmanage, chat, msg, alliance_add, alliance_remove) VALUES ('" + uid + "', '" + clanName +"', '2', '" + Utility.config("role_2") +"', " + Utility.configBoolean("kick_default") +", " + Utility.configBoolean("invite_default") +", " + Utility.configBoolean("cash_add_default") +", " + Utility.configBoolean("cash_remove_default") +", " + Utility.configBoolean("rmanage_default") +", " + Utility.configBoolean("chat_default") +", " + Utility.configBoolean("msg_default") +", " + Utility.configBoolean("alliance_add_default") +", " + Utility.configBoolean("alliance_remove_default") +")");
        SQLite.execute("INSERT INTO clan_permissions (id, clan, role, role_name, kick, invite, cash_add, cash_remove, rmanage, chat, msg, alliance_add, alliance_remove) VALUES ('" + uid + "', '" + clanName +"', '3', '" + Utility.config("role_3") +"', " + Utility.configBoolean("kick_default") +", " + Utility.configBoolean("invite_default") +", " + Utility.configBoolean("cash_add_default") +", " + Utility.configBoolean("cash_remove_default") +", " + Utility.configBoolean("rmanage_default") +", " + Utility.configBoolean("chat_default") +", " + Utility.configBoolean("msg_default") +", " + Utility.configBoolean("alliance_add_default") +", " + Utility.configBoolean("alliance_remove_default") +")");
        SQLite.execute("INSERT INTO clan_permissions (id, clan, role, role_name, kick, invite, cash_add, cash_remove, rmanage, chat, msg, alliance_add, alliance_remove) VALUES ('" + uid + "', '" + clanName +"', '4', '" + Utility.config("role_4") +"', " + Utility.configBoolean("kick_default") +", " + Utility.configBoolean("invite_default") +", " + Utility.configBoolean("cash_add_default") +", " + Utility.configBoolean("cash_remove_default") +", " + Utility.configBoolean("rmanage_default") +", " + Utility.configBoolean("chat_default") +", " + Utility.configBoolean("msg_default") +", " + Utility.configBoolean("alliance_add_default") +", " + Utility.configBoolean("alliance_remove_default") +")");

        Member creator = Member.builder().name(leader).build();

        Clan clan = Clan.builder()
                .name(clanName)
                .leader(leader)
                .cash(Utility.configInt("cash"))
                .rating(Utility.configInt("rating"))
                .type(Utility.configInt("type"))
                .tax(Utility.configInt("tax"))
                .status("&7╰(*´︶`*)╯&c♡")
                .social(null)
                .verification(Utility.configBoolean("verification"))
                .maxPlayer(Utility.configInt("max_player"))
                .message(null)
                .world(null)
                .x(0)
                .y(0)
                .z(0)
                .xcur(0)
                .ycur(0)
                .date(Timestamp.valueOf(LocalDateTime.now()))
                .creator(leader)
                .pvp(config.getBoolean("default-pvp"))
                .build();

        creator.setClan(clan);

        FunctionalClans.getInstance().getMemberRepository().save(creator);

        clanCache.put(clanName, clan);

    }

    public static void delete(String clan) {
        ArrayList<String> mem = Memberr.getMembers(clan);
        for (int i = 0; i < mem.size(); i++) {
            members.remove(mem.get(i));
            member_clan.remove(mem.get(i));
            SQLite.execute("DELETE FROM clan_members WHERE name='" + mem.get(i) + "'");
        }
        SQLite.execute("DELETE FROM clan_list WHERE name='" + clan + "'");
        SQLite.execute("DELETE FROM clan_permissions WHERE clan='" + clan + "'");
        clan_role.remove(clan);
        clans.remove(clan);
    }

    public static void update() {
        clans = new HashMap<>();
        members = new HashMap<>();
        member_clan = new HashMap<>();
        clan_role = new HashMap<>();
        clan_alliance = new HashMap<>();
        InviteRequest.request = new HashMap<>();
        SQLite.getClans();

        FunctionalClans.placeholders_config = new HashMap<>(); // Неработает, конфиг тот же
        FunctionalClans.hashConfig(); // Неработает, конфиг тот же
    }

    @Nullable
    public static Clan getClanByName(@NotNull String name) {
        return clanCache.get(name);
    }

    @Nullable
    public static Clan getClanByMemberName(@NotNull String name) {
        Set<String> strings = clanCache.keySet();
        for (String clanName : strings) {
            Clan clan = clanCache.get(clanName);
            for (Member member : clan.getMembers()) {
                if (member.getName().equals(name)) {
                    return clan;
                }
            }
        }
        return null;
    }

}
