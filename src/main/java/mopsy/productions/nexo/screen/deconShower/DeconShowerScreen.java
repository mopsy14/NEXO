package mopsy.productions.nexo.screen.deconShower;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModBlocks.entities.deconShower.DeconShowerEntity;
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

import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class DeconShowerScreen extends HandledScreen<DeconShowerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/decon_shower.png");
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate;

    public DeconShowerScreen(DeconShowerScreenHandler handler, PlayerInventory inventory, Text title) {
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

        renderFluidStorageTooltipPredicate = ScreenUtils.renderSmallFluidStorage(this, matrices, x+8, y+19, getFluidAmount(), getMaxFluidAmount(), getFluidType());
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderFluidStorageTooltipPredicate.test(mouse)) {
            renderFluidTooltip(hasShiftDown(), matrices, mouse);
        }
    }
    private void renderFluidTooltip(boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        renderTooltip(matrices, DisplayUtils.getFluidTooltipText(getFluidAmount(),getCapacitymB(), getFluidType(), exact), mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    private FluidVariant getFluidType(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage) {
            return ((IFluidStorage) blockEntity).getFluidType();
        }
        return FluidVariant.blank();
    }
    private long getFluidAmount(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage) {
            return ((IFluidStorage) blockEntity).getFluidAmount();
        }
        return 0;
    }
    private long getMaxFluidAmount(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof DeconShowerEntity entity) {
            return entity.fluidStorage.getCapacity();
        }
        return 0;
    }
    private long getFluidAmountmB(){
        return FluidUtils.dropletsTomB(getFluidAmount());
    }
    private long getCapacitymB(){
        return FluidUtils.dropletsTomB(getMaxFluidAmount());
    }
}
