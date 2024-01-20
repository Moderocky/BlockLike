package mx.kenzie.blocklike.offer;

import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public abstract class ModifierProvider implements OfferProvider {

    @Override
    public void apply(LivingEntity entity, Offer offer) {
        final Attribute attribute = this.getAttribute();
        final AttributeModifier modifier = this.createModifier(offer);
        entity.registerAttribute(attribute);
        final AttributeInstance instance = entity.getAttribute(attribute);
        assert instance != null;
        instance.addTransientModifier(modifier);
    }

    @Override
    public void remove(LivingEntity entity, Offer offer) {
        final Attribute attribute = this.getAttribute();
        final AttributeInstance instance = entity.getAttribute(attribute);
        if (instance == null) return;
        instance.removeModifier(this.createUUID(offer));
    }

    @Override
    public @NotNull NamespacedKey getKey() {
        return null;
    }

    public abstract Attribute getAttribute();

    public abstract AttributeModifier createModifier(Offer offer);

    protected UUID createUUID(Offer offer) {
        return new UUID(offer.type().hashCode(), ((long) offer.level()) << 32 | (offer.rarity().ordinal() & 0xffffffffL));
    }

}
