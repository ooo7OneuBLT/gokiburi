package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
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
        }
        commandSender.sendMessage("リセットします");
        file.resetPlayerData();

        if (start.taskID != -1) {
            Bukkit.getScheduler().cancelTask(start.taskID);
            start.taskID = -1;
        }

        return true;
    }
}
