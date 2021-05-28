package tfar.naturaldisasters.disasters.impl;

import net.minecraft.block.Blocks;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import tfar.naturaldisasters.disasters.Disaster;

import java.util.Random;

public class Hail extends Disaster {
    public Hail() {
        super(new StringTextComponent("Hail"));
    }

    //execute as @e[type=minecraft:area_effect_cloud,tag=nadi.anchor] at @s run summon falling_block ~ 255 ~ {BlockState: {Name: "minecraft:ice"}, Time: 1, HurtEntities: 1b, FallHurtMax: 40, FallHurtAmount: 0.03f}


    @Override
    public void run(PlayerEntity player) {
        super.run(player);
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
        checkTime(player);
    }
}
