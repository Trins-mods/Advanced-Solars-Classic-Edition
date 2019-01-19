package trinsdar.advancedsolars.util;

import ic2.core.block.base.tile.TileEntityElectricBlock;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.platform.lang.storage.Ic2GuiLang;
import ic2.core.platform.registry.Ic2GuiComp;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;

import java.util.Arrays;
import java.util.List;

public class AdvancedSolarEnergyStringComp extends GuiComponent {
    byte lastMode;
    TileEntityAdvancedSolarPanel block;
    int white = 13487565;

    public AdvancedSolarEnergyStringComp(TileEntityAdvancedSolarPanel tile) {
        super(Ic2GuiComp.nullBox);
        this.block = tile;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
               return Arrays.asList(ActionRequest.FrontgroundDraw, ActionRequest.BackgroundDraw);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawFrontground(GuiIC2 gui, int mouseX, int mouseY) {
        if (block instanceof TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel){
            gui.drawString(block.getBlockName(), 29, 8, 7718655);
        }else if (block instanceof TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel){
            gui.drawString(block.getBlockName(), 50, 8, 7718655);
        }else {
            gui.drawString(block.getBlockName(), 42, 8, 7718655);
        }
        int eu = this.block.getStoredEU();
        int max = this.block.getMaxEU();
        if (eu > max) {
            eu = max;
        }

        gui.drawString(AdvancedSolarLang.storage.getLocalizedFormatted(eu, max), 50, 21, white);
        gui.drawString(AdvancedSolarLang.maxOutput.getLocalizedFormatted(this.block.getMaxOutput()), 50, 31,
                white);
        gui.drawString(AdvancedSolarLang.generating.getLocalizedFormatted(this.block.getOutput()), 50, 41,
                white);
    }
}
