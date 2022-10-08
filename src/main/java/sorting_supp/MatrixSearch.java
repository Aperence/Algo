package sorting_supp;

/**
 * @author Aperence
 */

public class MatrixSearch {

    /**
     * search in a nxm matrix where element (i, j) where elements of row and columns are sorted
     *
     * such that   (i-1, j) < (i, j) < (i+1, j)
     * and         (i, j-1) < (i, j) < (i, j+1)
     *
     * considering (i,j) is the value at index i,j in the matrix
     *
     * @return  the index of the value => if element is at (k, l) return k*m + l
     *          -1 if element is not in the matrix
     *
     */

    // return i*(nb_columns) + j
    public static int findIter(int[][] matrix, int value){
        int i = 0;
        int j = matrix[0].length -1;
        while (i<matrix.length && j >=0){
            if (matrix[i][j]==value) return i*matrix[0].length + j;
            if (matrix[i][j] < value) i++;
            if (matrix[i][j] > value) j--;
        }
        return -1;
    }

    public static int findRec(int[][] matrix, int value, int i, int j){
        if (i >= matrix.length || j < 0) return -1;
        if (matrix[i][j] == value) return j +i*matrix[0].length;
        else if (matrix[i][j] < value) return findRec(matrix, value, i+1, j);
        else return findRec(matrix, value, i, j-1);
    }

    public static int findRec(int[][] matrix, int value){
        return findRec(matrix, value, 0, matrix[0].length-1);
    }

    public static void printIndex(int index, int nb_columns){
        if (index == -1){
            System.out.println(-1);
            return;
        }
        System.out.println("mat[" + index/nb_columns+ "][" + index%nb_columns+ "]");
    }

    public static void main(String[] args) {
        int [][] mat = {
                {1,3,5},
                {2,4,6},
                {7,8,9}
        };

        printIndex(findRec(mat, 8), mat[0].length);
        printIndex(findIter(mat, 6), mat[0].length);
    }
}
