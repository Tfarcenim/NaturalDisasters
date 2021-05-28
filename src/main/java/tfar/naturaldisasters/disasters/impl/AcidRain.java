package tfar.naturaldisasters.disasters.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import tfar.naturaldisasters.disasters.Disaster;

import java.util.Random;

public class AcidRain extends Disaster {

    public AcidRain() {
        super(new StringTextComponent("Acid Rain"));
    }

    @Override
    public void run(PlayerEntity player) {
        super.run(player);
        //remove 10 random blocks in a 40 block radius around the player evey minute
        long time = player.world.getGameTime();
        Random rand = player.getRNG();
        if (time % 20 == 0) {
            //summon green particles?

            //set to rain
            ((ServerWorld)player.world).setWeather(0, 10000000, true, false);
            //poison the player every second
            player.addPotionEffect(new EffectInstance(Effects.POISON,40,0));
            if (time % 1200 == 0) {
                for (int i = 0; i < 10; i++) {
                    int x = (int) (player.getPosX() + pickRandomNumber(rand, 40));
                    int z = (int) (player.getPosZ() + pickRandomNumber(rand, 40));
                    int y = player.world.getHeight(Heightmap.Type.MOTION_BLOCKING, x, z);
                    player.world.removeBlock(new BlockPos(x, y - 1, z), false);
                }
            }
        }
        checkTime(player);
    }
}
