package dev.moosescript.common.mixin;

import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.ChatScreen;
import dev.moosescript.common.Moosescript;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChatScreen.class)
public class ChatScreenMixin {
  @Shadow protected EditBox input;

  @Inject(at = @At("TAIL"), method = "init()V")
  protected void init(CallbackInfo ci) {
    Moosescript.setChatScreenInput(this.input);
  }
}
