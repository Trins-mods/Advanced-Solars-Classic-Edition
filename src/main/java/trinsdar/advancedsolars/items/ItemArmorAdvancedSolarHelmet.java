package trinsdar.advancedsolars.items;

import ic2.api.addons.IModule;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.core.IC2;
import ic2.core.item.wearable.armor.electric.NanoSuit;
import ic2.core.platform.registries.IC2Items;
import ic2.curioplugin.core.CurioPlugin;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.items.IItemHandler;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.TileEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public class ItemArmorAdvancedSolarHelmet extends NanoSuit {

    private int
    production,
    lowerProduction,
    energyPerDamage;
    double damageAbsorpationRatio;
    Supplier<Integer> capacity;
    Supplier<Integer> transferLimit;

    public ItemArmorAdvancedSolarHelmet(String name, int pro, int lowPro, Supplier<Integer> capacity, Supplier<Integer> transferLimit, int tier, int energyPerDamage, double damageAbsorpationRatio) {
        super(name + "_solar_helmet", EquipmentSlot.HEAD);
        if (!name.contains("advanced")) {
            this.addSlotType(IArmorModule.ModuleType.GENERIC, 1);
        }
        this.production = pro;
        this.lowerProduction = lowPro;
        this.energyPerDamage = energyPerDamage;
        this.damageAbsorpationRatio = damageAbsorpationRatio;
        this.capacity = capacity;
        this.transferLimit = transferLimit;
        IC2Items.registerItem(this);
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return capacity.get();
    }

    @Override
    public int getTransferLimit(ItemStack stack) {
        return transferLimit.get();
    }

    @Override
    public int getTransferLimit() {
        return transferLimit.get();
    }

    @Override
    public int getTier() {
        return tier;
    }

    @Override
    public int getTier(ItemStack stack) {
        return tier;
    }

    /*@Override
    public void onSortedItemToolTip(ItemStack stack, EntityPlayer player, boolean debugTooltip, List<String> tooltip, Map<ToolTipType, List<String>> sortedTooltip) {
        super.onSortedItemToolTip(stack, player, debugTooltip, tooltip, sortedTooltip);
        sortedTooltip.get(ToolTipType.Shift).add(AdvancedSolarLang.helmetProduction.getLocalizedFormatted(production));
        sortedTooltip.get(ToolTipType.Shift).add(AdvancedSolarLang.helmetLowerPorduction.getLocalizedFormatted(lowerProduction));

    }*/

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

    @Override
    public double getDamageAbsorptionRatio(ItemStack stack) {
        return damageAbsorpationRatio;
    }

    @Override
    public int getEnergyPerDamage(ItemStack stack) {
        return energyPerDamage;
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
}
