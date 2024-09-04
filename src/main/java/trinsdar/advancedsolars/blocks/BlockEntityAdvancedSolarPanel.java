package trinsdar.advancedsolars.blocks;

import ic2.api.energy.tile.IEnergySource;
import ic2.api.items.electric.ElectricItem;
import ic2.api.tiles.readers.IEUProducer;
import ic2.api.util.DirectionList;
import ic2.core.block.base.features.ITickListener;
import ic2.core.block.base.features.ITileActivityProvider;
import ic2.core.block.base.features.IWrenchableTile;
import ic2.core.block.base.misc.comparator.ComparatorNames;
import ic2.core.block.base.misc.comparator.types.base.FlagComparator;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import trinsdar.advancedsolars.util.AdvancedSolarsConfig;
import trinsdar.advancedsolars.util.Registry;

import java.util.Objects;
import java.util.function.Supplier;

public class BlockEntityAdvancedSolarPanel extends BaseGeneratorTileEntity implements ITickListener, IEnergySource, ITileGui, IWrenchableTile, IEUProducer, ITileActivityProvider {
    double config;
    int ticker;
    protected int lowerProduction;
    int maxOutput;
    boolean day = true;

    public BlockEntityAdvancedSolarPanel(BlockPos pos, BlockState state) {
        super(pos, state, 4);
        this.config = AdvancedSolarsConfig.ADVANCED_SOLAR_GENERATION_MULTIPLIER.get();
        this.ticker = 127;
        this.production = (int) (16 * config);
        this.lowerProduction = (int) (2 * config);
        this.maxStorage = 32000;
        this.tier = production > 32 ? 2 : 1;
        this.maxOutput = production > 32 ? 128 : 32;
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
        this.addGuiFields("day");
    }

    @Override
    public BlockEntityType<?> createType() {
        return Registry.ADVANCED_SOLAR_PANEL_TYPE;
    }

    @Override
    protected void addSlotInfo(InventoryHandler handler) {
        handler.registerBlockSides(DirectionList.UP.invert());
        handler.registerBlockAccess(DirectionList.UP.invert(), AccessRule.BOTH);
        handler.registerSlotAccess(AccessRule.BOTH, 0, 1, 2, 3);
        for (int i = 0; i < 4; i++) {
            handler.setSlotAccess(i, Direction.UP, AccessRule.DISABLED);
        }
        handler.registerSlotsForSide(DirectionList.UP.invert(), 0, 1, 2, 3);
        handler.registerInputFilter(ElectricItemFilter.CHARGE_FILTER, 0, 1, 2, 3);
        handler.registerOutputFilter(ElectricItemFilter.NOT_CHARGE_FILTER, 0, 1, 2, 3);
        handler.registerNamedSlot(SlotType.CHARGE, 0, 1, 2, 3);
    }

    @Override
    public IC2Container createContainer(Player player, InteractionHand interactionHand, Direction direction, int i) {
        return new ContainerAdvancedSolarPanel(this, player, i);
    }

    @Override
    public int getMaxEnergyOutput() {
        return maxOutput;
    }

    @Override
    public int getProvidedEnergy() {
        return Math.min(storage, maxOutput);
    }

    public boolean isConverting() {
        if (this.skyBlockCheck()) {
            if (isSunVisible()) {
                return this.storage + this.production <= this.maxStorage;
            } else {
                return this.storage + this.lowerProduction <= this.maxStorage;
            }
        } else {
            return false;
        }
    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    public boolean gainEnergy() {
        if (this.isConverting()) {
            if (this.skyBlockCheck()) {
                if (isSunVisible()) {
                    this.storage += this.production;
                } else {
                    this.storage += this.lowerProduction;
                }
            }
            return true;
        } else {
            return false;
        }
    }


    @Override
    public void onTick() {

        if (this.clock(64)) {
            this.day = isSunVisible();
            this.updateGuiField("day");
        }
        int oldEnergy = this.storage;
        boolean active = this.gainEnergy();
        if (this.storage > 0) {
            for (ItemStack tStack : this.inventory) {
                if (this.storage <= 0) break;
                if (tStack.isEmpty()) continue; // No item to charge
                int charged = ElectricItem.MANAGER.charge(tStack, this.storage, this.tier, false, false);
                this.storage -= charged;
            }

            if (this.storage > this.maxStorage) {
                this.storage = this.maxStorage;
            }
        }

        this.setActive(active);

        if (oldEnergy != this.storage) {
            this.updateGuiField("storage");
        }

        this.handleComparators();
    }

    public int getOutput() {
        if (skyBlockCheck()) {
            if (day) {
                return this.production;
            } else {
                return this.lowerProduction;
            }
        } else {
            return 0;
        }

    }

    public boolean skyBlockCheck() {
        return this.getLevel().canSeeSkyFromBelowWater(this.getPosition().above()) && this.getLevel().dimensionType().hasSkyLight();
    }

    public boolean isSunVisible() {
        return isSunVisible(Objects.requireNonNull(this.level), this.getPosition().above());
    }

    public static boolean isSunVisible(@NotNull Level world, BlockPos pos) {
        if (!world.isDay()) return false;
        Holder<Biome> biome = world.getBiome(pos);
        return biome.get().getPrecipitation() == Biome.Precipitation.NONE || (!world.isRaining() && !world.isThundering());
    }

    public boolean isDay() {
        return day;
    }

    /*public boolean canSetFacing(EntityPlayer player, EnumFacing facing) {
        return facing != getFacing() && facing.getAxis().isHorizontal();
    }*/

    @Override
    public double getDropRate(Player player) {
        return 1.0D;
    }

    @Override
    public float getEUProduction() {
        if (isSunVisible()) {
            return Math.min(this.storage, this.production);
        } else {
            return Math.min(this.storage, this.lowerProduction);
        }
    }

    @Override
    protected void consumeFuel() {
    }

    @Override
    public boolean needsFuel() {
        return false;
    }

    @Override
    public int getMaxFuel() {
        return 0;
    }

    public static class BlockEntityHybridSolarPanel extends BlockEntityAdvancedSolarPanel {
        public BlockEntityHybridSolarPanel(BlockPos pos, BlockState state) {
            super(pos, state);
            this.config = AdvancedSolarsConfig.HYBRID_SOLAR_GENERATION_MULTIPLIER.get();
            this.production = (int) (128 * config);
            this.lowerProduction = (int) (16 * config);
            this.maxStorage = 100000;
            this.tier = production > 128 ? 3 : 2;
            this.maxOutput = production > 128 ? 512 : 128;
        }

        @Override
        public BlockEntityType<?> createType() {
            return Registry.HYBRID_SOLAR_PANEL_TYPE;
        }
    }

    public static class BlockEntityUltimateHybridSolarPanel extends BlockEntityAdvancedSolarPanel {
        public BlockEntityUltimateHybridSolarPanel(BlockPos pos, BlockState state) {
            super(pos, state);
            this.config = AdvancedSolarsConfig.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER.get();
            this.production = (int) (1024 * config);
            this.lowerProduction = (int) (128 * config);
            this.maxStorage = 1000000;
            this.tier =  production > 2048 ? 5 : 4;
            this.maxOutput = production > 2048 ? 8192 : 2048;
        }

        @Override
        public BlockEntityType<?> createType() {
            return Registry.ULTIMATE_HYBRID_SOLAR_PANEL_TYPE;
        }
    }
}
