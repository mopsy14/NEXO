package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModFluids.Nitrogen;
import mopsy.productions.nucleartech.interfaces.IModID;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static mopsy.productions.nucleartech.Main.CREATIVE_TAB;
import static mopsy.productions.nucleartech.Main.modid;

public class ModdedFluids {
    public static FlowableFluid NITROGEN;
    public static Block NITROGEN_BLOCK;
    public static Item NITROGEN_BUCKET;

    public static void regFluids(){
        NITROGEN = regFluid(new Nitrogen.Still());
        NITROGEN_BLOCK = regBlock(NITROGEN);
        NITROGEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "nitrogen"),
                new BucketItem(ModdedFluids.NITROGEN, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
    }
    private static FlowableFluid regFluid(FlowableFluid fluid){
        return Registry.register(Registry.FLUID, new Identifier(modid,((IModID)fluid).getID()), fluid);
    }
    private static Block regBlock(FlowableFluid fluid){
        return Registry.register(Registry.BLOCK, new Identifier(modid,((IModID)fluid).getID()), new FluidBlock(fluid, FabricBlockSettings.copyOf(Blocks.WATER)){});
    }
}
