package net.nitkanikita21.metriccollector;

import com.mojang.logging.LogUtils;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;

import java.util.UUID;


@Mod("metric_collector")
public class MetricCollectorMod {
    public static final Logger LOGGER = LogUtils.getLogger();

    private final MetricCollector metricCollector = new MetricCollector(UUID.fromString(System.getenv(MetricCollector.SERVER_UUID_ENV_NAME)));


    public MetricCollectorMod(IEventBus modEventBus, ModContainer modContainer) {
        NeoForge.EVENT_BUS.addListener(this::tick);
    }

    private void tick(ServerTickEvent.Post event) {
        if (event.getServer().getTickCount() % 20 == 0) {
            float mspt = event.getServer().tickRateManager().millisecondsPerTick();
            float tps = event.getServer().tickRateManager().tickrate();

            metricCollector.collect(tps, mspt);
        }
    }
}
