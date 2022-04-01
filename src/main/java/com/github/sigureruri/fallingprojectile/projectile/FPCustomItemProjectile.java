package com.github.sigureruri.fallingprojectile.projectile;

import org.bukkit.Material;
import org.bukkit.entity.Projectile;
import org.bukkit.entity.Snowball;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FPCustomItemProjectile extends FPProjectile {
    final ItemStack displayedItem;

    public FPCustomItemProjectile(@NotNull ItemStack displayedItem) {
        super(Snowball.class);
        this.displayedItem = displayedItem.clone();
    }

    public FPCustomItemProjectile(@NotNull Material material) {
        this(new ItemStack(material));
    }

    @Override
    public void onLaunch(@NotNull Projectile projectile) {
        var snowball = (Snowball) projectile;
        snowball.setItem(displayedItem);
    }
}
