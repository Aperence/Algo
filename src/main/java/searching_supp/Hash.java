package searching_supp;

public class Hash {

    public static long hash(double i){
        return (int) i ^ ((long) i >>> 32);
    }
    public static long hash(long i){
        return (int) i ^ (i >>> 32);
    }

    public static void main(String[] args) {
        int i = -1;
        System.out.println(i);
        System.out.println(Long.toBinaryString(i));
        //System.out.println(Double.toHexString((double) i));
        System.out.println(Long.toBinaryString(hash((long) i)));
        System.out.println(Long.toBinaryString(hash((double) i )));
    }
}
