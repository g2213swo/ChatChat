package at.helpch.chatchat;

import at.helpch.chatchat.config.ConfigManager;
import at.helpch.chatchat.listener.ChatListener;
import at.helpch.chatchat.listener.PlayerListener;
import at.helpch.chatchat.user.UsersHolder;
import dev.triumphteam.annotations.BukkitMain;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@BukkitMain
public final class ChatChatPlugin extends JavaPlugin {

    private @NotNull final ConfigManager configManager = new ConfigManager(this.getDataFolder().toPath());
    private @NotNull final UsersHolder usersHolder = new UsersHolder();
    private BukkitAudiences audiences;

    @Override
    public void onEnable() {
        configManager.reload();
        audiences = BukkitAudiences.create(this);
        List.of(
                new PlayerListener(usersHolder),
                new ChatListener(this)
        ).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    @Override
    public void onDisable() {
        audiences.close();
    }

    public @NotNull ConfigManager configManager() {
        return configManager;
    }

    public @NotNull UsersHolder usersHolder() {
        return usersHolder;
    }

    public @NotNull BukkitAudiences audiences() {
        return audiences;
    }
}
