package net.mymai1208.villageoverlay.mixin;

import net.minecraft.util.hit.HitResult;

public interface IMinecraftClientMixin {

    HitResult villagerOverlay$getCrossHairTarget();
}
