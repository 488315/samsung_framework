.class public abstract Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# direct methods
.method public static final getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;
    .locals 3

    .line 1
    invoke-interface {p0}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeCount()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x0

    .line 6
    :goto_0
    if-ge v1, v0, :cond_1

    .line 7
    .line 8
    invoke-interface {p0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeName(I)Ljava/lang/String;

    .line 9
    .line 10
    .line 11
    move-result-object v2

    .line 12
    invoke-static {v2, p1}, Lkotlin/jvm/internal/Intrinsics;->areEqual(Ljava/lang/Object;Ljava/lang/Object;)Z

    .line 13
    .line 14
    .line 15
    move-result v2

    .line 16
    if-eqz v2, :cond_0

    .line 17
    .line 18
    invoke-interface {p0, v1}, Lorg/xmlpull/v1/XmlPullParser;->getAttributeValue(I)Ljava/lang/String;

    .line 19
    .line 20
    .line 21
    move-result-object p0

    .line 22
    return-object p0

    .line 23
    :cond_0
    add-int/lit8 v1, v1, 0x1

    .line 24
    .line 25
    goto :goto_0

    .line 26
    :cond_1
    const/4 p0, 0x0

    .line 27
    return-object p0
.end method

.method public static final readXml(Ljava/io/InputStream;)Landroid/util/SparseArray;
    .locals 6

    .line 1
    new-instance v0, Landroid/util/SparseArray;

    .line 2
    .line 3
    invoke-direct {v0}, Landroid/util/SparseArray;-><init>()V

    .line 4
    .line 5
    .line 6
    invoke-static {}, Landroid/util/Xml;->newPullParser()Lorg/xmlpull/v1/XmlPullParser;

    .line 7
    .line 8
    .line 9
    move-result-object v1

    .line 10
    sget-object v2, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    .line 11
    .line 12
    invoke-virtual {v2}, Ljava/nio/charset/Charset;->name()Ljava/lang/String;

    .line 13
    .line 14
    .line 15
    move-result-object v2

    .line 16
    invoke-interface {v1, p0, v2}, Lorg/xmlpull/v1/XmlPullParser;->setInput(Ljava/io/InputStream;Ljava/lang/String;)V

    .line 17
    .line 18
    .line 19
    const-string p0, "bs"

    .line 20
    .line 21
    invoke-static {v1, p0}, Lcom/android/internal/util/XmlUtils;->beginDocument(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)V

    .line 22
    .line 23
    .line 24
    invoke-interface {v1}, Lorg/xmlpull/v1/XmlPullParser;->getDepth()I

    .line 25
    .line 26
    .line 27
    move-result p0

    .line 28
    const-string/jumbo v2, "v"

    .line 29
    .line 30
    .line 31
    invoke-static {v1, v2}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 32
    .line 33
    .line 34
    move-result-object v2

    .line 35
    if-eqz v2, :cond_7

    .line 36
    .line 37
    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    .line 38
    .line 39
    .line 40
    move-result v2

    .line 41
    const/4 v3, 0x1

    .line 42
    if-eq v2, v3, :cond_4

    .line 43
    .line 44
    const/4 v3, 0x2

    .line 45
    if-eq v2, v3, :cond_0

    .line 46
    .line 47
    goto :goto_3

    .line 48
    :cond_0
    :goto_0
    invoke-static {v1, p0}, Lcom/android/internal/util/XmlUtils;->nextElementWithin(Lorg/xmlpull/v1/XmlPullParser;I)Z

    .line 49
    .line 50
    .line 51
    move-result v2

    .line 52
    if-eqz v2, :cond_7

    .line 53
    .line 54
    const-string/jumbo v2, "uid"

    .line 55
    .line 56
    .line 57
    invoke-static {v1, v2}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 58
    .line 59
    .line 60
    move-result-object v2

    .line 61
    if-nez v2, :cond_1

    .line 62
    .line 63
    goto :goto_0

    .line 64
    :cond_1
    invoke-interface {v1}, Lorg/xmlpull/v1/XmlPullParser;->getDepth()I

    .line 65
    .line 66
    .line 67
    move-result v3

    .line 68
    new-instance v4, Ljava/util/ArrayList;

    .line 69
    .line 70
    invoke-direct {v4}, Ljava/util/ArrayList;-><init>()V

    .line 71
    .line 72
    .line 73
    :goto_1
    invoke-static {v1, v3}, Lcom/android/internal/util/XmlUtils;->nextElementWithin(Lorg/xmlpull/v1/XmlPullParser;I)Z

    .line 74
    .line 75
    .line 76
    move-result v5

    .line 77
    if-eqz v5, :cond_3

    .line 78
    .line 79
    invoke-static {v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->readXmlEntry(Lorg/xmlpull/v1/XmlPullParser;)Lcom/android/wm/shell/bubbles/storage/BubbleEntity;

    .line 80
    .line 81
    .line 82
    move-result-object v5

    .line 83
    if-nez v5, :cond_2

    .line 84
    .line 85
    goto :goto_1

    .line 86
    :cond_2
    invoke-interface {v4, v5}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 87
    .line 88
    .line 89
    goto :goto_1

    .line 90
    :cond_3
    invoke-interface {v4}, Ljava/util/List;->isEmpty()Z

    .line 91
    .line 92
    .line 93
    move-result v3

    .line 94
    if-nez v3, :cond_0

    .line 95
    .line 96
    invoke-static {v2}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    .line 97
    .line 98
    .line 99
    move-result v2

    .line 100
    invoke-static {v4}, Lkotlin/collections/CollectionsKt___CollectionsKt;->toList(Ljava/lang/Iterable;)Ljava/util/List;

    .line 101
    .line 102
    .line 103
    move-result-object v3

    .line 104
    invoke-virtual {v0, v2, v3}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 105
    .line 106
    .line 107
    goto :goto_0

    .line 108
    :cond_4
    invoke-interface {v1}, Lorg/xmlpull/v1/XmlPullParser;->getDepth()I

    .line 109
    .line 110
    .line 111
    move-result p0

    .line 112
    new-instance v2, Ljava/util/ArrayList;

    .line 113
    .line 114
    invoke-direct {v2}, Ljava/util/ArrayList;-><init>()V

    .line 115
    .line 116
    .line 117
    :cond_5
    :goto_2
    invoke-static {v1, p0}, Lcom/android/internal/util/XmlUtils;->nextElementWithin(Lorg/xmlpull/v1/XmlPullParser;I)Z

    .line 118
    .line 119
    .line 120
    move-result v3

    .line 121
    if-eqz v3, :cond_6

    .line 122
    .line 123
    invoke-static {v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->readXmlEntry(Lorg/xmlpull/v1/XmlPullParser;)Lcom/android/wm/shell/bubbles/storage/BubbleEntity;

    .line 124
    .line 125
    .line 126
    move-result-object v3

    .line 127
    if-eqz v3, :cond_5

    .line 128
    .line 129
    iget v4, v3, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->userId:I

    .line 130
    .line 131
    if-nez v4, :cond_5

    .line 132
    .line 133
    invoke-interface {v2, v3}, Ljava/util/List;->add(Ljava/lang/Object;)Z

    .line 134
    .line 135
    .line 136
    goto :goto_2

    .line 137
    :cond_6
    invoke-interface {v2}, Ljava/util/List;->isEmpty()Z

    .line 138
    .line 139
    .line 140
    move-result p0

    .line 141
    if-nez p0, :cond_7

    .line 142
    .line 143
    const/4 p0, 0x0

    .line 144
    invoke-static {v2}, Lkotlin/collections/CollectionsKt___CollectionsKt;->toList(Ljava/lang/Iterable;)Ljava/util/List;

    .line 145
    .line 146
    .line 147
    move-result-object v1

    .line 148
    invoke-virtual {v0, p0, v1}, Landroid/util/SparseArray;->put(ILjava/lang/Object;)V

    .line 149
    .line 150
    .line 151
    :cond_7
    :goto_3
    return-object v0
.end method

.method public static final readXmlEntry(Lorg/xmlpull/v1/XmlPullParser;)Lcom/android/wm/shell/bubbles/storage/BubbleEntity;
    .locals 13

    .line 1
    :goto_0
    invoke-interface {p0}, Lorg/xmlpull/v1/XmlPullParser;->getEventType()I

    .line 2
    .line 3
    .line 4
    move-result v0

    .line 5
    const/4 v1, 0x2

    .line 6
    if-eq v0, v1, :cond_0

    .line 7
    .line 8
    invoke-interface {p0}, Lorg/xmlpull/v1/XmlPullParser;->next()I

    .line 9
    .line 10
    .line 11
    goto :goto_0

    .line 12
    :cond_0
    new-instance v0, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;

    .line 13
    .line 14
    const-string/jumbo v1, "uid"

    .line 15
    .line 16
    .line 17
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 18
    .line 19
    .line 20
    move-result-object v1

    .line 21
    const/4 v2, 0x0

    .line 22
    if-eqz v1, :cond_6

    .line 23
    .line 24
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    .line 25
    .line 26
    .line 27
    move-result v3

    .line 28
    const-string/jumbo v1, "pkg"

    .line 29
    .line 30
    .line 31
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 32
    .line 33
    .line 34
    move-result-object v4

    .line 35
    if-nez v4, :cond_1

    .line 36
    .line 37
    return-object v2

    .line 38
    :cond_1
    const-string/jumbo v1, "sid"

    .line 39
    .line 40
    .line 41
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 42
    .line 43
    .line 44
    move-result-object v5

    .line 45
    if-nez v5, :cond_2

    .line 46
    .line 47
    return-object v2

    .line 48
    :cond_2
    const-string v1, "key"

    .line 49
    .line 50
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 51
    .line 52
    .line 53
    move-result-object v6

    .line 54
    if-nez v6, :cond_3

    .line 55
    .line 56
    return-object v2

    .line 57
    :cond_3
    const-string v1, "h"

    .line 58
    .line 59
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 60
    .line 61
    .line 62
    move-result-object v1

    .line 63
    if-eqz v1, :cond_6

    .line 64
    .line 65
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    .line 66
    .line 67
    .line 68
    move-result v7

    .line 69
    const-string v1, "hid"

    .line 70
    .line 71
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 72
    .line 73
    .line 74
    move-result-object v1

    .line 75
    if-eqz v1, :cond_6

    .line 76
    .line 77
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    .line 78
    .line 79
    .line 80
    move-result v8

    .line 81
    const-string/jumbo v1, "t"

    .line 82
    .line 83
    .line 84
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 85
    .line 86
    .line 87
    move-result-object v9

    .line 88
    const-string/jumbo v1, "tid"

    .line 89
    .line 90
    .line 91
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 92
    .line 93
    .line 94
    move-result-object v1

    .line 95
    if-eqz v1, :cond_4

    .line 96
    .line 97
    invoke-static {v1}, Ljava/lang/Integer;->parseInt(Ljava/lang/String;)I

    .line 98
    .line 99
    .line 100
    move-result v1

    .line 101
    goto :goto_1

    .line 102
    :cond_4
    const/4 v1, -0x1

    .line 103
    :goto_1
    move v10, v1

    .line 104
    const-string v1, "l"

    .line 105
    .line 106
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 107
    .line 108
    .line 109
    move-result-object v11

    .line 110
    const-string v1, "d"

    .line 111
    .line 112
    invoke-static {p0, v1}, Lcom/android/wm/shell/bubbles/storage/BubbleXmlHelperKt;->getAttributeWithName(Lorg/xmlpull/v1/XmlPullParser;Ljava/lang/String;)Ljava/lang/String;

    .line 113
    .line 114
    .line 115
    move-result-object p0

    .line 116
    if-eqz p0, :cond_5

    .line 117
    .line 118
    invoke-static {p0}, Ljava/lang/Boolean;->parseBoolean(Ljava/lang/String;)Z

    .line 119
    .line 120
    .line 121
    move-result p0

    .line 122
    goto :goto_2

    .line 123
    :cond_5
    const/4 p0, 0x0

    .line 124
    :goto_2
    move v12, p0

    .line 125
    move-object v2, v0

    .line 126
    invoke-direct/range {v2 .. v12}, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;-><init>(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;IILjava/lang/String;ILjava/lang/String;Z)V

    .line 127
    .line 128
    .line 129
    return-object v0

    .line 130
    :cond_6
    return-object v2
.end method

.method public static final writeXml(Ljava/io/OutputStream;Landroid/util/SparseArray;)V
    .locals 10

    .line 1
    new-instance v0, Lcom/android/internal/util/FastXmlSerializer;

    .line 2
    .line 3
    invoke-direct {v0}, Lcom/android/internal/util/FastXmlSerializer;-><init>()V

    .line 4
    .line 5
    .line 6
    sget-object v1, Ljava/nio/charset/StandardCharsets;->UTF_8:Ljava/nio/charset/Charset;

    .line 7
    .line 8
    invoke-virtual {v1}, Ljava/nio/charset/Charset;->name()Ljava/lang/String;

    .line 9
    .line 10
    .line 11
    move-result-object v1

    .line 12
    invoke-virtual {v0, p0, v1}, Lcom/android/internal/util/FastXmlSerializer;->setOutput(Ljava/io/OutputStream;Ljava/lang/String;)V

    .line 13
    .line 14
    .line 15
    sget-object p0, Ljava/lang/Boolean;->TRUE:Ljava/lang/Boolean;

    .line 16
    .line 17
    const/4 v1, 0x0

    .line 18
    invoke-virtual {v0, v1, p0}, Lcom/android/internal/util/FastXmlSerializer;->startDocument(Ljava/lang/String;Ljava/lang/Boolean;)V

    .line 19
    .line 20
    .line 21
    const-string p0, "bs"

    .line 22
    .line 23
    invoke-virtual {v0, v1, p0}, Lcom/android/internal/util/FastXmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 24
    .line 25
    .line 26
    const-string/jumbo v2, "v"

    .line 27
    .line 28
    .line 29
    const-string v3, "2"

    .line 30
    .line 31
    invoke-virtual {v0, v1, v2, v3}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 32
    .line 33
    .line 34
    invoke-virtual {p1}, Landroid/util/SparseArray;->size()I

    .line 35
    .line 36
    .line 37
    move-result v2

    .line 38
    const/4 v3, 0x0

    .line 39
    :goto_0
    if-ge v3, v2, :cond_3

    .line 40
    .line 41
    invoke-virtual {p1, v3}, Landroid/util/SparseArray;->keyAt(I)I

    .line 42
    .line 43
    .line 44
    move-result v4

    .line 45
    invoke-virtual {p1, v3}, Landroid/util/SparseArray;->valueAt(I)Ljava/lang/Object;

    .line 46
    .line 47
    .line 48
    move-result-object v5

    .line 49
    check-cast v5, Ljava/util/List;

    .line 50
    .line 51
    invoke-virtual {v0, v1, p0}, Lcom/android/internal/util/FastXmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 52
    .line 53
    .line 54
    invoke-static {v4}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    .line 55
    .line 56
    .line 57
    move-result-object v4

    .line 58
    const-string/jumbo v6, "uid"

    .line 59
    .line 60
    .line 61
    invoke-virtual {v0, v1, v6, v4}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 62
    .line 63
    .line 64
    invoke-interface {v5}, Ljava/lang/Iterable;->iterator()Ljava/util/Iterator;

    .line 65
    .line 66
    .line 67
    move-result-object v4

    .line 68
    :goto_1
    invoke-interface {v4}, Ljava/util/Iterator;->hasNext()Z

    .line 69
    .line 70
    .line 71
    move-result v5

    .line 72
    if-eqz v5, :cond_2

    .line 73
    .line 74
    invoke-interface {v4}, Ljava/util/Iterator;->next()Ljava/lang/Object;

    .line 75
    .line 76
    .line 77
    move-result-object v5

    .line 78
    check-cast v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;

    .line 79
    .line 80
    const-string v7, "bb"

    .line 81
    .line 82
    :try_start_0
    invoke-virtual {v0, v1, v7}, Lcom/android/internal/util/FastXmlSerializer;->startTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 83
    .line 84
    .line 85
    iget v8, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->userId:I

    .line 86
    .line 87
    invoke-static {v8}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    .line 88
    .line 89
    .line 90
    move-result-object v8

    .line 91
    invoke-virtual {v0, v1, v6, v8}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 92
    .line 93
    .line 94
    const-string/jumbo v8, "pkg"

    .line 95
    .line 96
    .line 97
    iget-object v9, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->packageName:Ljava/lang/String;

    .line 98
    .line 99
    invoke-virtual {v0, v1, v8, v9}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 100
    .line 101
    .line 102
    const-string/jumbo v8, "sid"

    .line 103
    .line 104
    .line 105
    iget-object v9, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->shortcutId:Ljava/lang/String;

    .line 106
    .line 107
    invoke-virtual {v0, v1, v8, v9}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 108
    .line 109
    .line 110
    const-string v8, "key"

    .line 111
    .line 112
    iget-object v9, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->key:Ljava/lang/String;

    .line 113
    .line 114
    invoke-virtual {v0, v1, v8, v9}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 115
    .line 116
    .line 117
    const-string v8, "h"

    .line 118
    .line 119
    iget v9, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->desiredHeight:I

    .line 120
    .line 121
    invoke-static {v9}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    .line 122
    .line 123
    .line 124
    move-result-object v9

    .line 125
    invoke-virtual {v0, v1, v8, v9}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 126
    .line 127
    .line 128
    const-string v8, "hid"

    .line 129
    .line 130
    iget v9, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->desiredHeightResId:I

    .line 131
    .line 132
    invoke-static {v9}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    .line 133
    .line 134
    .line 135
    move-result-object v9

    .line 136
    invoke-virtual {v0, v1, v8, v9}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 137
    .line 138
    .line 139
    iget-object v8, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->title:Ljava/lang/String;

    .line 140
    .line 141
    if-eqz v8, :cond_0

    .line 142
    .line 143
    const-string/jumbo v9, "t"

    .line 144
    .line 145
    .line 146
    invoke-virtual {v0, v1, v9, v8}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 147
    .line 148
    .line 149
    :cond_0
    const-string/jumbo v8, "tid"

    .line 150
    .line 151
    .line 152
    iget v9, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->taskId:I

    .line 153
    .line 154
    invoke-static {v9}, Ljava/lang/String;->valueOf(I)Ljava/lang/String;

    .line 155
    .line 156
    .line 157
    move-result-object v9

    .line 158
    invoke-virtual {v0, v1, v8, v9}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 159
    .line 160
    .line 161
    iget-object v8, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->locus:Ljava/lang/String;

    .line 162
    .line 163
    if-eqz v8, :cond_1

    .line 164
    .line 165
    const-string v9, "l"

    .line 166
    .line 167
    invoke-virtual {v0, v1, v9, v8}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 168
    .line 169
    .line 170
    :cond_1
    const-string v8, "d"

    .line 171
    .line 172
    iget-boolean v5, v5, Lcom/android/wm/shell/bubbles/storage/BubbleEntity;->isDismissable:Z

    .line 173
    .line 174
    invoke-static {v5}, Ljava/lang/String;->valueOf(Z)Ljava/lang/String;

    .line 175
    .line 176
    .line 177
    move-result-object v5

    .line 178
    invoke-virtual {v0, v1, v8, v5}, Lcom/android/internal/util/FastXmlSerializer;->attribute(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 179
    .line 180
    .line 181
    invoke-virtual {v0, v1, v7}, Lcom/android/internal/util/FastXmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;
    :try_end_0
    .catch Ljava/io/IOException; {:try_start_0 .. :try_end_0} :catch_0

    .line 182
    .line 183
    .line 184
    goto :goto_1

    .line 185
    :catch_0
    move-exception p0

    .line 186
    new-instance p1, Ljava/lang/RuntimeException;

    .line 187
    .line 188
    invoke-direct {p1, p0}, Ljava/lang/RuntimeException;-><init>(Ljava/lang/Throwable;)V

    .line 189
    .line 190
    .line 191
    throw p1

    .line 192
    :cond_2
    invoke-virtual {v0, v1, p0}, Lcom/android/internal/util/FastXmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 193
    .line 194
    .line 195
    add-int/lit8 v3, v3, 0x1

    .line 196
    .line 197
    goto/16 :goto_0

    .line 198
    .line 199
    :cond_3
    invoke-virtual {v0, v1, p0}, Lcom/android/internal/util/FastXmlSerializer;->endTag(Ljava/lang/String;Ljava/lang/String;)Lorg/xmlpull/v1/XmlSerializer;

    .line 200
    .line 201
    .line 202
    invoke-virtual {v0}, Lcom/android/internal/util/FastXmlSerializer;->endDocument()V

    .line 203
    .line 204
    .line 205
    return-void
.end method
