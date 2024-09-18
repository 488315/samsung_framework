package com.samsung.vekit.Content;

import com.samsung.vekit.Common.Object.Element;
import com.samsung.vekit.Common.Type.ContentType;
import com.samsung.vekit.Common.Type.ElementType;
import com.samsung.vekit.Common.VEContext;

/* loaded from: classes6.dex */
public class Content extends Element {
    protected ContentType contentType;
    protected long duration;
    protected int height;
    protected int width;

    /* JADX INFO: Access modifiers changed from: protected */
    public Content(VEContext context, ContentType type, int id, String name) {
        super(context, ElementType.CONTENT, id, name);
        this.contentType = type;
        this.TAG = getClass().getSimpleName();
        this.width = 0;
        this.height = 0;
        this.duration = 0L;
    }

    public ContentType getContentType() {
        return this.contentType;
    }

    public int getWidth() {
        return this.width;
    }

    public int getHeight() {
        return this.height;
    }

    public long getDuration() {
        return this.duration;
    }

    public Content setWidth(int width) {
        this.width = width;
        return this;
    }

    public Content setHeight(int height) {
        this.height = height;
        return this;
    }

    public Content setDuration(long duration) {
        this.duration = duration;
        return this;
    }
}
