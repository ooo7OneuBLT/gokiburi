package me.ooo7Oneu.gokiburi;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class start implements CommandExecutor {

    private Gokiburi plugin;
    public static int taskID = -1;
    public start(Gokiburi plugin) {
        this.plugin = plugin;
    }
    public static boolean gameRunning = false;
    static List<String> people = new ArrayList<>();


    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        people = file.playerDataConfig.getStringList("people");
        if (people.isEmpty()) {
            commandSender.sendMessage("人間が登録されていません");
            return true;
        }

        //リストにゴキブリのプレイヤーを落とし込む
        gameSystemCore.goki = file.playerDataConfig.getStringList("goki");

        gameRunning = true;
        World world = Bukkit.getWorld("world");
        if (world != null) {
            world.setDifficulty(Difficulty.NORMAL);
        }

        for (String name : people) {
            Player target = Bukkit.getPlayerExact(name);
            if (target != null && target.isOnline()) {
                target.getAttribute(Attribute.GENERIC_SCALE).setBaseValue(6.0);
                target.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 9999999, 1));
                target.addPotionEffect(new PotionEffect(PotionEffectType.SLOWNESS, 999999999, 1));
            }
        }

        for (String name : gameSystemCore.goki) {
            Player target = Bukkit.getPlayerExact(name);
            if (target != null && target.isOnline()) {
                target.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
            }
        }

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayer.setGameMode(GameMode.ADVENTURE);
            onlinePlayer.setHealth(20);
            onlinePlayer.getInventory().clear();
        }

        //餌のスポーン
        feed.start();

        if (taskID != -1) {
            Bukkit.getScheduler().cancelTask(taskID);
        }
        taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            for (String name : people) {
                Player target = Bukkit.getPlayerExact(name);
                if (target != null && target.isOnline()) {
                    // 濃厚な残留ポーション（例: 即時ダメージ II）
                    ItemStack potion = new ItemStack(Material.LINGERING_POTION);
                    PotionMeta meta = (PotionMeta) potion.getItemMeta();
                    meta.addCustomEffect(new PotionEffect(PotionEffectType.POISON, 1, 1), true);
                    meta.setColor(Color.PURPLE);

                    potion.setItemMeta(meta);

                    // インベントリに追加
                    target.getInventory().addItem(potion);
                }
            }
        }, 0L, 100L); // 0L=即時開始, 100L=5秒間隔(20tick=1秒)

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            gameSystemCore.gokiVictory();
        },12000L);

        return true;
    }
}
