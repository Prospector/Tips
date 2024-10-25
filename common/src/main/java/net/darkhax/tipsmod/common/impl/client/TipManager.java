package net.darkhax.tipsmod.common.impl.client;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.mojang.serialization.JsonOps;
import net.darkhax.tipsmod.common.api.TipsAPI;
import net.darkhax.tipsmod.common.api.tips.ITip;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.darkhax.tipsmod.common.impl.config.Config;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class TipManager extends SimpleJsonResourceReloadListener {

    public static final TipManager INSTANCE = new TipManager();

    private final Map<ResourceLocation, ITip> loadedTips = new HashMap<>();
    private final Map<ITip, ResourceLocation> reverseLookup = new HashMap<>();
    private final List<ITip> randomAccess = new LinkedList<>();
    private final List<ITip> immutableAccess = Collections.unmodifiableList(randomAccess);

    private TipManager() {
        super(new Gson(), "tips");
    }

    public List<ITip> getTips() {
        return this.immutableAccess;
    }

    @Nullable
    public ResourceLocation getKey(ITip tip) {
        return this.reverseLookup.get(tip);
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> map, @NotNull ResourceManager resourceManager, @NotNull ProfilerFiller profilerFiller) {
        this.loadedTips.clear();
        this.reverseLookup.clear();
        this.randomAccess.clear();
        final long startTime = System.nanoTime();
        final Config config = TipsMod.CONFIG.get();
        for (Map.Entry<ResourceLocation, JsonElement> entry : map.entrySet()) {
            if (!config.ignored_namespaces.contains(entry.getKey().getNamespace()) && !config.ignored_tip_ids.contains(entry.getKey().toString())) {
                try {
                    final ITip tip = TipsAPI.TIP_CODEC.decode(JsonOps.INSTANCE, entry.getValue()).getOrThrow().getFirst();
                    this.loadedTips.put(entry.getKey(), tip);
                    this.reverseLookup.put(tip, entry.getKey());
                    this.randomAccess.add(tip);
                }
                catch (Exception e) {
                    TipsMod.LOG.error("Failed to load tip {}.", entry.getKey(), e);
                }
            }
        }
        Collections.shuffle(this.randomAccess);
        TipsMod.LOG.info("Loaded {} tips. Took {}ms.", this.loadedTips.size(), (double) (System.nanoTime() - startTime) / 1000000d);
    }
}