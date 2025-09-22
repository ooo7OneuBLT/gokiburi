package me.ooo7Oneu.gokiburi;

import org.bukkit.*;
import org.bukkit.attribute.Attribute;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;

import java.util.List;

public class deliveryEventHandler implements Listener {

    public final JavaPlugin plugin;
    public deliveryEventHandler(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public static BossBar bossBar;
    private int deliveredCount = 0; // 納品数


    @EventHandler
    public void onDropItem(PlayerDropItemEvent e) {
        if (!start.gameRunning) return;

        Item item = e.getItemDrop();
        Player player = e.getPlayer();

        // 納品対象アイテムか判定
        if (item.getItemStack().getType() != Material.SUGAR) return;

        // Configから納品場所と半径を取得
        World world = Bukkit.getWorld("world");
        if (world == null) return;

        List<Double> coords = plugin.getConfig().getDoubleList("deliveryPos.xyz");
        Location center = new Location(world, coords.get(0), coords.get(1), coords.get(2));
        double radius = plugin.getConfig().getDouble("deliveryPos.radius");


        // 1秒後に位置を確認して納品判定
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            if (!item.isValid()) return; // 既に拾われていたら終了

            if (item.getLocation().distance(center) <= radius) {
                item.remove(); // アイテムを消す
                addPoints(player);
                player.sendMessage("§a砂糖を納品！ポイント+1");
                world.playSound(center, Sound.ENTITY_PLAYER_LEVELUP, 1f, 1f);
                world.spawnParticle(Particle.HAPPY_VILLAGER, center, 10, 0.5, 0.5, 0.5);
            }
        }, 20L); // 20ティック = 1秒

    }

    public static void createBossBar() {
        bossBar = Bukkit.createBossBar(
                "納品進捗", // タイトル
                BarColor.GREEN, // 色
                BarStyle.SOLID  // スタイル
        );
        bossBar.setProgress(0.0); // 初期値
        bossBar.setVisible(true);

        // 全プレイヤーに表示（必要に応じて参加時に追加）
        for (Player p : Bukkit.getOnlinePlayers()) {
            bossBar.addPlayer(p);
        }
    }

    // 納品時に呼び出すメソッド
    public void addPoints(Player player) {
        deliveredCount++;
        double progress = Math.min(1.0, deliveredCount / 10.0); // 10個で満タン
        bossBar.setProgress(progress);
        bossBar.setTitle("納品進捗: " + deliveredCount + " / 10");

        if (deliveredCount >= 10) {
            Bukkit.broadcastMessage("§a納品が完了しました！");
            bossBar.setColor(BarColor.YELLOW);
            //必要ならリセット
            deliveredCount = 0;
            bossBar.setProgress(0.0);
            //ゴキブリにダイヤの剣を付与
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.getAttribute(Attribute.GENERIC_SCALE).getValue() == 1) {
                    p.getInventory().addItem(new ItemStack(Material.DIAMOND_SWORD));
                    p.sendMessage(ChatColor.GREEN + "人間に反撃開始!");
                } else {
                    p.removePotionEffect(PotionEffectType.SATURATION);
                    p.removePotionEffect(PotionEffectType.SLOWNESS);
                }
            }
        }
    }
}
