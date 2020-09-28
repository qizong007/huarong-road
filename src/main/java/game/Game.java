package game;

import java.util.*;

/**
 * 模拟玩的过程
 */
public class Game {

    public String op = null;
    public int swap[] = new int[2];

    private int[][] shuffle(Node node,int[] swap){
        int[][] board = new int[3][3];
        int offset=0;
        for (int i = 0; i < node.boardstring.length(); i++) {
            char c = node.boardstring.charAt(i);
            if(c >= '0' && c <= '9'){
                board[offset/3][offset%3] = c - '0';
                offset++;
            }
        }
        int swap1 = swap[0]-1;
        int swap2 = swap[1]-1;
        int t = board[swap1/3][swap1%3];
        board[swap1/3][swap1%3] = board[swap2/3][swap2%3];
        board[swap2/3][swap2%3] = t;
        //outputBoard(board);
        return board;
    }

    /**
     * 输出游戏状态
     */
    private void outputBoard(int[][] board){
        System.out.println("------------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]+" ");
            }
            System.out.println();
        }
        System.out.println("------------");
    }

    /**
     * 模拟游戏（简易swap）
     * @param board
     * @param targetInt
     * @param step
     * @param swap
     * @return 步数
     */
    public int slidingPuzzle(int[][] board,int[][] targetInt,int step,int[] swap,boolean again) {
        System.out.println("在第"+step+"步交换"+swap[0]+"和"+swap[1]);
        System.out.println("初始状态：");
        outputBoard(board);
        int R = board.length, C = board[0].length;
        this.swap = swap;
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

        String target = Arrays.deepToString(targetInt);

        while (!queue.isEmpty()) {

            Node node = queue.remove();

            // FIXME:swap
            readyToShuffle:{
                if(node.depth+1 == step){
                    System.out.println("（准备交换）当前状态："+node.boardstring);
                    Game newGame = new Game();
                    int[][] swapBoard = shuffle(node,swap);
                    outputBoard(swapBoard);
                    int temp = newGame.slidingPuzzle(swapBoard,targetInt);
                    // 交换后无解
                    if(temp == -1){
                        System.out.println("交换后无解");
                        if(again){
                            Node newNode = new Node(swapBoard,node.zero_r,node.zero_c,node.depth,node.operations);
                            int[] recordSwap = new int[2];
                            Game afterRoundGame = null;
                            trap:{
                                for(int i=1;i<=8;i++){
                                    for(int j=i+1;j<=9;j++){
                                        afterRoundGame = null;// avoid memory leak
                                        afterRoundGame = new Game();
                                        int[][] shuffledBord = shuffle(newNode,new int[]{i,j});
                                        outputBoard(shuffledBord);
                                        temp = afterRoundGame.slidingPuzzle(shuffledBord,targetInt);
                                        if(temp != -1){
                                            recordSwap[0] = i;
                                            recordSwap[1] = j;
                                            break trap;
                                        }
                                    }
                                }
                            }
                            // trap 出来
                            if(temp != -1){
                                this.swap = recordSwap;
                                System.out.println(newNode.operations);
                                System.out.println(afterRoundGame.op);
                                this.op = newNode.operations + afterRoundGame.op;
                                System.err.println("成功啦啦啦啦！");
                            }
                            return newNode.depth + temp;
                        }else{
                            break readyToShuffle;
                        }
                    }else{
                        System.out.println("交换后竟然有解！");
                        System.out.println(node.operations);
                        System.out.println(newGame.op);
                        this.op = node.operations + newGame.op;
                        return node.depth + temp;
                    }
                }
            }

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

    /**
     * 模拟游戏（无swap）
     * @param board 游戏开局
     * @param targetInt 目标
     * @return 步数
     */
    public int slidingPuzzle(int[][] board,int[][] targetInt) {
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
                this.op = node.operations.toString();
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

    /**
     * 初始版本
     * @param board 游戏开局
     * @return 步数
     */
    public int slidingPuzzle(int[][] board) {
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