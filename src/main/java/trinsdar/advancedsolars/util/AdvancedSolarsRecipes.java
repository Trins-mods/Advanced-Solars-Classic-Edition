package trinsdar.advancedsolars.util;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.api.recipe.IRecipeInput;
import ic2.core.block.machine.low.TileEntityCompressor;
import ic2.core.item.recipe.entry.RecipeInputItemStack;
import ic2.core.item.recipe.entry.RecipeInputOreDict;
import ic2.core.platform.registry.Ic2Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.oredict.OreDictionary;

public class AdvancedSolarsRecipes {
    public static void init(){
        initCraftingRecipes();
        initMiscRecipes();
    }

    static ICraftingRecipeList recipes = ClassicRecipes.advCrafting;

    static IRecipeInput getPlasma(){
        return Loader.isModLoaded("gtclassic") ? new RecipeInputOreDict("itemPlasma") : new RecipeInputItemStack(Ic2Items.plasmaCell);
    }

    public static void initCraftingRecipes(){
        if (AdvancedSolarsConfig.enabledItems.enableMiscCraftingItems){
            recipes.addRecipe(new ItemStack(Registry.SUNNARIUM), "uuu", "ggg", "uuu", 'u', Ic2Items.uuMatter, 'g', "dustGlowstone");
            recipes.addRecipe(new ItemStack(Registry.SUNNARIUM_ALLOY), "iii", "isi", "iii", 'i', Ic2Items.iridiumPlate, 's', Registry.SUNNARIUM);
            recipes.addRecipe(new ItemStack(Registry.IRIDIUM_IRON_PLATE), "rrr", "rir", "rrr", 'r', "ingotRefinedIron", 'i', "ingotIridium");
            recipes.addRecipe(new ItemStack(Registry.REINFORCED_IRIDIUM_IRON_PLATE), "aca", "cic", "aca", 'a', Ic2Items.advancedAlloy, 'c', Ic2Items.carbonPlate, 'i', Registry.IRIDIUM_IRON_PLATE);
            recipes.addRecipe(new ItemStack(Registry.IRRADIANT_REINFORCED_PLATE), "rsr", "lRl", "rdr", 'r', "dustRedstone", 's', Registry.SUNNARIUM_PART, 'l', "dyeBlue", 'R', Registry.REINFORCED_IRIDIUM_IRON_PLATE, 'd', "gemDiamond");
            recipes.addRecipe(new ItemStack(Registry.SUNNARIUM_PART), "ugu", 'u', Ic2Items.uuMatter, 'g', "dustGlowstone");
            recipes.addRecipe(new ItemStack(Registry.IRRADIANT_URANIUM), "ugu", "geg", "ugu", 'u', Ic2Items.uuMatter, 'e', getUranium(), 'g', "dustGlowstone");
            recipes.addRecipe(new ItemStack(Registry.ENRICHED_SUNNARIUM), "iii", "isi", "iii", 'i', Registry.IRRADIANT_URANIUM, 's', Registry.SUNNARIUM);
            recipes.addRecipe(new ItemStack(Registry.IRRADIANT_GLASS_PANE, 6), "rrr", "igi", "rrr", 'r', Ic2Items.reinforcedGlass, 'i', Registry.IRRADIANT_URANIUM, 'g', "dustGlowstone");
            if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarPanel){
                recipes.addRecipe(new ItemStack(Registry.ADVANCED_SOLAR_PANEL), "rrr", "asa", "cic", 'r', Registry.IRRADIANT_GLASS_PANE, 'a', Ic2Items.advancedAlloy, 's', Ic2Items.lvSolarPanel, 'c', "circuitAdvanced", 'i', Registry.IRRADIANT_REINFORCED_PLATE);
                if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarHelmet){
                    recipes.addRecipe(new ItemStack(Registry.ADVANCED_SOLAR_HELMET), "sas", "cnc", "glg", 's', Ic2Items.advSolarHelmet, 'a', Registry.ADVANCED_SOLAR_PANEL, 'c', "circuitAdvanced", 'n', Ic2Items.nanoHelmet, 'g', Ic2Items.doubleInsulatedGoldCable, 'l', Ic2Items.transformerLV);
                }
                if (AdvancedSolarsConfig.enabledItems.enableHybridSolarPanel){
                    recipes.addRecipe(new ItemStack(Registry.HYBRID_SOLAR_PANEL), "CmC", "iai", "csc", 'C', Ic2Items.carbonPlate, 'm', Ic2Items.mvSolarPanel, 'i', Ic2Items.iridiumPlate, 'a', Registry.ADVANCED_SOLAR_PANEL, 'c', "circuitAdvanced", 's', Registry.ENRICHED_SUNNARIUM);
                }
            }
            if (AdvancedSolarsConfig.enabledItems.enableHybridSolarPanel){
                if (AdvancedSolarsConfig.enabledItems.enableHybridSolarHelmet){
                    recipes.addRecipe(new ItemStack(Registry.HYBRID_SOLAR_HELMET), " H ", "cqc", "ghg", 'H', Registry.HYBRID_SOLAR_PANEL, 'c', "circuitAdvanced", 'q', Ic2Items.quantumHelmet, 'g', Ic2Items.glassFiberCable, 'h', Ic2Items.transformerHV);
                }
                if (AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel){
                    recipes.addRecipe(new ItemStack(Registry.ULTIMATE_HYBRID_SOLAR_PANEL), " h ", "pHp", "scs", 'h', Ic2Items.hvSolarPanel, 'p', Ic2Items.plasmaCore, 'H', Registry.HYBRID_SOLAR_PANEL, 's', Registry.ENRICHED_SUNNARIUM_ALLOY, 'c', Ic2Items.coalChunk);
                }
            }
            if (AdvancedSolarsConfig.enabledItems.enableHybridSolarHelmet && AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel && AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarHelmet){
                recipes.addRecipe(new ItemStack(Registry.ULTIMATE_HYBRID_SOLAR_HELMET),  "ppp", "pup", "ihi", 'p', getPlasma(), 'u', Registry.ULTIMATE_HYBRID_SOLAR_PANEL, 'i', Ic2Items.iridiumPlate, 'h', Registry.HYBRID_SOLAR_HELMET);
            }
            recipes.addRecipe(new ItemStack(Registry.ENRICHED_SUNNARIUM_ALLOY), "pep", "ese", "pep", 'p', getPlasma(), 'e', Registry.ENRICHED_SUNNARIUM, 's', Registry.SUNNARIUM_ALLOY);

        }

    }

    public static IRecipeInput getUranium(){
        IRecipeInput defaultUranium =  new RecipeInputItemStack(Ic2Items.enderPearlUraniumIngot.copy());
        switch (AdvancedSolarsConfig.misc.ingotInIrradiantUranium){
            case URANIUM: return new RecipeInputOreDict("ingotUranium");
            case URANIUM233: return OreDictionary.doesOreNameExist("ingotUranium233") ? new RecipeInputOreDict("ingotUranium233") : defaultUranium;
            case URANIUM235: return OreDictionary.doesOreNameExist("ingotUranium235") ? new RecipeInputOreDict("ingotUranium235") : defaultUranium;
            default: return defaultUranium;
        }
    }

    public static void initMiscRecipes(){
        OreDictionary.registerOre("ingotIridium", Registry.IRIDIUM_INGOT);
        if (!Loader.isModLoaded("gtclassic") && !Loader.isModLoaded("techreborn")){
            TileEntityCompressor.addRecipe(Ic2Items.iridiumOre, new ItemStack(Registry.IRIDIUM_INGOT));
        }
    }
}
