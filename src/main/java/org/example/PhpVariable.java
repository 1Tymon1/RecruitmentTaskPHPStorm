package org.example;

public class PhpVariable {
    private String name;
    private PhpDocBlock phpDocBlock;

    public PhpVariable(String name, PhpDocBlock phpDocBlock) {
        this.name = name;
        this.phpDocBlock = phpDocBlock;
    }
    public String getName() {
        return name;
    }
    public PhpDocBlock getPhpDocBlock() {
        return phpDocBlock;
    }
}
