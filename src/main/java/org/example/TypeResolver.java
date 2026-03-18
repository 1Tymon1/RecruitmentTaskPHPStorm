package org.example;

import java.util.ArrayList;
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

        for (DocTag tag : tags) {
            String partsOfTag[] = tag.getValue().split(" ");

            String types[] = partsOfTag[0].split("\\|");
            if (partsOfTag.length >= 2) {
                String name = partsOfTag[1];

                // Handling mismatched variable names
                if (!name.equals(variable.getName())) {
                    continue;
                }
            }

            // Standard case handling
            if (types.length == 1) {
                return typeFactory.createPhpType(types[0]);
            }
            else { // Union case handling
                List<PhpType> typesForUnion = new ArrayList<PhpType>();
                for (String type : types) {
                    typesForUnion.add(typeFactory.createPhpType(type));
                }
                return typeFactory.createUnionType(typesForUnion);
            }
        }

        // Handling no matching variable names
        return typeFactory.createPhpType("mixed");
    }
}
