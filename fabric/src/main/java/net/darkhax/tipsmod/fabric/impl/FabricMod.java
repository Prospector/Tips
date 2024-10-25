package net.darkhax.tipsmod.fabric.impl;

import net.darkhax.tipsmod.common.impl.TipsMod;
import net.fabricmc.api.ModInitializer;

public class FabricMod implements ModInitializer {

    @Override
    public void onInitialize() {
        TipsMod.init();
    }
}