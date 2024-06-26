package com.tiffanytimbric.xchange.core.model;

import jakarta.persistence.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "item")
public class Want implements Serializable {

    @Serial
    private static final long serialVersionUID = -8376813313220647607L;

    @Id
    private UUID id;
    private String name;
    private String description;
    @ManyToOne(
            optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL
    )
    @JoinColumn(name = "type")
    private ItemType type;
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "want_tag_xref",
            joinColumns = @JoinColumn(name = "want_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    private Set<Tag> tags;
    private long user;
    private int price;

    public Want() {
    }

    public Want(
            @NonNull final UUID id,
            @NonNull final String name,
            @NonNull final String description,
            @NonNull final ItemType type,
            @NonNull final Set<Tag> tags,
            final long user,
            final int price
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.tags = tags;
        this.user = user;
        this.price = price;
    }

    @Nullable
    public UUID getId() {
        return id;
    }

    @NonNull
    public Optional<UUID> idOpt() {
        return Optional.ofNullable(id);
    }

    public void setId(@NonNull final UUID id) {
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
    public ItemType getType() {
        return type;
    }

    @NonNull
    public Optional<ItemType> typeOpt() {
        return Optional.ofNullable(type);
    }

    public void setType(@NonNull final ItemType type) {
        this.type = type;
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

    public long getUser() {
        return user;
    }

    public void setUser(long owner) {
        this.user = owner;
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
        Want rhs = (Want) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.name, rhs.name)
                .append(this.description, rhs.description)
                .append(this.type, rhs.type)
                .append(this.tags, rhs.tags)
                .append(this.user, rhs.user)
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
                .append("type", type)
                .append("tags", tags)
                .append("owner", user)
                .append("price", price)
                .toString();
    }
}
