package me.ooo7Oneu.gokiburi;

import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.AreaEffectCloud;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.AreaEffectCloudApplyEvent;

import java.net.http.WebSocket;
import java.util.Collection;

public class PoisonListener implements Listener {

    private static final double DAMAGE = 4.0;

    @EventHandler(ignoreCancelled = true)
    public void onCloudApply(AreaEffectCloudApplyEvent event) {
        AreaEffectCloud cloud = event.getEntity();
        if (!(cloud.getSource() instanceof Player)) return;
        Player thrower = (Player) cloud.getSource();
        if (!isGiant(thrower)) return;
        Collection<LivingEntity> affected = event.getAffectedEntities();
        affected.removeIf(livingEntity -> (livingEntity instanceof Player) && isGiant((Player) livingEntity));
        for (LivingEntity le : affected) {
            le.damage(DAMAGE, thrower);
        }
    }

    private boolean isGiant(Player p) {
        AttributeInstance scale = p.getAttribute(Attribute.GENERIC_SCALE);
        if (scale == null) return false;
        return scale.getBaseValue() >= 1.5;
    }
}
