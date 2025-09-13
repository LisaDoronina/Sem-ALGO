import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);
        int maxPrime = scanner.nextInt();

        ArrayList<Integer> primes = getPrimes(maxPrime);

        int p = primes.get(random.nextInt(primes.size()));
        int q = primes.get(random.nextInt(primes.size()));
        int n = p * q;

        int eurlerFunc = (p - 1) * (q - 1);
        int e = 0;

        for (int i = 2; i < eurlerFunc; i++){
            if (extendedGcd(eurlerFunc, i).gcd() == 1) {
                e = i;
                break;
            }
        }

        int d = extendedGcd(eurlerFunc, e).b();

        var publicKey = new RsaPublicKey(e, n);
        var privateKey = new RsaPrivateKey(d, n);

        System.out.println(privateKey + " " + publicKey);

        int m = scanner.nextInt();

        int encrypted = publicKey.encrypt(m);

        System.out.println("Шифр: " + encrypted);
        System.out.println("Расш " + privateKey.decrypt(encrypted));
    }

    private record RsaPrivateKey(int d, int n) {
        public int decrypt(int c) {
            int result = 1;

            for (int i = 0; i < d; i++){
                result = ((result * c) % n + c) % c;
            }

            return result;
        }
    }

    private record RsaPublicKey(int e, int n) {
        public int encrypt(int m) {
            int result = 1;

            for (int i = 0; i < e; i++){
                result = ((result * m) % n + m) % m;
            }

            return result;
        }
    }

    private record GcdResult(int gcd, int a, int b) {

    }

    private static GcdResult extendedGcd(int n, int m) {
        if (n < m) {
            int t = m;
            m = n;
            n = t;
        }

        if (m == 0) {
            return new GcdResult(n, 1, 0);
        }

        GcdResult gcdResult = extendedGcd(m, n % m);

        int b = gcdResult.a() - (n/m) * gcdResult.b();
        int a = gcdResult.b();

        return new GcdResult(gcdResult.gcd(), a, b);
    }

    private static ArrayList<Integer> getPrimes(int n) {
        boolean[] notPrimes = new boolean[n];
        ArrayList<Integer> primes = new ArrayList<Integer>();


        for (int i = 2; i < n; i++) {
            if (!notPrimes[i]) {
                primes.add(i);

                for (int k = i; k < n; k++) {
                    if (i * k < n) {
                        notPrimes[i * k] = true;
                    }
                }
            }
        }
        return primes;
    }
}