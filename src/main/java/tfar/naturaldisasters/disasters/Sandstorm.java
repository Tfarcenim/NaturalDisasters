package tfar.naturaldisasters.disasters;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Random;

public class Sandstorm implements Disaster {

    private boolean running;

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run(PlayerEntity player) {
        long time = player.world.getGameTime();
        Random rand = player.getRNG();
        if (time % 20 == 0) {
            //set to rain
            //summon slowfalling snowballs
            //summon white particles
            ((ServerWorld)player.world).setWeather(1000000, 0, false, false);
            //slowness and blindness
            player.addPotionEffect(new EffectInstance(Effects.BLINDNESS,40,0));
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS,40,4));


            //summon falling blocks of sand
            for (int i = 0; i < 5;i++) {
                int x = (int) (player.getPosX() + pickRandomNumber(rand,32));
                int y = 255;
                int z = (int) (player.getPosZ() + pickRandomNumber(rand,32));

                FallingBlockEntity sand = new FallingBlockEntity(player.world,x,y,z, Blocks.SAND.getDefaultState());

                //prevent removal
                sand.fallTime = 1;
                //make it hurt
                sand.setHurtEntities(true);
                player.world.addEntity(sand);
            }

        }
    }

    //pick random number between -r and +r
    private static int pickRandomNumber(Random random,int r) {
        int i = random.nextInt(2 * r + 1) - r;//r = 2; -2,-1,0,1,2
        return i;
    }
}
