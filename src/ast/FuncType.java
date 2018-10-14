package ast;

import java.util.List;

public class FuncType implements Type {
    private List<Declaration> params;
    private Type retType;

    public FuncType(List<Declaration> params, Type retType) {
        this.params = params;
        this.retType = retType;
    }

    public Type getRetType() { return this.retType; }

    public List<Declaration> getParams() { return this.params; }
}
