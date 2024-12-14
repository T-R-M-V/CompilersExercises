package org.example.tree;

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

        public TempAggregateNode(TempAggregateNode tempAggregateNode, Node nodeToAdd) {
            children = tempAggregateNode.children;
            children.add(nodeToAdd);
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

        public List<VarDeclOpNode> varDeclOpNodes;
        public List<DefDeclOpNode> defDeclOpNodes;
    }
}
