package trinsdar.advancedsolars.blocks;

import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.IC2Screen;
import ic2.core.inventory.gui.components.simple.ChargebarComponent;
import ic2.core.inventory.slot.FilterSlot;
import ic2.core.utils.math.geometry.Box2i;
import ic2.core.utils.math.geometry.Vec2i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.gui.AdvancedSolarEnergyStringComp;
import trinsdar.advancedsolars.gui.AdvancedSolarPanelComp;

public class ContainerAdvancedSolarPanel extends ContainerComponent<TileEntityAdvancedSolarPanel> {

    public static final ResourceLocation GUI_TEXTURE = new ResourceLocation(AdvancedSolarsClassic.MODID, "textures/gui/advancedsolarpanel.png");

    public static final Box2i SOLAR_PANEL_LIGHT_BOX = new Box2i(147, 41, 12, 12);
    public static final Vec2i DAY_SOLAR_LIGHT_POS = new Vec2i(177, 17);
    public static final Vec2i NIGHT_SOLAR_LIGHT_POS = new Vec2i(193, 17);
    public static final Box2i CHARGE_BOX = new Box2i(141, 60, 24, 9);
    public static final Vec2i CHARGE_POS = new Vec2i(176, 0);

    public ContainerAdvancedSolarPanel(TileEntityAdvancedSolarPanel tile, Player player, int id) {
        super(tile, player, id);
        this.addSlot(FilterSlot.createChargeSlot(tile, tile.tier, 0, 98, 39));
        this.addSlot(FilterSlot.createChargeSlot(tile, tile.tier, 1, 116, 39));
        this.addSlot(FilterSlot.createChargeSlot(tile, tile.tier, 2, 98, 57));
        this.addSlot(FilterSlot.createChargeSlot(tile, tile.tier, 3, 116, 57));
        this.addComponent(new AdvancedSolarPanelComp(tile, SOLAR_PANEL_LIGHT_BOX, DAY_SOLAR_LIGHT_POS, NIGHT_SOLAR_LIGHT_POS));
        this.addComponent(new AdvancedSolarEnergyStringComp(tile));
        this.addComponent(new ChargebarComponent(CHARGE_BOX, tile, CHARGE_POS, false));
        this.addPlayerInventory(player.getInventory());
    }


    @Override
    @OnlyIn(Dist.CLIENT)
    public void onGuiLoaded(IC2Screen gui) {
        gui.clearFlag(IC2Screen.SHOW_PLAYER_INVENTORY_NAME);
    }

    @Override
    public ResourceLocation getTexture() {
        return GUI_TEXTURE;
    }

}
