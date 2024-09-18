package com.samsung.android.sume.core.format;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.function.BiConsumer;

/* loaded from: classes4.dex */
public interface UpdatableMediaFormat extends MediaFormat {
    public static final String UPDATE_AT_ALLOC = "update-at-alloc";

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface UpdatableAttribute {
    }

    Shape getCroppedShape();

    UpdatableMediaFormat set(String str);

    UpdatableMediaFormat setUpdater(BiConsumer<MediaFormat, MutableMediaFormat> biConsumer);

    MediaFormat update();

    UpdatableMediaFormat with(MediaFormat mediaFormat);

    static UpdatableMediaFormat of(MediaFormat format) {
        return new StapleUpdatableMediaFormat(format);
    }
}
