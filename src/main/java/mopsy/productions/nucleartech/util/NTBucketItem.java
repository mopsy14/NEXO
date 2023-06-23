package mopsy.productions.nucleartech.util;

import mopsy.productions.nucleartech.interfaces.IBucketFluidStorage;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;

public class NTBucketItem extends BucketItem implements IBucketFluidStorage {
    Fluid fluid;
    boolean placeable;
    public NTBucketItem(Fluid fluid, boolean placeable) {
        super(fluid, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1));
        this.fluid = fluid;
        this.placeable = placeable;
    }

    @Override
    public Fluid getFluid() {
        return fluid;
    }

    @Override
    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
        if (placeable)
            super.placeFluid(player, world, pos, hitResult);
        return false;
    }
}
