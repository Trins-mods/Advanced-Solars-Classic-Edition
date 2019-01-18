package trinsdar.advancedsolars.util;

import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.GuiComponent;
import ic2.core.util.math.Box2D;
import ic2.core.util.math.Vec2i;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;

import java.util.Arrays;
import java.util.List;

public class AdvancedSolarPanelComp extends GuiComponent {
    TileEntityAdvancedSolarPanel block;
    Vec2i texPos;

    public AdvancedSolarPanelComp(TileEntityAdvancedSolarPanel tile, Box2D box, Vec2i pos) {
        super(box);
        this.block = tile;
        this.texPos = pos;
    }

    @Override
    public List<ActionRequest> getNeededRequests() {
        return Arrays.asList(ActionRequest.BackgroundDraw);
    }

    @SideOnly(Side.CLIENT)
    public void drawBackground(GuiIC2 gui, int mouseX, int mouseY, float particalTicks) {
        Box2D box = this.getPosition();
        if (this.block.getActive()) {
            gui.drawTexturedModalRect(gui.getXOffset() + box.getX(), gui.getYOffset() + box.getY(), this.texPos.getX(), this.texPos.getY(), box.getHeight(), box.getLenght());
        }

    }
}
