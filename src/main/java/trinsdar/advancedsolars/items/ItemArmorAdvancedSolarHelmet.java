package trinsdar.advancedsolars.items;

import ic2.core.IC2;
import ic2.core.block.generator.tile.TileEntitySolarPanel;
import ic2.core.item.armor.base.ItemIC2AdvArmorBase;
import ic2.core.item.armor.electric.ItemArmorSolarHelmet;
import ic2.core.item.manager.ElectricItemManager;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.lang.storage.Ic2ItemLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.util.obj.IBootable;
import ic2.core.util.obj.ToolTipType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ItemArmorAdvancedSolarHelmet extends ItemIC2AdvArmorBase implements IBootable {
    @Override
    public void onLoad() {

    }

    public enum AdvancedSolarHelmetTypes{
        ADVANCED(11, 8, 1),
        HYBRID(12, 64, 8),
        ULTIMATE_HYBRID(13, 512, 64);

        private int id;
        private int production;
        private int lowerProduction;

        AdvancedSolarHelmetTypes(int id, int pro, int lowPro){
            this.id = id;
            this.production = pro;
            this.lowerProduction = lowPro;
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
    }
    AdvancedSolarHelmetTypes variant;

    public ItemArmorAdvancedSolarHelmet(AdvancedSolarHelmetTypes variant) {
        super(-1, EntityEquipmentSlot.HEAD);
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
            if (TileEntitySolarPanel.isSunVisible(world, player.getPosition())) {
                ElectricItemManager.chargeArmor(player, variant.getProduction());
            }else {
                ElectricItemManager.chargeArmor(player, variant.getLowerProduction());
            }

        }
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    public String getTexture() {
        return "ic2:textures/models/armor/solar";
    }

    @Override
    public ItemStack getRepairItem() {
        return ItemStack.EMPTY;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int meta) {
        return Ic2Icons.getTextures("advancedsolars_items")[variant.getId()];
    }
}
