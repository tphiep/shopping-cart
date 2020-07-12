package com.example.products.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.FIELD)
public class Quantity {

    public static final Quantity ZERO = new Quantity(0);

    private long amount;

    public Quantity() {}

    public Quantity(long amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);

    }

    public long getAmount() {
        return amount;
    }

    public Quantity add(Quantity other) {
        return new Quantity(amount + other.amount);
    }
    public Quantity subtract(Quantity other) {
        return new Quantity(amount - other.amount);
    }

    public boolean isGreaterThanOrEqual(Quantity other) {
        return amount >= other.getAmount();
    }
}
