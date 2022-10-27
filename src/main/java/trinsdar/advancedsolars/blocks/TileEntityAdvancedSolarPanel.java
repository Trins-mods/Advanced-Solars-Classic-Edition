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
import ic2.core.inventory.base.ITileGui;
import ic2.core.inventory.filter.special.ElectricItemFilter;
import ic2.core.inventory.handler.AccessRule;
import ic2.core.inventory.handler.InventoryHandler;
import ic2.core.inventory.handler.SlotType;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.AdvancedSolarLang;
import trinsdar.advancedsolars.util.AdvancedSolarsConfig;

public class TileEntityAdvancedSolarPanel extends BaseInventoryTileEntity implements ITickListener, IEnergySource, ITileGui, IWrenchableTile, IEUProducer, ITileActivityProvider {
    double config;
    int ticker;
    protected double lowerProduction;
    int maxOutput;
    public TileEntityAdvancedSolarPanel(BlockPos pos, BlockState state) {
        super(pos, state, 4);
        this.tier = 1;
        this.ticker = 127;
        this.production = 16;
        this.lowerProduction = 2.0D;
        this.maxStorage = 32000;
        this.maxOutput = 32;
        this.config = AdvancedSolarsConfig.powerGeneration.energyGeneratorSolarAdvanced;
        this.addComparator(FlagComparator.createTile("active", ComparatorNames.ACTIVE, this));
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
    public ContainerIC2 getGuiContainer(EntityPlayer player) {
        return new ContainerAdvancedSolarPanel(player.inventory, this);
    }

    @Override
    public LocaleComp getBlockName() {
        return AdvancedSolarLang.advancedSolarPanel;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(AdvancedSolarsClassic.MODID, "textures/sprites/guiadvancedsolarpanel.png");
    }

    public int getMaxOutput(){
        return maxOutput;
    }

    @Override
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
    public Box2D getEnergyBox() {
        return ContainerAdvancedSolarPanel.chargeBox;
    }

    @Override
    public Vec2i getEnergyPos() {
        return ContainerAdvancedSolarPanel.chargePos;
    }

    @Override
    public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
        return GuiComponentContainer.class;
    }

    @Override
    public double getOfferedEnergy() {
        if (isSunVisible()){
            return (double)Math.min(this.storage, this.production);
        }else {
            return Math.min(this.storage, this.lowerProduction);
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
                int charged = (int)(ElectricItem.manager.charge(tStack, (double)this.storage, this.tier, false, false));
                this.storage -= charged;
            }

            if (this.storage > this.maxStorage) {
                this.storage = this.maxStorage;
            }
        }

        if (!this.delayActiveUpdate()) {
            this.setActive(active);
        } else {
            if (this.ticksSinceLastActiveUpdate % this.getDelay() == 0) {
                this.setActive(this.activityMeter > 0);
                this.activityMeter = 0;
            }

            if (active) {
                ++this.activityMeter;
            } else {
                --this.activityMeter;
            }

            ++this.ticksSinceLastActiveUpdate;
        }

        if (oldEnergy != this.storage) {
            this.getNetwork().updateTileGuiField(this, "storage");
        }

        this.updateComparators();
    }

    @Override
    public int getOutput() {
        if (skyBlockCheck()){
            if (isSunVisible()){
                return (int)(this.production * this.config);
            }else {
                return (int)(this.lowerProduction * this.config);
            }
        }else {
            return 0;
        }

    }

    public boolean skyBlockCheck(){
        return this.getWorld().canBlockSeeSky(this.getPos().up()) && this.getWorld().provider.hasSkyLight();
    }

    public boolean isSunVisible(){
        return isSunVisible(this.getWorld(), this.getPos().up());
    }

    public static boolean isSunVisible(@NotNull World world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        if (world.getWorldTime() %24000  < 12600){
            return BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && !biome.canRain() || !world.isRaining() && !world.isThundering();
        }
        return false;
    }

    @Override
    public boolean canSetFacing(EntityPlayer player, EnumFacing facing) {
        return facing != getFacing() && facing.getAxis().isHorizontal();
    }

    public double getWrenchDropRate() {
        return 1.0D;
    }

    public static class TileEntityHybridSolarPanel extends TileEntityAdvancedSolarPanel{
        public TileEntityHybridSolarPanel() {
            this.tier = 2;
            this.production = 128;
            this.lowerProduction = 16.0D;
            this.maxStorage = 100000;
            this.maxOutput = 128;
            this.config = AdvancedSolarsConfig.powerGeneration.energyGeneratorSolarHybrid;
        }

        @Override
        public LocaleComp getBlockName() {
            return AdvancedSolarLang.hybridSolarPanel;
        }
    }

    public static class TileEntityUltimateHybridSolarPanel extends TileEntityAdvancedSolarPanel{
        public TileEntityUltimateHybridSolarPanel() {
            this.tier = 4;
            this.production =1024;
            this.lowerProduction = 128.0D;
            this.maxStorage = 1000000;
            this.maxOutput = 2048;
            this.config = AdvancedSolarsConfig.powerGeneration.energyGeneratorSolarUltimateHybrid;
        }

        @Override
        public LocaleComp getBlockName() {
            return AdvancedSolarLang.ultimateHybridSolarPanel;
        }
    }
}
