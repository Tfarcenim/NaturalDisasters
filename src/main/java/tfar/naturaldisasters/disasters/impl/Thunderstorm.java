package tfar.naturaldisasters.disasters.impl;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.LightningBoltEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.server.ServerWorld;
import tfar.naturaldisasters.disasters.Disaster;

import java.util.Random;

public class Thunderstorm extends Disaster {

    public Thunderstorm() {
        super(new StringTextComponent("Thunderstorm"));
    }

    @Override
    public void run(PlayerEntity player) {
        super.run(player);
        long time = player.world.getGameTime();
        Random rand = player.getRNG();
        if (time % 20 == 0) {
            //set thunderstorm
            ((ServerWorld)player.world).setWeather(0, 10000000, true, true);

            //summon thunderbolts
            for (int i = 0; i < 1;i++) {
                int x = (int) (player.getPosX() + pickRandomNumber(rand,32));
                int z = (int) (player.getPosZ() + pickRandomNumber(rand,32));

                LightningBoltEntity lightningboltentity = EntityType.LIGHTNING_BOLT.create(player.world);
                lightningboltentity.moveForced(x,player.world.getHeight(Heightmap.Type.MOTION_BLOCKING,x,z),z);
                player.world.addEntity(lightningboltentity);
            }
        }
        checkTime(player);
    }
}
