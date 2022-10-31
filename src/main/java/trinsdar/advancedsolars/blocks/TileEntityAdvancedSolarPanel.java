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
import ic2.core.block.base.tiles.BaseInventoryTileEntity;
import ic2.core.block.base.tiles.impls.BaseGeneratorTileEntity;
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.container.IC2Container;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.Tags;
import org.jetbrains.annotations.NotNull;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.AdvancedSolarLang;
import trinsdar.advancedsolars.util.AdvancedSolarsConfig;
import trinsdar.advancedsolars.util.Registry;

import java.util.Objects;
import java.util.function.Supplier;

public class TileEntityAdvancedSolarPanel extends BaseGeneratorTileEntity implements ITickListener, IEnergySource, ITileGui, IWrenchableTile, IEUProducer, ITileActivityProvider {
    Supplier<Double> config;
    int ticker;
    protected int lowerProduction;
    int maxOutput;
    public TileEntityAdvancedSolarPanel(BlockPos pos, BlockState state) {
        super(pos, state, 4);
        this.tier = 1;
        this.ticker = 127;
        this.production = 16;
        this.lowerProduction = 2;
        this.maxStorage = 32000;
        this.maxOutput = 32;
        this.config = () -> AdvancedSolarsConfig.POWER_GENERATION.ADVANCED_SOLAR_GENERATION_MULTIPLIER;
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
    }

    @Override
    public BlockEntityType<?> createType() {
        return Registry.ADVANCED_SOLAR_PANEL_TYPE;
    }

    protected void addSlots(InventoryHandler handler) {
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

    public int getMaxOutput(){
        return maxOutput;
    }

    public boolean isConverting() {
        if (this.skyBlockCheck()){
            if (isSunVisible()){
                return this.storage + this.production <= this.maxStorage;
            }else {
                return this.storage + this.lowerProduction <= this.maxStorage;
            }
        }else {
            return false;
        }
    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    public boolean gainEnergy() {
        if (this.isConverting()) {
            if (this.skyBlockCheck()){
                if (isSunVisible()){
                    this.storage += this.production;
                }else {
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

        int oldEnergy = this.storage;
        boolean active = this.gainEnergy();
        if (this.storage > 0) {
            for (ItemStack tStack : this.inventory) {
                if (this.storage <= 0) break;
                if (tStack.isEmpty()) continue; // No item to charge
                int charged = (int)(ElectricItem.MANAGER.charge(tStack, this.storage, this.tier, false, false));
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
        if (skyBlockCheck()){
            if (isSunVisible()){
                return (int)(this.production * this.config.get());
            }else {
                return (int)(this.lowerProduction * this.config.get());
            }
        }else {
            return 0;
        }

    }

    public boolean skyBlockCheck(){
        return this.getLevel().canSeeSkyFromBelowWater(this.getPosition().above()) && this.getLevel().dimensionType().hasSkyLight();
    }

    public boolean isSunVisible(){
        return isSunVisible(Objects.requireNonNull(this.getLevel()), this.getPosition().above());
    }

    public static boolean isSunVisible(@NotNull Level world, BlockPos pos) {
        if (world.isNight()) return false;
        Holder<Biome> biome = world.getBiome(pos);
        return biome.get().getPrecipitation() == Biome.Precipitation.NONE || (!world.isRaining() && !world.isThundering());
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
        if (isSunVisible()){
            return Math.min(this.storage, this.production);
        }else {
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

    public static class TileEntityHybridSolarPanel extends TileEntityAdvancedSolarPanel{
        public TileEntityHybridSolarPanel(BlockPos pos, BlockState state) {
            super(pos, state);
            this.tier = 2;
            this.production = 128;
            this.lowerProduction = 16;
            this.maxStorage = 100000;
            this.maxOutput = 128;
            this.config = () -> AdvancedSolarsConfig.POWER_GENERATION.HYBRID_SOLAR_GENERATION_MULTIPLIER;
        }

        @Override
        public BlockEntityType<?> createType() {
            return Registry.HYBRID_SOLAR_PANEL_TYPE;
        }
    }

    public static class TileEntityUltimateHybridSolarPanel extends TileEntityAdvancedSolarPanel{
        public TileEntityUltimateHybridSolarPanel(BlockPos pos, BlockState state) {
            super(pos, state);
            this.tier = 4;
            this.production = 1024;
            this.lowerProduction = 128;
            this.maxStorage = 1000000;
            this.maxOutput = 2048;
            this.config = () -> AdvancedSolarsConfig.POWER_GENERATION.ULTIMATE_HYBRID_SOLAR_GENERATION_MULTIPLIER;
        }

        @Override
        public BlockEntityType<?> createType() {
            return Registry.ULTIMATE_HYBRID_SOLAR_PANEL_TYPE;
        }
    }
}
