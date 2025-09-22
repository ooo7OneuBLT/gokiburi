package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.List;
import java.util.Random;

public class feed {

    public static JavaPlugin plugin = Gokiburi.getInstance();
    private static BukkitTask task;

    public feed(JavaPlugin plugin) {
        feed.plugin = plugin;
    }

    public static void start() {
        task = Bukkit.getScheduler().runTaskTimer(plugin, () -> {


            //餌スポーン
            List<Integer> feedPos = file.configConfig.getIntegerList("feedPos.xyz");
            int centerX = feedPos.get(0);
            int centerY = feedPos.get(1);
            int centerZ = feedPos.get(2);
            int radius = file.configConfig.getInt("feedPos.radius");

            World world = Bukkit.getWorld("world");
            Location center = new Location(world, centerX, centerY, centerZ);

            Random random = new Random();
            double distance = random.nextDouble() * radius;
            double angle = random.nextDouble() * 2 * Math.PI;

            double x = center.getX() + distance * Math.cos(angle);
            double z = center.getZ() + distance * Math.sin(angle);
            double y = world.getHighestBlockYAt((int) x, (int) z) + 1;

            Location dropLocation = new Location(world, x, y, z);

            world.dropItem(dropLocation, item.feed);
        }, 20L, 100L);

        deliveryEventHandler.createBossBar();
    }

    public static void stop() {
        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }
}
