package mopsy.productions.nexo.screen.smallReactor;

import com.mojang.blaze3d.systems.RenderSystem;
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

import java.util.List;
import java.util.function.Predicate;

import static mopsy.productions.nexo.Main.modid;
import static mopsy.productions.nexo.networking.PacketManager.SWITCH_REACTOR_POWER_PACKET;

@SuppressWarnings("UnstableApiUsage")
public class SmallReactorScreen extends HandledScreen<SmallReactorScreenHandler>{
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/small_reactor.png");
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate1;
    public Predicate<IntCords2D> renderFluidStorageTooltipPredicate2;
    public Predicate<IntCords2D> buttonCordPredicate;
    public Predicate<IntCords2D> renderCoreHeatTooltipPredicate;

    public SmallReactorScreen(SmallReactorScreenHandler handler, PlayerInventory inventory, Text title) {
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

        renderFluidStorageTooltipPredicate1 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+8, y+19, getFluidAmount(0), getMaxFluidAmount(0), getFluidType(0));
        renderFluidStorageTooltipPredicate2 = ScreenUtils.renderSmallFluidStorage(this, matrices, x+127, y+19, getFluidAmount(1), getMaxFluidAmount(1), getFluidType(1));
        buttonCordPredicate = ScreenUtils.renderButton(this, matrices, x+57, y+18, handler.isActive());
        renderCoreHeatTooltipPredicate = ScreenUtils.renderCoreHeatBar(this, matrices,x+57, y+39, handler.getCoreHeat(),TEXTURE);
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        if(button==0) {
            IntCords2D mouse = new IntCords2D(Math.toIntExact(Math.round(mouseX)),Math.toIntExact(Math.round(mouseY)));
            if(buttonCordPredicate.test(mouse)){
                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeBlockPos(handler.getBlockPos());
                ClientPlayNetworking.send(SWITCH_REACTOR_POWER_PACKET, buf);
            }
        }
        return super.mouseReleased(mouseX, mouseY, button);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderFluidStorageTooltipPredicate1.test(mouse)) {
            renderFluidTooltip(0, hasShiftDown(), matrices, mouse);
        }
        if (renderFluidStorageTooltipPredicate2.test(mouse)) {
            renderFluidTooltip(1, hasShiftDown(), matrices, mouse);
        }
        if (renderCoreHeatTooltipPredicate.test(mouse)) {
            renderTooltip(matrices, List.of(Text.of("Core Temperature"),Text.of(handler.getCoreHeat()+"°C")), mouse.x,mouse.y);
        }
    }

    private void renderFluidTooltip(int fluidIndex, boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        renderTooltip(matrices, DisplayUtils.getFluidTooltipText(getFluidAmountmB(fluidIndex),getCapacitymB(fluidIndex), getFluidType(fluidIndex), exact), mouseCords.x, mouseCords.y);
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

}
