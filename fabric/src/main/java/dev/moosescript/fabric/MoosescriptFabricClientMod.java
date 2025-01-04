package dev.moosescript.fabric;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientChunkEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenKeyboardEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.Screen;
import dev.moosescript.common.Moosescript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class MoosescriptFabricClientMod implements ClientModInitializer {
  private static final Logger LOGGER = LoggerFactory.getLogger("MoosescriptFabricClientMod");

  @Override
  public void onInitializeClient() {
    LOGGER.info("(moosescript) Moosescript mod starting...");

    ClientChunkEvents.CHUNK_LOAD.register((world, chunk) -> Moosescript.onChunkLoad(world, chunk));
    ClientChunkEvents.CHUNK_UNLOAD.register(
        (world, chunk) -> Moosescript.onChunkUnload(world, chunk));

    Moosescript.init(new FabricPlatform());
    ClientTickEvents.START_WORLD_TICK.register(world -> Moosescript.onClientWorldTick());
    ScreenEvents.AFTER_INIT.register(this::afterInitScreen);
    WorldRenderEvents.END.register(this::onRender);
  }

  private void afterInitScreen(Minecraft client, Screen screen, int windowWidth, int windowHeight) {
    if (screen instanceof ChatScreen) {
      ScreenKeyboardEvents.allowKeyPress(screen)
          .register(
              (_screen, key, scancode, modifiers) ->
                  !Moosescript.onKeyboardKeyPressed(_screen, key));
    }
  }

  private void onRender(WorldRenderContext context) {
    Moosescript.onRenderWorld();
  }
}
