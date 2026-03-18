package org.example;

import java.util.List;

public class TypeResolver {
    private final TypeFactory typeFactory;

    public TypeResolver(TypeFactory typeFactory) {
        this.typeFactory = typeFactory;
    }

    public PhpType inferTypeFromDoc(PhpVariable variable) {

        // Fallback for no DocBlock
        if (variable.getPhpDocBlock() == null) {
            return typeFactory.createPhpType("mixed");
        }

        List<DocTag> tags = variable.getPhpDocBlock().getTagsByName("@var");

        //fallback for when no @var tag was found
        if (tags == null || tags.isEmpty()) {
            return typeFactory.createPhpType("mixed");
        }

        return typeFactory.createPhpType("placeholder");
    }
}
