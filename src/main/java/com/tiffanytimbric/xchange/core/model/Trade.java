package com.tiffanytimbric.xchange.core.model;

import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.Optional;

@Entity
@Table(name = "trade")
public class Trade implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private Item itemOne;
    @ManyToOne
    private Item itemTwo;
    private String state;

    public Trade() {
    }

    public Trade(
            @NonNull final Long id,
            @NonNull final Item itemOne,
            @NonNull final Item itemTwo,
            @NonNull final String state
    ) {
        this.id = id;
        this.itemOne = itemOne;
        this.itemTwo = itemTwo;
        this.state = state;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public Item getItemOne() {
        return itemOne;
    }

    @NonNull
    public Optional<Item> itemOneOpt() {
        return Optional.ofNullable(itemOne);
    }

    public void setItemOne(@NonNull final Item itemOne) {
        this.itemOne = itemOne;
    }

    @Nullable
    public Item getItemTwo() {
        return itemTwo;
    }

    @NonNull
    public Optional<Item> itemTwoOpt() {
        return Optional.ofNullable(itemTwo);
    }

    public void setItemTwo(@NonNull final Item itemTwo) {
        this.itemTwo = itemTwo;
    }

    @NonNull
    public Optional<Long> nameOpt() {
        return Optional.ofNullable(id);
    }

    public void setId(@NonNull final Long name) {
        this.id = name;
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
        Trade rhs = (Trade) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .append(this.itemOne, rhs.itemOne)
                .append(this.itemTwo, rhs.itemTwo)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .append(itemOne)
                .append(itemTwo)
                .toHashCode();
    }

    @Override
    @NonNull
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("itemOne", itemOne)
                .append("itemTwo", itemTwo)
                .toString();
    }

    @Nullable
    public String getState() {
        return state;
    }

    @NonNull
    public Optional<String> stateOpt() {
        if (StringUtils.isBlank(state)) {
            return Optional.empty();
        }

        return Optional.of(state);
    }

    public void setState(@NonNull final String state) {
        this.state = state;
    }
}
