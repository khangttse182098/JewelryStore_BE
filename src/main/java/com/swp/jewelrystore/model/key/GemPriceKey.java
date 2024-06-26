package com.swp.jewelrystore.model.key;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class GemPriceKey implements Serializable {
    private String origin;
    private String color;
    private String clarity;
    private String cut;
    private Double carat_weight_from;
    private Double carat_weight_to;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GemPriceKey that = (GemPriceKey) o;
        return Objects.equals(origin, that.origin) &&
                Objects.equals(color, that.color) &&
                Objects.equals(clarity, that.clarity) &&
                Objects.equals(cut, that.cut) &&
                Objects.equals(carat_weight_from, that.carat_weight_from) &&
                Objects.equals(carat_weight_to, that.carat_weight_to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(origin, color, clarity, cut, carat_weight_from, carat_weight_to);
    }
}
