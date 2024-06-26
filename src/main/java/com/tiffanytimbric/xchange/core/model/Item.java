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

import static org.apache.commons.lang3.StringUtils.isBlank;

@Entity
@Table(name = "item")
public class Item implements Serializable {

    @Serial
    private static final long serialVersionUID = 3916133178124380235L;

    @Id
    private UUID id;
    private String name;
    private String description;
    private String photoUrl;
    @ManyToOne(
            optional = false, fetch = FetchType.EAGER, cascade = CascadeType.ALL
    )
    @JoinColumn(name = "type")
    private ItemType type;
    @ManyToMany(
            fetch = FetchType.EAGER
    )
    @JoinTable(
            name = "item_tag_xref",
            joinColumns = @JoinColumn(name = "item_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_name")
    )
    private Set<Tag> tags;
    private UUID owner;
    private int price;

    public Item() {
    }

    public Item(
            @NonNull final UUID id,
            @NonNull final String name,
            @NonNull final String description,
            @NonNull final String photoUrl,
            @NonNull final ItemType type,
            @NonNull final Set<Tag> tags,
            final UUID owner,
            final int price
    ) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photoUrl = photoUrl;
        this.type = type;
        this.tags = tags;
        this.owner = owner;
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
    public String getPhotoUrl() {
        return photoUrl;
    }

    @NonNull
    public Optional<String> photoUrlOpt() {
        if (isBlank(photoUrl)) {
            return Optional.empty();
        }

        return Optional.ofNullable(photoUrl);
    }

    public void setPhotoUrl(@NonNull final String photoUrl) {
        this.photoUrl = photoUrl;
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

    @Nullable
    public UUID getOwner() {
        return owner;
    }

    @NonNull
    public Optional<UUID> ownerOpt() {
        return Optional.ofNullable(owner);
    }

    public void setOwner(@NonNull final UUID owner) {
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
                .append(this.photoUrl, rhs.photoUrl)
                .append(this.type, rhs.type)
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
                .append("photoUrl", photoUrl)
                .append("type", type)
                .append("tags", tags)
                .append("owner", owner)
                .append("price", price)
                .toString();
    }
}
