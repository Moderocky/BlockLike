package mx.kenzie.blocklike.offer;

import mx.kenzie.blocklike.Rarity;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public record Offer(int level, Rarity rarity, NamespacedKey type) implements Keyed {

    private static final NamespacedKey LEVEL = new NamespacedKey("offer", "level"),
        RARITY = new NamespacedKey("offer", "rarity"),
        PROVIDER = new NamespacedKey("offer", "type");

    @Override
    public @NotNull NamespacedKey getKey() {
        return type;
    }

    public static void store(Offer offer, PersistentDataContainer container) {
        final int level = offer.level(), rarity = offer.rarity().ordinal();
        final String provider = offer.type().asString();
        container.set(LEVEL, PersistentDataType.INTEGER, level);
        container.set(RARITY, PersistentDataType.INTEGER, rarity);
        container.set(PROVIDER, PersistentDataType.STRING, provider);
    }

    public static Offer read(PersistentDataContainer container) {
        final int level = container.getOrDefault(LEVEL, PersistentDataType.INTEGER, 1);
        final int rarity = container.getOrDefault(RARITY, PersistentDataType.INTEGER, 0);
        final String string = container.get(PROVIDER, PersistentDataType.STRING);
        if (string == null) return null;
        final NamespacedKey provider = NamespacedKey.fromString(string);
        return new Offer(level, Rarity.values()[rarity], provider);
    }

}
