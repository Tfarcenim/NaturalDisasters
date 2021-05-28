package tfar.naturaldisasters.disasters.impl;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.server.ServerWorld;
import tfar.naturaldisasters.disasters.Disaster;

import java.util.Random;

public class Blizzard extends Disaster {

    public Blizzard() {
        super(new StringTextComponent("Blizzard"));
    }

    @Override
    public void run(PlayerEntity player) {
        super.run(player);
        long time = player.world.getGameTime();
        if (time % 20 == 0) {
            //set to rain
            //summon slowfalling snowballs
            //summon white particles
            ((ServerWorld)player.world).setWeather(0, 10000000, true, false);
            //slowness and blindness
            player.addPotionEffect(new EffectInstance(Effects.BLINDNESS,40,0));
            player.addPotionEffect(new EffectInstance(Effects.SLOWNESS,40,4));
        }
        checkTime(player);
    }
}
