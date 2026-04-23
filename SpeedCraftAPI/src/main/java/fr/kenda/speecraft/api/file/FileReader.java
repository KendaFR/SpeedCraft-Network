package fr.kenda.speecraft.api.file;

import fr.kenda.speecraft.api.enumeration.EExtension;

import java.io.File;
import java.io.IOException;


public class FileReader {
    public static File createFile(String folder, String fileName, EExtension extension) {
        File directory = new File(folder);

        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(directory, fileName + "." + extension.extension);

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException("Erreur création fichier: " + file.getPath(), e);
            }
        }

        return file;
    }

    public static File getFile(final String folder, final String fileName, EExtension extension) {
        return createFile(folder, fileName, extension);
    }
}