package trinsdar.advancedsolars.util;

import ic2.core.IC2;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.BlockAdvancedSolarPanels;
import trinsdar.advancedsolars.blocks.ItemBlockAdvancedSolarPanel;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.items.ItemArmorAdvancedSolarHelmet;
import trinsdar.advancedsolars.items.ItemMisc;

public class Registry {
    public static final BlockAdvancedSolarPanels
    advancedSolarPanel = new BlockAdvancedSolarPanels("advancedSolarPanel", AdvancedSolarLang.advancedSolarPanel),
    hybridSolarPanel = new BlockAdvancedSolarPanels("hybridSolarPanel", AdvancedSolarLang.hybridSolarPanel),
    ultimateHybridSolarPanel = new BlockAdvancedSolarPanels("ultimateSolarPanel", AdvancedSolarLang.ultimateHybridSolarPanel);

    public static final ItemMisc
    sunnarium = new ItemMisc("sunnarium", 0),
    sunnariumAlloy = new ItemMisc("sunnariumAlloy", 1),
    irradiantUranium = new ItemMisc("irradiantUranium", 2),
    enrichedSunnarium = new ItemMisc("enrichedSunnarium", 3),
    enrichedSunnariumAlloy = new ItemMisc("enrichedSunnariumAlloy", 4),
    irradiantGlassPane = new ItemMisc("irradiantGlassPane", 5),
    iridiumIronPlate = new ItemMisc("iridiumIronPlate", 6),
    reinforcedIridiumIronPlate = new ItemMisc("reinforcedIridiumIronPlate", 7),
    irradiantReinforcedPlate = new ItemMisc("irradiantReinforcedPlate", 8),
    sunnariumPart = new ItemMisc("sunnariumPart", 9),
    iridiumIngot = new ItemMisc("iridiumIngot", 10);

    public static final ItemArmorAdvancedSolarHelmet
    advancedSolarHelmet = new ItemArmorAdvancedSolarHelmet("advanced", 11, 16, 2, AdvancedSolarsConfig.powerValues.advancedSolarHelmetStorage, AdvancedSolarsConfig.powerValues.advancedSolarHelmetTransfer, 2, 800, 0.9D, ":textures/models/advancedsolarhelmet"),
    hybridSolarHelmet = new ItemArmorAdvancedSolarHelmet("hybrid", 12, 128, 16, AdvancedSolarsConfig.powerValues.hybridSolarHelmetStorage, AdvancedSolarsConfig.powerValues.hybridSolarHelmetTransfer, 3, 900, 1.0D, ":textures/models/hybridsolarhelmet"),
    ultimateHybridSolarHelmet = new ItemArmorAdvancedSolarHelmet("ultimateHybrid", 13, 1024, 128,  AdvancedSolarsConfig.powerValues.ultimateHybridSolarHelmetStorage, AdvancedSolarsConfig.powerValues.ultimateHybridSolarHelmetTransfer, getTier(AdvancedSolarsConfig.misc.enableUltimateHybridSolarHelmetTier4), 900, 1.0D, ":textures/models/ultimatesolarhelmet");

    private static int getTier(boolean config){
        return config ? 4 : 3;
    }

    public static void init(){
        if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarPanel){
            IC2.getInstance().createBlock(advancedSolarPanel, ItemBlockAdvancedSolarPanel.class);
        }
        if (AdvancedSolarsConfig.enabledItems.enableHybridSolarPanel){
            IC2.getInstance().createBlock(hybridSolarPanel, ItemBlockAdvancedSolarPanel.class);
        }
        if (AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel){
            IC2.getInstance().createBlock(ultimateHybridSolarPanel, ItemBlockAdvancedSolarPanel.class);
        }
        if (AdvancedSolarsConfig.enabledItems.enableMiscCraftingItems){
            IC2.getInstance().createItem(sunnarium);
            IC2.getInstance().createItem(sunnariumAlloy);
            IC2.getInstance().createItem(irradiantUranium);
            IC2.getInstance().createItem(enrichedSunnarium);
            IC2.getInstance().createItem(enrichedSunnariumAlloy);
            IC2.getInstance().createItem(irradiantGlassPane);
            IC2.getInstance().createItem(iridiumIronPlate);
            IC2.getInstance().createItem(reinforcedIridiumIronPlate);
            IC2.getInstance().createItem(irradiantReinforcedPlate);
            IC2.getInstance().createItem(sunnariumPart);
            IC2.getInstance().createItem(iridiumIngot);
        }
        if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarHelmet){
            IC2.getInstance().createItem(advancedSolarHelmet);
        }
        if (AdvancedSolarsConfig.enabledItems.enableHybridSolarHelmet){
            IC2.getInstance().createItem(hybridSolarHelmet);
        }
        if (AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel){
            IC2.getInstance().createItem(ultimateHybridSolarHelmet);
        }
    }

    public static void registerTiles()
    {
        if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarPanel){
            GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityAdvancedSolarPanel"));
        }
        if (AdvancedSolarsConfig.enabledItems.enableHybridSolarPanel){
            GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityHybridSolarPanel"));
        }
        if (AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel){
            GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityUltimateHybridSolarPanel"));
        }
    }
}
