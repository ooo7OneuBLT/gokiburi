package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class gameSystemCore extends JavaPlugin {
    public static boolean isNormalPlayer() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.getGameMode() != GameMode.ADVENTURE) continue;
            AttributeInstance scaleAttr = player.getAttribute(Attribute.GENERIC_SCALE);
            if (scaleAttr != null && scaleAttr.getBaseValue() < 1.5) {
                return true;
            }
        } return false;
    }

    public static void peopleVictory() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("人間の勝利");
        }
    }

    public static void gokiVictory() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("ゴキブリの勝利");
        }
    }
}
