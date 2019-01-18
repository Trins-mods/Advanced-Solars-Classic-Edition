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
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

public class TileEntityAdvancedSolarPanel extends TileEntityGeneratorBase {
    double config;
    int ticker;
    int storage;
    protected double lowerProduction;
    public TileEntityAdvancedSolarPanel() {
        super(4);
        this.tier = 1;
        this.ticker = 127;
        this.production = 8;
        this.lowerProduction = 1.0D;
        this.maxStorage = 50000;
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

    @Override
    public Box2D getEnergyBox() {
        return null;
    }

    @Override
    public Class<? extends GuiScreen> getGuiClass(EntityPlayer player) {
        return GuiComponentContainer.class;
    }

    @Override
    public double getOfferedEnergy() {
        return (double)this.storage;
    }

    @Override
    public void drawEnergy(double amount) {
        this.storage = (int)((double)this.storage - amount);
    }

    @Override
    public void update() {
        if (this.ticker++ % 128 == 0) {
            this.setActive(true);
        }

        if (this.getActive()) {
            if (isSunVisible(this.getWorld(), this.getPos().up())){
                this.storage = (int)(this.production * this.config);
            }else {
                this.storage = (int)(this.lowerProduction * this.config);
            }

        }

        if (this.storage > 0 && !((ItemStack)this.inventory.get(0)).isEmpty()) {
            this.storage = (int)((double)this.storage - ElectricItem.manager.charge((ItemStack)this.inventory.get(0), (double)this.storage, this.tier, false, false));
        }

    }

    @Override
    public boolean gainFuel() {
        return false;
    }

    @Override
    public int getOutput() {
        if (isSunVisible(this.getWorld(), this.getPos().up())){
            return (int)(this.production * this.config);
        }else {
            return (int)(this.lowerProduction * this.config);
        }

    }

    public static boolean isSunVisible(World world, BlockPos pos) {
        if (world.provider.hasSkyLight() && world.getWorldTime() < 12600) {
            if (!world.canBlockSeeSky(pos)) {
                return false;
            } else {
                Biome biome = world.getBiome(pos);
                if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.HOT) && !biome.canRain()) {
                    return true;
                } else {
                    return !world.isRaining() && !world.isThundering();
                }
            }
        } else {
            return false;
        }
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
            this.maxStorage = 6000000;
            this.config = (double) IC2.config.getInt("energyGeneratorSolarLV") / 100.0D;
        }

        @Override
        public LocaleComp getBlockName() {
            return AdvancedSolarLang.ultimateHybridSolarPanel;
        }
    }
}
