package org.example.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scope {

    public Scope() {
        scopeData = new HashMap<>();
        parent = null;
        children = new ArrayList<>();
    }

    public HashMap<String, ScopeEntry> scopeData;
    public Scope parent;

    public void setParent(Scope parent) {
        this.parent = parent;

        // T: for debug purpose only
        parent.children.add(this);
    }

    // T: for debug purpose only
    public List<Scope> children;
}
