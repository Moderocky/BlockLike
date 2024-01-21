package mx.kenzie.blocklike.offer;

import mx.kenzie.blocklike.Rarity;
import org.bukkit.Keyed;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface OfferProvider extends Keyed {

    static OfferProvider of(Plugin plugin, String name, OfferProvider.Simple simple) {
        return new FunctionalProvider(name, plugin, new NamespacedKey(plugin, name.replace(' ', '_').toLowerCase()), simple);
    }

    default Offer create(int level, Rarity rarity) {
        return new Offer(level, rarity, this);
    }

    void apply(LivingEntity entity, Offer offer);

    void remove(LivingEntity entity, Offer offer);

    String name();

    default boolean allowedAt(Rarity rarity) {
        return true;
    }

    default boolean compatibleWith(OfferProvider provider) {
        return true;
    }

    default @Override
    @NotNull NamespacedKey getKey() {
        return new NamespacedKey("blocklike", this.name().replace(' ', '_').toLowerCase());
    }

    Plugin plugin();

    @FunctionalInterface
    interface Simple {

        void apply(LivingEntity entity, Offer offer);

        default void remove(LivingEntity entity, Offer offer) {
        }

    }

}

record FunctionalProvider(String name, Plugin plugin, NamespacedKey getKey, OfferProvider.Simple simple) implements OfferProvider {

    public FunctionalProvider(String name, Plugin plugin, Simple simple) {
        this(name, plugin, new NamespacedKey("blocklike", name.replace(' ', '_').toLowerCase()), simple);
    }

    @Override
    public void apply(LivingEntity entity, Offer offer) {
        this.simple.apply(entity, offer);
    }

    @Override
    public void remove(LivingEntity entity, Offer offer) {
        this.simple.remove(entity, offer);
    }

}
