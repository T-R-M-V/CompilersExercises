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

    public static class TempDeclsNode extends Node {

        public TempDeclsNode() {
            varDeclOpNodes = new ArrayList<>();
            defDeclOpNodes = new ArrayList<>();
        }

        public void addVarDeclOpNode(VarDeclOpNode varDeclOpNode) {
            varDeclOpNodes.add(varDeclOpNode);
        }

        public void addDeclOpNode(DefDeclOpNode defDeclOpNode) {
            defDeclOpNodes.add(defDeclOpNode);
        }

        @Override
        public Object accept(Visitor v) {
            return null;
        }

        public List<VarDeclOpNode> varDeclOpNodes;
        public List<DefDeclOpNode> defDeclOpNodes;
    }
}
