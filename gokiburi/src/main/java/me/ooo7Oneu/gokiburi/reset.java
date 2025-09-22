package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class reset implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(1.0);
            player.removePotionEffect(PotionEffectType.SLOWNESS);
            player.getInventory().clear();
            player.setGameMode(GameMode.ADVENTURE);
        }
        commandSender.sendMessage("リセットします");
        file.resetPlayerData();
        start.gameRunning = false;
        //ゴキブリリストをクリア
        gameSystemCore.goki.clear();
        //餌まきストップ
        feed.stop();
        if (start.taskID != -1) {
            Bukkit.getScheduler().cancelTask(start.taskID);
            start.taskID = -1;
        }

        //ボスバー削除
        if (deliveryEventHandler.bossBar != null) {
            deliveryEventHandler.bossBar.removeAll();
            deliveryEventHandler.bossBar.setVisible(false);
            deliveryEventHandler.bossBar = null;
        }

        //アイテムをkill
        for (World world : Bukkit.getWorlds()) {
            for (Entity entity : world.getEntities()) {
                if (entity instanceof Item) {
                    Item dropped = (Item) entity;
                    dropped.remove();
                }
            }
        }

        return true;
    }
}
