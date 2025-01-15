package org.vaadin.walkingskeleton.generator;

// TODO add domain primitives for groupId, artifactId and basePackage that include input validation
public record ProjectConfiguration(String groupId,
                                   String artifactId,
                                   String basePackage,
                                   UIFramework uiFramework) {
}
