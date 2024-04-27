import Nodes.Node;
import Nodes.NodeBool;
import Nodes.NodeName;

import java.util.ArrayList;
import java.util.Collections;

public class DancingLinks {

    public DancingLinks(ArrayList<NodeName> headArray) {

        System.out.println(findColumn(headArray, new StringBuilder()));
    }

    boolean findColumn(ArrayList<NodeName> headArray, StringBuilder str) {

        boolean answer = false;
        Collections.sort(headArray);

        //  say(headArray);
        for (int i = 0; i < headArray.size() && !answer; i++) {//перебор всех столбцев отсортированных по возрастанию.
            answer = findString(headArray, i, str);
            if (!answer&&headArray.get(i).getDeep()==1){//при нынешнем разложении это условие покрывает только ода строка, не являющаяся ответом
                break;
            }
        }
        return answer;
    }

    boolean findString(ArrayList<NodeName> headArray, int column, StringBuilder str) {
        boolean answer = false;
        for (Node node = headArray.get(column).getDown(); node != null && !answer; node = node.getDown()) {
            StringBuilder newStr = new StringBuilder();
            newStr.append(str);
            newStr.append("\n");
            newStr.append(((NodeBool) node).getChangeNode().name);
            //   System.out.println(((NodeBool) node).getChangeNode().name);
            answer = tryDelNode(new ArrayList<>(headArray), node, newStr);

        }
        return answer;
    }

    boolean tryDelNode(ArrayList<NodeName> headArray, Node node, StringBuilder str) {

        ArrayList<NodeName> delChange = new ArrayList<>();
        for (Node nodeTmp = ((NodeBool) node).getChangeNode().getRight(); nodeTmp != null; nodeTmp = nodeTmp.getRight()) {
            headArray.remove(nodeTmp.getUper());
            for (Node nodeTmp2 = (nodeTmp).getUper().getDown(); nodeTmp2 != null; nodeTmp2 = nodeTmp2.getDown()) {
                if (!delChange.contains(((NodeBool) nodeTmp2).getChangeNode())) {
                    delChange.add(((NodeBool) nodeTmp2).getChangeNode());
                }
            }
        }
        for (NodeName nodeName : delChange) {
            nodeName.delAllStringFromColumn();
        }

        Collections.sort(headArray);



        if (headArray.size() == 0) {

            getMatr(str);
            return true;
        } else if (headArray.get(0).getDeep() == 0) {
            //System.out.println("restoreAllStringFromColumn");
            for (NodeName nodeName : delChange) {
                nodeName.restoreAllStringFromColumn();
            }
            return false;
        } else {
            boolean answer = findColumn(headArray, str);

            if (!answer) {
                // System.out.println("restoreUp");

                for (NodeName nodeName : delChange) {
                    nodeName.restoreAllStringFromColumn();
                }
            }
            return answer;
        }
    }


    static void say(ArrayList<NodeName> headArray) {
        for (NodeName nodeName : headArray) {
            System.out.print(nodeName.name + "  |");
            NodeBool thisN = (NodeBool) nodeName.getDown();
            while (thisN != null) {
                System.out.print(thisN.getChangeNode().name + "  /");

                thisN = (NodeBool) thisN.getDown();
            }

            System.out.println();
        }
    }

    void sayr(ArrayList<NodeName> chanceArray) {
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

    void getMatr(StringBuilder str) {

        String[] cell = str.toString().split("\n");
        int[][] sudocu = new int[(int) Math.sqrt(cell.length)][(int) Math.sqrt(cell.length)];
        for (int i = 0; i < cell.length; i++) {
            if(cell[i].length()!=0) {
                int x = Integer.parseInt(cell[i].split("x=")[1].split("y=")[0]);
                int y = Integer.parseInt(cell[i].split("y=")[1].split("значение=")[0]);
                int inf = Integer.parseInt(cell[i].split("значение=")[1]);
                sudocu[x-1][y-1] = inf;
            }
        }
        for (int i = 0; i < sudocu[0].length; i++) {
            if (i%((int)Math.sqrt((int) Math.sqrt(cell.length)))==0){
                System.out.println();
            } for (int j = 0; j < sudocu.length; j++) {
                if (j%((int)Math.sqrt((int) Math.sqrt(cell.length)))==0){
                    System.out.print(" ");
                } System.out.print(sudocu[j][i] + " ");
                for (int k = 0; k < ((((int) Math.sqrt(cell.length)) + "").length() - (sudocu[j][i] + "").length()); k++) {
                    System.out.print(" ");
                }
            }

            System.out.println();
        }
    }
}

