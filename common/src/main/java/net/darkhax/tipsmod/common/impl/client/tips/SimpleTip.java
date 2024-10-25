package net.darkhax.tipsmod.common.impl.client.tips;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecHelper;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecs;
import net.darkhax.tipsmod.common.api.TipsAPI;
import net.darkhax.tipsmod.common.api.tips.ITip;
import net.darkhax.tipsmod.common.api.tips.TipType;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.darkhax.tipsmod.common.impl.client.tips.conditions.ConditionRules;
import net.darkhax.tipsmod.common.impl.client.tips.conditions.RuleBuilders;
import net.darkhax.tipsmod.common.impl.resources.Helpers;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Optional;
import java.util.Set;

/**
 * A simple implementation of the tip.
 */
public class SimpleTip implements ITip {

    public static final ResourceLocation TYPE_ID = TipsMod.id("simple");
    public static final MapCodec<SimpleTip> CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            MapCodecs.TEXT.get("title", ITip::getTitle, TipsMod.CONFIG.get().default_title),
            MapCodecs.TEXT.get("text", ITip::getText),
            MapCodecs.INT.get("cycle_time", ITip::getCycleTime, TipsMod.CONFIG.get().default_cycle_time),
            Conditions.CODEC.get("conditions", SimpleTip::getConditions, Conditions.EMPTY)
    ).apply(instance, SimpleTip::new));

    private final Component title;
    private final Component text;
    private final int cycleTime;
    private final Conditions conditions;

    public SimpleTip(Component title, Component text, int cycleTime, Conditions conditions) {
        this.title = title;
        this.text = text;
        this.cycleTime = cycleTime;
        this.conditions = conditions;
    }

    protected Conditions getConditions() {
        return null;
    }

    @Override
    public Component getTitle() {
        return this.title;
    }

    @Override
    public Component getText() {
        return this.text;
    }

    @Override
    public TipType getType() {
        return TipsMod.SIMPLE_TYPE;
    }

    @Override
    public int getCycleTime() {
        return this.cycleTime;
    }

    @Override
    public boolean canDisplayOnScreen(Screen screen) {
        return this.conditions.canDisplayOnScreen(screen);
    }

    public record Conditions(Optional<ConditionRules<Screen>> screens, Optional<ConditionRules<Holder<Biome>>> biome, Optional<ConditionRules<Holder<DimensionType>>> dimension, Optional<ConditionRules<Set<ResourceLocation>>> advancements) {

        public static final Conditions EMPTY = new Conditions(Optional.empty(), Optional.empty(), Optional.empty(), Optional.empty());
        public static final MapCodecHelper<Conditions> CODEC = new MapCodecHelper<>(RecordCodecBuilder.create(instance -> instance.group(
                ConditionRules.codecOf(RuleBuilders.SCREEN).getOptional("screens", Conditions::screens),
                ConditionRules.codecOf(RuleBuilders.BIOME).getOptional("biomes", Conditions::biome),
                ConditionRules.codecOf(RuleBuilders.DIMENSION).getOptional("dimensions", Conditions::dimension),
                ConditionRules.codecOf(RuleBuilders.RESOURCE_LOCATION_SET).getOptional("advancements", Conditions::advancements)
        ).apply(instance, Conditions::new)));

        public boolean isEmpty() {
            return screens.isEmpty() && biome.isEmpty() && dimension().isEmpty() && advancements().isEmpty();
        }

        public boolean requiresPlayer() {
            return biome.isPresent() || dimension.isPresent() || advancements.isPresent();
        }

        public boolean canDisplayOnScreen(Screen screen) {
            if (this.screens.map(rules -> rules.test(screen)).orElseGet(() -> TipsAPI.isDefaultScreen(screen))) {
                if (this.requiresPlayer()) {
                    final LocalPlayer player = Minecraft.getInstance().player;
                    if (player == null) {
                        return false;
                    }
                    if (this.biome.isPresent() && !this.biome.get().test(player.level().getBiome(player.blockPosition()))) {
                        return false;
                    }
                    if (this.dimension.isPresent() && !this.dimension.get().test(player.level().dimensionTypeRegistration())) {
                        return false;
                    }
                    if (this.advancements.isPresent() && !this.advancements.get().test(Helpers.getCompletedAdvancements(player))) {
                        return false;
                    }
                }
                return true;
            }
            return false;
        }
    }
}