package trinsdar.advancedsolars.util;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.proxy.CommonProxy;

@Config(modid = AdvancedSolarsClassic.MODID, name = "ic2/advancedsolars")
public class AdvancedSolarsConfig {

    @Config.Comment("Set the solar panel generation multipliers here.")
    @Config.RequiresMcRestart
    public static PowerGeneration powerGeneration = new PowerGeneration();

    public static class PowerGeneration{
        @Config.Comment("Base energy generation multiplier values for advanced solar - increase them for higher yeilds.")
        public float energyGeneratorSolarAdvanced = 1.0F;
        @Config.Comment("Base energy generation multiplier values for hybrid solar - increase them for higher yeilds.")
        public float energyGeneratorSolarHybrid = 1.0F;
        @Config.Comment("Base energy generation multiplier values for ultimate hybrid - increase them for higher yeilds.")
        public float energyGeneratorSolarUltimateHybrid = 1.0F;
    }

    @Config.Comment("Set the max EU storage and max EU transfer of each item here.")
    @Config.RequiresMcRestart
    public static PowerValues powerValues = new PowerValues();

    public static class PowerValues{
        @Config.RangeInt(min = 1)
        public int advancedSolarHelmetStorage = 100000;
        @Config.RangeInt(min = 1)
        public int hybridSolarHelmetStorage = 1000000;
        @Config.RangeInt(min = 1)
        public int ultimateHybridSolarHelmetStorage = 1000000;
        @Config.RangeInt(min = 1)
        public int advancedSolarHelmetTransfer = 100;
        @Config.RangeInt(min = 1)
        public int hybridSolarHelmetTransfer = 1000;
        @Config.RangeInt(min = 1)
        public int ultimateHybridSolarHelmetTransfer = 2000;
    }

    @Config.Comment("Enable or Disable each item here.")
    @Config.RequiresMcRestart
    public static EnabledItems enabledItems = new EnabledItems();

    public static class EnabledItems{
        public boolean enableAdvancedSolarHelmet = true;
        public boolean enableHybridSolarHelmet = true;
        public boolean enableUltimateHybridSolarHelmet = true;
        public boolean enableAdvancedSolarPanel = true;
        public boolean enableHybridSolarPanel = true;
        public boolean enableUltimateHybridSolarPanel = true;
        public boolean enableMiscCraftingItems = true;
    }

    @Config.Comment("Misc. stuff")
    @Config.RequiresMcRestart
    public static Misc misc = new Misc();

    public static class Misc{
        @Config.Comment({
                "Enables the ultimate hybrid solar helmet being tier 4.",
                "WARNING: If you don't have gtclassic or tech reborn or another mod with tier 4 energy storage,",
                "you will have to get a pesu in order to charge it, unless of course you stand in the sun."
        })
        public boolean enableUltimateHybridSolarHelmetTier4 = false;
    }
}
