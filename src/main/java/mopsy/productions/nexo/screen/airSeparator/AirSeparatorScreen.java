package mopsy.productions.nexo.screen.airSeparator;

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
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;

public class AirSeparatorScreen extends HandledScreen<AirSeparatorScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of(modid, "textures/gui/air_separator.png");
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate1;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate2;

    public AirSeparatorScreen(AirSeparatorScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        backgroundHeight = 191;
        //playerInventoryTitleY = backgroundHeight - 94;
    }
    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040, false);
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

        context.drawTexture(RenderLayer::getGuiTextured,TEXTURE,x, y,0,0, backgroundWidth, backgroundHeight,256,256);

        renderProgress(context, x, y);
        renderFluidStorageTooltipPredicate1 = ScreenUtils.renderSmallFluidStorage(this, context, x+43, y+53, getFluidAmount(0), getMaxFluidAmount(0), getFluidType(0));
        renderFluidStorageTooltipPredicate2 = ScreenUtils.renderSmallFluidStorage(this, context, x+102, y+53, getFluidAmount(1), getMaxFluidAmount(1), getFluidType(1));
        renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, context, x+152, y+38, getPower(), AirSeparatorEntity.POWER_CAPACITY);

        context.drawText(textRenderer, "Connected Coolers: "+handler.getCoolerAmount(), x+36, y+24, 16777215,true);
        context.drawText(textRenderer, "Connected Pumps: "+handler.getPumpAmount(), x+36, y+34, 16777215,true);
    }

    @Override
    protected void drawMouseoverTooltip(DrawContext context, int x, int y) {
        super.drawMouseoverTooltip(context, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderEnergyTooltipPredicate.test(mouse))
            renderEnergyTooltip(hasShiftDown(), context, mouse);
        if (renderFluidStorageTooltipPredicate1.test(mouse)) {
            renderFluidTooltip(0, hasShiftDown(), context, mouse);
        }
        if (renderFluidStorageTooltipPredicate2.test(mouse)) {
            renderFluidTooltip(1, hasShiftDown(), context, mouse);
        }
    }

    private void renderFluidTooltip(int fluidIndex, boolean exact, DrawContext context, IntCords2D mouseCords){
        context.drawTooltip(textRenderer, DisplayUtils.getFluidTooltipText(getFluidAmount(fluidIndex),getCapacitymB(fluidIndex), getFluidType(fluidIndex), exact), mouseCords.x, mouseCords.y);
    }

    private void renderEnergyTooltip(boolean exact, DrawContext context, IntCords2D mouseCords){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(DisplayUtils.getEnergyBarText(getPower(), AirSeparatorEntity.POWER_CAPACITY, hasShiftDown())));
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        context.drawTooltip(textRenderer, text, mouseCords.x, mouseCords.y);
    }

    private void renderProgress(DrawContext context, int x, int y){
        if(handler.isCrafting()){
            context.drawTexture(RenderLayer::getGuiTextured, TEXTURE, x+15, y+69, 176, 0, handler.getScaledProgress(), 12, 256, 256);
        }
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context,mouseX,mouseY,delta);
        super.render(context, mouseX, mouseY, delta);
        drawMouseoverTooltip(context, mouseX, mouseY);
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
