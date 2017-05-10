
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
public class Option implements Parcelable {

    @SerializedName("id")
    @Expose
    private long id;
    @SerializedName("product_id")
    @Expose
    private long productId;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("position")
    @Expose
    private long position;
    @SerializedName("values")
    @Expose
    private List<String> values = new ArrayList<String>();

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
     * The name
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @param name
     * The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     *
     * @return
     * The position
     */
    public long getPosition() {
        return position;
    }

    /**
     *
     * @param position
     * The position
     */
    public void setPosition(long position) {
        this.position = position;
    }

    /**
     *
     * @return
     * The values
     */
    public List<String> getValues() {
        return values;
    }

    /**
     *
     * @param values
     * The values
     */
    public void setValues(List<String> values) {
        this.values = values;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.id);
        dest.writeLong(this.productId);
        dest.writeString(this.name);
        dest.writeLong(this.position);
        dest.writeStringList(this.values);
    }

    public Option() {
    }

    protected Option(Parcel in) {
        this.id = in.readLong();
        this.productId = in.readLong();
        this.name = in.readString();
        this.position = in.readLong();
        this.values = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Option> CREATOR = new Parcelable.Creator<Option>() {
        @Override
        public Option createFromParcel(Parcel source) {
            return new Option(source);
        }

        @Override
        public Option[] newArray(int size) {
            return new Option[size];
        }
    };
}