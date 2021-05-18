package tfar.naturaldisasters.disasters;

import net.minecraft.entity.player.PlayerEntity;

public interface Disaster {

    boolean isRunning();

    void setRunning(boolean running);

    default void start() {
        setRunning(true);
    }

    default void end() {
        setRunning(false);
    }

    void run(PlayerEntity player);

}
