package mopsy.productions.nucleartech.registry;

import mopsy.productions.nucleartech.ModFluids.*;
import mopsy.productions.nucleartech.ModFluids.steam.DenseSteam;
import mopsy.productions.nucleartech.ModFluids.steam.Steam;
import mopsy.productions.nucleartech.ModFluids.steam.SuperDenseSteam;
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
    public static FlowableFluid OXYGEN;
    public static Block OXYGEN_BLOCK;
    public static Item OXYGEN_BUCKET;
    public static FlowableFluid HYDROGEN;
    public static Block HYDROGEN_BLOCK;
    public static Item HYDROGEN_BUCKET;
    public static FlowableFluid SUPER_DENSE_STEAM;
    public static Block SUPER_DENSE_STEAM_BLOCK;
    public static Item SUPER_DENSE_STEAM_BUCKET;
    public static FlowableFluid DENSE_STEAM;
    public static Block DENSE_STEAM_BLOCK;
    public static Item DENSE_STEAM_BUCKET;
    public static FlowableFluid STEAM;
    public static Block STEAM_BLOCK;
    public static Item STEAM_BUCKET;
    public static FlowableFluid AMMONIA;
    public static Block AMMONIA_BLOCK;
    public static Item AMMONIA_BUCKET;
    public static FlowableFluid FLUORINE;
    public static Block FLUORINE_BLOCK;
    public static Item FLUORINE_BUCKET;
    public static FlowableFluid HYDROGEN_FLUORIDE;
    public static Block HYDROGEN_FLUORIDE_BLOCK;
    public static Item HYDROGEN_FLUORIDE_BUCKET;
    public static FlowableFluid SULFUR_DIOXIDE;
    public static Block SULFUR_DIOXIDE_BLOCK;
    public static Item SULFUR_DIOXIDE_BUCKET;
    public static FlowableFluid SULFURIC_ACID;
    public static Block SULFURIC_ACID_BLOCK;
    public static Item SULFURIC_ACID_BUCKET;
    public static FlowableFluid SULFUR_TRIOXIDE;
    public static Block SULFUR_TRIOXIDE_BLOCK;
    public static Item SULFUR_TRIOXIDE_BUCKET;
    public static FlowableFluid URANIUM_HEXAFLUORIDE;
    public static Block URANIUM_HEXAFLUORIDE_BLOCK;
    public static Item URANIUM_HEXAFLUORIDE_BUCKET;

    public static void regFluids(){
        NITROGEN = regFluid(new Nitrogen.Still());
        OXYGEN = regFluid(new Oxygen.Still());
        HYDROGEN = regFluid(new Hydrogen.Still());
        SUPER_DENSE_STEAM = regFluid(new SuperDenseSteam.Still());
        DENSE_STEAM = regFluid(new DenseSteam.Still());
        STEAM = regFluid(new Steam.Still());
        AMMONIA = regFluid(new Ammonia.Still());
        FLUORINE = regFluid(new Fluorine.Still());
        HYDROGEN_FLUORIDE = regFluid(new HydrogenFluoride.Still());
        SULFUR_DIOXIDE = regFluid(new Sulfurdioxide.Still());
        SULFURIC_ACID = regFluid(new SulfuricAcid.Still());
        SULFUR_TRIOXIDE = regFluid(new Sulfurtrioxide.Still());
        URANIUM_HEXAFLUORIDE = regFluid(new Uraniumhexafluoride.Still());
        NITROGEN_BLOCK = regBlock(NITROGEN);
        OXYGEN_BLOCK = regBlock(OXYGEN);
        HYDROGEN_BLOCK = regBlock(HYDROGEN);
        SUPER_DENSE_STEAM_BLOCK = regBlock(SUPER_DENSE_STEAM);
        DENSE_STEAM_BLOCK = regBlock(DENSE_STEAM);
        STEAM_BLOCK = regBlock(STEAM);
        AMMONIA_BLOCK = regBlock(AMMONIA);
        FLUORINE_BLOCK = regBlock(FLUORINE);
        HYDROGEN_FLUORIDE_BLOCK = regBlock(HYDROGEN_FLUORIDE);
        SULFUR_DIOXIDE_BLOCK = regBlock(SULFUR_DIOXIDE);
        SULFURIC_ACID_BLOCK = regBlock(SULFURIC_ACID);
        SULFUR_TRIOXIDE_BLOCK = regBlock(SULFUR_TRIOXIDE);
        URANIUM_HEXAFLUORIDE_BLOCK = regBlock(URANIUM_HEXAFLUORIDE);
        NITROGEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "nitrogen_bucket"),
                new BucketItem(ModdedFluids.NITROGEN, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        OXYGEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "oxygen_bucket"),
                new BucketItem(ModdedFluids.OXYGEN, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        HYDROGEN_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "hydrogen_bucket"),
                new BucketItem(ModdedFluids.HYDROGEN, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        SUPER_DENSE_STEAM_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "super_dense_steam_bucket"),
                new BucketItem(ModdedFluids.SUPER_DENSE_STEAM, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        DENSE_STEAM_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "dense_steam_bucket"),
                new BucketItem(ModdedFluids.DENSE_STEAM, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        STEAM_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "steam_bucket"),
                new BucketItem(ModdedFluids.STEAM, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        AMMONIA_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "ammonia_bucket"),
                new BucketItem(ModdedFluids.AMMONIA, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        FLUORINE_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "fluorine_bucket"),
                new BucketItem(ModdedFluids.FLUORINE, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        HYDROGEN_FLUORIDE_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "hydrogen_fluoride_bucket"),
                new BucketItem(ModdedFluids.HYDROGEN_FLUORIDE, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        SULFUR_DIOXIDE_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "sulfur_dioxide_bucket"),
                new BucketItem(ModdedFluids.SULFUR_DIOXIDE, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        SULFURIC_ACID_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "sulfuric_acid_bucket"),
                new BucketItem(ModdedFluids.SULFURIC_ACID, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        SULFUR_TRIOXIDE_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "sulfur_trioxide_bucket"),
                new BucketItem(ModdedFluids.SULFUR_TRIOXIDE, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
                    @Override
                    public boolean placeFluid(@Nullable PlayerEntity player, World world, BlockPos pos, @Nullable BlockHitResult hitResult) {
                        return false;
                    }
                });
        URANIUM_HEXAFLUORIDE_BUCKET = Registry.register(Registry.ITEM, new Identifier(modid, "uranium_hexafluoride_bucket"),
                new BucketItem(ModdedFluids.URANIUM_HEXAFLUORIDE, new FabricItemSettings().group(CREATIVE_TAB).recipeRemainder(Items.BUCKET).maxCount(1)){
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
