package trinsdar.advancedsolars.util;

import ic2.core.block.base.IC2ContainerBlock;
import ic2.core.platform.registries.IC2Tiles;
import ic2.core.utils.tooltips.helper.ITooltipProvider;
import net.minecraft.world.level.block.entity.BlockEntityType;
import trinsdar.advancedsolars.blocks.BlockAdvancedSolarPanels;
import trinsdar.advancedsolars.blocks.BlockEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.blocks.BlockEntityMolecularTransformer;
import trinsdar.advancedsolars.items.ItemArmorAdvancedSolarHelmet;
import trinsdar.advancedsolars.items.ItemMisc;

public class Registry {
    public static final BlockEntityType<BlockEntityAdvancedSolarPanel> ADVANCED_SOLAR_PANEL_TYPE = IC2Tiles.createTile("advanced_solar_panel", BlockEntityAdvancedSolarPanel::new);
    public static final BlockEntityType<BlockEntityAdvancedSolarPanel.BlockEntityHybridSolarPanel> HYBRID_SOLAR_PANEL_TYPE = IC2Tiles.createTile("hybrid_solar_panel", BlockEntityAdvancedSolarPanel.BlockEntityHybridSolarPanel::new);
    public static final BlockEntityType<BlockEntityAdvancedSolarPanel.BlockEntityUltimateHybridSolarPanel> ULTIMATE_HYBRID_SOLAR_PANEL_TYPE = IC2Tiles.createTile("ultimate_hybrid_solar_panel", BlockEntityAdvancedSolarPanel.BlockEntityUltimateHybridSolarPanel::new);

    public static final BlockEntityType<BlockEntityMolecularTransformer> MOLECULAR_TRANSFORMER_TYPE = IC2Tiles.createTile("molecular_transformer", BlockEntityMolecularTransformer::new);

    public static final IC2ContainerBlock ADVANCED_SOLAR_PANEL = new BlockAdvancedSolarPanels("advanced_solar_panel", ADVANCED_SOLAR_PANEL_TYPE).addTooltip(ITooltipProvider.euReaderTooltip("tooltip.item.advanced_solars.eu_reader.production", (AdvancedSolarsConfig.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get() * 16))).addTooltip(ITooltipProvider.tooltip("tooltip.item.advanced_solars.eu_reader.lower_production", (AdvancedSolarsConfig.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get() * 2)));
    public static final IC2ContainerBlock HYBRID_SOLAR_PANEL = new BlockAdvancedSolarPanels("hybrid_solar_panel", HYBRID_SOLAR_PANEL_TYPE).addTooltip(ITooltipProvider.euReaderTooltip("tooltip.item.advanced_solars.eu_reader.production", (AdvancedSolarsConfig.HYBRID_SOLAR_GENERATION_MULTIPLIER.get() * 128))).addTooltip(ITooltipProvider.tooltip("tooltip.item.advanced_solars.eu_reader.lower_production", (AdvancedSolarsConfig.HYBRID_SOLAR_GENERATION_MULTIPLIER.get() * 16)));
    public static final IC2ContainerBlock ULTIMATE_HYBRID_SOLAR_PANEL = new BlockAdvancedSolarPanels("ultimate_hybrid_solar_panel", ULTIMATE_HYBRID_SOLAR_PANEL_TYPE).addTooltip(ITooltipProvider.euReaderTooltip("tooltip.item.advanced_solars.eu_reader.production", (AdvancedSolarsConfig.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get() * 1024))).addTooltip(ITooltipProvider.tooltip("tooltip.item.advanced_solars.eu_reader.lower_production", (AdvancedSolarsConfig.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get() * 128)));

    public static final IC2ContainerBlock MOLECULAR_TRANSFORMER = new BlockAdvancedSolarPanels("molecular_transformer", MOLECULAR_TRANSFORMER_TYPE).addTooltip(ITooltipProvider.LuV_MACHINE).addTooltip(ITooltipProvider.euReaderTooltip("tooltip.advanced_solars.molecular_transformer.consumption"));


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
    public static final ItemMisc MT_CORE = new ItemMisc("mt_core");

    public static final ItemArmorAdvancedSolarHelmet ADVANCED_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("advanced", (int) (16 * AdvancedSolarsConfig.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get()), (int) (2 * AdvancedSolarsConfig.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get()), 2);
    public static final ItemArmorAdvancedSolarHelmet HYBRID_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("hybrid", (int) (128 * AdvancedSolarsConfig.HYBRID_SOLAR_GENERATION_MULTIPLIER.get()), (int) (16 * AdvancedSolarsConfig.HYBRID_SOLAR_GENERATION_MULTIPLIER.get()), 3);
    public static final ItemArmorAdvancedSolarHelmet ULTIMATE_HYBRID_SOLAR_HELMET = new ItemArmorAdvancedSolarHelmet("ultimate_hybrid", (int) (1024 * AdvancedSolarsConfig.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get()), (int) (128 * AdvancedSolarsConfig.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get()), 4);

    public static void init() {
    }
}
