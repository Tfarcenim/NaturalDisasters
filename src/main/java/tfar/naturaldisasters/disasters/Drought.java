package tfar.naturaldisasters.disasters;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import tfar.naturaldisasters.NaturalDisasters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Drought implements Disaster {

    private boolean running;

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }


    // "dries out"
    //fill ~ ~ ~ ~16 ~96 ~16 minecraft:air replace #2mal3:nadi/drought_remove
    //fill ~ ~ ~ ~16 ~96 ~16 minecraft:dead_bush replace #minecraft:flowers
    //fill ~ ~ ~ ~16 ~96 ~16 minecraft:sand replace minecraft:grass_block
    //fill ~ ~ ~ ~16 ~96 ~16 minecraft:sandstone replace minecraft:dirt
    //fill ~ ~ ~ ~16 ~96 ~16 minecraft:sandstone replace minecraft:coarse_dirt

    //fill ~ ~96 ~ ~16 ~160 ~16 minecraft:air replace #2mal3:nadi/drought_remove
    //fill ~ ~96 ~ ~16 ~160 ~16 minecraft:dead_bush replace #minecraft:flowers
    //fill ~ ~96 ~ ~16 ~160 ~16 minecraft:sand replace minecraft:grass_block
    //fill ~ ~96 ~ ~16 ~160 ~16 minecraft:sandstone replace minecraft:dirt
    //fill ~ ~96 ~ ~16 ~160 ~16 minecraft:sandstone replace minecraft:coarse_dirt;


    @Override
    public void run(PlayerEntity player) {
        long time = player.world.getGameTime();
        Random rand = player.getRNG();
        if (time % 20 == 0) {
            //clear the weather
            ((ServerWorld)player.world).setWeather(10000000, 0, false, false);
        }

        //pick a couple blocks at random to dehydrate every tick
        for (int i = 0; i < 20;i++) {
            int x = (int) (player.getPosX() + pickRandomNumber(rand,32));
            int y = (int) (player.getPosY() + pickRandomNumber(rand,32));
            int z = (int) (player.getPosZ() + pickRandomNumber(rand,32));

            BlockPos pos = new BlockPos(x,y,z);

            BlockState state = player.world.getBlockState(pos);

            BlockState newState = convert(state.getBlock());
            if (newState != null) {
                player.world.setBlockState(pos,newState);
            }
        }
    }

    //should use block ingredients but effort
    private static final Map<Block,Block> conv = new HashMap<>();

    private static final Map<ITag<Block>,Block> tagConv = new HashMap<>();

    static {
        tagConv.put(BlockTags.FLOWERS, Blocks.DEAD_BUSH);
        tagConv.put(NaturalDisasters.drought_remove,Blocks.AIR);

        conv.put(Blocks.GRASS_BLOCK,Blocks.SAND);
        conv.put(Blocks.DIRT,Blocks.SANDSTONE);
        conv.put(Blocks.COARSE_DIRT,Blocks.SANDSTONE);
    }

    private static BlockState convert(Block block) {
        for (Map.Entry<ITag<Block>,Block> conv : tagConv.entrySet()) {
            if (conv.getKey().contains(block)) {
                return conv.getValue().getDefaultState();
            }
        }

        for (Map.Entry<Block,Block> conv : conv.entrySet()) {
            if (conv.getKey().matchesBlock(block)) {
                return conv.getValue().getDefaultState();
            }
        }
        return null;
    }



    //pick random number between -r and +r
    private static int pickRandomNumber(Random random,int r) {
        int i = random.nextInt(2 * r + 1) - r;//r = 2; -2,-1,0,1,2
        return i;
    }
}
