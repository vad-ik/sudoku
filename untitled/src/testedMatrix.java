import Nodes.NodeBool;
import Nodes.NodeName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class testedMatrix {
    int[][] sudocu;

    ArrayList<NodeName> headArray;
    ArrayList<NodeName> chanceArray;

    public testedMatrix(int sizex,int sizey,Scanner scanner) {

        sudocu = new int[sizex][sizey];
        for (int j = 0; j < sizey; j++) {
        for (int i = 0; i < sizex; i++) {
                sudocu[i][j] = scanner.nextInt();
             }
        }
        for (int i = 0; i < sudocu[0].length; i++) {
            for (int j = 0; j <sudocu.length ; j++) {
                System.out.print(sudocu[j][i]+" ");
            }
            System.out.println();
        }
        this.headArray = new ArrayList<>();
        this.chanceArray = new ArrayList<>();
        for (int i = 0; i < sizey; i++) {
            chanceArray.add(new NodeName("A" + i));
            for (int j = 0; j < sizex; j++) {
                if (i == 0) {
                    headArray.add(new NodeName("" + j));
                }
                if (sudocu[j][i] == 1) {
                    chanceArray.get(chanceArray.size()-1).getRightmost().setRight(
                            new NodeBool(chanceArray.get(chanceArray.size() - 1).getRightmost(), null,
                                    headArray.get(j).getLower(), null, chanceArray.get(chanceArray.size()-1)));
                    headArray.get(j).getLower().setDown(chanceArray.get(chanceArray.size() - 1).getRightmost());

                }
            }
        }
      //  DancingLinks.say(headArray);
       new DancingLinks(headArray);

    }
}
/*
12 12
0 0 0 1 0 0 1 1 0 0 0 0
0 0 0 0 0 0 0 1 0 0 1 1
0 0 0 0 0 0 1 0 0 1 1 0
1 1 0 1 0 0 0 0 0 0 0 0
0 0 0 0 0 1 1 0 0 0 1 0
0 0 0 0 1 1 0 0 1 0 0 0
0 0 1 0 0 1 1 0 0 0 0 0
0 0 1 0 0 0 1 1 0 0 0 0
1 1 1 0 0 0 0 0 0 0 0 0
0 0 0 0 1 0 0 0 1 1 0 0
0 0 0 0 0 0 1 1 0 0 0 1
0 0 0 0 0 0 1 0 0 0 1 1




7 6

0 0 1 0 1 1 0
1 0 0 1 0 0 1
0 1 1 0 0 1 0
1 0 0 1 0 0 0
0 1 0 0 0 0 1
0 0 0 1 1 0 1
A0
A1
A4


7 6
1	0	0	1	0	0	1
1	0	0	1	0	0	0
0	0	0	1	1	0	1
0	0	1	0	1	1	0
0	1	1	0	0	1	1
0	1	0	0	0	0	1
 */