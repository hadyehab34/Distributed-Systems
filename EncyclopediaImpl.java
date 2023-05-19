import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EncyclopediaImpl extends UnicastRemoteObject implements Encyclopedia {
    private String[] words;

    public EncyclopediaImpl(String[] words) throws RemoteException {
        super();
        this.words = words;

    }

    @Override
    public int count() throws RemoteException {
        int count = 0;
        for (String word : words) {
            count += word.length();
        }
        return count;
    }

    @Override
    public String[] repeatedWords() throws RemoteException {
        Map<String, Integer> wordCount = new HashMap<>();

        for (String word : words) {
            if (wordCount.containsKey(word)) {
                wordCount.put(word, wordCount.get(word) + 1);
            } else {
                wordCount.put(word, 1);
            }
        }

        List<String> repeatedWords = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : wordCount.entrySet()) {
            if (entry.getValue() > 1) {
                repeatedWords.add(entry.getKey());
            }
        }

        return repeatedWords.toArray(new String[0]);

    }

//
    @Override
    public String longest() throws RemoteException {
        String longest = "";
        for (String word : words) {
            if (word.length() > longest.length()) {
                longest = word;
            }
        }
        return longest;
    }

    @Override
    public String shortest() throws RemoteException {
        String shortest = words[0];
        for (String word : words) {
            if (word.length() < shortest.length()) {
                shortest = word;
            }
        }
        return shortest;
    }

    @Override
    public Object[] repeat() throws RemoteException {
        int n = words.length;
        String[] uniqueStrings = new String[n];
        int[] repetitionCounts = new int[n];
        int uniqueCount = 0;

        for (int i = 0; i < n; i++) {
            // Check if the current string is already in the unique strings array
            boolean isUnique = true;
            for (int j = 0; j < uniqueCount; j++) {
                if (words[i].equals(uniqueStrings[j])) {
                    // If the string is already in the array, increment its repetition count
                    repetitionCounts[j]++;
                    isUnique = false;
                    break;
                }
            }
            if (isUnique) {
                // If the string is not already in the array, add it with a repetition count of 1
                uniqueStrings[uniqueCount] = words[i];
                repetitionCounts[uniqueCount] = 1;
                uniqueCount++;
            }
        }
        return new Object[]{uniqueStrings, repetitionCounts};
    }
}