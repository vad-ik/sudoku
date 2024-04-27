import Nodes.Node;
import Nodes.NodeBool;
import Nodes.NodeName;

import java.util.ArrayList;

public class ConditionMatrix {
    int[][] sudocu;
    int size;
    ArrayList<NodeName> headArray;
    ArrayList<NodeName> chanceArray;

    public ConditionMatrix(int[][] sudocu, int size) {
        this.sudocu = sudocu;
        this.size = size;
        headArray = new ArrayList<>();
        chanceArray = new ArrayList<>();

        //нужно добавить в массив несколько ограничений


        onlyOneOnCell();//в каждой кл только 1 цира

        onlyOneOnColumn();// в каждом столбце только по 1 символу каждого типа
        onlyOneOnString();// в каждой строке только по 1 символу каждого типа
        onlyOneOnCube();// в каждом квадрате только по 1 символу каждого типа
        //System.out.println(headArray);
       // sayMatr();
        System.out.println("матрица условий построена");

        long time = System.nanoTime();
        DancingLinks dancing = new DancingLinks(headArray);
        System.out.println(System.nanoTime() - time);
    }

    void onlyOneOnCell() {//в каждой кл только 1 цифра
        for (int i = 1; i <= size; i++) {
            for (int j = 1; j <= size; j++) {
                headArray.add(new NodeName("x=" + i + "y=" + j));
                if (sudocu[i - 1][j - 1] != 0) {
                    chanceArray.add(new NodeName("x=" + i + "y=" + j + "значение=" + sudocu[i - 1][j - 1]));
                    NodeName now = chanceArray.get(chanceArray.size() - 1);
                    NodeBool chance = new NodeBool(now, null, headArray.get(headArray.size() - 1), null, now);

                    now.setRight(chance);
                    headArray.get(headArray.size() - 1).setDown(chance);
                } else {
                    for (int k = 1; k <= size; k++) {
                        if (!(containColumn(i,k, size)||containString(j,k, size)||containCub((int)((i-1)/Math.sqrt(size)),(int)((j-1)/Math.sqrt(size)),k))) {
                            chanceArray.add(new NodeName("x=" + i + "y=" + j + "значение=" + k));
                            Node now = chanceArray.get(chanceArray.size() - 1).getLeftmost();
                            NodeBool chance = new NodeBool(now, null, headArray.get(headArray.size() - 1).getLower(), null, chanceArray.get(chanceArray.size() - 1));
                            now.setRight(chance);
                            headArray.get(headArray.size() - 1).getLower().setDown(chance);
                        }
                    }
                }

            }
        }
    }

    void onlyOneOnColumn() {// в каждом столбце только по 1 символу каждого типа
        for (int i = 1; i <= size; i++) {//перебираем все столбцы
            for (int j = 1; j <= size; j++) {//перебираем значения в этих столбцах
                headArray.add(new NodeName("y=" + i + "значение=" + j));
                Node up = headArray.get(headArray.size() - 1);
                if (!containColumn(i, j, size)) {
                    for (int k = 1; k <= size; k++) {
                        if (!(containString(k,j, size)||containCub((int)((i-1)/Math.sqrt(size)),(int)((k-1)/Math.sqrt(size)),j))) {
                            up = addNode(i, k, j, up);
                        }
                    }
                } else {
                    int k;
                    for (k = 1; sudocu[i - 1][k - 1] != j; k++) {
                    }
                    up = addNode(i, k, j, up);
                }
            }
        }
    }

    void onlyOneOnString() {// в каждой строке только по 1 символу каждого типа
        for (int i = 1; i <= size; i++) {//перебираем все строки
            for (int j = 1; j <= size; j++) {//перебираем значения в этих строк
                headArray.add(new NodeName("x=" + i + "значение=" + j));
                Node up = headArray.get(headArray.size() - 1);
                if (!containString(i, j, size)) {
                    for (int k = 1; k <= size; k++) {
                        if (!(containColumn(k,j, size)||containCub((int)((k-1)/Math.sqrt(size)),(int)((i-1)/Math.sqrt(size)),j))) {
                            up = addNode(k, i, j, up);
                        }
                    }
                } else {
                    int k;
                    for (k = 1; sudocu[k - 1][i - 1] != j; k++) {
                    }
                    up = addNode(k, i, j, up);
                }
            }
        }
    }

    void onlyOneOnCube() {// в каждом квадрате только по 1 символу каждого типа
        for (int i = 0; i < Math.sqrt(size); i++) {//перебор всех подквадратов
            for (int j = 0; j < Math.sqrt(size); j++) {
                for (int k = 1; k <= size; k++) {//перебор значений
                    headArray.add(new NodeName("квадрат[" + (i + 1) + "][" + (j + 1) + "]значение=" + k));
                    Node up = headArray.get(headArray.size() - 1);
                    if (!containCub(i, j, k)) {
                        for (int l = (int) (i * Math.sqrt(size) + 1); l <= (i + 1) * Math.sqrt(size); l++) {
                            for (int m = (int) (j * Math.sqrt(size) + 1); m <= (j + 1) * Math.sqrt(size); m++) {
                               if (!(containColumn(l,k, size)||containString(m,k, size))) {
                                   up = addNode(l, m, k, up);
                               }

                            }
                        }
                    } else {
                        for (int l = (int) (i * Math.sqrt(size) + 1); l <= (i + 1) * Math.sqrt(size); l++) {
                            for (int m = (int) (j * Math.sqrt(size) + 1); m <= (j + 1) * Math.sqrt(size); m++) {
                                if (sudocu[l - 1][m - 1] == k) {
                                    up = addNode(l, m, k, up);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    Node addNode(int x, int y, int value, Node up) {
        if (sudocu[x - 1][y - 1] == value || sudocu[x - 1][y - 1] == 0) {//значение в ячейке известно и равно j
            NodeName nodeChance = chanceArray.get(chanceArray.indexOf(new NodeName("x=" + x + "y=" + y + "значение=" + value)));
            Node nodeL = nodeChance.getRightmost();
            NodeBool thisN = new NodeBool(nodeL, null, up, null, nodeChance);
            nodeL.setRight(thisN);
            up.setDown(thisN);
            up = thisN;
        }
        return up;
    }

    void say() {
        for (NodeName nodeName : headArray) {
            System.out.print(nodeName.name + "  |");
            NodeBool thisN = (NodeBool) nodeName.getDown();
            while (thisN != null) {
                System.out.print(thisN.getChangeNode().name + "  _");

                thisN = (NodeBool) thisN.getDown();
            }

            System.out.println();
        }
    }

    void sayr() {
        for (NodeName nodeName : chanceArray) {
            System.out.print(nodeName.name + "  |");
            NodeBool thisN = (NodeBool) nodeName.getRight();
            while (thisN != null) {
                System.out.print(((NodeName) thisN.getUper()).name + "  /");

                thisN = (NodeBool) thisN.getRight();
            }

            System.out.println();
        }
    }

    boolean containString(int y, int num,int size) {
        boolean answer = false;
        for (int i = 1; i <= size; i++) {
            if (sudocu[i - 1][y - 1] == num) {
                answer = true;
            }
        }
        return answer;
    }

    boolean containColumn(int x, int num,int size) {
        boolean answer = false;
        for (int i = 1; i <= size; i++) {
            if (sudocu[x - 1][i - 1] == num) {
                answer = true;
            }
        }
        return answer;
    }

    boolean containCub(int xCub, int yCub, int num) {
        boolean answer = false;
        for (int l = (int) (xCub * Math.sqrt(size) + 1); l <= (xCub + 1) * Math.sqrt(size); l++) {
            for (int m = (int) (yCub * Math.sqrt(size) + 1); m <= (yCub + 1) * Math.sqrt(size); m++) {
                if (sudocu[l - 1][m - 1] == num) {
                    answer = true;
                }
            }
        }
        return answer;
    }

    void chek() {
        for (int i = 0; i < headArray.size(); i++) {
            if (!headArray.get(i).getLower().getUper().equals(headArray.get(i))) {
                System.out.println("error");
            }
        }
        for (int i = 0; i < chanceArray.size(); i++) {
            if (!chanceArray.get(i).getRightmost().getLeftmost().equals(chanceArray.get(i))) {
                System.out.println("error");
            }
        }
    }

    void sayMatr() {
        int[][] matr = new int[headArray.size()][chanceArray.size()];
        for (int i = 0; i < headArray.size(); i++) {
            Node node = headArray.get(i).getDown();
            while (node != null) {
                matr[i][chanceArray.indexOf(node.getLeftmost())] = 1;
                node = node.getDown();
            }

        }
        System.out.print("n ");
        for (int i = 0; i < headArray.size(); i++) {
            System.out.print(headArray.get(i).name + " ");
        }
        System.out.println();
        for (int i = 0; i < matr[0].length; i++) {
            System.out.print(chanceArray.get(i).name + " ");
            for (int j = 0; j < matr.length; j++) {
                System.out.print(matr[j][i] + " ");
            }
            System.out.println();
        }
    }
}
