import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class EncyclopediaClientMultiThreaded {


    public static void main(String[] args) throws MalformedURLException, NotBoundException, RemoteException {

        Encyclopedia encyclopedia = (Encyclopedia) Naming.lookup("rmi://localhost:1234/Encyclopedia");

        Thread countThread = new Thread(() -> {
            int count = 0;
            try {
                count = encyclopedia.count();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Number of letters: " + count);
        });

        Thread repeatedWordsThread = new Thread(() -> {
            try {
                String[] repeatedWords = encyclopedia.repeatedWords();
                System.out.println("Repeated words: " + String.join(", ", repeatedWords));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread longestThread = new Thread(() -> {
            try {
                String longest = encyclopedia.longest();
                System.out.println("Longest word: " + longest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread shortestThread = new Thread(() -> {
            try {
                String shortest = encyclopedia.shortest();
                System.out.println("Shortest word: " + shortest);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Thread repeatCountsThread = new Thread(() -> {
            Object[] repeatCounts ;
            try {
                repeatCounts = encyclopedia.repeat();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            String[] n = (String[]) repeatCounts[0];
            int[] nn = (int[]) repeatCounts[1];
            for (int i = 0; i < n.length; i++) {
                if (n[i] != null){
                    System.out.print("Repeat counts:" +n[i] + ": " + nn[i]+ " , ");
                }
            }
        });

        long startTime = System.currentTimeMillis();
        countThread.start();
        repeatedWordsThread.start();
        longestThread.start();
        shortestThread.start();
        repeatCountsThread.start();

        try {
            countThread.join();
            repeatedWordsThread.join();
            longestThread.join();
            shortestThread.join();
            repeatCountsThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println();
        System.out.println("Total execution time: " + elapsedTime + " ms");
    }
}