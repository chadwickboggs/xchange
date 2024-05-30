package com.tiffanytimbric.xchange.core.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Entity
@Table(name = "user")
public class User implements Serializable {

    @Id
    private UUID id;
    private String name;
    private String photoUrl;
    private int balance;
/*
    @OneToMany
    private Set<Item> items;
    @OneToMany
    private Set<Want> wants;
*/

    public User() {
    }

    public User(
            @NonNull final UUID id,
            @NonNull final String name,
            @NonNull final String photoUrl,
            final int balance/*,
            @NonNull final Set<Item> items,
            @NonNull final Set<Want> wants*/
    ) {
        this.id = id;
        this.name = name;
        this.photoUrl = photoUrl;
        this.balance = balance;
//        this.items = items;
//        this.wants = wants;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(final int balance) {
        this.balance = balance;
    }

/*
    @Nullable
    public Set<Item> getItems() {
        return items;
    }

    @NonNull
    public Set<Item> itemsOpt() {
        if (CollectionUtils.isEmpty(items)) {
            return Set.of();
        }

        return items;
    }

    public void setItems(@NonNull final Set<Item> items) {
        this.items = items;
    }

    @Nullable
    public Set<Want> getWants() {
        return wants;
    }

    @NonNull
    public Set<Want> wantsOpt() {
        if (CollectionUtils.isEmpty(wants)) {
            return Set.of();
        }

        return wants;
    }

    public void setWants(@NonNull final Set<Want> wants) {
        this.wants = wants;
    }
*/

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
        User rhs = (User) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.name, rhs.name)
                .append(this.photoUrl, rhs.photoUrl)
                .append(this.balance, rhs.balance)
//                .append(this.items, rhs.items)
//                .append(this.wants, rhs.wants)
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
                .append("photoUrl", photoUrl)
                .append("balance", balance)
//                .append("items", items)
//                .append("wants", wants)
                .toString();
    }
}
