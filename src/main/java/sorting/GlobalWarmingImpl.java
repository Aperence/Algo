package sorting;

import javax.swing.text.html.HTMLDocument;
import java.util.*;

/**
 * Author Pierre Schaus
 *
 * Assume the following 5x5 matrix that represent a grid surface:
 * int [][] tab = new int[][] {{1,3,3,1,3},
 *                             {4,2,2,4,5},
 *                             {4,4,1,4,2},
 *                             {1,4,2,3,6},
 *                             {1,1,1,6,3}};
 *
 * Each entry in the matrix represents an altitude point at the corresponding grid coordinate.
 * The goal is to implement a GlobalWarmingImpl class that extends the GlobalWarming abstract class described below.
 *
 * Given a global water level, all positions in the matrix with a value <= the water level are flooded and therefore unsafe.
 * So, assuming the water level is 3, all safe points are highlighted between parenthesis:
 *
 *   1 , 3 , 3 , 1 , 3
 *  (4), 2 , 2 ,(4),(5)
 *  (4),(4), 1 ,(4), 2
 *   1 ,(4), 2 , 3 ,(6)
 *   1 , 1 , 1 ,(6), 3}
 *
 * The method you need to implement is nbSafePoints
 * - calculating the number of safe points for a given water level
 *
 * Complete the code below. Pay attention to the expected time complexity of each method.
 * To meet this time complexity, you need to do some pre-computation in the constructor.
 * Feel free to create internal classes in GlobalWarmingImpl to make your implementation easier.
 * Feel free to use any method or data structure available in the Java language and API.
 */

abstract class GlobalWarming {


    protected final int[][] altitude;

    /**
     * @param altitude is a n x n matrix of int values representing altitudes (positive or negative)
     */
    public GlobalWarming(int[][] altitude) {
        this.altitude = altitude;
    }

    /**
     *
     * @param waterLevel
     * @return the number of entries in altitude matrix that would be above
     *         the specified waterLevel.
     *         Warning: this is not the waterLevel given in the constructor/
     */
    public abstract int nbSafePoints(int waterLevel);

}


public class GlobalWarmingImpl extends GlobalWarming {

    class PQIndex{
        int[] pq;
        int[] cumulateSum;

        PQIndex(){
            pq = new int[16];
        }

        void resize(int size){
            pq = Arrays.copyOf(pq, size);
        }

        void add(int level){
            if (level >= pq.length) resize(2*level);
            pq[level]++;
        }

        int[] cumulateSum(){
            int[] ret = new int[pq.length];
            int sum = 0;
            for (int i = 0; i < ret.length; i++) {
                sum += pq[i];
                ret[i] = sum;
            }
            return ret;
        }

        int getCumulateLevel(int level){
            if (cumulateSum == null) cumulateSum = cumulateSum();
            if (level < 0) return cumulateSum[cumulateSum.length + level];
            return cumulateSum[level];
        }

        int[] getCumulateSum(){
            if (cumulateSum == null) cumulateSum = cumulateSum();
            return cumulateSum;
        }
    }

    PQIndex pq;


    public GlobalWarmingImpl(int[][] altitude) {
        super(altitude);
        pq = new PQIndex();
        for (int[] i: altitude) {
            for (int j: i) {
                pq.add(j);
            }
        }
    }

    /**
     * Returns the number of safe points given a water level
     *
     * @param waterLevel the level of water
     */
    public int nbSafePoints(int waterLevel) {
        if (waterLevel < 0 ) return pq.getCumulateLevel(-1);
        if (waterLevel >= pq.getCumulateSum().length) return 0;
        return pq.getCumulateLevel(-1) - pq.getCumulateLevel(waterLevel);
    }

    public static void main(String[] args) {
        int [][] tab = new int[][] {{1,3,3,1,3},
                                    {4,2,2,4,5},
                                    {4,4,1,4,2},
                                    {1,4,2,3,6},
                                    {1,1,1,6,3}};
        GlobalWarmingImpl gw = new GlobalWarmingImpl(tab);
        System.out.println(Arrays.toString(gw.pq.getCumulateSum()));
        System.out.println(gw.nbSafePoints(3));
    }

}
