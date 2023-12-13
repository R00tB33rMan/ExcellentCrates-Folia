package su.nightexpress.excellentcrates.opening.animation;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import com.tcoded.folialib.wrapper.task.WrappedTask;
import su.nexmedia.engine.utils.random.Rnd;
import su.nexmedia.engine.utils.values.UniSound;
import su.nightexpress.excellentcrates.ExcellentCratesAPI;
import su.nightexpress.excellentcrates.opening.PlayerOpeningData;
import su.nightexpress.excellentcrates.opening.task.OpeningTask;

public class AnimationTask extends OpeningTask {

    private final AnimationInfo parent;
	    private WrappedTask task;

    public AnimationTask(@NotNull PlayerOpeningData data, @NotNull AnimationInfo parent) {
        super(data);
        this.parent = parent;
    }


    @Override
    public boolean isCancelled() {
    	return task.isCancelled();
    }

    @Override
    protected boolean onStart() {
        if (this.parent.getStartDelay() > 0 && this.parent.getTickInterval() <= 0) {
        	    task = ExcellentCratesAPI.PLUGIN.getFoliaLib().getImpl().runLater(this, this.parent.getStartDelay());
        }
        else {
        	    task = ExcellentCratesAPI.PLUGIN.getFoliaLib().getImpl().runTimer(this, this.parent.getStartDelay()+1, this.parent.getTickInterval());
        }
        return true;
    }

    @Override
    protected boolean onStop(boolean force) {
        return this.isStarted();
    }

    @Override
    public boolean canSkip() {
        return true;
    }

    @Override
    public void run() {
        if (this.data.isCompleted()) {
            task.cancel();
        }

        Inventory inventory = this.data.getInventory();
        if (this.parent.getMode() == AnimationInfo.Mode.TOGETHER) {
            ItemStack item = Rnd.get(this.parent.getItems());
            for (int slot : this.parent.getSlots()) {
                inventory.setItem(slot, item);
            }
        }
        else {
            for (int slot : this.parent.getSlots()) {
                inventory.setItem(slot, Rnd.get(this.parent.getItems()));
            }
        }

        inventory.getViewers().forEach(player -> {
            if (this.parent.getSoundTick() == null) return;

            UniSound.of(this.parent.getSoundTick()).play((Player) player);
        });
    }

    @NotNull
    public AnimationInfo getParent() {
        return parent;
    }
}
