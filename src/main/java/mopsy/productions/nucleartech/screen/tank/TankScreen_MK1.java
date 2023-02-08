package mopsy.productions.nucleartech.screen.tank;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.TankEntity_MK1;
import mopsy.productions.nucleartech.interfaces.IFluidStorage;
import net.fabricmc.fabric.api.transfer.v1.client.fluid.FluidVariantRendering;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawableHelper;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class TankScreen_MK1 extends HandledScreen<TankScreenHandler_MK1> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/tank.png");

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

        renderFluid(matrices, x, y);
        renderLines(matrices,x,y);
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        int relativeX = (width - backgroundWidth)/2;
        int relativeY = (height - backgroundHeight)/2;
        if(x>relativeX+24 && x<relativeX+76 && y>relativeY+10 && y<relativeY+ 74)
            renderTooltip(matrices, Text.of(Formatting.GOLD.toString()+getFluidAmountmb()+"mB/"+ getCapacitymb()+"mB"),x,y);
    }
    private void renderLines(MatrixStack matrices, int x, int y){
        RenderSystem.setShaderTexture(0, TEXTURE);
        drawTexture(matrices, x+25, y+11, 195, 8, 20, 63);
    }
    private void renderFluid(MatrixStack matrices, int x, int y){
        if(getFluidAmount()>0){
            RenderSystem.setShaderTexture(0, PlayerScreenHandler.BLOCK_ATLAS_TEXTURE);
            Sprite fluidSprite = FluidVariantRendering.getSprite(getFluid());

            int fluidColor = FluidVariantRendering.getColor(getFluid());
            RenderSystem.setShaderColor((fluidColor >> 16 & 255) / 255.0F, (float) (fluidColor >> 8 & 255) / 255.0F, (float) (fluidColor & 255) / 255.0F, 1F);

            DrawableHelper.drawSprite(matrices, x+25, y +11+getScaledFluid(), 0, 51, 63-getScaledFluid(), fluidSprite);
            RenderSystem.setShaderColor(1F,1F,1F,1F);
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    public int getScaledFluid(){
        long progress = getFluidAmount();
        long max = getCapacity();
        int barSize = 63;
        int res = Math.toIntExact(max != 0 && progress != 0 ? progress * barSize / max : 0);
        res = 63-res;
        return res;
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
    private long getFluidAmountmb(){
        return getFluidAmount()/81;
    }
    private long getCapacity(){
        TankEntity_MK1 entity = (TankEntity_MK1) client.world.getBlockEntity(handler.getBlockPos());
        if(entity!= null){
            return entity.fluidStorage.getCapacity();
        }
        return 0;
    }

    private long getCapacitymb(){
        return getCapacity()/81;
    }
}
