package trinsdar.advancedsolars.util;

import ic2.core.platform.lang.components.base.LangComponentHolder;
import ic2.core.platform.lang.components.base.LangComponentHolder.LocaleItemInfoComp;
import ic2.core.platform.lang.components.base.LangComponentHolder.LocaleTileInfoComp;
import ic2.core.platform.lang.components.base.LocaleComp;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

public class AdvancedSolarLang {
    public static LocaleComp advancedSolarPanel = new LangComponentHolder.LocaleBlockComp("tile.advancedSolarPanel");
    public static LocaleComp hybridSolarPanel = new LangComponentHolder.LocaleBlockComp("tile.hybridSolarPanel");
    public static LocaleComp ultimateHybridSolarPanel = new LangComponentHolder.LocaleBlockComp("tile.ultimateSolarPanel");

    public static LocaleComp storage = new LocaleTileInfoComp("tileInfo.storage.name");
    public static LocaleComp maxOutput = new LocaleTileInfoComp("tileInfo.maxOutput.name");
    public static LocaleComp generating = new LocaleTileInfoComp("tileInfo.generating.name");

    public static LocaleComp solarPanelTier = new LocaleItemInfoComp("itemInfo.solarPanelTier.name");
    public static LocaleComp helmetProduction = new LocaleItemInfoComp("itemInfo.helmetProduction.name");
    public static LocaleComp helmetLowerPorduction = new LocaleItemInfoComp("itemInfo.helmetLowerProduction.name");
    public static LocaleComp electricLowerProduction = new LocaleItemInfoComp("itemInfo.electricLowerProduction.name");
}
