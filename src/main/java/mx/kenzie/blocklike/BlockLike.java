package mx.kenzie.blocklike;

import mx.kenzie.blocklike.offer.Offer;
import mx.kenzie.blocklike.offer.OfferProvider;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class BlockLike extends JavaPlugin implements ChoiceService {

    public static final NamespacedKey STORAGE_KEY = new NamespacedKey("blocklike", "storage");
    protected boolean active;

    @Override
    public void onDisable() {
        this.active = false;
        this.providers.clear();
        Bukkit.getServicesManager().unregister(this);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        this.providers = new HashMap<>();
        this.active = true;
        Bukkit.getServicesManager().register(ChoiceService.class, this, this, ServicePriority.Normal);
        super.onEnable();
    }

    protected Map<NamespacedKey, OfferProvider> providers;

    @Override
    public void register(OfferProvider provider) {
        this.providers.put(provider.getKey(), provider);
        if (provider instanceof Listener listener)
            Bukkit.getPluginManager().registerEvents(listener, provider.plugin());
        if (this.active) this.recalculateAll();
    }

    @Override
    public void unregister(OfferProvider provider) {
        this.providers.remove(provider.getKey(), provider);
        if (provider instanceof Listener listener) HandlerList.unregisterAll(listener);
        if (this.active) this.recalculateAll();
    }

    @Override
    public Iterable<OfferProvider> providers() {
        return providers.values();
    }

    @Override
    public OfferProvider getProvider(NamespacedKey key) {
        return providers.get(key);
    }

    @Override
    public boolean hasProvider(NamespacedKey key) {
        return providers.containsKey(key);
    }

    @Override
    public void reset(LivingEntity entity) {

    }

    @Override
    public boolean levelUp(LivingEntity entity, int level) {
        return false;
    }

    @Override
    public int maxKnownLevel(LivingEntity entity) {
        return 0;
    }

    @Override
    public void offer(LivingEntity entity) {

    }

    @Override
    public void addOffer(LivingEntity entity, Offer offer) {
        final List<Offer> offers = this.getOffers(entity);
        offers.add(offer);
    }

    @Override
    public List<Offer> getOffers(LivingEntity entity) {
        final Collection<PersistentDataContainer> storage = this.getStorage(entity);
        final List<Offer> list = new ArrayList<>(storage.size());
        for (PersistentDataContainer container : storage) {
            final Offer offer = Offer.read(this, container);
            if (offer == null) continue;
            list.add(offer);
        }
        return list;
    }

    @Override
    public void setOffers(LivingEntity entity, Collection<Offer> offers) {
        final List<PersistentDataContainer> list = new ArrayList<>(offers.size());
        final PersistentDataAdapterContext context = entity.getPersistentDataContainer().getAdapterContext();
        for (Offer offer : offers) {
            final PersistentDataContainer container = context.newPersistentDataContainer();
            Offer.store(this, offer, container);
            list.add(container);
        }
        this.setStorage(entity, list);
    }

    @Override
    public void recalculateAll() {

    }

}
