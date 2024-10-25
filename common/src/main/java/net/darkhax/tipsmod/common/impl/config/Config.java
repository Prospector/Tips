package net.darkhax.tipsmod.common.impl.config;

import net.darkhax.pricklemc.common.api.annotations.Value;
import net.darkhax.tipsmod.common.api.TipsAPI;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.minecraft.network.chat.Component;

import java.util.HashSet;
import java.util.Set;

public class Config {

    @Value(comment = "The default amount of time for a tip to be displayed before it cycles to the next tip.")
    public int default_cycle_time = 5000;

    @Value(comment = "Tips from an ignored namespace will not be displayed in game.", writeDefault = false)
    public Set<String> ignored_namespaces = new HashSet<>();

    @Value(comment = "Adding a tip ID here will prevent it from being displayed.", writeDefault = false)
    public Set<String> ignored_tip_ids = new HashSet<>();

    @Value(comment = "The percentage of the screen width that tips should render over.")
    public float tip_render_width_percentage = 0.35f;

    @Value(comment = "The default tile to use when rendering a tip.", writeDefault = false)
    public Component default_title = TipsMod.DEFAULT_TITLE;
}