package trinsdar.advancedsolars.items;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.block.generator.tile.TileEntitySolarPanel;
import ic2.core.inventory.base.IHasInventory;
import ic2.core.item.armor.base.ItemElectricArmorBase;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.util.obj.IBootable;
import ic2.core.util.obj.ToolTipType;
import ic2.core.util.obj.plugins.IBaublesPlugin;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemArmorAdvancedSolarHelmet extends ItemElectricArmorBase implements IBootable {
    @Override
    public void onLoad() {

    }

    private int
    id,
    production,
    lowerProduction,
    energyPerDamage;
    double damageAbsorpationRatio;
    String texture;

    public ItemArmorAdvancedSolarHelmet(String name, int id, int pro, int lowPro, int maxCharge, int maxTransfer, int tier, int energyPerDamage, double damageAbsorpationRatio, String texture) {
        super(-1, EntityEquipmentSlot.HEAD, maxCharge, maxTransfer, tier);
        this.id = id;
        this.production = pro;
        this.lowerProduction = lowPro;
        this.energyPerDamage = energyPerDamage;
        this.damageAbsorpationRatio = damageAbsorpationRatio;
        this.texture = texture;
        this.setUnlocalizedName(name + "SolarHelmet");
        this.setMaxDamage(0);
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        super.onSortedItemToolTip(stack, player, debugTooltip, tooltip, sortedTooltip);
        sortedTooltip.get(ToolTipType.Shift).add(AdvancedSolarLang.helmetProduction.getLocalizedFormatted(production));
        sortedTooltip.get(ToolTipType.Shift).add(AdvancedSolarLang.helmetLowerPorduction.getLocalizedFormatted(lowerProduction));

    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!IC2.platform.isRendering()) {
            if (world.provider.hasSkyLight() && world.canBlockSeeSky(player.getPosition())){
                if (TileEntitySolarPanel.isSunVisible(world, player.getPosition())) {
                    chargeInventory(player, production, tier);
                }else {
                    chargeInventory(player, lowerProduction, tier);
                }
            }

        }
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    public String getTexture() {
        return AdvancedSolarsClassic.MODID + texture;
    }

    @Override
    public ItemStack getRepairItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return damageAbsorpationRatio;
    }

    @Override
    public int getEnergyPerDamage() {
        return energyPerDamage;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int meta) {
        return Ic2Icons.getTextures("advancedsolars_items")[id];
    }



    public int chargeInventory(EntityPlayer player, int provided, int tier) {

        int i;
        List<NonNullList<ItemStack>> invList = Arrays.asList(player.inventory.armorInventory, player.inventory.offHandInventory, player.inventory.mainInventory);

        int meSlot = player.inventory.getSlotFor(player.inventory.getItemStack());
        for (NonNullList inventory : invList) {
            int inventorySize = inventory.size();
            for (i=0; i < inventorySize && provided > 0; i++) {
                if (i == meSlot) continue;
                ItemStack tStack = (ItemStack)inventory.get(i);
                if (tStack.isEmpty()) continue;
                int charged = (int)(ElectricItem.manager.charge(tStack, (double)provided, this.tier, false, false));
                provided -= charged;
            }
        }

        IBaublesPlugin plugin = IC2.loader.getPlugin("baubles", IBaublesPlugin.class);
        if (plugin != null) {
            IHasInventory inv = plugin.getBaublesInventory(player);

            for(i = 0; i < inv.getSlotCount(); ++i) {
                if (provided <= 0) {
                    break;
                }
                provided = (int)((double)provided - ElectricItem.manager.charge(inv.getStackInSlot(i), (double)provided, tier, false, false));
            }
        }

        return provided;
    }
}
