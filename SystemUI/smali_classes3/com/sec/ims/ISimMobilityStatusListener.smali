.class public interface abstract Lcom/sec/ims/ISimMobilityStatusListener;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/os/IInterface;


# annotations
.annotation system Ldalvik/annotation/MemberClasses;
    value = {
        Lcom/sec/ims/ISimMobilityStatusListener$Stub;,
        Lcom/sec/ims/ISimMobilityStatusListener$Default;
    }
.end annotation


# static fields
.field public static final DESCRIPTOR:Ljava/lang/String; = "com.sec.ims.ISimMobilityStatusListener"


# virtual methods
.method public abstract onSimMobilityStateChanged(Z)V
.end method