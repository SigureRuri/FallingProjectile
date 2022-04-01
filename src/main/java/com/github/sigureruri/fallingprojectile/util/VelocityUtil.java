package com.github.sigureruri.fallingprojectile.util;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Projectile;
import org.bukkit.util.Vector;

import java.util.Random;

public class VelocityUtil {
    private VelocityUtil() {}

    private static final Random random = new Random();

    public static void setVelocityFromDirection(Projectile projectile, double x, double y, double z, float speed, float divergence) {
        var vector = new Vector(x, y, z)
                .normalize()
                .add(new Vector(
                        random.nextGaussian() * (double)0.0075f * (double)divergence,
                        random.nextGaussian() * (double)0.0075f * (double)divergence,
                        random.nextGaussian() * (double)0.0075f * (double)divergence))
                .multiply(speed);
        projectile.setVelocity(vector);
        double d = horizontalDistance(vector);
        projectile.getLocation().setYaw((float) (Math.atan2(vector.getX(), vector.getZ()) * 57.2957763671875));
        projectile.getLocation().setYaw((float) (Math.atan2(vector.getY(), d) * 57.2957763671875));
    }

    public static void setVelocityFromRotation(Projectile projectile, LivingEntity shooter, VelocityOption velocityOption) {
        var location = shooter.getEyeLocation();
        var yaw = location.getYaw();
        var pitch = location.getPitch();
        var f = (float) (-Math.sin(yaw * ((float) Math.PI / 180)) * Math.cos(pitch * ((float) Math.PI / 180)));
        var g = (float) -Math.sin((pitch + velocityOption.roll()) * ((float)Math.PI / 180));
        var h = (float) (Math.cos(yaw * ((float)Math.PI / 180)) * Math.cos(pitch * ((float)Math.PI / 180)));
        setVelocityFromDirection(projectile, f, g, h, velocityOption.speed(), velocityOption.divergence());
        var shooterVelocity = shooter.getVelocity();
        projectile.setVelocity(projectile.getVelocity().add(new Vector(shooterVelocity.getX(), shooter.isOnGround() ? 0.0 : shooterVelocity.getY(), shooterVelocity.getZ())));
    }

    private static double horizontalDistance(Vector vector) {
        return Math.sqrt(vector.getX() * vector.getX() + vector.getZ() * vector.getZ());
    }
}
