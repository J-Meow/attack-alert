package net.jmeow.attack_alert.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Inject(at = @At("HEAD"), method = "isGlowing", cancellable = true)
    private void isGlowing(CallbackInfoReturnable<Boolean> cir) {
        if((Entity)(Object)this instanceof PlayerEntity) {
            if(!((PlayerEntity)(Object)this).getGameProfile().equals(MinecraftClient.getInstance().getGameProfile())) {
                if(((Entity)(Object)this).isInRange(MinecraftClient.getInstance().player, 20.0)) {
                    if (((PlayerEntity)(Object)this).getHandItems().iterator().next().getItem().getTranslationKey().endsWith("_sword")) {
                        cir.setReturnValue(true);
                    }
                    if (((PlayerEntity)(Object)this).getHandItems().iterator().next().getItem().getTranslationKey().endsWith("_axe")) {
                        cir.setReturnValue(true);
                    }
                }
                if(((Entity)(Object)this).isInRange(MinecraftClient.getInstance().player, 60.0)) {
                    if (((PlayerEntity)(Object)this).getHandItems().iterator().next().getItem().getTranslationKey().endsWith("bow")) {
                        cir.setReturnValue(true);
                    }
                }
            }
        }
    }
}
