// Edge.java
package de.uni_mannheim.informatik.dws.jrdf2vec.util;

import java.util.Objects;

public class Edge {
    private String subject;
    private String predicate;
    private String object;

    public Edge(String subject, String predicate, String object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    // Getters
    public String getSubject() {
        return subject;
    }

    public String getPredicate() {
        return predicate;
    }

    public String getObject() {
        return object;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        // if (o == null || getClass() != o.getClass()) return false;
        if (!(o instanceof Edge)) return false;

        Edge other = (Edge) o;

        // if (!subject.equals(edge.subject)) return false;
        // if (!predicate.equals(edge.predicate)) return false;
        // return object.equals(edge.object);
        return Objects.equals(subject, other.subject) &&
               Objects.equals(predicate, other.predicate) &&
               Objects.equals(object, other.object);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, predicate, object);
    }
}
