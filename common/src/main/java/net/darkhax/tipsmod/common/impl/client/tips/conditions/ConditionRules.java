package net.darkhax.tipsmod.common.impl.client.tips.conditions;

import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecHelper;

import java.util.Optional;
import java.util.function.Predicate;

/**
 * Represents a collection of rule groups that are applied in different circumstances.
 *
 * @param any  An optional rule group that requires at least one rule to match.
 * @param all  An optional rule group that requires all rules to match.
 * @param none An optional rule group that will fail if any rules match.
 * @param <T>  The input type.
 */
public record ConditionRules<T>(Optional<RuleGroup<T>> any, Optional<RuleGroup<T>> all, Optional<RuleGroup<T>> none) implements Predicate<T> {

    /**
     * Checks if the condition contains any rules.
     *
     * @return If the condition contains any rules.
     */
    public boolean isEmpty() {
        return any.isEmpty() && all.isEmpty() && none.isEmpty();
    }

    @Override
    public boolean test(T t) {
        return (any.isEmpty() || any.get().anyMatch(t)) && (all.isEmpty() || all.get().allMatch(t)) && (none.isEmpty() || none.get().noneMatch(t));
    }

    /**
     * Builds a codec that can handle all the sub-rule groups.
     *
     * @param ruleBuilder Determines how rule patterns are compiled.
     * @param <T>         The input type.
     * @return A codec that can handle all the sub-rule groups.
     */
    public static <T> MapCodecHelper<ConditionRules<T>> codecOf(IRuleBuilder<T> ruleBuilder) {
        final MapCodecHelper<RuleGroup<T>> rulesCodec = new MapCodecHelper<>(RuleGroup.codecOf(ruleBuilder));
        return new MapCodecHelper<>(RecordCodecBuilder.create(instance -> instance.group(
                rulesCodec.getOptional("any_of", ConditionRules::any),
                rulesCodec.getOptional("all_of", ConditionRules::all),
                rulesCodec.getOptional("none_of", ConditionRules::none)
        ).apply(instance, ConditionRules::new)));
    }
}