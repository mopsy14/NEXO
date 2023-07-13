package mopsy.productions.nexo.interfaces;

import mopsy.productions.nexo.enums.SlotIO;

public interface IBlockEntityRecipeCompat {
    SlotIO[] getFluidSlotIOs();
    SlotIO[] getItemSlotIOs();
}
