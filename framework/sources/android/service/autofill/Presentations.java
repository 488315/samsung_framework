package android.service.autofill;

import android.widget.RemoteViews;

/* loaded from: classes3.dex */
public final class Presentations {
    private RemoteViews mDialogPresentation;
    private InlinePresentation mInlinePresentation;
    private InlinePresentation mInlineTooltipPresentation;
    private RemoteViews mMenuPresentation;

    /* renamed from: -$$Nest$smdefaultDialogPresentation */
    static /* bridge */ /* synthetic */ RemoteViews m3599$$Nest$smdefaultDialogPresentation() {
        return defaultDialogPresentation();
    }

    /* renamed from: -$$Nest$smdefaultInlinePresentation */
    static /* bridge */ /* synthetic */ InlinePresentation m3600$$Nest$smdefaultInlinePresentation() {
        return defaultInlinePresentation();
    }

    /* renamed from: -$$Nest$smdefaultInlineTooltipPresentation */
    static /* bridge */ /* synthetic */ InlinePresentation m3601$$Nest$smdefaultInlineTooltipPresentation() {
        return defaultInlineTooltipPresentation();
    }

    /* renamed from: -$$Nest$smdefaultMenuPresentation */
    static /* bridge */ /* synthetic */ RemoteViews m3602$$Nest$smdefaultMenuPresentation() {
        return defaultMenuPresentation();
    }

    private static RemoteViews defaultMenuPresentation() {
        return null;
    }

    private static InlinePresentation defaultInlinePresentation() {
        return null;
    }

    private static RemoteViews defaultDialogPresentation() {
        return null;
    }

    private static InlinePresentation defaultInlineTooltipPresentation() {
        return null;
    }

    private void onConstructed() {
        if (this.mMenuPresentation == null && this.mInlinePresentation == null && this.mDialogPresentation == null) {
            throw new IllegalStateException("All presentations are null.");
        }
        if (this.mInlineTooltipPresentation != null && this.mInlinePresentation == null) {
            throw new IllegalStateException("The inline presentation is required for mInlineTooltipPresentation.");
        }
    }

    Presentations(RemoteViews menuPresentation, InlinePresentation inlinePresentation, RemoteViews dialogPresentation, InlinePresentation inlineTooltipPresentation) {
        this.mMenuPresentation = menuPresentation;
        this.mInlinePresentation = inlinePresentation;
        this.mDialogPresentation = dialogPresentation;
        this.mInlineTooltipPresentation = inlineTooltipPresentation;
        onConstructed();
    }

    public RemoteViews getMenuPresentation() {
        return this.mMenuPresentation;
    }

    public InlinePresentation getInlinePresentation() {
        return this.mInlinePresentation;
    }

    public RemoteViews getDialogPresentation() {
        return this.mDialogPresentation;
    }

    public InlinePresentation getInlineTooltipPresentation() {
        return this.mInlineTooltipPresentation;
    }

    /* loaded from: classes3.dex */
    public static final class Builder {
        private long mBuilderFieldsSet = 0;
        private RemoteViews mDialogPresentation;
        private InlinePresentation mInlinePresentation;
        private InlinePresentation mInlineTooltipPresentation;
        private RemoteViews mMenuPresentation;

        public Builder setMenuPresentation(RemoteViews value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 1;
            this.mMenuPresentation = value;
            return this;
        }

        public Builder setInlinePresentation(InlinePresentation value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 2;
            this.mInlinePresentation = value;
            return this;
        }

        public Builder setDialogPresentation(RemoteViews value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 4;
            this.mDialogPresentation = value;
            return this;
        }

        public Builder setInlineTooltipPresentation(InlinePresentation value) {
            checkNotUsed();
            this.mBuilderFieldsSet |= 8;
            this.mInlineTooltipPresentation = value;
            return this;
        }

        public Presentations build() {
            checkNotUsed();
            long j = this.mBuilderFieldsSet | 16;
            this.mBuilderFieldsSet = j;
            if ((j & 1) == 0) {
                this.mMenuPresentation = Presentations.m3602$$Nest$smdefaultMenuPresentation();
            }
            if ((this.mBuilderFieldsSet & 2) == 0) {
                this.mInlinePresentation = Presentations.m3600$$Nest$smdefaultInlinePresentation();
            }
            if ((this.mBuilderFieldsSet & 4) == 0) {
                this.mDialogPresentation = Presentations.m3599$$Nest$smdefaultDialogPresentation();
            }
            if ((this.mBuilderFieldsSet & 8) == 0) {
                this.mInlineTooltipPresentation = Presentations.m3601$$Nest$smdefaultInlineTooltipPresentation();
            }
            Presentations o = new Presentations(this.mMenuPresentation, this.mInlinePresentation, this.mDialogPresentation, this.mInlineTooltipPresentation);
            return o;
        }

        private void checkNotUsed() {
            if ((this.mBuilderFieldsSet & 16) != 0) {
                throw new IllegalStateException("This Builder should not be reused. Use a new Builder instance instead");
            }
        }
    }

    @Deprecated
    private void __metadata() {
    }
}
