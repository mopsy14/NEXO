package mopsy.productions.nucleartech.ModItems;

import mopsy.productions.nucleartech.interfaces.IItemRadiation;

public class NTRadiatingItem extends NTItem implements IItemRadiation {

    public float radiation;

    public NTRadiatingItem(String ID, float radiation) {
        super(ID);
        this.radiation = radiation;
    }
    public NTRadiatingItem(Settings settings, String ID, float radiation) {
        super(settings, ID);
        this.radiation = radiation;
    }

    @Override
    public float getRadiation() {
        return radiation;
    }

    @Override
    public float getHeat() {
        return 0;
    }

    @Override
    public boolean hasHeat() {
        return false;
    }
}
