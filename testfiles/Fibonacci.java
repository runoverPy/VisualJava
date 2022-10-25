class Fibonacci {
    public static void main(String[] args) {
        fibonacci_iterative(8);
    }

    public static int fibonacci_iterative(int iters) {
        if (iters < 0) return -1;
        int p0 = 0;
        int p1 = 1;
        int count = 1;
        while (count < iters) {
            int f = p0 + p1;     
            p0 = p1;              
            p1 = f;
            count++;
        }
        return p1;
    }

    public static int fibonacci_recursive(int level) {
        if (level <= 0) return 0;
        if (level == 1) return 1;
        return fibonacci_recursive(level - 1) + fibonacci_recursive(level - 2); 
    }
}