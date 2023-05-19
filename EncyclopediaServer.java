import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class EncyclopediaServer {
    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.createRegistry(1234);
            //For Anonymous array creation, do not mention size in []. The number of values passing inside {} will become the size.
            String[] words =  {"band", "data", "classical", "rapping", "hiphop", "jazz", "guitar", "piano",
                    "violin", "cello", "drums", "jazz", "saxophone", "trombone", "plane", "clarinet", "rapping",
                    "bassoon", "French", "Italian", "Spanish", "Norwegian", "Swedish", "plane", "Chinese",
                    "Japanese", "Korean", "English", "history", "mathematics", "Spain", "war", "physics",
                    "geography", "art", "band","horse", "government", "war", "peace", "war", "lunar",
                    "data", "band", "band", "data", "Venus", "Spain", "Mexico", "Brazil", "Argentina",
                    "Australia", "India", "Kenya", "television", "Namibia", "war", "war", "Italian", "tiger",
                    "jazz", "wolf", "band", "cat", "mouse", "horse", "pig", "cow", "goat", "sheep", "chicken", "duck",
                    "wheat", "rice", "band", "potato", "onion", "carrot", "cabbage", "jazz", "apple", "orange", "pear",
                    "banana", "plane", "band", "Spain", "blueberry", "Italian", "cauliflower", "television",
                    "almond", "walnut", "television", "data", "jazz", "film", "television", "phone", "jazz", "computer",
                    "mouse", "physics", "physics", "physics", "plane", "train", "data", "Spain", "hydrogen", "helium", "data",
                    "television", "data", "Italian", "stone", "glass", "metal", "city", "Spain", "string", "polymer",
                    "molecule", "atoms", "protons", "mouse", "electrons", "ions", "data", "information", "physics",
                    "research", "analysis", "conclusion", "synthesis", "classic", "modern", "television", "ancient", "medieval",
                    "millennium", "band", "television", "ecological", "city", "species", "ecosystem", "reproduction",
                    "stone", "stone", "band", "country", "city", "town", "village", "road", "intersection", "lake",
                    "ocean", "river", "waterfall", "mountain", "valley", "city", "data", "television", "jungle", "desert",
                    "television"};
            EncyclopediaImpl encyclopedia = new EncyclopediaImpl(words);
            r.rebind("Encyclopedia", encyclopedia);
            System.out.println("Encyclopedia server running...");
        } catch (Exception e) {
            System.err.println("Encyclopedia server exception:");
            e.printStackTrace();
        }
    }
}
