package me.ooo7Oneu.gokiburi;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public final class Gokiburi extends JavaPlugin {

    public static Gokiburi instance;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Bukkit.getLogger().info("gokiburiをロード");
        Bukkit.getLogger().info("[gokiburi] version:" + getPluginVersion());

        saveDefaultConfig();
        file.configConfig = getConfig();

        setInstance(this);
        file.load();

        this.getCommand("people").setExecutor(new people());
        this.getCommand("goki").setExecutor(new goki());
        this.getCommand("start").setExecutor(new start(this));
        this.getCommand("reset").setExecutor(new reset());

        Bukkit.getServer().getPluginManager().registerEvents(new PoisonListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new playerListener(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new deliveryEventHandler(this), this);


        getServer().getPluginManager().registerEvents(new PoisonListener(), this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static String getPluginVersion() {
        return "0.2";
    }

    public static Gokiburi getInstance() {
        return getPlugin(Gokiburi.class);
    }

    public static void setInstance(Gokiburi instance) {
        Gokiburi.instance = instance;
    }

    public static void debug(String message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage(message);
        }
    }
}
