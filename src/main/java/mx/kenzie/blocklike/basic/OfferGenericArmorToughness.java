package mx.kenzie.blocklike.basic;

import mx.kenzie.blocklike.offer.ModifierProvider;
import mx.kenzie.blocklike.offer.Offer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;

public class OfferGenericArmorToughness extends ModifierProvider {

    @Override
    public Attribute getAttribute() {
        return Attribute.GENERIC_ARMOR_TOUGHNESS;
    }

    @Override
    public AttributeModifier createModifier(Offer offer) {
        final double amount = 0.5 + offer.rarity().ordinal();
        return new AttributeModifier(this.createUUID(offer), this.name(), amount,
            AttributeModifier.Operation.ADD_NUMBER);
    }

    @Override
    public String name() {
        return "Walking Fortress";
    }

}
