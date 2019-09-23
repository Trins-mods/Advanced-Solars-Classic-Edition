package trinsdar.advancedsolars.blocks;

import ic2.core.IC2;
import ic2.core.item.block.ItemBlockRare;
import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import trinsdar.advancedsolars.util.AdvancedSolarLang;
import trinsdar.advancedsolars.util.Config;
import trinsdar.advancedsolars.util.Registry;

import java.util.List;

public class ItemBlockAdvancedSolarPanel extends ItemBlockRare {
    public ItemBlockAdvancedSolarPanel(Block block) {
        super(block);
    }

    @Override
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
        if (handler.hasEUReader()) {
            tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(this.getProduction()));
            tooltip.add(AdvancedSolarLang.electricLowerProduction.getLocalizedFormatted(this.getLowerProduction()));
            tooltip.add(AdvancedSolarLang.solarPanelTier.getLocalizedFormatted(this.getTier()));
        }

    }

    private String getTier() {
        if (this.getBlock() == Registry.advancedSolarPanel){

            return getTierFromOutput(Config.energyGeneratorSolarAdvanced * 16.0F);
        }else if (this.getBlock() == Registry.hybridSolarPanel){
            return getTierFromOutput(Config.energyGeneratorSolarHybrid * 128.0F);
        }else if (this.getBlock() == Registry.ultimateHybridSolarPanel){
            return getTierFromOutput(Config.energyGeneratorSolarUltimateHybrid * 1024.0F);
        }else {
            return "";
        }
    }

    public String getTierFromOutput(float output){
        if (output <= 32){
            return "LV";
        } else if (output <= 128){
            return "MV";
        }else if (output <= 512){
            return "HV";
        }else if (output <= 2048){
            return "EV";
        } else if (output <= 8192){
            return "IV";
        } else if (output <= 32768){
            return "LuV";
        } else{
            return "UV";
        }
    }


    private double getProduction() {
        if (this.getBlock() == Registry.advancedSolarPanel){
            return (double) Config.energyGeneratorSolarAdvanced * 16.0F;
        }else if (this.getBlock() == Registry.hybridSolarPanel){
            return (double) Config.energyGeneratorSolarHybrid * 128.0F;
        }else if (this.getBlock() == Registry.ultimateHybridSolarPanel){
            return (double) Config.energyGeneratorSolarUltimateHybrid * 1024.0F;
        }else {
            return 0.0D;
        }
    }

    private double getLowerProduction() {
        if (this.getBlock() == Registry.advancedSolarPanel){
            return (double) Config.energyGeneratorSolarAdvanced * 2.0F;
        }else if (this.getBlock() == Registry.hybridSolarPanel){
            return (double) Config.energyGeneratorSolarHybrid * 16.0F;
        }else if (this.getBlock() == Registry.ultimateHybridSolarPanel){
            return (double) Config.energyGeneratorSolarUltimateHybrid * 128.0F;
        }else {
            return 0.0D;
        }
    }
}
