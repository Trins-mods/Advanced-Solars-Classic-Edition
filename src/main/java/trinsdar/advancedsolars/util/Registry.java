package trinsdar.advancedsolars.util;

import ic2.core.platform.registries.IC2Tiles;
import net.minecraft.world.level.block.entity.BlockEntityType;
import trinsdar.advancedsolars.blocks.BlockAdvancedSolarPanels;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.items.ItemArmorAdvancedSolarHelmet;
import trinsdar.advancedsolars.items.ItemMisc;

public class Registry {
    public static final BlockEntityType<TileEntityAdvancedSolarPanel> ADVANCED_SOLAR_PANEL_TYPE = IC2Tiles.createTile("advanced_solar_panel", TileEntityAdvancedSolarPanel::new);
    public static final BlockEntityType<TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel> HYBRID_SOLAR_PANEL_TYPE = IC2Tiles.createTile("hybrid_solar_panel", TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel::new);
    public static final BlockEntityType<TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel> ULTIMATE_HYBRID_SOLAR_PANEL_TYPE = IC2Tiles.createTile("ultimate_hybrid_solar_panel", TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel::new);

    public static final BlockAdvancedSolarPanels ADVANCED_SOLAR_PANEL = new BlockAdvancedSolarPanels("advanced_solar_panel", ADVANCED_SOLAR_PANEL_TYPE);
    public static final BlockAdvancedSolarPanels HYBRID_SOLAR_PANEL = new BlockAdvancedSolarPanels("hybrid_solar_panel", HYBRID_SOLAR_PANEL_TYPE);
    public static final BlockAdvancedSolarPanels ULTIMATE_HYBRID_SOLAR_PANEL = new BlockAdvancedSolarPanels("ultimate_hybrid_solar_panel", ULTIMATE_HYBRID_SOLAR_PANEL_TYPE);



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

    public static final ItemArmorAdvancedSolarHelmet ADVANCED_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("advanced", 16, 2, 2);
    public static final ItemArmorAdvancedSolarHelmet HYBRID_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("hybrid", 128, 16, 3);
    public static final ItemArmorAdvancedSolarHelmet ULTIMATE_HYBRID_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("ultimate_hybrid", 1024, 128,  4);

    public static void init(){
    }
}
