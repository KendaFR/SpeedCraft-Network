package fr.kenda.speedcraft.core.command;

import fr.kenda.speedcraft.core.command.annotation.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class CommandManager {

    private final Plugin plugin;
    private final String basePackage;

    public CommandManager(Plugin plugin, String basePackage) {
        this.plugin = plugin;
        this.basePackage = basePackage;
    }

    public void registerAll() {
        try {
            Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            CommandMap commandMap = (CommandMap) field.get(Bukkit.getServer());

            List<Class<?>> classes = getClasses(basePackage);
            Bukkit.getLogger().info("[CommandManager] Classes scannées : " + classes.size());

            for (Class<?> clazz : classes) {

                if (!clazz.isAnnotationPresent(Command.class)) continue;
                if (!AbstractCommand.class.isAssignableFrom(clazz)) continue;

                Command annotation = clazz.getAnnotation(Command.class);
                AbstractCommand executor =
                        (AbstractCommand) clazz.getDeclaredConstructor().newInstance();

                Constructor<PluginCommand> constructor =
                        PluginCommand.class.getDeclaredConstructor(String.class, Plugin.class);
                constructor.setAccessible(true);

                PluginCommand cmd = constructor.newInstance(annotation.name(), plugin);
                cmd.setExecutor(executor);
                cmd.setAliases(Arrays.asList(annotation.aliases()));

                if (!annotation.permission().isEmpty()) {
                    cmd.setPermission(annotation.permission());
                }

                commandMap.register(plugin.getName(), cmd);
                Bukkit.getLogger().info("[CommandManager] Commande enregistrée : /" + annotation.name());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Class<?>> getClasses(String packageName) throws Exception {
        List<Class<?>> classes = new ArrayList<>();
        String path = packageName.replace('.', '/');

        // ✅ ClassLoader du plugin (URLClassLoader de Bukkit), pas le ContextClassLoader
        ClassLoader classLoader = plugin.getClass().getClassLoader();

        Enumeration<URL> resources = classLoader.getResources(path);

        while (resources.hasMoreElements()) {
            URL resource = resources.nextElement();
            String protocol = resource.getProtocol();

            Bukkit.getLogger().info("[CommandManager] Protocole détecté : " + protocol + " | " + resource);

            // ✅ CAS JAR (production Bukkit)
            if (protocol.equals("jar")) {
                String jarPath = resource.getPath();
                // format : file:/path/to/plugin.jar!/fr/kenda/...
                jarPath = jarPath.substring(5, jarPath.indexOf("!"));

                try (JarFile jar = new JarFile(jarPath)) {
                    Enumeration<JarEntry> entries = jar.entries();

                    while (entries.hasMoreElements()) {
                        JarEntry entry = entries.nextElement();
                        String name = entry.getName();

                        if (name.startsWith(path) && name.endsWith(".class") && !name.contains("$")) {
                            String className = name.replace('/', '.').replace(".class", "");
                            try {
                                classes.add(Class.forName(className, true, classLoader));
                            } catch (ClassNotFoundException | NoClassDefFoundError e) {
                                Bukkit.getLogger().warning("[CommandManager] Impossible de charger : " + className);
                            }
                        }
                    }
                }

                // ✅ CAS IDE (développement local)
            } else if (protocol.equals("file")) {
                java.io.File dir = new java.io.File(resource.toURI());
                scanDirectory(dir, packageName, classLoader, classes);
            }
        }

        return classes;
    }

    // ✅ Scan récursif pour supporter les sous-packages en mode IDE
    private void scanDirectory(java.io.File dir, String packageName,
                               ClassLoader classLoader, List<Class<?>> classes) {
        if (!dir.exists() || !dir.isDirectory()) return;

        for (java.io.File file : Objects.requireNonNull(dir.listFiles())) {
            if (file.isDirectory()) {
                scanDirectory(file, packageName + "." + file.getName(), classLoader, classes);
            } else if (file.getName().endsWith(".class") && !file.getName().contains("$")) {
                String className = packageName + "." + file.getName().replace(".class", "");
                try {
                    classes.add(Class.forName(className, true, classLoader));
                } catch (ClassNotFoundException | NoClassDefFoundError e) {
                    Bukkit.getLogger().warning("[CommandManager] Impossible de charger : " + className);
                }
            }
        }
    }
}