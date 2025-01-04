package net.legitimoose.script.mixin;

import net.minecraft.client.MouseHandler;
import net.legitimoose.script.Moosescript;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
  private static final Logger LOGGER = LoggerFactory.getLogger("MouseHandlerMixin");

  @Shadow
  public abstract double xpos();

  @Shadow
  public abstract double ypos();

  @Inject(at = @At("HEAD"), method = "onPress(JIII)V", cancellable = false)
  private void onPress(long window, int button, int action, int modifiers, CallbackInfo ci) {
    Moosescript.onMouseClick(button, action, modifiers, this.xpos(), this.ypos());
  }
}
