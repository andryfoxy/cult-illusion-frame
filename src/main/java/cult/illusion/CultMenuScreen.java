package cult.illusion;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Главное меню Cult Illusion Frame (Observer): только «Проявления».
 *
 * Два независимых переключателя («Влияние сил» и «Влияние Безымянного») вызывают прежние системы AbilityInfluence и
 * Nameless; состояние видно в подписи кнопки и сохраняется в файле настроек. Категории «События» нет: Observer не
 * запускает ритуалы, обряды, способности и другие события. Закрывается клавишей Escape или кнопкой «Закрыть».
 */
public final class CultMenuScreen extends Screen {
    private static final int BUTTON_HEIGHT = 20;
    private static final int GAP = 4;
    private static final int MAX_WIDTH = 220;

    public CultMenuScreen() {
        super(Component.translatable("gui.cult_illusion.menu.title"));
    }

    @Override
    protected void init() {
        int w = Math.min(MAX_WIDTH, this.width - 20);
        int x = (this.width - w) / 2;
        int rows = 5; // заголовок, вкладка «Проявления», два переключателя, «Закрыть»
        int total = rows * BUTTON_HEIGHT + (rows - 1) * GAP;
        int y = Math.max(4, (this.height - total) / 2);

        Button header = Button.builder(this.title, b -> { }).bounds(x, y, w, BUTTON_HEIGHT).build();
        header.active = false;
        this.addRenderableWidget(header);
        y += BUTTON_HEIGHT + GAP;

        Button category = Button.builder(Component.translatable("gui.cult_illusion.menu.cat.manifest"), b -> { })
                .bounds(x, y, w, BUTTON_HEIGHT).build();
        category.active = false; // единственная категория
        this.addRenderableWidget(category);
        y += BUTTON_HEIGHT + GAP;

        this.addRenderableWidget(Button.builder(influenceLabel(), b -> {
            AbilityInfluence.toggle(Minecraft.getInstance());
            b.setMessage(influenceLabel());
        }).bounds(x, y, w, BUTTON_HEIGHT).build());
        y += BUTTON_HEIGHT + GAP;
        this.addRenderableWidget(Button.builder(namelessLabel(), b -> {
            Nameless.toggleAuto(Minecraft.getInstance());
            b.setMessage(namelessLabel());
        }).bounds(x, y, w, BUTTON_HEIGHT).build());
        y += BUTTON_HEIGHT + GAP;

        this.addRenderableWidget(Button.builder(Component.translatable("gui.cult_illusion.menu.close"),
                b -> this.onClose()).bounds(x, y, w, BUTTON_HEIGHT).build());
    }

    private static Component influenceLabel() {
        return Component.translatable("gui.cult_illusion.menu.influence", state(AbilityInfluence.enabled()));
    }

    private static Component namelessLabel() {
        return Component.translatable("gui.cult_illusion.menu.nameless", state(Nameless.autoEnabled()));
    }

    private static Component state(boolean on) {
        return Component.translatable(on ? "gui.cult_illusion.menu.on" : "gui.cult_illusion.menu.off")
                .withStyle(on ? ChatFormatting.GREEN : ChatFormatting.RED);
    }
}
