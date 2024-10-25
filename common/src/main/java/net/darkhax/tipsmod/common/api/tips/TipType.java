package net.darkhax.tipsmod.common.api.tips;

import com.mojang.serialization.MapCodec;
import net.minecraft.resources.ResourceLocation;

public record TipType(ResourceLocation id, MapCodec<? extends ITip> codec) {
}
