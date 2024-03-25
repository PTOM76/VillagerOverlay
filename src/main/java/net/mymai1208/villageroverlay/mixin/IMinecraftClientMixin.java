package net.mymai1208.villageroverlay.mixin;

import net.minecraft.util.hit.HitResult;

public interface IMinecraftClientMixin {

    HitResult villagerOverlay$getCrossHairTarget();
}
