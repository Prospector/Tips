package net.darkhax.tipsmod.common.api.tips;

import net.darkhax.tipsmod.common.api.TipsAPI;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/**
 * Represents a tip that can be rendered on a screen.
 */
public interface ITip {

    /**
     * Gets the title for the tip. This is the part on top of the tip which explains what it is.
     *
     * @return The title of the tip.
     */
    Component getTitle();

    /**
     * Gets the body of the tip. This is the actual tip information.
     *
     * @return The body of the tip.
     */
    Component getText();

    /**
     * Gets the type of the tip. This is used for serialization.
     *
     * @return The type of the tip.
     */
    TipType getType();

    /**
     * Gets the amount of time until the next tip can be displayed. By default, this will be the config defined cycle
     * time.
     *
     * @return The amount of time in ticks until the next tip will be displayed.
     */
    default int getCycleTime() {
        return TipsMod.CONFIG.get().default_cycle_time;
    }

    /**
     * Tests if the tip can be displayed on the specified screen.
     *
     * @param screen The screen to test.
     * @return If the tip can be displayed on the screen.
     */
    default boolean canDisplayOnScreen(Screen screen) {
        return TipsAPI.isDefaultScreen(screen);
    }
}