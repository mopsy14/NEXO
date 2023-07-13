package mopsy.productions.nexo.ModItems;

import mopsy.productions.nexo.interfaces.IItemRadiation;

public class NTRadiatingItem extends NTItem implements IItemRadiation {

    public float radiation;
    public boolean hasHeat = false;
    public float heat = 0;

    public NTRadiatingItem(String ID, float radiation, float heat) {
        super(ID);
        this.radiation = radiation;
        this.hasHeat = true;
        this.heat = heat;
    }
    public NTRadiatingItem(String ID, float radiation) {
        super(ID);
        this.radiation = radiation;
    }
    public NTRadiatingItem(Settings settings, String ID, float radiation) {
        super(settings, ID);
        this.radiation = radiation;
    }
    public NTRadiatingItem(Settings settings, String ID, float radiation, float heat) {
        super(settings, ID);
        this.radiation = radiation;
        this.hasHeat = true;
        this.heat = heat;
    }

    @Override
    public float getRadiation() {
        return radiation;
    }

    @Override
    public float getHeat() {
        return heat;
    }

    @Override
    public boolean hasHeat() {
        return hasHeat;
    }
}
