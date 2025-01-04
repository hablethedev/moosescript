package net.legitimoose.script.mixin;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.legitimoose.script.Moosescript;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyMapping.class)
public abstract class KeyMappingMixin {
  @Shadow
  public abstract String getName();

  // TODO(maxuser): Clicking the button in the Key Binds screen to reset all bindings does not
  // trigger setKey(...) below, but it seems it should since KeyBindsScreen.init() calls
  // `keyMapping.setKey(keyMapping.getDefaultKey())` for all key mappings. The workaround is to
  // restart Minecraft after clicking the "reset all" button so that Moosescript has up-to-date key
  // bindings.

  @Inject(
      at = @At("TAIL"),
      method =
          "<init>(Ljava/lang/String;Lcom/mojang/blaze3d/platform/InputConstants$Type;ILjava/lang/String;)V")
  public void init(
      String name, InputConstants.Type type, int keyCode, String category, CallbackInfo ci) {
    Moosescript.setKeyBind(name, type.getOrCreate(keyCode));
  }

  @Inject(at = @At("HEAD"), method = "setKey(Lcom/mojang/blaze3d/platform/InputConstants$Key;)V")
  public void setKey(InputConstants.Key key, CallbackInfo ci) {
    Moosescript.setKeyBind(this.getName(), key);
  }
}
