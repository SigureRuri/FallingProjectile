package com.github.sigureruri.fallingprojectile;

import com.github.sigureruri.fallingprojectile.listener.ProjectileListener;
import com.github.sigureruri.fallingprojectile.projectile.FPProjectile;
import com.github.sigureruri.fallingprojectile.registry.FPRegistry;
import org.bukkit.plugin.java.JavaPlugin;

public final class FallingProjectile extends JavaPlugin {

    public static FPRegistry<String, FPProjectile> PROJECTILE_REGISTRY = new FPRegistry<>();

    private static FallingProjectile instance;

    public static FallingProjectile getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getServer().getPluginManager().registerEvents(new ProjectileListener(), this);
    }
}
