package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class goki implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        List<String> people = file.playerDataConfig.getStringList("people");
        List<String> goki = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!people.contains(p.getName())) {
                goki.add(p.getName());
            }
        }
        file.playerDataConfig.set("goki", goki);
        file.savePlayerData();
        return false;
    }
}
