package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

public class playerListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!start.gameRunning) return;
        Player player = event.getPlayer();

        if (gameSystemCore.goki.contains(player.getName())) {
            gameSystemCore.goki.remove(player.getName());
            if (gameSystemCore.goki.isEmpty()) {
                gameSystemCore.peopleVictory();
            }
        }
        //人間が死んだ
        if (start.people.contains(player.getName())) {
            start.people.remove(player.getName());
            if (start.people.isEmpty()) {
                gameSystemCore.gokiVictory();
            }
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Player p = e.getPlayer();
        if (start.gameRunning) {
            p.setGameMode(GameMode.SPECTATOR);
        }
    }
}
