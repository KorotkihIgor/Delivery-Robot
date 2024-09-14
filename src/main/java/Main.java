import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Main {
    public static final String MOVEMENTS = "RLRFR";
    public static final int NUMBER_OF_TURNS = 100;
    public static final int NUMBER_OF_THREADS = 100;
    public static final Map<Integer, Integer> sizeToFreq = new HashMap<>();

    public static String generateRoute(String letters, int length) {
        Random random = new Random();
        StringBuilder route = new StringBuilder();
        for (int i = 0; i < length; i++) {
            route.append(letters.charAt(random.nextInt(letters.length())));
        }
        return route.toString();
    }

    public static void main(String[] args) {
        for (int i = 0; i < NUMBER_OF_THREADS; i++) {
            new Thread(() -> {
                String result = generateRoute(MOVEMENTS, NUMBER_OF_TURNS);
                int compare = (int) result.chars().filter(ch -> ch == 'R').count();
                synchronized (sizeToFreq) {
                    if (sizeToFreq.containsKey(compare)) {
                        sizeToFreq.put(compare, sizeToFreq.get(compare) + 1);
                    } else {
                        sizeToFreq.put(compare, 1);
                    }
                }
            }).start();
        }
        Map.Entry<Integer, Integer> maxValue = sizeToFreq
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .get();
        System.out.printf("Самое частое количество повторений %s (встретилось %s раз)", maxValue.getKey(), maxValue.getValue());

        System.out.println(" Другие размеры : ");
        for (Map.Entry<Integer, Integer> kv : sizeToFreq.entrySet()) {
            System.out.println("-" + kv.getKey() + " ( " + kv.getValue() + "раз )");
        }
    }
}
