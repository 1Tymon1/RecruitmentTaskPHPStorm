import org.example.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class TypeResolverTest {

    private TypeResolver resolver;

    private static PhpVariable var(String name, String... tagValues) {
        List<DocTag> tags = Arrays.stream(tagValues)
                .map(DocTag::new)
                .toList();
        PhpDocBlock docBlock = new PhpDocBlock(Map.of("@var", tags));
        return new PhpVariable(name, docBlock);
    }

    private static PhpVariable varNoDoc(String name) {
        return new PhpVariable(name, null);
    }

    @BeforeEach
    void setUp() {
        resolver = new TypeResolver(new TypeFactory());
    }

    @Test
    void standardType_noVariableName() {
        // /** @var User */ for $user  →  User
        assertEquals("User", resolver.inferTypeFromDoc(var("$user", "User")).getType());
    }

    @Test
    void unionType() {
        // /** @var string|int */ for $id  →  string|int
        PhpType result = resolver.inferTypeFromDoc(var("$id", "string|int"));
        assertInstanceOf(UnionType.class, result);
        assertEquals("string|int", result.getType());
    }

    @Test
    void namedTag_matching() {
        // /** @var Logger $log */ for $log  →  Logger
        assertEquals("Logger", resolver.inferTypeFromDoc(var("$log", "Logger $log")).getType());
    }

    @Test
    void namedTag_mismatch_returnsMixed() {
        // /** @var Admin $adm */ for $guest  →  mixed
        assertEquals("mixed", resolver.inferTypeFromDoc(var("$guest", "Admin $adm")).getType());
    }

    @Test
    void multipleTags_picksMatchingVariable() {
        // @var int $id and @var string $name; inspecting $name  →  string
        assertEquals("string", resolver.inferTypeFromDoc(var("$name", "int $id", "string $name")).getType());
    }

    @Test
    void noDocBlock_returnsMixed() {
        assertEquals("mixed", resolver.inferTypeFromDoc(varNoDoc("$x")).getType());
    }

    @Test
    void emptyDocBlock_returnsMixed() {
        PhpDocBlock emptyBlock = new PhpDocBlock(Map.of());
        PhpVariable variable = new PhpVariable("$x", emptyBlock);
        assertEquals("mixed", resolver.inferTypeFromDoc(variable).getType());
    }
}
