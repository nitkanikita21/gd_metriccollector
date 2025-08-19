package net.nitkanikita21.metriccollector;

public class Lag implements Runnable {
    public static int TICK_COUNT = 0;
    public static final long[] TICKS = new long[600];

    // --- TPS ---

    public static synchronized double getTPS() {
        return getTPS(100);
    }

    public static synchronized double getTPS(int ticks) {
        double mspt = getMSPT(ticks);
        return Math.min(1000.0 / mspt, 20.0);
    }

    // --- MSPT ---

    public static synchronized double getMSPT() {
        return getMSPT(100);
    }

    public static synchronized double getMSPT(int ticks) {
        if (TICK_COUNT < ticks + 1) {
            return 50.0D; // за замовчуванням
        }

        long total = 0;
        for (int i = 0; i < ticks; i++) {
            int currentIndex = (TICK_COUNT - 1 - i) % TICKS.length;
            int previousIndex = (TICK_COUNT - 2 - i) % TICKS.length;
            long diff = TICKS[currentIndex] - TICKS[previousIndex];
            total += diff;
        }

        return total / (double) ticks;
    }

    // --- Інтервал від певного тікета ---

    public static synchronized long getElapsed(int tickID) {
        if (tickID < 0 || tickID >= TICK_COUNT || TICK_COUNT - tickID >= TICKS.length) {
            return -1;
        }

        long time = TICKS[tickID % TICKS.length];
        return System.currentTimeMillis() - time;
    }

    // --- Оновлення тікету ---

    @Override
    public void run() {
        long currentTime = System.currentTimeMillis();
        TICKS[TICK_COUNT % TICKS.length] = currentTime;
        TICK_COUNT++;
    }
}
