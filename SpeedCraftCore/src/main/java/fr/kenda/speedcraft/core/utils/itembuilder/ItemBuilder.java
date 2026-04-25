package fr.kenda.speedcraft.core.utils.itembuilder;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder implements IItemBuilder {

    protected final ItemStack item;
    protected final ItemMeta meta;

    private SkullItemBuilder skullDelegate;

    public ItemBuilder(Material material) {
        this.item = new ItemStack(material);
        this.meta = item.getItemMeta();

        if (material == Material.PLAYER_HEAD) {
            this.skullDelegate = new SkullItemBuilder(this);
        }
    }

    // 🔥 FACTORY
    public static ItemBuilder of(Material material) {
        return new ItemBuilder(material);
    }

    // -------------------------
    // GENERAL METHODS
    // -------------------------

    public ItemBuilder name(String name) {
        meta.displayName(Component.text(name));
        return this;
    }

    public ItemBuilder amount(int amount) {
        item.setAmount(amount);
        return this;
    }

    public ItemBuilder lore(List<String> lines) {
        List<Component> lore = new ArrayList<>();
        lines.forEach(l -> lore.add(Component.text(l)));
        meta.lore(lore);
        return this;
    }

    public ItemBuilder glow() {
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        return this;
    }

    // -------------------------
    // SKULL ACCESS
    // -------------------------

    public SkullItemBuilder skull() {
        if (skullDelegate == null) {
            throw new IllegalStateException("Not a skull item!");
        }
        return skullDelegate;
    }

    // -------------------------
    // BUILD
    // -------------------------

    @Override
    public ItemStack build() {

        if (skullDelegate != null) {
            return skullDelegate.build();
        }

        item.setItemMeta(meta);
        return item;
    }
}