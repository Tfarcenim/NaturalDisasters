package tfar.naturaldisasters.disasters.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import tfar.naturaldisasters.disasters.Disaster;

import java.util.Random;

public class FireStorm extends Disaster {

    public FireStorm() {
        super(new StringTextComponent("Firestorm"));
    }

    @Override
    public void run(PlayerEntity player) {
        super.run(player);
        long time = player.world.getGameTime();
        Random rand = player.getRNG();
        if (time % 20 == 0) {
            //clear the weather
            ((ServerWorld)player.world).setWeather(10000000, 0, false, false);
        }

        //summon fireballs and aim them at the ground
        for (int i = 0; i < 2;i++) {
            int x = (int) (player.getPosX() + pickRandomNumber(rand,32));
            int y = 255;
            int z = (int) (player.getPosZ() + pickRandomNumber(rand,32));

            SmallFireballEntity fireballEntity = new SmallFireballEntity(player.world,x,y,z,0,-1,0);

            player.world.addEntity(fireballEntity);
        }
        checkTime(player);
    }
}
