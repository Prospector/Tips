package net.darkhax.tipsmod.impl;

import net.darkhax.tipsmod.common.impl.TipsMod;
import net.minecraft.client.Minecraft;
import net.neoforged.fml.common.Mod;

@Mod(TipsMod.MOD_ID)
public class NeoForgeMod {
    public NeoForgeMod() {
        TipsMod.init();
    }
}