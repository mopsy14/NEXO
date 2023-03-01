package mopsy.productions.nucleartech.screen.centrifuge;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.CentrifugeEntity;
import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.util.DisplayUtils;
import mopsy.productions.nucleartech.util.FluidUtils;
import mopsy.productions.nucleartech.util.IntCords2D;
import mopsy.productions.nucleartech.util.ScreenUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.Fluids;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nucleartech.Main.modid;

public class CentrifugeScreen extends HandledScreen<CentrifugeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/centrifuge.png");
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate1;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate2;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate3;

    public CentrifugeScreen(CentrifugeScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }

    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth-textRenderer.getWidth(title))/2;
    }

    @Override
    protected void drawBackground(MatrixStack matrices, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - backgroundWidth)/2;
        int y = (height - backgroundHeight)/2;
        drawTexture(matrices, x, y, 0, 0, backgroundWidth, backgroundHeight);

        renderFluidStorageTooltipPredicate1 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+8, y+19, getFluidAmount(0), getMaxFluidAmount(0), getFluidType(0));
        renderFluidStorageTooltipPredicate2 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+58, y+19, getFluidAmount(1), getMaxFluidAmount(1), getFluidType(1));
        renderFluidStorageTooltipPredicate3 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+108, y+19, getFluidAmount(2), getMaxFluidAmount(2), getFluidType(2));
        renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, matrices, x+152, y+11, getPower(), CentrifugeEntity.POWER_CAPACITY);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderEnergyTooltipPredicate.test(mouse))
            renderTooltip(matrices, Text.of(DisplayUtils.getEnergyBarText(getPower(), CentrifugeEntity.POWER_CAPACITY)), x, y);
        if (renderFluidStorageTooltipPredicate1.test(mouse)) {
            renderFluidTooltip(0, matrices, mouse);
        }
        if (renderFluidStorageTooltipPredicate2.test(mouse)) {
            renderFluidTooltip(1, matrices, mouse);
        }
        if (renderFluidStorageTooltipPredicate3.test(mouse)) {
            renderFluidTooltip(2, matrices, mouse);
        }

    }

    private void renderFluidTooltip(int fluidIndex, MatrixStack matrices, IntCords2D mouseCords){
        if (getFluidType(fluidIndex).getFluid() != Fluids.EMPTY && getFluidAmount(fluidIndex)>0) {
            List<Text> text = new ArrayList<>();
            text.add(Text.translatable(getFluidType(fluidIndex).getFluid().getDefaultState().getBlockState().getBlock().getTranslationKey()));
            text.add(Text.of(DisplayUtils.getFluidBarText(getFluidAmountmB(fluidIndex), getCapacitymB(fluidIndex))));
            renderTooltip(matrices, text, mouseCords.x, mouseCords.y);
        } else {
            Text text = Text.of(Formatting.GOLD + "0mB/" + getCapacitymB(fluidIndex) + "mB");
            renderTooltip(matrices, text, mouseCords.x, mouseCords.y);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    private long getPower(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IEnergyStorage) {
            return ((IEnergyStorage) blockEntity).getPower();
        }
        return 0;
    }
    private FluidVariant getFluidType(int index){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage) {
            return ((IFluidStorage) blockEntity).getFluidStorages().get(index).variant;
        }
        return FluidVariant.blank();
    }
    private long getFluidAmount(int index){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage) {
            return ((IFluidStorage) blockEntity).getFluidStorages().get(index).amount;
        }
        return 0;
    }
    private long getMaxFluidAmount(int index){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage) {
            return ((IFluidStorage) blockEntity).getFluidStorages().get(index).getCapacity();
        }
        return 0;
    }
    private long getFluidAmountmB(int index){
        return FluidUtils.dropletsTomB(getFluidAmount(index));
    }
    private long getCapacitymB(int index){
        return FluidUtils.dropletsTomB(getMaxFluidAmount(index));
    }
}