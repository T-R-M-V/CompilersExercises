package org.example.scope;

import java.util.Objects;

public class ScopeKey {

    public ScopeKey(String lexem, ScopeEntry.Kind kind) {
        this.lexem = lexem;
        this.kind = kind;
    }

    public int hashCode() {
        return Objects.hash(lexem, kind);
    }

    public boolean equals(Object obj) {
        if(obj instanceof ScopeKey) {
            ScopeKey other = (ScopeKey)obj;
            return this.lexem.equals(other.lexem) && this.kind == other.kind;
        }

        return false;
    }

    public String lexem;
    public ScopeEntry.Kind kind;
}
