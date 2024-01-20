package mx.kenzie.blocklike;

import mx.kenzie.blocklike.offer.OfferProvider;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.persistence.ListPersistentDataType;
import org.bukkit.persistence.PersistentDataContainer;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public interface ChoiceService {

    void register(OfferProvider provider);

    void unregister(OfferProvider provider);

    Iterable<OfferProvider> providers();

    OfferProvider getProvider(NamespacedKey key);

    boolean hasProvider(NamespacedKey key);

    void reset(LivingEntity entity);

    boolean levelUp(LivingEntity entity, int level);

    int maxKnownLevel(LivingEntity entity);

    void offer(LivingEntity entity);

    default Collection<PersistentDataContainer> getStorage(LivingEntity entity) {
        final PersistentDataContainer container = entity.getPersistentDataContainer();
        return container.getOrDefault(BlockLike.STORAGE_KEY, ListPersistentDataType.LIST.dataContainers(), Collections.emptyList());
    }

    default void setStorage(LivingEntity entity, List<PersistentDataContainer> data) {
        final PersistentDataContainer container = entity.getPersistentDataContainer();
        container.set(BlockLike.STORAGE_KEY, ListPersistentDataType.LIST.dataContainers(), data);
    }

    default RarityTable rarityTable() {
        return RarityTable.STANDARD;
    }

}
