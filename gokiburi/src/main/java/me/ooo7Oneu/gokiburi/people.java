package me.ooo7Oneu.gokiburi;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class people implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {

        if (strings[0] != null) {
            String targetName = strings[0];

            List<String> people = file.playerDataConfig.getStringList("people");
            people.add(targetName);
            file.playerDataConfig.set("people", people);
            file.savePlayerData();

            if (commandSender instanceof Player) {
                Player player = (Player) commandSender;
                player.sendMessage(targetName + "を人間に追加しました");
            }
        } else if (strings[0] == null) {
            commandSender.sendMessage(ChatColor.DARK_RED + "プレイヤーが指定されてません。");
        }
        return false;
    }
}
