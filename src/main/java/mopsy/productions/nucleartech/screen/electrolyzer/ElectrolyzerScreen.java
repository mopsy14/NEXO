package mopsy.productions.nucleartech.screen.electrolyzer;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.AirSeparatorEntity;
import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import mopsy.productions.nucleartech.util.ScreenUtils;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class ElectrolyzerScreen extends HandledScreen<ElectrolyzerScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/electrolyzer.png");

    public ElectrolyzerScreen(ElectrolyzerScreenHandler handler, PlayerInventory inventory, Text title) {
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

        ScreenUtils.renderSmallFluidStorage(this, matrices, x+5, y+5, getFluidAmount(0), getMaxFluidAmount(0), getFluidType(0));
        ScreenUtils.renderSmallFluidStorage(this, matrices, x+25, y+5, getFluidAmount(1), getMaxFluidAmount(1), getFluidType(1));
        ScreenUtils.renderSmallFluidStorage(this, matrices, x+45, y+5, getFluidAmount(2), getMaxFluidAmount(2), getFluidType(2));
        renderPower(matrices, x, y);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        int relativeX = (width - backgroundWidth)/2;
        int relativeY = (height - backgroundHeight)/2;
        if(x>relativeX+147 && x<relativeX+163 && y>relativeY+10 && y<relativeY+ 75)
            renderTooltip(matrices, Text.of(Formatting.GOLD.toString()+getPower()+"E/"+ AirSeparatorEntity.POWER_CAPACITY+"E"),x,y);
    }
    private void renderPower(MatrixStack matrices, int x, int y){
        if(getPower()!=0){
            drawTexture(matrices, x+147, y+11+getScaledPower(), 203, getScaledPower(), 16, 62-getScaledPower());
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public int getScaledPower(){
        long progress = getPower();
        long max = AirSeparatorEntity.POWER_CAPACITY;
        int barSize = 62;
        int res=  Math.toIntExact(max != 0 && progress != 0 ? progress * barSize / max : 0);
        res = 62-res;
        return res;
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
}
