package net.nitkanikita21.metriccollector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class MetricCollector {
    public static final String SERVER_UUID_ENV_NAME = "HOSTNAME";
    public static final Duration PUSH_PERIOD = Duration.ofSeconds(15);

    private final UUID serverUUID;
    private final Logger LOGGER = LoggerFactory.getLogger("MetricCollector Common module");
    private final Timer timer = new Timer();

    public MetricCollector(UUID serverUUID) {
        this.serverUUID = serverUUID;
    }


    public void initializeAutoPush(
        Supplier<Float> tpsSupplier,
        Supplier<Float> msptSupplier
    ) {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                collect(tpsSupplier.get(), msptSupplier.get());
            }
        }, 0, PUSH_PERIOD.toMillis());
    }

    public void stopTimer() {
        timer.cancel();
    }

    public void collect(float tps, float mspt) {


        String body = String.format(
            Locale.US,
            "# TYPE minecraft_tps gauge\n" +
            "minecraft_tps{server=\"%s\"} %.2f\n" +
            "# TYPE minecraft_mspt gauge\n" +
            "minecraft_mspt{server=\"%s\"} %.2f\n",
            serverUUID, tps, serverUUID, mspt
        ) + "\n";

//        LOGGER.info("Sending metrics to Godlike Metric Collector: \n{}", body);

        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("http://194.247.42.61:9091/metrics/job/minecraft_server/instance/" + serverUUID).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "text/plain; version=0.0.4; charset=utf-8");
            try (OutputStream os = connection.getOutputStream()) {
                os.write(body.getBytes(StandardCharsets.UTF_8));
            }
            int responseCode = connection.getResponseCode();
            InputStream responseStream = (responseCode >= 200 && responseCode < 400)
                ? connection.getInputStream()
                : connection.getErrorStream();

            String responseText = new BufferedReader(new InputStreamReader(responseStream))
                .lines()
                .collect(Collectors.joining("\n"));

//            LOGGER.info("Pushgateway response ({}):\n{}", responseCode, responseText);

            connection.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
