package trinsdar.advancedsolars.blocks;

import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.gui.GuiCompMTEnergyBar;
import trinsdar.advancedsolars.gui.MolecularTransformerStringComp;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;

public class ContainerMolecularTransformer extends ContainerComponent<BlockEntityMolecularTransformer> {
    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(AdvancedSolarsClassic.MODID, "textures/gui/molecular_transformer.png");

    public ContainerMolecularTransformer(BlockEntityMolecularTransformer key, Player player, int id) {
        super(key, player, id);
        this.addSlot(new FilterSlot(key, 0, 26, 9, i -> {
            return AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipe(i, true, false) != null;
        }));
        this.addSlot(FilterSlot.createOutputSlot(key, 1, 26, 59));
        this.addComponent(new GuiCompMTEnergyBar(key));
        this.addComponent(new MolecularTransformerStringComp(key));
        this.addPlayerInventory(player.getInventory());
    }

    @Override
    public Vec2i getComparatorButtonOffset() {
        return new Vec2i(-11, 22);
    }

    @Override
    public void onGuiLoaded(IC2Screen screen) {
        screen.setGuiName(Component.empty());
        screen.clearFlag(IC2Screen.SHOW_PLAYER_INVENTORY_NAME);
    }

    @Override
    public ResourceLocation getTexture() {
        return GUI_TEXTURE;
    }
}
