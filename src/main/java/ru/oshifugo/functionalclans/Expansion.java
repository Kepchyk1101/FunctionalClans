package ru.oshifugo.functionalclans;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import ru.oshifugo.functionalclans.command.subcommands.topCMD;
import ru.oshifugo.functionalclans.sql.Clann;
import ru.oshifugo.functionalclans.sql.Memberr;

import java.util.ArrayList;
import java.util.Arrays;

    /*
        Вот что бывает когда писать код без комментариев
        Сочуствую тому кто тут захочет разобраться ;D
        Комментариям ниже не доверять ;c
     */
public class Expansion extends PlaceholderExpansion {

    @Override
    public @NotNull String getIdentifier() {
        return "fc";
    }

    @Override
    public @NotNull String getAuthor() {
        return "OshiFugo";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0";
    }

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public boolean persist() {
        return true;
    }

    public String space(String parameter, String type) { // Если не ошибаюсь это выводит когда все хорошо
        type = "fc_" + type;
        if (Arrays.asList(FunctionalClans.placeholders_config.get("placeholder_list")).contains(type)) {
            return Utility.hex(FunctionalClans.placeholders_config.get("settings")[1] + parameter + FunctionalClans.placeholders_config.get("settings")[2]);
        } else if (FunctionalClans.placeholders_config.get("settings")[0].equalsIgnoreCase("true")) {
            return Utility.hex(" " + parameter + " ");
        } else return Utility.hex(parameter);
    }

    public String placeholderReturn(String type) {
        type = "fc_" + type;
        if (Arrays.asList(FunctionalClans.placeholders_config.get("placeholder_null_list")).contains(type)) {
            return Utility.hex(FunctionalClans.placeholders_config.get("settings")[4]);
        } else if (type.equals("fc_null")) {
            return Utility.hex(FunctionalClans.placeholders_config.get("settings")[3]);
        } else return Utility.hex(FunctionalClans.placeholders_config.get("settings")[3]);
    }

    public boolean isOfflinePlayerExists(String playerName) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(playerName);
        return offlinePlayer.hasPlayedBefore();
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {

        if (player == null) {
            return placeholderReturn("null");
        }
        Player onlinePlayer = player.getPlayer();
        if (Integer.parseInt(Clann.getCountClan()) == 0) {
            return placeholderReturn("null");
        }
        if (params.contains("player_clan_name") || params.contains("clan_name_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_name_uid_")) {
                return placeholderReturn("clan_name_uid_");
            }
            if (params.contains("clan_name_uid_")) {
                return space(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)), "clan_name_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_name");
            }
            return space(Memberr.getClan(player.getName()), "player_clan_name");
        } else if (params.contains("player_clan_leader") || params.contains("clan_leader_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_leader_uid_")) {
                return placeholderReturn("clan_leader_uid_");
            }
            if (params.contains("clan_leader_uid_")) {
                return space(Clann.getLeader(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_leader_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_leader");
            }
            return space(Clann.getLeader(Memberr.getClan(player.getName())), "player_clan_leader");
        } else if (params.contains("player_clan_cash")  || params.contains("clan_cash_uid_")){
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_cash_uid_")) {
                return placeholderReturn("clan_cash_uid_");
            }
            if (params.contains("clan_cash_uid_")) {
                return space(String.valueOf(Clann.getCash(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_cash_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_cash");
            }
            return space(String.valueOf(Clann.getCash(Memberr.getClan(player.getName()))), "player_clan_cash");
        } else if (params.contains("player_clan_rating") || params.contains("clan_rating_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_rating_uid_")) {
                return placeholderReturn("clan_rating_uid_");
            } else if (params.contains("clan_rating_uid_")) {
                return space(String.valueOf(Clann.getRating(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_rating_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_rating");
            }
            return space(String.valueOf(Clann.getRating(Memberr.getClan(player.getName()))), "player_clan_rating");
        } else if (params.contains("player_clan_type") || params.contains("clan_type_uid_")){
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_type_uid_")) {
                return placeholderReturn("clan_type_uid_");
            } else if (params.contains("clan_type_uid_")) {
                if (Clann.getType(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))) == 0) {
                    return space(Utility.hex(Utility.lang(onlinePlayer,"main.closed")), "clan_type_uid_");
                } else {
                    return space(Utility.hex(Utility.lang(onlinePlayer,"main.open")), "clan_type_uid_");
                }
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_type");
            }
            if (Clann.getType(Memberr.getClan(player.getName())) == 0) {
                return space(Utility.hex(Utility.lang(onlinePlayer,"main.closed")), "player_clan_type");
            } else {
                return space(Utility.hex(Utility.lang(onlinePlayer,"main.open")), "player_clan_type");
            }
        } else if (params.contains("player_clan_tax")|| params.contains("clan_tax_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_tax_uid_")) {
                return placeholderReturn("clan_tax_uid_");
            } else if (params.contains("clan_tax_uid_")) {
                return space(String.valueOf(Clann.getTax(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_tax_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_tax");
            }
            return space(String.valueOf(Clann.getTax(Memberr.getClan(player.getName()))), "player_clan_tax");
        } else if (params.contains("player_clan_status") || params.contains("clan_status_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_status_uid_")) {
                return placeholderReturn("clan_status_uid_");
            } else if (params.contains("clan_status_uid_")) {
                return space(Clann.getStatus(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_status_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_status");
            }
            return space(Utility.hex(Clann.getStatus(Memberr.getClan(player.getName()))), "player_clan_status");
        } else if (params.contains("player_clan_social") || params.contains("clan_social_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_social_uid_")) {
                return placeholderReturn("clan_social_uid_");
            } else if (params.contains("clan_social_uid_")) {
                return space(Clann.getSocial(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_social_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_social");
            }
            return space(Utility.hex(Clann.getSocial(Memberr.getClan(player.getName()))), "player_clan_social");
        } else if (params.contains("player_clan_verification") || params.contains("clan_verification_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_verification_uid_")) {
                return placeholderReturn("clan_verification_uid_");
            } else if (params.contains("clan_verification_uid_")) {
                if (Clann.getVerification(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))) {
                    return space(Utility.hex(Utility.lang(onlinePlayer,"main.true")), "clan_verification_uid_");
                } else {
                    return space(Utility.hex(Utility.lang(onlinePlayer,"main.false")), "clan_verification_uid_");
                }
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_verification");
            }
            if (Clann.getVerification(Memberr.getClan(player.getName()))) {
                return space(Utility.hex(Utility.lang(onlinePlayer,"main.true")), "player_clan_verification");
            } else {
                return space(Utility.hex(Utility.lang(onlinePlayer,"main.false")), "player_clan_verification");
            }
        } else if (params.contains("player_clan_max-player") || params.contains("clan_max-player_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_max-player_uid_")) {
                return placeholderReturn("clan_max-player_uid_");
            } else if (params.contains("clan_max-player_uid_")) {
                return space(String.valueOf(Clann.getMax_player(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_max-player_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_max-player");
            }
            return space(String.valueOf(Clann.getMax_player(Memberr.getClan(player.getName()))), "player_clan_max-player");
        } else if (params.contains("player_clan_online-player") || params.contains("clan_online-player_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_online-player_uid_")) {
                return placeholderReturn("clan_online-player_uid_");
            } else if (params.contains("clan_online-player_uid_")) {
                return space(String.valueOf(Memberr.getOnlineCount(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_online-player_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_online-player");
            }
            return space(String.valueOf(Memberr.getOnlineCount(Memberr.getClan(player.getName()))), "player_clan_online-player");
        } else if (params.contains("player_clan_count-player") || params.contains("clan_count-player_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_count-player_uid_")) {
                return placeholderReturn("clan_count-player_uid_");
            } else if (params.contains("clan_count-player_uid_")) {
                return space(String.valueOf(Memberr.getCount(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_count-player_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_count-player");
            }
            return space(String.valueOf(Memberr.getCount(Memberr.getClan(player.getName()))), "player_clan_count-player");
        } else if (params.contains("player_clan_message") || params.contains("clan_message_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_message_uid_")) {
                return placeholderReturn("clan_message_uid_");
            } else if (params.contains("clan_message_uid_")) {
                return space(Utility.hex(Clann.getMessage(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1)))), "clan_message_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_message");
            }
            return space(Utility.hex(Clann.getMessage(Memberr.getClan(player.getName()))), "player_clan_message");
        } else if (params.contains("player_clan_home-world") || params.contains("clan_home-world_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_home-world_uid_")) {
                return placeholderReturn("clan_home-world_uid_");
            } else if (params.contains("clan_home-world_uid_")) {
                return space(Clann.getWorld(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_home-world_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_home-world");
            }
            return space(Clann.getWorld(Memberr.getClan(player.getName())), "player_clan_home-world");
        } else if (params.contains("player_clan_home-x") || params.contains("clan_home-x_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_home-x_uid_")) {
                return placeholderReturn("clan_home-x_uid_");
            } else if (params.contains("clan_home-x_uid_")) {
                return space(String.valueOf(String.format("%.1f", Clann.getX(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))))), "clan_home-x_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_home-x");
            }
            return space(String.valueOf(String.format("%.1f", Clann.getX(Memberr.getClan(player.getName())))), "player_clan_home-x");
        } else if (params.contains("player_clan_home-y") || params.contains("clan_home-y_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_home-y_uid_")) {
                return placeholderReturn("clan_home-y_uid_");
            } else if (params.contains("clan_home-y_uid_")) {
                return space(String.valueOf(String.format("%.1f", Clann.getY(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))))), "clan_home-y_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_home-y");
            }
            return space(String.valueOf(String.format("%.1f", Clann.getY(Memberr.getClan(player.getName())))), "player_clan_home-y");
        } else if (params.contains("player_clan_home-z") || params.contains("clan_home-z_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_home-z_uid_")) {
                return placeholderReturn("clan_home-z_uid_");
            } else if (params.contains("clan_home-z_uid_")) {
                return space(String.valueOf(String.format("%.1f", Clann.getZ(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))))), "clan_home-z_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_home-z");
            }
            return space(String.valueOf(String.format("%.1f", Clann.getZ(Memberr.getClan(player.getName())))), "player_clan_home-z");
        } else if (params.contains("player_clan_date") || params.contains("clan_date_uid_")){
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_date_uid_")) {
                return placeholderReturn("clan_date_uid_");
            } else if (params.contains("clan_date_uid_")) {
                return space(Clann.getDate(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_date_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_date");
            }
            return space(Clann.getDate(Memberr.getClan(player.getName())), "player_clan_date");
        } else if (params.contains("player_clan_uid") || params.contains("clan_uid_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_uid_uid_")) {
                return placeholderReturn("clan_uid_uid_");
            } else if (params.contains("clan_uid_uid_")) {
                return space(Clann.getUID(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_uid_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_uid");
            }
            return space(Clann.getUID(Memberr.getClan(player.getName())), "player_clan_uid");
        } else if (params.contains("player_clan_creator") || params.contains("clan_creator_uid_")) {
            if (Clann.hasUID(params.substring(params.lastIndexOf("_") + 1)) == true && params.contains("clan_creator_uid_")) {
                return placeholderReturn("clan_creator_uid_");
            } else if (params.contains("clan_creator_uid_")) {
                return space(Clann.getCreator(Clann.getClanNameUID(params.substring(params.lastIndexOf("_") + 1))), "clan_creator_uid_");
            }
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_clan_creator");
            }
            return space(Clann.getCreator(Memberr.getClan(player.getName())), "player_clan_creator");
        }

        if (params.contains("top_") || params.contains("stats_")) {
            String[] func = new String[3];
            if (params.contains("top")) {
                func[0] = "top";
            } else if (params.contains("stats")) {
                func[0] = "stats";
            } else return params;
            if (params.contains("rating")) {
                func[1] = "rating";
            } else if (params.contains("members") && params.contains("top_")) {
                func[1] = "members";
            } else if (params.contains("kills")) {
                func[1] = "kills";
            } else if (params.contains("deaths")) {
                func[1] = "deaths";
            } else if (params.contains("kdr")) {
                func[1] = "kdr";
            } else return params;
            if (params.contains("max")) {
                func[2] = "max";
            } else if (params.contains("min")) {
                func[2] = "min";
            } else return params;
            if (Integer.parseInt(Clann.getCountClan()) == 0) {
                if (params.contains("my_")) {
                    placeholderReturn("my");
                } else if (params.contains("top_")) {
                    return placeholderReturn("top");
                } else return placeholderReturn("stats");
            }
            ArrayList<topCMD> cash = new ArrayList<>();
            if (params.contains("top_") || (params.contains("stats_") && Memberr.getClan(player.getName()) != null)) {
                cash = topCMD.sorting(onlinePlayer, new String[]{func[0], func[1], func[2]});
            } else {
                if (params.contains("my_")) {
                    placeholderReturn("my");
                } else if (params.contains("top_")) {
                    return placeholderReturn("top");
                } else return placeholderReturn("stats");
            }
            if (cash == null) {
                if (params.contains("my_")) {
                    placeholderReturn("my");
                } else if (params.contains("top_")) {
                    return placeholderReturn("top");
                } else return placeholderReturn("stats");
            } else if (cash.size() == 0) {
                if (params.contains("my_")) {
                    placeholderReturn("my");
                } else if (params.contains("top_")) {
                    return placeholderReturn("top");
                } else return placeholderReturn("stats");
            }
            if (!params.contains("my_")) { // TOP, STATS
                if (params.contains("name")) {
                    if (Integer.parseInt(params.substring(params.lastIndexOf("_") + 1)) - 1 < cash.size()) {
                        if (params.contains("top")) {
                            return space(cash.get(Integer.parseInt(params.substring(params.lastIndexOf("_") + 1)) - 1).getName(), "top");
                        } else return space(cash.get(Integer.parseInt(params.substring(params.lastIndexOf("_") + 1)) - 1).getName(), "stats");
                    } else if (params.contains("top")) {
                        return placeholderReturn("top");
                    } else return placeholderReturn("stats");
                } else if (params.contains("count")) {
                    if (Integer.parseInt(params.substring(params.lastIndexOf("_") + 1)) - 1 < cash.size()) {
                        if (params.contains("top")) {
                            return space(String.valueOf(cash.get(Integer.parseInt(params.substring(params.lastIndexOf("_") + 1)) - 1).getCount()), "top");
                        } else return space(String.valueOf(cash.get(Integer.parseInt(params.substring(params.lastIndexOf("_") + 1)) - 1).getCount()), "stats");
                    } else if (params.contains("top")) {
                        return placeholderReturn("top");
                    } else return placeholderReturn("stats");
                } else {
                    if (params.contains("top")) {
                        return placeholderReturn("top");
                    } else if (params.contains("stats")) {
                        return placeholderReturn("stats");
                    }
                }
            } else { // MY(my_top, my_stats)
                if (params.contains("top")) {
                    if (Memberr.getClan(player.getName()) == null) {
                        return placeholderReturn("my");
                    }
                    for (int i = 0; i < cash.size(); i++) {
                        if (cash.get(i).getName().equals(Memberr.getClan(player.getName()))) {
                            if (params.contains("number")) {
                              return space(String.valueOf(i + 1), "my");
                            } else if (params.contains("name")) {
                                return space(cash.get(i).getName(), "my");
                            } else if (params.contains("count")) {
                                return space(String.valueOf(cash.get(i).getCount()), "my");
                            } else return placeholderReturn("my");
                        }
                    }
                } else if (params.contains("stats")) {
                    if (Memberr.getClan(player.getName()) == null) {
                        return placeholderReturn("my");
                    }
                    for (int i = 0; i < cash.size(); i++) {
                        if (cash.get(i).getName().equals(player.getName())) {
                            if (params.contains("number")) {
                                return space(String.valueOf(i + 1), "my");
                            } else if (params.contains("name")) {
                                return space(cash.get(i).getName(), "my");
                            } else if (params.contains("count")) {
                                return space(String.valueOf(cash.get(i).getCount()), "my");
                            } else return placeholderReturn("my");
                        }
                    }
                }
            }
        }
        if (params.equals("player_rank")) {
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_rank");
            }
            return space(Memberr.getRank(player.getName()), "player_rank");
        } else if (params.equals("player_rank-name")) {
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_rank-name");
            }
            return space(Memberr.getRankName(Memberr.getClan(player.getName()), player.getName()), "player_rank-name");
        } else if (params.equals("player_kills")) {
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_kills");
            }
            return space(Memberr.getKills(player.getName()), "player_kills");
        } else if (params.equals("player_deaths")) {
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_deaths");
            }
            return space(Memberr.getDeaths(player.getName()), "player_deaths");
        } else if (params.equals("player_kdr")) {
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_kdr");
            }
            return space(Memberr.getKDR(player.getName()), "player_kdr");
        } else if (params.equals("player_rating")) {
            if (Memberr.getClan(player.getName()) == null) {
                return placeholderReturn("player_rating");
            }
            return space(String.valueOf(Memberr.getRating(player.getName())), "player_rating");
        }
        if (params.equals("all_count_clans")) {
            return space(Clann.getCountClan(), "all_count_clans");
        }
        return null;
    }
}
