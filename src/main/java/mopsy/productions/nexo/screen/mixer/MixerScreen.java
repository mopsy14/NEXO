package mopsy.productions.nexo.screen.mixer;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModBlocks.entities.machines.MixerEntity;
import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.interfaces.IFluidStorage;
import mopsy.productions.nexo.util.DisplayUtils;
import mopsy.productions.nexo.util.FluidUtils;
import mopsy.productions.nexo.util.IntCords2D;
import mopsy.productions.nexo.util.ScreenUtils;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;
import static mopsy.productions.nexo.networking.PacketManager.CHANGE_MIXER_SLIDER_PACKET;
import static mopsy.productions.nexo.networking.PacketManager.START_MIXER_PACKET;

@SuppressWarnings("UnstableApiUsage")
public class MixerScreen extends HandledScreen<MixerScreenHandler>{
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/mixer.png");
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate1;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate2;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate3;
    public Predicate<IntCords2D> buttonCordPredicate;
    /*public Predicate<IntCords2D> sliderCordPredicate =
            ic2d -> (
                ic2d.x>x+38+handler.getSliderPos() && ic2d.x<x+42+handler.getSliderPos() &&
                ic2d.y>y+72 && ic2d.y<y+78
            );

     */
    public Predicate<IntCords2D> sliderTooltipPredicate =
            ic2d -> (
                ic2d.x>x+45 && ic2d.x<x+113 &&
                ic2d.y>y+71 && ic2d.y<y+78
    );
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;
    private boolean isDragging = false;
    private int prevDrag = 0;

    public MixerScreen(MixerScreenHandler handler, PlayerInventory inventory, Text title) {
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

        renderFluidStorageTooltipPredicate1 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+5, y+19, getFluidAmount(0), getMaxFluidAmount(0), getFluidType(0));
        renderFluidStorageTooltipPredicate2 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+43, y+19, getFluidAmount(1), getMaxFluidAmount(1), getFluidType(1));
        renderFluidStorageTooltipPredicate3 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+81, y+19, getFluidAmount(2), getMaxFluidAmount(2), getFluidType(2));
        buttonCordPredicate = ScreenUtils.renderSmallButton(this, matrices, x+4, y+5, handler.isActive());
        renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, matrices, x+156, y+11, getPower(), MixerEntity.POWER_CAPACITY);
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x+46+handler.getSliderPos(), y+72, 224, 0, 4, 6);

        if(isDragging){
            if(prevDrag!=mouseX){
                prevDrag = mouseX;
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(handler.getBlockPos());
                buf.writeInt(handler.getHeatFromSliderPos(mouseX-48-x));
                ClientPlayNetworking.send(CHANGE_MIXER_SLIDER_PACKET, buf);
            }
        }
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        isDragging = false;
        if(button==0) {
            IntCords2D mouse = new IntCords2D(Math.toIntExact(Math.round(mouseX)),Math.toIntExact(Math.round(mouseY)));
            if(buttonCordPredicate.test(mouse)){
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(handler.getBlockPos());
                ClientPlayNetworking.send(START_MIXER_PACKET, buf);
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        isDragging = sliderTooltipPredicate.test(new IntCords2D(Math.toIntExact(Math.round(mouseX)),Math.toIntExact(Math.round(mouseY))));
        return super.mouseClicked(mouseX, mouseY, button);
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
        if (renderFluidStorageTooltipPredicate3.test(mouse)) {
            renderFluidTooltip(2, hasShiftDown(), matrices, mouse);
        }
        if (sliderTooltipPredicate.test(mouse)){
            renderTooltip(matrices, Text.of("Heat: "+handler.getHeat()+"°C"), mouse.x, mouse.y);
        }
    }
    private void renderEnergyTooltip(boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(DisplayUtils.getEnergyBarText(getPower(), MixerEntity.POWER_CAPACITY, hasShiftDown())));
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        renderTooltip(matrices, text, mouseCords.x, mouseCords.y);
    }

    private void renderFluidTooltip(int fluidIndex, boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        renderTooltip(matrices, DisplayUtils.getFluidTooltipText(getFluidAmount(fluidIndex),getCapacitymB(fluidIndex), getFluidType(fluidIndex), exact), mouseCords.x, mouseCords.y);
    }
    private void renderProgress(MatrixStack matrices, int x, int y){
        if(handler.isActive()){
            drawTexture(matrices, x+118, y+56, 176, 0, handler.getScaledProgress(), 11);
        }
    }
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
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
    private long getPower(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IEnergyStorage) {
            return ((IEnergyStorage) blockEntity).getPower();
        }
        return 0;
    }
}
