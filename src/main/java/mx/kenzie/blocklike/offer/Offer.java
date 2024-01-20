package mx.kenzie.blocklike.offer;

import mx.kenzie.blocklike.ChoiceService;
import mx.kenzie.blocklike.Rarity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import org.bukkit.Keyed;
import org.bukkit.Nameable;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public record Offer(int level, Rarity rarity, OfferProvider type) implements Keyed, Nameable, ComponentLike {

    private static final NamespacedKey LEVEL = new NamespacedKey("offer", "level"),
        RARITY = new NamespacedKey("offer", "rarity"),
        PROVIDER = new NamespacedKey("offer", "type");

    public static void store(ChoiceService service, Offer offer, PersistentDataContainer container) {
        final int level = offer.level(), rarity = offer.rarity().ordinal();
        final String provider = offer.getKey().asString();
        container.set(LEVEL, PersistentDataType.INTEGER, level);
        container.set(RARITY, PersistentDataType.INTEGER, rarity);
        container.set(PROVIDER, PersistentDataType.STRING, provider);
    }

    public static Offer read(ChoiceService service, PersistentDataContainer container) {
        final int level = container.getOrDefault(LEVEL, PersistentDataType.INTEGER, 1);
        final int rarity = container.getOrDefault(RARITY, PersistentDataType.INTEGER, 0);
        final String string = container.get(PROVIDER, PersistentDataType.STRING);
        if (string == null) return null;
        final NamespacedKey key = NamespacedKey.fromString(string);
        final OfferProvider provider = service.getProvider(key);
        if (provider == null) return null;
        return new Offer(level, Rarity.values()[rarity], provider);
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return type.getKey();
    }

    @Override
    public @NotNull Component customName() {
        return this.asComponent();
    }

    @Override
    public void customName(@Nullable Component customName) {
    }

    @Override
    public @Nullable String getCustomName() {
        return type.name();
    }

    @Override
    public void setCustomName(@Nullable String name) {
    }

    @Override
    public @NotNull Component asComponent() {
        return Component.translatable(type.getKey().toString(), type.name(), rarity.color());
    }

}
