package org.example.scope;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Scope {

    public Scope() {
        scopeData = new HashMap<>();
        parent = null;
    }

    public HashMap<String, ScopeEntry> scopeData;
    public Scope parent;
}
