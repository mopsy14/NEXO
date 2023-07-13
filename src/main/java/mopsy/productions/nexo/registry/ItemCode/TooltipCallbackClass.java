package mopsy.productions.nexo.registry.ItemCode;

import mopsy.productions.nexo.interfaces.IArmorRadiationProtection;
import mopsy.productions.nexo.interfaces.IItemRadiation;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.util.List;

public class TooltipCallbackClass implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if(stack.getItem() instanceof IItemRadiation){
            float radPI = ((IItemRadiation)stack.getItem()).getRadiation();
            lines.add(1, Text.of( Formatting.DARK_RED.toString()+radPI*stack.getCount()+"RAD/s"));
        }
        if(stack.getItem() instanceof IArmorRadiationProtection){
            float modifier = ((IArmorRadiationProtection)stack.getItem()).getRadiationProtection();
            lines.add(1, Text.of(Formatting.YELLOW+"Radiation Modifier: "+modifier));
        }
    }
}
