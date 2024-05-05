package ru.oshifugo.functionalclans.sql;

import ru.oshifugo.functionalclans.FunctionalClans;
import ru.oshifugo.functionalclans.Utility;
import ru.oshifugo.functionalclans.sql.mapper.ClanItemMapper;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLite {

    private static final ClanItemMapper CLAN_ITEM_MAPPER = new ClanItemMapper();
    public static Connection connection = null;

    public static void connect() {
        try {
            if (!FunctionalClans.getInstance().getDataFolder().mkdirs())
                FunctionalClans.getInstance().getDataFolder().mkdirs();

            String defaultPVP = FunctionalClans.getInstance().getConfig().getString("default-pvp");
            if (Utility.config("data.type").equalsIgnoreCase("SQLITE")) {
                Class.forName("org.sqlite.JDBC");
                connection = DriverManager.getConnection("jdbc:sqlite:" + FunctionalClans.getInstance().getDataFolder().getAbsolutePath() + "/clans.db");
            }
            else if (Utility.config("data.type").equalsIgnoreCase("MYSQL")) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection("jdbc:mysql://" + Utility.config("data.host") + "/" + Utility.config("data.database"),  Utility.config("data.username"),  Utility.config("data.password"));
            } else {
                Utility.error("Database type not found");
                return;
            }
            execute("CREATE TABLE IF NOT EXISTS `clan_list` (`id` varchar(255) NOT NULL,`name` varchar(255) NOT NULL,`leader` varchar(255) NOT NULL,`cash` varchar(255) NOT NULL,`rating` varchar(255) NOT NULL,`type` varchar(255) NOT NULL,`tax` varchar(255) NOT NULL,`status` varchar(255) NOT NULL,`social` varchar(255) NOT NULL,`verification` varchar(255) NOT NULL,`max_player` varchar(255) NOT NULL,`message` varchar(255) NOT NULL,`world` varchar(255) NOT NULL,`x` varchar(255) NOT NULL,`y` varchar(255) NOT NULL,`z` varchar(255) NOT NULL,`xcur` varchar(255) NOT NULL,`ycur` varchar(255) NOT NULL,`date` varchar(255) NOT NULL,`uid` varchar(255) NOT NULL,`creator` varchar(255) NOT NULL, `pvp` varchar(255) NOT NULL DEFAULT " + defaultPVP  + ")");
            execute("CREATE TABLE IF NOT EXISTS `clan_members` (`id` varchar(255) NOT NULL ,`name` varchar(255) NOT NULL,`role` varchar(255) NOT NULL,`clan` varchar(255) NOT NULL,`kills` varchar(255) NOT NULL,`death` varchar(255) NOT NULL,`quest` varchar(255) NOT NULL,`rating` varchar(255) NOT NULL)");
            execute("CREATE TABLE IF NOT EXISTS `clan_permissions` (`id` varchar(255) NOT NULL,`clan` varchar(255) NOT NULL,`role` varchar(255) NOT NULL,`role_name` varchar(255) NOT NULL,`kick` boolean NOT NULL,`invite` boolean NOT NULL,`cash_add` boolean NOT NULL,`cash_remove` boolean NOT NULL,`rmanage` boolean NOT NULL,`chat` boolean NOT NULL,`msg` boolean NOT NULL,`alliance_add` boolean NOT NULL,`alliance_remove` boolean NOT NULL)");
            execute("CREATE TABLE IF NOT EXISTS `clan_alliance` (`id` varchar(255) NOT NULL, `UID_1` varchar(255) NOT NULL, `UID_2` varchar(255) NOT NULL)");
            execute("CREATE TABLE IF NOT EXISTS `clan_chest` (" +
                    "`id` INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "`clan_id` INT, " +
                    "`slot` TINYINT, " +
                    "`page` TINYINT, " +
                    "`itemstack` TEXT" +
                    ");");

            update2p1p0();

            addClanChestColumnToClanTable();

        } catch (Exception e) {
            e.printStackTrace();
            Utility.error("An error occurred while connecting to the database.");
        }
    }

    private static void addClanChestColumnToClanTable() {
        try {
            connection.createStatement().execute("ALTER TABLE `clan_list` ADD COLUMN `chest_pages` TINYINT DEFAULT 1;");
        } catch (SQLException ignored) {

        }
    }

    private static void update2p1p0() throws IOException {
        if (FunctionalClans.getInstance().getDBVersion() < 2) {
            execute("ALTER TABLE clan_list ADD COLUMN pvp varchar(255) DEFAULT '1'");
            FunctionalClans.getInstance().setDBVersion(2);
        }
    }

    @Deprecated
    public static void execute(String query) {
        Utility.debug("Sended Query: " + query);
        try {
            connection.createStatement().execute(query);
        } catch (Exception e) {
            e.printStackTrace();
            Utility.debug(e.getMessage());
        }
    }

    public static void execute(String sql, Object... args) {
        PreparedStatement pstmt;
        try {
            pstmt = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; i++)  {
                pstmt.setObject(i + 1, args[i]);
            }
            pstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet executeQuery(String query, Object... args) {
        Utility.debug("Sending query: " + query);
        ResultSet resultSet = null;
        try {
            PreparedStatement prSt = connection.prepareStatement(query);
            for (int i = 0; i < args.length; i++) {
                prSt.setObject(i + 1, args[i]);
            }
            resultSet = prSt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public static void getClans() {
        try {
            int a, b, p, d;
            try {
                ResultSet resultSet = executeQuery("SELECT * FROM clan_list");
                for (a = 0; resultSet.next(); a++) {
                    String clanName = resultSet.getString("name");
                    String[] clan = {clanName, resultSet.getString("leader"), resultSet.getString("cash"), resultSet.getString("rating"), resultSet.getString("type"), resultSet.getString("tax"), resultSet.getString("status"), resultSet.getString("social"), resultSet.getString("verification"), resultSet.getString("max_player"), resultSet.getString("message"), resultSet.getString("world"), resultSet.getString("x"), resultSet.getString("y"), resultSet.getString("z"), resultSet.getString("xcur"), resultSet.getString("ycur"), resultSet.getString("date"), resultSet.getString("uid"), resultSet.getString("creator"), resultSet.getString("pvp"), resultSet.getString("chest_pages")};
                    SQLiteUtility.clans.put(clanName, clan);

                    ResultSet clanChestRs = executeQuery("SELECT * FROM `clan_chest` WHERE `clan_id` = ?;", resultSet.getInt("id"));
                    List<ClanItem> clanItems = new ArrayList<>();
                    while (clanChestRs.next()) {
                        clanItems.add(CLAN_ITEM_MAPPER.mapRow(clanChestRs));
                    }

                    SQLiteUtility.clanChests.put(clanName, new ClanChest(resultSet.getInt("id"), resultSet.getInt("chest_pages"), clanItems));
                }
            } catch (Exception errors) {
                a = -1;
                Utility.error("getClans -> SELECT * FROM clan_list");
                errors.printStackTrace();
            }
            try {
                ResultSet resultSet = executeQuery("SELECT * FROM clan_members");
                for (b = 0; resultSet.next(); b++) {
                    String[] m = {resultSet.getString("name"), resultSet.getString("role"), resultSet.getString("clan"), resultSet.getString("kills"), resultSet.getString("death"), resultSet.getString("quest"), resultSet.getString("rating")};
                    SQLiteUtility.members.put(resultSet.getString("name"), m);
                    SQLiteUtility.member_clan.put(resultSet.getString("name"), resultSet.getString("clan"));
                }
            } catch (Exception errors) {
                b = -1;
                Utility.error("getClans -> SELECT * FROM clan_members");
                errors.printStackTrace();
            }
            try {
                ResultSet resultSet = executeQuery("SELECT * FROM clan_permissions");
                for (p = 0; resultSet.next(); p++) {
                    String[] role = {resultSet.getString("role"), resultSet.getString("role_name"), resultSet.getString("kick"), resultSet.getString("invite"), resultSet.getString("cash_add"), resultSet.getString("cash_remove"), resultSet.getString("rmanage"), resultSet.getString("chat"), resultSet.getString("msg"), resultSet.getString("alliance_add"), resultSet.getString("alliance_remove")};

                    SQLiteUtility.clan_role.put(resultSet.getString("clan") + "_" + resultSet.getString("role"), role);
                }
            } catch (Exception errors) {
                p = -1;
                Utility.error("getClans -> SELECT * FROM clan_permissions");
                errors.printStackTrace();
            }
            try {
                ResultSet resultSet = executeQuery("SELECT * FROM clan_alliance");
                for (d = 0; resultSet.next(); d++) {
                    String[] alliance = {resultSet.getString("UID_1"), resultSet.getString("UID_2")};

                    SQLiteUtility.clan_alliance.put(resultSet.getString("UID_1") + "_" + resultSet.getString("UID_2"), alliance);
                }
            } catch (Exception errors) {
                d = -1;
                Utility.error("getClans -> SELECT * FROM clan_alliance");
                errors.printStackTrace();
            }
            Utility.debug("The clan base is loaded. (" + a + ")");
            Utility.debug("The player base is loaded. (" + b + ")");
            Utility.debug("The rank database is loaded. (" + p + ")");
            Utility.debug("Alliance database is loading. (" + d + ")");
        } catch (Exception e) {
            Utility.error("An error occurred while requesting clans");
            e.printStackTrace();
        }
    }

    public static void disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (Exception var1) {
            var1.printStackTrace();
        }
    }

}
