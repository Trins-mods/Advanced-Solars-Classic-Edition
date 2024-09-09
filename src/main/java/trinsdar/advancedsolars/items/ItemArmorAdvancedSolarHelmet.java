package trinsdar.advancedsolars.items;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import ic2.api.addons.IModule;
import ic2.api.events.ArmorSlotEvent;
import ic2.api.items.armor.IArmorModule;
import ic2.api.items.electric.ElectricItem;
import ic2.api.network.buffer.INetworkDataBuffer;
import ic2.core.IC2;
import ic2.core.item.base.PropertiesBuilder;
import ic2.core.item.wearable.base.IBaseArmorModule;
import ic2.core.item.wearable.base.IC2AdvancedArmorBase;
import ic2.core.item.wearable.base.IC2ModularElectricArmor;
import ic2.core.platform.registries.IC2Items;
import ic2.core.utils.collection.CollectionUtils;
import ic2.core.utils.helpers.StackUtil;
import ic2.core.utils.tooltips.ToolTipHelper;
import ic2.curioplugin.core.CurioPlugin;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntMaps;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.items.IItemHandler;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.BlockEntityAdvancedSolarPanel;
import trinsdar.advancedsolars.util.AdvancedSolarLang;
import trinsdar.advancedsolars.util.Registry;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ItemArmorAdvancedSolarHelmet extends IC2ModularElectricArmor implements IBaseArmorModule {

    private int
            production,
            lowerProduction,
            tier;
    private final boolean nano;
    protected Object2IntMap<ModuleType> alternative = new Object2IntOpenHashMap<>();
    public ItemArmorAdvancedSolarHelmet(String name, int pro, int lowPro, int tier) {
        super(name + "_solar_helmet", EquipmentSlot.HEAD, new PropertiesBuilder().rarity(Rarity.RARE));
        this.production = pro;
        this.lowerProduction = lowPro;
        this.tier = tier;
        this.nano = name.equals("advanced");
        IC2Items.registerItem(this);
        this.addSlotType(ModuleType.BATTERY, 1);
        this.addSlotType(ModuleType.GENERIC, nano ? 2 : 3);
        MinecraftForge.EVENT_BUS.post(new ArmorSlotEvent(this, "Advanced Solar Helmets", slot, this.types));
        if (nano){
            this.alternative.put(ModuleType.HUD, 9);
            this.alternative.mergeInt(ModuleType.BATTERY, 1, Integer::sum);
            this.alternative.mergeInt(ModuleType.CHARGER, 1, Integer::sum);
            this.alternative.mergeInt(ModuleType.GENERIC, 3, Integer::sum);
            MinecraftForge.EVENT_BUS.post(new ArmorSlotEvent(this, "Advanced Solar Helmet Alternative", slot, this.alternative));
        }
    }

    public Object2IntMap<IArmorModule.ModuleType> getModuleLimits(ItemStack stack) {
        return StackUtil.getNbtData(stack).getBoolean("upgraded") ? Object2IntMaps.unmodifiable(this.alternative) : Object2IntMaps.unmodifiable(this.types);
    }

    public ItemStack createDefaultArmor() {
        ItemStack stack = new ItemStack(this);
        Map<ModuleType, List<ItemStack>> types = CollectionUtils.createLinkedMap();
        Item battery = nano ? IC2Items.ENERGY_CRYSTAL : IC2Items.LAPATRON_CRYSTAL;
        types.put(ModuleType.BATTERY, ObjectArrayList.wrap(new ItemStack[]{new ItemStack(battery)}));
        if (!nano) {
            types.put(ModuleType.GENERIC, ObjectArrayList.wrap(new ItemStack[]{new ItemStack(IC2Items.AUTO_FEED_MODULE), new ItemStack(IC2Items.AIR_REFILL_MODULE), new ItemStack(IC2Items.PROTECTION_MODULE)}));
        }
        setAndInstallTypes(stack, types);
        return stack;
    }

    public double getDamageAbsorptionRatio(ItemStack stack) {
        return nano ? 0.9 : 1.0;
    }

    public int getEnergyPerDamage(ItemStack stack) {
        return nano ? 800 : 900;
    }

    public boolean isFullyAbsorbingFallDamage(ItemStack stack) {
        return !nano || StackUtil.getNbtData(stack).getBoolean("upgraded");
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public boolean canInstallInArmor(ItemStack stack, ItemStack armor, EquipmentSlot type) {
        return false;
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
            if (world.dimensionType().hasSkyLight() && world.canSeeSkyFromBelowWater(player.blockPosition())) {
                if (BlockEntityAdvancedSolarPanel.isSunVisible(world, player.blockPosition())) {
                    chargeInventory(player, production, tier, stack);
                } else {
                    chargeInventory(player, lowerProduction, tier, stack);
                }
            }

        }
    }

    @Override
    public void onTick(ItemStack stack, ItemStack armor, Level world, Player player) {
        //onArmorTick(armor, world, player);
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

        if (ElectricItem.MANAGER.getCharge(helmet) != ElectricItem.MANAGER.getCapacity(helmet)) {
            int charged = (int) (ElectricItem.MANAGER.charge(helmet, provided, this.tier, false, false));
            provided -= charged;
        } else {
            for (NonNullList<ItemStack> inventory : invList) {
                int inventorySize = inventory.size();
                for (i = 0; i < inventorySize && provided > 0; i++) {
                    ItemStack tStack = inventory.get(i);
                    if (tStack.isEmpty()) continue;
                    int charged = ElectricItem.MANAGER.charge(tStack, provided, this.tier, false, false);
                    provided -= charged;
                }
            }

            IModule plugin = IC2.PLUGINS.getModule("curio");
            if (plugin != null) {
                IItemHandler inv = new CurioPlugin().getCurioHandler(player);

                for (i = 0; i < inv.getSlots(); ++i) {
                    if (provided <= 0) {
                        break;
                    }
                    provided = (int) ((double) provided - ElectricItem.MANAGER.charge(inv.getStackInSlot(i), provided, tier, false, false));
                }
            }
        }

        return provided;
    }

    @Override
    public ModuleType getType(ItemStack itemStack) {
        return ModuleType.CHARGER;
    }

    @Override
    public boolean handlePacket(Player player, ItemStack itemStack, ItemStack itemStack1, String s, INetworkDataBuffer iNetworkDataBuffer, Dist dist) {
        return false;
    }

}
