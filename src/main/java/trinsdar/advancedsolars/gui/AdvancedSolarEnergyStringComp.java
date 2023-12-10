package trinsdar.advancedsolars.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.network.chat.Component;
import trinsdar.advancedsolars.blocks.BlockEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

import java.util.Set;

public class AdvancedSolarEnergyStringComp extends GuiWidget {
    byte lastMode;
    BlockEntityAdvancedSolarPanel block;
    int white = 13487565;

    public AdvancedSolarEnergyStringComp(BlockEntityAdvancedSolarPanel tile) {
        super(Box2i.EMPTY_BOX);
        this.block = tile;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.add(ActionRequest.DRAW_FOREGROUND);
    }

    @Override
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        gui.drawCenterString(matrix, block.getName(), 176 / 2, 5, 7718655);
        int eu = this.block.getStoredEU();
        int max = this.block.getMaxEU();
        if (eu > max) {
            eu = max;
        }

        gui.drawString(matrix, Component.translatable(AdvancedSolarLang.storage, eu), 7, 21, white);
        gui.drawString(matrix, Component.literal("/" + max), 7, 31, white);
        gui.drawString(matrix, Component.translatable(AdvancedSolarLang.maxOutput), 7, 41, white);
        gui.drawString(matrix, Component.literal(this.block.getMaxEnergyOutput() + " EU/t"), 7, 51, white);
        gui.drawString(matrix, Component.translatable(AdvancedSolarLang.generating), 7, 61, white);
        gui.drawString(matrix, Component.literal(this.block.getOutput() + " EU/t"), 7, 71, white);
    }
}
