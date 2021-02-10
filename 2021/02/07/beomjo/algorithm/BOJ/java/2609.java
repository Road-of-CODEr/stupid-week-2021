
import java.io.*;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        int a = sc.nextInt();
        int b = sc.nextInt();

        int gcp = getGCP(a, b);
        int lcm = getLCM(a, b, gcp);

        System.out.println(gcp);
        System.out.println(lcm);
    }

    static int getGCP(int a, int b) {
        while (b != 0) {
            int r = a % b;
            a = b;
            b = r;
        }
        return a;
    }

    static int getLCM(int a, int b, int gcp) {
        return a * b / gcp;
    }
}