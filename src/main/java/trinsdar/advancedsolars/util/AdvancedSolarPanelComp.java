package trinsdar.advancedsolars.util;


import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;

import java.util.Set;

public class AdvancedSolarPanelComp extends GuiWidget {
    TileEntityAdvancedSolarPanel block;
    Vec2i dayTexPos;
    Vec2i nightTexPos;

    public AdvancedSolarPanelComp(TileEntityAdvancedSolarPanel tile, Box2i box, Vec2i dayPos, Vec2i nightPos) {
        super(box);
        this.block = tile;
        this.dayTexPos = dayPos;
        this.nightTexPos = nightPos;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.add(ActionRequest.DRAW_BACKGROUND);
    }

    @OnlyIn(Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        Box2i box = this.getBox();
        if (this.block.isActive()) {
            Vec2i pos = this.block.isSunVisible() ? dayTexPos : nightTexPos;
            gui.drawTextureRegion(matrix,gui.getGuiLeft() + box.getX(), gui.getGuiTop() + box.getY(), pos.getX(), pos.getY(), box.getWidth(), box.getHeight());
        }

    }
}
