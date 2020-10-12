package game;

import java.util.Random;

public class BoardCreator {

    private static Random rand = new Random();

    private static <T> void swap(T[] a, int i, int j){
        T temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    private static <T> void shuffle(T[] arr) {
        int length = arr.length;
        for ( int i = length; i > 0; i-- ){
            int randInd = rand.nextInt(i);
            swap(arr, randInd, i - 1);
        }
    }

    public static int[][] generateBoard(){
        Integer[] arr = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        shuffle(arr);
        int[][] board = new int[3][3];
        for (int i = 0; i < 9; i++) {
            board[i/3][i%3] = arr[i];
        }
        return board;
    }

}
