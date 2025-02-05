.class public final Lcom/android/launcher3/icons/IconFactory;
.super Lcom/android/launcher3/icons/BaseIconFactory;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static sPool:Lcom/android/launcher3/icons/IconFactory;

.field public static final sPoolSync:Ljava/lang/Object;


# instance fields
.field public final mPoolId:I

.field public next:Lcom/android/launcher3/icons/IconFactory;


# direct methods
.method public static constructor <clinit>()V
    .locals 1

    .line 1
    new-instance v0, Ljava/lang/Object;

    .line 2
    .line 3
    invoke-direct {v0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    sput-object v0, Lcom/android/launcher3/icons/IconFactory;->sPoolSync:Ljava/lang/Object;

    .line 7
    .line 8
    return-void
.end method

.method private constructor <init>(Landroid/content/Context;III)V
    .locals 0

    .line 1
    invoke-direct {p0, p1, p2, p3}, Lcom/android/launcher3/icons/BaseIconFactory;-><init>(Landroid/content/Context;II)V

    .line 2
    .line 3
    .line 4
    iput p4, p0, Lcom/android/launcher3/icons/IconFactory;->mPoolId:I

    .line 5
    .line 6
    return-void
.end method

.method public static obtain(Landroid/content/Context;)Lcom/android/launcher3/icons/IconFactory;
    .locals 4

    .line 1
    sget-object v0, Lcom/android/launcher3/icons/IconFactory;->sPoolSync:Ljava/lang/Object;

    .line 2
    .line 3
    monitor-enter v0

    .line 4
    :try_start_0
    sget-object v1, Lcom/android/launcher3/icons/IconFactory;->sPool:Lcom/android/launcher3/icons/IconFactory;

    .line 5
    .line 6
    if-eqz v1, :cond_0

    .line 7
    .line 8
    iget-object p0, v1, Lcom/android/launcher3/icons/IconFactory;->next:Lcom/android/launcher3/icons/IconFactory;

    .line 9
    .line 10
    sput-object p0, Lcom/android/launcher3/icons/IconFactory;->sPool:Lcom/android/launcher3/icons/IconFactory;

    .line 11
    .line 12
    const/4 p0, 0x0

    .line 13
    iput-object p0, v1, Lcom/android/launcher3/icons/IconFactory;->next:Lcom/android/launcher3/icons/IconFactory;

    .line 14
    .line 15
    monitor-exit v0

    .line 16
    return-object v1

    .line 17
    :cond_0
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 18
    new-instance v0, Lcom/android/launcher3/icons/IconFactory;

    .line 19
    .line 20
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 21
    .line 22
    .line 23
    move-result-object v1

    .line 24
    invoke-virtual {v1}, Landroid/content/res/Resources;->getConfiguration()Landroid/content/res/Configuration;

    .line 25
    .line 26
    .line 27
    move-result-object v1

    .line 28
    iget v1, v1, Landroid/content/res/Configuration;->densityDpi:I

    .line 29
    .line 30
    invoke-virtual {p0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    .line 31
    .line 32
    .line 33
    move-result-object v2

    .line 34
    const v3, 0x7f070286

    .line 35
    .line 36
    .line 37
    invoke-virtual {v2, v3}, Landroid/content/res/Resources;->getDimensionPixelSize(I)I

    .line 38
    .line 39
    .line 40
    move-result v2

    .line 41
    const/4 v3, 0x0

    .line 42
    invoke-direct {v0, p0, v1, v2, v3}, Lcom/android/launcher3/icons/IconFactory;-><init>(Landroid/content/Context;III)V

    .line 43
    .line 44
    .line 45
    return-object v0

    .line 46
    :catchall_0
    move-exception p0

    .line 47
    :try_start_1
    monitor-exit v0
    :try_end_1
    .catchall {:try_start_1 .. :try_end_1} :catchall_0

    .line 48
    throw p0
.end method


# virtual methods
.method public final close()V
    .locals 2

    .line 1
    sget-object v0, Lcom/android/launcher3/icons/IconFactory;->sPoolSync:Ljava/lang/Object;

    .line 2
    .line 3
    monitor-enter v0

    .line 4
    :try_start_0
    iget v1, p0, Lcom/android/launcher3/icons/IconFactory;->mPoolId:I

    .line 5
    .line 6
    if-eqz v1, :cond_0

    .line 7
    .line 8
    monitor-exit v0

    .line 9
    goto :goto_0

    .line 10
    :cond_0
    const/4 v1, -0x1

    .line 11
    iput v1, p0, Lcom/android/launcher3/icons/BaseIconFactory;->mWrapperBackgroundColor:I

    .line 12
    .line 13
    sget-object v1, Lcom/android/launcher3/icons/IconFactory;->sPool:Lcom/android/launcher3/icons/IconFactory;

    .line 14
    .line 15
    iput-object v1, p0, Lcom/android/launcher3/icons/IconFactory;->next:Lcom/android/launcher3/icons/IconFactory;

    .line 16
    .line 17
    sput-object p0, Lcom/android/launcher3/icons/IconFactory;->sPool:Lcom/android/launcher3/icons/IconFactory;

    .line 18
    .line 19
    monitor-exit v0

    .line 20
    :goto_0
    return-void

    .line 21
    :catchall_0
    move-exception p0

    .line 22
    monitor-exit v0
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 23
    throw p0
.end method
