package trinsdar.advancedsolars.util;

import ic2.api.recipes.registries.IAdvancedCraftingManager;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.api.recipes.registries.IMachineRecipeList;
import ic2.core.IC2;
import ic2.core.block.machines.recipes.ElectrolyzerRecipeList;
import ic2.core.block.machines.recipes.MachineRecipeList;
import ic2.core.platform.recipes.misc.AdvRecipeRegistry;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.registries.IC2Tags;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.registries.ForgeRegistries;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

public class AdvancedSolarsRecipes {
    public static final ElectrolyzerRecipeList MOLECULAR_TRANSFORMER = new ElectrolyzerRecipeList(AdvancedSolarsRecipes::initMolecularTransformerRecipes){
        @Override
        public void addDualRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
            addChargeRecipe(id, input, output, energy);
        }

        @Override
        public void addDischargeRecipe(ResourceLocation id, ItemStack input, ItemStack output, int energy) {
        }

        @Override
        public String getFolder() {
            return "molecular_transformer";
        }
    };

    public static void init(){
        IC2.RECIPES.get(true).getLists().add(MOLECULAR_TRANSFORMER);
        //hack till ic2c has a proper method of doing custom recipe maps
        IC2.RECIPES.get(true).electrolyzer.registerListener(r -> MOLECULAR_TRANSFORMER.reload());
        IC2.RECIPES.get(true).compressor.registerListener(r -> r.addSimpleRecipe(id( "iridium_ore_to_iridium_ingot"), new ItemStack(Registry.IRIDIUM_INGOT), IC2Items.ORE_IRIDIUM));
        AdvRecipeRegistry.INSTANCE.registerListener(AdvancedSolarsRecipes::initCraftingRecipes);
    }

    private static void initMolecularTransformerRecipes(IElectrolyzerRecipeList list){
        list.addChargeRecipe(id( "nether_star"), new ItemStack(Items.WITHER_SKELETON_SKULL), new ItemStack(Items.NETHER_STAR), 250000000);
        list.addChargeRecipe(id( "iridium_ore"), new ItemStack(Items.IRON_INGOT), new ItemStack(IC2Items.ORE_IRIDIUM), 9000000);
        list.addChargeRecipe(id("gunpowder"), new ItemStack(Items.NETHERRACK), new ItemStack(Items.GUNPOWDER, 2), 70000);
        list.addChargeRecipe(id("gravel"), new ItemStack(Items.SAND), new ItemStack(Items.GRAVEL), 50000);
        list.addChargeRecipe(id("clay"), new ItemStack(Items.DIRT), new ItemStack(Items.CLAY), 50000);
        list.addChargeRecipe(id("coal"), new ItemStack(Items.CHARCOAL), new ItemStack(Items.COAL), 60000);
        list.addChargeRecipe(id("sunnarium_piece"), new ItemStack(Items.GLOWSTONE_DUST), new ItemStack(Registry.SUNNARIUM_PART), 1000000);
        list.addChargeRecipe(id("sunnarium"), new ItemStack(Items.GLOWSTONE), new ItemStack(Registry.SUNNARIUM), 9000000);
        list.addChargeRecipe(id("glowstone"), new ItemStack(Items.YELLOW_WOOL), new ItemStack(Items.GLOWSTONE), 500000);
        list.addChargeRecipe(id("lapis_block"), new ItemStack(Items.BLUE_WOOL), new ItemStack(Items.LAPIS_BLOCK), 500000);
        list.addChargeRecipe(id("redstone_block"), new ItemStack(Items.RED_WOOL), new ItemStack(Items.REDSTONE_BLOCK), 500000);
        if (ModList.get().isLoaded("antimatter_shared")){
            Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "sapphire"));
            if (item != null){
                list.addChargeRecipe(id("sapphire"), new ItemStack(Items.LAPIS_LAZULI), new ItemStack(item), 5000000);
            }
            item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "ruby"));
            if (item != null){
                list.addChargeRecipe(id("ruby"), new ItemStack(Items.REDSTONE), new ItemStack(item), 5000000);
            }
            item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "dust_chrome"));
            Item item2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "dust_titanium"));
            if (item != null && item2 != null){
                list.addChargeRecipe(id("chrome_dust"), new ItemStack(item2), new ItemStack(item), 500000);
            }
            item = ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "ingot_chrome"));
            item2 = ForgeRegistries.ITEMS.getValue(new ResourceLocation("antimatter_shared", "ingot_titanium"));
            if (item != null && item2 != null){
                list.addChargeRecipe(id("chrome_ingot"), new ItemStack(item2), new ItemStack(item), 500000);
            }
        }
        if (ModList.get().isLoaded("ae2")){
            list.addChargeRecipe(id("certus_quartz"), new ItemStack(Items.QUARTZ), new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation("ae2", "certus_quartz_crystal"))), 500000);
        }
        //todo copper to nickel for 300000
        list.addChargeRecipe(id("silver_ingot"), new ItemStack(IC2Items.INGOT_TIN), new ItemStack(IC2Items.INGOT_SILVER), 500000);
        list.addChargeRecipe(id("gold_ingot"), new ItemStack(IC2Items.INGOT_SILVER), new ItemStack(Items.GOLD_INGOT), 500000);
        //TODO gold to platinum for 9000000
        list.addChargeRecipe(id("industrial_diamond"), new ItemStack(Items.COAL), new ItemStack(IC2Items.INDUSTRIAL_DIAMOND), 9000000);
        list.addChargeRecipe(id("diamond"), new ItemStack(IC2Items.INDUSTRIAL_DIAMOND), new ItemStack(Items.DIAMOND), 1000000);
    }

    public static void initCraftingRecipes(IAdvancedCraftingManager registry){
        registry.addShapedRecipe(id( "sunnarium"), new ItemStack(Registry.SUNNARIUM), "UUU", "GGG", "UUU", 'U', IC2Items.UUMATTER, 'G', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(id( "sunnarium_alloy"), new ItemStack(Registry.SUNNARIUM_ALLOY), "III", "ISI", "III", 'I', IC2Items.PLATE_IRIDIUM, 'S', Registry.SUNNARIUM);
        registry.addShapedRecipe(id( "iridium_iron_plate"), new ItemStack(Registry.IRIDIUM_IRON_PLATE), "rrr", "rir", "rrr", 'r', IC2Tags.INGOT_REFINED_IRON, 'i', getItemTag(new ResourceLocation("forge", "ingots/iridium")));
        registry.addShapedRecipe(id( "reinforced_iridium_iron_plate"), new ItemStack(Registry.REINFORCED_IRIDIUM_IRON_PLATE), "aca", "cic", "aca", 'a', IC2Items.PLATE_ADVANCED_ALLOY, 'c', IC2Items.CARBON_PLATE, 'i', Registry.IRIDIUM_IRON_PLATE);
        registry.addShapedRecipe(id("irradiant_reinforced_plate"), new ItemStack(Registry.IRRADIANT_REINFORCED_PLATE), "rsr", "lRl", "rdr", 'r', Tags.Items.DUSTS_REDSTONE, 's', Registry.SUNNARIUM_PART, 'l', Tags.Items.DYES_BLUE, 'R', Registry.REINFORCED_IRIDIUM_IRON_PLATE, 'd', Tags.Items.GEMS_DIAMOND);
        registry.addShapedRecipe(id( "sunnarium_part"), new ItemStack(Registry.SUNNARIUM_PART), "ugu", 'u', IC2Items.UUMATTER, 'g', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(id( "irradiant_uranium"), new ItemStack(Registry.IRRADIANT_URANIUM), "ugu", "geg", "ugu", 'u', IC2Items.UUMATTER, 'e', getUranium(), 'g', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(id( "enriched_sunnarium"), new ItemStack(Registry.ENRICHED_SUNNARIUM), "iii", "isi", "iii", 'i', Registry.IRRADIANT_URANIUM, 's', Registry.SUNNARIUM);
        registry.addShapedRecipe(id( "irradiant_glass_pane"), new ItemStack(Registry.IRRADIANT_GLASS_PANE, 6), "rrr", "igi", "rrr", 'r', IC2Blocks.REINFORCED_GLASS, 'i', Registry.IRRADIANT_URANIUM, 'g', Tags.Items.DUSTS_GLOWSTONE);
        registry.addShapedRecipe(id( "enriched_sunnarium_alloy"), new ItemStack(Registry.ENRICHED_SUNNARIUM_ALLOY), "pep", "ese", "pep", 'p', IC2Items.CELL_PLASMA, 'e', Registry.ENRICHED_SUNNARIUM, 's', Registry.SUNNARIUM_ALLOY);
        registry.addShapedRecipe(id( "mt_core"), new ItemStack(Registry.MT_CORE), "INI", "I I", "INI", 'I', Registry.IRRADIANT_GLASS_PANE, 'N', IC2Items.REFLECTOR_THICK);

        registry.addShapedRecipe(id( "advanced_solar_panel"), new ItemStack(Registry.ADVANCED_SOLAR_PANEL), "rrr", "asa", "cic", 'r', Registry.IRRADIANT_GLASS_PANE, 'a', IC2Items.PLATE_ADVANCED_ALLOY, 's', IC2Blocks.SOLAR_PANEL_LV, 'c', IC2Items.ADVANCED_CIRCUIT, 'i', Registry.IRRADIANT_REINFORCED_PLATE);
        registry.addShapedRecipe(id( "advanced_solar_helmet"), new ItemStack(Registry.ADVANCED_SOLAR_HELMET), "sas", "cnc", "glg", 's', IC2Items.SOLAR_HELMET_ADVANCED, 'a', Registry.ADVANCED_SOLAR_PANEL, 'c', IC2Items.ADVANCED_CIRCUIT, 'n', IC2Items.SOLAR_HELMET_ADVANCED, 'g', IC2Items.GOLD_CABLE_2X_INSULATED, 'l', IC2Blocks.TRANSFORMER_LV);
        registry.addShapedRecipe(id( "hybrid_solar_panel"), new ItemStack(Registry.HYBRID_SOLAR_PANEL), "CmC", "iai", "csc", 'C', IC2Items.CARBON_PLATE, 'm', IC2Blocks.SOLAR_PANEL_MV, 'i', IC2Items.PLATE_IRIDIUM, 'a', Registry.ADVANCED_SOLAR_PANEL, 'c', IC2Items.ADVANCED_CIRCUIT, 's', Registry.ENRICHED_SUNNARIUM);
        registry.addShapedRecipe(id( "hybrid_solar_helmet"), new ItemStack(Registry.HYBRID_SOLAR_HELMET), " H ", "cqc", "ghg", 'H', Registry.HYBRID_SOLAR_PANEL, 'c', IC2Items.ADVANCED_CIRCUIT, 'q', Registry.ADVANCED_SOLAR_HELMET, 'g', IC2Items.GLASSFIBER_CABLE, 'h', IC2Blocks.TRANSFORMER_HV);
        registry.addShapedRecipe(id( "ultimate_hybrid_solar_panel"), new ItemStack(Registry.ULTIMATE_HYBRID_SOLAR_PANEL), " h ", "pHp", "scs", 'h', IC2Blocks.SOLAR_PANEL_HV, 'p', IC2Items.PLASMA_CORE, 'H', Registry.HYBRID_SOLAR_PANEL, 's', Registry.ENRICHED_SUNNARIUM_ALLOY, 'c', IC2Items.COAL_CHUNK);
        registry.addShapedRecipe(id( "ultimate_hybrid_solar_helmet"), new ItemStack(Registry.ULTIMATE_HYBRID_SOLAR_HELMET),  "ppp", "pup", "ihi", 'p', IC2Items.CELL_PLASMA, 'u', Registry.ULTIMATE_HYBRID_SOLAR_PANEL, 'i', IC2Items.PLATE_IRIDIUM, 'h', Registry.HYBRID_SOLAR_HELMET);
        registry.addShapedRecipe(id( "molecular_transformer"), new ItemStack(Registry.MOLECULAR_TRANSFORMER), "AEA", "CMC", "AEA", 'A', IC2Blocks.ADVANCED_MACHINE_BLOCK, 'E', IC2Blocks.TRANSFORMER_EV, 'C', IC2Items.ADVANCED_CIRCUIT, 'M', Registry.MT_CORE);
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

    private static ResourceLocation id(String id){
        return new ResourceLocation(AdvancedSolarsClassic.MODID, id);
    }
}
