package trinsdar.advancedsolars.util;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

@Mod.EventBusSubscriber(modid = AdvancedSolarsClassic.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class AdvancedSolarsConfig {
    public static final PowerGeneration POWER_GENERATION = new PowerGeneration();
    public static final PowerValues POWER_VALUES = new PowerValues();
    public static final Misc MISC = new Misc();
    public static final EnabledItems ENABLED_ITEMS = new EnabledItems();
    public static final CommonConfig COMMON_CONFIG;
    public static final ForgeConfigSpec COMMON_SPEC;

    static {

        final Pair<CommonConfig, ForgeConfigSpec> COMMON_PAIR = new ForgeConfigSpec.Builder().configure(CommonConfig::new);
        COMMON_CONFIG = COMMON_PAIR.getLeft();
        COMMON_SPEC = COMMON_PAIR.getRight();

    }

    @SubscribeEvent
    public static void onModConfigEvent(final ModConfigEvent e) {
        onModConfigEvent(e.getConfig());
    }

    public static void onModConfigEvent(final ModConfig e) {
        if (e.getModId().equals(AdvancedSolarsClassic.MODID)) {
            if (e.getSpec() == COMMON_SPEC) bakeCommonConfig();
        }
    }

    public static class PowerGeneration {
        public Double ADVANCED_SOLAR_GENERATION_MULTIPLIER;
        public Double HYBRID_SOLAR_GENERATION_MULTIPLIER;
        public Double ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
    }

    public static class PowerValues {
        public int ADVANCED_SOLAR_HELMET_STORAGE, HYBRID_SOLAR_HELMET_STORAGE, ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE,
                ADVANCED_SOLAR_HELMET_TRANSFER, HYBRID_SOLAR_HELMET_TRANSFER, ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER;
    }

    public static class EnabledItems {
        public boolean enableAdvancedSolarHelmet = true;
        public boolean enableHybridSolarHelmet = true;
        public boolean enableUltimateHybridSolarHelmet = true;
        public boolean enableAdvancedSolarPanel = true;
        public boolean enableHybridSolarPanel = true;
        public boolean enableUltimateHybridSolarPanel = true;
        public boolean enableMiscCraftingItems = true;
    }

    public static class Misc {
        public CenterIngot INGOT_IN_IRRADIANT_URANIUM = CenterIngot.ENDERPEARL_URANIUM;

        enum CenterIngot {
            ENDERPEARL_URANIUM,
            URANIUM,
            URANIUM235,
            URANIUM233;
        }
    }

    public static class CommonConfig {
        public final ForgeConfigSpec.IntValue ADVANCED_SOLAR_HELMET_STORAGE, HYBRID_SOLAR_HELMET_STORAGE, ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE;
        public final ForgeConfigSpec.IntValue ADVANCED_SOLAR_HELMET_TRANSFER, HYBRID_SOLAR_HELMET_TRANSFER, ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER;

        public final ForgeConfigSpec.DoubleValue ADVANCED_SOLAR_GENERATION_MULTIPLIER, HYBRID_SOLAR_GENERATION_MULTIPLIER, ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
        public final ForgeConfigSpec.EnumValue<Misc.CenterIngot> INGOT_IN_IRRADIANT_URANIUM;

        public CommonConfig(ForgeConfigSpec.Builder builder) {
            builder.push("PowerGeneration");
            ADVANCED_SOLAR_GENERATION_MULTIPLIER = builder.comment("Base energy generation multiplier values for advanced solar - increase them for higher yields.")
                    .translation(AdvancedSolarsClassic.MODID + "config.advanced_solar_generation_multiplier")
                    .defineInRange("ADVANCED_SOLAR_GENERATION_MULTIPLIER", 1.0, 0.0, 4.0);
            HYBRID_SOLAR_GENERATION_MULTIPLIER = builder.comment("Base energy generation multiplier values for hybrid solar - increase them for higher yields.")
                    .translation(AdvancedSolarsClassic.MODID + "config.hybrid_solar_generation_multiplier")
                    .defineInRange("HYBRID_SOLAR_GENERATION_MULTIPLIER", 1.0, 0.0, 4.0);
            ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER = builder.comment("Base energy generation multiplier values for ultimate hybrid solar - increase them for higher yields.")
                    .translation(AdvancedSolarsClassic.MODID + "config.ultimate_hybrid_solar_generation_multiplier")
                    .defineInRange("ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER", 1.0, 0.0, 4.0);
            builder.pop();
            builder.push("PowerValues");
            ADVANCED_SOLAR_HELMET_STORAGE = builder.defineInRange("ADVANCED_SOLAR_HELMET_STORAGE", 100000, 1, Integer.MAX_VALUE);
            HYBRID_SOLAR_HELMET_STORAGE = builder.defineInRange("HYBRID_SOLAR_HELMET_STORAGE", 1000000, 1, Integer.MAX_VALUE);
            ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE = builder.defineInRange("ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE", 10000000, 1, Integer.MAX_VALUE);

            ADVANCED_SOLAR_HELMET_TRANSFER = builder.defineInRange("ADVANCED_SOLAR_HELMET_TRANSFER", 100, 1, Integer.MAX_VALUE);
            HYBRID_SOLAR_HELMET_TRANSFER = builder.defineInRange("HYBRID_SOLAR_HELMET_TRANSFER", 1000, 1, Integer.MAX_VALUE);
            ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER = builder.defineInRange("ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER", 4000, 1, Integer.MAX_VALUE);
            builder.pop();

            builder.push("Misc");
            INGOT_IN_IRRADIANT_URANIUM = builder.comment("Determines what ingot is used in the center of the irradiant uranium recipe",
                            "If the selected option does not exist it will fall back to the default choice EnderPearl",
                            "Items associated with values: ENDERPEARL_URANIUM: Ic2c enderpearl enriched uranium,",
                            "URANIUM: #forge:ingots/uranium, URANIUM235: #forge:ingots/uranium235, URANIUM233: #forge:ingots/uranium235")
                    .translation(AdvancedSolarsClassic.MODID + "config.ingot_in_irradiant_uranium")
                    .defineEnum("INGOT_IN_IRRADIANT_URANIUM", Misc.CenterIngot.ENDERPEARL_URANIUM);
            builder.pop();
        }
    }

    private static void bakeCommonConfig() {
        POWER_GENERATION.ADVANCED_SOLAR_GENERATION_MULTIPLIER = COMMON_CONFIG.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get();
        POWER_GENERATION.HYBRID_SOLAR_GENERATION_MULTIPLIER = COMMON_CONFIG.HYBRID_SOLAR_GENERATION_MULTIPLIER.get();
        POWER_GENERATION.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER = COMMON_CONFIG.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get();
        POWER_VALUES.ADVANCED_SOLAR_HELMET_STORAGE = COMMON_CONFIG.ADVANCED_SOLAR_HELMET_STORAGE.get();
        POWER_VALUES.HYBRID_SOLAR_HELMET_STORAGE = COMMON_CONFIG.HYBRID_SOLAR_HELMET_STORAGE.get();
        POWER_VALUES.ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE = COMMON_CONFIG.ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE.get();
        POWER_VALUES.ADVANCED_SOLAR_HELMET_TRANSFER = COMMON_CONFIG.ADVANCED_SOLAR_HELMET_TRANSFER.get();
        POWER_VALUES.HYBRID_SOLAR_HELMET_TRANSFER = COMMON_CONFIG.HYBRID_SOLAR_HELMET_TRANSFER.get();
        POWER_VALUES.ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER = COMMON_CONFIG.ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER.get();
        MISC.INGOT_IN_IRRADIANT_URANIUM = COMMON_CONFIG.INGOT_IN_IRRADIANT_URANIUM.get();
    }
}
