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
            recipes.addRecipe(new ItemStack(Registry.sunnarium), "uuu", "ggg", "uuu", 'u', Ic2Items.uuMatter, 'g', "dustGlowstone");
            recipes.addRecipe(new ItemStack(Registry.sunnariumAlloy), "iii", "isi", "iii", 'i', Ic2Items.iridiumPlate, 's', Registry.sunnarium);
            recipes.addRecipe(new ItemStack(Registry.iridiumIronPlate), "rrr", "rir", "rrr", 'r', "ingotRefinedIron", 'i', "ingotIridium");
            recipes.addRecipe(new ItemStack(Registry.reinforcedIridiumIronPlate), "aca", "cic", "aca", 'a', Ic2Items.advancedAlloy, 'c', Ic2Items.carbonPlate, 'i', Registry.iridiumIronPlate);
            recipes.addRecipe(new ItemStack(Registry.irradiantReinforcedPlate), "rsr", "lRl", "rdr", 'r', "dustRedstone", 's', Registry.sunnariumPart, 'l', "dyeBlue", 'R', Registry.reinforcedIridiumIronPlate, 'd', "gemDiamond");
            recipes.addRecipe(new ItemStack(Registry.sunnariumPart), "ugu", 'u', Ic2Items.uuMatter, 'g', "dustGlowstone");
            recipes.addRecipe(new ItemStack(Registry.irradiantUranium), "ugu", "geg", "ugu", 'u', Ic2Items.uuMatter, 'e', Ic2Items.enderPearlUraniumIngot, 'g', "dustGlowstone");
            recipes.addRecipe(new ItemStack(Registry.enrichedSunnarium), "iii", "isi", "iii", 'i', Registry.irradiantUranium, 's', Registry.sunnarium);
            recipes.addRecipe(new ItemStack(Registry.irradiantGlassPane, 6), "rrr", "igi", "rrr", 'r', Ic2Items.reinforcedGlass, 'i', Registry.irradiantUranium, 'g', "dustGlowstone");
            if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarPanel){
                recipes.addRecipe(new ItemStack(Registry.advancedSolarPanel), "rrr", "asa", "cic", 'r', Registry.irradiantGlassPane, 'a', Ic2Items.advancedAlloy, 's', Ic2Items.lvSolarPanel, 'c', "circuitAdvanced", 'i', Registry.irradiantReinforcedPlate);
                if (AdvancedSolarsConfig.enabledItems.enableAdvancedSolarHelmet){
                    recipes.addRecipe(new ItemStack(Registry.advancedSolarHelmet), "sas", "cnc", "glg", 's', Ic2Items.advSolarHelmet, 'a', Registry.advancedSolarPanel, 'c', "circuitAdvanced", 'n', Ic2Items.nanoHelmet, 'g', Ic2Items.doubleInsulatedGoldCable, 'l', Ic2Items.transformerLV);
                }
                if (AdvancedSolarsConfig.enabledItems.enableHybridSolarPanel){
                    recipes.addRecipe(new ItemStack(Registry.hybridSolarPanel), "CmC", "iai", "csc", 'C', Ic2Items.carbonPlate, 'm', Ic2Items.mvSolarPanel, 'i', Ic2Items.iridiumPlate, 'a', Registry.advancedSolarPanel, 'c', "circuitAdvanced", 's', Registry.enrichedSunnarium);
                }
            }
            if (AdvancedSolarsConfig.enabledItems.enableHybridSolarPanel){
                if (AdvancedSolarsConfig.enabledItems.enableHybridSolarHelmet){
                    recipes.addRecipe(new ItemStack(Registry.hybridSolarHelmet), " H ", "cqc", "ghg", 'H', Registry.hybridSolarPanel, 'c', "circuitAdvanced", 'q', Ic2Items.quantumHelmet, 'g', Ic2Items.glassFiberCable, 'h', Ic2Items.transformerHV);
                }
                if (AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel){
                    recipes.addRecipe(new ItemStack(Registry.ultimateHybridSolarPanel), " h ", "pHp", "scs", 'h', Ic2Items.hvSolarPanel, 'p', Ic2Items.plasmaCore, 'H', Registry.hybridSolarPanel, 's', Registry.enrichedSunnariumAlloy, 'c', Ic2Items.coalChunk);
                }
            }
            if (AdvancedSolarsConfig.enabledItems.enableHybridSolarHelmet && AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarPanel && AdvancedSolarsConfig.enabledItems.enableUltimateHybridSolarHelmet){
                recipes.addRecipe(new ItemStack(Registry.ultimateHybridSolarHelmet),  "ppp", "pup", "ihi", 'p', getPlasma(), 'u', Registry.ultimateHybridSolarPanel, 'i', Ic2Items.iridiumPlate, 'h', Registry.hybridSolarHelmet);
            }
            recipes.addRecipe(new ItemStack(Registry.enrichedSunnariumAlloy), "pep", "ese", "pep", 'p', getPlasma(), 'e', Registry.enrichedSunnarium, 's', Registry.sunnariumAlloy);

        }

    }

    public static void initMiscRecipes(){
        OreDictionary.registerOre("ingotIridium", Registry.iridiumIngot);
        if (!Loader.isModLoaded("gtclassic") && !Loader.isModLoaded("techreborn")){
            TileEntityCompressor.addRecipe(Ic2Items.iridiumOre, new ItemStack(Registry.iridiumIngot));
        }
    }
}
