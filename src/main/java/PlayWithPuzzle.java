import java.util.*;

/**
 * 模拟玩的过程
 */
public class PlayWithPuzzle {

    static String op="";

    public static int slidingPuzzle(int[][] board,int[][] targetInt) {
        int R = board.length, C = board[0].length;
        int sr = 0, sc = 0;
        search:
        for (sr = 0; sr < R; sr++)
            for (sc = 0; sc < C; sc++)
                if (board[sr][sc] == 0)
                    break search;
        // 下，上，右，左
        int[][] directions = new int[][]{{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        Queue<Node> queue = new ArrayDeque();
        Node start = new Node(board, sr, sc, 0,new StringBuilder(""));
        queue.add(start);

        Set<String> seen = new HashSet();
        seen.add(start.boardstring);

        //String target = Arrays.deepToString(new int[][]{{1,2,3}, {4,5,6},{7,8,0}});
        String target = Arrays.deepToString(targetInt);

        while (!queue.isEmpty()) {
            Node node = queue.remove();
            if (node.boardstring.equals(target)){
                op = node.operations.toString();
                return node.depth;
            }

            int index = 0;
            for (int[] di: directions) {
                int nei_r = di[0] + node.zero_r;
                int nei_c = di[1] + node.zero_c;

                // 操作：下，上，右，左
                if(di[0]==1 && di[1]==0){
                    if(index == 0){
                        node.operations.append('s');
                        index = node.operations.length();
                    }else{
                        node.operations.setCharAt(index-1,'s');
                    }
                }
                else if(di[0]==-1 && di[1]==0){
                    if(index == 0){
                        node.operations.append('w');
                        index = node.operations.length();
                    }else{
                        node.operations.setCharAt(index-1,'w');
                    }
                }
                else if(di[1]==1 && di[0]==0){
                    if(index == 0){
                        node.operations.append('d');
                        index = node.operations.length();
                    }else{
                        node.operations.setCharAt(index-1,'d');
                    }
                }
                else if(di[1]==-1 && di[0]==0){
                    if(index == 0){
                        node.operations.append('a');
                        index = node.operations.length();
                    }else{
                        node.operations.setCharAt(index-1,'a');
                    }
                }

                if ((Math.abs(nei_r - node.zero_r) + Math.abs(nei_c - node.zero_c) != 1) ||
                        nei_r < 0 || nei_r >= R || nei_c < 0 || nei_c >= C)
                    continue;

                int[][] newboard = new int[R][C];
                int t = 0;
                for (int[] row: node.board)
                    newboard[t++] = row.clone();
                newboard[node.zero_r][node.zero_c] = newboard[nei_r][nei_c];
                newboard[nei_r][nei_c] = 0;

                Node nei = new Node(newboard, nei_r, nei_c, node.depth+1,node.operations);
                if (seen.contains(nei.boardstring))
                    continue;
                queue.add(nei);
                seen.add(nei.boardstring);
            }
        }

        return -1;
    }

    public static int slidingPuzzle(int[][] board) {
        return slidingPuzzle(board,new int[][]{{1,2,3}, {4,5,6},{7,8,0}});
    }

    static class Node {

        int[][] board;
        String boardstring;
        int zero_r;
        int zero_c;
        int depth;
        StringBuilder operations;

        Node(int[][] B, int r, int c, int d,StringBuilder str) {
            board = B;
            boardstring = Arrays.deepToString(board);
            zero_r = r;
            zero_c = c;
            depth = d;
            operations = new StringBuilder(str);
        }

    }
}


