package com.vida.pet.vidapet.Core.Domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
public abstract class Identifier<T extends Identifier<T>> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    public Long getId() {
        return id;
    }

    public boolean sameIdentityAs(final T that) {
        return that != null && this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        Identifier<?> other = (Identifier<?>) obj;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected void _checkIdentity(final Identifier<?> entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalStateException("Comparison identity missing in entity: " + entity);
        }
    }
}
