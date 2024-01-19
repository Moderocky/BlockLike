package mx.kenzie.blocklike;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public record RarityTable(float common, float rare, float epic) {

    public static final RarityTable STANDARD = RarityTable.of(79, 15, 5, 1);

    public static RarityTable of(int common, int rare, int epic, int legendary) {
        final float maximum = common + rare + epic + legendary;
        return new RarityTable(common / maximum, (common + rare) / maximum, (common + rare + epic) / maximum);
    }

    public Rarity choose(Random random) {
        final float guess = random.nextFloat();
        if (guess > epic) return Rarity.LEGENDARY;
        else if (guess > rare) return Rarity.EPIC;
        else if (guess > common) return Rarity.RARE;
        return Rarity.COMMON;
    }

    public Rarity choose() {
        return this.choose(ThreadLocalRandom.current());
    }

}
