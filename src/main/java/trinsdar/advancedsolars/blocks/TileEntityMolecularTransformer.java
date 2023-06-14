package trinsdar.advancedsolars.blocks;

import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.block.base.tiles.impls.machine.single.BasicMachineTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

public class TileEntityMolecularTransformer extends BasicMachineTileEntity {
    private int energyInPerTick = 0;
    public TileEntityMolecularTransformer(BlockPos pos, BlockState state) {
        super(pos, state, 2, 2, 1, 100, 10000, 32);
    }

    @Override
    public BlockEntityType<?> createType() {
        return Registry.MOLECULAR_TRANSFORMER_TYPE;
    }

    @Override
    public ResourceLocation getTexture() {
        return null;
    }

    @Override
    public IMachineRecipeList getRecipeList() {
        return AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER;
    }

    @Override
    protected void onPreTick(boolean hasRecipe, boolean canWork, boolean canOperate) {
        if (canOperate){
            progressPerTick = energyInPerTick;
            this.recipeEnergy = (int) progressPerTick;
        }
    }

    @Override
    public int acceptEnergy(Direction side, int amount, int voltage) {
        int oldEnergy = this.energy;
        int superCall = super.acceptEnergy(side, amount, voltage);
        int newEnergy = this.energy;
        this.energyInPerTick = newEnergy - oldEnergy;
        return superCall;
    }

    //public static IMachineRecipeList molecularAssembler = new BasicMachineRecipeList("molecularAssembler");

    /*public TileEntityMolecularTransformer() {
        super(3, 1, 100, 536870912);
    }*/

    /*@Override
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
    }*/
}
