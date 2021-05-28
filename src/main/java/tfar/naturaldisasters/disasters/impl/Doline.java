package tfar.naturaldisasters.disasters.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import tfar.naturaldisasters.disasters.Disaster;

import java.util.Random;

public class Doline extends Disaster {

    private BlockPos initial;

    public Doline() {
        super(new StringTextComponent("Doline"));
    }

    //make hole underneath player, then deactivate
    @Override
    public void run(PlayerEntity player) {
        super.run(player);
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
            end(player);
        }
        time++;
    }

    @Override
    public int getTimeLimit() {
        return 30 * 5;
    }
}
