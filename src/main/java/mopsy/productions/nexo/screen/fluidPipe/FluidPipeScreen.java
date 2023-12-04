package mopsy.productions.nexo.screen.fluidPipe;

import com.mojang.blaze3d.systems.RenderSystem;
import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.util.IntCords2D;
import mopsy.productions.nexo.util.NEXORotation;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
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
import static mopsy.productions.nexo.networking.PacketManager.FLUID_PIPE_INVERT_STATE_PACKET;
import static mopsy.productions.nexo.util.NEXORotation.*;

public class FluidPipeScreen extends HandledScreen<FluidPipeScreenHandler> {
    private static final Identifier TEXTURE = new Identifier(modid, "textures/gui/fluid_pipe.png");
    public Predicate<IntCords2D> renderUpTooltipPredicate;
    public Predicate<IntCords2D> renderDownTooltipPredicate;
    public Predicate<IntCords2D> renderNorthTooltipPredicate;
    public Predicate<IntCords2D> renderEastTooltipPredicate;
    public Predicate<IntCords2D> renderSouthTooltipPredicate;
    public Predicate<IntCords2D> renderWestTooltipPredicate;
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


        if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
            renderUpTooltipPredicate = renderSlot(matrices, x+19, y+29, TEXTURE, entity, UP);
            renderDownTooltipPredicate = renderSlot(matrices, x+19, y+69, TEXTURE, entity, DOWN);
            renderNorthTooltipPredicate = renderSlot(matrices, x+69, y+29, TEXTURE, entity, NORTH);
            renderEastTooltipPredicate = renderSlot(matrices, x+89, y+49, TEXTURE, entity, EAST);
            renderSouthTooltipPredicate = renderSlot(matrices, x+69, y+69, TEXTURE, entity, SOUTH);
            renderWestTooltipPredicate = renderSlot(matrices, x+49, y+49, TEXTURE, entity, WEST);
        }
    }

    public Predicate<IntCords2D> renderSlot(MatrixStack matrices, int x, int y, Identifier texture, FluidPipe_MK1Entity entity, NEXORotation rotation){

        if(entity.endStates.get(rotation).isInput()) {
            RenderSystem.setShaderTexture(0, texture);
            drawTexture(matrices, x, y, 176, 0, 20, 20);
            itemRenderer.renderInGui(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().asItem().getDefaultStack(),x+2,y+2);
        }else if(entity.endStates.get(rotation).isOutput()){
            RenderSystem.setShaderTexture(0, texture);
            drawTexture(matrices, x, y, 196, 0, 20, 20);
            itemRenderer.renderInGui(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().asItem().getDefaultStack(),x+2,y+2);
        }else{
            RenderSystem.setShaderTexture(0, texture);
            drawTexture(matrices, x, y, 216, 0, 20, 20);
            itemRenderer.renderInGui(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().asItem().getDefaultStack(),x+2,y+2);
        }

        return ic2d -> (
                ic2d.x>x+2 && ic2d.x<x+18 &&
                        ic2d.y>y+2 && ic2d.y<y+18
        );
    }

    @Override
    protected void drawMouseoverTooltip(MatrixStack matrices, int x, int y) {
        super.drawMouseoverTooltip(matrices, x, y);
        if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
            IntCords2D mouse = new IntCords2D(x, y);
            if (renderUpTooltipPredicate.test(mouse))
                renderSlotTooltip(matrices, mouse, entity, UP);
            if (renderDownTooltipPredicate.test(mouse))
                renderSlotTooltip(matrices, mouse, entity, DOWN);
            if (renderNorthTooltipPredicate.test(mouse))
                renderSlotTooltip(matrices, mouse, entity, NORTH);
            if (renderEastTooltipPredicate.test(mouse))
                renderSlotTooltip(matrices, mouse, entity, EAST);
            if (renderSouthTooltipPredicate.test(mouse))
                renderSlotTooltip(matrices, mouse, entity, SOUTH);
            if (renderWestTooltipPredicate.test(mouse))
                renderSlotTooltip(matrices, mouse, entity, WEST);
        }
    }

    @Override
    protected void drawForeground(MatrixStack matrices, int mouseX, int mouseY) {
        this.textRenderer.draw(matrices, this.title, (float)this.titleX, (float)this.titleY, 4210752);
    }

    private void renderSlotTooltip(MatrixStack matrices, IntCords2D mouseCords, FluidPipe_MK1Entity entity, NEXORotation rotation){
        List<Text> text = new ArrayList<>();
        text.add(Text.of("Side: "+rotation.id.toUpperCase()));
        text.add(Text.of("Block name:"));
        text.add(Text.translatable(entity.getWorld().getBlockState(entity.getPos().add(rotation.transformToVec3i())).getBlock().getTranslationKey()));
        text.add(Text.of("Current mode: "+entity.endStates.get(rotation).toString()));
        if(entity.endStates.get(rotation).isEnd())
            text.add(Text.of("Click to switch input and output"));
        if(entity.endStates.get(rotation).isPipe())
            text.add(Text.of("Another pipe is connected here"));
        if(entity.endStates.get(rotation).isNone())
            text.add(Text.of("No fluid storage was found on this side"));
        renderTooltip(matrices, text, mouseCords.x, mouseCords.y);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        renderBackground(matrices);
        super.render(matrices, mouseX, mouseY, delta);
        drawMouseoverTooltip(matrices, mouseX, mouseY);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {

        IntCords2D ic2d = new IntCords2D(Math.toIntExact(Math.round(mouseX)),Math.toIntExact(Math.round(mouseY)));
        if(renderUpTooltipPredicate.test(ic2d)){
            if(MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(UP).isEnd())
                    sendInvertStatePacket(UP);
            }
        }else if(renderDownTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(DOWN).isEnd())
                    sendInvertStatePacket(DOWN);
            }
        }else if(renderNorthTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(NORTH).isEnd())
                    sendInvertStatePacket(NORTH);
            }
        }else if(renderEastTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(EAST).isEnd())
                    sendInvertStatePacket(EAST);
            }
        }else if(renderSouthTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(SOUTH).isEnd())
                    sendInvertStatePacket(SOUTH);
            }
        }else if(renderWestTooltipPredicate.test(ic2d)) {
            if (MinecraftClient.getInstance().world.getBlockEntity(handler.getBlockPos()) instanceof FluidPipe_MK1Entity entity) {
                if (entity.endStates.get(WEST).isEnd())
                    sendInvertStatePacket(WEST);
            }
        }


        return super.mouseClicked(mouseX, mouseY, button);
    }
    private void sendInvertStatePacket(NEXORotation rotation){
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeBlockPos(handler.getBlockPos());
        rotation.writeToPacket(buf);

        ClientPlayNetworking.send(FLUID_PIPE_INVERT_STATE_PACKET,buf);
    }
}
