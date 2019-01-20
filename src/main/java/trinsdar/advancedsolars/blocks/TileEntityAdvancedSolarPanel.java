package trinsdar.advancedsolars.blocks;

import ic2.api.classic.tile.machine.IEUStorage;
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.RotationList;
import ic2.core.block.base.tile.TileEntityGeneratorBase;
import ic2.core.block.generator.container.ContainerSolarPanel;
import ic2.core.block.generator.tile.TileEntitySolarPanel;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.inventory.filters.CommonFilters;
import ic2.core.inventory.gui.GuiComponentContainer;
import ic2.core.inventory.management.AccessRule;
import ic2.core.inventory.management.InventoryHandler;
import ic2.core.inventory.management.SlotType;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.storage.Ic2BlockLang;
import ic2.core.platform.registry.Ic2Resources;
import ic2.core.util.math.Box2D;
import ic2.core.util.math.Vec2i;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import org.jetbrains.annotations.NotNull;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

public class TileEntityAdvancedSolarPanel extends TileEntityGeneratorBase {
    double config;
    int ticker;
    protected double lowerProduction;
    int maxOutput;
    public TileEntityAdvancedSolarPanel() {
        super(4);
        this.tier = 1;
        this.ticker = 127;
        this.production = 8;
        this.lowerProduction = 1.0D;
        this.maxStorage = 32000;
        this.maxOutput = 32;
        this.config = (double) IC2.config.getInt("energyGeneratorSolarLV") / 100.0D;
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
        if (isSunVisible()){
            return this.storage + this.production <= this.maxStorage;
        }else {
            if (this.getWorld().canBlockSeeSky(this.getPos().up())){
                return this.storage + this.lowerProduction <= this.maxStorage;
            }else {
                return false;
            }
        }
    }

    @Override
    public boolean gainEnergy() {
        if (this.isConverting()) {
            if (isSunVisible()){
                this.storage += this.production;
            }else {
                if (this.getWorld().canBlockSeeSky(this.getPos().up())){
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
            return (double)Math.min(this.storage, this.lowerProduction);
        }

    }


    @Override
    public void update() {

        int oldEnergy = this.storage;
        boolean active = this.gainEnergy();
        if (this.storage > 0) {
            if (!(this.inventory.get(0)).isEmpty()) {
                this.storage = (int)((double)this.storage - ElectricItem.manager.charge(this.inventory.get(0), (double)this.storage, this.tier, false, false));
            }else if (!this.inventory.get(1).isEmpty()){
                this.storage = (int)((double)this.storage - ElectricItem.manager.charge(this.inventory.get(1), (double)this.storage, this.tier, false, false));
            }else if (!this.inventory.get(2).isEmpty()){
                this.storage = (int)((double)this.storage - ElectricItem.manager.charge(this.inventory.get(2), (double)this.storage, this.tier, false, false));
            }else if (!this.inventory.get(3).isEmpty()){
                this.storage = (int)((double)this.storage - ElectricItem.manager.charge(this.inventory.get(3), (double)this.storage, this.tier, false, false));
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
    public boolean gainFuel() {
        return false;
    }

    @Override
    public int getOutput() {
        if (isSunVisible()){
            return (int)(this.production * this.config);
        }else {
            return (int)(this.lowerProduction * this.config);
        }

    }

    public boolean isSunVisible(){
        return isSunVisible(this.getWorld(), this.getPos().up());
    }

    public static boolean isSunVisible(@NotNull World world, BlockPos pos) {
        if ((world.getWorldTime() > 12700)
                || !world.canBlockSeeSky(pos)
                || world.isRaining()
                || world.isThundering()) {
            return false;
        }
        return true;
    }

    public static float calculateLightRatio(World world) {
        int lightValue = EnumSkyBlock.SKY.defaultLightValue - world.getSkylightSubtracted();
        float sunAngle = world.getCelestialAngleRadians(1.0F);

        if (sunAngle < (float) Math.PI) {
            sunAngle += (0.0F - sunAngle) * 0.2F;
        } else {
            sunAngle += (((float) Math.PI * 2F) - sunAngle) * 0.2F;
        }

        lightValue = Math.round(lightValue * MathHelper.cos(sunAngle));

        lightValue = MathHelper.clamp(lightValue, 0, 15);
        float test = lightValue /15f;
        return test;
    }

    public double getWrenchDropRate() {
        return 1.0D;
    }

    public static class TileEntityHybridSolarPanel extends TileEntityAdvancedSolarPanel{
        public TileEntityHybridSolarPanel() {
            this.tier = 2;
            this.production = 64;
            this.lowerProduction = 8.0D;
            this.maxStorage = 100000;
            this.maxOutput = 128;
            this.config = (double) IC2.config.getInt("energyGeneratorSolarLV") / 100.0D;
        }

        @Override
        public LocaleComp getBlockName() {
            return AdvancedSolarLang.hybridSolarPanel;
        }
    }

    public static class TileEntityUltimateHybridSolarPanel extends TileEntityAdvancedSolarPanel{
        public TileEntityUltimateHybridSolarPanel() {
            this.tier = 3;
            this.production =512;
            this.lowerProduction = 64.0D;
            this.maxStorage = 1000000;
            this.maxOutput = 512;
            this.config = (double) IC2.config.getInt("energyGeneratorSolarLV") / 100.0D;
        }

        @Override
        public LocaleComp getBlockName() {
            return AdvancedSolarLang.ultimateHybridSolarPanel;
        }
    }
}
