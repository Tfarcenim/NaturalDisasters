package tfar.naturaldisasters.disasters;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Blizzard implements Disaster {

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
            ((ServerWorld)player.world).setWeather(0, 10000000, true, false);
            //slowness and blindness
            player.addPotionEffect(new EffectInstance(Effects.BLINDNESS,40,0));
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS,40,4));
        }
    }

    //pick random number between -r and +r
    private static int pickRandomNumber(Random random,int r) {
        int i = random.nextInt(2 * r + 1) - r;//r = 2; -2,-1,0,1,2
        return i;
    }
}
