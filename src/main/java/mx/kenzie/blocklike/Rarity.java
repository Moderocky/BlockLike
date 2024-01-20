package mx.kenzie.blocklike;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentLike;
import net.kyori.adventure.text.format.TextColor;
import org.jetbrains.annotations.NotNull;

public enum Rarity implements ComponentLike {

    COMMON(TextColor.color(194, 194, 194)),
    RARE(TextColor.color(0, 170, 255)),
    EPIC(TextColor.color(174, 0, 255)),
    LEGENDARY(TextColor.color(255, 60, 0));
    public final TextColor color;

    Rarity(TextColor color) {
        this.color = color;
    }

    public TextColor color() {
        return color;
    }

    @Override
    public @NotNull Component asComponent() {
        final String name = this.name().toLowerCase().replace('_', ' ');
        return Component.translatable("rarity." + name, name.substring(0, 1).toUpperCase() + name.substring(1), color);
    }
}
