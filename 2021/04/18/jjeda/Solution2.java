import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Solution2 {
    int[] result = {0, 0};
    int[] passenger;
    Map<Integer, LinkedList<Integer>> map = new HashMap<>();

    public int[] solution(int n, int[] passenger, int[][] train) {
        this.passenger = passenger;

        for (int[] info: train) {
            int key = info[0];
            int value = info[1];

            if (map.containsKey(key)) {
                map.get(key).add(value);
            } else {
                LinkedList<Integer> newList = new LinkedList<>();
                newList.add(value);
                map.put(key, newList);
            }
        }
        go(1, passenger[0]);

        return result;
    }
    private void go(int station, int totalPassenger) {
        if (!map.containsKey(station)) {
            if (result[1] <= totalPassenger) {
                result[1] = totalPassenger;
                result[0] = station;
            }
            return;
        }

        LinkedList<Integer> list = map.get(station);
        for (int nextStation: list) {
            go(nextStation, totalPassenger + passenger[nextStation - 1]);
        }
    }
}
