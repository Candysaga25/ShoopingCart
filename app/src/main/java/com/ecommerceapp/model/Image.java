
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
public class Image implements Parcelable {
    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("product_id")
    @Expose
    private long productId;
    @SerializedName("position")
    @Expose
    private int position;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("src")
    @Expose
    private String src;
    @SerializedName("variant_ids")
    @Expose
    private List<Object> variantIds = new ArrayList<Object>();

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
     * The productId
     */
    public long getProductId() {
        return productId;
    }

    /**
     *
     * @param productId
     * The product_id
     */
    public void setProductId(long productId) {
        this.productId = productId;
    }

    /**
     *
     * @return
     * The position
     */
    public int getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     *
     * @return
     * The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     *
     * @param createdAt
     * The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     *
     * @return
     * The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     *
     * @param updatedAt
     * The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     *
     * @return
     * The src
     */
    public String getSrc() {
        return src;
    }

    /**
     *
     * @param src
     * The src
     */
    public void setSrc(String src) {
        this.src = src;
    }

    /**
     *
     * @return
     * The variantIds
     */
    public List<Object> getVariantIds() {
        return variantIds;
    }

    /**
     *
     * @param variantIds
     * The variant_ids
     */
    public void setVariantIds(List<Object> variantIds) {
        this.variantIds = variantIds;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.productId);
        dest.writeInt(this.position);
        dest.writeString(this.createdAt);
        dest.writeString(this.updatedAt);
        dest.writeString(this.src);
        dest.writeList(this.variantIds);
    }

    public Image() {
    }

    protected Image(Parcel in) {
        this.id = in.readLong();
        this.productId = in.readLong();
        this.position = in.readInt();
        this.createdAt = in.readString();
        this.updatedAt = in.readString();
        this.src = in.readString();
        this.variantIds = new ArrayList<Object>();
        in.readList(this.variantIds, Object.class.getClassLoader());
    }

    public static final Parcelable.Creator<Image> CREATOR = new Parcelable.Creator<Image>() {
        @Override
        public Image createFromParcel(Parcel source) {
            return new Image(source);
        }

        @Override
        public Image[] newArray(int size) {
            return new Image[size];
        }
    };
}