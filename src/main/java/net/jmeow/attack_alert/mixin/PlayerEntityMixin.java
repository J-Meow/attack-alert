package net.jmeow.attack_alert.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Objects;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract GameProfile getGameProfile();

    @Shadow private ItemStack selectedItem;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void init(CallbackInfo info) {
        if(!this.getGameProfile().equals(MinecraftClient.getInstance().getGameProfile()) && this.selectedItem.getItem().getTranslationKey().endsWith("_sword")) {
            System.out.println(this.getGameProfile().getName() + " nearby, holding sword");
        }
    }
}
