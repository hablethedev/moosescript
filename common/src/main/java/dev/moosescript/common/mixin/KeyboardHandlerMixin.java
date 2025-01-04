package dev.moosescript.common.mixin;

import static dev.moosescript.common.Moosescript.ENTER_KEY;
import static dev.moosescript.common.Moosescript.config;

import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.Minecraft;
import dev.moosescript.common.Moosescript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {
  private static final Logger LOGGER = LoggerFactory.getLogger("KeyboardHandlerMixin");

  private static int KEY_ACTION_DOWN = 1;
  private static int KEY_ACTION_REPEAT = 2;
  private static int KEY_ACTION_UP = 0;

  @Inject(at = @At("HEAD"), method = "keyPress(JIIII)V", cancellable = true)
  private void keyPress(
      long window, int key, int scanCode, int action, int modifiers, CallbackInfo ci) {
    Moosescript.onKeyboardEvent(key, scanCode, action, modifiers);
    var screen = Minecraft.getInstance().screen;
    if (screen == null) {
      Moosescript.onKeyInput(key);
    } else if ((key == ENTER_KEY || key == config.secondaryEnterKeyCode())
        && action == KEY_ACTION_DOWN
        && Moosescript.onKeyboardKeyPressed(screen, key)) {
      ci.cancel();
    }
  }
}
