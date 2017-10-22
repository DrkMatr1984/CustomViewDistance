package me.drkmatr1984.customviewdistance.tasks;

public class ServerTPS implements Runnable
{
    private static int TICK_COUNT;
    private static long[] TICKS;
    public static long LAST_TICK;
    
    public static double getTPS() {
        return getTPS(100);
    }
    
    public static double getTPS(final int ticks) {
        if (ServerTPS.TICK_COUNT < ticks) {
            return 20.0;
        }
        final int target = (ServerTPS.TICK_COUNT - 1 - ticks) % ServerTPS.TICKS.length;
        final long elapsed = System.currentTimeMillis() - ServerTPS.TICKS[target];
        return ticks / (elapsed / 1000.0);
    }
    
    public static long getElapsed(final int tickID) {
        if (ServerTPS.TICK_COUNT - tickID >= ServerTPS.TICKS.length) {}
        final long time = ServerTPS.TICKS[tickID % ServerTPS.TICKS.length];
        return System.currentTimeMillis() - time;
    }
    
    @Override
    public void run() {
        ServerTPS.TICKS[ServerTPS.TICK_COUNT % ServerTPS.TICKS.length] = System.currentTimeMillis();
        ++ServerTPS.TICK_COUNT;
    }
    
    static {
        ServerTPS.TICK_COUNT = 0;
        ServerTPS.TICKS = new long[600];
        ServerTPS.LAST_TICK = 0L;
    }
}
