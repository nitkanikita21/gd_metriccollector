package net.nitkanikita21.metriccollector;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class MetricCollectorMod implements ModInitializer {
    public static final String MOD_ID = "metric_collector";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    private final MetricCollector metricCollector = new MetricCollector(UUID.fromString(System.getenv(MetricCollector.SERVER_UUID_ENV_NAME)));
//    private final MetricCollector metricCollector = new MetricCollector(UUID.randomUUID());

    @Override
    public void onInitialize() {
        LOGGER.info("METRIC COLLECTOR SETUP");

        ServerLifecycleEvents.SERVER_STARTED.register(server -> {
            metricCollector.initializeAutoPush(
                server.getTickManager()::getTickRate,
                server.getTickManager()::getMillisPerTick
            );
        });


    }
}