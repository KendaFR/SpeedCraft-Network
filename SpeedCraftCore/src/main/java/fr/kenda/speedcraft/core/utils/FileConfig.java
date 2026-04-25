package fr.kenda.speedcraft.core.utils;

import fr.kenda.speedcraft.api.enumeration.EExtension;
import fr.kenda.speedcraft.api.file.FileReader;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class FileConfig {

    public static String createPlaceholder(String placeholder)
    {
        return "{" + placeholder + "}";
    }
    @Getter
    private final File file;
    private final YamlConfiguration config;

    public FileConfig(String folder, String fileName, EExtension extension) {
        File targetFolder = new File("plugins/SpeedCraft", folder);
        if (!targetFolder.exists()) {
            targetFolder.mkdirs();
        }

        this.file = FileReader.getFile(targetFolder.getPath(), fileName, extension);
        this.config = YamlConfiguration.loadConfiguration(this.file);
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

    public <T> T getOrDefault(String path, T defaultValue, Class<T> type) {
        Object value = config.get(path);

        if (value == null) return defaultValue;

        if (type == List.class && value instanceof List) {
            return (T) value;
        }

        if (!type.isInstance(value)) {
            throw new IllegalStateException(
                    "Type mismatch for path '" + path + "' (expected " +
                            type.getSimpleName() + ", got " + value.getClass().getSimpleName() + ")"
            );
        }

        return type.cast(value);
    }

    public void addDefault(String path, Object value) {
        if (!config.contains(path)) {
            config.set(path, value);
        }
    }

    public String getString(String path, String def) {
        return getOrDefault(path, def, String.class);
    }

    public List<String> getStringList(String path, List<String> def) {
        return getOrDefault(path, def, List.class);
    }
}