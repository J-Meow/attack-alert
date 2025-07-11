package net.jmeow.attack_alert.client;

import com.mojang.authlib.GameProfile;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudLayerRegistrationCallback;
import net.fabricmc.fabric.api.client.rendering.v1.IdentifiedLayer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.util.Identifier;

import java.util.ArrayList;

public class Attack_alertClient implements ClientModInitializer {
    private static final Identifier ALERT_LAYER = Identifier.of("attack_alert", "attack-alert-layer");

    @Override
    public void onInitializeClient() {
        HudLayerRegistrationCallback.EVENT.register(layeredDrawer -> layeredDrawer.attachLayerBefore(IdentifiedLayer.CHAT, ALERT_LAYER, Attack_alertClient::render));
    }

    private static void render(DrawContext context, RenderTickCounter tickCounter) {
        if(!attackers.isEmpty()) {
            context.fill(0, 0, 10, 10, 0, 0xFFFF0055);
        }
    }

    public static ArrayList<GameProfile> attackers = new ArrayList<>();
}
