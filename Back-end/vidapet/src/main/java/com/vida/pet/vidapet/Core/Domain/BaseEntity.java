package com.vida.pet.vidapet.Core.Domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity<T extends BaseEntity<T>> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    public Long getId() {
        return id;
    }

    public boolean sameIdentityAs(final T that) {
        return that != null && this.getId() != null && this.getId().equals(that.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        BaseEntity<?> entity = (BaseEntity<?>) o;
        return id != null && id.equals(entity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    protected void _checkIdentity(final BaseEntity<?> entity) {
        if (entity == null || entity.getId() == null) {
            throw new IllegalStateException("Comparison identity missing in entity: " + entity);
        }
    }

}
