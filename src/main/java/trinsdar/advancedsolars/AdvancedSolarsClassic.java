package trinsdar.advancedsolars;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Logger;
import trinsdar.advancedsolars.proxy.CommonProxy;

@Mod(modid = AdvancedSolarsClassic.MODID)
public class AdvancedSolarsClassic {
    public static final String MODID = "advanced_solars";
    public static final String MODNAME = "Advanced Solars Classic Edition";
    public static final String MODVERSION = "@VERSION@";
    public static final String DEPENDS ="required-after:ic2;required-after:ic2-classic-spmod";

    @SidedProxy(clientSide = "trinsdar.advancedsolars.proxy.ClientProxy", serverSide = "trinsdar.advancedsolars.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static AdvancedSolarsClassic instance;

    public static Logger logger;

    public AdvancedSolarsClassic(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public synchronized void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent e) {
        proxy.init(e);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent e) {
        proxy.postInit(e);
    }

    @SubscribeEvent
    public void onConfigChangedEvent(ConfigChangedEvent.OnConfigChangedEvent event)
    {
        if (event.getModID().equals(MODID))
        {
            ConfigManager.sync(MODID, Config.Type.INSTANCE);
        }
    }
}
