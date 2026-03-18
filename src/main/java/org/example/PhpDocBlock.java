package org.example;

import java.util.List;
import java.util.Map;

public class PhpDocBlock {
    private Map<String, List<DocTag>> tags;

    public PhpDocBlock(Map<String, List<DocTag>> tags) {
        this.tags = tags;
    }
    public List<DocTag> getTagsByName(String tagName) {
        return tags.get(tagName);
    }

}
