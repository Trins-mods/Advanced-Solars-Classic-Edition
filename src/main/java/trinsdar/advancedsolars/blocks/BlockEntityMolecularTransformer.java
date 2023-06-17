package trinsdar.advancedsolars.blocks;

import ic2.api.energy.EnergyNet;
import ic2.api.energy.tile.IEnergyEmitter;
import ic2.api.energy.tile.IEnergySink;
import ic2.api.network.buffer.NetworkInfo;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.api.tiles.readers.IEUStorage;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.EUComparator;
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.utils.helpers.StackUtil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

public class BlockEntityMolecularTransformer extends BaseInventoryTileEntity implements IEnergySink, IEUStorage, IWrenchableTile, ITileGui, ITickListener {
    @NetworkInfo
    public int energyInPerTick = 0;
    @NetworkInfo
    public int energy = 0;
    @NetworkInfo
    public int maxEnergy = 0;

    public IElectrolyzerRecipeList.ElectrolyzerRecipe entry;
    @NetworkInfo
    public ItemStack input = ItemStack.EMPTY;
    @NetworkInfo
    public ItemStack output = ItemStack.EMPTY;
    boolean addedToEnet;
    boolean consumedInputs = false;
    private int energyAccepted = 0;
    private int ticker = 0;

    public BlockEntityMolecularTransformer(BlockPos pos, BlockState state) {
        super(pos, state, 2);
        this.addGuiFields("energy", "maxEnergy", "energyInPerTick", "input", "output");
        this.addComparator(new EUComparator("eu_storage", ComparatorNames.EU_STORAGE, this));
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.ALL);
        handler.registerBlockAccess(DirectionList.ALL, AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.IMPORT, 0);
        handler.registerSlotAccess(AccessRule.EXPORT, 1);
        handler.registerSlotsForSide(DirectionList.DOWN.invert(), 0);
        handler.registerSlotsForSide(DirectionList.UP.invert(), 1);
        handler.registerInputFilter((i, s) -> isValidItem(s, i, true), 0);
    }

    @Override
    public BlockEntityType<?> createType() {
        return Registry.MOLECULAR_TRANSFORMER_TYPE;
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand hand, Direction side, int windowID) {
        return new ContainerMolecularTransformer(this, player, windowID);
    }

    @Override
    public int getStoredEU() {
        return energy;
    }

    @Override
    public int getMaxEU() {
        return maxEnergy;
    }

    @Override
    public int getTier() {
        return 6;
    }

    public boolean isValidItem(ItemStack stack, int slot, boolean input) {
        ItemStack compare = this.inventory.get(slot);
        if (!compare.isEmpty()){
            return StackUtil.isStackEqual(stack, compare);
        }
        return AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe(stack, true, false) != null;
    }

    @Override
    public void onTick() {
        boolean active = false;
        if (shouldProcess()){
            if (energy == 0){
                if (!consumedInputs){
                    this.inventory.get(0).shrink(entry.getInput().getCount());
                    consumedInputs = true;
                }
            }
            if (energyAccepted > 0){
                this.energy += energyAccepted;
                this.updateGuiField("energy");
                energyAccepted = 0;
                this.ticker = 0;
            }
            int needed = this.entry.getEnergy();
            if (this.maxEnergy != needed) {
                this.maxEnergy = needed;
                this.updateGuiField("maxEnergy");
            }
            if (energy >= this.maxEnergy){
                energy = 0;
                this.updateGuiField("energy");
                consumedInputs = false;
                this.setOrGrow(1, this.entry.getOutput().copy(), true);
            }
            active = true;
        }

        this.setActive(active);
        this.handleComparators();
    }

    public void saveAdditional(CompoundTag compound) {
        super.saveAdditional(compound);
        compound.putInt("energy", this.energy);
        compound.putInt("maxEnergy", this.maxEnergy);
        compound.putBoolean("consumedInputs", consumedInputs);
        compound.put("input", input.save(new CompoundTag()));
        compound.put("output", output.save(new CompoundTag()));
    }

    public void load(CompoundTag compound) {
        super.load(compound);
        this.energy = compound.getInt("energy");
        this.maxEnergy = compound.getInt("maxEnergy");
        this.consumedInputs = compound.getBoolean("consumedInputs");
        this.input = ItemStack.of(compound.getCompound("input"));
        this.output = ItemStack.of(compound.getCompound("output"));
    }

    public boolean shouldProcess() {
        if (consumedInputs) return true;
        if (!this.inventory.get(0).isEmpty()) {
            this.entry = AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe(this.inventory.get(0), true, true);
            if (entry == null){
                input = ItemStack.EMPTY;
                output = ItemStack.EMPTY;
                updateGuiFields("input", "output");
            } else {
                input = entry.getInput();
                output = entry.getOutput();
                updateGuiFields("input", "output");
            }
            return this.entry != null && StackUtil.canFitInto(this.inventory.get(1), this.entry.getOutput(), 22);
        } else {
            if (entry != null){
                this.entry = null;
                this.energy = 0;
                this.input = ItemStack.EMPTY;
                this.output = ItemStack.EMPTY;
                this.updateGuiFields("input", "output", "energy");
            }
            return false;
        }
    }

    @Override
    public void onLoaded() {
        super.onLoaded();
        if (this.isSimulating() && !addedToEnet){
            addedToEnet = true;
            EnergyNet.INSTANCE.addTile(this);
        }
    }

    @Override
    public void onUnloaded(boolean chunk) {
        if (this.isSimulating() && addedToEnet){
            addedToEnet = false;
            EnergyNet.INSTANCE.removeTile(this);
        }
        super.onUnloaded(chunk);
    }

    @Override
    public int getSinkTier() {
        return 6;
    }

    @Override
    public int getRequestedEnergy() {
        return this.maxEnergy <= 0 ? 0 : this.maxEnergy - this.energy;
    }

    @Override
    public int acceptEnergy(Direction direction, int amount, int voltage) {
        this.energyInPerTick = amount;
        if (maxEnergy <= 0 || entry == null) return 0;
        int added = Math.min(amount, this.maxEnergy - (energy + energyAccepted));
        if (added > 0){
            this.energyAccepted += added;
        }
        return amount > 0 ? amount - added : 0;
    }

    @Override
    public boolean canAcceptEnergy(IEnergyEmitter iEnergyEmitter, Direction direction) {
        return true;
    }

    @Override
    public boolean canSetFacing(Direction direction) {
        return false;
    }

    @Override
    public boolean canRemoveBlock(Player player) {
        return true;
    }

    @Override
    public double getDropRate(Player player) {
        return 1.0;
    }
}
