/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m9.UF2.Processosifils.Activitat3;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

/**
 *
 * @author Alumne
 */
public class MaximTask extends RecursiveTask<Short> {

    private static final int LLINDAR = 10000000;
    private short[] arr;
    private int inici, fi;
    
    // Posem el comptador
    private static int comptador;

    public MaximTask(short[] arr, int inici, int fi) {
        this.arr = arr;
        this.inici = inici;
        this.fi = fi;
    }

    private short getMaxSeq() {
        short max = arr[inici];
        for (int i = inici + 1; i < fi; i++) {
            if (arr[i] > max) {
                max = arr[i];
            }
        }
        
// Sumem el comptador i mostrem els resultats
        comptador++;
        System.out.println("Comptador " + comptador + " Inici " + inici + " Fi " + fi);
        
        return max;
    }

    private short getMaxReq() {
        MaximTask task1;

        MaximTask task2;
        int mig = (inici + fi) / 2 + 1;
        task1 = new MaximTask(arr, inici, mig);

        task1.fork();
        task2 = new MaximTask(arr, mig, fi);

        task2.fork();
        
        // Sumem el comptador i mostrem els resultats
        comptador++;
        System.out.println("Comptador " + comptador + " Inici " + inici + " Fi " + fi);
        return (short) Math.max(task1.join(), task2.join());
    }

    @Override
    protected Short compute() {
        if (fi - inici <= LLINDAR) {
            return getMaxSeq();
        } else {
            return getMaxReq();
        }
    }

    public static void main(String[] args) {
        short[] data = createArray(100000000);
        // Mira el número de processadors
        System.out.println("Inici càlcul");
        ForkJoinPool pool = new ForkJoinPool();
        int inici = 0;
        int fi = data.length;
        MaximTask tasca = new MaximTask(data, inici, fi);
        long time = System.currentTimeMillis();

        // crida la tasca i espera que es completin
        int result1 = pool.invoke(tasca);

        // màxim
        int result = tasca.join();
        System.out.println("Temps utilitzat:" + (System.currentTimeMillis() - time));
        System.out.println("Màxim es " + result);// + " Comptador=" + comptador);
    }

    private static short[] createArray(int size) {
        short[] ret = new short[size];
        for (int i = 0; i < size; i++) {
            ret[i] = (short) (1000 * Math.random());
            if (i == ((short) (size * 0.9))) {
                ret[i] = 1005;
            }
        }
        return ret;
    }
}
