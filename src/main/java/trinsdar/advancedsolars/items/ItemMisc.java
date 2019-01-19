package trinsdar.advancedsolars.items;

import ic2.core.IC2;
import ic2.core.platform.textures.Ic2Icons;
import ic2.core.platform.textures.obj.IStaticTexturedItem;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.item.Item;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

import java.util.Arrays;
import java.util.List;

public class ItemMisc extends Item implements IStaticTexturedItem {
    public enum ItemMiscTypes {
        SUNNARIUM(0),
        SUNNARIUM_ALLOY(1),
        IRRADIANT_URANIUM(2),
        ENRICHED_SUNNARIUM(3),
        ENRICHED_SUNNARIUM_ALLOY(4),
        IRRADIANT_GLASS_PANE(5),
        IRIDIUM_IRON_PLATE(6),
        REINFORCED_IRIDIUM_IRON_PLATE(7),
        IRRADIANT_REINFORCED_PLATE(8),
        SUNNARIUM_PART(9),
        IRIDIUM_INGOT(10);

        private int id;

        ItemMiscTypes(int id) {
            this.id = id;
        }

        public int getID() {
            return id;
        }
    }

    ItemMiscTypes variant;

    public ItemMisc(ItemMiscTypes variant) {
        this.variant = variant;
        setRegistryName(variant.toString().toLowerCase());
        setUnlocalizedName(AdvancedSolarsClassic.MODID + "." + variant.toString().toLowerCase());
        setCreativeTab(IC2.tabIC2);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("advancedsolars_items")[variant.getID()];
    }
}
