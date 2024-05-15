package com.tiffanytimbric.xchange.core.model;

import jakarta.persistence.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "item_tag_xref",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    private Set<Tag> tags;
    private long owner;
    private int price;

    @Nullable
    public Long getId() {
        return id;
    }

    @NonNull
    public Optional<Long> idOpt() {
        return Optional.ofNullable(id);
    }

    public void setId(@NonNull final Long id) {
        this.id = id;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NonNull
    public Optional<String> nameOpt() {
        return Optional.ofNullable(name);
    }

    public void setName(@NonNull final String name) {
        this.name = name;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public Optional<String> descriptionOpt() {
        return Optional.ofNullable(description);
    }

    public void setDescription(@NonNull final String description) {
        this.description = description;
    }

    @Nullable
    public Set<Tag> getTags() {
        return tags;
    }

    @NonNull
    public Set<Tag> tagsOpt() {
        if (CollectionUtils.isEmpty(tags)) {
            return new HashSet<>();
        }

        return tags;
    }

    public void setTags(@NonNull final Set<Tag> tags) {
        this.tags = tags;
    }

    public long getOwner() {
        return owner;
    }

    public void setOwner(long owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        Item rhs = (Item) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.name, rhs.name)
                .append(this.description, rhs.description)
                .append(this.tags, rhs.tags)
                .append(this.owner, rhs.owner)
                .append(this.price, rhs.price)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    @NonNull
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .append("tags", tags)
                .append("owner", owner)
                .append("price", price)
                .toString();
    }
}
