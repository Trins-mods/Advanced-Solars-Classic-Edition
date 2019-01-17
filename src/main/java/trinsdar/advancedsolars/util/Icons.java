package trinsdar.advancedsolars.util;

import ic2.core.platform.textures.Sprites;
import ic2.core.platform.textures.Sprites.SpriteData;
import ic2.core.platform.textures.Sprites.SpriteInfo;
import ic2.core.platform.textures.Sprites.TextureEntry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import static ic2.core.platform.textures.Ic2Icons.addSprite;
import static ic2.core.platform.textures.Ic2Icons.addTextureEntry;

public class Icons {
    @SideOnly(Side.CLIENT)
    public static void loadSprites()
    {
        addSprite(new SpriteData("advancedsolarpanel", "advancedsolars:textures/sprites/advancedsolarpanel.png", new SpriteInfo(2, 2)));
        addSprite(new SpriteData("hybridsolarpanel", "advancedsolars:textures/sprites/hybridsolarpanel.png", new SpriteInfo(1, 12)));
        addSprite(new SpriteData("ultimatesolarpanel", "advancedsolars:textures/sprites/ultimatesolarpanel.png", new SpriteInfo(1, 12)));
        addSprite(new SpriteData("advancedsolars_items", "advancedsolars:textures/sprites/adv_items.png", new SpriteInfo(16, 1)));
        addTextureEntry(new TextureEntry("advancedsolarpanel", 0, 0, 1, 12));
        addTextureEntry(new TextureEntry("hybridsolarpanel", 0, 0, 1, 12));
        addTextureEntry(new TextureEntry("ultimatesolarpanel", 0, 0, 1, 12));
        addTextureEntry(new TextureEntry("advancedsolars_items", 0, 0, 16, 1));
    }
}
