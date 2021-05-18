package tfar.naturaldisasters.disasters;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

import java.util.Random;

public class Doline implements Disaster {

    private boolean running;
    private int time;
    private BlockPos initial;

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public void start() {
        Disaster.super.start();
        time = 0;
    }

    @Override
    public void setRunning(boolean running) {
        this.running = running;
    }


    //make hole underneath player, then deactivate
    @Override
    public void run(PlayerEntity player) {
        int stage = time / 5;
        if (time == 0) {
            initial = player.getPosition();
        }

        if (stage > 0) {
            int y = initial.getY() - stage;
            for (int x = -5; x < 5;x++) {
                for (int z = -5; z < 5;z++) {
                    player.world.removeBlock(new BlockPos(initial.getX() + x,y,initial.getZ() + z),false);
                }
            }
        }

        if (stage > 30) {
            end();
        }

        time++;
    }

    //pick random number between -r and +r
    private static int pickRandomNumber(Random random,int r) {
        int i = random.nextInt(2 * r + 1) - r;//r = 2; -2,-1,0,1,2
        return i;
    }
}
