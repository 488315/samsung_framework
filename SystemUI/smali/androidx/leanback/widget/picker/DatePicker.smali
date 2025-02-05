.class public Landroidx/leanback/widget/picker/DatePicker;
.super Landroidx/leanback/widget/picker/Picker;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# static fields
.field public static final DATE_FIELDS:[I


# instance fields
.field public mColDayIndex:I

.field public mColMonthIndex:I

.field public mColYearIndex:I

.field public mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

.field public mCurrentDate:Ljava/util/Calendar;

.field public final mDateFormat:Ljava/text/DateFormat;

.field public mDatePickerFormat:Ljava/lang/String;

.field public mDayColumn:Landroidx/leanback/widget/picker/PickerColumn;

.field public mMaxDate:Ljava/util/Calendar;

.field public mMinDate:Ljava/util/Calendar;

.field public mMonthColumn:Landroidx/leanback/widget/picker/PickerColumn;

.field public mTempDate:Ljava/util/Calendar;

.field public mYearColumn:Landroidx/leanback/widget/picker/PickerColumn;


# direct methods
.method public static constructor <clinit>()V
    .locals 3

    .line 1
    const/4 v0, 0x2

    .line 2
    const/4 v1, 0x1

    .line 3
    const/4 v2, 0x5

    .line 4
    filled-new-array {v2, v0, v1}, [I

    .line 5
    .line 6
    .line 7
    move-result-object v0

    .line 8
    sput-object v0, Landroidx/leanback/widget/picker/DatePicker;->DATE_FIELDS:[I

    .line 9
    .line 10
    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;)V
    .locals 1

    const v0, 0x7f040189

    .line 1
    invoke-direct {p0, p1, p2, v0}, Landroidx/leanback/widget/picker/DatePicker;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    return-void
.end method

.method public constructor <init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V
    .locals 11

    .line 2
    invoke-direct {p0, p1, p2, p3}, Landroidx/leanback/widget/picker/Picker;-><init>(Landroid/content/Context;Landroid/util/AttributeSet;I)V

    .line 3
    new-instance p3, Ljava/text/SimpleDateFormat;

    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object v0

    const-string v1, "MM/dd/yyyy"

    invoke-direct {p3, v1, v0}, Ljava/text/SimpleDateFormat;-><init>(Ljava/lang/String;Ljava/util/Locale;)V

    iput-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mDateFormat:Ljava/text/DateFormat;

    .line 4
    invoke-static {}, Ljava/util/Locale;->getDefault()Ljava/util/Locale;

    move-result-object p3

    .line 5
    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getContext()Landroid/content/Context;

    move-result-object v0

    invoke-virtual {v0}, Landroid/content/Context;->getResources()Landroid/content/res/Resources;

    move-result-object v0

    .line 6
    new-instance v2, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    invoke-direct {v2, p3, v0}, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;-><init>(Ljava/util/Locale;Landroid/content/res/Resources;)V

    .line 7
    iput-object v2, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    .line 8
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    iget-object v0, v2, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->locale:Ljava/util/Locale;

    invoke-static {p3, v0}, Landroidx/leanback/widget/picker/PickerUtility;->getCalendarForLocale(Ljava/util/Calendar;Ljava/util/Locale;)Ljava/util/Calendar;

    move-result-object p3

    iput-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 9
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMinDate:Ljava/util/Calendar;

    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object v0, v0, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->locale:Ljava/util/Locale;

    invoke-static {p3, v0}, Landroidx/leanback/widget/picker/PickerUtility;->getCalendarForLocale(Ljava/util/Calendar;Ljava/util/Locale;)Ljava/util/Calendar;

    move-result-object p3

    iput-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMinDate:Ljava/util/Calendar;

    .line 10
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMaxDate:Ljava/util/Calendar;

    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object v0, v0, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->locale:Ljava/util/Locale;

    invoke-static {p3, v0}, Landroidx/leanback/widget/picker/PickerUtility;->getCalendarForLocale(Ljava/util/Calendar;Ljava/util/Locale;)Ljava/util/Calendar;

    move-result-object p3

    iput-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMaxDate:Ljava/util/Calendar;

    .line 11
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object v0, v0, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->locale:Ljava/util/Locale;

    invoke-static {p3, v0}, Landroidx/leanback/widget/picker/PickerUtility;->getCalendarForLocale(Ljava/util/Calendar;Ljava/util/Locale;)Ljava/util/Calendar;

    move-result-object p3

    iput-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 12
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMonthColumn:Landroidx/leanback/widget/picker/PickerColumn;

    if-eqz p3, :cond_0

    .line 13
    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object v0, v0, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->months:[Ljava/lang/String;

    .line 14
    iput-object v0, p3, Landroidx/leanback/widget/picker/PickerColumn;->mStaticLabels:[Ljava/lang/CharSequence;

    .line 15
    iget v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mColMonthIndex:I

    invoke-virtual {p0, v0, p3}, Landroidx/leanback/widget/picker/Picker;->setColumnAt(ILandroidx/leanback/widget/picker/PickerColumn;)V

    .line 16
    :cond_0
    sget-object v4, Landroidx/leanback/R$styleable;->lbDatePicker:[I

    invoke-virtual {p1, p2, v4}, Landroid/content/Context;->obtainStyledAttributes(Landroid/util/AttributeSet;[I)Landroid/content/res/TypedArray;

    move-result-object p3

    const/4 v7, 0x0

    const/4 v8, 0x0

    .line 17
    sget-object v0, Landroidx/core/view/ViewCompat;->sViewPropertyAnimatorMap:Ljava/util/WeakHashMap;

    move-object v2, p0

    move-object v3, p1

    move-object v5, p2

    move-object v6, p3

    .line 18
    invoke-static/range {v2 .. v8}, Landroidx/core/view/ViewCompat$Api29Impl;->saveAttributeDataForStyleable(Landroid/view/View;Landroid/content/Context;[ILandroid/util/AttributeSet;Landroid/content/res/TypedArray;II)V

    const/4 p2, 0x0

    .line 19
    :try_start_0
    invoke-virtual {p3, p2}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v0

    const/4 v2, 0x1

    .line 20
    invoke-virtual {p3, v2}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v3

    const/4 v4, 0x2

    .line 21
    invoke-virtual {p3, v4}, Landroid/content/res/TypedArray;->getString(I)Ljava/lang/String;

    move-result-object v4
    :try_end_0
    .catchall {:try_start_0 .. :try_end_0} :catchall_0

    .line 22
    invoke-virtual {p3}, Landroid/content/res/TypedArray;->recycle()V

    .line 23
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p3}, Ljava/util/Calendar;->clear()V

    .line 24
    invoke-static {v0}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p3

    const/16 v5, 0x76c

    if-nez p3, :cond_1

    .line 25
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p0, v0, p3}, Landroidx/leanback/widget/picker/DatePicker;->parseDate(Ljava/lang/String;Ljava/util/Calendar;)Z

    move-result p3

    if-nez p3, :cond_2

    .line 26
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p3, v5, p2, v2}, Ljava/util/Calendar;->set(III)V

    goto :goto_0

    .line 27
    :cond_1
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p3, v5, p2, v2}, Ljava/util/Calendar;->set(III)V

    .line 28
    :cond_2
    :goto_0
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMinDate:Ljava/util/Calendar;

    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {v0}, Ljava/util/Calendar;->getTimeInMillis()J

    move-result-wide v5

    invoke-virtual {p3, v5, v6}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 29
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p3}, Ljava/util/Calendar;->clear()V

    .line 30
    invoke-static {v3}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p3

    const/16 v0, 0x834

    if-nez p3, :cond_3

    .line 31
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p0, v3, p3}, Landroidx/leanback/widget/picker/DatePicker;->parseDate(Ljava/lang/String;Ljava/util/Calendar;)Z

    move-result p3

    if-nez p3, :cond_4

    .line 32
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p3, v0, p2, v2}, Ljava/util/Calendar;->set(III)V

    goto :goto_1

    .line 33
    :cond_3
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {p3, v0, p2, v2}, Ljava/util/Calendar;->set(III)V

    .line 34
    :cond_4
    :goto_1
    iget-object p3, p0, Landroidx/leanback/widget/picker/DatePicker;->mMaxDate:Ljava/util/Calendar;

    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    invoke-virtual {v0}, Ljava/util/Calendar;->getTimeInMillis()J

    move-result-wide v5

    invoke-virtual {p3, v5, v6}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 35
    invoke-static {v4}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p3

    if-eqz p3, :cond_5

    .line 36
    new-instance v4, Ljava/lang/String;

    .line 37
    invoke-static {p1}, Landroid/text/format/DateFormat;->getDateFormatOrder(Landroid/content/Context;)[C

    move-result-object p1

    invoke-direct {v4, p1}, Ljava/lang/String;-><init>([C)V

    .line 38
    :cond_5
    invoke-static {v4}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_6

    .line 39
    new-instance v4, Ljava/lang/String;

    .line 40
    invoke-virtual {p0}, Landroid/widget/FrameLayout;->getContext()Landroid/content/Context;

    move-result-object p1

    invoke-static {p1}, Landroid/text/format/DateFormat;->getDateFormatOrder(Landroid/content/Context;)[C

    move-result-object p1

    invoke-direct {v4, p1}, Ljava/lang/String;-><init>([C)V

    .line 41
    :cond_6
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mDatePickerFormat:Ljava/lang/String;

    invoke-static {p1, v4}, Landroid/text/TextUtils;->equals(Ljava/lang/CharSequence;Ljava/lang/CharSequence;)Z

    move-result p1

    if-eqz p1, :cond_7

    goto/16 :goto_a

    .line 42
    :cond_7
    iput-object v4, p0, Landroidx/leanback/widget/picker/DatePicker;->mDatePickerFormat:Ljava/lang/String;

    .line 43
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object p1, p1, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->locale:Ljava/util/Locale;

    invoke-static {p1, v4}, Landroid/text/format/DateFormat;->getBestDateTimePattern(Ljava/util/Locale;Ljava/lang/String;)Ljava/lang/String;

    move-result-object p1

    .line 44
    invoke-static {p1}, Landroid/text/TextUtils;->isEmpty(Ljava/lang/CharSequence;)Z

    move-result p3

    if-eqz p3, :cond_8

    goto :goto_2

    :cond_8
    move-object v1, p1

    .line 45
    :goto_2
    new-instance p1, Ljava/util/ArrayList;

    invoke-direct {p1}, Ljava/util/ArrayList;-><init>()V

    .line 46
    new-instance p3, Ljava/lang/StringBuilder;

    invoke-direct {p3}, Ljava/lang/StringBuilder;-><init>()V

    const/4 v0, 0x6

    new-array v3, v0, [C

    .line 47
    fill-array-data v3, :array_0

    move v5, p2

    move v6, v5

    move v7, v6

    .line 48
    :goto_3
    invoke-virtual {v1}, Ljava/lang/String;->length()I

    move-result v8

    if-ge v5, v8, :cond_11

    .line 49
    invoke-virtual {v1, v5}, Ljava/lang/String;->charAt(I)C

    move-result v8

    const/16 v9, 0x20

    if-ne v8, v9, :cond_9

    goto :goto_7

    :cond_9
    const/16 v9, 0x27

    if-ne v8, v9, :cond_b

    if-nez v6, :cond_a

    .line 50
    invoke-virtual {p3, p2}, Ljava/lang/StringBuilder;->setLength(I)V

    move v6, v2

    goto :goto_7

    :cond_a
    move v6, p2

    goto :goto_7

    :cond_b
    if-eqz v6, :cond_c

    .line 51
    invoke-virtual {p3, v8}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    goto :goto_6

    :cond_c
    move v9, p2

    :goto_4
    if-ge v9, v0, :cond_e

    .line 52
    aget-char v10, v3, v9

    if-ne v8, v10, :cond_d

    move v9, v2

    goto :goto_5

    :cond_d
    add-int/lit8 v9, v9, 0x1

    goto :goto_4

    :cond_e
    move v9, p2

    :goto_5
    if-eqz v9, :cond_f

    if-eq v8, v7, :cond_10

    .line 53
    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object v7

    invoke-virtual {p1, v7}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 54
    invoke-virtual {p3, p2}, Ljava/lang/StringBuilder;->setLength(I)V

    goto :goto_6

    .line 55
    :cond_f
    invoke-virtual {p3, v8}, Ljava/lang/StringBuilder;->append(C)Ljava/lang/StringBuilder;

    :cond_10
    :goto_6
    move v7, v8

    :goto_7
    add-int/lit8 v5, v5, 0x1

    goto :goto_3

    .line 56
    :cond_11
    invoke-virtual {p3}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p3

    invoke-virtual {p1, p3}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 57
    invoke-virtual {p1}, Ljava/util/ArrayList;->size()I

    move-result p3

    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result v0

    add-int/2addr v0, v2

    if-ne p3, v0, :cond_19

    .line 58
    iget-object p3, p0, Landroidx/leanback/widget/picker/Picker;->mSeparators:Ljava/util/List;

    check-cast p3, Ljava/util/ArrayList;

    invoke-virtual {p3}, Ljava/util/ArrayList;->clear()V

    .line 59
    iget-object p3, p0, Landroidx/leanback/widget/picker/Picker;->mSeparators:Ljava/util/List;

    check-cast p3, Ljava/util/ArrayList;

    invoke-virtual {p3, p1}, Ljava/util/ArrayList;->addAll(Ljava/util/Collection;)Z

    const/4 p1, 0x0

    .line 60
    iput-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mDayColumn:Landroidx/leanback/widget/picker/PickerColumn;

    iput-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mMonthColumn:Landroidx/leanback/widget/picker/PickerColumn;

    iput-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mYearColumn:Landroidx/leanback/widget/picker/PickerColumn;

    const/4 p1, -0x1

    .line 61
    iput p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mColMonthIndex:I

    iput p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mColDayIndex:I

    iput p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mColYearIndex:I

    .line 62
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object p1, p1, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->locale:Ljava/util/Locale;

    invoke-virtual {v4, p1}, Ljava/lang/String;->toUpperCase(Ljava/util/Locale;)Ljava/lang/String;

    move-result-object p1

    .line 63
    new-instance p3, Ljava/util/ArrayList;

    const/4 v0, 0x3

    invoke-direct {p3, v0}, Ljava/util/ArrayList;-><init>(I)V

    move v0, p2

    .line 64
    :goto_8
    invoke-virtual {p1}, Ljava/lang/String;->length()I

    move-result v1

    if-ge v0, v1, :cond_18

    .line 65
    invoke-virtual {p1, v0}, Ljava/lang/String;->charAt(I)C

    move-result v1

    const/16 v2, 0x44

    const-string v3, "datePicker format error"

    if-eq v1, v2, :cond_16

    const/16 v2, 0x4d

    if-eq v1, v2, :cond_14

    const/16 v2, 0x59

    if-ne v1, v2, :cond_13

    .line 66
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mYearColumn:Landroidx/leanback/widget/picker/PickerColumn;

    if-nez v1, :cond_12

    .line 67
    new-instance v1, Landroidx/leanback/widget/picker/PickerColumn;

    invoke-direct {v1}, Landroidx/leanback/widget/picker/PickerColumn;-><init>()V

    iput-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mYearColumn:Landroidx/leanback/widget/picker/PickerColumn;

    invoke-virtual {p3, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 68
    iput v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mColYearIndex:I

    .line 69
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mYearColumn:Landroidx/leanback/widget/picker/PickerColumn;

    const-string v2, "%d"

    .line 70
    iput-object v2, v1, Landroidx/leanback/widget/picker/PickerColumn;->mLabelFormat:Ljava/lang/String;

    goto :goto_9

    .line 71
    :cond_12
    new-instance p0, Ljava/lang/IllegalArgumentException;

    invoke-direct {p0, v3}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 72
    :cond_13
    new-instance p0, Ljava/lang/IllegalArgumentException;

    invoke-direct {p0, v3}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 73
    :cond_14
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mMonthColumn:Landroidx/leanback/widget/picker/PickerColumn;

    if-nez v1, :cond_15

    .line 74
    new-instance v1, Landroidx/leanback/widget/picker/PickerColumn;

    invoke-direct {v1}, Landroidx/leanback/widget/picker/PickerColumn;-><init>()V

    iput-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mMonthColumn:Landroidx/leanback/widget/picker/PickerColumn;

    invoke-virtual {p3, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 75
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mMonthColumn:Landroidx/leanback/widget/picker/PickerColumn;

    iget-object v2, p0, Landroidx/leanback/widget/picker/DatePicker;->mConstant:Landroidx/leanback/widget/picker/PickerUtility$DateConstant;

    iget-object v2, v2, Landroidx/leanback/widget/picker/PickerUtility$DateConstant;->months:[Ljava/lang/String;

    .line 76
    iput-object v2, v1, Landroidx/leanback/widget/picker/PickerColumn;->mStaticLabels:[Ljava/lang/CharSequence;

    .line 77
    iput v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mColMonthIndex:I

    goto :goto_9

    .line 78
    :cond_15
    new-instance p0, Ljava/lang/IllegalArgumentException;

    invoke-direct {p0, v3}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 79
    :cond_16
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mDayColumn:Landroidx/leanback/widget/picker/PickerColumn;

    if-nez v1, :cond_17

    .line 80
    new-instance v1, Landroidx/leanback/widget/picker/PickerColumn;

    invoke-direct {v1}, Landroidx/leanback/widget/picker/PickerColumn;-><init>()V

    iput-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mDayColumn:Landroidx/leanback/widget/picker/PickerColumn;

    invoke-virtual {p3, v1}, Ljava/util/ArrayList;->add(Ljava/lang/Object;)Z

    .line 81
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mDayColumn:Landroidx/leanback/widget/picker/PickerColumn;

    const-string v2, "%02d"

    .line 82
    iput-object v2, v1, Landroidx/leanback/widget/picker/PickerColumn;->mLabelFormat:Ljava/lang/String;

    .line 83
    iput v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mColDayIndex:I

    :goto_9
    add-int/lit8 v0, v0, 0x1

    goto :goto_8

    .line 84
    :cond_17
    new-instance p0, Ljava/lang/IllegalArgumentException;

    invoke-direct {p0, v3}, Ljava/lang/IllegalArgumentException;-><init>(Ljava/lang/String;)V

    throw p0

    .line 85
    :cond_18
    invoke-virtual {p0, p3}, Landroidx/leanback/widget/picker/Picker;->setColumns(Ljava/util/List;)V

    .line 86
    new-instance p1, Landroidx/leanback/widget/picker/DatePicker$1;

    invoke-direct {p1, p0, p2}, Landroidx/leanback/widget/picker/DatePicker$1;-><init>(Landroidx/leanback/widget/picker/DatePicker;Z)V

    invoke-virtual {p0, p1}, Landroid/widget/FrameLayout;->post(Ljava/lang/Runnable;)Z

    :goto_a
    return-void

    .line 87
    :cond_19
    new-instance p0, Ljava/lang/IllegalStateException;

    new-instance p2, Ljava/lang/StringBuilder;

    const-string p3, "Separators size: "

    invoke-direct {p2, p3}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    invoke-virtual {p1}, Ljava/util/ArrayList;->size()I

    move-result p1

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " must equal the size of datePickerFormat: "

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 88
    invoke-virtual {v4}, Ljava/lang/String;->length()I

    move-result p1

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(I)Ljava/lang/StringBuilder;

    const-string p1, " + 1"

    invoke-virtual {p2, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    invoke-virtual {p2}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    move-result-object p1

    invoke-direct {p0, p1}, Ljava/lang/IllegalStateException;-><init>(Ljava/lang/String;)V

    throw p0

    :catchall_0
    move-exception p0

    .line 89
    invoke-virtual {p3}, Landroid/content/res/TypedArray;->recycle()V

    .line 90
    throw p0

    nop

    :array_0
    .array-data 2
        0x59s
        0x79s
        0x4ds
        0x6ds
        0x44s
        0x64s
    .end array-data
.end method


# virtual methods
.method public final onColumnValueChanged(II)V
    .locals 6

    .line 1
    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 2
    .line 3
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 4
    .line 5
    invoke-virtual {v1}, Ljava/util/Calendar;->getTimeInMillis()J

    .line 6
    .line 7
    .line 8
    move-result-wide v1

    .line 9
    invoke-virtual {v0, v1, v2}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 10
    .line 11
    .line 12
    iget-object v0, p0, Landroidx/leanback/widget/picker/Picker;->mColumns:Ljava/util/ArrayList;

    .line 13
    .line 14
    if-nez v0, :cond_0

    .line 15
    .line 16
    const/4 v0, 0x0

    .line 17
    goto :goto_0

    .line 18
    :cond_0
    invoke-virtual {v0, p1}, Ljava/util/ArrayList;->get(I)Ljava/lang/Object;

    .line 19
    .line 20
    .line 21
    move-result-object v0

    .line 22
    check-cast v0, Landroidx/leanback/widget/picker/PickerColumn;

    .line 23
    .line 24
    :goto_0
    iget v0, v0, Landroidx/leanback/widget/picker/PickerColumn;->mCurrentValue:I

    .line 25
    .line 26
    iget v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mColDayIndex:I

    .line 27
    .line 28
    const/4 v2, 0x1

    .line 29
    const/4 v3, 0x2

    .line 30
    const/4 v4, 0x5

    .line 31
    if-ne p1, v1, :cond_1

    .line 32
    .line 33
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 34
    .line 35
    sub-int/2addr p2, v0

    .line 36
    invoke-virtual {p1, v4, p2}, Ljava/util/Calendar;->add(II)V

    .line 37
    .line 38
    .line 39
    goto :goto_1

    .line 40
    :cond_1
    iget v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mColMonthIndex:I

    .line 41
    .line 42
    if-ne p1, v1, :cond_2

    .line 43
    .line 44
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 45
    .line 46
    sub-int/2addr p2, v0

    .line 47
    invoke-virtual {p1, v3, p2}, Ljava/util/Calendar;->add(II)V

    .line 48
    .line 49
    .line 50
    goto :goto_1

    .line 51
    :cond_2
    iget v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mColYearIndex:I

    .line 52
    .line 53
    if-ne p1, v1, :cond_8

    .line 54
    .line 55
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 56
    .line 57
    sub-int/2addr p2, v0

    .line 58
    invoke-virtual {p1, v2, p2}, Ljava/util/Calendar;->add(II)V

    .line 59
    .line 60
    .line 61
    :goto_1
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 62
    .line 63
    invoke-virtual {p1, v2}, Ljava/util/Calendar;->get(I)I

    .line 64
    .line 65
    .line 66
    move-result p1

    .line 67
    iget-object p2, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 68
    .line 69
    invoke-virtual {p2, v3}, Ljava/util/Calendar;->get(I)I

    .line 70
    .line 71
    .line 72
    move-result p2

    .line 73
    iget-object v0, p0, Landroidx/leanback/widget/picker/DatePicker;->mTempDate:Ljava/util/Calendar;

    .line 74
    .line 75
    invoke-virtual {v0, v4}, Ljava/util/Calendar;->get(I)I

    .line 76
    .line 77
    .line 78
    move-result v0

    .line 79
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 80
    .line 81
    invoke-virtual {v1, v2}, Ljava/util/Calendar;->get(I)I

    .line 82
    .line 83
    .line 84
    move-result v1

    .line 85
    const/4 v5, 0x0

    .line 86
    if-ne v1, p1, :cond_4

    .line 87
    .line 88
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 89
    .line 90
    invoke-virtual {v1, v3}, Ljava/util/Calendar;->get(I)I

    .line 91
    .line 92
    .line 93
    move-result v1

    .line 94
    if-ne v1, v0, :cond_4

    .line 95
    .line 96
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 97
    .line 98
    invoke-virtual {v1, v4}, Ljava/util/Calendar;->get(I)I

    .line 99
    .line 100
    .line 101
    move-result v1

    .line 102
    if-eq v1, p2, :cond_3

    .line 103
    .line 104
    goto :goto_2

    .line 105
    :cond_3
    move v2, v5

    .line 106
    :cond_4
    :goto_2
    if-nez v2, :cond_5

    .line 107
    .line 108
    goto :goto_4

    .line 109
    :cond_5
    iget-object v1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 110
    .line 111
    invoke-virtual {v1, p1, p2, v0}, Ljava/util/Calendar;->set(III)V

    .line 112
    .line 113
    .line 114
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 115
    .line 116
    iget-object p2, p0, Landroidx/leanback/widget/picker/DatePicker;->mMinDate:Ljava/util/Calendar;

    .line 117
    .line 118
    invoke-virtual {p1, p2}, Ljava/util/Calendar;->before(Ljava/lang/Object;)Z

    .line 119
    .line 120
    .line 121
    move-result p1

    .line 122
    if-eqz p1, :cond_6

    .line 123
    .line 124
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 125
    .line 126
    iget-object p2, p0, Landroidx/leanback/widget/picker/DatePicker;->mMinDate:Ljava/util/Calendar;

    .line 127
    .line 128
    invoke-virtual {p2}, Ljava/util/Calendar;->getTimeInMillis()J

    .line 129
    .line 130
    .line 131
    move-result-wide v0

    .line 132
    invoke-virtual {p1, v0, v1}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 133
    .line 134
    .line 135
    goto :goto_3

    .line 136
    :cond_6
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 137
    .line 138
    iget-object p2, p0, Landroidx/leanback/widget/picker/DatePicker;->mMaxDate:Ljava/util/Calendar;

    .line 139
    .line 140
    invoke-virtual {p1, p2}, Ljava/util/Calendar;->after(Ljava/lang/Object;)Z

    .line 141
    .line 142
    .line 143
    move-result p1

    .line 144
    if-eqz p1, :cond_7

    .line 145
    .line 146
    iget-object p1, p0, Landroidx/leanback/widget/picker/DatePicker;->mCurrentDate:Ljava/util/Calendar;

    .line 147
    .line 148
    iget-object p2, p0, Landroidx/leanback/widget/picker/DatePicker;->mMaxDate:Ljava/util/Calendar;

    .line 149
    .line 150
    invoke-virtual {p2}, Ljava/util/Calendar;->getTimeInMillis()J

    .line 151
    .line 152
    .line 153
    move-result-wide v0

    .line 154
    invoke-virtual {p1, v0, v1}, Ljava/util/Calendar;->setTimeInMillis(J)V

    .line 155
    .line 156
    .line 157
    :cond_7
    :goto_3
    new-instance p1, Landroidx/leanback/widget/picker/DatePicker$1;

    .line 158
    .line 159
    invoke-direct {p1, p0, v5}, Landroidx/leanback/widget/picker/DatePicker$1;-><init>(Landroidx/leanback/widget/picker/DatePicker;Z)V

    .line 160
    .line 161
    .line 162
    invoke-virtual {p0, p1}, Landroid/widget/FrameLayout;->post(Ljava/lang/Runnable;)Z

    .line 163
    .line 164
    .line 165
    :goto_4
    return-void

    .line 166
    :cond_8
    new-instance p0, Ljava/lang/IllegalArgumentException;

    .line 167
    .line 168
    invoke-direct {p0}, Ljava/lang/IllegalArgumentException;-><init>()V

    .line 169
    .line 170
    .line 171
    throw p0
.end method

.method public final parseDate(Ljava/lang/String;Ljava/util/Calendar;)Z
    .locals 0

    .line 1
    :try_start_0
    iget-object p0, p0, Landroidx/leanback/widget/picker/DatePicker;->mDateFormat:Ljava/text/DateFormat;

    .line 2
    .line 3
    invoke-virtual {p0, p1}, Ljava/text/DateFormat;->parse(Ljava/lang/String;)Ljava/util/Date;

    .line 4
    .line 5
    .line 6
    move-result-object p0

    .line 7
    invoke-virtual {p2, p0}, Ljava/util/Calendar;->setTime(Ljava/util/Date;)V
    :try_end_0
    .catch Ljava/text/ParseException; {:try_start_0 .. :try_end_0} :catch_0

    .line 8
    .line 9
    .line 10
    const/4 p0, 0x1

    .line 11
    return p0

    .line 12
    :catch_0
    new-instance p0, Ljava/lang/StringBuilder;

    .line 13
    .line 14
    const-string p2, "Date: "

    .line 15
    .line 16
    invoke-direct {p0, p2}, Ljava/lang/StringBuilder;-><init>(Ljava/lang/String;)V

    .line 17
    .line 18
    .line 19
    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 20
    .line 21
    .line 22
    const-string p1, " not in format: MM/dd/yyyy"

    .line 23
    .line 24
    invoke-virtual {p0, p1}, Ljava/lang/StringBuilder;->append(Ljava/lang/String;)Ljava/lang/StringBuilder;

    .line 25
    .line 26
    .line 27
    invoke-virtual {p0}, Ljava/lang/StringBuilder;->toString()Ljava/lang/String;

    .line 28
    .line 29
    .line 30
    move-result-object p0

    .line 31
    const-string p1, "DatePicker"

    .line 32
    .line 33
    invoke-static {p1, p0}, Landroid/util/Log;->w(Ljava/lang/String;Ljava/lang/String;)I

    .line 34
    .line 35
    .line 36
    const/4 p0, 0x0

    .line 37
    return p0
.end method
