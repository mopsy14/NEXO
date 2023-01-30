package mopsy.productions.nucleartech.screen.crusher;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nucleartech.ModBlocks.entities.machines.CrusherEntity;
import mopsy.productions.nucleartech.interfaces.IEnergyStorage;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static mopsy.productions.nucleartech.Main.modid;

public class CrusherScreen extends HandledScreen<CrusherScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/crusher.png");

    public CrusherScreen(CrusherScreenHandler handler, PlayerInventory inventory, Text title) {
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

        renderProgress(matrices, x, y);
        renderPower(matrices, x, y);
    }

    private void renderProgress(MatrixStack matrices, int x, int y){
        if(handler.isCrafting()){
            drawTexture(matrices, x+76, y+24, 176, 0, handler.getScaledProgress(), 37);
        }
    }
    private void renderPower(MatrixStack matrices, int x, int y){
        if(getPower()!=0){
            drawTexture(matrices, x+144, y+16, 203, getScaledPower(), 16, 64);
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
        long max = CrusherEntity.CAPACITY;
        int barSize = 64;
        return Math.toIntExact(max != 0 && progress != 0 ? progress * barSize / max : 0);
    }

    private long getPower(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IEnergyStorage) {
            return ((IEnergyStorage) blockEntity).getPower();
        }
        return 0;
    }
}
