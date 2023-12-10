package trinsdar.advancedsolars.jei;

import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.core.block.machines.tiles.lv.ElectrolyzerTileEntity;
import ic2.jeiplugin.JEIModule;
import ic2.jeiplugin.core.recipes.categories.ElectrolyzerCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

@JeiPlugin
public class AdvancedSolarsJeiPlugin implements IModPlugin {

    public static final RecipeType<IElectrolyzerRecipeList.ElectrolyzerRecipe> MOLECULAR_TRANSFORMER = RecipeType.create(AdvancedSolarsClassic.MODID, "molecular_transformer", IElectrolyzerRecipeList.ElectrolyzerRecipe.class);

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(AdvancedSolarsClassic.MODID, "jei_plugin");
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(Registry.MOLECULAR_TRANSFORMER), MOLECULAR_TRANSFORMER);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) return;
        registration.addRecipeCategories(new ElectrolyzerCategory(registration.getJeiHelpers().getGuiHelper(), MOLECULAR_TRANSFORMER, ElectrolyzerTileEntity.TEXTURE, Registry.MOLECULAR_TRANSFORMER));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        if (!JEIModule.ALLOWS_LOADING) return;
        registration.addRecipes(MOLECULAR_TRANSFORMER, AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipes());
    }
}
