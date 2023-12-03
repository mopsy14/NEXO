package mopsy.productions.nexo.screen.fluidPipe;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModBlocks.entities.machines.CrusherEntity;
import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.interfaces.IEnergyStorage;
import mopsy.productions.nexo.util.DisplayUtils;
import mopsy.productions.nexo.util.IntCords2D;
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

public class FluidPipeScreen extends HandledScreen<FluidPipeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/fluid_pipe.png");
    public Predicate<IntCords2D> renderEnergyTooltipPredicate;

    public FluidPipeScreen(FluidPipeScreenHandler handler, PlayerInventory inventory, Text title) {
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

        drawSlots(matrices, x, y);

        //renderEnergyTooltipPredicate = ScreenUtils.renderEnergyStorage(this, matrices, x+147, y+11, getPower(), CrusherEntity.POWER_CAPACITY);
    }

    private void drawSlots(MatrixStack matrices, int x, int y){

        if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
            itemRenderer.renderInGui(entity.getWorld().getBlockState(entity.getPos().add(0,0,1)).getBlock().asItem().getDefaultStack(),x+50,y+50);
        }
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        IntCords2D mouse = new IntCords2D(x,y);
        if (renderEnergyTooltipPredicate.test(mouse))
            renderEnergyTooltip(hasShiftDown(), matrices, mouse);
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    private void renderEnergyTooltip(boolean exact, MatrixStack matrices, IntCords2D mouseCords){
        List<Text> text = new ArrayList<>();
        text.add(Text.of(DisplayUtils.getEnergyBarText(getPower(), CrusherEntity.POWER_CAPACITY, hasShiftDown())));
        if(!exact)
            text.add(Text.of("Hold shift for advanced view"));
        renderTooltip(matrices, text, mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        //drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    private long getPower(){
        BlockEntity blockEntity = MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos());
        if(blockEntity instanceof IEnergyStorage) {
            return ((IEnergyStorage) blockEntity).getPower();
        }
        return 0;
    }
}
