package Nodes;

public abstract class Node {

    Node left;
    Node right;
    Node up;
    Node down;
    Node changeNode;

    public void restoreString() {
        left.setRight(this);
        right.setLeft(this);
    }

    public void restoreColumn() {
        if (up!=null){up.setDown(this);}
        if (down!=null){down.setUp(this);}
    }

    public void delFromString() {
        left.setRight(right);
        right.setLeft(left);
    }

    public void delFromColumn() {
        if (up != null) {
            up.setDown(down);
        }
        if (down != null) {
            down.setUp(up);
        }
    }

    public void setLeft(Node node) {
        left = node;
    }


    public void setRight(Node node) {
        right = node;
    }

    public void setUp(Node node) {
        up = node;
    }


    public void setDown(Node node) {
        down = node;
    }


    public int getDeep() {
        if (down == null) {
            return 0;
        } else {
            return (down.getDeep() + 1);
        }
    }

    public int getNumRight() {
        if (right == null) {
            return 0;
        } else {
            return (right.getNumRight() + 1);
        }
    }

    public Node getLeftmost() {
        if (left == null) {
            return this;
        } else {
            return left.getLeftmost();
        }
    }

    public Node getRightmost() {
        if (right == null) {
            return this;
        } else {
            return right.getRightmost();
        }
    }

    public Node getLower() {
        if (down == null) {
            return this;
        } else {
            return down.getLower();
        }
    }

    public Node getUper() {
        if (up == null) {
            return this;
        } else {
            return up.getUper();
        }
    }


    public void delAllColumnFromString() {
        delFromString();
        if (down != null) {
            down.delAllColumnFromString();
        }
    }

    public void delAllStringFromColumn() {
        delFromColumn();
        if (right != null) {
            right.delAllStringFromColumn();
        }
    }

    public void restoreAllColumnFromString() {
        restoreString();
        if (down != null) {
            down.restoreAllColumnFromString();
        }
    }

    public void restoreAllStringFromColumn() {
        restoreColumn();
        if (right != null) {
            right.restoreAllStringFromColumn();
        }
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public Node getUp() {
        return up;
    }

    public Node getDown() {
        return down;
    }

}
