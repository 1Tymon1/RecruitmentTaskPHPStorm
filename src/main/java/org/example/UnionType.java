package org.example;

import java.util.List;
import java.util.stream.Collectors;

public class UnionType extends PhpType {
    private List<PhpType> types;

    public UnionType(List<PhpType> types) {
        super(types.stream().map(PhpType::getType).collect(Collectors.joining("|")));
        this.types = types;
    }
    public List<PhpType> getTypes() {
        return types;
    }
}
