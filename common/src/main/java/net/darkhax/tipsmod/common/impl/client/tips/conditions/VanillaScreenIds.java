package net.darkhax.tipsmod.common.impl.client.tips.conditions;

import net.minecraft.client.gui.components.PopupScreen;
import net.minecraft.client.gui.screens.AccessibilityOnboardingScreen;
import net.minecraft.client.gui.screens.AlertScreen;
import net.minecraft.client.gui.screens.BackupConfirmScreen;
import net.minecraft.client.gui.screens.ChatScreen;
import net.minecraft.client.gui.screens.ConfirmLinkScreen;
import net.minecraft.client.gui.screens.ConfirmScreen;
import net.minecraft.client.gui.screens.ConnectScreen;
import net.minecraft.client.gui.screens.CreateBuffetWorldScreen;
import net.minecraft.client.gui.screens.CreateFlatWorldScreen;
import net.minecraft.client.gui.screens.CreditsAndAttributionScreen;
import net.minecraft.client.gui.screens.DatapackLoadFailureScreen;
import net.minecraft.client.gui.screens.DeathScreen;
import net.minecraft.client.gui.screens.DemoIntroScreen;
import net.minecraft.client.gui.screens.DirectJoinServerScreen;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.client.gui.screens.EditServerScreen;
import net.minecraft.client.gui.screens.ErrorScreen;
import net.minecraft.client.gui.screens.GenericMessageScreen;
import net.minecraft.client.gui.screens.GenericWaitingScreen;
import net.minecraft.client.gui.screens.InBedChatScreen;
import net.minecraft.client.gui.screens.LevelLoadingScreen;
import net.minecraft.client.gui.screens.OutOfMemoryScreen;
import net.minecraft.client.gui.screens.PauseScreen;
import net.minecraft.client.gui.screens.PresetFlatWorldScreen;
import net.minecraft.client.gui.screens.ProgressScreen;
import net.minecraft.client.gui.screens.ReceivingLevelScreen;
import net.minecraft.client.gui.screens.ShareToLanScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.WinScreen;
import net.minecraft.client.gui.screens.achievement.StatsScreen;
import net.minecraft.client.gui.screens.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screens.debug.GameModeSwitcherScreen;
import net.minecraft.client.gui.screens.inventory.AbstractCommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.AbstractFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.AbstractSignEditScreen;
import net.minecraft.client.gui.screens.inventory.AnvilScreen;
import net.minecraft.client.gui.screens.inventory.BeaconScreen;
import net.minecraft.client.gui.screens.inventory.BlastFurnaceScreen;
import net.minecraft.client.gui.screens.inventory.BookEditScreen;
import net.minecraft.client.gui.screens.inventory.BookViewScreen;
import net.minecraft.client.gui.screens.inventory.BrewingStandScreen;
import net.minecraft.client.gui.screens.inventory.CartographyTableScreen;
import net.minecraft.client.gui.screens.inventory.CommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.ContainerScreen;
import net.minecraft.client.gui.screens.inventory.CraftingScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.screens.inventory.DispenserScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.gui.screens.inventory.EnchantmentScreen;
import net.minecraft.client.gui.screens.inventory.FurnaceScreen;
import net.minecraft.client.gui.screens.inventory.GrindstoneScreen;
import net.minecraft.client.gui.screens.inventory.HangingSignEditScreen;
import net.minecraft.client.gui.screens.inventory.HopperScreen;
import net.minecraft.client.gui.screens.inventory.HorseInventoryScreen;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.ItemCombinerScreen;
import net.minecraft.client.gui.screens.inventory.JigsawBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.LecternScreen;
import net.minecraft.client.gui.screens.inventory.LoomScreen;
import net.minecraft.client.gui.screens.inventory.MerchantScreen;
import net.minecraft.client.gui.screens.inventory.MinecartCommandBlockEditScreen;
import net.minecraft.client.gui.screens.inventory.ShulkerBoxScreen;
import net.minecraft.client.gui.screens.inventory.SignEditScreen;
import net.minecraft.client.gui.screens.inventory.SmithingScreen;
import net.minecraft.client.gui.screens.inventory.SmokerScreen;
import net.minecraft.client.gui.screens.inventory.StonecutterScreen;
import net.minecraft.client.gui.screens.inventory.StructureBlockEditScreen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.multiplayer.SafetyScreen;
import net.minecraft.client.gui.screens.multiplayer.WarningScreen;
import net.minecraft.client.gui.screens.options.AccessibilityOptionsScreen;
import net.minecraft.client.gui.screens.options.ChatOptionsScreen;
import net.minecraft.client.gui.screens.options.LanguageSelectScreen;
import net.minecraft.client.gui.screens.options.MouseSettingsScreen;
import net.minecraft.client.gui.screens.options.OnlineOptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsScreen;
import net.minecraft.client.gui.screens.options.OptionsSubScreen;
import net.minecraft.client.gui.screens.options.SkinCustomizationScreen;
import net.minecraft.client.gui.screens.options.SoundOptionsScreen;
import net.minecraft.client.gui.screens.options.VideoSettingsScreen;
import net.minecraft.client.gui.screens.options.controls.ControlsScreen;
import net.minecraft.client.gui.screens.options.controls.KeyBindsScreen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.reporting.ChatReportScreen;
import net.minecraft.client.gui.screens.reporting.ChatSelectionScreen;
import net.minecraft.client.gui.screens.reporting.ReportReasonSelectionScreen;
import net.minecraft.client.gui.screens.social.SocialInteractionsScreen;
import net.minecraft.client.gui.screens.telemetry.TelemetryInfoScreen;
import net.minecraft.client.gui.screens.worldselection.ConfirmExperimentalFeaturesScreen;
import net.minecraft.client.gui.screens.worldselection.CreateWorldScreen;
import net.minecraft.client.gui.screens.worldselection.EditGameRulesScreen;
import net.minecraft.client.gui.screens.worldselection.EditWorldScreen;
import net.minecraft.client.gui.screens.worldselection.ExperimentsScreen;
import net.minecraft.client.gui.screens.worldselection.OptimizeWorldScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.resources.ResourceLocation;

import net.darkhax.bookshelf.common.api.function.CachedSupplier;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Minecraft does not keep a registry of screens which makes referencing screens in user configs especially arduous.
 * Historically the class name could be used as a pseudo-id but on modern versions we must contend with users having
 * different runtime mappings. For example, the enchantment screen class is named EnchantmentScreen in Mojmap and Forge,
 * but Fabric environments use class_486.
 * <p>
 * This class attempts to solve this issue by providing IDs for the vanilla screen classes. We use class references that
 * will be remapped at compile time and/or runtime.
 * <p>
 * Keep in mind that accessing this class will class-load the screen classes. Accessing this class from a mixin or from
 * an especially early loading stage can cause issues.
 * <p>
 * Note: The death details screen and the discard report warning screen are not mapped in this registry. They're private
 * inner classes, and I don't like ATs/AWs, and I am too lazy to write remapping code.
 */
public class VanillaScreenIds {

    private static final CachedSupplier<Map<ResourceLocation, CachedSupplier<Class<?>>>> SCREEN_IDS = CachedSupplier.cache(VanillaScreenIds::buildScreenMap);

    public static boolean has(ResourceLocation id) {
        return SCREEN_IDS.get().containsKey(id);
    }

    @Nullable
    public static Class<?> getScreen(ResourceLocation id) {
        return SCREEN_IDS.get().containsKey(id) ? SCREEN_IDS.get().get(id).get() : null;
    }

    public static boolean is(ResourceLocation screenId, Class<?> screenClass) {
        return SCREEN_IDS.get().containsKey(screenId) && SCREEN_IDS.get().get(screenId).get() == screenClass;
    }

    private static Map<ResourceLocation, CachedSupplier<Class<?>>> buildScreenMap() {
        final Map<ResourceLocation, CachedSupplier<Class<?>>> screenMap = new HashMap<>();
        screenMap.put(ResourceLocation.withDefaultNamespace("abstract_command_block_edit_screen"), CachedSupplier.cache(() -> AbstractCommandBlockEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("abstract_container_screen"), CachedSupplier.cache(() -> AbstractContainerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("abstract_furnace_screen"), CachedSupplier.cache(() -> AbstractFurnaceScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("abstract_sign_edit_screen"), CachedSupplier.cache(() -> AbstractSignEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("accessibility_onboarding_screen"), CachedSupplier.cache(() -> AccessibilityOnboardingScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("accessibility_options_screen"), CachedSupplier.cache(() -> AccessibilityOptionsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("advancements_screen"), CachedSupplier.cache(() -> AdvancementsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("alert_screen"), CachedSupplier.cache(() -> AlertScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("anvil_screen"), CachedSupplier.cache(() -> AnvilScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("backup_confirm_screen"), CachedSupplier.cache(() -> BackupConfirmScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("beacon_screen"), CachedSupplier.cache(() -> BeaconScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("blast_furnace_screen"), CachedSupplier.cache(() -> BlastFurnaceScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("book_edit_screen"), CachedSupplier.cache(() -> BookEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("book_view_screen"), CachedSupplier.cache(() -> BookViewScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("brewing_stand_screen"), CachedSupplier.cache(() -> BrewingStandScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("cartography_table_screen"), CachedSupplier.cache(() -> CartographyTableScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("chat_options_screen"), CachedSupplier.cache(() -> ChatOptionsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("chat_report_screen"), CachedSupplier.cache(() -> ChatReportScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("chat_screen"), CachedSupplier.cache(() -> ChatScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("chat_selection_screen"), CachedSupplier.cache(() -> ChatSelectionScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("command_block_edit_screen"), CachedSupplier.cache(() -> CommandBlockEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("confirm_experimental_features_screen"), CachedSupplier.cache(() -> ConfirmExperimentalFeaturesScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("confirm_link_screen"), CachedSupplier.cache(() -> ConfirmLinkScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("confirm_screen"), CachedSupplier.cache(() -> ConfirmScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("connect_screen"), CachedSupplier.cache(() -> ConnectScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("container_screen"), CachedSupplier.cache(() -> ContainerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("controls_screen"), CachedSupplier.cache(() -> ControlsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("crafting_screen"), CachedSupplier.cache(() -> CraftingScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("create_buffet_world_screen"), CachedSupplier.cache(() -> CreateBuffetWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("create_flat_world_screen"), CachedSupplier.cache(() -> CreateFlatWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("create_world_screen"), CachedSupplier.cache(() -> CreateWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("creative_mode_inventory_screen"), CachedSupplier.cache(() -> CreativeModeInventoryScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("credits_and_attribution_screen"), CachedSupplier.cache(() -> CreditsAndAttributionScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("datapack_load_failure_screen"), CachedSupplier.cache(() -> DatapackLoadFailureScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("death_screen"), CachedSupplier.cache(() -> DeathScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("demo_intro_screen"), CachedSupplier.cache(() -> DemoIntroScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("direct_join_server_screen"), CachedSupplier.cache(() -> DirectJoinServerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("disconnected_realms_screen"), CachedSupplier.cache(() -> DisconnectedRealmsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("disconnected_screen"), CachedSupplier.cache(() -> DisconnectedScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("dispenser_screen"), CachedSupplier.cache(() -> DispenserScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("edit_game_rules_screen"), CachedSupplier.cache(() -> EditGameRulesScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("edit_server_screen"), CachedSupplier.cache(() -> EditServerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("edit_world_screen"), CachedSupplier.cache(() -> EditWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("effect_rendering_inventory_screen"), CachedSupplier.cache(() -> EffectRenderingInventoryScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("enchantment_screen"), CachedSupplier.cache(() -> EnchantmentScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("error_screen"), CachedSupplier.cache(() -> ErrorScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("experiments_screen"), CachedSupplier.cache(() -> ExperimentsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("furnace_screen"), CachedSupplier.cache(() -> FurnaceScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("game_mode_switcher_screen"), CachedSupplier.cache(() -> GameModeSwitcherScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("generic_message_screen"), CachedSupplier.cache(() -> GenericMessageScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("generic_waiting_screen"), CachedSupplier.cache(() -> GenericWaitingScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("grindstone_screen"), CachedSupplier.cache(() -> GrindstoneScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("hanging_sign_edit_screen"), CachedSupplier.cache(() -> HangingSignEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("hopper_screen"), CachedSupplier.cache(() -> HopperScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("horse_inventory_screen"), CachedSupplier.cache(() -> HorseInventoryScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("in_bed_chat_screen"), CachedSupplier.cache(() -> InBedChatScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("inventory_screen"), CachedSupplier.cache(() -> InventoryScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("item_combiner_screen"), CachedSupplier.cache(() -> ItemCombinerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("jigsaw_block_edit_screen"), CachedSupplier.cache(() -> JigsawBlockEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("join_multiplayer_screen"), CachedSupplier.cache(() -> JoinMultiplayerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("key_binds_screen"), CachedSupplier.cache(() -> KeyBindsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("language_select_screen"), CachedSupplier.cache(() -> LanguageSelectScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("lectern_screen"), CachedSupplier.cache(() -> LecternScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("level_loading_screen"), CachedSupplier.cache(() -> LevelLoadingScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("loom_screen"), CachedSupplier.cache(() -> LoomScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("merchant_screen"), CachedSupplier.cache(() -> MerchantScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("minecart_command_block_edit_screen"), CachedSupplier.cache(() -> MinecartCommandBlockEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("mouse_settings_screen"), CachedSupplier.cache(() -> MouseSettingsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("online_options_screen"), CachedSupplier.cache(() -> OnlineOptionsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("optimize_world_screen"), CachedSupplier.cache(() -> OptimizeWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("options_screen"), CachedSupplier.cache(() -> OptionsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("options_sub_screen"), CachedSupplier.cache(() -> OptionsSubScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("out_of_memory_screen"), CachedSupplier.cache(() -> OutOfMemoryScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("pack_selection_screen"), CachedSupplier.cache(() -> PackSelectionScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("pause_screen"), CachedSupplier.cache(() -> PauseScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("popup_screen"), CachedSupplier.cache(() -> PopupScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("preset_flat_world_screen"), CachedSupplier.cache(() -> PresetFlatWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("progress_screen"), CachedSupplier.cache(() -> ProgressScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("realms_screen"), CachedSupplier.cache(() -> RealmsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("receiving_level_screen"), CachedSupplier.cache(() -> ReceivingLevelScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("report_reason_selection_screen"), CachedSupplier.cache(() -> ReportReasonSelectionScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("safety_screen"), CachedSupplier.cache(() -> SafetyScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("select_world_screen"), CachedSupplier.cache(() -> SelectWorldScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("share_to_lan_screen"), CachedSupplier.cache(() -> ShareToLanScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("shulker_box_screen"), CachedSupplier.cache(() -> ShulkerBoxScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("sign_edit_screen"), CachedSupplier.cache(() -> SignEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("skin_customization_screen"), CachedSupplier.cache(() -> SkinCustomizationScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("smithing_screen"), CachedSupplier.cache(() -> SmithingScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("smoker_screen"), CachedSupplier.cache(() -> SmokerScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("social_interactions_screen"), CachedSupplier.cache(() -> SocialInteractionsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("sound_options_screen"), CachedSupplier.cache(() -> SoundOptionsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("stats_screen"), CachedSupplier.cache(() -> StatsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("stonecutter_screen"), CachedSupplier.cache(() -> StonecutterScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("structure_block_edit_screen"), CachedSupplier.cache(() -> StructureBlockEditScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("telemetry_info_screen"), CachedSupplier.cache(() -> TelemetryInfoScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("title_confirm_screen"), CachedSupplier.cache(() -> DeathScreen.TitleConfirmScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("title_screen"), CachedSupplier.cache(() -> TitleScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("video_settings_screen"), CachedSupplier.cache(() -> VideoSettingsScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("warning_screen"), CachedSupplier.cache(() -> WarningScreen.class));
        screenMap.put(ResourceLocation.withDefaultNamespace("win_screen"), CachedSupplier.cache(() -> WinScreen.class));
        return Collections.unmodifiableMap(screenMap);
    }
}