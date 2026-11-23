package cult.illusion.mixin;

import cult.illusion.BladeCeremony;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.CharacterEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Версия наблюдателя: клавиш церемонии и ритуала нет, остался только обработчик ввода символа (ничего не отбрасывает, пока нет перехвата).
 */
@Mixin(KeyboardHandler.class)
public abstract class KeyboardHandlerMixin {
    @Inject(method = "charTyped", at = @At("HEAD"), cancellable = true)
    private void cult_illusion$ceremonyChar(long window, CharacterEvent event, CallbackInfo ci) {
        if (BladeCeremony.swallowChar()) {
            ci.cancel();
        }
    }
}
