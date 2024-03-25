package net.mymai1208.villageroverlay.mixin.impl;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.util.hit.EntityHitResult;
import net.mymai1208.villageroverlay.VillagerOverlay;
import net.mymai1208.villageroverlay.mixin.IMinecraftClientMixin;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Shadow @Final private MinecraftClient client;
    @Inject(method = "onSetTradeOffers", at = @At(value = "INVOKE", target = "Lnet/minecraft/screen/MerchantScreenHandler;setCanRefreshTrades(Z)V"))
    public void onSetTradeOffers(SetTradeOffersS2CPacket packet, CallbackInfo ci) {
        if(packet.getSyncId() != client.player.currentScreenHandler.syncId) {
            return;
        }

        IMinecraftClientMixin mcClient = (IMinecraftClientMixin)client;

        if(!(mcClient.villagerOverlay$getCrossHairTarget() instanceof EntityHitResult entityHitResult)) {
            return;
        }

        if(!(entityHitResult.getEntity() instanceof VillagerEntity villagerEntity)) {
            return;
        }

        if(!VillagerOverlay.getOpenedVillagers().contains(villagerEntity.getUuid())) {
            //サーバー側の交易情報をクライアント内部と同期
            villagerEntity.setOffers(packet.getOffers());

            VillagerOverlay.getOpenedVillagers().add(villagerEntity.getUuid());

            if(VillagerOverlay.getCurrentOpenVillager() != null) {
                VillagerOverlay.setCurrentOpenVillager(null);
                client.player.closeHandledScreen();
            }
        }
    }
}
