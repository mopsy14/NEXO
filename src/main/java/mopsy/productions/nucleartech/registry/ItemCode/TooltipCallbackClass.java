package mopsy.productions.nucleartech.registry.ItemCode;

import mopsy.productions.nucleartech.interfaces.IItemRadiation;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

import java.util.List;

public class TooltipCallbackClass implements ItemTooltipCallback {
    @Override
    public void getTooltip(ItemStack stack, TooltipContext context, List<Text> lines) {
        if(stack.getItem() instanceof IItemRadiation){
            float radPI = ((IItemRadiation)stack.getItem()).getRadiation();
            lines.add(1, Text.of( "§4"+radPI*stack.getCount()+"RAD/s"));
        }
    }
}
