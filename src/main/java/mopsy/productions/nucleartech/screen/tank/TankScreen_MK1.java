package mopsy.productions.nucleartech.screen.tank;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.TankEntity_MK1;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.util.DisplayUtils;
import mopsy.productions.nucleartech.util.IntCords2D;
import mopsy.productions.nucleartech.util.ScreenUtils;
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

import static mopsy.productions.nucleartech.Main.modid;

@SuppressWarnings("UnstableApiUsage")
public class TankScreen_MK1 extends HandledScreen<TankScreenHandler_MK1> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/tank.png");
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate;

    public TankScreen_MK1(TankScreenHandler_MK1 handler, PlayerInventory inventory, Text title) {
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

        renderFluidStorageTooltipPredicate = ScreenUtils.renderBigFluidStorage(this, matrices, x+25, y+11, getFluidAmount(), getCapacity(), getFluid());
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
        renderTooltip(matrices, DisplayUtils.getFluidTooltipText(getFluidAmountmB(),getCapacitymB(), getFluid(), exact), mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    private FluidVariant getFluid(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage) {
            return ((IFluidStorage) blockEntity).getFluidType();
        }
        return null;
    }
    private long getFluidAmount(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IFluidStorage){
            return ((IFluidStorage)blockEntity).getFluidAmount();
        }
        return 0;
    }
    private long getFluidAmountmB(){
        return getFluidAmount()/81;
    }
    private long getCapacity(){
        TankEntity_MK1 entity = (TankEntity_MK1) client.world.getBlockEntity(handler.getBlockPos());
        if(entity!= null){
            return entity.fluidStorage.getCapacity();
        }
        return 0;
    }

    private long getCapacitymB(){
        return getCapacity()/81;
    }
}
