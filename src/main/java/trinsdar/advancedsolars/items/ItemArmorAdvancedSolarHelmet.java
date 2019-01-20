package trinsdar.advancedsolars.items;

import ic2.core.item.armor.electric.ItemArmorSolarHelmet;
import ic2.core.platform.lang.storage.Ic2ItemLang;
import ic2.core.platform.registry.Ic2Items;
import ic2.core.platform.textures.Ic2Icons;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.AdvancedSolarsClassic;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;

public class ItemArmorAdvancedSolarHelmet extends ItemArmorSolarHelmet {
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
        super();
        this.variant = variant;
        String name = variant.toString().toLowerCase() + "_solar_helmet";
        this.setRegistryName(name);
        this.setUnlocalizedName(AdvancedSolarsClassic.MODID + name);
        this.setMaxDamage(0);
    }

    @Override
    public void onLoad() {
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
    @SideOnly(Side.CLIENT)
    public TextureAtlasSprite getTexture(int meta) {
        return Ic2Icons.getTextures("i2")[32 + meta];
    }
}
