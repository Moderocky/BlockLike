package mx.kenzie.blocklike;

import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public class BlockLike extends JavaPlugin implements ChoiceService {

    public static final NamespacedKey STORAGE_KEY = new NamespacedKey("blocklike", "storage");

    @Override
    public void onDisable() {
        Bukkit.getServicesManager().unregister(this);
        super.onDisable();
    }

    @Override
    public void onEnable() {
        Bukkit.getServicesManager().register(ChoiceService.class, this, this, ServicePriority.Normal);
        super.onEnable();
    }

}
