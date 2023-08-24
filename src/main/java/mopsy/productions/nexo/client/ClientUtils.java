package mopsy.productions.nexo.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.impl.FabricLoaderImpl;

public class ClientUtils {
    public static final boolean isClient = FabricLoaderImpl.INSTANCE.getEnvironmentType() == EnvType.CLIENT;
}
