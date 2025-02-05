package com.sec.internal.omanetapi.nms.data;

import com.google.gson.annotations.SerializedName;
import com.sec.internal.constants.ims.servicemodules.volte2.CallConstants;

/* loaded from: classes.dex */
public class GeoLocation {

    @SerializedName("circle")
    public Circle mCircle;

    @SerializedName("label")
    public String mLabel;

    @SerializedName("timeOffset")
    public String mTimeOffset;

    @SerializedName("timestamp")
    public String mTimestamp;

    @SerializedName("until")
    public String mUntil;

    public static class Circle {

        @SerializedName(CallConstants.ComposerData.LATITUDE)
        public Double mLatitude;

        @SerializedName(CallConstants.ComposerData.LONGITUDE)
        public Double mLongitude;

        @SerializedName(CallConstants.ComposerData.RADIUS)
        public float mRadius;

        public Circle(Double d, Double d2, float f) {
            this.mLatitude = d;
            this.mLongitude = d2;
            this.mRadius = f;
        }
    }

    public GeoLocation(String str, String str2, String str3, String str4, Circle circle) {
        this.mLabel = str;
        this.mTimestamp = str2;
        this.mUntil = str3;
        this.mTimeOffset = str4;
        this.mCircle = circle;
    }
}
