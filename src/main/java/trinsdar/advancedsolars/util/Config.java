package trinsdar.advancedsolars.util;

import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.proxy.CommonProxy;

public class Config {


    private static final String CATEGORY_POWER_GENERATION = "power generation";
    private static final String CATEGORY_POWER_VALUES = "power values";
    private static final String CATEGORY_ENABLED_ITEMS = "enabled items";
    private static final String CATEGORY_MISC = "misc";

    public static float
    energyGeneratorSolarAdvanced = 1.0F,
    energyGeneratorSolarHybrid = 1.0F,
    energyGeneratorSolarUltimateHybrid = 1.0F;

    public static int
    advancedSolarHelmetStorage = 100000,
    hybridSolarHelmetStorage = 1000000,
    ultimateHybridSolarHelmetStorage = 1000000;

    public static int
    advancedSolarHelmetTransfer = 100,
    hybridSolarHelmetTransfer = 1000,
    ultimateHybridSolarHelmetTransfer = 2000;

    public static boolean
    enableAdvancedSolarHelmet = true,
    enableHybridSolarHelmet = true,
    enableUltimateHybridSolarHelmet = true,
    enableAdvancedSolarPanel = true,
    enableHybridSolarPanel = true,
    enableUltimateHybridSolarPanel = true,
    enableMiscCraftingItems = true;

    public static boolean enableUltimateHybridSolarHelmetTier4 = false;
    public static void readConfig() {
        Configuration cfg = CommonProxy.config;
        try {
            cfg.load();
            initGeneralConfig(cfg);
        } catch (Exception e1) {
            AdvancedSolarsClassic.logger.log(Level.ERROR, "Problem loading config file!", e1);
        } finally {
            if (cfg.hasChanged()) {
                cfg.save();
            }
        }
    }

    private static void initGeneralConfig(Configuration cfg) {
        cfg.addCustomCategoryComment(CATEGORY_POWER_VALUES, "Set the max EU storage and max EU transfer of each item here.");
        advancedSolarHelmetStorage = cfg.getInt("advancedSolarHelmetStorage", CATEGORY_POWER_VALUES, advancedSolarHelmetStorage, 1, Integer.MAX_VALUE, "");
        hybridSolarHelmetStorage = cfg.getInt("hybridSolarHelmetStorage", CATEGORY_POWER_VALUES, hybridSolarHelmetStorage, 1, Integer.MAX_VALUE, "");
        ultimateHybridSolarHelmetStorage = cfg.getInt("ultimateHybridSolarHelmetStorage", CATEGORY_POWER_VALUES, ultimateHybridSolarHelmetStorage, 1, Integer.MAX_VALUE, "");
        advancedSolarHelmetTransfer = cfg.getInt("advancedSolarHelmetTransfer", CATEGORY_POWER_VALUES, advancedSolarHelmetTransfer, 1, Integer.MAX_VALUE, "");
        hybridSolarHelmetTransfer = cfg.getInt("hybridSolarHelmetTransfer", CATEGORY_POWER_VALUES, hybridSolarHelmetTransfer, 1, Integer.MAX_VALUE, "");
        ultimateHybridSolarHelmetTransfer = cfg.getInt("ultimateHybridSolarHelmetTransfer", CATEGORY_POWER_VALUES, ultimateHybridSolarHelmetTransfer, 1, Integer.MAX_VALUE, "");

        cfg.addCustomCategoryComment(CATEGORY_ENABLED_ITEMS, "Enable or Disable each item here.");

        enableAdvancedSolarHelmet = cfg.getBoolean("enableAdvancedSolarHelmet", CATEGORY_ENABLED_ITEMS, enableAdvancedSolarHelmet, "");
        enableHybridSolarHelmet = cfg.getBoolean("enableHybridSolarHelmet", CATEGORY_ENABLED_ITEMS, enableHybridSolarHelmet, "");
        enableUltimateHybridSolarHelmet = cfg.getBoolean("enableUltimateHybridSolarHelmet", CATEGORY_ENABLED_ITEMS, enableUltimateHybridSolarHelmet, "");
        enableAdvancedSolarPanel = cfg.getBoolean("enableAdvancedSolarPanel", CATEGORY_ENABLED_ITEMS, enableAdvancedSolarPanel, "");
        enableHybridSolarPanel = cfg.getBoolean("enableHybridSolarPanel", CATEGORY_ENABLED_ITEMS, enableHybridSolarPanel, "");
        enableUltimateHybridSolarPanel = cfg.getBoolean("enableUltimateHybridSolarPanel", CATEGORY_ENABLED_ITEMS, enableUltimateHybridSolarPanel, "");
        enableMiscCraftingItems = cfg.getBoolean("enableMiscCraftingItems", CATEGORY_ENABLED_ITEMS, enableMiscCraftingItems, "");
        energyGeneratorSolarAdvanced = cfg.getFloat("energyGeneratorSolarAdvanced", CATEGORY_POWER_GENERATION, energyGeneratorSolarAdvanced, 0.01F, 5.0F, "Base energy generation multiplier values for advanced solar - increase them for higher yeilds.");
        energyGeneratorSolarHybrid = cfg.getFloat("energyGeneratorSolarHybrid", CATEGORY_POWER_GENERATION, energyGeneratorSolarHybrid, 0.01F, 5.0F, "Base energy generation multiplier values for hybrid solar - increase them for higher yeilds.");
        energyGeneratorSolarUltimateHybrid = cfg.getFloat("energyGeneratorSolarUltimateHybrid", CATEGORY_POWER_GENERATION, energyGeneratorSolarUltimateHybrid, 0.01F, 5.0F, "Base energy generation multiplier values for ultimate hybrid - increase them for higher yeilds. Note: Higer values then 1.0 for this might not have an effect as the max input of the solar panel is 512/t");

        enableUltimateHybridSolarHelmetTier4 = cfg.getBoolean("enableUltimateHybridSolarHelmetTier4", CATEGORY_MISC, enableUltimateHybridSolarHelmetTier4, "Enables the ultimate hybrid solar helmet being tier 4. WARNING: If you don't have gtclassic or tech reborn or another mod with tier 4 energy storage, you will have to get a pesu in order to charge it, unless of course you stand in the sun.");
    }
}
