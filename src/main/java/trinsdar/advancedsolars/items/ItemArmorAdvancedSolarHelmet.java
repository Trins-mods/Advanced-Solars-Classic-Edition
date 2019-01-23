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

    public enum AdvancedSolarHelmetTypes{
        ADVANCED(11, 8, 1, 100000, 100, 2, 800, 0.9D, ":textures/models/advancedsolarhelmet"),
        HYBRID(12, 64, 8, 1000000, 1000, 3, 900, 1.0D, ":textures/models/hybridsolarhelmet"),
        ULTIMATE_HYBRID(13, 512, 64, 1000000, 2000, 3, 900, 1.0D, ":textures/models/ultimatesolarhelmet");

        private int
        id,
        production,
        lowerProduction,
        maxCharge,
        maxTransfer,
        tier,
        energyPerDamage;
        double damageAbsorpationRatio;
        String texture;

        AdvancedSolarHelmetTypes(int id, int pro, int lowPro, int maxCharge, int maxTransfer, int tier, int energyPerDamage, double damageAbsorpationRatio, String texture){
            this.id = id;
            this.production = pro;
            this.lowerProduction = lowPro;
            this.maxCharge = maxCharge;
            this.maxTransfer = maxTransfer;
            this.tier = tier;
            this.energyPerDamage = energyPerDamage;
            this.damageAbsorpationRatio = damageAbsorpationRatio;
            this.texture = texture;
        }

        public int getId() {
            return id;
        }

        public int getProduction() {
            return production;
        }

        public int getLowerProduction() {
            return lowerProduction;
        }

        public int getMaxCharge(){
            return maxCharge;
        }

        public int getMaxTransfer() {
            return maxTransfer;
        }

        public int getTier() {
            return tier;
        }

        public int getEnergyPerDamage() {
            return energyPerDamage;
        }

        public double getDamageAbsorpationRatio() {
            return damageAbsorpationRatio;
        }

        public String getTexture() {
            return texture;
        }
    }
    AdvancedSolarHelmetTypes variant;

    public ItemArmorAdvancedSolarHelmet(AdvancedSolarHelmetTypes variant) {
        super(-1, EntityEquipmentSlot.HEAD, variant.getMaxCharge(), variant.getMaxTransfer(), variant.getTier());
        this.variant = variant;
        String name = variant.toString().toLowerCase() + "_solar_helmet";
        this.setRegistryName(name);
        this.setUnlocalizedName(AdvancedSolarsClassic.MODID + "." + name);
        this.setMaxDamage(0);
    }

    @Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        super.onSortedItemToolTip(stack, player, debugTooltip, tooltip, sortedTooltip);
        sortedTooltip.get(ToolTipType.Shift).add(AdvancedSolarLang.helmetProduction.getLocalizedFormatted(variant.getProduction()));
        sortedTooltip.get(ToolTipType.Shift).add(AdvancedSolarLang.helmetLowerPorduction.getLocalizedFormatted(variant.getLowerProduction()));

    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
        if (!IC2.platform.isRendering()) {
            if (world.provider.hasSkyLight() && world.canBlockSeeSky(player.getPosition())){
                if (TileEntitySolarPanel.isSunVisible(world, player.getPosition())) {
                    chargeInventory(player, variant.getProduction(), variant.getTier());
                }else {
                    chargeInventory(player, variant.getLowerProduction(), variant.getTier());
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
        return AdvancedSolarsClassic.MODID + variant.getTexture();
    }

    @Override
    public ItemStack getRepairItem() {
        return ItemStack.EMPTY;
    }

    @Override
    public double getDamageAbsorptionRatio() {
        return variant.getDamageAbsorpationRatio();
    }

    @Override
    public int getEnergyPerDamage() {
        return variant.getEnergyPerDamage();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int meta) {
        return Ic2Icons.getTextures("advancedsolars_items")[variant.getId()];
    }



    public int chargeInventory(EntityPlayer player, int provided, int tier) {
        EntityEquipmentSlot[] var2 = EntityEquipmentSlot.values();

        int i;
        for(i = 0; i < var2.length; ++i) {
            EntityEquipmentSlot slot = var2[i];
            if (provided <= 0) {
                break;
            }

            if (slot.getSlotType() != EntityEquipmentSlot.Type.HAND) {
                provided = (int)((double)provided - ElectricItem.manager.charge(player.getItemStackFromSlot(slot), (double)provided, tier, false, false));
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
        for(i = 0; i < player.inventory.mainInventory.size(); ++i) {
            if (provided <= 0) {
                break;
            }
            provided = (int)((double)provided - ElectricItem.manager.charge(player.inventory.mainInventory.get(i), (double)provided, tier, false, false));
        }

        return provided;
    }
}
