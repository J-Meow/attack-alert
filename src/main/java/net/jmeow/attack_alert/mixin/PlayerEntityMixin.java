package net.jmeow.attack_alert.mixin;

import com.mojang.authlib.GameProfile;
import net.jmeow.attack_alert.client.Attack_alertClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
    @Shadow public abstract GameProfile getGameProfile();

    @Shadow private ItemStack selectedItem;

    @Inject(at = @At("HEAD"), method = "tick()V")
    private void tick(CallbackInfo info) {
        boolean isAttacker = false;
        //noinspection ConstantValue
        if(!this.getGameProfile().equals(MinecraftClient.getInstance().getGameProfile())) {
            if(((Entity)(Object)this).isInRange(MinecraftClient.getInstance().player, 20.0)) {
                if (this.selectedItem.getItem().getTranslationKey().endsWith("_sword")) {
                    isAttacker = true;
                    MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(this.getGameProfile().getName() + " nearby, holding sword"), true);
                }
                if (this.selectedItem.getItem().getTranslationKey().endsWith("_axe")) {
                    isAttacker = true;
                    MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(this.getGameProfile().getName() + " nearby, holding axe"), true);
                }
            }
            if(((Entity)(Object)this).isInRange(MinecraftClient.getInstance().player, 60.0)) {
                if (this.selectedItem.getItem().getTranslationKey().endsWith("bow")) {
                    isAttacker = true;
                    MinecraftClient.getInstance().getMessageHandler().onGameMessage(Text.of(this.getGameProfile().getName() + " <60 blocks away, holding bow"), true);
                }
            }
        }
        if(isAttacker) {
            if(!Attack_alertClient.attackers.contains(this.getGameProfile())) {
                Attack_alertClient.attackers.add(this.getGameProfile());
            }
        } else {
            Attack_alertClient.attackers.remove(this.getGameProfile());
        }
    }
}
