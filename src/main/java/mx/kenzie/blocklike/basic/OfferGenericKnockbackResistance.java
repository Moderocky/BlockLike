package mx.kenzie.blocklike.basic;

import mx.kenzie.blocklike.offer.ModifierProvider;
import mx.kenzie.blocklike.offer.Offer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class OfferGenericKnockbackResistance extends ModifierProvider {

    @Override
    public Attribute getAttribute() {
        return Attribute.GENERIC_KNOCKBACK_RESISTANCE;
    }

    @Override
    public AttributeModifier createModifier(Offer offer) {
        final double amount = 0.06 * (offer.rarity().ordinal() + 1);
        return new AttributeModifier(this.createUUID(offer), this.name(), amount,
            AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public String name() {
        return "Brace Yourself";
    }

}
