package net.darkhax.tipsmod.common.impl.client;

import net.darkhax.tipsmod.common.api.tips.ITip;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.Nullable;

import java.lang.ref.WeakReference;
import java.util.List;

public class TipRenderer {

    public static final TipRenderer INSTANCE = new TipRenderer();

    private long initTime = System.currentTimeMillis();

    @Nullable
    private ITip tip;

    @Nullable
    private WeakReference<Screen> lastScreen;

    private int rngIndex = 0;

    private void setTip(ITip newTip) {
        tip = newTip;
        initTime = System.currentTimeMillis();
    }

    @Nullable
    private ITip getNextTip(Screen parentScreen) {
        final List<ITip> shuffledTips = TipManager.INSTANCE.getTips().stream().filter(tip -> tip.canDisplayOnScreen(parentScreen)).toList();
        if (!shuffledTips.isEmpty() && rngIndex + 1 > shuffledTips.size()) {
            rngIndex = 0;
        }
        return shuffledTips.isEmpty() ? null : shuffledTips.get(rngIndex++);
     }

    public void drawTip(GuiGraphics graphics, Screen parentScreen) {
        // If the tip is null, and we're on a new screen try to load a new tip.
        if (tip == null && (lastScreen == null || !lastScreen.refersTo(parentScreen))) {
            setTip(getNextTip(parentScreen));
            lastScreen = new WeakReference<>(parentScreen);
        }
        if (tip != null) {
            // Cycle to the next tip if the timer has expired.
            final long currentTime = System.currentTimeMillis();
            final int currentCycleTime = tip.getCycleTime();
            if (currentTime - initTime > currentCycleTime) {
                setTip(getNextTip(parentScreen));
            }
            // Render the tip.
            if (tip != null && tip.canDisplayOnScreen(parentScreen)) {
                final int textWidth = Mth.floor(parentScreen.width * TipsMod.CONFIG.get().tip_render_width_percentage);
                int height = parentScreen.height - 10;
                height -= renderLinesReversed(graphics, 10, height, tip.getText(), textWidth);
                height -= 3; // padding for title
                renderLinesReversed(graphics, 10, height, tip.getTitle(), textWidth);
            }
        }
    }

    private static int renderLinesReversed(GuiGraphics graphics, int x, int y, FormattedText text, int textWidth) {
        final Font font = Minecraft.getInstance().font;
        return renderLinesReversed(graphics, font, x, y, font.lineHeight, 0xffffff, text, textWidth);
    }

    private static int renderLinesReversed(GuiGraphics graphics, Font font, int x, int y, int spacing, int defaultColor, FormattedText text, int textWidth) {
        return renderLinesReversed(graphics, font, x, y, spacing, defaultColor, font.split(text, textWidth));
    }

    private static int renderLinesReversed(GuiGraphics graphics, Font font, int x, int y, int spacing, int defaultColor, List<FormattedCharSequence> lines) {
        final int lineCount = lines.size();
        for (int lineNum = lineCount - 1; lineNum >= 0; lineNum--) {
            final FormattedCharSequence lineFragment = lines.get(lineCount - 1 - lineNum);
            graphics.drawString(font, lineFragment, x, y - (lineNum + 1) * (spacing + 1), defaultColor);
        }
        return lineCount * (spacing + 1);
    }
}