package org.example;

import java.util.List;

public class UnionType {
    private List<PhpType> types;

    public UnionType(List<PhpType> types) {
        this.types = types;
    }
    public List<PhpType> getTypes() {
        return types;
    }
}
