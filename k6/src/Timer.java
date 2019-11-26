public class Timer {

    private long start;

    public Timer() {
        start = System.currentTimeMillis();
    }

    public Double getElapsedTime() {
        return (System.currentTimeMillis() - start) / 1000D;
    }

    public String getElapsedTimeMessage() {
        return "Time elapsed: " + getElapsedTime() + " s";
    }

}
