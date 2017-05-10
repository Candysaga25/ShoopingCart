
package com.ecommerceapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Sagar Shedge on 29/11/16.
 */
public class Product implements Parcelable {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("vendor")
    @Expose
    private String vendor;
    @SerializedName("product_type")
    @Expose
    private String productType;
    @SerializedName("tags")
    @Expose
    private String tags;
    @SerializedName("images")
    @Expose
    private List<Image> images = new ArrayList<Image>();
    @SerializedName("options")
    @Expose
    private List<Option> options = new ArrayList<Option>();
    @SerializedName("variants")
    @Expose
    private Variants variants;

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
     * The title
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @param title
     * The title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     *
     * @return
     * The vendor
     */
    public String getVendor() {
        return vendor;
    }

    /**
     *
     * @param vendor
     * The vendor
     */
    public void setVendor(String vendor) {
        this.vendor = vendor;
    }

    /**
     *
     * @return
     * The productType
     */
    public String getProductType() {
        return productType;
    }

    /**
     *
     * @param productType
     * The product_type
     */
    public void setProductType(String productType) {
        this.productType = productType;
    }

    /**
     *
     * @return
     * The tags
     */
    public String getTags() {
        return tags;
    }

    /**
     *
     * @param tags
     * The tags
     */
    public void setTags(String tags) {
        this.tags = tags;
    }

    /**
     *
     * @return
     * The images
     */
    public List<Image> getImages() {
        return images;
    }

    /**
     *
     * @param images
     * The images
     */
    public void setImages(List<Image> images) {
        this.images = images;
    }

    /**
     *
     * @return
     * The options
     */
    public List<Option> getOptions() {
        return options;
    }

    /**
     *
     * @param options
     * The options
     */
    public void setOptions(List<Option> options) {
        this.options = options;
    }

    /**
     *
     * @return
     * The variants
     */
    public Variants getVariants() {
        return variants;
    }

    /**
     *
     * @param variants
     * The variants
     */
    public void setVariants(Variants variants) {
        this.variants = variants;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeString(this.title);
        dest.writeString(this.vendor);
        dest.writeString(this.productType);
        dest.writeString(this.tags);
        dest.writeList(this.images);
        dest.writeList(this.options);
        dest.writeParcelable(this.variants, flags);
    }

    public Product() {
    }

    protected Product(Parcel in) {
        this.id = in.readLong();
        this.title = in.readString();
        this.vendor = in.readString();
        this.productType = in.readString();
        this.tags = in.readString();
        this.images = new ArrayList<Image>();
        in.readList(this.images, Image.class.getClassLoader());
        this.options = new ArrayList<Option>();
        in.readList(this.options, Option.class.getClassLoader());
        this.variants = in.readParcelable(Variants.class.getClassLoader());
    }

    public static final Parcelable.Creator<Product> CREATOR = new Parcelable.Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel source) {
            return new Product(source);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };
}
