package mx.kenzie.blocklike.basic;

import mx.kenzie.blocklike.offer.ModifierProvider;
import mx.kenzie.blocklike.offer.Offer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class OfferGenericAttackSpeed extends ModifierProvider {

    @Override
    public Attribute getAttribute() {
        return Attribute.GENERIC_ATTACK_SPEED;
    }

    @Override
    public AttributeModifier createModifier(Offer offer) {
        final double amount = 0.01 + (0.01 * offer.rarity().ordinal());
        return new AttributeModifier(this.createUUID(offer), this.name(), amount,
            AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public String name() {
        return "Hit Fast";
    }

}
