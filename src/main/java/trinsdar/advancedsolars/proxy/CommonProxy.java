package trinsdar.advancedsolars.proxy;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import trinsdar.advancedsolars.util.Config;
import trinsdar.advancedsolars.util.Registry;

import java.io.File;

public class CommonProxy {
    public static Configuration config;

    public void preInit(FMLPreInitializationEvent e) {
        File directory = e.getModConfigurationDirectory();
        config = new Configuration(new File(directory.getPath(), "ic2/advancedsolars.cfg"));
        Config.readConfig();
        MinecraftForge.EVENT_BUS.register(Registry.class);
        Registry.registerTiles();
    }

    public void init(FMLInitializationEvent e) {
        // temporarily empty init method
    }

    public void postInit(FMLPostInitializationEvent e) {
        // temporarily empty post init method
    }
}
