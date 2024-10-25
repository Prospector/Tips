package net.darkhax.tipsmod.common.impl.client.tips.conditions;

import net.darkhax.tipsmod.common.api.TipsAPI;
import net.darkhax.tipsmod.common.impl.TipsMod;
import net.darkhax.tipsmod.common.impl.resources.Helpers;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RuleBuilders {

    public static final IRuleBuilder<Screen> SCREEN = rule -> {
        if (rule.equalsIgnoreCase("tipsmod:built-in")) {
            return TipsAPI::isDefaultScreen;
        }
        // Match by VanillaScreenIds class
        else if (Helpers.isValid(rule)) {
            final ResourceLocation targetScreen = ResourceLocation.tryParse(rule);
            if (targetScreen != null && "minecraft".equalsIgnoreCase(targetScreen.getNamespace())) {
                return screen -> VanillaScreenIds.is(targetScreen, screen.getClass());
            }
            TipsMod.LOG.error("Screen condition with ID {} is not valid. Only the vanilla screens have IDs.", rule);
        }
        // Match by canonical class name
        else if (rule.contains(".")) {
            return screen -> rule.equalsIgnoreCase(screen.getClass().getCanonicalName());
        }
        // Match by simplified class name.
        else {
            return screen -> rule.equals(screen.getClass().getSimpleName());
        }
        return screen -> false;
    };

    /**
     * A rule builder for resource location predicates. This builder supports direct matches, namespace only matches,
     * and regex matches if the rule starts with a tilda. If a valid rule can not be compiled from the input a predicate
     * that always returns false will be used.
     */
    public static final IRuleBuilder<ResourceLocation> RESOURCE_LOCATION = rule -> {
        // Match by ID
        if (Helpers.isValid(rule)) {
            final ResourceLocation targetEntryID = ResourceLocation.tryParse(rule);
            return targetEntryID == null ? entry -> false : entry -> entry.equals(targetEntryID);
        }
        // Match by Namespace
        else if (Helpers.isNamespace(rule)) {
            return entry -> rule.equalsIgnoreCase(entry.getNamespace());
        }
        // Try with Regex
        else if (rule.startsWith("~")) {
            try {
                final Pattern pattern = Pattern.compile(rule);
                return entry -> pattern.matcher(entry.toString()).matches();
            }
            catch (PatternSyntaxException e) {
                TipsMod.LOG.error("An invalid Regex pattern was used! Rule '{}' is invalid!", rule, e);
                return entry -> false;
            }
        }
        TipsMod.LOG.error("An invalid pattern was used. Pattern must be a valid resource location, namespace, or regex pattern. '{}'", rule);
        return entry -> false;
    };

    /**
     * A rule builder that applies resource location rules to an entire set of resource locations.
     */
    public static final IRuleBuilder<Set<ResourceLocation>> RESOURCE_LOCATION_SET = rule -> {
        final Predicate<ResourceLocation> rlRule = RESOURCE_LOCATION.build(rule);
        return set -> set.stream().anyMatch(rlRule);
    };

    /**
     * A rule builder that creates rules for registered biome entries.
     */
    public static final IRuleBuilder<Holder<Biome>> BIOME = rule -> buildRegistryRule(Registries.BIOME, rule);

    /**
     * A rule builder that creates rules for registered dimension entries.
     */
    public static final IRuleBuilder<Holder<DimensionType>> DIMENSION = rule -> buildRegistryRule(Registries.DIMENSION_TYPE, rule);

    /**
     * Builds a new rule for entries of a given registry.
     *
     * @param regKey The key of the target registry.
     * @param rule   The rule to compile.
     * @param <T>    The type of the registry.
     * @return A rule for registry entries.
     */
    private static <T> Predicate<Holder<T>> buildRegistryRule(ResourceKey<? extends Registry<T>> regKey, String rule) {
        // Match by ID
        if (Helpers.isValid(rule)) {
            final ResourceLocation targetEntryID = ResourceLocation.tryParse(rule);
            return targetEntryID == null ? entry -> false : entry -> entry.is(targetEntryID);
        }
        // Match by Namespace
        else if (Helpers.isNamespace(rule)) {
            return entry -> entry.unwrapKey().map(id -> rule.equalsIgnoreCase(id.location().getNamespace())).orElse(false);
        }
        // Match by tag
        else if (rule.startsWith("#") && Helpers.isValid(rule.substring(1))) {
            final TagKey<T> tag = TagKey.create(regKey, Objects.requireNonNull(ResourceLocation.tryParse(rule.substring(1))));
            return entry -> entry.is(tag);
        }
        // Try with Regex
        else if (rule.startsWith("~")) {
            try {
                final Pattern pattern = Pattern.compile(rule);
                return entry -> entry.unwrapKey().map(key -> pattern.matcher(key.toString()).matches()).orElse(false);
            }
            catch (PatternSyntaxException e) {
                TipsMod.LOG.error("An invalid Regex pattern was used! Rule '{}' is invalid!", rule, e);
                return entry -> false;
            }
        }
        TipsMod.LOG.error("An invalid pattern was used. Pattern must be a valid resource location, namespace, tag, or regex pattern. '{}'", rule);
        return entry -> false;
    }
}
