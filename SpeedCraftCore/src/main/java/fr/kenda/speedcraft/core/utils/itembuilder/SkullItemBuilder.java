package fr.kenda.speedcraft.core.utils.itembuilder;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
public class SkullItemBuilder implements IItemBuilder {

    private final ItemBuilder parent;

    private Player owner;
    private String name;
    private List<Component> lore = new ArrayList<>();

    public SkullItemBuilder(ItemBuilder parent) {
        this.parent = parent;
    }

    public SkullItemBuilder owner(Player player) {
        this.owner = player;
        return this;
    }

    public SkullItemBuilder owner(String name) {
        this.owner = Bukkit.getOfflinePlayer(name).getPlayer();
        return this;
    }

    public SkullItemBuilder name(String name) {
        this.name = name;
        return this;
    }

    public SkullItemBuilder lore(List<String> lines) {
        lore.clear();
        lines.forEach(l -> lore.add(Component.text(l)));
        return this;
    }

    public SkullItemBuilder addLine(String line) {
        lore.add(Component.text(line));
        return this;
    }

    @Override
    public ItemStack build() {

        ItemStack skull = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) skull.getItemMeta();

        if (meta == null) return skull;

        if (owner != null) meta.setOwningPlayer(owner);
        if (name != null) meta.displayName(Component.text(name));
        if (!lore.isEmpty()) meta.lore(lore);

        skull.setItemMeta(meta);
        return skull;
    }
}