package tfar.naturaldisasters;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import tfar.naturaldisasters.disasters.*;
import tfar.naturaldisasters.disasters.impl.*;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(NaturalDisasters.MODID)
public class NaturalDisasters {
    // Directly reference a log4j logger.

    public static final String MODID = "naturaldisasters";

    public static final Tags.IOptionalNamedTag<Block> drought_remove = BlockTags.createOptional(new ResourceLocation(MODID,"drought_remove"));

    private static final Map<UUID,List<Disaster>> activeDisasters = new HashMap<>();

    public NaturalDisasters() {

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.addListener(this::playerTick);
        MinecraftForge.EVENT_BUS.addListener(this::login);
    }

    private void login(PlayerEvent.PlayerLoggedInEvent e) {
        UUID uuid = e.getPlayer().getGameProfile().getId();
        addDisasters(uuid);
    }

    //need a fresh instance for every player
    private static void addDisasters(UUID uuid) {
        List<Disaster> dis =  new ArrayList<>();
        dis.add(new AcidRain());//0
        dis.add(new Blizzard());//1
        dis.add(new Doline());//2
        dis.add(new Drought());//3
        dis.add(new FireStorm());//4
        dis.add(new Hail());//5
        dis.add(new Sandstorm());//6
        dis.add(new Thunderstorm());//7
        activeDisasters.put(uuid,dis);
    }

    private void playerTick(final TickEvent.PlayerTickEvent event) {
        PlayerEntity player = event.player;
        if (!player.world.isRemote) {
            if (event.phase == TickEvent.Phase.START) {
                //for testing purposes only
                if (player.world.getGameTime() % 1200 == 0) {
                    toggleRandomDisaster(player);
                }
            } else {
                //run the disasters
                List<Disaster> disasters = activeDisasters.get(player.getGameProfile().getId());
                disasters.stream().filter(Disaster::isRunning).forEach(disaster -> disaster.run(player));
            }
        }
    }

    //activate random disaster
    public static void toggleRandomDisaster(PlayerEntity player) {
        UUID uuid = player.getGameProfile().getId();
        List<Disaster> disasters = activeDisasters.get(uuid);
        disasters.get(player.getRNG().nextInt(disasters.size())).start(player);
    }
}
