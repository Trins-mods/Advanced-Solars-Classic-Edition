package trinsdar.advancedsolars.util;

import carbonconfiglib.CarbonConfig;
import carbonconfiglib.config.Config;
import carbonconfiglib.config.ConfigEntry;
import carbonconfiglib.config.ConfigHandler;
import carbonconfiglib.config.ConfigSection;
import com.electronwill.nightconfig.core.CommentedConfig;
import com.electronwill.nightconfig.core.file.FileNotFoundAction;
import com.electronwill.nightconfig.toml.TomlParser;
import ic2.core.IC2;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Files;
import java.nio.file.Path;

public class AdvancedSolarsConfig {
    public static ConfigEntry.IntValue ADVANCED_SOLAR_HELMET_STORAGE, HYBRID_SOLAR_HELMET_STORAGE, ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE;
    public static ConfigEntry.IntValue ADVANCED_SOLAR_HELMET_TRANSFER, HYBRID_SOLAR_HELMET_TRANSFER, ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER;

    public static ConfigEntry.DoubleValue ADVANCED_SOLAR_GENERATION_MULTIPLIER, HYBRID_SOLAR_GENERATION_MULTIPLIER, ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
    public static ConfigEntry.EnumValue<CenterIngot> INGOT_IN_IRRADIANT_URANIUM;
    static ConfigHandler CONFIG;

    public static void createConfig(){
        Config config = new Config("ic2c/advanced_solars");
        ConfigSection powerGeneration = config.add("power_generation");
        ADVANCED_SOLAR_GENERATION_MULTIPLIER = powerGeneration.addDouble("advanced_solar_generation_multiplier", 1.0, "Base energy generation multiplier values for advanced solar - increase them for higher yields.").setRange(0.0, 4.0);
        HYBRID_SOLAR_GENERATION_MULTIPLIER = powerGeneration.addDouble("hybrid_solar_generation_multiplier", 1.0, "Base energy generation multiplier values for hybrid solar - increase them for higher yields.").setRange(0.0, 4.0);
        ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER = powerGeneration.addDouble("ultimate_hybrid_solar_generation_multiplier", 1.0, "Base energy generation multiplier values for ultimate hybrid solar - increase them for higher yields.").setRange(0.0, 4.0);
        ConfigSection powerValues = config.add("power_values");
        ADVANCED_SOLAR_HELMET_STORAGE = powerValues.addInt("advanced_solar_helmet_storage", 100000).setMin(1);
        HYBRID_SOLAR_HELMET_STORAGE = powerValues.addInt("hybrid_solar_helmet_storage", 1000000).setMin(1);
        ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE = powerValues.addInt("ultimate_hybrid_solar_helmet_storage", 10000000).setMin(1);
        ADVANCED_SOLAR_HELMET_TRANSFER = powerValues.addInt("advanced_solar_helmet_transfer", 100).setMin(1);
        HYBRID_SOLAR_HELMET_TRANSFER = powerValues.addInt("hybrid_solar_helmet_transfer", 1000).setMin(1);
        ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER = powerValues.addInt("ultimate_hybrid_solar_helmet_transfer", 4000).setMin(1);
        ConfigSection misc = config.add("misc");
        INGOT_IN_IRRADIANT_URANIUM = misc.addEnum("ingot_in_irradiant_uranium", CenterIngot.ENDERPEARL_URANIUM, CenterIngot.class,
                "Determines what ingot is used in the center of the irradiant uranium recipe",
                "If the selected option does not exist it will fall back to the default choice EnderPearl",
                "Items associated with values: ENDERPEARL_URANIUM: Ic2c enderpearl enriched uranium,",
                "URANIUM: #forge:ingots/uranium, URANIUM235: #forge:ingots/uranium235, URANIUM233: #forge:ingots/uranium235");
        CONFIG = CarbonConfig.CONFIGS.createConfig(config);
        CONFIG.register();

        Path configFile = Path.of(FMLPaths.CONFIGDIR.get().toString(),  "advanced_solars-common.toml");

        if (!Files.exists(configFile)){
            return;
        }
        CommentedConfig forgeConfig = PARSER.parse(configFile, FileNotFoundAction.READ_NOTHING);
        try {
            String path = "PowerGeneration";
            ADVANCED_SOLAR_GENERATION_MULTIPLIER.set(getDouble(forgeConfig, path + ".ADVANCED_SOLAR_GENERATION_MULTIPLIER"));
            HYBRID_SOLAR_GENERATION_MULTIPLIER.set(getDouble(forgeConfig, path + ".HYBRID_SOLAR_GENERATION_MULTIPLIER"));
            ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.set(getDouble(forgeConfig, path + ".ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER"));
            path = "PowerValues";
            ADVANCED_SOLAR_HELMET_STORAGE.set(getInt(forgeConfig, path + ".ADVANCED_SOLAR_HELMET_STORAGE"));
            HYBRID_SOLAR_HELMET_STORAGE.set(getInt(forgeConfig, path + ".HYBRID_SOLAR_HELMET_STORAGE"));
            ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE.set(getInt(forgeConfig, path + ".ULTIMATE_HYBRID_SOLAR_HELMET_STORAGE"));
            ADVANCED_SOLAR_HELMET_TRANSFER.set(getInt(forgeConfig, path + ".ADVANCED_SOLAR_HELMET_TRANSFER"));
            HYBRID_SOLAR_HELMET_TRANSFER.set(getInt(forgeConfig, path + ".HYBRID_SOLAR_HELMET_TRANSFER"));
            ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER.set(getInt(forgeConfig, path + ".ULTIMATE_HYBRID_SOLAR_HELMET_TRANSFER"));
            path = "Misc";
            if (forgeConfig.get(path + ".INGOT_IN_IRRADIANT_URANIUM") instanceof String string){
                try {
                    CenterIngot centerIngot = CenterIngot.valueOf(string);
                    INGOT_IN_IRRADIANT_URANIUM.set(centerIngot);
                } catch (IllegalArgumentException ignored){
                }
            }
            Files.move(configFile, Path.of(FMLPaths.CONFIGDIR.get().toString(),  "advanced_solars-common-replaced.toml.bak"));
            CONFIG.save();
        } catch (Exception e){
            IC2.LOGGER.error(e);
        }
    }


    private static final TomlParser PARSER = new TomlParser();

    private static int getInt(CommentedConfig config, String path){
        if (!config.contains(path)){
            throw new RuntimeException("Path does not exist in old config!");
        }
        return config.getInt(path);
    }

    private static double getDouble(CommentedConfig config, String path){
        if (!config.contains(path)){
            throw new RuntimeException("Path does not exist in old config!");
        }
        return config.getRaw(path);
    }

    public enum CenterIngot {
        ENDERPEARL_URANIUM,
        URANIUM,
        URANIUM235,
        URANIUM233;
    }
}
