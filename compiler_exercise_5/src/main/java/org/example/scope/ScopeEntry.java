package org.example.scope;

import org.example.Type;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ScopeEntry {

    public enum Kind {
        Proc, // Procedure or Function
        Var, // Variable
    }

    public static class TypeProcParameter {

        public TypeProcParameter(Type type, boolean ref) {
            this.type = type;
            this.ref = ref;
        }

        public boolean ref;
        public Type type;
    }

    public static ScopeEntry createProcScope(Type returnType, TypeProcParameter... parameters) {
        ScopeEntry entry = new ScopeEntry();

        entry.kind = Kind.Proc;
        entry.parameters = Arrays.asList(parameters);
        entry.returnType = returnType;

        return entry;
    }

    public static ScopeEntry createProcScope(Type returnType, List<TypeProcParameter> parameters) {
        ScopeEntry entry = new ScopeEntry();

        entry.kind = Kind.Proc;
        entry.parameters = parameters;
        entry.returnType = returnType;

        return entry;
    }

    public static ScopeEntry createVarScope(Type varType) {
        ScopeEntry entry = new ScopeEntry();

        entry.kind = Kind.Var;
        entry.varType = varType;

        return entry;
    }

    public Kind kind;

    public Type varType;

    public List<TypeProcParameter> parameters;
    public Type returnType;
}
