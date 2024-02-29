package trinsdar.advancedsolars.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import ic2.core.inventory.gui.components.GuiWidget;
import ic2.core.inventory.gui.components.simple.ProgressComponent;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.advancedsolars.blocks.BlockEntityMolecularTransformer;

import java.util.Set;
import java.util.function.Consumer;

public class GuiCompMTEnergyBar extends ProgressComponent {
    BlockEntityMolecularTransformer transformer;
    Vec2i pos = new Vec2i(176, 0);

    public GuiCompMTEnergyBar(BlockEntityMolecularTransformer transformer) {
        super(new Box2i(30, 30, 8, 24), null, null, false);
        this.transformer = transformer;
    }

    @Override
    protected void addRequests(Set<ActionRequest> set) {
        set.add(ActionRequest.DRAW_BACKGROUND);
    }

    @OnlyIn(Dist.CLIENT)
    public void drawBackground(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        Box2i box = this.getBox();
        if (this.transformer.isActive()) {
            float percentage = (float) transformer.energy / transformer.maxEnergy;
            gui.drawTextureRegion(matrix, gui.getGuiLeft() + box.getX(), gui.getGuiTop() + box.getY(), pos.getX(), pos.getY(), box.getWidth(), percentage * box.getHeight());
        }

    }

    @Override
    public void addTooltips(PoseStack matrix, int mouseX, int mouseY, Consumer<Component> tooltips) {

    }
}
