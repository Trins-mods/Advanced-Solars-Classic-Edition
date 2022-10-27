package trinsdar.advancedsolars.util;

import trinsdar.advancedsolars.blocks.BlockAdvancedSolarPanels;
import trinsdar.advancedsolars.items.ItemArmorAdvancedSolarHelmet;
import trinsdar.advancedsolars.items.ItemMisc;

public class Registry {
    public static final BlockAdvancedSolarPanels
            ADVANCED_SOLAR_PANEL = new BlockAdvancedSolarPanels("advancedSolarPanel"),
    HYBRID_SOLAR_PANEL = new BlockAdvancedSolarPanels("hybridSolarPanel"),
    ULTIMATE_HYBRID_SOLAR_PANEL = new BlockAdvancedSolarPanels("ultimateSolarPanel");

    public static final ItemMisc SUNNARIUM = new ItemMisc("sunnarium");
    public static final ItemMisc SUNNARIUM_ALLOY = new ItemMisc("sunnarium_alloy");
    public static final ItemMisc IRRADIANT_URANIUM = new ItemMisc("irradiant_uranium");
    public static final ItemMisc ENRICHED_SUNNARIUM = new ItemMisc("enriched_sunnarium");
    public static final ItemMisc ENRICHED_SUNNARIUM_ALLOY = new ItemMisc("enriched_sunnarium_alloy");
    public static final ItemMisc IRRADIANT_GLASS_PANE = new ItemMisc("irradiant_glass_pane");
    public static final ItemMisc IRIDIUM_IRON_PLATE = new ItemMisc("iridium_iron_plate");
    public static final ItemMisc REINFORCED_IRIDIUM_IRON_PLATE = new ItemMisc("reinforced_iridium_iron_plate");
    public static final ItemMisc IRRADIANT_REINFORCED_PLATE = new ItemMisc("irradiant_reinforced_plate");
    public static final ItemMisc SUNNARIUM_PART = new ItemMisc("sunnarium_part");
    public static final ItemMisc IRIDIUM_INGOT = new ItemMisc("iridium_ingot");

    public static final ItemArmorAdvancedSolarHelmet
            ADVANCED_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("advanced", 11, 16, 2, AdvancedSolarsConfig.powerValues.advancedSolarHelmetStorage, AdvancedSolarsConfig.powerValues.advancedSolarHelmetTransfer, 2, 800, 0.9D, ":textures/models/advancedsolarhelmet"),
    HYBRID_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("hybrid", 12, 128, 16, AdvancedSolarsConfig.powerValues.hybridSolarHelmetStorage, AdvancedSolarsConfig.powerValues.hybridSolarHelmetTransfer, 3, 900, 1.0D, ":textures/models/hybridsolarhelmet"),
    ULTIMATE_HYBRID_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("ultimate_hybrid", 13, 1024, 128,  AdvancedSolarsConfig.powerValues.ultimateHybridSolarHelmetStorage, AdvancedSolarsConfig.powerValues.ultimateHybridSolarHelmetTransfer, getTier(AdvancedSolarsConfig.misc.enableUltimateHybridSolarHelmetTier4), 900, 1.0D, ":textures/models/ultimatesolarhelmet");

    private static int getTier(boolean config){
        return config ? 4 : 3;
    }

    public static void init(){
    }

    public static void registerTiles() {
    }
}
