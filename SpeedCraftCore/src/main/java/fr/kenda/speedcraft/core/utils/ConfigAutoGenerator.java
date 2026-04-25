package fr.kenda.speedcraft.core.utils;

import java.util.Map;

public class ConfigAutoGenerator {

    public static void generate(Map<String, Object> messages, FileConfig config) {

        messages.forEach(config::addDefault);
        config.save();

        Logger.info("Enregistrement de " + messages.size() + " entrées.");
    }
}