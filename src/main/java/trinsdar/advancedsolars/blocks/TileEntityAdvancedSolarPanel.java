package trinsdar.advancedsolars.blocks;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.block.generator.container.ContainerSolarPanel;
import ic2.core.block.generator.tile.TileEntitySolarPanel;
import ic2.core.inventory.container.ContainerIC2;
import ic2.core.inventory.gui.GuiComponentContainer;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.lang.storage.Ic2BlockLang;
import ic2.core.platform.registry.Ic2Resources;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

public class TileEntityAdvancedSolarPanel extends TileEntitySolarPanel {
    double config;
    int ticker;
    int storage;
    public TileEntityAdvancedSolarPanel() {
        this.tier = 1;
        this.ticker = 127;
        this.production = getProduction();
        this.config = (double) IC2.config.getInt("energyGeneratorSolarLV") / 100.0D;
    }

    public double getProduction(){
        if (isSunVisible(this.world, this.getPos().up())){
            return 8.0D;
        }else {
            return 1.0D;
        }
    }

    @Override
    public ContainerIC2 getGuiContainer(EntityPlayer player) {
        return new ContainerSolarPanel(player.inventory, this);
    }

    @Override
    public LocaleComp getBlockName() {
        return Ic2BlockLang.solarPanel;
    }

    @Override
    public ResourceLocation getTexture() {
        return new ResourceLocation(AdvancedSolarsClassic.MODID, "textures/sprites/guiadvancedsolarpanel.png");
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
            this.storage = (int)(this.production * this.config);
        }

        if (this.storage > 0 && !((ItemStack)this.inventory.get(0)).isEmpty()) {
            this.storage = (int)((double)this.storage - ElectricItem.manager.charge((ItemStack)this.inventory.get(0), (double)this.storage, this.tier, false, false));
        }

    }

    @Override
    public int getOutput() {
        return (int)(this.production * this.config);
    }

    public double getWrenchDropRate() {
        return 1.0D;
    }
}
