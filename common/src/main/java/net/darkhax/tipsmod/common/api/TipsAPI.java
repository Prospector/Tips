package net.darkhax.tipsmod.common.api;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.darkhax.tipsmod.common.api.tips.ITip;
import net.darkhax.tipsmod.common.api.tips.TipType;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.darkhax.tipsmod.common.impl.client.TipManager;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class TipsAPI {

    private static final Map<ResourceLocation, TipType> TIP_TYPES = new HashMap<>();
    private static final Codec<TipType> TIP_TYPE_CODEC = ResourceLocation.CODEC.xmap(TIP_TYPES::get, TipType::id);
    public static final Codec<ITip> TIP_CODEC = TIP_TYPE_CODEC.dispatch(ITip::getType, TipType::codec);
    private static final Set<Class<? extends Screen>> DEFAULT_SCREENS = new HashSet<>();

    @Nullable
    public static TipType getType(ResourceLocation id) {
        return TIP_TYPES.get(id);
    }

    public static <T extends ITip> TipType registerType(ResourceLocation id, MapCodec<T> codec) {
        if (TIP_TYPES.containsKey(id)) {
            TipsMod.LOG.warn("Tip type {} has already been registered as {}. It will be replace by {}.", id, Objects.requireNonNull(getType(id)).codec(), codec);
        }
        final TipType type = new TipType(id, codec);
        TIP_TYPES.put(id, type);
        return type;
    }

    public static void registerDefaultScreen(Class<? extends Screen> screenClass) {
        DEFAULT_SCREENS.add(screenClass);
    }

    public static boolean isDefaultScreen(Screen screen) {
        return DEFAULT_SCREENS.stream().anyMatch(clazz -> clazz.isInstance(screen));
    }

    public static List<ITip> getLoadedTips() {
        return TipManager.INSTANCE.getTips();
    }

    @Nullable
    public ResourceLocation getTipId(ITip tip) {
        return TipManager.INSTANCE.getKey(tip);
    }
}