
package com.ecommerceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Sagar Shedge on 29/11/16.
 */
public class Variants implements Parcelable {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("sku")
    @Expose
    private String sku;
    @SerializedName("compare_at_price")
    @Expose
    private String compareAtPrice;
    @SerializedName("weight")
    @Expose
    private long weight;
    @SerializedName("weight_unit")
    @Expose
    private String weightUnit;

    /**
     *
     * @return
     * The id
     */
    public long getId() {
        return id;
    }

    /**
     *
     * @param id
     * The id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     *
     * @return
     * The price
     */
    public String getPrice() {
        return price;
    }

    /**
     *
     * @param price
     * The price
     */
    public void setPrice(String price) {
        this.price = price;
    }

    /**
     *
     * @return
     * The sku
     */
    public String getSku() {
        return sku;
    }

    /**
     *
     * @param sku
     * The sku
     */
    public void setSku(String sku) {
        this.sku = sku;
    }

    /**
     *
     * @return
     * The compareAtPrice
     */
    public String getCompareAtPrice() {
        return compareAtPrice;
    }

    /**
     *
     * @param compareAtPrice
     * The compare_at_price
     */
    public void setCompareAtPrice(String compareAtPrice) {
        this.compareAtPrice = compareAtPrice;
    }

    /**
     *
     * @return
     * The weight
     */
    public long getWeight() {
        return weight;
    }

    /**
     *
     * @param weight
     * The weight
     */
    public void setWeight(long weight) {
        this.weight = weight;
    }

    /**
     *
     * @return
     * The weightUnit
     */
    public String getWeightUnit() {
        return weightUnit;
    }

    /**
     *
     * @param weightUnit
     * The weight_unit
     */
    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.price);
        dest.writeString(this.sku);
        dest.writeString(this.compareAtPrice);
        dest.writeLong(this.weight);
        dest.writeString(this.weightUnit);
    }

    public Variants() {
    }

    protected Variants(Parcel in) {
        this.id = in.readLong();
        this.price = in.readString();
        this.sku = in.readString();
        this.compareAtPrice = in.readString();
        this.weight = in.readLong();
        this.weightUnit = in.readString();
    }

    public static final Parcelable.Creator<Variants> CREATOR = new Parcelable.Creator<Variants>() {
        @Override
        public Variants createFromParcel(Parcel source) {
            return new Variants(source);
        }

        @Override
        public Variants[] newArray(int size) {
            return new Variants[size];
        }
    };
}