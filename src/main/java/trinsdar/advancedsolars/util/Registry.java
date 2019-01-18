package trinsdar.advancedsolars.util;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.BlockAdvancedSolarPanels;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;

public class Registry {
    public static final BlockAdvancedSolarPanels
    advancedSolarPanel = new BlockAdvancedSolarPanels("advancedSolarPanel"),
    hybridSolarPanel = new BlockAdvancedSolarPanels("hybridSolarPanel"),
    ultimateSolarPanel = new BlockAdvancedSolarPanels("ultimateSolarPanel");


    public static final Block[] blocks =
            {
                    advancedSolarPanel,
                    hybridSolarPanel,
                    ultimateSolarPanel
            };

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event)
    {
        IForgeRegistry registry = event.getRegistry();

        for (Block block : blocks)
        {
            registry.register(block);
        }

    }

    public static void registerTiles()
    {
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityAdvancedSolarPanel"));
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityHybridSolarPanel"));
        GameRegistry.registerTileEntity(TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel.class, new ResourceLocation(AdvancedSolarsClassic.MODID, "tileEntityUltimateHybridSolarPanel"));
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event)
    {
        IForgeRegistry registry = event.getRegistry();

        for (Block block : blocks)
        {
            registry.register(new ItemBlock(block).setRegistryName(block.getRegistryName()).setUnlocalizedName(block.getUnlocalizedName()));
        }
    }
}
