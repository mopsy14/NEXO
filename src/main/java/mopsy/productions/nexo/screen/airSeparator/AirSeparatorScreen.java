package mopsy.productions.nexo.screen.airSeparator;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModBlocks.entities.machines.AirSeparatorEntity;
import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.interfaces.IFluidStorage;
import mopsy.productions.nexo.util.DisplayUtils;
import mopsy.productions.nexo.util.FluidUtils;
import mopsy.productions.nexo.util.IntCords2D;
import mopsy.productions.nexo.util.ScreenUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;

public class AirSeparatorScreen extends HandledScreen<AirSeparatorScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/air_separator.png");
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate1;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate2;

    public AirSeparatorScreen(AirSeparatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
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

        renderProgress(matrices, x, y);
        renderFluidStorageTooltipPredicate1 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+43, y+19, getFluidAmount(0), getMaxFluidAmount(0), getFluidType(0));
        renderFluidStorageTooltipPredicate2 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+102, y+19, getFluidAmount(1), getMaxFluidAmount(1), getFluidType(1));
        renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, matrices, x+152, y+11, getPower(), AirSeparatorEntity.POWER_CAPACITY);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderEnergyTooltipPredicate.test(mouse))
            renderEnergyTooltip(hasShiftDown(), matrices, mouse);
        if (renderFluidStorageTooltipPredicate1.test(mouse)) {
            renderFluidTooltip(0, hasShiftDown(), matrices, mouse);
        }
        if (renderFluidStorageTooltipPredicate2.test(mouse)) {
            renderFluidTooltip(1, hasShiftDown(), matrices, mouse);
        }
    }

    private void renderFluidTooltip(int fluidIndex, boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        renderTooltip(matrices, DisplayUtils.getFluidTooltipText(getFluidAmount(fluidIndex),getCapacitymB(fluidIndex), getFluidType(fluidIndex), exact), mouseCords.x, mouseCords.y);
    }

    private void renderEnergyTooltip(boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(DisplayUtils.getEnergyBarText(getPower(), AirSeparatorEntity.POWER_CAPACITY, hasShiftDown())));
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        renderTooltip(matrices, text, mouseCords.x, mouseCords.y);
    }

    private void renderProgress(MatrixStack matrices, int x, int y){
        if(handler.isCrafting()){
            drawTexture(matrices, x+15, y+35, 176, 0, handler.getScaledProgress(), 12);
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
