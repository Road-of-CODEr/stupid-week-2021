import java.io.*;
import java.util.StringTokenizer;
  
public class Solution {
    private static class Node {
        int x, y;
        double cost;
  
        Node(int x, int y) {
            this.x = x;
            this.y = y;
        }
  
        Node(int x, int y, double cost) {
            this.x = x;
            this.y = y;
            this.cost = cost;
        }
    }
  
    private static class KeroList {
        Node[] data;
        int size, capacity = 8;
  
        KeroList() {
            data = new Node[capacity];
            size = 0;
        }
  
        void init() {
            size = 0;
        }
  
        void reallocate() {
            Node[] temp = new Node[capacity <<= 1];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
  
        void add(Node n) {
            if (size == capacity) {
                reallocate();
            }
            data[size++] = n;
        }
  
        Node get(int idx) {
            return data[idx];
        }
    }
  
    private static class KeroHeap {
        Node[] data;
        int size, capacity = 8;
  
        KeroHeap() {
            data = new Node[capacity];
            size = 1;
        }
  
        void reallocate() {
            Node[] temp = new Node[capacity <<= 1];
            for (int i = 0; i < size; i++) {
                temp[i] = data[i];
            }
            data = temp;
        }
  
        void init() {
            size = 1;
        }
  
        boolean isEmpty() {
            return size <= 1;
        }
  
        void add(Node n) {
            if (size == capacity) {
                reallocate();
            }
  
            data[size++] = n;
            downToUp();
        }
  
        Node poll() {
            Node n = data[1];
            data[1] = data[--size];
            upToDown();
            return n;
        }
  
        void downToUp() {
            int now = size - 1, next;
            while ((next = now >> 1) >= 1) {
                if (data[now].cost < data[next].cost) {
                    swap(now, next);
                    now = next;
                } else {
                    break;
                }
            }
        }
  
        void upToDown() {
            int now = 1, next;
  
            while ((next = now << 1) < size) {
                if (next + 1 < size && data[next].cost > data[next + 1].cost) {
                    next++;
                }
  
                if (data[next].cost < data[now].cost) {
                    swap(next, now);
                    now = next;
                } else {
                    break;
                }
            }
  
        }
  
        void swap(int a, int b) {
            Node temp = data[a];
            data[a] = data[b];
            data[b] = temp;
        }
  
        void print() {
            for (int i = 1; i < size; i++) {
                System.out.print(data[i] + " ");
            }
            System.out.println();
        }
    }
    static int N;
    static int[] x = new int[1000];
    static int[] parents = new int[1000];
    static double E;
    static KeroList keroList = new KeroList();
    static KeroHeap keroHeap = new KeroHeap();
    static StringBuilder sb = new StringBuilder();
  
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer st = new StringTokenizer(br.readLine());
        int tnum = stoi(st.nextToken());
  
        for (int t = 1; t <= tnum; t++) {
            st = new StringTokenizer(br.readLine());
            N = stoi(st.nextToken());
  
            init();
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                x[i] = stoi(st.nextToken());
            }
            st = new StringTokenizer(br.readLine());
            for (int i = 0; i < N; i++) {
                keroList.add(new Node(x[i], stoi(st.nextToken())));
            }
  
            st = new StringTokenizer(br.readLine());
            E = Double.parseDouble(st.nextToken());
  
            preProcess();
            sb.append("#" + t + " " + Math.round(kruscal()) + "\n");
        }
        System.out.print(sb.toString());
    }
  
    private static double kruscal() {
        long allSum = 0;
        while (N != 1 && !keroHeap.isEmpty()) {
            Node n = keroHeap.poll();
            if (union(n.x, n.y)) {
                N--;
                allSum += n.cost;
            }
        }
        return allSum * E;
    }
  
    private static void init() {
        keroList.init();
        keroHeap.init();
        for (int i = 0; i < N; i++) {
            parents[i] = -1;
        }
    }
  
    private static void preProcess() {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (i != j)
                    keroHeap.add(new Node(i, j, calc(i, j)));
            }
        }
    }
  
    private static double calc(int a, int b) {
        Node A = keroList.get(a);
        Node B = keroList.get(b);
        return Math.pow(A.x - B.x, 2) + Math.pow(A.y - B.y, 2);
    }
  
    private static boolean union(int a, int b) {
        a = find(a);
        b = find(b);
        if (a != b) {
            parents[b] = a;
            return true;
        }
        return false;
    }
  
    private static int find(int a) {
        return parents[a] < 0 ? a : (parents[a] = find(parents[a]));
    }
  
    private static int stoi(String input) {
        return Integer.parseInt(input);
    }
}