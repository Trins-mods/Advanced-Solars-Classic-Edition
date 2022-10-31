package trinsdar.advancedsolars.blocks;

import ic2.core.block.base.drops.IBlockDropProvider;
import ic2.core.block.generators.BaseGeneratorBlock;
import ic2.core.platform.registries.IC2Blocks;
import ic2.core.platform.rendering.IC2Textures;
import ic2.core.platform.rendering.features.ITextureProvider;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.Registry;

import java.util.Map;


public class BlockAdvancedSolarPanels extends BaseGeneratorBlock {
    public BlockAdvancedSolarPanels(String blockName, BlockEntityType<? extends BlockEntity> type) {
        super(blockName, IBlockDropProvider.SELF, new SolarTextureProvider(AdvancedSolarsClassic.MODID, blockName), type);
        IC2Blocks.registerBlock(this);
    }

    public static class SolarTextureProvider implements ITextureProvider {

        String path;
        String mod;

        public SolarTextureProvider(String mod, String path) {
            this.mod = mod;
            this.path = path;
        }

        @OnlyIn(Dist.CLIENT)
        @Override
        public TextureAtlasSprite getTexture(BlockState blockState, Direction direction) {
            Map<String, TextureAtlasSprite> map = IC2Textures.getMappedEntriesBlock(this.mod, this.path);
            return switch (direction){
                case UP -> map.get("top");
                case DOWN -> map.get("bottom");
                default -> map.get("side");
            };
        }
    }
}
