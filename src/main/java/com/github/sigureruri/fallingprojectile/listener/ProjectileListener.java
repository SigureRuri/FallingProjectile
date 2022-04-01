package com.github.sigureruri.fallingprojectile.listener;

import com.github.sigureruri.fallingprojectile.FallingProjectile;
import com.github.sigureruri.fallingprojectile.projectile.FPProjectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;

public class ProjectileListener implements Listener {
    @EventHandler
    public void onProjectileLaunch(ProjectileLaunchEvent event) {
        var projectile = event.getEntity();
        var projectileKey = FPProjectile.getRegistryKeyFromProjectileEntity(projectile);
        if (projectileKey == null) return;
        var fpProjectile = FallingProjectile.PROJECTILE_REGISTRY.getFromKey(projectileKey);
        if (fpProjectile == null) return;

        fpProjectile.onLaunch(projectile);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        var projectile = event.getEntity();
        var projectileKey = FPProjectile.getRegistryKeyFromProjectileEntity(projectile);
        if (projectileKey == null) return;
        var fpProjectile = FallingProjectile.PROJECTILE_REGISTRY.getFromKey(projectileKey);
        if (fpProjectile == null) return;

        fpProjectile.onHit(projectile, event.getHitEntity(), event.getHitBlock(), event.getHitBlockFace());
    }
}
