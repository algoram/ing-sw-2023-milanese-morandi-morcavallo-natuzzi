package myshelfie_view;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class InputReader {
    private BufferedReader reader;
    private Thread inputThread;
    private FutureTask<String> futureTask;

    public InputReader() {
        reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public String readLine() throws InterruptedException, ExecutionException {
        futureTask = new FutureTask<>(() -> reader.readLine());
        inputThread = new Thread(futureTask);
        inputThread.start();
        return futureTask.get();
    }

    public void cancelInput() {
        if (inputThread != null) {
            inputThread.interrupt();
            futureTask.cancel(true);
        }
    }
}