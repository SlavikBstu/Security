import org.apache.commons.lang3.ArrayUtils;

import javax.xml.bind.DatatypeConverter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class Hash {
    private static byte[] X_bytes = new byte[512];
   // public static byte[] C = DatatypeConverter.parseHexBinary(" B194BAC80A08F53B");
    public static String H_value  = "B194BAC80A08F53B366D008E584A5DE48504FA9D1BB6C7AC252E72C202FDCE0D5BE3D61217B96181FE6786AD716B890B" +
            "5CB0C0FF33C356B835C405AED8E07F99E12BDC1AE28257EC703FCCF095EE8DF1C1AB76389FE678CAF7C6F860D5BB9C4FF33C657B637C306ADD4EA7799EB23D3" +
            "3E98B56E27D3BCCF591E181F4C5AB793E9DEE72C8F0C0FA62DDB49F46F73964706075316ED247A3739CBA38303A98BF692BD9B1CE5D141015445FBC95E4D0EF2682080AA227D642F" +
            "2687F93490405511";
    public static byte[] S_value = DatatypeConverter.parseHexBinary(H_value);

    public static byte[] Sj6;
    public static byte[] Sj14;
    public static byte[] Sj22;
    public static byte[] Sj5;
    public static byte[] Sj13;
    public static byte[] Sj21;
    public static byte[] Sj4;
    public static byte[] Sj12;
    public static byte[] Sj20;
    public static byte[] Sj3;
    public static byte[] Sj11;
    public static byte[] Sj19;
    public static byte[] Sj2;
    public static byte[] Sj10;
    public static byte[] Sj18;
    public static byte[] Sj1;
    public static byte[] Sj9;
    public static byte[] Sj17;
    public static byte[] Sj ;
    public static byte[] Sj8;
    public static byte[] Sj16;
    public static byte[] Sj7 ;
    public static byte[] Sj15;
    public static byte[] Sj23;

    private static int T;
    private static int flag;
    private static long length;
    public static int N=0;
    public static String getHash(){
        RandomAccessFile file = null;
        long point = 0;
        long current;
        try {
            // System.out.println(S_value);
            file = new RandomAccessFile("D:/Sec.mp4", "rw");
            length = file.length();
            point = ((int)(length/256)*256);
            while (file.getFilePointer()!= length) {
                if(file.getFilePointer() == point){
                    flag = file.read(X_bytes = new byte[(int)(length-file.getFilePointer())]);
                    ++ N;
                    madeMultiple();
                }
                else {
                    flag = file.read(X_bytes);
                    System.out.println(X_bytes);
                    ++N;
                }
                S_value= ArrayUtils.addAll(X_bytes,Arrays.copyOfRange(Arrays.copyOf(S_value,1536), 512 ,1536));
                S_value=Bash_f(S_value);
            }
        }catch(FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        return String.valueOf(S_value);

    }




    public static void madeMultiple(){
        T  = (X_bytes.length%(1536-256*4));

        X_bytes = ArrayUtils.addAll(X_bytes,DatatypeConverter.parseHexBinary("01000000"));

        for(int i = 0; i < ((1536-256*4)-T-1);i++){

            X_bytes = Arrays.copyOf(X_bytes,256);
            System.out.println(X_bytes);
        }

    }
    //0-64 64-128 128-192 192-256 256-320 320-384  384-448 448-512 // 512-576 576-640 640-704 704-768 768-832 832-896 896-960 960-1024
    //


    public static byte[] Bash_f(byte [] S)
    {

        for(int i=0;i<=8;i++) {
            int m1 = 8, n1 = 53, m2 = 14, n2 = 1;
            int kol = 64 * i;
            byte[] S_j = Arrays.copyOfRange(S, 0 + kol, 64 + kol);
            byte[] S_j8 = Arrays.copyOfRange(S, 448 + kol, 512 + kol);
            byte[] S_j16 = Arrays.copyOfRange(S, 960 + kol, 1024 + kol);

                m1 = (m1 * 7) % 64;
                n1 = (n1 * 7) % 64;
                m2 = (m2 * 7) % 64;
                n2 = (n2 * 7) % 64;

                byte[] res1 = Bash_s(S_j, S_j8, S_j16, m1, n1, m2, n2).get("w0");
                byte[] res2 = Bash_s(S_j, S_j8, S_j16, m1, n1, m2, n2).get("w1");
                byte[] res3 = Bash_s(S_j, S_j8, S_j16, m1, n1, m2, n2).get("w2");

                if (i == 0) {
                     Sj = res1;
                     Sj8 = res2;
                     Sj16 = res3;
                }
                if (i == 1) {
                     Sj1 = res1;
                     Sj9 = res2;
                     Sj17 = res3;
                }
                    if (i == 2) {
                         Sj2 = res1;
                         Sj10 = res2;
                         Sj18 = res3;
                    }
                    if (i == 3) {
                        Sj3 = res1;
                        Sj11 = res2;
                        Sj19 = res3;
                    }
                    if (i == 4) {
                         Sj4 = res1;
                         Sj12 = res2;
                         Sj20 = res3;
                    }
                    if (i == 5) {
                         Sj5 = res1;
                         Sj13 = res2;
                         Sj21 = res3;
                    }
                    if (i == 6) {
                         Sj6 = res1;
                         Sj14 = res2;
                         Sj22 = res3;
                    }
                    if(i==7)
                    {
                        Sj7 = res1;
                        Sj15 = res2;
                        Sj23 = res3;
                    }
            }
        S=ArrayUtils.addAll(Sj15,Sj10);
        S=ArrayUtils.addAll(S,Sj9);
        S=ArrayUtils.addAll(S,Sj12);
        S=ArrayUtils.addAll(S,Sj11);
        S=ArrayUtils.addAll(S,Sj14);
        S=ArrayUtils.addAll(S,Sj13);
        S=ArrayUtils.addAll(S,Sj8);
        S=ArrayUtils.addAll(S,Sj17);
        S=ArrayUtils.addAll(S,Sj16);
        S=ArrayUtils.addAll(S,Sj19);
        S=ArrayUtils.addAll(S,Sj18);
        S=ArrayUtils.addAll(S,Sj21);
        S=ArrayUtils.addAll(S,Sj20);
        S=ArrayUtils.addAll(S,Sj23);
        S=ArrayUtils.addAll(S,Sj22);
        S=ArrayUtils.addAll(S,Sj6);
        S=ArrayUtils.addAll(S,Sj3);
        S=ArrayUtils.addAll(S,Sj);
        S=ArrayUtils.addAll(S,Sj5);
        S=ArrayUtils.addAll(S,Sj2);
        S=ArrayUtils.addAll(S,Sj7);
        S=ArrayUtils.addAll(S,Sj4);
        S=ArrayUtils.addAll(S,Sj1);
       // S=XOR(S,C);

        return S;
    }

    public byte[] Lo(byte[] S){
        T  = (X_bytes.length%512);
        S = ArrayUtils.addAll(X_bytes,Arrays.copyOfRange(S,512,1536));
        return null;
    }

    public static byte[] ShLo(byte[] u, byte[] d){

        byte[] result = new byte[u.length];

        for (int i = 0; i < u.length; i++) {
            result[i] = (byte) (u[i] << d[i]);
        }
        return result;
    }

    /*public long ShLo(long u, long d){
        return u >>= d;
    }*/

    public static  byte[] ShHi(byte[] u, byte[] d){

        byte[] result = new byte[u.length];

        for (int i = 0; i < u.length; i++) {
            result[i] = (byte) (u[i] >> d[i]);
        }
        return result;
    }

    /*public long ShHi(long u, long d){
        return u <<= d;
    }*/

    public static byte[] XOR(byte[] u1, byte[] u2){
        byte[] result = new byte[u1.length];

        for (int i = 0; i < u1.length; i++) {
            result[i] = (byte) (u1[i] ^ u2[i]);
        }
        return result;
    }


    public static byte[] OR(byte[] u1, byte[] u2){
        byte[] result = new byte[u1.length];

        for (int i = 0; i < u1.length; i++) {
            result[i] = (byte) (u1[i] | u2[i]);
        }
        return result;
    }


    public static byte[] AND(byte[] u1, byte[] u2){
        byte[] result = new byte[u1.length];

        for (int i = 0; i < u1.length; i++) {
            result[i] = (byte) (u1[i] & u2[i]);
        }
        return result;
    }

    private static byte[] XORWithOnes(byte[] array){
        byte[] result = new byte[array.length];
        int currentIndex;
        for (currentIndex = 0; currentIndex < array.length; currentIndex++){
            result[currentIndex] = (byte)(array[currentIndex] ^ (byte)Integer.parseInt("11111111", 2));
        }
        return result;
    }

    public static byte[] RotHi(byte[] u, byte[] w){
        return XOR(ShLo(u, w), ShHi(u, w));
    }
    
    public static Map<String, byte[]> Bash_s(byte[] w0, byte[] w1, byte[] w2, int m1, int m2, int n1, int n2) {
        byte[] T0,  T1, T2;
        T2 = RotHi(w0, DatatypeConverter.parseHexBinary(String.valueOf(m1)));
        w0 = XOR(w1, w2);
        T1 = XOR(w1, RotHi(w0, DatatypeConverter.parseHexBinary(String.valueOf(n1))));
        w1 = (XOR(T1, T2));
        w2 = XOR(RotHi(w2, DatatypeConverter.parseHexBinary(String.valueOf(m2))), RotHi(T1, DatatypeConverter.parseHexBinary(String.valueOf(n2))));
        T1 = OR(w0, w2);
        T2 = AND(w0, w1);
        T0 = XORWithOnes(w2);
        T0 = OR(T0, w1);
        w0 = XOR(w0, T0);
        w1 = XOR(w1, T1);
        w2 = XOR(w2, T2);
        Map<String, byte[]> result = new HashMap<>();
        result.put("w0", w0);
        result.put("w1", w1);
        result.put("w2", w2);

        return result;
    }

}
