package ast;

public class TypeScope {
    private final Type ty;
    private final Scope scope;

    public enum Scope {
        Global,
        Param,
        Local
    }

    public TypeScope(Type ty, Scope scope) {
        this.ty = ty;
        this.scope = scope;
    }

    public Type getTy() { return this.ty; }
    public Scope getScope() { return this.scope; }
}
