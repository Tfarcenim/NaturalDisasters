package tfar.naturaldisasters.disasters;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ITag;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import tfar.naturaldisasters.NaturalDisasters;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Hail implements Disaster {

    private boolean running;

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    //execute as @e[type=minecraft:area_effect_cloud,tag=nadi.anchor] at @s run summon falling_block ~ 255 ~ {BlockState: {Name: "minecraft:ice"}, Time: 1, HurtEntities: 1b, FallHurtMax: 40, FallHurtAmount: 0.03f}


    @Override
    public void run(PlayerEntity player) {
        long time = player.world.getGameTime();
        Random rand = player.getRNG();
        if (time % 20 == 0) {
            //clear the weather
            ((ServerWorld)player.world).setWeather(10000000, 0, false, false);
        }

        //summon falling blocks of  ice that hurt
        for (int i = 0; i < 1;i++) {
            int x = (int) (player.getPosX() + pickRandomNumber(rand,32));
            int y = 255;
            int z = (int) (player.getPosZ() + pickRandomNumber(rand,32));

            FallingBlockEntity hail = new FallingBlockEntity(player.world,x,y,z,Blocks.ICE.getDefaultState());

            //prevent removal
            hail.fallTime = 1;
            //make it hurt
            hail.setHurtEntities(true);
            //a lot
            ObfuscationReflectionHelper.setPrivateValue(FallingBlockEntity.class,hail,40,"field_145816_i");
            player.world.addEntity(hail);
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
