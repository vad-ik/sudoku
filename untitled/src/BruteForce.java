public class BruteForce {
    int[][] sudocu;

    void bruteforce(int[][] sudocu) {
        this.sudocu = sudocu;
        if (recursion()) {
           printSudocu();
        }
    }
void printSudocu(){
    for (int i = 0; i < sudocu.length; i++) {
        if (i % ((int) Math.sqrt(sudocu.length)) == 0) {
            System.out.println();
        }
        for (int j = 0; j < sudocu.length; j++) {
            if (j % ((int) Math.sqrt(sudocu.length)) == 0) {
                System.out.print(" ");
            }
            System.out.print(sudocu[j][i] + " ");
            for (int k = 0; k < ((sudocu.length + "").length() - (sudocu[j][i] + "").length()); k++) {
                System.out.print(" ");
            }
        }
        System.out.println();
    }
}
    boolean recursion() {
        int x = 0;
        int y = 0;
        boolean stop = false;
        for (int i = 0; i < sudocu.length && !stop; i++) {
            for (int j = 0; j < sudocu.length && !stop; j++) {
                if (sudocu[i][j] == 0) {
                    x = i;
                    y = j;
                    stop = true;
                }
            }
        }
        if (stop==false){
            return true;
        }
        for (int i = 1; i <= sudocu.length; i++) {



            if (cenPut(x, y, i)) {

                sudocu[x][y] = i;
                if (x == sudocu.length - 1 && y == sudocu.length - 1) {
                    return true;
                }
                if (recursion()) {
                    return true;
                }
            }
        }
        sudocu[x][y] = 0;
        return false;
    }

    boolean cenPut(int x, int y, int i) {
        boolean answer = true;
        for (int j = 0; j < sudocu.length; j++) {
            if (sudocu[x][j] == i) {
                answer = false;
            }
        }
        for (int j = 0; j < sudocu.length; j++) {
            if (sudocu[j][y] == i) {
                answer = false;
            }
        }
        int cube = (int) Math.sqrt(sudocu.length);
        int xCube = x - x % cube;
        int yCube = y - y % cube;
        for (int j = 0; j < cube; j++) {
            for (int k = 0; k < cube; k++) {
                if (sudocu[xCube + j][yCube + k] == i) {
                    answer = false;
                }
            }
        }
        return answer;
    }
}
