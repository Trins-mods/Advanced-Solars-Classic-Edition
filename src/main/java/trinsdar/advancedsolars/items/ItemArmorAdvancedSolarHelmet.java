package trinsdar.advancedsolars.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.addons.IModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.IC2;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.tooltips.ToolTipHelper;
import ic2.curioplugin.core.CurioPlugin;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.items.IItemHandler;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class ItemArmorAdvancedSolarHelmet extends IC2AdvancedArmorBase implements IBaseArmorModule {

    private int
    production,
    lowerProduction,
    tier;
    public ItemArmorAdvancedSolarHelmet(String name, int pro, int lowPro, int tier) {
        super(name + "_solar_helmet", EquipmentSlot.HEAD, new PropertiesBuilder().maxDamage(0).rarity(Rarity.RARE));
        this.production = pro;
        this.lowerProduction = lowPro;
        this.tier = tier;
        IC2Items.registerItem(this);

    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return type == EquipmentSlot.HEAD;
    }

    @Override
    public void addToolTip(ItemStack stack, Player player, TooltipFlag type, ToolTipHelper helper) {
        super.addToolTip(stack, player, type, helper);
        helper.addDataTooltip(AdvancedSolarLang.helmetProduction, production);
        helper.addDataTooltip(AdvancedSolarLang.helmetLowerProduction, lowerProduction);
    }

    @Override
    public void onArmorTick(ItemStack stack, Level world, Player player) {
        super.onArmorTick(stack, world, player);
        if (!IC2.PLATFORM.isRendering()) {
            if (world.dimensionType().hasSkyLight() && world.canSeeSkyFromBelowWater(player.blockPosition())){
                if (TileEntityAdvancedSolarPanel.isSunVisible(world, player.blockPosition())) {
                    chargeInventory(player, production, tier, stack);
                }else {
                    chargeInventory(player, lowerProduction, tier, stack);
                }
            }

        }
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        onArmorTick(armor, world, player);
    }

    @Override
    public String getArmorTexture() {
        return AdvancedSolarsClassic.MODID + ":textures/models/" + getRegistryName().getPath();
    }

    @Override
    public String getTextureName() {
        return getRegistryName().getPath();
    }

    @Override
    public String getTextureFolder() {
        return "solar_helmets";
    }



    public int chargeInventory(Player player, int provided, int tier, ItemStack helmet) {

        int i;
        List<NonNullList<ItemStack>> invList = Arrays.asList(player.getInventory().armor, player.getInventory().offhand, player.getInventory().items);

        if (ElectricItem.MANAGER.getCharge(helmet) != ElectricItem.MANAGER.getCapacity(helmet)){
            int charged = (int)(ElectricItem.MANAGER.charge(helmet, provided, this.tier, false, false));
            provided -= charged;
        } else {
            for (NonNullList<ItemStack> inventory : invList) {
                int inventorySize = inventory.size();
                for (i=0; i < inventorySize && provided > 0; i++) {
                    ItemStack tStack = inventory.get(i);
                    if (tStack.isEmpty()) continue;
                    int charged = ElectricItem.MANAGER.charge(tStack, provided, this.tier, false, false);
                    provided -= charged;
                }
            }

            IModule plugin = IC2.PLUGINS.getModule("curio");
            if (plugin != null) {
                IItemHandler inv = new CurioPlugin().getCurioHandler(player);

                for(i = 0; i < inv.getSlots(); ++i) {
                    if (provided <= 0) {
                        break;
                    }
                    provided = (int)((double)provided - ElectricItem.MANAGER.charge(inv.getStackInSlot(i), provided, tier, false, false));
                }
            }
        }

        return provided;
    }

    public Multimap<Attribute, AttributeModifier> getDefaultAttributeModifiers(EquipmentSlot equipmentSlot) {
        Multimap<Attribute, AttributeModifier> modifiers = HashMultimap.create();
        if (equipmentSlot == EquipmentSlot.HEAD) {
            modifiers.put(Attributes.ARMOR, new AttributeModifier(UUID.fromString("2AD3F246-FEE1-4E67-B886-69FD380BB150"), "Armor modifier", 1.0, AttributeModifier.Operation.ADDITION));
        }

        return modifiers;
    }

    @Override
    public ModuleType getType(ItemStack itemStack) {
        return ModuleType.CHARGER;
    }

    @Override
    public boolean handlePacket(Player player, ItemStack itemStack, ItemStack itemStack1, String s, INetworkDataBuffer iNetworkDataBuffer, Dist dist) {
        return false;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.EMPTY;
    }
}
