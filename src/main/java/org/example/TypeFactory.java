package org.example;

import java.util.List;

public class TypeFactory {
    public PhpType createPhpType(String typeName) {
        return new PhpType(typeName);
    }

    public UnionType createUnionType(List<PhpType> types) {
        return new UnionType(types);
    }
}
