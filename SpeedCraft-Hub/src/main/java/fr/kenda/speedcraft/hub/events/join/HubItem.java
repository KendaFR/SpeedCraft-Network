package fr.kenda.speedcraft.hub.events.join;

import fr.kenda.speedcraft.core.utils.itembuilder.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public record HubItem(String name, List<String> lore, Material material, int slot, Consumer<Player> action) {

    public HubItem(String name, List<String> lore, Material material, int slot, Consumer<Player> action) {
        this.name = name != null ? name : " ";
        this.lore = lore != null ? lore : List.of();
        this.material = material;
        this.slot = slot;
        this.action = action;
    }

    // =========================
    // PLACEHOLDER CORE
    // =========================
    private String apply(String text, Map<String, String> placeholders) {
        if (text == null) return " ";

        for (var entry : placeholders.entrySet()) {
            text = text.replace(entry.getKey(), entry.getValue());
        }

        return text;
    }

    private List<String> applyLore(Map<String, String> placeholders) {
        return lore.stream()
                .map(line -> apply(line, placeholders))
                .toList();
    }

    // =========================
    // BUILD WITHOUT PLACEHOLDERS
    // =========================
    public ItemStack build(Player player) {
        return build(player, Map.of());
    }

    // =========================
    // BUILD WITH PLACEHOLDERS
    // =========================
    public ItemStack build(Player player, Map<String, String> placeholders) {

        String finalName = apply(name, placeholders);
        List<String> finalLore = applyLore(placeholders);

        ItemBuilder builder = new ItemBuilder(material)
                .name(finalName)
                .lore(finalLore);

        if (material == Material.PLAYER_HEAD) {
            return builder
                    .skull()
                    .owner(player)
                    .build();
        }

        return builder.build();
    }
}