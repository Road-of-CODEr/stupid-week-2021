import java.util.HashMap;
import java.util.Map;

class Solution {
    public int solution(int[] gift_cards, int[] wants) {
        int count = 0;
        Map<Integer, Integer> map = new HashMap<>();
        Map<Integer, Integer> wantMap = new HashMap<>();

        for (int card: gift_cards) {
            if (map.containsKey(card)) {
                map.put(card, map.get(card) + 1);
            } else {
                map.put(card, 1);
            }
        }

        for (int card: wants) {
            if (wantMap.containsKey(card)) {
                wantMap.put(card, wantMap.get(card) + 1);
            } else {
                wantMap.put(card, 1);
            }
        }

        for (int card: map.keySet()) {
            if (!wantMap.containsKey(card)) continue;

            count += Math.min(map.get(card), wantMap.get(card));
        }

        return gift_cards.length - count;
    }
}
