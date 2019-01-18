package trinsdar.advancedsolars.blocks;

import ic2.core.block.generator.tile.TileEntitySolarPanel;
import ic2.core.inventory.container.ContainerTileComponent;
import ic2.core.inventory.filters.BasicItemFilter;
import ic2.core.inventory.gui.GuiIC2;
import ic2.core.inventory.gui.components.base.GeneratorChargeComp;
import ic2.core.inventory.gui.components.base.MachineProgressComp;
import ic2.core.inventory.gui.components.special.CreativeEnergyStorageComp;
import ic2.core.inventory.gui.components.special.SolarPanelComp;
import ic2.core.inventory.slots.SlotCharge;
import ic2.core.inventory.slots.SlotCustom;
import ic2.core.inventory.slots.SlotOutput;
import ic2.core.platform.registry.Ic2GuiComp;
import ic2.core.util.math.Box2D;
import ic2.core.util.math.Vec2i;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.util.AdvancedSolarPanelComp;
import trinsdar.advancedsolars.util.SolarChargeComponent;

public class ContainerAdvancedSolarPanel extends ContainerTileComponent<TileEntityAdvancedSolarPanel> {

    public static Box2D solarPanelLightBox = new Box2D(24, 41, 14, 14);
    public static Vec2i daySolarLightPos = new Vec2i(195, 15);
    public static Vec2i nightSolarLightPos = new Vec2i(210, 15);
    public static Box2D chargeBox = new Box2D(19, 24, 24, 14);
    public static Vec2i chargePos = new Vec2i(195, 0);

    public ContainerAdvancedSolarPanel(InventoryPlayer player, TileEntityAdvancedSolarPanel tile) {
        super(tile);
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 0, 17, 59));
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 1, 35, 59));
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 2, 53, 59));
        this.addSlotToContainer(new SlotCharge(tile, tile.tier, 3, 71, 59));

        if (tile.isSunVisible(tile.getWorld(), tile.getPos().up())){
            this.addComponent(new AdvancedSolarPanelComp(tile, solarPanelLightBox, daySolarLightPos));
        }else {
            if (tile.getWorld().canBlockSeeSky(tile.getPos().up())){
                this.addComponent(new AdvancedSolarPanelComp(tile, solarPanelLightBox, nightSolarLightPos));
            }
        }
        this.addComponent(new SolarChargeComponent(tile, chargeBox, chargePos));

        this.addPlayerInventory(player, 9, 2);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void onGuiLoaded(GuiIC2 gui) {
        gui.setMaxGuiX(194);
        gui.dissableInvName();
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
