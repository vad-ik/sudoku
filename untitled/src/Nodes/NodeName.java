package Nodes;

import java.util.Objects;

public class NodeName extends Node implements Comparable<NodeName> {
    public String name;

    public NodeName(String name) {
        this.name = name;
        this.left = null;
        this.right = null;
        this.up = null;
        this.down = null;
    }

    public NodeName(String name, Node left, Node right, Node up, Node down) {
        this.name = name;
        this.left = left;
        this.right = right;
        this.up = up;
        this.down = down;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeName nodeName = (NodeName) o;
        return Objects.equals(name, nodeName.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public int compareTo(NodeName o) {
        return this.getDeep()- o.getDeep() ;
    }

    @Override
    public String toString() {
        return "NodeName{" +
                "name='" + name + '\'' +
                '}';
    }
}