package org.example.tree;

import org.example.Visitor;

import java.util.ArrayList;
import java.util.List;

public class TempNode {

    public static class TempAggregateNode extends Node{

        public TempAggregateNode(Node node) {
            children = new ArrayList<>();
            children.add(node);
        }

        public TempAggregateNode() {
            children = new ArrayList<>();
        }

        public TempAggregateNode(TempAggregateNode tempAggregateNode, Node nodeToAdd, boolean reverse) {
            children = tempAggregateNode.children;
            if(reverse == false) {
                children.add(nodeToAdd);
            }
            else {
                children.add(0, nodeToAdd);
            }
        }

        @Override
        public Object accept(Visitor v) {
            return null;
        }

        public List<Node> children;
    }
}
