public class Timer {

    private long start;

    public Timer() {
        start = System.currentTimeMillis();
    }

    public Double getElapsedTime() {
        return (System.currentTimeMillis() - start) / 1000D;
    }

    public void getElapsedTimeMessage() {
        System.out.println("Time elapsed: " + getElapsedTime() + " s");
    }

}
