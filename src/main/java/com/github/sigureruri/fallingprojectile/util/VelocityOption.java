package com.github.sigureruri.fallingprojectile.util;

public record VelocityOption(float roll, float speed, float divergence) {
    // For Snowball, Egg, EnderPearl and so on
    public final static VelocityOption GENERIC = new VelocityOption(0.0f, 1.5f, 1.0f);

    public final static VelocityOption ARROW = new VelocityOption(0.0f, 3.0f, 1.0f);

    public final static VelocityOption POTION = new VelocityOption(-20.0F, 0.5F, 1.0F);

    public final static VelocityOption EXP_BOTTLE = new VelocityOption(-20.0F, 0.7F, 1.0F);
}
