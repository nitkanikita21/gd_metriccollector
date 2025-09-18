package net.nitkanikita21.metriccollector;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Timer;
import java.util.UUID;

public class MetricCollectorPlugin extends JavaPlugin {
    private final MetricCollector metricCollector = new MetricCollector(UUID.fromString(System.getenv(MetricCollector.SERVER_UUID_ENV_NAME)));


    @Override
    public void onEnable() {
        super.onEnable();

        Bukkit.getScheduler().runTaskTimer(this, new Lag(), 0L, 1L);

        metricCollector.initializeAutoPush(() -> (float) Lag.getTPS(), () -> (float) Lag.getMSPT());

    }
}
