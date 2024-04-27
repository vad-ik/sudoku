package Nodes;

public class NodeBool extends Node{

    NodeName changeNode;
    public NodeBool( Node left, Node right, Node up, Node down, NodeName changeNode) {

        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
        this.changeNode=changeNode;
    }
    public NodeName getChangeNode(){
        return changeNode;
    }

}
