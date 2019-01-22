package trinsdar.advancedsolars.util;

import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.platform.registry.Ic2GuiComp;
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
            gui.drawString(block.getBlockName(), 20, 5, 7718655);
        }else if (block instanceof TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel){
            gui.drawString(block.getBlockName(), 41, 5, 7718655);
        }else {
            gui.drawString(block.getBlockName(), 33, 5, 7718655);
        }
        int eu = this.block.getStoredEU();
        int max = this.block.getMaxEU();
        if (eu > max) {
            eu = max;
        }

        gui.drawString(AdvancedSolarLang.storage.getLocalizedFormatted(eu), 7, 21, white);
        gui.drawString("/" + max, 7, 31, white);
        gui.drawString(AdvancedSolarLang.maxOutput, 7, 41, white);
        gui.drawString("" + this.block.getMaxOutput() + " EU/t", 7, 51, white);
        gui.drawString(AdvancedSolarLang.generating, 7, 61, white);
        gui.drawString("" + this.block.getOutput() + " EU/t", 7, 71, white);
    }
}
