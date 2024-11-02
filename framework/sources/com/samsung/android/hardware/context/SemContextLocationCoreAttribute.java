package com.samsung.android.hardware.context;

import android.hardware.scontext.SContextConstants;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/* loaded from: classes5.dex */
public class SemContextLocationCoreAttribute extends SemContextAttribute {
    public static final Parcelable.Creator<SemContextLocationCoreAttribute> CREATOR = new Parcelable.Creator<SemContextLocationCoreAttribute>() { // from class: com.samsung.android.hardware.context.SemContextLocationCoreAttribute.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextLocationCoreAttribute createFromParcel(Parcel in) {
            return new SemContextLocationCoreAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextLocationCoreAttribute[] newArray(int size) {
            return new SemContextLocationCoreAttribute[size];
        }
    };
    private static final String TAG = "SemContextLocationCoreAttribute";
    private int mAccuracy;
    private int mAction;
    private int mBatchingSize;
    private int mFenceId;
    private FusedBatchOption mFusedBatchOption;
    private double mLatitude;
    private Location mLocation;
    private double mLongitude;
    private int mMinDistance;
    private int mMinTime;
    private int mMode;
    private int mRadius;
    private int[] mRawData;
    private int mRequestId;
    private int mStatus;
    private int mSuccessGpsCnt;
    private long mTimeStamp;
    private int mTotalGpsCnt;

    /* synthetic */ SemContextLocationCoreAttribute(Parcel parcel, SemContextLocationCoreAttributeIA semContextLocationCoreAttributeIA) {
        this(parcel);
    }

    /* renamed from: com.samsung.android.hardware.context.SemContextLocationCoreAttribute$1 */
    /* loaded from: classes5.dex */
    class AnonymousClass1 implements Parcelable.Creator<SemContextLocationCoreAttribute> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public SemContextLocationCoreAttribute createFromParcel(Parcel in) {
            return new SemContextLocationCoreAttribute(in);
        }

        @Override // android.os.Parcelable.Creator
        public SemContextLocationCoreAttribute[] newArray(int size) {
            return new SemContextLocationCoreAttribute[size];
        }
    }

    public SemContextLocationCoreAttribute() {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        setAttribute();
    }

    private SemContextLocationCoreAttribute(Parcel src) {
        super(src);
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
    }

    public SemContextLocationCoreAttribute(int mode, int action) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, int fence_id, double latitude, double longitude, int radius, int total_gps_cnt, int success_gps_cnt) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        this.mFenceId = fence_id;
        this.mRadius = radius;
        this.mTotalGpsCnt = total_gps_cnt;
        this.mSuccessGpsCnt = success_gps_cnt;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, int val) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        if (mode == 0) {
            this.mFenceId = val;
        } else if (mode == 3) {
            if (action == 18) {
                this.mRequestId = val;
            } else if (action == 19) {
                this.mBatchingSize = val;
            }
        }
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, int fence_id, int radius, int status) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        this.mFenceId = fence_id;
        this.mRadius = radius;
        this.mStatus = status;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, int min_distance, int min_time) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        this.mMinDistance = min_distance;
        this.mMinTime = min_time;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, double latitude, double longitude, int accuracy, long timestamp) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        this.mLatitude = latitude;
        this.mLongitude = longitude;
        this.mAccuracy = accuracy;
        this.mTimeStamp = timestamp;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, int request_id, long period, int source, int flags, double max_power, float distance_thrs) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        this.mRequestId = request_id;
        this.mFusedBatchOption = new FusedBatchOption(period, source, flags, max_power, distance_thrs);
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, Location location) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        this.mLocation = location;
        setAttribute();
    }

    public SemContextLocationCoreAttribute(int mode, int action, int[] rawData) {
        this.mMode = -1;
        this.mAction = -1;
        this.mFenceId = 0;
        this.mRadius = 0;
        this.mStatus = 0;
        this.mTotalGpsCnt = 0;
        this.mSuccessGpsCnt = 0;
        this.mMinDistance = 0;
        this.mMinTime = 0;
        this.mAccuracy = 0;
        this.mTimeStamp = 0L;
        this.mLongitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mLatitude = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
        this.mRequestId = 0;
        this.mBatchingSize = 0;
        this.mRawData = null;
        this.mFusedBatchOption = null;
        this.mLocation = null;
        this.mMode = mode;
        this.mAction = action;
        int[] iArr = new int[rawData.length];
        this.mRawData = iArr;
        System.arraycopy(rawData, 0, iArr, 0, rawData.length);
        setAttribute();
    }

    @Override // com.samsung.android.hardware.context.SemContextAttribute
    public boolean checkAttribute() {
        int i = this.mMode;
        if (i < -1 || i > 3) {
            Log.d(TAG, "Mode value is wrong!!");
            return false;
        }
        if (i == 0) {
            int i2 = this.mAction;
            if ((i2 < -1 || i2 > 10) && i2 != 23) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
        } else if (i == 1) {
            int i3 = this.mAction;
            if (i3 < -1 || i3 > 14) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
        } else if (i == 3) {
            int i4 = this.mAction;
            if (i4 < 16 || i4 > 22) {
                Log.d(TAG, "Action value is wrong!!");
                return false;
            }
            if ((i4 == 16 || i4 == 17) && !this.mFusedBatchOption.isValid()) {
                Log.d(TAG, "FusedBatchOption is wrong");
                return false;
            }
        }
        if (this.mFenceId < 0) {
            Log.d(TAG, "FenceID is wrong!!");
            return false;
        }
        if (this.mRadius < 0) {
            Log.d(TAG, "Radius is wrong!!");
            return false;
        }
        if (this.mStatus < 0) {
            Log.d(TAG, "Status is wrong!1");
            return false;
        }
        if (this.mTotalGpsCnt < 0) {
            Log.d(TAG, "TotalGpsCount is wrong!!");
            return false;
        }
        if (this.mSuccessGpsCnt < 0) {
            Log.d(TAG, "Success gps count is wrong");
            return false;
        }
        if (this.mMinDistance < 0) {
            Log.d(TAG, "Minimum distance is wrong");
            return false;
        }
        if (this.mMinTime < 0) {
            Log.d(TAG, "Minimum time is wrong");
            return false;
        }
        if (this.mAccuracy < 0) {
            Log.d(TAG, "Accuracy is wrong");
            return false;
        }
        if (this.mTimeStamp < 0) {
            Log.d(TAG, "Timestamp is wrong");
            return false;
        }
        double d = this.mLongitude;
        if (d < -180.0d || d > 180.0d) {
            Log.d(TAG, "Longitude is wrong");
            return false;
        }
        double d2 = this.mLatitude;
        if (d2 < -90.0d || d2 > 90.0d) {
            Log.d(TAG, "Latitude is wrong");
            return false;
        }
        if (this.mRequestId < 0) {
            Log.d(TAG, "RequestId is wrong");
            return false;
        }
        if (this.mBatchingSize >= 0) {
            return true;
        }
        Log.d(TAG, "BatchingSize is wrong");
        return false;
    }

    private void setAttribute() {
        Bundle attribute = new Bundle();
        switch (this.mMode) {
            case 0:
                int i = this.mAction;
                if (i == 1) {
                    double[] doubleType = {this.mLatitude, this.mLongitude};
                    attribute.putIntArray("IntType", new int[]{this.mFenceId, this.mRadius, this.mTotalGpsCnt, this.mSuccessGpsCnt});
                    attribute.putDoubleArray("DoubleType", doubleType);
                    break;
                } else if (i == 2) {
                    attribute.putIntArray("IntType", new int[]{this.mFenceId});
                    break;
                } else if (i == 7) {
                    attribute.putIntArray("IntType", new int[]{this.mFenceId, this.mRadius, this.mStatus});
                    break;
                } else if (i == 9) {
                    attribute.putIntArray("IntType", new int[]{this.mMinDistance, this.mMinTime});
                    break;
                } else if (i == 23) {
                    int[] iArr = this.mRawData;
                    int[] intType = new int[iArr.length];
                    System.arraycopy(iArr, 0, intType, 0, iArr.length);
                    attribute.putIntArray("IntType", intType);
                    break;
                } else {
                    Log.d(TAG, "This Type is default attribute type");
                    break;
                }
            case 1:
                if (this.mAction == 8) {
                    double[] doubleType2 = {this.mLatitude, this.mLongitude};
                    int[] intType2 = {this.mAccuracy};
                    long[] longType = {this.mTimeStamp};
                    attribute.putDoubleArray("DoubleType", doubleType2);
                    attribute.putIntArray("IntType", intType2);
                    attribute.putLongArray("LongType", longType);
                    break;
                } else {
                    Log.d(TAG, "This Type is default attribute type");
                    break;
                }
            case 3:
                int i2 = this.mAction;
                if (i2 == 16 || i2 == 17) {
                    int[] intType3 = {this.mRequestId, this.mFusedBatchOption.user_info, this.mFusedBatchOption.flags};
                    long[] longType2 = {this.mFusedBatchOption.period};
                    double[] doubleType3 = {this.mFusedBatchOption.max_power};
                    float[] floatType = {this.mFusedBatchOption.distance_thrs};
                    attribute.putIntArray("IntType", intType3);
                    attribute.putLongArray("LongType", longType2);
                    attribute.putDoubleArray("DoubleType", doubleType3);
                    attribute.putFloatArray("FloatType", floatType);
                    break;
                } else if (i2 == 18) {
                    attribute.putIntArray("IntType", new int[]{this.mRequestId});
                    break;
                } else if (i2 == 19) {
                    attribute.putIntArray("IntType", new int[]{this.mBatchingSize});
                    break;
                } else if (i2 == 21) {
                    String stringType = this.mLocation.getProvider();
                    long[] longType3 = {this.mLocation.getTime()};
                    double[] doubleType4 = {this.mLocation.getLatitude(), this.mLocation.getLongitude(), this.mLocation.getAltitude()};
                    float[] floatType2 = {this.mLocation.getSpeed(), this.mLocation.getBearing(), this.mLocation.getAccuracy()};
                    attribute.putString("StringType", stringType);
                    attribute.putLongArray("IntType", longType3);
                    attribute.putDoubleArray("DoubleType", doubleType4);
                    attribute.putFloatArray("FloatType", floatType2);
                    break;
                } else {
                    Log.d(TAG, "This Type is default attribute type");
                    break;
                }
                break;
        }
        attribute.putInt("Mode", this.mMode);
        attribute.putInt("Action", this.mAction);
        Log.d(TAG, "setAttribute() mode : " + attribute.getInt("Mode") + " action : " + attribute.getInt("Action"));
        super.setAttribute(47, attribute);
    }

    /* loaded from: classes5.dex */
    public static class FusedBatchOption {
        final float distance_thrs;
        final int flags;
        final double max_power;
        final long period;
        final int user_info;

        FusedBatchOption(long period, int user_info, int flags, double max_power, float distance_thrs) {
            this.period = period;
            this.user_info = user_info;
            this.flags = flags;
            this.max_power = max_power;
            this.distance_thrs = distance_thrs;
        }

        boolean isValid() {
            if (this.period < 0) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.period is wrong.");
                return false;
            }
            if (this.user_info < 0) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.user_info is wrong.");
                return false;
            }
            if (this.flags < 0) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.flags is wrong.");
                return false;
            }
            if (this.max_power < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.max_power is wrong.");
                return false;
            }
            if (this.distance_thrs < 0.0f) {
                Log.d(SemContextLocationCoreAttribute.TAG, "FusedBatchOption.distance_thrs is wrong.");
                return false;
            }
            return true;
        }
    }
}
