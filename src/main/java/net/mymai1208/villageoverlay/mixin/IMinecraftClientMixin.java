package net.mymai1208.villageoverlay.mixin;

import net.minecraft.util.hit.HitResult;
import net.mymai1208.villageroverlay.VillagerStats;

import java.util.Optional;

public interface IMinecraftClientMixin {

    HitResult villagerOverlay$getCrossHairTarget();
}
