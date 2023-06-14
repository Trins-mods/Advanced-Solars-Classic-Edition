package trinsdar.advancedsolars.util;

import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.MachineRecipeList;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.Tags;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

public class AdvancedSolarsRecipes {
    public static final MachineRecipeList MOLECULAR_TRANSFORMER = new MachineRecipeList("molecular_transformer", AdvancedSolarsRecipes::initMolecularTransformerRecipes);

    public static void init(){
        IC2.RECIPES.get(true).getLists().add(MOLECULAR_TRANSFORMER);
        IC2.RECIPES.get(true).compressor.registerListener(r -> r.addSimpleRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "iridium_ore_to_iridium_ingot"), new ItemStack(Registry.IRIDIUM_INGOT), IC2Items.ORE_IRIDIUM));
        AdvRecipeRegistry.INSTANCE.registerListener(AdvancedSolarsRecipes::initCraftingRecipes);
    }

    private static void initMolecularTransformerRecipes(IMachineRecipeList list){

    }

    public static void initCraftingRecipes(IAdvancedCraftingManager registry){
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "sunnarium"), new ItemStack(Registry.SUNNARIUM), "UUU", "GGG", "UUU", 'U', IC2Items.UUMATTER, 'G', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "sunnarium_alloy"), new ItemStack(Registry.SUNNARIUM_ALLOY), "III", "ISI", "III", 'I', IC2Items.PLATE_IRIDIUM, 'S', Registry.SUNNARIUM);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "iridium_iron_plate"), new ItemStack(Registry.IRIDIUM_IRON_PLATE), "rrr", "rir", "rrr", 'r', IC2Tags.INGOT_REFINED_IRON, 'i', getItemTag(new ResourceLocation("forge", "ingots/iridium")));
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "reinforced_iridium_iron_plate"), new ItemStack(Registry.REINFORCED_IRIDIUM_IRON_PLATE), "aca", "cic", "aca", 'a', IC2Items.PLATE_ADVANCED_ALLOY, 'c', IC2Items.CARBON_PLATE, 'i', Registry.IRIDIUM_IRON_PLATE);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID,"irradiant_reinforced_plate"), new ItemStack(Registry.IRRADIANT_REINFORCED_PLATE), "rsr", "lRl", "rdr", 'r', Tags.Items.DUSTS_REDSTONE, 's', Registry.SUNNARIUM_PART, 'l', Tags.Items.DYES_BLUE, 'R', Registry.REINFORCED_IRIDIUM_IRON_PLATE, 'd', Tags.Items.GEMS_DIAMOND);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "sunnarium_part"), new ItemStack(Registry.SUNNARIUM_PART), "ugu", 'u', IC2Items.UUMATTER, 'g', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "irradiant_uranium"), new ItemStack(Registry.IRRADIANT_URANIUM), "ugu", "geg", "ugu", 'u', IC2Items.UUMATTER, 'e', getUranium(), 'g', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "enriched_sunnarium"), new ItemStack(Registry.ENRICHED_SUNNARIUM), "iii", "isi", "iii", 'i', Registry.IRRADIANT_URANIUM, 's', Registry.SUNNARIUM);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "irradiant_glass_pane"), new ItemStack(Registry.IRRADIANT_GLASS_PANE, 6), "rrr", "igi", "rrr", 'r', IC2Blocks.REINFORCED_GLASS, 'i', Registry.IRRADIANT_URANIUM, 'g', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "enriched_sunnarium_alloy"), new ItemStack(Registry.ENRICHED_SUNNARIUM_ALLOY), "pep", "ese", "pep", 'p', IC2Items.CELL_PLASMA, 'e', Registry.ENRICHED_SUNNARIUM, 's', Registry.SUNNARIUM_ALLOY);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "mt_core"), new ItemStack(Registry.MT_CORE), "INI", "I I", "INI", 'I', Registry.IRRADIANT_GLASS_PANE, 'N', IC2Items.REFLECTOR_THICK);

        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "advanced_solar_panel"), new ItemStack(Registry.ADVANCED_SOLAR_PANEL), "rrr", "asa", "cic", 'r', Registry.IRRADIANT_GLASS_PANE, 'a', IC2Items.PLATE_ADVANCED_ALLOY, 's', IC2Blocks.SOLAR_PANEL_LV, 'c', IC2Items.ADVANCED_CIRCUIT, 'i', Registry.IRRADIANT_REINFORCED_PLATE);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "advanced_solar_helmet"), new ItemStack(Registry.ADVANCED_SOLAR_HELMET), "sas", "cnc", "glg", 's', IC2Items.SOLAR_HELMET_ADVANCED, 'a', Registry.ADVANCED_SOLAR_PANEL, 'c', IC2Items.ADVANCED_CIRCUIT, 'n', IC2Items.SOLAR_HELMET_ADVANCED, 'g', IC2Items.GOLD_CABLE_2X_INSULATED, 'l', IC2Blocks.TRANSFORMER_LV);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "hybrid_solar_panel"), new ItemStack(Registry.HYBRID_SOLAR_PANEL), "CmC", "iai", "csc", 'C', IC2Items.CARBON_PLATE, 'm', IC2Blocks.SOLAR_PANEL_MV, 'i', IC2Items.PLATE_IRIDIUM, 'a', Registry.ADVANCED_SOLAR_PANEL, 'c', IC2Items.ADVANCED_CIRCUIT, 's', Registry.ENRICHED_SUNNARIUM);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "hybrid_solar_helmet"), new ItemStack(Registry.HYBRID_SOLAR_HELMET), " H ", "cqc", "ghg", 'H', Registry.HYBRID_SOLAR_PANEL, 'c', IC2Items.ADVANCED_CIRCUIT, 'q', Registry.ADVANCED_SOLAR_HELMET, 'g', IC2Items.GLASSFIBER_CABLE, 'h', IC2Blocks.TRANSFORMER_HV);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "ultimate_hybrid_solar_panel"), new ItemStack(Registry.ULTIMATE_HYBRID_SOLAR_PANEL), " h ", "pHp", "scs", 'h', IC2Blocks.SOLAR_PANEL_HV, 'p', IC2Items.PLASMA_CORE, 'H', Registry.HYBRID_SOLAR_PANEL, 's', Registry.ENRICHED_SUNNARIUM_ALLOY, 'c', IC2Items.COAL_CHUNK);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "ultimate_hybrid_solar_helmet"), new ItemStack(Registry.ULTIMATE_HYBRID_SOLAR_HELMET),  "ppp", "pup", "ihi", 'p', IC2Items.CELL_PLASMA, 'u', Registry.ULTIMATE_HYBRID_SOLAR_PANEL, 'i', IC2Items.PLATE_IRIDIUM, 'h', Registry.HYBRID_SOLAR_HELMET);
        registry.addShapedRecipe(new ResourceLocation(AdvancedSolarsClassic.MODID, "molecular_transformer"), new ItemStack(Registry.MOLECULAR_TRANSFORMER), "AEA", "CMC", "AEA", 'A', IC2Blocks.ADVANCED_MACHINE_BLOCK, 'E', IC2Blocks.TRANSFORMER_EV, 'C', IC2Items.ADVANCED_CIRCUIT, 'M', Registry.MT_CORE);
    }

    public static Object getUranium(){
        Object defaultUranium =  IC2Items.INGOT_URANIUM_ENRICHED_ENDERPEARL;
        return switch (AdvancedSolarsConfig.MISC.INGOT_IN_IRRADIANT_URANIUM) {
            case URANIUM -> getItemTag(new ResourceLocation("forge", "ingots/uranium"));
            case URANIUM233 ->
                    getItemTag(new ResourceLocation("forge", "ingots/uranium233"));
            case URANIUM235 ->
                    getItemTag(new ResourceLocation("forge", "ingots/uranium235"));
            default -> defaultUranium;
        };
    }

    private static TagKey<Item> getItemTag(ResourceLocation location){
        return TagKey.create(net.minecraft.core.Registry.ITEM_REGISTRY, location);
    }
}
