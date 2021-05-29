package tfar.naturaldisasters.disasters;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.server.CustomServerBossInfo;
import net.minecraft.server.CustomServerBossInfoManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentUtils;
import tfar.naturaldisasters.NaturalDisasters;

import java.util.Random;

public abstract class Disaster {

    protected static int TIME_LIMIT = 60 * 20;
    protected boolean running;
    protected int time;
    protected CustomServerBossInfo customServerBossInfo;
    protected final ITextComponent disasterName;

    static int id = 0;

    protected Disaster(ITextComponent disasterName) {
        this.disasterName = disasterName;
    }

    public static int getNextID() {
        return ++id;
    }

    public final boolean isRunning() {
        return running;
    }

    public final void setRunning(boolean running) {
        this.running = running;
    }

    public void start(PlayerEntity player) {

        //before starting, kill any lingering boss bars before adding new ones
        if (isRunning()) removeBossBar(player);
        createBossBar(player);
        setRunning(true);
        time = 0;
    }

    public final void createBossBar(PlayerEntity player) {
        ITextComponent display = disasterName;
        CustomServerBossInfoManager customserverbossinfomanager = player.getServer().getCustomBossEvents();
        ResourceLocation id = new ResourceLocation(NaturalDisasters.MODID, getNextID() + "");
        try {
            customServerBossInfo = customserverbossinfomanager
                    .add(id, TextComponentUtils.func_240645_a_(player.getCommandSource(), display, null, 0));
        } catch (CommandSyntaxException e) {
            e.printStackTrace();
        }
        customServerBossInfo.addPlayer((ServerPlayerEntity) player);

        customServerBossInfo.setMax(getTimeLimit());
    }

    public final void checkTime(PlayerEntity player) {
        time++;
        if (time > getTimeLimit()) {
            end(player);
        }
    }

    public int getTimeLimit() {
        return TIME_LIMIT;
    }

    public final void updateBossBar() {
        customServerBossInfo.setPercent(1 - ((float) time / getTimeLimit()));
    }

    public final void removeBossBar(PlayerEntity player) {
        if (customServerBossInfo != null) {
            customServerBossInfo.removeAllPlayers();
            customServerBossInfo = null;
        }
    }

    public final void end(PlayerEntity player) {
        setRunning(false);
        removeBossBar(player);
    }

    public void run(PlayerEntity player) {
        updateBossBar();
    }

    //pick random number between -r and +r
    public static int pickRandomNumber(Random random, int r) {
        int i = random.nextInt(2 * r + 1) - r;//r = 2; -2,-1,0,1,2
        return i;
    }

}
