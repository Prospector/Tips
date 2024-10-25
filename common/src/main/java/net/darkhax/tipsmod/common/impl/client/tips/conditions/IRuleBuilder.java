package net.darkhax.tipsmod.common.impl.client.tips.conditions;

import java.util.function.Predicate;

/**
 * Defines a formula that is used to compile a rule (predicate) from a string value.
 *
 * @param <T> The input value type of the predicate.
 */
public interface IRuleBuilder<T> {

    /**
     * Compiles a rule from the provided string.
     *
     * @param rule The rule to compile into a predicate.
     * @return The compiled rule (predicate).
     */
    Predicate<T> build(String rule);
}
