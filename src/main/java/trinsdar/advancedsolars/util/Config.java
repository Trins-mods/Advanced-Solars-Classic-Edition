package trinsdar.advancedsolars.util;

import ic2.core.IC2;
import ic2.core.platform.config.ConfigEntry;
import ic2.core.platform.config.IC2Config;
import ic2.core.platform.config.components.IConfigNotify;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.proxy.CommonProxy;

public class Config {


    private static final String CATEGORY_GENERAL = "general";

    // general
    public static float
    energyGeneratorSolarAdvanced = 1.0F,
    energyGeneratorSolarHybrid = 1.0F,
    energyGeneratorSolarUltimateHybrid = 1.0F;

    // generation
    public static boolean classicRecipe= false;
    public static boolean ic2cRecipes = true;
    public static boolean harderRecipes = true;

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
        cfg.addCustomCategoryComment(CATEGORY_GENERAL, "General");
        classicRecipe = cfg.getBoolean("classicRecipes", CATEGORY_GENERAL, classicRecipe,
                "Enables recipes based off ones from 1.4.7");
        ic2cRecipes = cfg.getBoolean("ic2cRecipes", CATEGORY_GENERAL, ic2cRecipes,
                "Enables Recipes that use items from ic2 classic such as compact solar panels");
        harderRecipes = cfg.getBoolean("harderRecipes", CATEGORY_GENERAL, harderRecipes,
                "Enables Recipes based off the classic harder mode. Only applicable when classicRecipes is enabled.");

        energyGeneratorSolarAdvanced = cfg.getFloat("energyGeneratorSolarAdvanced", CATEGORY_GENERAL, energyGeneratorSolarAdvanced, 0.01F, 5.0F, "Base energy generation multiplier values for advanced solar - increase them for higher yeilds.");
        energyGeneratorSolarHybrid = cfg.getFloat("energyGeneratorSolarHybrid", CATEGORY_GENERAL, energyGeneratorSolarHybrid, 0.01F, 5.0F, "Base energy generation multiplier values for hybrid solar - increase them for higher yeilds.");
        energyGeneratorSolarUltimateHybrid = cfg.getFloat("energyGeneratorSolarUltimateHybrid", CATEGORY_GENERAL, energyGeneratorSolarUltimateHybrid, 0.01F, 5.0F, "Base energy generation multiplier values for ultimate hybrid - increase them for higher yeilds. Note: Higer values then 1.0 for this might not have an effect as the max input of the solar panel is 512/t");
    }
}
