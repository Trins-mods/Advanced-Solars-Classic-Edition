/*package trinsdar.advancedsolars.blocks;

import ic2.api.classic.item.IMachineUpgradeItem;
import ic2.api.classic.recipe.machine.IMachineRecipeList;
import ic2.api.classic.tile.MachineType;
import ic2.core.block.base.tile.TileEntityBasicElectricMachine;
import ic2.core.block.machine.recipes.managers.BasicMachineRecipeList;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class TileEntityMolecularAssembler extends TileEntityBasicElectricMachine {
    public static IMachineRecipeList molecularAssembler = new BasicMachineRecipeList("molecularAssembler");
    public TileEntityMolecularAssembler() {
        super(3, 1, 100, 536870912);
    }

    @Override
    public IMachineRecipeList.RecipeEntry getOutputFor(ItemStack itemStack) {
        return molecularAssembler.getRecipeInAndOutput(itemStack, false);
    }

    @Override
    public ResourceLocation getGuiTexture() {
        return null;
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return molecularAssembler;
    }

    @Override
    public MachineType getType() {
        return null;
    }

    public void update() {
        this.progressPerTick = this.energyConsume;
    }
}*/
