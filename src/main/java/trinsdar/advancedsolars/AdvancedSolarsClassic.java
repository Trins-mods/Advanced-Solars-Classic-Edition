package trinsdar.advancedsolars;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import trinsdar.advancedsolars.proxy.CommonProxy;

@Mod(modid = AdvancedSolarsClassic.MODID, name = AdvancedSolarsClassic.MODNAME, version = AdvancedSolarsClassic.MODVERSION, dependencies = AdvancedSolarsClassic.DEPENDS)
public class AdvancedSolarsClassic {
    public static final String MODID = "advancedsolars";
    public static final String MODNAME = "Advanced Solars Classic Edition";
    public static final String MODVERSION = "@VERSION@";
    public static final String DEPENDS ="required-after:ic2;required-after:ic2-classic-spmod";

    @SidedProxy(clientSide = "trinsdar.advancedsolars.proxy.ClientProxy", serverSide = "trinsdar.advancedsolars.proxy.CommonProxy")
    public static CommonProxy proxy;

    @Mod.Instance
    public static AdvancedSolarsClassic instance;

    public static Logger logger;

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
}
