package trinsdar.advancedsolars.util;

import ic2.core.platform.lang.components.base.LangComponentHolder;
import ic2.core.platform.lang.components.base.LangComponentHolder.LocaleTileInfoComp;
import ic2.core.platform.lang.components.base.LocaleComp;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

public class AdvancedSolarLang {
    public static LocaleComp advancedSolarPanel = new LangComponentHolder.LocaleBlockComp("tile." + AdvancedSolarsClassic.MODID + ".advancedSolarPanel");
    public static LocaleComp hybridSolarPanel = new LangComponentHolder.LocaleBlockComp("tile." + AdvancedSolarsClassic.MODID + ".hybridSolarPanel");
    public static LocaleComp ultimateHybridSolarPanel = new LangComponentHolder.LocaleBlockComp("tile." + AdvancedSolarsClassic.MODID + ".ultimateSolarPanel");

    public static LocaleComp storage = new LocaleTileInfoComp("tileInfo.storage.name");
    public static LocaleComp maxOutput = new LocaleTileInfoComp("tileInfo.maxOutput.name");
    public static LocaleComp generating = new LocaleTileInfoComp("tileInfo.generating.name");
}
