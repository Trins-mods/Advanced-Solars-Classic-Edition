package trinsdar.advancedsolars.util;

import ic2.core.IC2;
import ic2.core.item.block.ItemBlockRare;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.BlockAdvancedSolarPanels;
import trinsdar.advancedsolars.blocks.ItemBlockAdvancedSolarPanel;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.items.ItemArmorAdvancedSolarHelmet;
import trinsdar.advancedsolars.items.ItemMisc;

public class Registry {
    public static final BlockAdvancedSolarPanels
    advancedSolarPanel = new BlockAdvancedSolarPanels("advancedSolarPanel"),
    hybridSolarPanel = new BlockAdvancedSolarPanels("hybridSolarPanel"),
    ultimateHybridSolarPanel = new BlockAdvancedSolarPanels("ultimateSolarPanel");

    public static final ItemMisc
    sunnarium = new ItemMisc("sunnarium", 0),
    sunnariumAlloy = new ItemMisc("sunnariumAlloy", 1),
    irradiantUranium = new ItemMisc("iridiantUranium", 2),
    enrichedSunnarium = new ItemMisc("enrichedSunnarium", 3),
    enrichedSunnariumAlloy = new ItemMisc("enrichedSunnariumAlloy", 4),
    irradiantGlassPane = new ItemMisc("irradiantGlassPane", 5),
    iridiumIronPlate = new ItemMisc("iridiumIronPlate", 6),
    reinforcedIridiumIronPlate = new ItemMisc("reinforcedIridiumIronPlate", 7),
    irradiantReinforcedPlate = new ItemMisc("irradiantReinforcedPlate", 8),
    sunnariumPart = new ItemMisc("sunnariumPart", 9),
    iridiumIngot = new ItemMisc("iridiumIngot", 10);

    public static final ItemArmorAdvancedSolarHelmet
    advancedSolarHelmet = new ItemArmorAdvancedSolarHelmet("advanced", 11, 8, 1, 100000, 100, 2, 800, 0.9D, ":textures/models/advancedsolarhelmet"),
    hybridSolarHelmet = new ItemArmorAdvancedSolarHelmet("hybrid", 12, 64, 8, 1000000, 1000, 3, 900, 1.0D, ":textures/models/hybridsolarhelmet"),
    ultimateHybridSolarHelmet = new ItemArmorAdvancedSolarHelmet("ultimateHybrid", 13, 512, 64, 1000000, 2000, 3, 900, 1.0D, ":textures/models/ultimatesolarhelmet");


    public static void init(){
        IC2.getInstance().createBlock(advancedSolarPanel, ItemBlockAdvancedSolarPanel.class);
        IC2.getInstance().createBlock(hybridSolarPanel, ItemBlockAdvancedSolarPanel.class);
        IC2.getInstance().createBlock(ultimateHybridSolarPanel, ItemBlockAdvancedSolarPanel.class);

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
        IC2.getInstance().createItem(advancedSolarHelmet);
        IC2.getInstance().createItem(hybridSolarHelmet);
        IC2.getInstance().createItem(ultimateHybridSolarHelmet);
    }

    public static void registerTiles()
    {
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityAdvancedSolarPanel"));
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityHybridSolarPanel"));
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityUltimateHybridSolarPanel"));
    }
}
