import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;

public class RandomMusicGenerator {
    public static void main(String[] args) {
        Map<Integer, String> noteSerializationMap = getNoteSerializationMap();

        StringBuilder abc = new StringBuilder();

        abc.append("X: 1\n");
        abc.append("M: 4/4\n");
        abc.append("L: 1/16\n");

        // Get random numbers
        Random ran = new Random(LocalDateTime.now().toEpochSecond(ZoneOffset.MIN));

        System.out.println("How many measures would you like to generate?");

        Scanner in = new Scanner(System.in);
        int measures = in.nextInt();
        System.out.println();

        int lastNote = -1;
        for (int measure = 0 ; measure < measures ; measure++) {
            for (int group = 0 ; group < 4 ; group++) {
                for (int note = 0 ; note < 4 ; note++) {
                    int num = ran.nextInt(10);

                    // Prevent repeating notes
                    while (num == lastNote) {
                        num = ran.nextInt(10);
                    }

                    // Adds accidental if previous note is sharp/flat of same note
                    if (Math.abs(num - lastNote) == 1) {
                        if (noteSerializationMap.get(lastNote).length() == 2) {
                            abc.append('=');
                        }
                    }

                    abc.append(noteSerializationMap.get(num));

                    // Randomly decide octave
                    if (ran.nextInt() % 2 == 0) {
                        abc.append('\'');
                    }

                    lastNote = num;
                }
                abc.append(' ');
            }
            abc.append("|\n");
        }

        try (FileWriter writer = new FileWriter("notes.abc")) {
            writer.write(abc.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Successfully saved to notes.abc");
    }

    private static Map<Integer, String> getNoteSerializationMap() {
        Map<Integer, String> noteSerializationMap = new HashMap<>();

        noteSerializationMap.put(0, "C");
        noteSerializationMap.put(1, "^C");
        noteSerializationMap.put(2, "D");
        noteSerializationMap.put(3, "^D");
        noteSerializationMap.put(4, "E");
        noteSerializationMap.put(5, "F");
        noteSerializationMap.put(6, "^F");
        noteSerializationMap.put(7, "G");
        noteSerializationMap.put(8, "^G");
        noteSerializationMap.put(9, "A");

        return noteSerializationMap;
    }
}
