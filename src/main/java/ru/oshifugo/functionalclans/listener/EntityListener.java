package ru.oshifugo.functionalclans.listener;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityCombustByEntityEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.projectiles.ProjectileSource;
import ru.oshifugo.functionalclans.entity.Clan;
import ru.oshifugo.functionalclans.entity.Member;
import ru.oshifugo.functionalclans.sql.Clann;
import ru.oshifugo.functionalclans.sql.Memberr;
import ru.oshifugo.functionalclans.sql.SQLiteUtility;

public class EntityListener implements Listener {

    @EventHandler
    private void onEntityDamagedByEntityEvent(EntityDamageByEntityEvent event) {

        Entity damager = event.getDamager();
        Entity damaged = event.getEntity();

        if (!(damaged instanceof Player)) return;
        if (!(damager instanceof Player) && !(damager instanceof Projectile)) return;
        if (damager instanceof  Projectile) {
            Projectile projectile = (Projectile) damager;
            ProjectileSource shooter = projectile.getShooter();
            if (!(shooter instanceof Player)) return;
            damager = (Entity) shooter;
        }

        Clan clan = SQLiteUtility.getClanByMemberName(damager.getName());
        if (clan == null || clan.isPvp()) return;

        for (Member member : clan.getMembers()) {
            if (member.getName().equals(damaged.getName())) {
                return;
            }
        }

        event.setCancelled(true);
    }

    @EventHandler
    private void onEntityCombustByEntityEvent(EntityCombustByEntityEvent event) {

        Entity combusted = event.getEntity();
        Entity combuster = event.getCombuster();

        if (!(combusted instanceof Player) || !(combuster instanceof Player)) return;

        Clan clan = SQLiteUtility.getClanByMemberName(combusted.getName());
        if (clan == null || clan.isPvp()) return;

        for (Member member : clan.getMembers()) {
            if (member.getName().equals(combuster.getName())) {
                return;
            }
        }

        event.setCancelled(true);

    }


}
