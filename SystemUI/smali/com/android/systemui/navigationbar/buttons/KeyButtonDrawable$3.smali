.class public final Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable$3;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"

# interfaces
.implements Landroid/graphics/drawable/Drawable$Callback;


# instance fields
.field public final synthetic this$0:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;


# direct methods
.method public constructor <init>(Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;)V
    .locals 0

    .line 1
    iput-object p1, p0, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable$3;->this$0:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 2
    .line 3
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 4
    .line 5
    .line 6
    return-void
.end method


# virtual methods
.method public final invalidateDrawable(Landroid/graphics/drawable/Drawable;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable$3;->this$0:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 2
    .line 3
    invoke-virtual {p0}, Landroid/graphics/drawable/Drawable;->invalidateSelf()V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final scheduleDrawable(Landroid/graphics/drawable/Drawable;Ljava/lang/Runnable;J)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable$3;->this$0:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 2
    .line 3
    invoke-virtual {p0, p2, p3, p4}, Landroid/graphics/drawable/Drawable;->scheduleSelf(Ljava/lang/Runnable;J)V

    .line 4
    .line 5
    .line 6
    return-void
.end method

.method public final unscheduleDrawable(Landroid/graphics/drawable/Drawable;Ljava/lang/Runnable;)V
    .locals 0

    .line 1
    iget-object p0, p0, Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable$3;->this$0:Lcom/android/systemui/navigationbar/buttons/KeyButtonDrawable;

    .line 2
    .line 3
    invoke-virtual {p0, p2}, Landroid/graphics/drawable/Drawable;->unscheduleSelf(Ljava/lang/Runnable;)V

    .line 4
    .line 5
    .line 6
    return-void
.end method