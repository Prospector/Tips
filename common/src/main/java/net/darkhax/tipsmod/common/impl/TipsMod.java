package net.darkhax.tipsmod.common.impl;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import net.darkhax.bookshelf.common.api.service.Services;
import net.darkhax.pricklemc.common.api.config.ConfigManager;
import net.darkhax.tipsmod.common.api.TipsAPI;
import net.darkhax.tipsmod.common.api.tips.TipType;
import net.darkhax.tipsmod.common.impl.client.tips.SimpleTip;
import net.darkhax.tipsmod.common.impl.config.Config;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TipsMod {

    public static final String MOD_ID = "tipsmod";
    public static final String MOD_NAME = "Tips";
    public static final Logger LOG = LoggerFactory.getLogger(MOD_NAME);
    public static final Component DEFAULT_TITLE = Component.translatable("tipsmod.title.default").withStyle(ChatFormatting.BOLD, ChatFormatting.UNDERLINE, ChatFormatting.YELLOW);
    public static final CachedSupplier<Config> CONFIG = CachedSupplier.cache(() -> ConfigManager.load(MOD_ID, new Config()));
    public static final TipType SIMPLE_TYPE = TipsAPI.registerType(SimpleTip.TYPE_ID, SimpleTip.CODEC);

    public static ResourceLocation id(String path) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, path);
    }

    public static void init() {
        if (Services.PLATFORM.isPhysicalClient()) {
            registerDefaultScreens();
        }
    }
    
    private static void registerDefaultScreens() {
        TipsAPI.registerDefaultScreen(GenericMessageScreen.class);
        TipsAPI.registerDefaultScreen(ConnectScreen.class);
        TipsAPI.registerDefaultScreen(DisconnectedScreen.class);
        TipsAPI.registerDefaultScreen(LevelLoadingScreen.class);
        TipsAPI.registerDefaultScreen(ProgressScreen.class);
        TipsAPI.registerDefaultScreen(PauseScreen.class);
        TipsAPI.registerDefaultScreen(DeathScreen.class);
    }
}