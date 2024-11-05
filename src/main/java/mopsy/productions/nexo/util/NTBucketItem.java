package mopsy.productions.nexo.util;

import mopsy.productions.nexo.interfaces.IBucketFluidStorage;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Items;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nexo.Main.CREATIVE_TAB_KEY;

public class NTBucketItem extends BucketItem implements IBucketFluidStorage {
    Fluid fluid;
    boolean placeable;
    public NTBucketItem(Fluid fluid, boolean placeable) {
        super(fluid, new Settings().recipeRemainder(Items.BUCKET).maxCount(1));
        ItemGroupEvents.modifyEntriesEvent(CREATIVE_TAB_KEY).register(entries -> entries.add(this));
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
