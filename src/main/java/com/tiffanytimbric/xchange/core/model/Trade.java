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

import java.io.Serial;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "trade")
public class Trade implements Serializable, Cloneable {

    @Serial
    private static final long serialVersionUID = 448879814131468699L;

    @Id
    private UUID id;
    private UUID compositeId;
    @JoinColumn(
            name = "item_one_id",
            nullable = false,
            referencedColumnName = "id"
    )
    @JsonProperty("item_one_id")
    private UUID itemOneId;
    @JoinColumn(
            name = "item_two_id",
            nullable = false,
            referencedColumnName = "id"
    )
    @JsonProperty("item_two_id")
    private UUID itemTwoId;
    private String state = "Proposed";
    private String dataItem;

    public Trade() {
    }

    public Trade(
            @NonNull final UUID id,
            @NonNull final UUID compositeId,
            @NonNull final UUID itemOneId,
            @NonNull final UUID itemTwoId,
            @NonNull final String state,
            @NonNull final String dataItem
    ) {
        this.id = id;
        this.compositeId = compositeId;
        this.itemOneId = itemOneId;
        this.itemTwoId = itemTwoId;
        this.state = state;
        this.dataItem = dataItem;
    }

    @Nullable
    public UUID getId() {
        return id;
    }

    @NonNull
    public Optional<UUID> idOpt() {
        return Optional.ofNullable(id);
    }

    @Nullable
    public UUID getCompositeId() {
        return compositeId;
    }

    @NonNull
    public Optional<UUID> compositeIdOpt() {
        return Optional.ofNullable(compositeId);
    }

    public void setCompositeId(
            @NonNull final UUID compositeId
    ) {
        this.compositeId = compositeId;
    }

    @Nullable
    public UUID getItemOneId() {
        return itemOneId;
    }

    @NonNull
    public Optional<UUID> itemOneIdOpt() {
        return Optional.ofNullable(itemOneId);
    }

    public void setItemOneId(@NonNull final UUID itemOneId) {
        this.itemOneId = itemOneId;
    }

    @Nullable
    public UUID getItemTwoId() {
        return itemTwoId;
    }

    @NonNull
    public Optional<UUID> itemTwoIdOpt() {
        return Optional.ofNullable(itemTwoId);
    }

    public void setItemTwoId(@NonNull final UUID itemTwoId) {
        this.itemTwoId = itemTwoId;
    }

    @NonNull
    public Optional<UUID> nameOpt() {
        return Optional.ofNullable(id);
    }

    public void setId(@NonNull final UUID name) {
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
                .append(this.compositeId, rhs.compositeId)
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
                .append("compositeId", compositeId)
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
