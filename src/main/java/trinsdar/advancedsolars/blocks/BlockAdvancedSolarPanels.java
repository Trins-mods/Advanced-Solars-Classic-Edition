package trinsdar.advancedsolars.blocks;

import ic2.core.IC2;
import ic2.core.block.base.BlockMultiID;
import ic2.core.block.base.tile.TileEntityBlock;
import ic2.core.platform.lang.components.base.LocaleComp;
import ic2.core.platform.textures.Ic2Icons;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import trinsdar.advancedsolars.AdvancedSolarsClassic;
import trinsdar.advancedsolars.util.Registry;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BlockAdvancedSolarPanels extends BlockMultiID {
    public BlockAdvancedSolarPanels(String blockName, LocaleComp comp)
    {
        super(Material.IRON);
        this.setHardness(4.0F);
        this.setResistance(20.0F);
        this.setSoundType(SoundType.METAL);
        this.setCreativeTab(IC2.tabIC2);
        this.setRegistryName(blockName.toLowerCase());
        this.setUnlocalizedName(comp);
    }

    public List<Integer> getValidMetas() {
        return Arrays.asList(0);
    }

    @Override
    public TileEntityBlock createNewTileEntity(World worldIn, int meta)
    {
        if (this == Registry.advancedSolarPanel) {
            return new TileEntityAdvancedSolarPanel();
        }else if (this == Registry.hybridSolarPanel){
            return new TileEntityAdvancedSolarPanel.TileEntityHybridSolarPanel();
        }else if (this == Registry.ultimateHybridSolarPanel){
            return new TileEntityAdvancedSolarPanel.TileEntityUltimateHybridSolarPanel();
        }else {
            return new TileEntityBlock();
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public TextureAtlasSprite[] getIconSheet(int meta)
    {
        if (this == Registry.advancedSolarPanel){
            return Ic2Icons.getTextures("advancedsolarpanel");
        }else if (this == Registry.hybridSolarPanel){
            return Ic2Icons.getTextures("hybridsolarpanel");
        }else if (this == Registry.ultimateHybridSolarPanel){
            return Ic2Icons.getTextures("ultimatesolarpanel");
        }else{
            return Ic2Icons.getTextures("advancedsolarpanel");
        }
    }
    @Override
    public int getMaxSheetSize(int meta)
    {
        return 1;
    }

    @Override
    public List<IBlockState> getValidStateList()
    {
        IBlockState def = getDefaultState();
        List<IBlockState> states = new ArrayList<IBlockState>();
        for(EnumFacing side : EnumFacing.VALUES)
        {
            states.add(def.withProperty(getMetadataProperty(), 0).withProperty(allFacings, side).withProperty(active, false));
            states.add(def.withProperty(getMetadataProperty(), 0).withProperty(allFacings, side).withProperty(active, true));
        }
        return states;
    }

    @Override
    public List<IBlockState> getValidStates()
    {
        return getBlockState().getValidStates();
    }
}
