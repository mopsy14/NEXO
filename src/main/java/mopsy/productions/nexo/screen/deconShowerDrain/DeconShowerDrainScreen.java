package mopsy.productions.nexo.screen.deconShowerDrain;

import mopsy.productions.nexo.ModBlocks.entities.deconShower.DeconShowerDrainEntity;
import mopsy.productions.nexo.interfaces.IFluidStorage;
import mopsy.productions.nexo.util.DisplayUtils;
import mopsy.productions.nexo.util.FluidUtils;
import mopsy.productions.nexo.util.IntCords2D;
import mopsy.productions.nexo.util.ScreenUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;


public class DeconShowerDrainScreen extends HandledScreen<DeconShowerDrainScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(modid, "textures/gui/decon_shower_drain.png");
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate;

    public DeconShowerDrainScreen(DeconShowerDrainScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
    }
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(textRenderer, this.title, this.titleX, this.titleY, 0x404040,false);
    }
    @Override
    protected void init() {
        super.init();
        titleX = (backgroundWidth-textRenderer.getWidth(title))/2;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (width - backgroundWidth)/2;
        int y = (height - backgroundHeight)/2;
        context.drawTexture(RenderLayer::getGuiTextured,TEXTURE, x, y, 0, 0, backgroundWidth, backgroundHeight,256,256);

        renderFluidStorageTooltipPredicate = ScreenUtils.renderSmallFluidStorage(this, context, x+67, y+17, getFluidAmount(), getMaxFluidAmount(), getFluidType());
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderFluidStorageTooltipPredicate.test(mouse)) {
            renderFluidTooltip(hasShiftDown(), context, mouse);
        }
    }
    private void renderFluidTooltip(boolean exact, DrawContext context, IntCords2D mouseCords){
        context.drawTooltip(textRenderer, DisplayUtils.getFluidTooltipText(getFluidAmount(),getCapacitymB(), getFluidType(), exact), mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
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
        if(blockEntity instanceof DeconShowerDrainEntity entity) {
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
