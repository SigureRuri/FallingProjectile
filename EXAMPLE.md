```java
package com.github.sigureruri.fallingprojectile;

import com.github.sigureruri.fallingprojectile.projectile.FPCustomItemProjectile;
import com.github.sigureruri.fallingprojectile.projectile.FPProjectile;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DebugClass implements Listener {

    public static FPProjectile WITHER_SKULL = new FPProjectile(WitherSkull.class) {
        @Override
        public void onHit(@NotNull Projectile projectile, @Nullable Entity hitEntity, @Nullable Block hitBlock, @Nullable BlockFace hitFace) {
            projectile.getWorld().spawnEntity(projectile.getLocation(), EntityType.PRIMED_TNT);
        }
    };

    public static FPCustomItemProjectile FLYING_SLIME_BALL = new FPCustomItemProjectile(Material.SLIME_BALL) {
        @Override
        public void onLaunch(@NotNull Projectile projectile) {
            super.onLaunch(projectile);
            
            if (projectile.getShooter() == null || !(projectile.getShooter() instanceof Player player)) return;

            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_PLACE, 1.0f, 1.0f);
        }

        @Override
        public void onTick(@NotNull Projectile projectile) {
            projectile.getWorld().spawnParticle(Particle.VILLAGER_HAPPY, projectile.getLocation(), 5, 0.1, 0.1, 0.1, 1);
        }

        @Override
        public void onHit(@NotNull Projectile projectile, @Nullable Entity hitEntity, @Nullable Block hitBlock, @Nullable BlockFace hitFace) {
            projectile.getWorld().spawnParticle(Particle.CLOUD, projectile.getLocation(), 100, 1.0, 1.0, 1.0, 1);
        }
    };

    static {
        FallingProjectile.PROJECTILE_REGISTRY.register("wither_skull", WITHER_SKULL);
        FallingProjectile.PROJECTILE_REGISTRY.register("flying_slime_ball", FLYING_SLIME_BALL);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent ev) {
        if (ev.getItem() == null) return;
        var item = ev.getItem();

        switch (item.getType()) {
            case STICK -> WITHER_SKULL.launch(ev.getPlayer());
            case CARROT_ON_A_STICK -> FLYING_SLIME_BALL.launch(ev.getPlayer());
        }
    }

}

```