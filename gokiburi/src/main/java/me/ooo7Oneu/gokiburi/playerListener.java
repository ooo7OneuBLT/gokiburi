package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.EventListener;

public class playerListener implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        if (!start.gameRunning) return;

        Player deathPlayer = event.getPlayer();
        deathPlayer.setGameMode(GameMode.SPECTATOR);
        if (!gameSystemCore.isNormalPlayer()) {
            gameSystemCore.peopleVictory();
        }
    }
}
