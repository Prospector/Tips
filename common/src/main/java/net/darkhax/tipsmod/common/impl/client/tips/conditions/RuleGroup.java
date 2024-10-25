package net.darkhax.tipsmod.common.impl.client.tips.conditions;

import com.mojang.serialization.Codec;
import net.darkhax.bookshelf.common.api.data.codecs.map.MapCodecs;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Represents a group of compiled rules.
 *
 * @param predicates A list of predicates contained in the rule group.
 * @param rawRules   The raw set of rules used to compile the rule group.
 * @param <T>        The type of value these rules will be applied to.
 */
public record RuleGroup<T>(List<Predicate<T>> predicates, Set<String> rawRules) {

    /**
     * Checks if any of the rules match the input.
     *
     * @param t The input value.
     * @return If at least one rule matches the input.
     */
    public boolean anyMatch(T t) {
        return this.predicates.stream().anyMatch(rule -> rule.test(t));
    }

    /**
     * Checks if all rules match the input.
     *
     * @param t The input value.
     * @return If all rules matched the input.
     */
    public boolean allMatch(T t) {
        return this.predicates.stream().allMatch(rule -> rule.test(t));
    }

    /**
     * Check if no rules match the input.
     *
     * @param t The input value.
     * @return If all rules do not match the input.
     */
    public boolean noneMatch(T t) {
        return this.predicates.stream().noneMatch(rule -> rule.test(t));
    }

    /**
     * Creates a codec for a given rule builder.
     *
     * @param ruleBuilder The builder used to compile rules.
     * @param <T>         The input type for the rules.
     * @return A codec for the rule builder.
     */
    public static <T> Codec<RuleGroup<T>> codecOf(IRuleBuilder<T> ruleBuilder) {
        return MapCodecs.STRING.getSet().xmap(
                to -> new RuleGroup<>(to.stream().map(ruleBuilder::build).collect(Collectors.toList()), to),
                from -> from.rawRules
        );
    }
}