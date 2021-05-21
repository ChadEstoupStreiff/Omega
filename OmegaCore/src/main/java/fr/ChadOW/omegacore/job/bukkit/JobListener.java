package fr.ChadOW.omegacore.job.bukkit;

import fr.ChadOW.omegacore.job.JobPayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class JobListener implements Listener {

    @EventHandler
    public void onBreak(BlockBreakEvent event) {
        JobPayer.addEvent(event.getPlayer(), event.getBlock().getBlockData().getMaterial());
    }
}
