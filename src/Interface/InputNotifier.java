package Interface;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class InputNotifier implements Runnable {

    private Thread mainThread;
    private volatile boolean isFinish;

    public InputNotifier(Thread mainThread) {
        this.mainThread = mainThread;
    }

    public void finish() {
        this.isFinish = true;
    }

    @Override
    public void run() {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (isFinish == false) {
                Thread.sleep(50);
                if (bufferedReader.ready()) {
                    this.mainThread.interrupt();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace(); // should be good if do nothing
        }
    }
}
