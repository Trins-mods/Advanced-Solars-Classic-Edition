package trinsdar.advancedsolars;

import ic2.core.platform.events.impl.WikiEvent;
import ic2.core.wiki.base.IChapterBuilder;
import ic2.core.wiki.components.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import trinsdar.advancedsolars.util.Registry;

import java.util.ArrayList;
import java.util.List;

public class AdvancedSolarsWiki {
    @SubscribeEvent
    public static void onWikiEvent(WikiEvent event){
        if (event instanceof WikiEvent.WikiSetupEvent setupEvent){
            setupEvent.builder.addChapterBuilder(AdvancedSolarsWiki::createWiki);
        }
        if (event instanceof WikiEvent.AddonSetupEvent setupEvent) setupEvent.enableAddons();
        if (event instanceof WikiEvent.AddonPageEvent setupEvent){
            setupEvent.registerChapters(new CategoryObj("wiki.ic2.category.general", new CategoryObj.Link(Registry.ADVANCED_SOLAR_PANEL, "advancedsolars:items").with("wiki.advanced_solars.category")));
        }
    }

    public static void createWiki(IChapterBuilder builder){
        builder.startBuildChapter("advancedsolars", "items");
        builder.addSimplePage(new HeaderObj("wiki.advanced_solars.header.main"), new TextObj("wiki.advanced_solars.preview.main.desc"));
        builder.addSimplePage(createItemList(Registry.ADVANCED_SOLAR_HELMET,Registry.HYBRID_SOLAR_HELMET, Registry.ULTIMATE_HYBRID_SOLAR_HELMET));
        builder.finishBuildChapter(true);
        builder.startBuildChapter("advancedsolars", "blocks");
        builder.addSimplePage(createItemList(Registry.ADVANCED_SOLAR_PANEL,Registry.HYBRID_SOLAR_PANEL, Registry.ULTIMATE_HYBRID_SOLAR_PANEL));
        builder.addSubPages(Registry.MOLECULAR_TRANSFORMER);
        builder.finishBuildChapter(true);
    }

    private static List<IWikiObj> createItemList(ItemLike... items){
        List<IWikiObj> objs = new ArrayList<>();
        objs.add(new PreviewObj(items));
        objs.add(new HeaderObj(items[0].asItem().getDescriptionId()));
        objs.add(new CraftObj(items));
        ResourceLocation location = ForgeRegistries.ITEMS.getKey(items[0].asItem());
        objs.add((new TextObj("wiki." + location.getNamespace() + ".preview." + location.getPath() + ".desc")).setCutOff());
        objs.add(new LinkObj(location.getNamespace(), "subpage." + location.getPath()));
        return objs;
    }
}
