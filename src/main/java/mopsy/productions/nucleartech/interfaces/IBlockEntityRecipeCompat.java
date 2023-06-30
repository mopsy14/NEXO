package mopsy.productions.nucleartech.interfaces;

import mopsy.productions.nucleartech.enums.SlotIO;

public interface IBlockEntityRecipeCompat {
    SlotIO[] getFluidSlotIOs();
    SlotIO[] getItemSlotIOs();
}
