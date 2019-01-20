package trinsdar.advancedsolars.blocks;

import ic2.core.inventory.container.ContainerTileComponent;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.base.GeneratorChargeComp;
import ic2.core.inventory.slots.SlotCharge;
import ic2.core.util.math.Box2D;
import ic2.core.util.math.Vec2i;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.util.AdvancedSolarEnergyStringComp;
import trinsdar.advancedsolars.util.AdvancedSolarPanelComp;

public class ContainerAdvancedSolarPanel extends ContainerTileComponent<TileEntityAdvancedSolarPanel> {

    public static Box2D solarPanelLightBox = new Box2D(147, 41, 12, 12);
    public static Vec2i daySolarLightPos = new Vec2i(177, 17);
    public static Vec2i nightSolarLightPos = new Vec2i(193, 17);
    public static Box2D chargeBox = new Box2D(141, 60, 24, 9);
    public static Vec2i chargePos = new Vec2i(176, 0);

    public ContainerAdvancedSolarPanel(InventoryPlayer player, TileEntityAdvancedSolarPanel tile) {
        super(tile);
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 0, 98, 39));
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 1, 116, 39));
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 2, 98, 57));
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 3, 116, 57));

        if (tile.isSunVisible(tile.getWorld(), tile.getPos().up())){
            this.addComponent(new AdvancedSolarPanelComp(tile, solarPanelLightBox, daySolarLightPos));
        }else {
            if (tile.getWorld().canBlockSeeSky(tile.getPos().up())){
                this.addComponent(new AdvancedSolarPanelComp(tile, solarPanelLightBox, nightSolarLightPos));
            }
        }
        this.addComponent(new AdvancedSolarEnergyStringComp(tile));
        this.addComponent(new GeneratorChargeComp(tile, chargeBox, chargePos));
        this.addPlayerInventory(player);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiLoaded(GuiIC2 gui) {
        gui.dissableInvName();
        gui.disableName();
    }

    @Override
    public ResourceLocation getTexture() {
        return this.getGuiHolder().getTexture();
    }

    @Override
    public int guiInventorySize() {
        return this.getGuiHolder().slotCount;
    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.getGuiHolder().canInteractWith(playerIn);
    }

}
