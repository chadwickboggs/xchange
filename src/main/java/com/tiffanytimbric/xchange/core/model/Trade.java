package com.tiffanytimbric.xchange.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.*;
import org.apache.commons.lang3.StringUtils;
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
@Table(name = "trade")
public class Trade implements Serializable, Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JoinColumn(
            name = "item_one_id",
            nullable = false,
            referencedColumnName = "id"
    )
    @JsonProperty("item_one_id")
    private int itemOneId;
    @JoinColumn(
            name = "item_two_id",
            nullable = false,
            referencedColumnName = "id"
    )
    @JsonProperty("item_two_id")
    private int itemTwoId;
    private String state = "Proposed";
    private String dataItem;

    public Trade() {
    }

    public Trade(
            @NonNull final Long id,
            @NonNull final int itemOneId,
            @NonNull final int itemTwoId,
            @NonNull final String state,
            @NonNull final String dataItem
    ) {
        this.id = id;
        this.itemOneId = itemOneId;
        this.itemTwoId = itemTwoId;
        this.state = state;
        this.dataItem = dataItem;
    }

    @Nullable
    public Long getId() {
        return id;
    }

    @Nullable
    public int getItemOneId() {
        return itemOneId;
    }

    public void setItemOneId(@NonNull final int itemOneId) {
        this.itemOneId = itemOneId;
    }

    @Nullable
    public int getItemTwoId() {
        return itemTwoId;
    }

    public void setItemTwoId(@NonNull final int itemTwoId) {
        this.itemTwoId = itemTwoId;
    }

    @NonNull
    public Optional<Long> nameOpt() {
        return Optional.ofNullable(id);
    }

    public void setId(@NonNull final Long name) {
        this.id = name;
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

    @Nullable
    public String getDataItem() {
        return dataItem;
    }

    @NonNull
    public Optional<String> dataItemOpt() {
        if (StringUtils.isBlank(dataItem)) {
            return Optional.empty();
        }

        return Optional.of(dataItem);
    }

    public void setDataItem(
            @Nullable final String dataItem
    ) {
        this.dataItem = dataItem;
    }

    @NonNull
    public Set<String> dataItemSet() {
        return dataItemOpt()
                .map(dataItem -> {
                    try {
                        return new ObjectMapper().readValue(dataItem, HashSet.class);
                    }
                    catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                })
                .orElse(new HashSet<String>());
    }

    public void dataItemSet(@NonNull final Set<String> dataItemSet) {
        try {
            setDataItem(
                    new ObjectMapper().writeValueAsString(dataItemSet)
            );
        }
        catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
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
                .append(this.itemOneId, rhs.itemOneId)
                .append(this.itemTwoId, rhs.itemTwoId)
                .append(this.state, rhs.state)
                .append(this.dataItem, rhs.dataItem)
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
                .append("itemOne", itemOneId)
                .append("itemTwo", itemTwoId)
                .append("state", state)
                .append("dataItem", dataItem)
                .toString();
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }
}
