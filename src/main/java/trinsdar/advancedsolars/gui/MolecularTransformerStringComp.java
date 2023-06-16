package trinsdar.advancedsolars.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import net.minecraft.network.chat.Component;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.blocks.TileEntityMolecularTransformer;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

import java.util.Set;

public class MolecularTransformerStringComp extends GuiWidget {
    byte lastMode;
    TileEntityMolecularTransformer block;
    int white = 13487565;

    public MolecularTransformerStringComp(TileEntityMolecularTransformer tile) {
        super(Box2i.EMPTY_BOX);
        this.block = tile;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.add(ActionRequest.DRAW_FOREGROUND);
    }


    @Override
    public void drawForeground(PoseStack matrix, int mouseX, int mouseY) {
        int eu = this.block.getStoredEU();
        int max = this.block.getMaxEU();
        if (eu > max) {
            eu = max;
        }
        if (block.input.isEmpty() || block.output.isEmpty()) return;

        gui.drawString(matrix, Component.literal("Input: ").append(block.input.getDisplayName()), 56, 11, white);
        gui.drawString(matrix, Component.literal("Output: ").append(block.output.getDisplayName()), 56, 21, white);
        gui.drawString(matrix, Component.literal("Energy: " + max), 56, 31, white);
        gui.drawString(matrix, Component.literal("EU in: " + block.energyInPerTick), 56, 41, white);
        gui.drawString(matrix, Component.literal("Progress: " + Math.round(((float) eu / max) * 100) + "%"), 56, 51, white);
    }
}
