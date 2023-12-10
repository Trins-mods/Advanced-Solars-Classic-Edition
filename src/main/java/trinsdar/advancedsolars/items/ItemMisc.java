package trinsdar.advancedsolars.items;

import ic2.core.IC2;
import ic2.core.platform.registries.IC2Items;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.item.ISimpleItemModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

public class ItemMisc extends Item implements ISimpleItemModel {

    final ResourceLocation id;

    public ItemMisc(String name) {
        super(new Item.Properties().tab(IC2.IC2_MAIN_GROUP));
        id = new ResourceLocation(AdvancedSolarsClassic.MODID, name);
        IC2Items.registerItem(this, id);
    }

    @Override
    public TextureAtlasSprite getTexture() {
        return IC2Textures.getMappedEntriesItem(AdvancedSolarsClassic.MODID, "materials").get(id.getPath());
    }

    @Override
    public ResourceLocation getRegistryName() {
        return id;
    }
}
