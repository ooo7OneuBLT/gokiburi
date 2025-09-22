package me.ooo7Oneu.gokiburi;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class file {

    public static File playerDataFile;
    public static FileConfiguration playerDataConfig;
    public static FileConfiguration configConfig;

    public static void load() {
        File pluginFolder = Gokiburi.getInstance().getDataFolder();
        playerDataFile = new File(Gokiburi.getInstance().getDataFolder(), "playerData.yml");

        if (!pluginFolder.exists()) {
            pluginFolder.mkdir();
        }

        Gokiburi.getInstance().saveResource("playerData.yml", true);
        playerDataConfig = YamlConfiguration.loadConfiguration(playerDataFile);
    }

    public static void savePlayerData() {
        try {
            playerDataConfig.save(playerDataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void resetPlayerData() {
        file.playerDataConfig.set("people", new ArrayList<>());
        file.playerDataConfig.set("goki", new ArrayList<>());
    }
}
