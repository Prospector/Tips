package net.darkhax.tipsmod.forge;

import net.darkhax.tipsmod.common.impl.TipsMod;
import net.minecraftforge.fml.common.Mod;

@Mod(TipsMod.MOD_ID)
public class ForgeMod {
    public ForgeMod() {
        TipsMod.init();
    }
}