package mopsy.productions.nexo.interfaces;

public interface IItemRadiation {
    /**
     * Default value = 0
     * @return the amount of RAD per Second per Item
     **/
    float getRadiation();

    float getHeat();
    boolean hasHeat();
}
