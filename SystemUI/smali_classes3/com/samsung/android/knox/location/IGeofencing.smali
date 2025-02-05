.class public interface abstract Lcom/samsung/android/knox/location/IGeofencing;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/samsung/android/knox/location/IGeofencing$Stub;,
        Lcom/samsung/android/knox/location/IGeofencing$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.samsung.android.knox.location.IGeofencing"


# virtual methods
.method public abstract createGeofence(Lcom/samsung/android/knox/ContextInfo;Lcom/samsung/android/knox/location/Geofence;)I
.end method

.method public abstract destroyGeofence(Lcom/samsung/android/knox/ContextInfo;I)Z
.end method

.method public abstract getGeofences(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
    .annotation system Ldalvik/annotation/Signature;
        value = {
            "(",
            "Lcom/samsung/android/knox/ContextInfo;",
            ")",
            "Ljava/util/List<",
            "Lcom/samsung/android/knox/location/Geofence;",
            ">;"
        }
    .end annotation
.end method

.method public abstract getMinDistanceParameter(Lcom/samsung/android/knox/ContextInfo;)F
.end method

.method public abstract getMinTimeParameter(Lcom/samsung/android/knox/ContextInfo;)J
.end method

.method public abstract isDeviceInsideGeofence(Lcom/samsung/android/knox/ContextInfo;)Ljava/util/List;
.end method

.method public abstract isGeofencingEnabled(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract setMinDistanceParameter(Lcom/samsung/android/knox/ContextInfo;F)Z
.end method

.method public abstract setMinTimeParameter(Lcom/samsung/android/knox/ContextInfo;J)Z
.end method

.method public abstract startGeofencing(Lcom/samsung/android/knox/ContextInfo;)Z
.end method

.method public abstract stopGeofencing(Lcom/samsung/android/knox/ContextInfo;)Z
.end method
