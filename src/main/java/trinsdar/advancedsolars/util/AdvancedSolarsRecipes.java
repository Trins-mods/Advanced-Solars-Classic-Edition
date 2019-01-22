package trinsdar.advancedsolars.util;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.core.block.machine.low.TileEntityCompressor;
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

    public static void initCraftingRecipes(){
        recipes.addRecipe(new ItemStack(Registry.sunnarium), "uuu", "ggg", "uuu", 'u', Ic2Items.uuMatter, 'g', "dustGlowstone");
        recipes.addRecipe(new ItemStack(Registry.sunnariumAlloy), "iii", "isi", "iii", 'i', Ic2Items.iridiumPlate, 's', Registry.sunnarium);
        recipes.addRecipe(new ItemStack(Registry.iridiumIronPlate), "rrr", "rir", "rrr", 'r', "ingotRefinedIron", 'i', "ingotIridium");
        recipes.addRecipe(new ItemStack(Registry.reinforcedIridiumIronPlate), "aca", "cic", "aca", 'a', Ic2Items.advancedAlloy, 'c', Ic2Items.carbonPlate, 'i', Registry.iridiumIronPlate);
        recipes.addRecipe(new ItemStack(Registry.irradiantReinforcedPlate), "rsr", "lRl", "rdr", 'r', "dustRedstone", 's', Registry.sunnariumPart, 'l', "dyeBlue", 'R', Registry.reinforcedIridiumIronPlate, 'd', "gemDiamond");
        recipes.addRecipe(new ItemStack(Registry.sunnariumPart), "ugu", 'u', Ic2Items.uuMatter, 'g', "dustGlowstone");
        recipes.addRecipe(new ItemStack(Registry.advancedSolarHelmet), " a ", "cnc", "glg", 'a', Registry.advancedSolarPanel, 'c', "circuitAdvanced", 'n', Ic2Items.nanoHelmet, 'g', Ic2Items.doubleInsulatedGoldCable, 'l', Ic2Items.transformerLV);
        recipes.addRecipe(new ItemStack(Registry.hybridSolarHelmet), " H ", "cqc", "ghg", 'H', Registry.hybridSolarPanel, 'c', "circuitAdvanced", 'q', Ic2Items.quantumHelmet, 'g', Ic2Items.glassFiberCable, 'h', Ic2Items.transformerHV);
        recipes.addRecipe(new ItemStack(Registry.irradiantUranium), "ugu", "geg", "ugu", 'u', Ic2Items.uuMatter, 'e', Ic2Items.enderPearlUraniumIngot, 'g', "dustGlowstone");
        recipes.addRecipe(new ItemStack(Registry.enrichedSunnarium), "iii", "isi", "iii", 'i', Registry.irradiantUranium, 's', Registry.sunnarium);
        recipes.addRecipe(new ItemStack(Registry.irradiantGlassPane, 6), "rrr", "igi", "rrr", 'r', Ic2Items.reinforcedGlass, 'i', Registry.irradiantUranium, 'g', "dustGlowstone");
        recipes.addRecipe(new ItemStack(Registry.advancedSolarPanel), "rrr", "asa", "cic", 'r', Registry.irradiantGlassPane, 'a', Ic2Items.advancedAlloy, 's', Ic2Items.lvSolarPanel, 'c', "circuitAdvanced", 'i', Registry.irradiantReinforcedPlate);
        recipes.addRecipe(new ItemStack(Registry.hybridSolarPanel), "CmC", "iai", "csc", 'C', Ic2Items.carbonPlate, 'm', Ic2Items.mvSolarPanel, 'i', Ic2Items.iridiumPlate, 'a', Registry.advancedSolarPanel, 'c', "circuitAdvanced", 's', Registry.enrichedSunnarium);
        recipes.addRecipe(new ItemStack(Registry.ultimateHybridSolarPanel), " h ", "pHp", "scs", 'h', Ic2Items.hvSolarPanel, 'p', Ic2Items.plasmaCore, 'H', Registry.hybridSolarPanel, 's', Registry.enrichedSunnariumAlloy, 'c', Ic2Items.coalChunk);
        recipes.addRecipe(new ItemStack(Registry.ultimateHybridSolarHelmet), " u ", "ihi", 'u', Registry.ultimateHybridSolarPanel, 'i', Ic2Items.iridiumPlate, 'h', Registry.hybridSolarHelmet);
        if (Loader.isModLoaded("gtclassic")){
            recipes.addRecipe(new ItemStack(Registry.enrichedSunnariumAlloy), "pep", "ese", "pep", 'p', "itemPlasma", 'e', Registry.enrichedSunnarium, 's', Registry.sunnariumAlloy);
        }else {
            recipes.addRecipe(new ItemStack(Registry.enrichedSunnariumAlloy), "pep", "ese", "pep", 'p', Ic2Items.plasmaCell, 'e', Registry.enrichedSunnarium, 's', Registry.sunnariumAlloy);
        }
    }

    public static void initMiscRecipes(){
        OreDictionary.registerOre("ingotIridium", Registry.iridiumIngot);
        if (!Loader.isModLoaded("gtclassic") && !Loader.isModLoaded("techreborn")){
            TileEntityCompressor.addRecipe(Ic2Items.iridiumOre, new ItemStack(Registry.iridiumIngot));
        }
    }
}
