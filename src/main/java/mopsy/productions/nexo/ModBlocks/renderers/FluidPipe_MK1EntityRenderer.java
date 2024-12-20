package mopsy.productions.nexo.ModBlocks.renderers;

import mopsy.productions.nexo.ModBlocks.entities.transport.FluidPipe_MK1Entity;
import mopsy.productions.nexo.util.NEXORotation;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.Model;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.render.block.entity.BlockEntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static mopsy.productions.nexo.Main.modid;

@Environment(EnvType.CLIENT)
public class FluidPipe_MK1EntityRenderer implements BlockEntityRenderer<FluidPipe_MK1Entity> {

    private static final Identifier MAIN_TEXTURE = Identifier.of(modid,"textures/block/fluid_pipe_mk1.png");
    private static final Identifier INPUT_TEXTURE = Identifier.of(modid,"textures/block/fluid_pipe_mk1_input.png");
    private static final Identifier OUTPUT_TEXTURE = Identifier.of(modid,"textures/block/fluid_pipe_mk1_output.png");
    //private static final Identifier IO_TEXTURE = Identifier.of(modid,"textures/block/fluid_pipe_mk1_io.png");
    private static final Identifier PIPE_TEXTURE = Identifier.of(modid,"textures/block/fluid_pipe_mk1_pipe.png");

    private static final FluidPipe_MK1BaseModel BASE_MODEL = new FluidPipe_MK1BaseModel();
    private static final FluidPipe_MK1EndModel END_MODEL = new FluidPipe_MK1EndModel();
    private static final FluidPipe_MK1PipeModel PIPE_MODEL = new FluidPipe_MK1PipeModel();

    public FluidPipe_MK1EntityRenderer(BlockEntityRendererFactory.Context ctx) {}

    @Override
    public void render(FluidPipe_MK1Entity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();


        //Rendering the base part
        final VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getEntitySolid(MAIN_TEXTURE));
        BASE_MODEL.render(matrices,vertexConsumer,light,overlay,0xFFFFFFFF);


        //rendering all end textures
        for (NEXORotation rotation : NEXORotation.values()) {
            switch (entity.endStates.get(rotation)) {
                case IN -> render(END_MODEL,rotation,matrices,vertexConsumers, INPUT_TEXTURE,light,overlay);
                case OUT -> render(END_MODEL,rotation,matrices,vertexConsumers, OUTPUT_TEXTURE,light,overlay);
                //case IN_OUT -> render(END_MODEL,rotation,matrices,vertexConsumers, IO_TEXTURE,light,overlay);
                case PIPE -> render(PIPE_MODEL,rotation,matrices,vertexConsumers, PIPE_TEXTURE,light,overlay);
            }
        }



        matrices.pop();
    }
    private void render(Model model, NEXORotation rotation, MatrixStack matrices, VertexConsumerProvider vertexProvider, Identifier identifier, int light, int overlay){
        model.getRootPart().setAngles(rotation.x, rotation.y+1.5708f, rotation.z);
        //fun code:
        //model.getRootPart().rotate(rotation.getVec3f());
        model.render(matrices, vertexProvider.getBuffer(RenderLayer.getEntitySolid(identifier)), light, overlay,0xFFFFFFFF);
    }

    private static class FluidPipe_MK1BaseModel extends Model{

        public FluidPipe_MK1BaseModel(){
            super(new ModelPart(List.of(new ModelPart.Cuboid(0,0,6f,6f,6f,4f,4f,4f, 0f,0f,0f, false, 16f,16f, Set.copyOf(List.of(Direction.values())))), Collections.emptyMap()),
                    RenderLayer::getEntityCutoutNoCull);
        }
    }
    private static class FluidPipe_MK1EndModel extends Model{

        public FluidPipe_MK1EndModel(){
            super(new ModelPart(List.of(
                    new ModelPart.Cuboid(0,0,2f,-2f,-2f,4f,4f,4f, 0f,0f,0f, false, 32f,32f,Set.copyOf(List.of(Direction.values()))),
                    new ModelPart.Cuboid(0,10,6f,-3f,-3f,1f,6f,6f, 0f,0f,0f, false, 32f,32f,Set.copyOf(List.of(Direction.values()))),
                    new ModelPart.Cuboid(8,0,7f,-4f,-4f,1f,8f,8f, 0f,0f,0f, false, 32f,32f,Set.copyOf(List.of(Direction.values())))), Collections.emptyMap()),
                    RenderLayer::getEntityCutoutNoCull);

            root.setPivot(8f,8f,8f);
        }
    }
    private static class FluidPipe_MK1PipeModel extends Model{

        public FluidPipe_MK1PipeModel(){
            super(new ModelPart(List.of(new ModelPart.Cuboid(0,0,2f,-2f,-2f,6f,4f,4f, 0f,0f,0f, false, 32f,32f,Set.copyOf(List.of(Direction.values())))), Collections.emptyMap()),
                    RenderLayer::getEntityCutoutNoCull);

            root.setPivot(8f,8f,8f);
        }
    }
}
