package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class gameSystemCore extends JavaPlugin {

    static List<String> goki = new ArrayList<>();

    public static void peopleVictory() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("人間の勝利");
        }

        //餌まきストップ
        feed.stop();
    }

    public static void gokiVictory() {

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("ゴキブリの勝利");
        }

        //餌まきストップ
        feed.stop();
    }
}
