package com.github.sigureruri.fallingprojectile.projectile;

import com.github.sigureruri.fallingprojectile.FallingProjectile;
import com.github.sigureruri.fallingprojectile.util.VelocityOption;
import com.github.sigureruri.fallingprojectile.util.VelocityUtil;
import com.google.common.base.Preconditions;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FPProjectile {
    public final static NamespacedKey NBT_KEY = new NamespacedKey(FallingProjectile.getInstance(), "key");

    private final Class<? extends Projectile> projectileType;

    public FPProjectile(Class<? extends Projectile> projectileType) {
        this.projectileType = projectileType;
    }

    /**
     * `source`に指定した {@link LivingEntity} の向きを元として、投擲物を発射する。
     * 投擲物の所有者は指定した`source`になる。
     *
     * @param source この {@link FPProjectile} を発射するエンティティ
     */
    public final void launch(@NotNull LivingEntity source) {
        launch(source, VelocityOption.GENERIC, true);
    }

    /**
     * `source`に指定した {@link LivingEntity} の向きを元として、投擲物を発射する。
     *
     * @see VelocityOption
     *
     * @param source この {@link FPProjectile} を発射するエンティティ
     * @param velocity この {@link FPProjectile} を発射する際の速度
     * @param setAsShooter この {@link FPProjectile} の所有者を`source`に設定するかどうか
     */
    public final void launch(@NotNull LivingEntity source, @NotNull VelocityOption velocity, boolean setAsShooter) {
        var location = source.getEyeLocation();
        Preconditions.checkArgument(location.getWorld() != null, "world is not loaded");

        location.getWorld().spawn(location, projectileType, false, projectile -> {
            if (setAsShooter) {
                projectile.setShooter(source);
            }
            VelocityUtil.setVelocityFromRotation(projectile, source, velocity);

            applyPersistenceData(projectile);
            startWatchingProjectileEntity(projectile);
        });
    }

    /**
     * `source`に指定した {@link LivingEntity} の目の位置の座標を元にして、投擲物を発射する
     * 投擲物の所有者は指定した`source`になる。
     *
     * @param source この {@link FPProjectile} を発射するエンティティ
     * @param vector この {@link FPProjectile} を発射する際のベクトル
     */
    public final void launch(@NotNull LivingEntity source, @Nullable Vector vector) {
        launch(source.getEyeLocation(), source, vector);
    }

    /**
     * `location`に指定した位置を元として、投擲物を発射する
     *
     * @param location この {@link FPProjectile} を発射する位置
     * @param source この {@link FPProjectile} を発射するエンティティ
     * @param vector この {@link FPProjectile} を発射する際のベクトル
     */
    public final void launch(@NotNull Location location, @Nullable LivingEntity source, @Nullable Vector vector) {
        Preconditions.checkArgument(location.getWorld() != null, "world is not loaded");

        location.getWorld().spawn(location, projectileType, false, projectile -> {
            projectile.setShooter(source);
            projectile.setVelocity(vector == null ? projectile.getVelocity() : vector);

            applyPersistenceData(projectile);
            startWatchingProjectileEntity(projectile);
        });
    }

    public void onLaunch(@NotNull Projectile projectile) {}

    public void onHit(@NotNull Projectile projectile, @Nullable Entity hitEntity, @Nullable Block hitBlock, @Nullable BlockFace hitFace) {}

    public void onTick(@NotNull Projectile projectile) {}

    private void applyPersistenceData(Projectile projectile) {
        var dataContainer = projectile.getPersistentDataContainer();
        var key = FallingProjectile.PROJECTILE_REGISTRY.getFromValue(this);

        if (key == null) throw new IllegalArgumentException("This projectile is not registered to registry!");

        dataContainer.set(NBT_KEY, PersistentDataType.STRING, key);
    }

    private void startWatchingProjectileEntity(Projectile projectile) {
        FallingProjectile.getInstance().getServer().getScheduler().runTaskTimer(
                FallingProjectile.getInstance(),
                task -> {
                    if (projectile.isDead() || !projectile.isValid()) {
                        task.cancel();
                    } else {
                        onTick(projectile);
                    }
                },
                1, 1
        );
    }

    @Nullable
    public static String getRegistryKeyFromProjectileEntity(Projectile projectile) {
        try {
            return projectile.getPersistentDataContainer().get(NBT_KEY, PersistentDataType.STRING);
        } catch (Exception e) {
            return null;
        }
    }
}
