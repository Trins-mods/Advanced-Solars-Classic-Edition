package trinsdar.advancedsolars.rei;

import com.mojang.blaze3d.systems.RenderSystem;
import ic2.api.recipes.registries.IElectrolyzerRecipeList;
import ic2.core.inventory.container.ContainerComponent;
import ic2.core.inventory.gui.ComponentContainerScreen;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ClickArea;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import me.shedaniel.rei.forge.REIPluginClient;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.blocks.BlockEntityMolecularTransformer;
import trinsdar.advancedsolars.util.AdvancedSolarsRecipes;
import trinsdar.advancedsolars.util.Registry;

import java.util.Collections;
import java.util.List;

@REIPluginClient
public class AdvancedSolarsREIPlugin implements REIClientPlugin {

    public static final CategoryIdentifier<TransformerDisplay> TRANSFORMER = CategoryIdentifier.of(new ResourceLocation(AdvancedSolarsClassic.MODID, "mol_transformer"));

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(TransformerCategory.INSTANCE);
        registry.addWorkstations(TRANSFORMER, EntryStacks.of(Registry.MOLECULAR_TRANSFORMER.asItem()));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        AdvancedSolarsRecipes.MOLECULAR_TRANSFORMER.getRecipes().forEach(recipe -> registry.add(new TransformerDisplay(recipe)));
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(ComponentContainerScreen.class, new MolecularTransformerGUIHandler());
    }

    public static class TransformerCategory implements DisplayCategory<TransformerDisplay> {

        public static final TransformerCategory INSTANCE = new TransformerCategory();

        @Override
        public CategoryIdentifier<? extends TransformerDisplay> getCategoryIdentifier() {
            return TRANSFORMER;
        }

        @Override
        public List<Widget> setupDisplay(TransformerDisplay display, Rectangle bounds) {
            List<Widget> widgets = new ObjectArrayList<>();
            int minX = bounds.getMinX();
            int minY = bounds.getMinY();
            int centerY = bounds.getCenterY() - Minecraft.getInstance().font.lineHeight / 2;
            ResourceLocation texture = new ResourceLocation(AdvancedSolarsClassic.MODID, "textures/gui/transformer_recipe.png");
            ItemStack input = display.recipe().getInput();
            ItemStack output = display.recipe().getOutput();
            widgets.add(Widgets.createTexturedWidget(texture, bounds.getMinX(), bounds.getMinY(), 0, 0, 176, 88));
            widgets.add(createProgressBar(minX + 20, minY + 40, texture));


            widgets.add(Widgets.createSlot(new Point(minX + 17, minY + 19)).entries(EntryIngredients.of(input)).markInput().disableBackground());
            widgets.add(Widgets.createSlot(new Point(minX + 17, minY + 54)).entries(EntryIngredients.of(output)).markOutput().disableBackground());

            String inputName = input.getDisplayName().getString().replaceAll("\\[", "").replaceAll("]", "");
            String outputName = output.getDisplayName().getString().replaceAll("\\[", "").replaceAll("]", "");

            widgets.add(Widgets.createLabel(new Point(minX + 50, centerY - 10), Component.translatable("item_info.advanced_solars.molecular.input", inputName)).shadow(false).leftAligned());
            widgets.add(Widgets.createLabel(new Point(minX + 50, centerY), Component.translatable("item_info.advanced_solars.molecular.output", outputName)).shadow(false).leftAligned());
            widgets.add(Widgets.createLabel(new Point(minX + 50, centerY + 10), Component.translatable("item_info.advanced_solars.molecular.eu", display.recipe().getEnergy())).shadow(false).leftAligned());
            return widgets;
        }

        @Override
        public Component getTitle() {
            return Registry.MOLECULAR_TRANSFORMER.getName();
        }

        @Override
        public Renderer getIcon() {
            return EntryStacks.of(Registry.MOLECULAR_TRANSFORMER.asItem());
        }

        @Override
        public int getDisplayWidth(TransformerDisplay display) {
            return 176;
        }

        @Override
        public int getDisplayHeight() {
            return 88;
        }

        private static Widget createProgressBar(int x, int y, ResourceLocation texture) {
            return Widgets.createDrawableWidget((helper, matrices, mouseX, mouseY, delta) -> {
                RenderSystem.setShaderTexture(0, texture);
                helper.blit(matrices, x, y, 20, 40, 10, 9);
                double i = ((double) System.currentTimeMillis() / 2000) % 1.0 * 10;
                if (i < 0) {
                    i = 0;
                }
                helper.blit(matrices, x, y, 177, 3, 10, (int) i);
            });
        }
    }

    public static class TransformerDisplay extends BasicDisplay {
        IElectrolyzerRecipeList.ElectrolyzerRecipe RECIPE;

        public TransformerDisplay(IElectrolyzerRecipeList.ElectrolyzerRecipe recipe) {
            this(Collections.singletonList(EntryIngredients.of(recipe.getInput())),
                    Collections.singletonList(EntryIngredients.of(recipe.getOutput())));
            this.RECIPE = recipe;
        }

        public TransformerDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
            super(inputs, outputs);
        }

        public IElectrolyzerRecipeList.ElectrolyzerRecipe recipe() {
            return this.RECIPE;
        }

        @Override
        public CategoryIdentifier<?> getCategoryIdentifier() {
            return TRANSFORMER;
        }
    }

    public static class MolecularTransformerGUIHandler implements ClickArea<ComponentContainerScreen> {

        @Override
        public Result handle(ClickAreaContext<ComponentContainerScreen> clickAreaContext) {
            ComponentContainerScreen container = clickAreaContext.getScreen();
            Point mousePoint = clickAreaContext.getMousePosition();
            ContainerComponent<?> comp = (ContainerComponent<?>)container.getCastedContainer(ContainerComponent.class);
            if (comp.getHolder() instanceof BlockEntityMolecularTransformer) {
                Rectangle recipeBox = new Rectangle(container.getGuiLeft() + 29, container.getGuiTop() + 29, 10, 26);
                if (recipeBox.contains(mousePoint)) {
                    return ClickArea.Result.success().category(TRANSFORMER);
                }
            }
            return ClickArea.Result.success();
        }
    }
}
