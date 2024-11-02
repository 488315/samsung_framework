package android.view;

import android.graphics.Insets;
import android.graphics.Rect;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.proto.ProtoOutputStream;
import android.view.DisplayCutout;
import android.view.WindowInsets;
import java.io.PrintWriter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import java.util.StringJoiner;

/* loaded from: classes4.dex */
public class InsetsState implements Parcelable {
    public static final Parcelable.Creator<InsetsState> CREATOR = new Parcelable.Creator<InsetsState>() { // from class: android.view.InsetsState.1
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public InsetsState createFromParcel(Parcel in) {
            return new InsetsState(in);
        }

        @Override // android.os.Parcelable.Creator
        public InsetsState[] newArray(int size) {
            return new InsetsState[size];
        }
    };
    static final int ISIDE_BOTTOM = 3;
    static final int ISIDE_FLOATING = 4;
    static final int ISIDE_LEFT = 0;
    static final int ISIDE_RIGHT = 2;
    static final int ISIDE_TOP = 1;
    static final int ISIDE_UNKNOWN = 5;
    private final DisplayCutout.ParcelableWrapper mDisplayCutout;
    private final Rect mDisplayFrame;
    private DisplayShape mDisplayShape;
    private PrivacyIndicatorBounds mPrivacyIndicatorBounds;
    private final Rect mRoundedCornerFrame;
    private RoundedCorners mRoundedCorners;
    private final SparseArray<InsetsSource> mSources;

    @Retention(RetentionPolicy.SOURCE)
    /* loaded from: classes4.dex */
    public @interface InternalInsetsSide {
    }

    public InsetsState() {
        this.mDisplayFrame = new Rect();
        this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        this.mRoundedCornerFrame = new Rect();
        this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
        this.mDisplayShape = DisplayShape.NONE;
        this.mSources = new SparseArray<>();
    }

    public InsetsState(InsetsState copy) {
        this(copy, false);
    }

    public InsetsState(InsetsState copy, boolean copySources) {
        this.mDisplayFrame = new Rect();
        this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        this.mRoundedCornerFrame = new Rect();
        this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
        this.mDisplayShape = DisplayShape.NONE;
        this.mSources = new SparseArray<>(copy.mSources.size());
        set(copy, copySources);
    }

    public WindowInsets calculateInsets(Rect frame, InsetsState ignoringVisibilityState, boolean isScreenRound, int legacySoftInputMode, int legacyWindowFlags, int legacySystemUiFlags, int windowType, int activityType, SparseIntArray idSideMap) {
        int forceConsumingTypes;
        int suppressScrimTypes;
        int i;
        int i2;
        boolean[] typeVisibilityMap;
        Insets[] typeMaxInsetsMap;
        Insets[] typeInsetsMap;
        InsetsSource ignoringVisibilitySource;
        InsetsState insetsState = ignoringVisibilityState;
        int i3 = legacyWindowFlags;
        Insets[] typeInsetsMap2 = new Insets[10];
        Insets[] typeMaxInsetsMap2 = new Insets[10];
        boolean[] typeVisibilityMap2 = new boolean[10];
        Rect relativeFrame = new Rect(frame);
        Rect relativeFrameMax = new Rect(frame);
        int forceConsumingTypes2 = 0;
        int suppressScrimTypes2 = 0;
        int i4 = this.mSources.size() - 1;
        while (i4 >= 0) {
            InsetsSource source = this.mSources.valueAt(i4);
            int type = source.getType();
            if ((source.getFlags() & 4) == 0) {
                forceConsumingTypes = forceConsumingTypes2;
            } else {
                forceConsumingTypes = forceConsumingTypes2 | type;
            }
            int forceConsumingTypes3 = source.getFlags();
            if ((forceConsumingTypes3 & 1) == 0) {
                suppressScrimTypes = suppressScrimTypes2;
            } else {
                suppressScrimTypes = suppressScrimTypes2 | type;
            }
            processSource(source, relativeFrame, false, typeInsetsMap2, idSideMap, typeVisibilityMap2);
            if (type == WindowInsets.Type.ime()) {
                i = legacyWindowFlags;
                i2 = i4;
                typeVisibilityMap = typeVisibilityMap2;
                typeMaxInsetsMap = typeMaxInsetsMap2;
                typeInsetsMap = typeInsetsMap2;
            } else {
                if (insetsState != null) {
                    ignoringVisibilitySource = insetsState.peekSource(source.getId());
                } else {
                    ignoringVisibilitySource = source;
                }
                if (ignoringVisibilitySource == null) {
                    i = legacyWindowFlags;
                    i2 = i4;
                    typeVisibilityMap = typeVisibilityMap2;
                    typeMaxInsetsMap = typeMaxInsetsMap2;
                    typeInsetsMap = typeInsetsMap2;
                } else {
                    i2 = i4;
                    typeVisibilityMap = typeVisibilityMap2;
                    typeMaxInsetsMap = typeMaxInsetsMap2;
                    typeInsetsMap = typeInsetsMap2;
                    i = legacyWindowFlags;
                    processSource(ignoringVisibilitySource, relativeFrameMax, true, typeMaxInsetsMap, null, null);
                }
            }
            i4 = i2 - 1;
            insetsState = ignoringVisibilityState;
            i3 = i;
            typeVisibilityMap2 = typeVisibilityMap;
            forceConsumingTypes2 = forceConsumingTypes;
            suppressScrimTypes2 = suppressScrimTypes;
            typeMaxInsetsMap2 = typeMaxInsetsMap;
            typeInsetsMap2 = typeInsetsMap;
        }
        boolean[] typeVisibilityMap3 = typeVisibilityMap2;
        Insets[] typeMaxInsetsMap3 = typeMaxInsetsMap2;
        Insets[] typeInsetsMap3 = typeInsetsMap2;
        int i5 = i3;
        int softInputAdjustMode = legacySoftInputMode & 240;
        int compatInsetsTypes = WindowInsets.Type.systemBars() | WindowInsets.Type.displayCutout();
        if (softInputAdjustMode == 16) {
            compatInsetsTypes |= WindowInsets.Type.ime();
        }
        if ((i5 & 1024) != 0) {
            compatInsetsTypes &= ~WindowInsets.Type.statusBars();
        }
        if (clearsCompatInsets(windowType, i5, activityType, forceConsumingTypes2)) {
            compatInsetsTypes = 0;
        }
        return new WindowInsets(typeInsetsMap3, typeMaxInsetsMap3, typeVisibilityMap3, isScreenRound, forceConsumingTypes2, suppressScrimTypes2, calculateRelativeCutout(frame), calculateRelativeRoundedCorners(frame), calculateRelativePrivacyIndicatorBounds(frame), calculateRelativeDisplayShape(frame), compatInsetsTypes, (legacySystemUiFlags & 256) != 0);
    }

    private DisplayCutout calculateRelativeCutout(Rect frame) {
        DisplayCutout raw = this.mDisplayCutout.get();
        if (this.mDisplayFrame.equals(frame)) {
            return raw;
        }
        if (frame == null) {
            return DisplayCutout.NO_CUTOUT;
        }
        int insetLeft = frame.left - this.mDisplayFrame.left;
        int insetTop = frame.top - this.mDisplayFrame.top;
        int insetRight = this.mDisplayFrame.right - frame.right;
        int insetBottom = this.mDisplayFrame.bottom - frame.bottom;
        if (insetLeft >= raw.getSafeInsetLeft() && insetTop >= raw.getSafeInsetTop() && insetRight >= raw.getSafeInsetRight() && insetBottom >= raw.getSafeInsetBottom()) {
            return DisplayCutout.NO_CUTOUT;
        }
        return raw.inset(insetLeft, insetTop, insetRight, insetBottom);
    }

    private RoundedCorners calculateRelativeRoundedCorners(Rect frame) {
        if (frame == null) {
            return RoundedCorners.NO_ROUNDED_CORNERS;
        }
        Rect roundedCornerFrame = new Rect(this.mRoundedCornerFrame);
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            if (source.insetsRoundedCornerFrame()) {
                Insets insets = source.calculateInsets(roundedCornerFrame, false);
                roundedCornerFrame.inset(insets);
            }
        }
        if (!roundedCornerFrame.isEmpty() && !roundedCornerFrame.equals(this.mDisplayFrame)) {
            return this.mRoundedCorners.insetWithFrame(frame, roundedCornerFrame);
        }
        if (this.mDisplayFrame.equals(frame)) {
            return this.mRoundedCorners;
        }
        int insetLeft = frame.left - this.mDisplayFrame.left;
        int insetTop = frame.top - this.mDisplayFrame.top;
        int insetRight = this.mDisplayFrame.right - frame.right;
        int insetBottom = this.mDisplayFrame.bottom - frame.bottom;
        return this.mRoundedCorners.inset(insetLeft, insetTop, insetRight, insetBottom);
    }

    private PrivacyIndicatorBounds calculateRelativePrivacyIndicatorBounds(Rect frame) {
        if (this.mDisplayFrame.equals(frame)) {
            return this.mPrivacyIndicatorBounds;
        }
        if (frame == null) {
            return null;
        }
        int insetLeft = frame.left - this.mDisplayFrame.left;
        int insetTop = frame.top - this.mDisplayFrame.top;
        int insetRight = this.mDisplayFrame.right - frame.right;
        int insetBottom = this.mDisplayFrame.bottom - frame.bottom;
        return this.mPrivacyIndicatorBounds.inset(insetLeft, insetTop, insetRight, insetBottom);
    }

    private DisplayShape calculateRelativeDisplayShape(Rect frame) {
        if (this.mDisplayFrame.equals(frame)) {
            return this.mDisplayShape;
        }
        if (frame == null) {
            return DisplayShape.NONE;
        }
        return this.mDisplayShape.setOffset(-frame.left, -frame.top);
    }

    public Insets calculateInsets(Rect frame, int types, boolean ignoreVisibility) {
        Insets insets = Insets.NONE;
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            if ((source.getType() & types) != 0) {
                insets = Insets.max(source.calculateInsets(frame, ignoreVisibility), insets);
            }
        }
        return insets;
    }

    public Insets calculateInsets(Rect frame, int types, int requestedVisibleTypes) {
        Insets insets = Insets.NONE;
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            if ((source.getType() & types & requestedVisibleTypes) != 0) {
                insets = Insets.max(source.calculateInsets(frame, true), insets);
            }
        }
        return insets;
    }

    public Insets calculateVisibleInsets(Rect frame, int windowType, int activityType, int softInputMode, int windowFlags) {
        int visibleInsetsTypes;
        int softInputAdjustMode = softInputMode & 240;
        if (softInputAdjustMode != 48) {
            visibleInsetsTypes = WindowInsets.Type.systemBars() | WindowInsets.Type.ime();
        } else {
            visibleInsetsTypes = WindowInsets.Type.systemBars();
        }
        int forceConsumingTypes = 0;
        Insets insets = Insets.NONE;
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            if ((source.getType() & visibleInsetsTypes) != 0) {
                if (source.hasFlags(4)) {
                    forceConsumingTypes |= source.getType();
                }
                insets = Insets.max(source.calculateVisibleInsets(frame), insets);
            }
        }
        if (clearsCompatInsets(windowType, windowFlags, activityType, forceConsumingTypes)) {
            return Insets.NONE;
        }
        return insets;
    }

    public int calculateUncontrollableInsetsFromFrame(Rect frame) {
        int blocked = 0;
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            if (!canControlSource(frame, source)) {
                blocked |= source.getType();
            }
        }
        return blocked;
    }

    private static boolean canControlSource(Rect frame, InsetsSource source) {
        Insets insets = source.calculateInsets(frame, true);
        Rect sourceFrame = source.getFrame();
        int sourceWidth = sourceFrame.width();
        int sourceHeight = sourceFrame.height();
        return insets.left == sourceWidth || insets.right == sourceWidth || insets.top == sourceHeight || insets.bottom == sourceHeight;
    }

    private void processSource(InsetsSource source, Rect relativeFrame, boolean ignoreVisibility, Insets[] typeInsetsMap, SparseIntArray idSideMap, boolean[] typeVisibilityMap) {
        Insets insets = source.calculateInsets(relativeFrame, ignoreVisibility);
        int type = source.getType();
        processSourceAsPublicType(source, typeInsetsMap, idSideMap, typeVisibilityMap, insets, type);
        if (type == 32) {
            processSourceAsPublicType(source, typeInsetsMap, idSideMap, typeVisibilityMap, insets, 16);
        }
        if (type == 4) {
            processSourceAsPublicType(source, typeInsetsMap, idSideMap, typeVisibilityMap, insets, 16);
            processSourceAsPublicType(source, typeInsetsMap, idSideMap, typeVisibilityMap, insets, 32);
            processSourceAsPublicType(source, typeInsetsMap, idSideMap, typeVisibilityMap, insets, 64);
        }
    }

    private void processSourceAsPublicType(InsetsSource source, Insets[] typeInsetsMap, SparseIntArray idSideMap, boolean[] typeVisibilityMap, Insets insets, int type) {
        int insetSide;
        int index = WindowInsets.Type.indexOf(type);
        Insets existing = typeInsetsMap[index];
        if (existing == null) {
            typeInsetsMap[index] = insets;
        } else {
            typeInsetsMap[index] = Insets.max(existing, insets);
        }
        if (typeVisibilityMap != null) {
            typeVisibilityMap[index] = source.isVisible();
        }
        if (idSideMap != null && (insetSide = getInsetSide(insets)) != 5) {
            idSideMap.put(source.getId(), insetSide);
        }
    }

    public static int getInsetSide(Insets insets) {
        if (Insets.NONE.equals(insets)) {
            return 4;
        }
        if (insets.left != 0) {
            return 0;
        }
        if (insets.top != 0) {
            return 1;
        }
        if (insets.right != 0) {
            return 2;
        }
        if (insets.bottom != 0) {
            return 3;
        }
        return 5;
    }

    public InsetsSource getOrCreateSource(int id, int type) {
        InsetsSource source = this.mSources.get(id);
        if (source != null) {
            return source;
        }
        InsetsSource source2 = new InsetsSource(id, type);
        this.mSources.put(id, source2);
        return source2;
    }

    public InsetsSource peekSource(int id) {
        return this.mSources.get(id);
    }

    public int sourceIdAt(int index) {
        return this.mSources.keyAt(index);
    }

    public InsetsSource sourceAt(int index) {
        return this.mSources.valueAt(index);
    }

    public int sourceSize() {
        return this.mSources.size();
    }

    public boolean isSourceOrDefaultVisible(int id, int type) {
        InsetsSource source = this.mSources.get(id);
        return source != null ? source.isVisible() : (WindowInsets.Type.defaultVisible() & type) != 0;
    }

    public void setDisplayFrame(Rect frame) {
        this.mDisplayFrame.set(frame);
    }

    public Rect getDisplayFrame() {
        return this.mDisplayFrame;
    }

    public void setDisplayCutout(DisplayCutout cutout) {
        this.mDisplayCutout.set(cutout);
    }

    public DisplayCutout getDisplayCutout() {
        return this.mDisplayCutout.get();
    }

    public void getDisplayCutoutSafe(Rect outBounds) {
        outBounds.set(-100000, -100000, 100000, 100000);
        DisplayCutout cutout = this.mDisplayCutout.get();
        Rect displayFrame = this.mDisplayFrame;
        if (!cutout.isEmpty()) {
            if (cutout.getSafeInsetLeft() > 0) {
                outBounds.left = displayFrame.left + cutout.getSafeInsetLeft();
            }
            if (cutout.getSafeInsetTop() > 0) {
                outBounds.top = displayFrame.top + cutout.getSafeInsetTop();
            }
            if (cutout.getSafeInsetRight() > 0) {
                outBounds.right = displayFrame.right - cutout.getSafeInsetRight();
            }
            if (cutout.getSafeInsetBottom() > 0) {
                outBounds.bottom = displayFrame.bottom - cutout.getSafeInsetBottom();
            }
        }
    }

    public void setRoundedCorners(RoundedCorners roundedCorners) {
        this.mRoundedCorners = roundedCorners;
    }

    public RoundedCorners getRoundedCorners() {
        return this.mRoundedCorners;
    }

    public void setRoundedCornerFrame(Rect frame) {
        this.mRoundedCornerFrame.set(frame);
    }

    public void setPrivacyIndicatorBounds(PrivacyIndicatorBounds bounds) {
        this.mPrivacyIndicatorBounds = bounds;
    }

    public PrivacyIndicatorBounds getPrivacyIndicatorBounds() {
        return this.mPrivacyIndicatorBounds;
    }

    public void setDisplayShape(DisplayShape displayShape) {
        this.mDisplayShape = displayShape;
    }

    public DisplayShape getDisplayShape() {
        return this.mDisplayShape;
    }

    public void removeSource(int id) {
        this.mSources.delete(id);
    }

    public void removeSourceAt(int index) {
        this.mSources.removeAt(index);
    }

    public void setSourceVisible(int id, boolean visible) {
        InsetsSource source = this.mSources.get(id);
        if (source != null) {
            source.setVisible(visible);
        }
    }

    public void scale(float scale) {
        this.mDisplayFrame.scale(scale);
        this.mDisplayCutout.scale(scale);
        this.mRoundedCorners = this.mRoundedCorners.scale(scale);
        this.mRoundedCornerFrame.scale(scale);
        this.mPrivacyIndicatorBounds = this.mPrivacyIndicatorBounds.scale(scale);
        this.mDisplayShape = this.mDisplayShape.setScale(scale);
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            source.getFrame().scale(scale);
            Rect visibleFrame = source.getVisibleFrame();
            if (visibleFrame != null) {
                visibleFrame.scale(scale);
            }
        }
    }

    public void set(InsetsState other) {
        set(other, false);
    }

    public void set(InsetsState other, boolean copySources) {
        InsetsSource insetsSource;
        this.mDisplayFrame.set(other.mDisplayFrame);
        this.mDisplayCutout.set(other.mDisplayCutout);
        this.mRoundedCorners = other.getRoundedCorners();
        this.mRoundedCornerFrame.set(other.mRoundedCornerFrame);
        this.mPrivacyIndicatorBounds = other.getPrivacyIndicatorBounds();
        this.mDisplayShape = other.getDisplayShape();
        this.mSources.clear();
        int size = other.mSources.size();
        for (int i = 0; i < size; i++) {
            InsetsSource otherSource = other.mSources.valueAt(i);
            SparseArray<InsetsSource> sparseArray = this.mSources;
            int id = otherSource.getId();
            if (copySources) {
                insetsSource = new InsetsSource(otherSource);
            } else {
                insetsSource = otherSource;
            }
            sparseArray.append(id, insetsSource);
        }
    }

    public void set(InsetsState other, int types) {
        this.mDisplayFrame.set(other.mDisplayFrame);
        this.mDisplayCutout.set(other.mDisplayCutout);
        this.mRoundedCorners = other.getRoundedCorners();
        this.mRoundedCornerFrame.set(other.mRoundedCornerFrame);
        this.mPrivacyIndicatorBounds = other.getPrivacyIndicatorBounds();
        this.mDisplayShape = other.getDisplayShape();
        if (types == 0) {
            return;
        }
        for (int i = this.mSources.size() - 1; i >= 0; i--) {
            InsetsSource source = this.mSources.valueAt(i);
            if ((source.getType() & types) != 0) {
                this.mSources.removeAt(i);
            }
        }
        for (int i2 = other.mSources.size() - 1; i2 >= 0; i2--) {
            InsetsSource otherSource = other.mSources.valueAt(i2);
            if ((otherSource.getType() & types) != 0) {
                this.mSources.put(otherSource.getId(), otherSource);
            }
        }
    }

    public void addSource(InsetsSource source) {
        this.mSources.put(source.getId(), source);
    }

    public static boolean clearsCompatInsets(int windowType, int windowFlags, int activityType, int forceConsumingTypes) {
        return ((windowFlags & 512) == 0 || windowType == 2013 || windowType == 2010 || (forceConsumingTypes != 0 && activityType == 1)) ? false : true;
    }

    public void dump(String prefix, PrintWriter pw) {
        String newPrefix = prefix + "  ";
        pw.println(prefix + "InsetsState");
        pw.println(newPrefix + "mDisplayFrame=" + this.mDisplayFrame);
        pw.println(newPrefix + "mDisplayCutout=" + this.mDisplayCutout.get());
        pw.println(newPrefix + "mRoundedCorners=" + this.mRoundedCorners);
        pw.println(newPrefix + "mRoundedCornerFrame=" + this.mRoundedCornerFrame);
        pw.println(newPrefix + "mPrivacyIndicatorBounds=" + this.mPrivacyIndicatorBounds);
        pw.println(newPrefix + "mDisplayShape=" + this.mDisplayShape);
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            this.mSources.valueAt(i).dump(newPrefix + "  ", pw);
        }
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        InsetsSource source = this.mSources.get(InsetsSource.ID_IME);
        if (source != null) {
            source.dumpDebug(proto, 2246267895809L);
        }
        this.mDisplayFrame.dumpDebug(proto, 1146756268034L);
        this.mDisplayCutout.get().dumpDebug(proto, 1146756268035L);
        proto.end(token);
    }

    public boolean equals(Object o) {
        return equals(o, false, false);
    }

    public boolean equals(Object o, boolean excludingCaptionInsets, boolean excludeInvisibleImeFrames) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        InsetsState state = (InsetsState) o;
        if (!this.mDisplayFrame.equals(state.mDisplayFrame) || !this.mDisplayCutout.equals(state.mDisplayCutout) || !this.mRoundedCorners.equals(state.mRoundedCorners) || !this.mRoundedCornerFrame.equals(state.mRoundedCornerFrame) || !this.mPrivacyIndicatorBounds.equals(state.mPrivacyIndicatorBounds) || !this.mDisplayShape.equals(state.mDisplayShape)) {
            return false;
        }
        SparseArray<InsetsSource> thisSources = this.mSources;
        SparseArray<InsetsSource> thatSources = state.mSources;
        if (!excludingCaptionInsets && !excludeInvisibleImeFrames) {
            return thisSources.contentEquals(thatSources);
        }
        int thisSize = thisSources.size();
        int thatSize = thatSources.size();
        int thisIndex = 0;
        int thatIndex = 0;
        while (thisIndex < thisSize && thatIndex < thatSize) {
            InsetsSource thisSource = thisSources.valueAt(thisIndex);
            while (true) {
                InsetsSource insetsSource = null;
                if (thisSource == null || !((excludingCaptionInsets && thisSource.getType() == WindowInsets.Type.captionBar()) || (excludeInvisibleImeFrames && thisSource.getType() == WindowInsets.Type.ime() && !thisSource.isVisible()))) {
                    break;
                }
                thisIndex++;
                if (thisIndex < thisSize) {
                    insetsSource = thisSources.valueAt(thisIndex);
                }
                thisSource = insetsSource;
            }
            InsetsSource thatSource = thatSources.valueAt(thatIndex);
            while (thatSource != null && ((excludingCaptionInsets && thatSource.getType() == WindowInsets.Type.captionBar()) || (excludeInvisibleImeFrames && thatSource.getType() == WindowInsets.Type.ime() && !thatSource.isVisible()))) {
                thatIndex++;
                thatSource = thatIndex < thatSize ? thatSources.valueAt(thatIndex) : null;
            }
            if (!Objects.equals(thisSource, thatSource)) {
                return false;
            }
            thisIndex++;
            thatIndex++;
        }
        return thisIndex >= thisSize && thatIndex >= thatSize;
    }

    public int hashCode() {
        return Objects.hash(this.mDisplayFrame, this.mDisplayCutout, Integer.valueOf(this.mSources.contentHashCode()), this.mRoundedCorners, this.mPrivacyIndicatorBounds, this.mRoundedCornerFrame, this.mDisplayShape);
    }

    public InsetsState(Parcel in) {
        this.mDisplayFrame = new Rect();
        this.mDisplayCutout = new DisplayCutout.ParcelableWrapper();
        this.mRoundedCornerFrame = new Rect();
        this.mRoundedCorners = RoundedCorners.NO_ROUNDED_CORNERS;
        this.mPrivacyIndicatorBounds = new PrivacyIndicatorBounds();
        this.mDisplayShape = DisplayShape.NONE;
        this.mSources = readFromParcel(in);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        this.mDisplayFrame.writeToParcel(dest, flags);
        this.mDisplayCutout.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mRoundedCorners, flags);
        this.mRoundedCornerFrame.writeToParcel(dest, flags);
        dest.writeTypedObject(this.mPrivacyIndicatorBounds, flags);
        dest.writeTypedObject(this.mDisplayShape, flags);
        int size = this.mSources.size();
        dest.writeInt(size);
        for (int i = 0; i < size; i++) {
            dest.writeTypedObject(this.mSources.valueAt(i), flags);
        }
    }

    /* renamed from: android.view.InsetsState$1 */
    /* loaded from: classes4.dex */
    class AnonymousClass1 implements Parcelable.Creator<InsetsState> {
        AnonymousClass1() {
        }

        @Override // android.os.Parcelable.Creator
        public InsetsState createFromParcel(Parcel in) {
            return new InsetsState(in);
        }

        @Override // android.os.Parcelable.Creator
        public InsetsState[] newArray(int size) {
            return new InsetsState[size];
        }
    }

    public SparseArray<InsetsSource> readFromParcel(Parcel in) {
        SparseArray<InsetsSource> sources;
        this.mDisplayFrame.readFromParcel(in);
        this.mDisplayCutout.readFromParcel(in);
        this.mRoundedCorners = (RoundedCorners) in.readTypedObject(RoundedCorners.CREATOR);
        this.mRoundedCornerFrame.readFromParcel(in);
        this.mPrivacyIndicatorBounds = (PrivacyIndicatorBounds) in.readTypedObject(PrivacyIndicatorBounds.CREATOR);
        this.mDisplayShape = (DisplayShape) in.readTypedObject(DisplayShape.CREATOR);
        int size = in.readInt();
        if (this.mSources == null) {
            sources = new SparseArray<>(size);
        } else {
            sources = this.mSources;
            sources.clear();
        }
        for (int i = 0; i < size; i++) {
            InsetsSource source = (InsetsSource) in.readTypedObject(InsetsSource.CREATOR);
            sources.append(source.getId(), source);
        }
        return sources;
    }

    public String toString() {
        StringJoiner joiner = new StringJoiner(", ");
        int size = this.mSources.size();
        for (int i = 0; i < size; i++) {
            joiner.add(this.mSources.valueAt(i).toString());
        }
        return "InsetsState: {mDisplayFrame=" + this.mDisplayFrame + ", mDisplayCutout=" + this.mDisplayCutout + ", mRoundedCorners=" + this.mRoundedCorners + "  mRoundedCornerFrame=" + this.mRoundedCornerFrame + ", mPrivacyIndicatorBounds=" + this.mPrivacyIndicatorBounds + ", mDisplayShape=" + this.mDisplayShape + ", mSources= { " + joiner + " }";
    }

    public static void traverse(InsetsState state1, InsetsState state2, OnTraverseCallbacks cb) {
        cb.onStart(state1, state2);
        int size1 = state1.sourceSize();
        int size2 = state2.sourceSize();
        int index1 = 0;
        int index2 = 0;
        while (index1 < size1 && index2 < size2) {
            int id1 = state1.sourceIdAt(index1);
            int id2 = state2.sourceIdAt(index2);
            while (id1 != id2) {
                if (id1 < id2) {
                    cb.onIdNotFoundInState2(index1, state1.sourceAt(index1));
                    index1++;
                    if (index1 >= size1) {
                        break;
                    } else {
                        id1 = state1.sourceIdAt(index1);
                    }
                } else {
                    cb.onIdNotFoundInState1(index2, state2.sourceAt(index2));
                    index2++;
                    if (index2 >= size2) {
                        break;
                    } else {
                        id2 = state2.sourceIdAt(index2);
                    }
                }
            }
            if (index1 >= size1 || index2 >= size2) {
                break;
            }
            InsetsSource source1 = state1.sourceAt(index1);
            InsetsSource source2 = state2.sourceAt(index2);
            cb.onIdMatch(source1, source2);
            index1++;
            index2++;
        }
        while (index2 < size2) {
            cb.onIdNotFoundInState1(index2, state2.sourceAt(index2));
            index2++;
        }
        while (index1 < size1) {
            cb.onIdNotFoundInState2(index1, state1.sourceAt(index1));
            index1++;
        }
        cb.onFinish(state1, state2);
    }

    /* loaded from: classes4.dex */
    public interface OnTraverseCallbacks {
        default void onStart(InsetsState state1, InsetsState state2) {
        }

        default void onIdMatch(InsetsSource source1, InsetsSource source2) {
        }

        default void onIdNotFoundInState1(int index2, InsetsSource source2) {
        }

        default void onIdNotFoundInState2(int index1, InsetsSource source1) {
        }

        default void onFinish(InsetsState state1, InsetsState state2) {
        }
    }
}
