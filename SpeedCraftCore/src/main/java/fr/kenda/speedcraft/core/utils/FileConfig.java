package fr.kenda.speedcraft.core.utils;

import fr.kenda.speecraft.api.enumeration.EExtension;
import fr.kenda.speecraft.api.file.FileReader;
import fr.kenda.speedcraft.core.SpeedCraftCore;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class FileConfig {

    private final File file;
    private final YamlConfiguration config;

    public FileConfig(String folder, String fileName, EExtension extension) {

        File targetFolder = new File("plugins/SpeedCraft", folder);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        this.file = FileReader.getFile(targetFolder.getPath(), fileName, extension);
        this.config = YamlConfiguration.loadConfiguration(file);

        save();
    }

    public FileConfig(String fileName, EExtension extension) {
        this("", fileName, extension);
    }

    public YamlConfiguration get() {
        return config;
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            throw new RuntimeException("Erreur sauvegarde fichier: " + file.getName(), e);
        }
    }

    public void reload() {
        try {
            config.load(file);
        } catch (Exception e) {
            throw new RuntimeException("Erreur reload fichier: " + file.getName(), e);
        }
    }

    public File getFile() {
        return file;
    }

    public <T> T getOrDefault(String path, T defaultValue, Class<T> type) {
        Object value = config.get(path);

        if (value == null) {
            config.set(path, defaultValue);
            save();
            return defaultValue;
        }

        if (!type.isInstance(value)) {
            throw new IllegalStateException(
                    "Type mismatch for path '" + path + "' (expected " + type.getSimpleName() + ")"
            );
        }

        return type.cast(value);
    }
}