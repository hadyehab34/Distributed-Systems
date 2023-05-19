import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class enc2threads {
    private static final int NUM_THREADS = 5;

    public static void main(String[] args) {
        try {
            Encyclopedia encyclopedia = (Encyclopedia) Naming.lookup("rmi://localhost:1234/Encyclopedia");

            ExecutorService executor = Executors.newFixedThreadPool(NUM_THREADS);

            long start = System.currentTimeMillis();

            executor.submit(() -> {
                try {
                    int count = encyclopedia.count();
                    System.out.println("Number of letters: " + count);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executor.submit(() -> {
                try {
                    String[] repeatedWords = encyclopedia.repeatedWords();
                    System.out.println("Repeated words: " + String.join(", ", repeatedWords));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executor.submit(() -> {
                try {
                    String longest = encyclopedia.longest();
                    System.out.println("Longest word: " + longest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executor.submit(() -> {
                try {
                    String shortest = encyclopedia.shortest();
                    System.out.println("Shortest word: " + shortest);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });

            executor.submit(() -> {
                Object[] repeatCounts ;
                try {
                    repeatCounts = encyclopedia.repeat();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
                String[] n = (String[]) repeatCounts[0];
                int[] nn = (int[]) repeatCounts[1];
                System.out.print("Repeat counts: ");
                for (int i = 0; i < n.length; i++) {
                    if (n[i] != null){
                        System.out.println(n[i] + ": " + nn[i]);
                    }
                }
            });

            executor.shutdown();
            executor.awaitTermination(Long.MAX_VALUE, TimeUnit.MILLISECONDS);

            long end = System.currentTimeMillis();
            System.out.println("Time taken: " + (end - start) + "ms");
        } catch (Exception e) {
            System.err.println("Encyclopedia client exception:");
            e.printStackTrace();
        }
    }
}
