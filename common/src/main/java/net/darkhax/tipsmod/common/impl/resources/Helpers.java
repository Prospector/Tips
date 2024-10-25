package net.darkhax.tipsmod.common.impl.resources;

import net.darkhax.tipsmod.common.mixin.AccessorClientAdvancements;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Helpers {

    public static boolean isValid(String path) {
        return ResourceLocation.tryParse(path) != null;
    }

    public static boolean isNamespace(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (!allowedInNamespace(input.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean allowedInNamespace(char input) {
        return input == '_' || input == '-' || input >= 'a' && input <= 'z' || input >= '0' && input <= '9' || input == '.';
    }

    public static Set<ResourceLocation> getCompletedAdvancements(LocalPlayer player) {
        if (player.connection.getAdvancements() instanceof AccessorClientAdvancements access) {
            final Set<ResourceLocation> completed = new HashSet<>();
            final Registry<Advancement> registry = player.registryAccess().registry(Registries.ADVANCEMENT).orElseThrow();
            for (Map.Entry<Advancement, AdvancementProgress> entry : access.bookshelf$getProgress().entrySet()) {
                if (entry.getValue().isDone()) {
                    final ResourceLocation advancementId = registry.getKey(entry.getKey());
                    if (advancementId != null) {
                        completed.add(advancementId);
                    }
                }
            }
            return completed;
        }
        return Set.of();
    }
}
