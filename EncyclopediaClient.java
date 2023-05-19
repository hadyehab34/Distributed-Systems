
import java.rmi.Naming;

public class EncyclopediaClient {
    public static void main(String[] args) {
        try {
            Encyclopedia encyclopedia = (Encyclopedia) Naming.lookup("rmi://localhost:1234/Encyclopedia");

            long start = System.currentTimeMillis();

            int count = encyclopedia.count();
            System.out.println("Number of letters: " + count);

            String[] repeatedWords = encyclopedia.repeatedWords();
            System.out.println("Repeated words: " + String.join(", ", repeatedWords));

            String longest = encyclopedia.longest();
            System.out.println("Longest word: " + longest);

            String shortest = encyclopedia.shortest();
            System.out.println("Shortest word: " + shortest);

            Object[] repeatCounts = encyclopedia.repeat();
            String[] n = (String[]) repeatCounts[0];
            int[] nn = (int[]) repeatCounts[1];
            System.out.print("Repeat counts: ");
            for (int i = 0; i < n.length; i++) {
                if (n[i] != null){
                    System.out.print(n[i] + ": " + nn[i]+" , ");
                }
            }

            long end = System.currentTimeMillis();
            System.out.println();
            System.out.println("Time taken: " + (end - start) + "ms");
        } catch (Exception e) {
            System.err.println("Encyclopedia client exception:");
            e.printStackTrace();
        }
    }
}
