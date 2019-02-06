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

    private int id;

    public ItemMisc(String name, int id) {
        this.id = id;
        setUnlocalizedName(name);
        setCreativeTab(IC2.tabIC2);
    }

    @Override
    public List<Integer> getValidVariants() {
        return Arrays.asList(0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int i) {
        return Ic2Icons.getTextures("advancedsolars_items")[id];
    }
}
