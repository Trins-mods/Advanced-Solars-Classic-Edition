package trinsdar.advancedsolars;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

@Mod(AdvancedSolarsClassic.MODID)
public class AdvancedSolarsClassic {
    public static final String MODID = "advanced_solars";

    public AdvancedSolarsClassic(){
        FMLJavaModLoadingContext.get().getModEventBus().register(this);
    }

    @SubscribeEvent
    public void onRegister(RegisterEvent event){
        if (event.getRegistryKey().equals(ForgeRegistries.Keys.BLOCKS)){
            Registry.init();
        }
    }

    @SubscribeEvent
    public void onCommonSetup(FMLCommonSetupEvent event){
        AdvancedSolarsRecipes.init();
    }
}
