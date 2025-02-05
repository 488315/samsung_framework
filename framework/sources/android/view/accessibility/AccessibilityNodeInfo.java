package android.view.accessibility;

import android.app.admin.DevicePolicyResources;
import android.graphics.Rect;
import android.graphics.Region;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AccessibilityClickableSpan;
import android.text.style.AccessibilityReplacementSpan;
import android.text.style.AccessibilityURLSpan;
import android.text.style.ClickableSpan;
import android.text.style.ReplacementSpan;
import android.text.style.URLSpan;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.Log;
import android.util.LongArray;
import android.util.Size;
import android.view.View;
import android.view.ViewRootImpl;
import com.android.internal.util.BitUtils;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/* loaded from: classes4.dex */
public class AccessibilityNodeInfo implements Parcelable {
    public static final int ACTION_ACCESSIBILITY_FOCUS = 64;
    public static final String ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN = "android.view.accessibility.action.ACTION_ARGUMENT_ACCESSIBLE_CLICKABLE_SPAN";
    public static final String ACTION_ARGUMENT_COLUMN_INT = "android.view.accessibility.action.ARGUMENT_COLUMN_INT";
    public static final String ACTION_ARGUMENT_DIRECTION_INT = "android.view.accessibility.action.ARGUMENT_DIRECTION_INT";
    public static final String ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN = "ACTION_ARGUMENT_EXTEND_SELECTION_BOOLEAN";
    public static final String ACTION_ARGUMENT_HTML_ELEMENT_STRING = "ACTION_ARGUMENT_HTML_ELEMENT_STRING";
    public static final String ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT = "ACTION_ARGUMENT_MOVEMENT_GRANULARITY_INT";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_X = "ACTION_ARGUMENT_MOVE_WINDOW_X";
    public static final String ACTION_ARGUMENT_MOVE_WINDOW_Y = "ACTION_ARGUMENT_MOVE_WINDOW_Y";
    public static final String ACTION_ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT = "android.view.accessibility.action.ARGUMENT_PRESS_AND_HOLD_DURATION_MILLIS_INT";
    public static final String ACTION_ARGUMENT_PROGRESS_VALUE = "android.view.accessibility.action.ARGUMENT_PROGRESS_VALUE";
    public static final String ACTION_ARGUMENT_ROW_INT = "android.view.accessibility.action.ARGUMENT_ROW_INT";
    public static final String ACTION_ARGUMENT_SCROLL_AMOUNT_FLOAT = "android.view.accessibility.action.ARGUMENT_SCROLL_AMOUNT_FLOAT";
    public static final String ACTION_ARGUMENT_SELECTION_END_INT = "ACTION_ARGUMENT_SELECTION_END_INT";
    public static final String ACTION_ARGUMENT_SELECTION_START_INT = "ACTION_ARGUMENT_SELECTION_START_INT";
    public static final String ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE = "ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE";
    public static final int ACTION_CLEAR_ACCESSIBILITY_FOCUS = 128;
    public static final int ACTION_CLEAR_FOCUS = 2;
    public static final int ACTION_CLEAR_SELECTION = 8;
    public static final int ACTION_CLICK = 16;
    public static final int ACTION_COLLAPSE = 524288;
    public static final int ACTION_COPY = 16384;
    public static final int ACTION_CUT = 65536;
    public static final int ACTION_DISMISS = 1048576;
    public static final int ACTION_EXPAND = 262144;
    public static final int ACTION_FOCUS = 1;
    public static final int ACTION_LONG_CLICK = 32;
    public static final int ACTION_NEXT_AT_MOVEMENT_GRANULARITY = 256;
    public static final int ACTION_NEXT_HTML_ELEMENT = 1024;
    public static final int ACTION_PASTE = 32768;
    public static final int ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = 512;
    public static final int ACTION_PREVIOUS_HTML_ELEMENT = 2048;
    public static final int ACTION_SCROLL_BACKWARD = 8192;
    public static final int ACTION_SCROLL_FORWARD = 4096;
    public static final int ACTION_SELECT = 4;
    public static final int ACTION_SET_SELECTION = 131072;
    public static final int ACTION_SET_TEXT = 2097152;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_DATA_SENSITIVE = 33554432;
    private static final int BOOLEAN_PROPERTY_ACCESSIBILITY_FOCUSED = 1024;
    private static final int BOOLEAN_PROPERTY_CHECKABLE = 1;
    private static final int BOOLEAN_PROPERTY_CHECKED = 2;
    private static final int BOOLEAN_PROPERTY_CLICKABLE = 32;
    private static final int BOOLEAN_PROPERTY_CONTENT_INVALID = 65536;
    private static final int BOOLEAN_PROPERTY_CONTEXT_CLICKABLE = 131072;
    private static final int BOOLEAN_PROPERTY_DISMISSABLE = 16384;
    private static final int BOOLEAN_PROPERTY_EDITABLE = 4096;
    private static final int BOOLEAN_PROPERTY_ENABLED = 128;
    private static final int BOOLEAN_PROPERTY_FOCUSABLE = 4;
    private static final int BOOLEAN_PROPERTY_FOCUSED = 8;
    private static final int BOOLEAN_PROPERTY_IMPORTANCE = 262144;
    private static final int BOOLEAN_PROPERTY_IS_HEADING = 2097152;
    private static final int BOOLEAN_PROPERTY_IS_SHOWING_HINT = 1048576;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_ENTRY_KEY = 4194304;
    private static final int BOOLEAN_PROPERTY_IS_TEXT_SELECTABLE = 8388608;
    private static final int BOOLEAN_PROPERTY_LONG_CLICKABLE = 64;
    private static final int BOOLEAN_PROPERTY_MULTI_LINE = 32768;
    private static final int BOOLEAN_PROPERTY_OPENS_POPUP = 8192;
    private static final int BOOLEAN_PROPERTY_PASSWORD = 256;
    private static final int BOOLEAN_PROPERTY_REQUEST_INITIAL_ACCESSIBILITY_FOCUS = 16777216;
    private static final int BOOLEAN_PROPERTY_SCREEN_READER_FOCUSABLE = 524288;
    private static final int BOOLEAN_PROPERTY_SCROLLABLE = 512;
    private static final int BOOLEAN_PROPERTY_SELECTED = 16;
    private static final int BOOLEAN_PROPERTY_SUPPORTS_GRANULAR_SCROLLING = 67108864;
    private static final int BOOLEAN_PROPERTY_VISIBLE_TO_USER = 2048;
    public static final Parcelable.Creator<AccessibilityNodeInfo> CREATOR;
    private static final boolean DEBUG;
    private static final AccessibilityNodeInfo DEFAULT;
    public static final String EXTRA_DATA_RENDERING_INFO_KEY = "android.view.accessibility.extra.DATA_RENDERING_INFO_KEY";
    public static final String EXTRA_DATA_REQUESTED_KEY = "android.view.accessibility.AccessibilityNodeInfo.extra_data_requested";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH";
    public static final int EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_MAX_LENGTH = 20000;
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_ARG_START_INDEX";
    public static final String EXTRA_DATA_TEXT_CHARACTER_LOCATION_KEY = "android.view.accessibility.extra.DATA_TEXT_CHARACTER_LOCATION_KEY";
    public static final int FLAG_PREFETCH_ANCESTORS = 1;
    public static final int FLAG_PREFETCH_DESCENDANTS_BREADTH_FIRST = 16;
    public static final int FLAG_PREFETCH_DESCENDANTS_DEPTH_FIRST = 8;
    public static final int FLAG_PREFETCH_DESCENDANTS_HYBRID = 4;
    public static final int FLAG_PREFETCH_DESCENDANTS_MASK = 28;
    public static final int FLAG_PREFETCH_MASK = 63;
    public static final int FLAG_PREFETCH_SIBLINGS = 2;
    public static final int FLAG_PREFETCH_UNINTERRUPTIBLE = 32;
    public static final int FLAG_REPORT_MASK = 896;
    public static final int FLAG_SERVICE_IS_ACCESSIBILITY_TOOL = 512;
    public static final int FLAG_SERVICE_REQUESTS_INCLUDE_NOT_IMPORTANT_VIEWS = 128;
    public static final int FLAG_SERVICE_REQUESTS_REPORT_VIEW_IDS = 256;
    public static final int FOCUS_ACCESSIBILITY = 2;
    public static final int FOCUS_INPUT = 1;
    private static final int INVALID_ACTIONS_MASK = -4194304;
    public static final int LAST_LEGACY_STANDARD_ACTION = 2097152;
    public static final int LEASHED_ITEM_ID = 2147483645;
    public static final long LEASHED_NODE_ID;
    public static final int MAX_NUMBER_OF_PREFETCHED_NODES = 50;
    public static final int MOVEMENT_GRANULARITY_CHARACTER = 1;
    public static final int MOVEMENT_GRANULARITY_LINE = 4;
    public static final int MOVEMENT_GRANULARITY_PAGE = 16;
    public static final int MOVEMENT_GRANULARITY_PARAGRAPH = 8;
    public static final int MOVEMENT_GRANULARITY_WORD = 2;
    public static final int ROOT_ITEM_ID = 2147483646;
    public static final long ROOT_NODE_ID;
    public static final int SEM_ACTION_AUTOSCROLL_DOWN = 33554432;
    public static final int SEM_ACTION_AUTOSCROLL_OFF = 8388608;
    public static final int SEM_ACTION_AUTOSCROLL_ON = 4194304;
    public static final int SEM_ACTION_AUTOSCROLL_SPEED_DOWN = 536870912;
    public static final int SEM_ACTION_AUTOSCROLL_SPEED_UP = 268435456;
    public static final int SEM_ACTION_AUTOSCROLL_TOP = 67108864;
    public static final int SEM_ACTION_AUTOSCROLL_UP = 16777216;
    private static final String TAG = "AccessibilityNodeInfo";
    public static final int UNDEFINED_CONNECTION_ID = -1;
    public static final int UNDEFINED_ITEM_ID = Integer.MAX_VALUE;
    public static final long UNDEFINED_NODE_ID;
    public static final int UNDEFINED_SELECTION_INDEX = -1;
    private static final long VIRTUAL_DESCENDANT_ID_MASK = -4294967296L;
    private static final int VIRTUAL_DESCENDANT_ID_SHIFT = 32;
    private ArrayList<AccessibilityAction> mActions;
    private int mBooleanProperties;
    private LongArray mChildNodeIds;
    private CharSequence mClassName;
    private CollectionInfo mCollectionInfo;
    private CollectionItemInfo mCollectionItemInfo;
    private CharSequence mContainerTitle;
    private CharSequence mContentDescription;
    private int mDrawingOrderInParent;
    private CharSequence mError;
    private ArrayList<String> mExtraDataKeys;
    private ExtraRenderingInfo mExtraRenderingInfo;
    private Bundle mExtras;
    private CharSequence mHintText;
    private IBinder mLeashedChild;
    private IBinder mLeashedParent;
    private int mMovementGranularities;
    private CharSequence mOriginalText;
    private CharSequence mPackageName;
    private CharSequence mPaneTitle;
    private RangeInfo mRangeInfo;
    private boolean mSealed;
    private CharSequence mStateDescription;
    private CharSequence mText;
    private CharSequence mTooltipText;
    private TouchDelegateInfo mTouchDelegateInfo;
    private String mUniqueId;
    private String mViewIdResourceName;
    private int mWindowId = -1;
    private long mSourceNodeId = UNDEFINED_NODE_ID;
    private long mParentNodeId = UNDEFINED_NODE_ID;
    private long mLabelForId = UNDEFINED_NODE_ID;
    private long mLabeledById = UNDEFINED_NODE_ID;
    private long mTraversalBefore = UNDEFINED_NODE_ID;
    private long mTraversalAfter = UNDEFINED_NODE_ID;
    private long mMinDurationBetweenContentChanges = 0;
    private final Rect mBoundsInParent = new Rect();
    private final Rect mBoundsInScreen = new Rect();
    private final Rect mBoundsInWindow = new Rect();
    private int mMaxTextLength = -1;
    private int mTextSelectionStart = -1;
    private int mTextSelectionEnd = -1;
    private int mInputType = 0;
    private int mLiveRegion = 0;
    private int mConnectionId = -1;
    private long mLeashedParentNodeId = UNDEFINED_NODE_ID;

    @Retention(RetentionPolicy.SOURCE)
    public @interface PrefetchingStrategy {
    }

    static {
        DEBUG = Log.isLoggable(TAG, 3) && Build.IS_DEBUGGABLE;
        UNDEFINED_NODE_ID = makeNodeId(Integer.MAX_VALUE, Integer.MAX_VALUE);
        ROOT_NODE_ID = makeNodeId(2147483646, -1);
        LEASHED_NODE_ID = makeNodeId(LEASHED_ITEM_ID, -1);
        DEFAULT = new AccessibilityNodeInfo();
        CREATOR = new Parcelable.Creator<AccessibilityNodeInfo>() { // from class: android.view.accessibility.AccessibilityNodeInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityNodeInfo createFromParcel(Parcel parcel) {
                AccessibilityNodeInfo info = new AccessibilityNodeInfo();
                info.initFromParcel(parcel);
                return info;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityNodeInfo[] newArray(int size) {
                return new AccessibilityNodeInfo[size];
            }
        };
    }

    public static int getAccessibilityViewId(long accessibilityNodeId) {
        return (int) accessibilityNodeId;
    }

    public static int getVirtualDescendantId(long accessibilityNodeId) {
        return (int) ((VIRTUAL_DESCENDANT_ID_MASK & accessibilityNodeId) >> 32);
    }

    public static long makeNodeId(int accessibilityViewId, int virtualDescendantId) {
        return (virtualDescendantId << 32) | accessibilityViewId;
    }

    public AccessibilityNodeInfo() {
    }

    public AccessibilityNodeInfo(View source) {
        setSource(source);
    }

    public AccessibilityNodeInfo(View root, int virtualDescendantId) {
        setSource(root, virtualDescendantId);
    }

    public AccessibilityNodeInfo(AccessibilityNodeInfo info) {
        init(info);
    }

    public void setSource(View source) {
        setSource(source, -1);
    }

    public void setSource(View root, int virtualDescendantId) {
        enforceNotSealed();
        this.mWindowId = root != null ? root.getAccessibilityWindowId() : Integer.MAX_VALUE;
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mSourceNodeId = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
    }

    public AccessibilityNodeInfo findFocus(int focus) {
        enforceSealed();
        enforceValidFocusType(focus);
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().findFocus(this.mConnectionId, this.mWindowId, this.mSourceNodeId, focus);
    }

    public AccessibilityNodeInfo focusSearch(int direction) {
        enforceSealed();
        enforceValidFocusDirection(direction);
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        return AccessibilityInteractionClient.getInstance().focusSearch(this.mConnectionId, this.mWindowId, this.mSourceNodeId, direction);
    }

    public int getWindowId() {
        return this.mWindowId;
    }

    public boolean refresh(Bundle arguments, boolean bypassCache) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        AccessibilityNodeInfo refreshedInfo = client.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, bypassCache, 0, arguments);
        if (refreshedInfo == null) {
            return false;
        }
        init(refreshedInfo);
        return true;
    }

    public boolean refresh() {
        return refresh(null, true);
    }

    public boolean refreshWithExtraData(String extraDataKey, Bundle args) {
        if (args.getInt(EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, -1) > 20000) {
            args.putInt(EXTRA_DATA_TEXT_CHARACTER_LOCATION_ARG_LENGTH, 20000);
        }
        args.putString(EXTRA_DATA_REQUESTED_KEY, extraDataKey);
        return refresh(args, true);
    }

    public LongArray getChildNodeIds() {
        return this.mChildNodeIds;
    }

    public long getChildId(int index) {
        if (this.mChildNodeIds == null) {
            throw new IndexOutOfBoundsException();
        }
        return this.mChildNodeIds.get(index);
    }

    public int getChildCount() {
        if (this.mChildNodeIds == null) {
            return 0;
        }
        return this.mChildNodeIds.size();
    }

    public AccessibilityNodeInfo getChild(int index) {
        return getChild(index, 4);
    }

    public AccessibilityNodeInfo getChild(int index, int prefetchingStrategy) {
        enforceSealed();
        if (this.mChildNodeIds == null || !canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        long childId = this.mChildNodeIds.get(index);
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        if (this.mLeashedChild != null && childId == LEASHED_NODE_ID) {
            return client.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mLeashedChild, ROOT_NODE_ID, false, prefetchingStrategy, (Bundle) null);
        }
        return client.findAccessibilityNodeInfoByAccessibilityId(this.mConnectionId, this.mWindowId, childId, false, prefetchingStrategy, (Bundle) null);
    }

    public void addChild(View child) {
        addChildInternal(child, -1, true);
    }

    public void addChild(IBinder token) {
        enforceNotSealed();
        if (token == null) {
            return;
        }
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new LongArray();
        }
        this.mLeashedChild = token;
        if (this.mChildNodeIds.indexOf(LEASHED_NODE_ID) >= 0) {
            return;
        }
        this.mChildNodeIds.add(LEASHED_NODE_ID);
    }

    public void addChildUnchecked(View child) {
        addChildInternal(child, -1, false);
    }

    public boolean removeChild(View child) {
        return removeChild(child, -1);
    }

    public boolean removeChild(IBinder token) {
        enforceNotSealed();
        if (this.mChildNodeIds == null || this.mLeashedChild == null || !this.mLeashedChild.equals(token)) {
            return false;
        }
        int index = this.mChildNodeIds.indexOf(LEASHED_NODE_ID);
        this.mLeashedChild = null;
        if (index < 0) {
            return false;
        }
        this.mChildNodeIds.remove(index);
        return true;
    }

    public void addChild(View root, int virtualDescendantId) {
        addChildInternal(root, virtualDescendantId, true);
    }

    private void addChildInternal(View root, int virtualDescendantId, boolean checked) {
        enforceNotSealed();
        if (this.mChildNodeIds == null) {
            this.mChildNodeIds = new LongArray();
        }
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        long childNodeId = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
        if (childNodeId == this.mSourceNodeId) {
            Log.e(TAG, "Rejecting attempt to make a View its own child");
        } else {
            if (checked && this.mChildNodeIds.indexOf(childNodeId) >= 0) {
                return;
            }
            this.mChildNodeIds.add(childNodeId);
        }
    }

    public boolean removeChild(View root, int virtualDescendantId) {
        enforceNotSealed();
        LongArray childIds = this.mChildNodeIds;
        if (childIds == null) {
            return false;
        }
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        long childNodeId = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
        int index = childIds.indexOf(childNodeId);
        if (index < 0) {
            return false;
        }
        childIds.remove(index);
        return true;
    }

    public List<AccessibilityAction> getActionList() {
        return CollectionUtils.emptyIfNull(this.mActions);
    }

    @Deprecated
    public int getActions() {
        int returnValue = 0;
        if (this.mActions == null) {
            return 0;
        }
        int actionSize = this.mActions.size();
        for (int i = 0; i < actionSize; i++) {
            int actionId = this.mActions.get(i).getId();
            if (actionId <= 2097152) {
                returnValue |= actionId;
            }
        }
        return returnValue;
    }

    public void addAction(AccessibilityAction action) {
        enforceNotSealed();
        addActionUnchecked(action);
    }

    private void addActionUnchecked(AccessibilityAction action) {
        if (action == null) {
            return;
        }
        if (this.mActions == null) {
            this.mActions = new ArrayList<>();
        }
        this.mActions.remove(action);
        this.mActions.add(action);
    }

    @Deprecated
    public void addAction(int action) {
        enforceNotSealed();
        if ((INVALID_ACTIONS_MASK & action) != 0) {
            throw new IllegalArgumentException("Action is not a combination of the standard actions: " + action);
        }
        addStandardActions(action);
    }

    @Deprecated
    public void removeAction(int action) {
        enforceNotSealed();
        removeAction(getActionSingleton(action));
    }

    public boolean removeAction(AccessibilityAction action) {
        enforceNotSealed();
        if (this.mActions == null || action == null) {
            return false;
        }
        return this.mActions.remove(action);
    }

    public void removeAllActions() {
        if (this.mActions != null) {
            this.mActions.clear();
        }
    }

    public AccessibilityNodeInfo getTraversalBefore() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalBefore);
    }

    public void setTraversalBefore(View view) {
        setTraversalBefore(view, -1);
    }

    public void setTraversalBefore(View root, int virtualDescendantId) {
        enforceNotSealed();
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mTraversalBefore = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
    }

    public AccessibilityNodeInfo getTraversalAfter() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTraversalAfter);
    }

    public void setTraversalAfter(View view) {
        setTraversalAfter(view, -1);
    }

    public void setTraversalAfter(View root, int virtualDescendantId) {
        enforceNotSealed();
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mTraversalAfter = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
    }

    public List<String> getAvailableExtraData() {
        if (this.mExtraDataKeys != null) {
            return Collections.unmodifiableList(this.mExtraDataKeys);
        }
        return Collections.EMPTY_LIST;
    }

    public void setAvailableExtraData(List<String> extraDataKeys) {
        enforceNotSealed();
        this.mExtraDataKeys = new ArrayList<>(extraDataKeys);
    }

    public void setMaxTextLength(int max) {
        enforceNotSealed();
        this.mMaxTextLength = max;
    }

    public int getMaxTextLength() {
        return this.mMaxTextLength;
    }

    public void setMovementGranularities(int granularities) {
        enforceNotSealed();
        this.mMovementGranularities = granularities;
    }

    public int getMovementGranularities() {
        return this.mMovementGranularities;
    }

    public void setMinDurationBetweenContentChanges(Duration duration) {
        enforceNotSealed();
        this.mMinDurationBetweenContentChanges = duration.toMillis();
    }

    public Duration getMinDurationBetweenContentChanges() {
        return Duration.ofMillis(this.mMinDurationBetweenContentChanges);
    }

    public boolean performAction(int action) {
        Bundle arguments;
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        if (this.mExtras == null) {
            arguments = null;
        } else {
            Bundle arguments2 = this.mExtras;
            arguments = arguments2;
        }
        return client.performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, action, arguments);
    }

    public boolean performAction(int action, Bundle arguments) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return false;
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        return client.performAccessibilityAction(this.mConnectionId, this.mWindowId, this.mSourceNodeId, action, arguments);
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String text) {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return Collections.emptyList();
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        return client.findAccessibilityNodeInfosByText(this.mConnectionId, this.mWindowId, this.mSourceNodeId, text);
    }

    public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByViewId(String viewId) {
        enforceSealed();
        if (viewId == null) {
            Log.e(TAG, "returns empty list due to null viewId.");
            return Collections.emptyList();
        }
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return Collections.emptyList();
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        return client.findAccessibilityNodeInfosByViewId(this.mConnectionId, this.mWindowId, this.mSourceNodeId, viewId);
    }

    public AccessibilityWindowInfo getWindow() {
        enforceSealed();
        if (!canPerformRequestOverConnection(this.mConnectionId, this.mWindowId, this.mSourceNodeId)) {
            return null;
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        return client.getWindow(this.mConnectionId, this.mWindowId);
    }

    public AccessibilityNodeInfo getParent() {
        enforceSealed();
        if (this.mLeashedParent != null && this.mLeashedParentNodeId != UNDEFINED_NODE_ID) {
            return getNodeForAccessibilityId(this.mConnectionId, this.mLeashedParent, this.mLeashedParentNodeId, 3);
        }
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId);
    }

    public AccessibilityNodeInfo getParent(int prefetchingStrategy) {
        enforceSealed();
        if (this.mLeashedParent != null && this.mLeashedParentNodeId != UNDEFINED_NODE_ID) {
            return getNodeForAccessibilityId(this.mConnectionId, this.mLeashedParent, this.mLeashedParentNodeId, prefetchingStrategy);
        }
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mParentNodeId, prefetchingStrategy);
    }

    public long getParentNodeId() {
        return this.mParentNodeId;
    }

    public void setParent(View parent) {
        setParent(parent, -1);
    }

    public void setParent(View root, int virtualDescendantId) {
        enforceNotSealed();
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mParentNodeId = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
    }

    @Deprecated
    public void getBoundsInParent(Rect outBounds) {
        outBounds.set(this.mBoundsInParent.left, this.mBoundsInParent.top, this.mBoundsInParent.right, this.mBoundsInParent.bottom);
    }

    @Deprecated
    public void setBoundsInParent(Rect bounds) {
        enforceNotSealed();
        this.mBoundsInParent.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public void getBoundsInScreen(Rect outBounds) {
        outBounds.set(this.mBoundsInScreen.left, this.mBoundsInScreen.top, this.mBoundsInScreen.right, this.mBoundsInScreen.bottom);
    }

    public Rect getBoundsInScreen() {
        return this.mBoundsInScreen;
    }

    public void setBoundsInScreen(Rect bounds) {
        enforceNotSealed();
        this.mBoundsInScreen.set(bounds.left, bounds.top, bounds.right, bounds.bottom);
    }

    public void getBoundsInWindow(Rect outBounds) {
        outBounds.set(this.mBoundsInWindow.left, this.mBoundsInWindow.top, this.mBoundsInWindow.right, this.mBoundsInWindow.bottom);
    }

    public Rect getBoundsInWindow() {
        return this.mBoundsInWindow;
    }

    public void setBoundsInWindow(Rect bounds) {
        enforceNotSealed();
        this.mBoundsInWindow.set(bounds);
    }

    public boolean isCheckable() {
        return getBooleanProperty(1);
    }

    public void setCheckable(boolean checkable) {
        setBooleanProperty(1, checkable);
    }

    public boolean isChecked() {
        return getBooleanProperty(2);
    }

    public void setChecked(boolean checked) {
        setBooleanProperty(2, checked);
    }

    public boolean isFocusable() {
        return getBooleanProperty(4);
    }

    public void setFocusable(boolean focusable) {
        setBooleanProperty(4, focusable);
    }

    public boolean isFocused() {
        return getBooleanProperty(8);
    }

    public void setFocused(boolean focused) {
        setBooleanProperty(8, focused);
    }

    public boolean isVisibleToUser() {
        return getBooleanProperty(2048);
    }

    public void setVisibleToUser(boolean visibleToUser) {
        setBooleanProperty(2048, visibleToUser);
    }

    public boolean isAccessibilityFocused() {
        return getBooleanProperty(1024);
    }

    public void setAccessibilityFocused(boolean focused) {
        setBooleanProperty(1024, focused);
    }

    public boolean isSelected() {
        return getBooleanProperty(16);
    }

    public void setSelected(boolean selected) {
        setBooleanProperty(16, selected);
    }

    public boolean isClickable() {
        return getBooleanProperty(32);
    }

    public void setClickable(boolean clickable) {
        setBooleanProperty(32, clickable);
    }

    public boolean isLongClickable() {
        return getBooleanProperty(64);
    }

    public void setLongClickable(boolean longClickable) {
        setBooleanProperty(64, longClickable);
    }

    public boolean isEnabled() {
        return getBooleanProperty(128);
    }

    public void setEnabled(boolean enabled) {
        setBooleanProperty(128, enabled);
    }

    public boolean isPassword() {
        return getBooleanProperty(256);
    }

    public void setPassword(boolean password) {
        setBooleanProperty(256, password);
    }

    public boolean isScrollable() {
        return getBooleanProperty(512);
    }

    public void setScrollable(boolean scrollable) {
        setBooleanProperty(512, scrollable);
    }

    public boolean isGranularScrollingSupported() {
        return getBooleanProperty(67108864);
    }

    public void setGranularScrollingSupported(boolean granularScrollingSupported) {
        setBooleanProperty(67108864, granularScrollingSupported);
    }

    public boolean isTextSelectable() {
        return getBooleanProperty(8388608);
    }

    public void setTextSelectable(boolean selectableText) {
        setBooleanProperty(8388608, selectableText);
    }

    public boolean hasRequestInitialAccessibilityFocus() {
        return getBooleanProperty(16777216);
    }

    public void setRequestInitialAccessibilityFocus(boolean requestInitialAccessibilityFocus) {
        setBooleanProperty(16777216, requestInitialAccessibilityFocus);
    }

    public boolean isEditable() {
        return getBooleanProperty(4096);
    }

    public void setEditable(boolean editable) {
        setBooleanProperty(4096, editable);
    }

    public boolean isAccessibilityDataSensitive() {
        return getBooleanProperty(33554432);
    }

    public void setAccessibilityDataSensitive(boolean accessibilityDataSensitive) {
        setBooleanProperty(33554432, accessibilityDataSensitive);
    }

    public void setPaneTitle(CharSequence paneTitle) {
        enforceNotSealed();
        this.mPaneTitle = paneTitle == null ? null : paneTitle.subSequence(0, paneTitle.length());
    }

    public CharSequence getPaneTitle() {
        return this.mPaneTitle;
    }

    public int getDrawingOrder() {
        return this.mDrawingOrderInParent;
    }

    public void setDrawingOrder(int drawingOrderInParent) {
        enforceNotSealed();
        this.mDrawingOrderInParent = drawingOrderInParent;
    }

    public CollectionInfo getCollectionInfo() {
        return this.mCollectionInfo;
    }

    public void setCollectionInfo(CollectionInfo collectionInfo) {
        enforceNotSealed();
        this.mCollectionInfo = collectionInfo;
    }

    public CollectionItemInfo getCollectionItemInfo() {
        return this.mCollectionItemInfo;
    }

    public void setCollectionItemInfo(CollectionItemInfo collectionItemInfo) {
        enforceNotSealed();
        this.mCollectionItemInfo = collectionItemInfo;
    }

    public RangeInfo getRangeInfo() {
        return this.mRangeInfo;
    }

    public void setRangeInfo(RangeInfo rangeInfo) {
        enforceNotSealed();
        this.mRangeInfo = rangeInfo;
    }

    public ExtraRenderingInfo getExtraRenderingInfo() {
        return this.mExtraRenderingInfo;
    }

    public void setExtraRenderingInfo(ExtraRenderingInfo extraRenderingInfo) {
        enforceNotSealed();
        this.mExtraRenderingInfo = extraRenderingInfo;
    }

    public boolean isContentInvalid() {
        return getBooleanProperty(65536);
    }

    public void setContentInvalid(boolean contentInvalid) {
        setBooleanProperty(65536, contentInvalid);
    }

    public boolean isContextClickable() {
        return getBooleanProperty(131072);
    }

    public void setContextClickable(boolean contextClickable) {
        setBooleanProperty(131072, contextClickable);
    }

    public int getLiveRegion() {
        return this.mLiveRegion;
    }

    public void setLiveRegion(int mode) {
        enforceNotSealed();
        this.mLiveRegion = mode;
    }

    public boolean isMultiLine() {
        return getBooleanProperty(32768);
    }

    public void setMultiLine(boolean multiLine) {
        setBooleanProperty(32768, multiLine);
    }

    public boolean canOpenPopup() {
        return getBooleanProperty(8192);
    }

    public void setCanOpenPopup(boolean opensPopup) {
        enforceNotSealed();
        setBooleanProperty(8192, opensPopup);
    }

    public boolean isDismissable() {
        return getBooleanProperty(16384);
    }

    public void setDismissable(boolean dismissable) {
        setBooleanProperty(16384, dismissable);
    }

    public boolean isImportantForAccessibility() {
        return getBooleanProperty(262144);
    }

    public void setImportantForAccessibility(boolean important) {
        setBooleanProperty(262144, important);
    }

    public boolean isScreenReaderFocusable() {
        return getBooleanProperty(524288);
    }

    public void setScreenReaderFocusable(boolean screenReaderFocusable) {
        setBooleanProperty(524288, screenReaderFocusable);
    }

    public boolean isShowingHintText() {
        return getBooleanProperty(1048576);
    }

    public void setShowingHintText(boolean showingHintText) {
        setBooleanProperty(1048576, showingHintText);
    }

    public boolean isHeading() {
        if (getBooleanProperty(2097152)) {
            return true;
        }
        CollectionItemInfo itemInfo = getCollectionItemInfo();
        return itemInfo != null && itemInfo.mHeading;
    }

    public void setHeading(boolean isHeading) {
        setBooleanProperty(2097152, isHeading);
    }

    public boolean isTextEntryKey() {
        return getBooleanProperty(4194304);
    }

    public void setTextEntryKey(boolean isTextEntryKey) {
        setBooleanProperty(4194304, isTextEntryKey);
    }

    public CharSequence getPackageName() {
        return this.mPackageName;
    }

    public void setPackageName(CharSequence packageName) {
        enforceNotSealed();
        this.mPackageName = packageName;
    }

    public CharSequence getClassName() {
        return this.mClassName;
    }

    public void setClassName(CharSequence className) {
        enforceNotSealed();
        this.mClassName = className;
    }

    public CharSequence getText() {
        if (this.mText instanceof Spanned) {
            Spanned spanned = (Spanned) this.mText;
            AccessibilityClickableSpan[] clickableSpans = (AccessibilityClickableSpan[]) spanned.getSpans(0, this.mText.length(), AccessibilityClickableSpan.class);
            for (AccessibilityClickableSpan accessibilityClickableSpan : clickableSpans) {
                accessibilityClickableSpan.copyConnectionDataFrom(this);
            }
            AccessibilityURLSpan[] urlSpans = (AccessibilityURLSpan[]) spanned.getSpans(0, this.mText.length(), AccessibilityURLSpan.class);
            for (AccessibilityURLSpan accessibilityURLSpan : urlSpans) {
                accessibilityURLSpan.copyConnectionDataFrom(this);
            }
        }
        return this.mText;
    }

    public CharSequence getOriginalText() {
        return this.mOriginalText;
    }

    public void setText(CharSequence text) {
        enforceNotSealed();
        this.mOriginalText = text;
        if (text instanceof Spanned) {
            CharSequence tmpText = replaceClickableSpan(text);
            this.mText = replaceReplacementSpan(tmpText);
        } else {
            this.mText = text == null ? null : text.subSequence(0, text.length());
        }
    }

    private CharSequence replaceClickableSpan(CharSequence text) {
        ClickableSpan replacementSpan;
        ClickableSpan[] clickableSpans = (ClickableSpan[]) ((Spanned) text).getSpans(0, text.length(), ClickableSpan.class);
        Spannable spannable = new SpannableStringBuilder(text);
        if (clickableSpans.length == 0) {
            return text;
        }
        for (ClickableSpan span : clickableSpans) {
            if ((span instanceof AccessibilityClickableSpan) || (span instanceof AccessibilityURLSpan)) {
                break;
            }
            int spanToReplaceStart = spannable.getSpanStart(span);
            int spanToReplaceEnd = spannable.getSpanEnd(span);
            int spanToReplaceFlags = spannable.getSpanFlags(span);
            if (spanToReplaceStart >= 0) {
                spannable.removeSpan(span);
                if (span instanceof URLSpan) {
                    replacementSpan = new AccessibilityURLSpan((URLSpan) span);
                } else {
                    replacementSpan = new AccessibilityClickableSpan(span.getId());
                }
                spannable.setSpan(replacementSpan, spanToReplaceStart, spanToReplaceEnd, spanToReplaceFlags);
            }
        }
        return spannable;
    }

    private CharSequence replaceReplacementSpan(CharSequence text) {
        ReplacementSpan[] replacementSpans = (ReplacementSpan[]) ((Spanned) text).getSpans(0, text.length(), ReplacementSpan.class);
        SpannableStringBuilder spannable = new SpannableStringBuilder(text);
        if (replacementSpans.length == 0) {
            return text;
        }
        for (ReplacementSpan span : replacementSpans) {
            CharSequence replacementText = span.getContentDescription();
            if (span instanceof AccessibilityReplacementSpan) {
                break;
            }
            if (replacementText != null) {
                int spanToReplaceStart = spannable.getSpanStart(span);
                int spanToReplaceEnd = spannable.getSpanEnd(span);
                int spanToReplaceFlags = spannable.getSpanFlags(span);
                if (spanToReplaceStart >= 0) {
                    spannable.removeSpan(span);
                    ReplacementSpan replacementSpan = new AccessibilityReplacementSpan(replacementText);
                    spannable.setSpan(replacementSpan, spanToReplaceStart, spanToReplaceEnd, spanToReplaceFlags);
                }
            }
        }
        return spannable;
    }

    public CharSequence getHintText() {
        return this.mHintText;
    }

    public void setHintText(CharSequence hintText) {
        enforceNotSealed();
        this.mHintText = hintText == null ? null : hintText.subSequence(0, hintText.length());
    }

    public void setError(CharSequence error) {
        enforceNotSealed();
        this.mError = error == null ? null : error.subSequence(0, error.length());
    }

    public CharSequence getError() {
        return this.mError;
    }

    public CharSequence getStateDescription() {
        return this.mStateDescription;
    }

    public CharSequence getContentDescription() {
        return this.mContentDescription;
    }

    public void setStateDescription(CharSequence stateDescription) {
        enforceNotSealed();
        this.mStateDescription = stateDescription == null ? null : stateDescription.subSequence(0, stateDescription.length());
    }

    public void setContentDescription(CharSequence contentDescription) {
        enforceNotSealed();
        this.mContentDescription = contentDescription == null ? null : contentDescription.subSequence(0, contentDescription.length());
    }

    public CharSequence getTooltipText() {
        return this.mTooltipText;
    }

    public void setTooltipText(CharSequence tooltipText) {
        enforceNotSealed();
        this.mTooltipText = tooltipText == null ? null : tooltipText.subSequence(0, tooltipText.length());
    }

    public void setLabelFor(View labeled) {
        setLabelFor(labeled, -1);
    }

    public void setLabelFor(View root, int virtualDescendantId) {
        enforceNotSealed();
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mLabelForId = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
    }

    public AccessibilityNodeInfo getLabelFor() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabelForId);
    }

    public void setLabeledBy(View label) {
        setLabeledBy(label, -1);
    }

    public void setLabeledBy(View root, int virtualDescendantId) {
        enforceNotSealed();
        int rootAccessibilityViewId = root != null ? root.getAccessibilityViewId() : Integer.MAX_VALUE;
        this.mLabeledById = makeNodeId(rootAccessibilityViewId, virtualDescendantId);
    }

    public AccessibilityNodeInfo getLabeledBy() {
        enforceSealed();
        return getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mLabeledById);
    }

    public void setViewIdResourceName(String viewIdResName) {
        enforceNotSealed();
        this.mViewIdResourceName = viewIdResName;
    }

    public String getViewIdResourceName() {
        return this.mViewIdResourceName;
    }

    public int getTextSelectionStart() {
        return this.mTextSelectionStart;
    }

    public int getTextSelectionEnd() {
        return this.mTextSelectionEnd;
    }

    public void setTextSelection(int start, int end) {
        enforceNotSealed();
        this.mTextSelectionStart = start;
        this.mTextSelectionEnd = end;
    }

    public int getInputType() {
        return this.mInputType;
    }

    public void setInputType(int inputType) {
        enforceNotSealed();
        this.mInputType = inputType;
    }

    public Bundle getExtras() {
        if (this.mExtras == null) {
            this.mExtras = new Bundle();
        }
        return this.mExtras;
    }

    public boolean hasExtras() {
        return this.mExtras != null;
    }

    public TouchDelegateInfo getTouchDelegateInfo() {
        if (this.mTouchDelegateInfo != null) {
            this.mTouchDelegateInfo.setConnectionId(this.mConnectionId);
            this.mTouchDelegateInfo.setWindowId(this.mWindowId);
        }
        return this.mTouchDelegateInfo;
    }

    public void setTouchDelegateInfo(TouchDelegateInfo delegatedInfo) {
        enforceNotSealed();
        this.mTouchDelegateInfo = delegatedInfo;
    }

    private boolean getBooleanProperty(int property) {
        return (this.mBooleanProperties & property) != 0;
    }

    private void setBooleanProperty(int property, boolean value) {
        enforceNotSealed();
        if (value) {
            this.mBooleanProperties |= property;
        } else {
            this.mBooleanProperties &= ~property;
        }
    }

    public void setConnectionId(int connectionId) {
        enforceNotSealed();
        this.mConnectionId = connectionId;
    }

    public int getConnectionId() {
        return this.mConnectionId;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public void setSourceNodeId(long sourceId, int windowId) {
        enforceNotSealed();
        this.mSourceNodeId = sourceId;
        this.mWindowId = windowId;
    }

    public long getSourceNodeId() {
        return this.mSourceNodeId;
    }

    public void setUniqueId(String uniqueId) {
        enforceNotSealed();
        this.mUniqueId = uniqueId;
    }

    public String getUniqueId() {
        return this.mUniqueId;
    }

    public void setContainerTitle(CharSequence containerTitle) {
        enforceNotSealed();
        this.mContainerTitle = containerTitle == null ? null : containerTitle.subSequence(0, containerTitle.length());
    }

    public CharSequence getContainerTitle() {
        return this.mContainerTitle;
    }

    public void setLeashedParent(IBinder token, int viewId) {
        enforceNotSealed();
        this.mLeashedParent = token;
        this.mLeashedParentNodeId = makeNodeId(viewId, -1);
    }

    public IBinder getLeashedParent() {
        return this.mLeashedParent;
    }

    public long getLeashedParentNodeId() {
        return this.mLeashedParentNodeId;
    }

    public void setQueryFromAppProcessEnabled(View view, boolean enabled) {
        enforceNotSealed();
        if (enabled) {
            if (this.mConnectionId != -1) {
                return;
            }
            ViewRootImpl viewRootImpl = view.getViewRootImpl();
            if (viewRootImpl == null) {
                throw new IllegalStateException("Cannot link a node to a view that is not attached to a window.");
            }
            setConnectionId(viewRootImpl.getDirectAccessibilityConnectionId());
            return;
        }
        setConnectionId(-1);
    }

    public void setSealed(boolean sealed) {
        this.mSealed = sealed;
    }

    public boolean isSealed() {
        return this.mSealed;
    }

    private static boolean usingDirectConnection(int connectionId) {
        return AccessibilityInteractionClient.getConnection(connectionId) instanceof DirectAccessibilityConnection;
    }

    protected void enforceSealed() {
        if (!usingDirectConnection(this.mConnectionId) && !isSealed()) {
            throw new IllegalStateException("Cannot perform this action on a not sealed instance.");
        }
    }

    private void enforceValidFocusDirection(int direction) {
        switch (direction) {
            case 1:
            case 2:
            case 17:
            case 33:
            case 66:
            case 130:
                return;
            default:
                throw new IllegalArgumentException("Unknown direction: " + direction);
        }
    }

    private void enforceValidFocusType(int focusType) {
        switch (focusType) {
            case 1:
            case 2:
                return;
            default:
                throw new IllegalArgumentException("Unknown focus type: " + focusType);
        }
    }

    protected void enforceNotSealed() {
        if (isSealed()) {
            throw new IllegalStateException("Cannot perform this action on a sealed instance.");
        }
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain(View source) {
        return new AccessibilityNodeInfo(source);
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain(View root, int virtualDescendantId) {
        return new AccessibilityNodeInfo(root, virtualDescendantId);
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain() {
        return new AccessibilityNodeInfo();
    }

    @Deprecated
    public static AccessibilityNodeInfo obtain(AccessibilityNodeInfo info) {
        return new AccessibilityNodeInfo(info);
    }

    @Deprecated
    public void recycle() {
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int flags) {
        writeToParcelNoRecycle(parcel, flags);
    }

    public void writeToParcelNoRecycle(Parcel parcel, int i) {
        int i2;
        long bitAt = isSealed() != DEFAULT.isSealed() ? 0 | BitUtils.bitAt(0) : 0L;
        int i3 = 0 + 1;
        if (this.mSourceNodeId != DEFAULT.mSourceNodeId) {
            bitAt |= BitUtils.bitAt(i3);
        }
        int i4 = i3 + 1;
        if (this.mWindowId != DEFAULT.mWindowId) {
            bitAt |= BitUtils.bitAt(i4);
        }
        int i5 = i4 + 1;
        if (this.mParentNodeId != DEFAULT.mParentNodeId) {
            bitAt |= BitUtils.bitAt(i5);
        }
        int i6 = i5 + 1;
        if (this.mLabelForId != DEFAULT.mLabelForId) {
            bitAt |= BitUtils.bitAt(i6);
        }
        int i7 = i6 + 1;
        if (this.mLabeledById != DEFAULT.mLabeledById) {
            bitAt |= BitUtils.bitAt(i7);
        }
        int i8 = i7 + 1;
        if (this.mTraversalBefore != DEFAULT.mTraversalBefore) {
            bitAt |= BitUtils.bitAt(i8);
        }
        int i9 = i8 + 1;
        if (this.mTraversalAfter != DEFAULT.mTraversalAfter) {
            bitAt |= BitUtils.bitAt(i9);
        }
        int i10 = i9 + 1;
        if (this.mMinDurationBetweenContentChanges != DEFAULT.mMinDurationBetweenContentChanges) {
            bitAt |= BitUtils.bitAt(i10);
        }
        int i11 = i10 + 1;
        if (this.mConnectionId != DEFAULT.mConnectionId) {
            bitAt |= BitUtils.bitAt(i11);
        }
        int i12 = i11 + 1;
        if (!LongArray.elementsEqual(this.mChildNodeIds, DEFAULT.mChildNodeIds)) {
            bitAt |= BitUtils.bitAt(i12);
        }
        int i13 = i12 + 1;
        if (!Objects.equals(this.mBoundsInParent, DEFAULT.mBoundsInParent)) {
            bitAt |= BitUtils.bitAt(i13);
        }
        int i14 = i13 + 1;
        if (!Objects.equals(this.mBoundsInScreen, DEFAULT.mBoundsInScreen)) {
            bitAt |= BitUtils.bitAt(i14);
        }
        int i15 = i14 + 1;
        if (!Objects.equals(this.mBoundsInWindow, DEFAULT.mBoundsInWindow)) {
            bitAt |= BitUtils.bitAt(i15);
        }
        int i16 = i15 + 1;
        if (!Objects.equals(this.mActions, DEFAULT.mActions)) {
            bitAt |= BitUtils.bitAt(i16);
        }
        int i17 = i16 + 1;
        if (this.mMaxTextLength != DEFAULT.mMaxTextLength) {
            bitAt |= BitUtils.bitAt(i17);
        }
        int i18 = i17 + 1;
        if (this.mMovementGranularities != DEFAULT.mMovementGranularities) {
            bitAt |= BitUtils.bitAt(i18);
        }
        int i19 = i18 + 1;
        if (this.mBooleanProperties != DEFAULT.mBooleanProperties) {
            bitAt |= BitUtils.bitAt(i19);
        }
        int i20 = i19 + 1;
        if (!Objects.equals(this.mPackageName, DEFAULT.mPackageName)) {
            bitAt |= BitUtils.bitAt(i20);
        }
        int i21 = i20 + 1;
        if (!Objects.equals(this.mClassName, DEFAULT.mClassName)) {
            bitAt |= BitUtils.bitAt(i21);
        }
        int i22 = i21 + 1;
        if (!Objects.equals(this.mText, DEFAULT.mText)) {
            bitAt |= BitUtils.bitAt(i22);
        }
        int i23 = i22 + 1;
        if (!Objects.equals(this.mHintText, DEFAULT.mHintText)) {
            bitAt |= BitUtils.bitAt(i23);
        }
        int i24 = i23 + 1;
        if (!Objects.equals(this.mError, DEFAULT.mError)) {
            bitAt |= BitUtils.bitAt(i24);
        }
        int i25 = i24 + 1;
        if (!Objects.equals(this.mStateDescription, DEFAULT.mStateDescription)) {
            bitAt |= BitUtils.bitAt(i25);
        }
        int i26 = i25 + 1;
        if (!Objects.equals(this.mContentDescription, DEFAULT.mContentDescription)) {
            bitAt |= BitUtils.bitAt(i26);
        }
        int i27 = i26 + 1;
        if (!Objects.equals(this.mPaneTitle, DEFAULT.mPaneTitle)) {
            bitAt |= BitUtils.bitAt(i27);
        }
        int i28 = i27 + 1;
        if (!Objects.equals(this.mTooltipText, DEFAULT.mTooltipText)) {
            bitAt |= BitUtils.bitAt(i28);
        }
        int i29 = i28 + 1;
        if (!Objects.equals(this.mContainerTitle, DEFAULT.mContainerTitle)) {
            bitAt |= BitUtils.bitAt(i29);
        }
        int i30 = i29 + 1;
        if (!Objects.equals(this.mViewIdResourceName, DEFAULT.mViewIdResourceName)) {
            bitAt |= BitUtils.bitAt(i30);
        }
        int i31 = i30 + 1;
        if (!Objects.equals(this.mUniqueId, DEFAULT.mUniqueId)) {
            bitAt |= BitUtils.bitAt(i31);
        }
        int i32 = i31 + 1;
        if (this.mTextSelectionStart != DEFAULT.mTextSelectionStart) {
            bitAt |= BitUtils.bitAt(i32);
        }
        int i33 = i32 + 1;
        if (this.mTextSelectionEnd != DEFAULT.mTextSelectionEnd) {
            bitAt |= BitUtils.bitAt(i33);
        }
        int i34 = i33 + 1;
        if (this.mInputType != DEFAULT.mInputType) {
            bitAt |= BitUtils.bitAt(i34);
        }
        int i35 = i34 + 1;
        if (this.mLiveRegion != DEFAULT.mLiveRegion) {
            bitAt |= BitUtils.bitAt(i35);
        }
        int i36 = i35 + 1;
        if (this.mDrawingOrderInParent != DEFAULT.mDrawingOrderInParent) {
            bitAt |= BitUtils.bitAt(i36);
        }
        int i37 = i36 + 1;
        if (!Objects.equals(this.mExtraDataKeys, DEFAULT.mExtraDataKeys)) {
            bitAt |= BitUtils.bitAt(i37);
        }
        int i38 = i37 + 1;
        if (!Objects.equals(this.mExtras, DEFAULT.mExtras)) {
            bitAt |= BitUtils.bitAt(i38);
        }
        int i39 = i38 + 1;
        if (!Objects.equals(this.mRangeInfo, DEFAULT.mRangeInfo)) {
            bitAt |= BitUtils.bitAt(i39);
        }
        int i40 = i39 + 1;
        if (!Objects.equals(this.mCollectionInfo, DEFAULT.mCollectionInfo)) {
            bitAt |= BitUtils.bitAt(i40);
        }
        int i41 = i40 + 1;
        if (!Objects.equals(this.mCollectionItemInfo, DEFAULT.mCollectionItemInfo)) {
            bitAt |= BitUtils.bitAt(i41);
        }
        int i42 = i41 + 1;
        if (!Objects.equals(this.mTouchDelegateInfo, DEFAULT.mTouchDelegateInfo)) {
            bitAt |= BitUtils.bitAt(i42);
        }
        int i43 = i42 + 1;
        if (!Objects.equals(this.mExtraRenderingInfo, DEFAULT.mExtraRenderingInfo)) {
            bitAt |= BitUtils.bitAt(i43);
        }
        int i44 = i43 + 1;
        if (this.mLeashedChild != DEFAULT.mLeashedChild) {
            bitAt |= BitUtils.bitAt(i44);
        }
        int i45 = i44 + 1;
        if (this.mLeashedParent != DEFAULT.mLeashedParent) {
            bitAt |= BitUtils.bitAt(i45);
        }
        int i46 = i45 + 1;
        if (this.mLeashedParentNodeId != DEFAULT.mLeashedParentNodeId) {
            bitAt |= BitUtils.bitAt(i46);
        }
        parcel.writeLong(bitAt);
        int i47 = 0 + 1;
        if (BitUtils.isBitSet(bitAt, 0)) {
            parcel.writeInt(isSealed() ? 1 : 0);
        }
        int i48 = i47 + 1;
        if (BitUtils.isBitSet(bitAt, i47)) {
            parcel.writeLong(this.mSourceNodeId);
        }
        int i49 = i48 + 1;
        if (BitUtils.isBitSet(bitAt, i48)) {
            parcel.writeInt(this.mWindowId);
        }
        int i50 = i49 + 1;
        if (BitUtils.isBitSet(bitAt, i49)) {
            parcel.writeLong(this.mParentNodeId);
        }
        int i51 = i50 + 1;
        if (BitUtils.isBitSet(bitAt, i50)) {
            parcel.writeLong(this.mLabelForId);
        }
        int i52 = i51 + 1;
        if (BitUtils.isBitSet(bitAt, i51)) {
            parcel.writeLong(this.mLabeledById);
        }
        int i53 = i52 + 1;
        if (BitUtils.isBitSet(bitAt, i52)) {
            parcel.writeLong(this.mTraversalBefore);
        }
        int i54 = i53 + 1;
        if (BitUtils.isBitSet(bitAt, i53)) {
            parcel.writeLong(this.mTraversalAfter);
        }
        int i55 = i54 + 1;
        if (BitUtils.isBitSet(bitAt, i54)) {
            parcel.writeLong(this.mMinDurationBetweenContentChanges);
        }
        int i56 = i55 + 1;
        if (BitUtils.isBitSet(bitAt, i55)) {
            parcel.writeInt(this.mConnectionId);
        }
        int i57 = i56 + 1;
        if (BitUtils.isBitSet(bitAt, i56)) {
            LongArray longArray = this.mChildNodeIds;
            if (longArray == null) {
                parcel.writeInt(0);
            } else {
                int size = longArray.size();
                parcel.writeInt(size);
                for (int i58 = 0; i58 < size; i58++) {
                    parcel.writeLong(longArray.get(i58));
                }
            }
        }
        int i59 = i57 + 1;
        if (BitUtils.isBitSet(bitAt, i57)) {
            parcel.writeInt(this.mBoundsInParent.top);
            parcel.writeInt(this.mBoundsInParent.bottom);
            parcel.writeInt(this.mBoundsInParent.left);
            parcel.writeInt(this.mBoundsInParent.right);
        }
        int i60 = i59 + 1;
        if (BitUtils.isBitSet(bitAt, i59)) {
            parcel.writeInt(this.mBoundsInScreen.top);
            parcel.writeInt(this.mBoundsInScreen.bottom);
            parcel.writeInt(this.mBoundsInScreen.left);
            parcel.writeInt(this.mBoundsInScreen.right);
        }
        int i61 = i60 + 1;
        if (BitUtils.isBitSet(bitAt, i60)) {
            parcel.writeInt(this.mBoundsInWindow.top);
            parcel.writeInt(this.mBoundsInWindow.bottom);
            parcel.writeInt(this.mBoundsInWindow.left);
            parcel.writeInt(this.mBoundsInWindow.right);
        }
        int i62 = i61 + 1;
        if (BitUtils.isBitSet(bitAt, i61)) {
            if (this.mActions != null && !this.mActions.isEmpty()) {
                int size2 = this.mActions.size();
                int i63 = 0;
                long j = 0;
                for (int i64 = 0; i64 < size2; i64++) {
                    AccessibilityAction accessibilityAction = this.mActions.get(i64);
                    if (isDefaultStandardAction(accessibilityAction)) {
                        j |= accessibilityAction.mSerializationFlag;
                    } else {
                        i63++;
                    }
                }
                parcel.writeLong(j);
                parcel.writeInt(i63);
                for (int i65 = 0; i65 < size2; i65++) {
                    AccessibilityAction accessibilityAction2 = this.mActions.get(i65);
                    if (!isDefaultStandardAction(accessibilityAction2)) {
                        accessibilityAction2.writeToParcel(parcel, i);
                    }
                }
            } else {
                parcel.writeLong(0L);
                parcel.writeInt(0);
            }
        }
        int i66 = i62 + 1;
        if (BitUtils.isBitSet(bitAt, i62)) {
            parcel.writeInt(this.mMaxTextLength);
        }
        int i67 = i66 + 1;
        if (BitUtils.isBitSet(bitAt, i66)) {
            parcel.writeInt(this.mMovementGranularities);
        }
        int i68 = i67 + 1;
        if (BitUtils.isBitSet(bitAt, i67)) {
            parcel.writeInt(this.mBooleanProperties);
        }
        int i69 = i68 + 1;
        if (BitUtils.isBitSet(bitAt, i68)) {
            parcel.writeCharSequence(this.mPackageName);
        }
        int i70 = i69 + 1;
        if (BitUtils.isBitSet(bitAt, i69)) {
            parcel.writeCharSequence(this.mClassName);
        }
        int i71 = i70 + 1;
        if (BitUtils.isBitSet(bitAt, i70)) {
            parcel.writeCharSequence(this.mText);
        }
        int i72 = i71 + 1;
        if (BitUtils.isBitSet(bitAt, i71)) {
            parcel.writeCharSequence(this.mHintText);
        }
        int i73 = i72 + 1;
        if (BitUtils.isBitSet(bitAt, i72)) {
            parcel.writeCharSequence(this.mError);
        }
        int i74 = i73 + 1;
        if (BitUtils.isBitSet(bitAt, i73)) {
            parcel.writeCharSequence(this.mStateDescription);
        }
        int i75 = i74 + 1;
        if (BitUtils.isBitSet(bitAt, i74)) {
            parcel.writeCharSequence(this.mContentDescription);
        }
        int i76 = i75 + 1;
        if (BitUtils.isBitSet(bitAt, i75)) {
            parcel.writeCharSequence(this.mPaneTitle);
        }
        int i77 = i76 + 1;
        if (BitUtils.isBitSet(bitAt, i76)) {
            parcel.writeCharSequence(this.mTooltipText);
        }
        int i78 = i77 + 1;
        if (BitUtils.isBitSet(bitAt, i77)) {
            parcel.writeCharSequence(this.mContainerTitle);
        }
        int i79 = i78 + 1;
        if (BitUtils.isBitSet(bitAt, i78)) {
            parcel.writeString(this.mViewIdResourceName);
        }
        int i80 = i79 + 1;
        if (BitUtils.isBitSet(bitAt, i79)) {
            parcel.writeString(this.mUniqueId);
        }
        int i81 = i80 + 1;
        if (BitUtils.isBitSet(bitAt, i80)) {
            parcel.writeInt(this.mTextSelectionStart);
        }
        int i82 = i81 + 1;
        if (BitUtils.isBitSet(bitAt, i81)) {
            parcel.writeInt(this.mTextSelectionEnd);
        }
        int i83 = i82 + 1;
        if (BitUtils.isBitSet(bitAt, i82)) {
            parcel.writeInt(this.mInputType);
        }
        int i84 = i83 + 1;
        if (BitUtils.isBitSet(bitAt, i83)) {
            parcel.writeInt(this.mLiveRegion);
        }
        int i85 = i84 + 1;
        if (BitUtils.isBitSet(bitAt, i84)) {
            parcel.writeInt(this.mDrawingOrderInParent);
        }
        int i86 = i85 + 1;
        if (BitUtils.isBitSet(bitAt, i85)) {
            parcel.writeStringList(this.mExtraDataKeys);
        }
        int i87 = i86 + 1;
        if (BitUtils.isBitSet(bitAt, i86)) {
            parcel.writeBundle(this.mExtras);
        }
        int i88 = i87 + 1;
        if (BitUtils.isBitSet(bitAt, i87)) {
            parcel.writeInt(this.mRangeInfo.getType());
            parcel.writeFloat(this.mRangeInfo.getMin());
            parcel.writeFloat(this.mRangeInfo.getMax());
            parcel.writeFloat(this.mRangeInfo.getCurrent());
        }
        int i89 = i88 + 1;
        if (BitUtils.isBitSet(bitAt, i88)) {
            parcel.writeInt(this.mCollectionInfo.getRowCount());
            parcel.writeInt(this.mCollectionInfo.getColumnCount());
            parcel.writeInt(this.mCollectionInfo.isHierarchical() ? 1 : 0);
            parcel.writeInt(this.mCollectionInfo.getSelectionMode());
            parcel.writeInt(this.mCollectionInfo.getItemCount());
            parcel.writeInt(this.mCollectionInfo.getImportantForAccessibilityItemCount());
        }
        int i90 = i89 + 1;
        if (BitUtils.isBitSet(bitAt, i89)) {
            parcel.writeString(this.mCollectionItemInfo.getRowTitle());
            parcel.writeInt(this.mCollectionItemInfo.getRowIndex());
            parcel.writeInt(this.mCollectionItemInfo.getRowSpan());
            parcel.writeString(this.mCollectionItemInfo.getColumnTitle());
            parcel.writeInt(this.mCollectionItemInfo.getColumnIndex());
            parcel.writeInt(this.mCollectionItemInfo.getColumnSpan());
            parcel.writeInt(this.mCollectionItemInfo.isHeading() ? 1 : 0);
            parcel.writeInt(this.mCollectionItemInfo.isSelected() ? 1 : 0);
        }
        int i91 = i90 + 1;
        if (BitUtils.isBitSet(bitAt, i90)) {
            this.mTouchDelegateInfo.writeToParcel(parcel, i);
        }
        int i92 = i91 + 1;
        if (BitUtils.isBitSet(bitAt, i91)) {
            parcel.writeValue(this.mExtraRenderingInfo.getLayoutSize());
            parcel.writeFloat(this.mExtraRenderingInfo.getTextSizeInPx());
            parcel.writeInt(this.mExtraRenderingInfo.getTextSizeUnit());
        }
        int i93 = i92 + 1;
        if (BitUtils.isBitSet(bitAt, i92)) {
            parcel.writeStrongBinder(this.mLeashedChild);
        }
        int i94 = i93 + 1;
        if (BitUtils.isBitSet(bitAt, i93)) {
            parcel.writeStrongBinder(this.mLeashedParent);
        }
        int i95 = i94 + 1;
        if (BitUtils.isBitSet(bitAt, i94)) {
            parcel.writeLong(this.mLeashedParentNodeId);
        }
        if (DEBUG && i46 != i95 - 1) {
            throw new IllegalStateException("Number of fields mismatch: " + i46 + " vs " + i2);
        }
    }

    private void init(AccessibilityNodeInfo other) {
        this.mSealed = other.mSealed;
        this.mSourceNodeId = other.mSourceNodeId;
        this.mParentNodeId = other.mParentNodeId;
        this.mLabelForId = other.mLabelForId;
        this.mLabeledById = other.mLabeledById;
        this.mTraversalBefore = other.mTraversalBefore;
        this.mTraversalAfter = other.mTraversalAfter;
        this.mMinDurationBetweenContentChanges = other.mMinDurationBetweenContentChanges;
        this.mWindowId = other.mWindowId;
        this.mConnectionId = other.mConnectionId;
        this.mUniqueId = other.mUniqueId;
        this.mBoundsInParent.set(other.mBoundsInParent);
        this.mBoundsInScreen.set(other.mBoundsInScreen);
        this.mBoundsInWindow.set(other.mBoundsInWindow);
        this.mPackageName = other.mPackageName;
        this.mClassName = other.mClassName;
        this.mText = other.mText;
        this.mOriginalText = other.mOriginalText;
        this.mHintText = other.mHintText;
        this.mError = other.mError;
        this.mStateDescription = other.mStateDescription;
        this.mContentDescription = other.mContentDescription;
        this.mPaneTitle = other.mPaneTitle;
        this.mTooltipText = other.mTooltipText;
        this.mContainerTitle = other.mContainerTitle;
        this.mViewIdResourceName = other.mViewIdResourceName;
        if (this.mActions != null) {
            this.mActions.clear();
        }
        ArrayList<AccessibilityAction> otherActions = other.mActions;
        if (otherActions != null && otherActions.size() > 0) {
            if (this.mActions == null) {
                this.mActions = new ArrayList<>(otherActions);
            } else {
                this.mActions.addAll(other.mActions);
            }
        }
        this.mBooleanProperties = other.mBooleanProperties;
        this.mMaxTextLength = other.mMaxTextLength;
        this.mMovementGranularities = other.mMovementGranularities;
        if (this.mChildNodeIds != null) {
            this.mChildNodeIds.clear();
        }
        LongArray otherChildNodeIds = other.mChildNodeIds;
        if (otherChildNodeIds != null && otherChildNodeIds.size() > 0) {
            if (this.mChildNodeIds == null) {
                this.mChildNodeIds = otherChildNodeIds.m5212clone();
            } else {
                this.mChildNodeIds.addAll(otherChildNodeIds);
            }
        }
        this.mTextSelectionStart = other.mTextSelectionStart;
        this.mTextSelectionEnd = other.mTextSelectionEnd;
        this.mInputType = other.mInputType;
        this.mLiveRegion = other.mLiveRegion;
        this.mDrawingOrderInParent = other.mDrawingOrderInParent;
        this.mExtraDataKeys = other.mExtraDataKeys;
        this.mExtras = other.mExtras != null ? new Bundle(other.mExtras) : null;
        initCopyInfos(other);
        TouchDelegateInfo otherInfo = other.mTouchDelegateInfo;
        this.mTouchDelegateInfo = otherInfo != null ? new TouchDelegateInfo(otherInfo.mTargetMap, true) : null;
        this.mLeashedChild = other.mLeashedChild;
        this.mLeashedParent = other.mLeashedParent;
        this.mLeashedParentNodeId = other.mLeashedParentNodeId;
    }

    private void initCopyInfos(AccessibilityNodeInfo accessibilityNodeInfo) {
        RangeInfo rangeInfo;
        CollectionInfo collectionInfo;
        CollectionItemInfo build;
        RangeInfo rangeInfo2 = accessibilityNodeInfo.mRangeInfo;
        ExtraRenderingInfo extraRenderingInfo = null;
        byte b = 0;
        if (rangeInfo2 == null) {
            rangeInfo = null;
        } else {
            rangeInfo = new RangeInfo(rangeInfo2.mType, rangeInfo2.mMin, rangeInfo2.mMax, rangeInfo2.mCurrent);
        }
        this.mRangeInfo = rangeInfo;
        CollectionInfo collectionInfo2 = accessibilityNodeInfo.mCollectionInfo;
        if (collectionInfo2 == null) {
            collectionInfo = null;
        } else {
            collectionInfo = new CollectionInfo(collectionInfo2.mRowCount, collectionInfo2.mColumnCount, collectionInfo2.mHierarchical, collectionInfo2.mSelectionMode, collectionInfo2.mItemCount, collectionInfo2.mImportantForAccessibilityItemCount);
        }
        this.mCollectionInfo = collectionInfo;
        CollectionItemInfo collectionItemInfo = accessibilityNodeInfo.mCollectionItemInfo;
        CollectionItemInfo.Builder builder = new CollectionItemInfo.Builder();
        if (collectionItemInfo == null) {
            build = null;
        } else {
            build = builder.setRowTitle(collectionItemInfo.mRowTitle).setRowIndex(collectionItemInfo.mRowIndex).setRowSpan(collectionItemInfo.mRowSpan).setColumnTitle(collectionItemInfo.mColumnTitle).setColumnIndex(collectionItemInfo.mColumnIndex).setColumnSpan(collectionItemInfo.mColumnSpan).setHeading(collectionItemInfo.mHeading).setSelected(collectionItemInfo.mSelected).build();
        }
        this.mCollectionItemInfo = build;
        ExtraRenderingInfo extraRenderingInfo2 = accessibilityNodeInfo.mExtraRenderingInfo;
        if (extraRenderingInfo2 != null) {
            extraRenderingInfo = new ExtraRenderingInfo(extraRenderingInfo2);
        }
        this.mExtraRenderingInfo = extraRenderingInfo;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void initFromParcel(Parcel parcel) {
        boolean z;
        ArrayList<String> arrayList;
        Bundle bundle;
        RangeInfo rangeInfo;
        CollectionInfo collectionInfo;
        CollectionItemInfo collectionItemInfo;
        long readLong = parcel.readLong();
        int i = 0 + 1;
        if (BitUtils.isBitSet(readLong, 0)) {
            z = parcel.readInt() == 1;
        } else {
            z = DEFAULT.mSealed;
        }
        int i2 = i + 1;
        if (BitUtils.isBitSet(readLong, i)) {
            this.mSourceNodeId = parcel.readLong();
        }
        int i3 = i2 + 1;
        if (BitUtils.isBitSet(readLong, i2)) {
            this.mWindowId = parcel.readInt();
        }
        int i4 = i3 + 1;
        if (BitUtils.isBitSet(readLong, i3)) {
            this.mParentNodeId = parcel.readLong();
        }
        int i5 = i4 + 1;
        if (BitUtils.isBitSet(readLong, i4)) {
            this.mLabelForId = parcel.readLong();
        }
        int i6 = i5 + 1;
        if (BitUtils.isBitSet(readLong, i5)) {
            this.mLabeledById = parcel.readLong();
        }
        int i7 = i6 + 1;
        if (BitUtils.isBitSet(readLong, i6)) {
            this.mTraversalBefore = parcel.readLong();
        }
        int i8 = i7 + 1;
        if (BitUtils.isBitSet(readLong, i7)) {
            this.mTraversalAfter = parcel.readLong();
        }
        int i9 = i8 + 1;
        if (BitUtils.isBitSet(readLong, i8)) {
            this.mMinDurationBetweenContentChanges = parcel.readLong();
        }
        int i10 = i9 + 1;
        if (BitUtils.isBitSet(readLong, i9)) {
            this.mConnectionId = parcel.readInt();
        }
        int i11 = i10 + 1;
        ExtraRenderingInfo extraRenderingInfo = null;
        byte b = 0;
        if (BitUtils.isBitSet(readLong, i10)) {
            int readInt = parcel.readInt();
            if (readInt > 0) {
                this.mChildNodeIds = new LongArray(readInt);
                for (int i12 = 0; i12 < readInt; i12++) {
                    this.mChildNodeIds.add(parcel.readLong());
                }
            } else {
                this.mChildNodeIds = null;
            }
        }
        int i13 = i11 + 1;
        if (BitUtils.isBitSet(readLong, i11)) {
            this.mBoundsInParent.top = parcel.readInt();
            this.mBoundsInParent.bottom = parcel.readInt();
            this.mBoundsInParent.left = parcel.readInt();
            this.mBoundsInParent.right = parcel.readInt();
        }
        int i14 = i13 + 1;
        if (BitUtils.isBitSet(readLong, i13)) {
            this.mBoundsInScreen.top = parcel.readInt();
            this.mBoundsInScreen.bottom = parcel.readInt();
            this.mBoundsInScreen.left = parcel.readInt();
            this.mBoundsInScreen.right = parcel.readInt();
        }
        int i15 = i14 + 1;
        if (BitUtils.isBitSet(readLong, i14)) {
            this.mBoundsInWindow.top = parcel.readInt();
            this.mBoundsInWindow.bottom = parcel.readInt();
            this.mBoundsInWindow.left = parcel.readInt();
            this.mBoundsInWindow.right = parcel.readInt();
        }
        int i16 = i15 + 1;
        if (BitUtils.isBitSet(readLong, i15)) {
            addStandardActions(parcel.readLong());
            int readInt2 = parcel.readInt();
            for (int i17 = 0; i17 < readInt2; i17++) {
                addActionUnchecked(AccessibilityAction.CREATOR.createFromParcel(parcel));
            }
        }
        int i18 = i16 + 1;
        if (BitUtils.isBitSet(readLong, i16)) {
            this.mMaxTextLength = parcel.readInt();
        }
        int i19 = i18 + 1;
        if (BitUtils.isBitSet(readLong, i18)) {
            this.mMovementGranularities = parcel.readInt();
        }
        int i20 = i19 + 1;
        if (BitUtils.isBitSet(readLong, i19)) {
            this.mBooleanProperties = parcel.readInt();
        }
        int i21 = i20 + 1;
        if (BitUtils.isBitSet(readLong, i20)) {
            this.mPackageName = parcel.readCharSequence();
        }
        int i22 = i21 + 1;
        if (BitUtils.isBitSet(readLong, i21)) {
            this.mClassName = parcel.readCharSequence();
        }
        int i23 = i22 + 1;
        if (BitUtils.isBitSet(readLong, i22)) {
            this.mText = parcel.readCharSequence();
        }
        int i24 = i23 + 1;
        if (BitUtils.isBitSet(readLong, i23)) {
            this.mHintText = parcel.readCharSequence();
        }
        int i25 = i24 + 1;
        if (BitUtils.isBitSet(readLong, i24)) {
            this.mError = parcel.readCharSequence();
        }
        int i26 = i25 + 1;
        if (BitUtils.isBitSet(readLong, i25)) {
            this.mStateDescription = parcel.readCharSequence();
        }
        int i27 = i26 + 1;
        if (BitUtils.isBitSet(readLong, i26)) {
            this.mContentDescription = parcel.readCharSequence();
        }
        int i28 = i27 + 1;
        if (BitUtils.isBitSet(readLong, i27)) {
            this.mPaneTitle = parcel.readCharSequence();
        }
        int i29 = i28 + 1;
        if (BitUtils.isBitSet(readLong, i28)) {
            this.mTooltipText = parcel.readCharSequence();
        }
        int i30 = i29 + 1;
        if (BitUtils.isBitSet(readLong, i29)) {
            this.mContainerTitle = parcel.readCharSequence();
        }
        int i31 = i30 + 1;
        if (BitUtils.isBitSet(readLong, i30)) {
            this.mViewIdResourceName = parcel.readString();
        }
        int i32 = i31 + 1;
        if (BitUtils.isBitSet(readLong, i31)) {
            this.mUniqueId = parcel.readString();
        }
        int i33 = i32 + 1;
        if (BitUtils.isBitSet(readLong, i32)) {
            this.mTextSelectionStart = parcel.readInt();
        }
        int i34 = i33 + 1;
        if (BitUtils.isBitSet(readLong, i33)) {
            this.mTextSelectionEnd = parcel.readInt();
        }
        int i35 = i34 + 1;
        if (BitUtils.isBitSet(readLong, i34)) {
            this.mInputType = parcel.readInt();
        }
        int i36 = i35 + 1;
        if (BitUtils.isBitSet(readLong, i35)) {
            this.mLiveRegion = parcel.readInt();
        }
        int i37 = i36 + 1;
        if (BitUtils.isBitSet(readLong, i36)) {
            this.mDrawingOrderInParent = parcel.readInt();
        }
        int i38 = i37 + 1;
        if (BitUtils.isBitSet(readLong, i37)) {
            arrayList = parcel.createStringArrayList();
        } else {
            arrayList = null;
        }
        this.mExtraDataKeys = arrayList;
        int i39 = i38 + 1;
        if (BitUtils.isBitSet(readLong, i38)) {
            bundle = parcel.readBundle();
        } else {
            bundle = null;
        }
        this.mExtras = bundle;
        int i40 = i39 + 1;
        if (BitUtils.isBitSet(readLong, i39)) {
            rangeInfo = new RangeInfo(parcel.readInt(), parcel.readFloat(), parcel.readFloat(), parcel.readFloat());
        } else {
            rangeInfo = null;
        }
        this.mRangeInfo = rangeInfo;
        int i41 = i40 + 1;
        if (BitUtils.isBitSet(readLong, i40)) {
            collectionInfo = new CollectionInfo(parcel.readInt(), parcel.readInt(), parcel.readInt() == 1, parcel.readInt(), parcel.readInt(), parcel.readInt());
        } else {
            collectionInfo = null;
        }
        this.mCollectionInfo = collectionInfo;
        int i42 = i41 + 1;
        if (BitUtils.isBitSet(readLong, i41)) {
            collectionItemInfo = new CollectionItemInfo(parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt(), parcel.readInt(), parcel.readInt() == 1, parcel.readInt() == 1);
        } else {
            collectionItemInfo = null;
        }
        this.mCollectionItemInfo = collectionItemInfo;
        int i43 = i42 + 1;
        if (BitUtils.isBitSet(readLong, i42)) {
            this.mTouchDelegateInfo = TouchDelegateInfo.CREATOR.createFromParcel(parcel);
        }
        int i44 = i43 + 1;
        if (BitUtils.isBitSet(readLong, i43)) {
            this.mExtraRenderingInfo = new ExtraRenderingInfo(extraRenderingInfo);
            this.mExtraRenderingInfo.mLayoutSize = (Size) parcel.readValue(null);
            this.mExtraRenderingInfo.mTextSizeInPx = parcel.readFloat();
            this.mExtraRenderingInfo.mTextSizeUnit = parcel.readInt();
        }
        int i45 = i44 + 1;
        if (BitUtils.isBitSet(readLong, i44)) {
            this.mLeashedChild = parcel.readStrongBinder();
        }
        int i46 = i45 + 1;
        if (BitUtils.isBitSet(readLong, i45)) {
            this.mLeashedParent = parcel.readStrongBinder();
        }
        int i47 = i46 + 1;
        if (BitUtils.isBitSet(readLong, i46)) {
            this.mLeashedParentNodeId = parcel.readLong();
        }
        this.mSealed = z;
    }

    private void clear() {
        init(DEFAULT);
    }

    private static boolean isDefaultStandardAction(AccessibilityAction action) {
        return action.mSerializationFlag != -1 && TextUtils.isEmpty(action.getLabel());
    }

    private static AccessibilityAction getActionSingleton(int actionId) {
        int actions = AccessibilityAction.sStandardActions.size();
        for (int i = 0; i < actions; i++) {
            AccessibilityAction currentAction = AccessibilityAction.sStandardActions.valueAt(i);
            if (actionId == currentAction.getId()) {
                return currentAction;
            }
        }
        return null;
    }

    private static AccessibilityAction getActionSingletonBySerializationFlag(long flag) {
        int actions = AccessibilityAction.sStandardActions.size();
        for (int i = 0; i < actions; i++) {
            AccessibilityAction currentAction = AccessibilityAction.sStandardActions.valueAt(i);
            if (flag == currentAction.mSerializationFlag) {
                return currentAction;
            }
        }
        return null;
    }

    private void addStandardActions(long serializationIdMask) {
        long remainingIds = serializationIdMask;
        while (remainingIds > 0) {
            long id = 1 << Long.numberOfTrailingZeros(remainingIds);
            remainingIds &= ~id;
            AccessibilityAction action = getActionSingletonBySerializationFlag(id);
            addAction(action);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static String getActionSymbolicName(int action) {
        switch (action) {
            case 1:
                return "ACTION_FOCUS";
            case 2:
                return "ACTION_CLEAR_FOCUS";
            case 4:
                return "ACTION_SELECT";
            case 8:
                return "ACTION_CLEAR_SELECTION";
            case 16:
                return "ACTION_CLICK";
            case 32:
                return "ACTION_LONG_CLICK";
            case 64:
                return "ACTION_ACCESSIBILITY_FOCUS";
            case 128:
                return "ACTION_CLEAR_ACCESSIBILITY_FOCUS";
            case 256:
                return "ACTION_NEXT_AT_MOVEMENT_GRANULARITY";
            case 512:
                return "ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY";
            case 1024:
                return "ACTION_NEXT_HTML_ELEMENT";
            case 2048:
                return "ACTION_PREVIOUS_HTML_ELEMENT";
            case 4096:
                return "ACTION_SCROLL_FORWARD";
            case 8192:
                return "ACTION_SCROLL_BACKWARD";
            case 16384:
                return "ACTION_COPY";
            case 32768:
                return "ACTION_PASTE";
            case 65536:
                return "ACTION_CUT";
            case 131072:
                return "ACTION_SET_SELECTION";
            case 262144:
                return "ACTION_EXPAND";
            case 524288:
                return "ACTION_COLLAPSE";
            case 1048576:
                return "ACTION_DISMISS";
            case 2097152:
                return "ACTION_SET_TEXT";
            case 16908342:
                return "ACTION_SHOW_ON_SCREEN";
            case 16908343:
                return "ACTION_SCROLL_TO_POSITION";
            case 16908344:
                return "ACTION_SCROLL_UP";
            case 16908345:
                return "ACTION_SCROLL_LEFT";
            case 16908346:
                return "ACTION_SCROLL_DOWN";
            case 16908347:
                return "ACTION_SCROLL_RIGHT";
            case 16908348:
                return "ACTION_CONTEXT_CLICK";
            case 16908349:
                return "ACTION_SET_PROGRESS";
            case 16908356:
                return "ACTION_SHOW_TOOLTIP";
            case 16908357:
                return "ACTION_HIDE_TOOLTIP";
            case 16908358:
                return "ACTION_PAGE_UP";
            case 16908359:
                return "ACTION_PAGE_DOWN";
            case 16908360:
                return "ACTION_PAGE_LEFT";
            case 16908361:
                return "ACTION_PAGE_RIGHT";
            case 16908362:
                return "ACTION_PRESS_AND_HOLD";
            case 16908372:
                return "ACTION_IME_ENTER";
            case 16908373:
                return "ACTION_DRAG";
            case 16908374:
                return "ACTION_DROP";
            case 16908375:
                return "ACTION_CANCEL_DRAG";
            default:
                if (action == 16908376) {
                    return "ACTION_SHOW_TEXT_SUGGESTIONS";
                }
                if (action == 16908382) {
                    return "ACTION_SCROLL_IN_DIRECTION";
                }
                return "ACTION_UNKNOWN";
        }
    }

    private static String getMovementGranularitySymbolicName(int granularity) {
        switch (granularity) {
            case 1:
                return "MOVEMENT_GRANULARITY_CHARACTER";
            case 2:
                return "MOVEMENT_GRANULARITY_WORD";
            case 4:
                return "MOVEMENT_GRANULARITY_LINE";
            case 8:
                return "MOVEMENT_GRANULARITY_PARAGRAPH";
            case 16:
                return "MOVEMENT_GRANULARITY_PAGE";
            default:
                throw new IllegalArgumentException("Unknown movement granularity: " + granularity);
        }
    }

    private static boolean canPerformRequestOverConnection(int connectionId, int windowId, long accessibilityNodeId) {
        boolean hasWindowId = windowId != -1;
        return ((!usingDirectConnection(connectionId) && !hasWindowId) || getAccessibilityViewId(accessibilityNodeId) == Integer.MAX_VALUE || connectionId == -1) ? false : true;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        AccessibilityNodeInfo other = (AccessibilityNodeInfo) object;
        if (this.mSourceNodeId == other.mSourceNodeId && this.mWindowId == other.mWindowId) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = (1 * 31) + getAccessibilityViewId(this.mSourceNodeId);
        return (((result * 31) + getVirtualDescendantId(this.mSourceNodeId)) * 31) + this.mWindowId;
    }

    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.toString());
        if (DEBUG) {
            builder.append("; sourceNodeId: 0x").append(Long.toHexString(this.mSourceNodeId));
            builder.append("; windowId: 0x").append(Long.toHexString(this.mWindowId));
            builder.append("; accessibilityViewId: 0x").append(Long.toHexString(getAccessibilityViewId(this.mSourceNodeId)));
            builder.append("; virtualDescendantId: 0x").append(Long.toHexString(getVirtualDescendantId(this.mSourceNodeId)));
            builder.append("; mParentNodeId: 0x").append(Long.toHexString(this.mParentNodeId));
            builder.append("; traversalBefore: 0x").append(Long.toHexString(this.mTraversalBefore));
            builder.append("; traversalAfter: 0x").append(Long.toHexString(this.mTraversalAfter));
            builder.append("; minDurationBetweenContentChanges: ").append(this.mMinDurationBetweenContentChanges);
            int granularities = this.mMovementGranularities;
            builder.append("; MovementGranularities: [");
            while (granularities != 0) {
                int granularity = 1 << Integer.numberOfTrailingZeros(granularities);
                granularities &= ~granularity;
                builder.append(getMovementGranularitySymbolicName(granularity));
                if (granularities != 0) {
                    builder.append(", ");
                }
            }
            builder.append(NavigationBarInflaterView.SIZE_MOD_END);
            builder.append("; childAccessibilityIds: [");
            LongArray childIds = this.mChildNodeIds;
            if (childIds != null) {
                int count = childIds.size();
                for (int i = 0; i < count; i++) {
                    builder.append("0x").append(Long.toHexString(childIds.get(i)));
                    if (i < count - 1) {
                        builder.append(", ");
                    }
                }
            }
            builder.append(NavigationBarInflaterView.SIZE_MOD_END);
        }
        builder.append("; boundsInParent: ").append(this.mBoundsInParent);
        builder.append("; boundsInScreen: ").append(this.mBoundsInScreen);
        builder.append("; boundsInWindow: ").append(this.mBoundsInScreen);
        builder.append("; packageName: ").append(this.mPackageName);
        builder.append("; className: ").append(this.mClassName);
        builder.append("; text: ").append(this.mText);
        builder.append("; error: ").append(this.mError);
        builder.append("; maxTextLength: ").append(this.mMaxTextLength);
        builder.append("; stateDescription: ").append(this.mStateDescription);
        builder.append("; contentDescription: ").append(this.mContentDescription);
        builder.append("; tooltipText: ").append(this.mTooltipText);
        builder.append("; containerTitle: ").append(this.mContainerTitle);
        builder.append("; viewIdResName: ").append(this.mViewIdResourceName);
        builder.append("; uniqueId: ").append(this.mUniqueId);
        builder.append("; checkable: ").append(isCheckable());
        builder.append("; checked: ").append(isChecked());
        builder.append("; focusable: ").append(isFocusable());
        builder.append("; focused: ").append(isFocused());
        builder.append("; selected: ").append(isSelected());
        builder.append("; clickable: ").append(isClickable());
        builder.append("; longClickable: ").append(isLongClickable());
        builder.append("; contextClickable: ").append(isContextClickable());
        builder.append("; enabled: ").append(isEnabled());
        builder.append("; password: ").append(isPassword());
        builder.append("; scrollable: ").append(isScrollable());
        builder.append("; granularScrollingSupported: ").append(isGranularScrollingSupported());
        builder.append("; importantForAccessibility: ").append(isImportantForAccessibility());
        builder.append("; visible: ").append(isVisibleToUser());
        builder.append("; actions: ").append(this.mActions);
        builder.append("; isTextSelectable: ").append(isTextSelectable());
        return builder.toString();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static AccessibilityNodeInfo getNodeForAccessibilityId(int connectionId, int windowId, long accessibilityId) {
        return getNodeForAccessibilityId(connectionId, windowId, accessibilityId, 7);
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int connectionId, int windowId, long accessibilityId, int prefetchingStrategy) {
        if (!canPerformRequestOverConnection(connectionId, windowId, accessibilityId)) {
            return null;
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        return client.findAccessibilityNodeInfoByAccessibilityId(connectionId, windowId, accessibilityId, false, prefetchingStrategy, (Bundle) null);
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int connectionId, IBinder leashToken, long accessibilityId) {
        return getNodeForAccessibilityId(connectionId, leashToken, accessibilityId, 7);
    }

    private static AccessibilityNodeInfo getNodeForAccessibilityId(int connectionId, IBinder leashToken, long accessibilityId, int prefetchingStrategy) {
        if (leashToken == null || getAccessibilityViewId(accessibilityId) == Integer.MAX_VALUE || connectionId == -1) {
            return null;
        }
        AccessibilityInteractionClient client = AccessibilityInteractionClient.getInstance();
        return client.findAccessibilityNodeInfoByAccessibilityId(connectionId, leashToken, accessibilityId, false, prefetchingStrategy, (Bundle) null);
    }

    public static String idToString(long accessibilityId) {
        int accessibilityViewId = getAccessibilityViewId(accessibilityId);
        int virtualDescendantId = getVirtualDescendantId(accessibilityId);
        if (virtualDescendantId == -1) {
            return idItemToString(accessibilityViewId);
        }
        return idItemToString(accessibilityViewId) + ":" + idItemToString(virtualDescendantId);
    }

    private static String idItemToString(int item) {
        switch (item) {
            case -1:
                return "HOST";
            case 2147483646:
                return "ROOT";
            case Integer.MAX_VALUE:
                return DevicePolicyResources.UNDEFINED;
            default:
                return "" + item;
        }
    }

    public static final class AccessibilityAction implements Parcelable {
        private final int mActionId;
        private final CharSequence mLabel;
        public long mSerializationFlag;
        public static final ArraySet<AccessibilityAction> sStandardActions = new ArraySet<>();
        public static final AccessibilityAction ACTION_FOCUS = new AccessibilityAction(1);
        public static final AccessibilityAction ACTION_CLEAR_FOCUS = new AccessibilityAction(2);
        public static final AccessibilityAction ACTION_SELECT = new AccessibilityAction(4);
        public static final AccessibilityAction ACTION_CLEAR_SELECTION = new AccessibilityAction(8);
        public static final AccessibilityAction ACTION_CLICK = new AccessibilityAction(16);
        public static final AccessibilityAction ACTION_LONG_CLICK = new AccessibilityAction(32);
        public static final AccessibilityAction ACTION_ACCESSIBILITY_FOCUS = new AccessibilityAction(64);
        public static final AccessibilityAction ACTION_CLEAR_ACCESSIBILITY_FOCUS = new AccessibilityAction(128);
        public static final AccessibilityAction ACTION_NEXT_AT_MOVEMENT_GRANULARITY = new AccessibilityAction(256);
        public static final AccessibilityAction ACTION_PREVIOUS_AT_MOVEMENT_GRANULARITY = new AccessibilityAction(512);
        public static final AccessibilityAction ACTION_NEXT_HTML_ELEMENT = new AccessibilityAction(1024);
        public static final AccessibilityAction ACTION_PREVIOUS_HTML_ELEMENT = new AccessibilityAction(2048);
        public static final AccessibilityAction ACTION_SCROLL_FORWARD = new AccessibilityAction(4096);
        public static final AccessibilityAction ACTION_SCROLL_BACKWARD = new AccessibilityAction(8192);
        public static final AccessibilityAction ACTION_COPY = new AccessibilityAction(16384);
        public static final AccessibilityAction ACTION_PASTE = new AccessibilityAction(32768);
        public static final AccessibilityAction ACTION_CUT = new AccessibilityAction(65536);
        public static final AccessibilityAction ACTION_SET_SELECTION = new AccessibilityAction(131072);
        public static final AccessibilityAction ACTION_EXPAND = new AccessibilityAction(262144);
        public static final AccessibilityAction ACTION_COLLAPSE = new AccessibilityAction(524288);
        public static final AccessibilityAction ACTION_DISMISS = new AccessibilityAction(1048576);
        public static final AccessibilityAction ACTION_SET_TEXT = new AccessibilityAction(2097152);
        public static final AccessibilityAction ACTION_SHOW_ON_SCREEN = new AccessibilityAction(16908342);
        public static final AccessibilityAction ACTION_SCROLL_TO_POSITION = new AccessibilityAction(16908343);
        public static final AccessibilityAction ACTION_SCROLL_IN_DIRECTION = new AccessibilityAction(16908382);
        public static final AccessibilityAction ACTION_SCROLL_UP = new AccessibilityAction(16908344);
        public static final AccessibilityAction ACTION_SCROLL_LEFT = new AccessibilityAction(16908345);
        public static final AccessibilityAction ACTION_SCROLL_DOWN = new AccessibilityAction(16908346);
        public static final AccessibilityAction ACTION_SCROLL_RIGHT = new AccessibilityAction(16908347);
        public static final AccessibilityAction SEM_ACTION_AUTOSCROLL_ON = new AccessibilityAction(4194304);
        public static final AccessibilityAction SEM_ACTION_AUTOSCROLL_OFF = new AccessibilityAction(8388608);
        public static final AccessibilityAction ACTION_PAGE_UP = new AccessibilityAction(16908358);
        public static final AccessibilityAction ACTION_PAGE_DOWN = new AccessibilityAction(16908359);
        public static final AccessibilityAction ACTION_PAGE_LEFT = new AccessibilityAction(16908360);
        public static final AccessibilityAction ACTION_PAGE_RIGHT = new AccessibilityAction(16908361);
        public static final AccessibilityAction ACTION_CONTEXT_CLICK = new AccessibilityAction(16908348);
        public static final AccessibilityAction ACTION_SET_PROGRESS = new AccessibilityAction(16908349);
        public static final AccessibilityAction ACTION_MOVE_WINDOW = new AccessibilityAction(16908354);
        public static final AccessibilityAction ACTION_SHOW_TOOLTIP = new AccessibilityAction(16908356);
        public static final AccessibilityAction ACTION_HIDE_TOOLTIP = new AccessibilityAction(16908357);
        public static final AccessibilityAction ACTION_PRESS_AND_HOLD = new AccessibilityAction(16908362);
        public static final AccessibilityAction ACTION_IME_ENTER = new AccessibilityAction(16908372);
        public static final AccessibilityAction ACTION_DRAG_START = new AccessibilityAction(16908373);
        public static final AccessibilityAction ACTION_DRAG_DROP = new AccessibilityAction(16908374);
        public static final AccessibilityAction ACTION_DRAG_CANCEL = new AccessibilityAction(16908375);
        public static final AccessibilityAction ACTION_SHOW_TEXT_SUGGESTIONS = new AccessibilityAction(16908376);
        public static final Parcelable.Creator<AccessibilityAction> CREATOR = new Parcelable.Creator<AccessibilityAction>() { // from class: android.view.accessibility.AccessibilityNodeInfo.AccessibilityAction.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityAction createFromParcel(Parcel in) {
                return new AccessibilityAction(in);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public AccessibilityAction[] newArray(int size) {
                return new AccessibilityAction[size];
            }
        };

        public AccessibilityAction(int actionId, CharSequence label) {
            this.mSerializationFlag = -1L;
            this.mActionId = actionId;
            this.mLabel = label;
        }

        private AccessibilityAction(int standardActionId) {
            this(standardActionId, (CharSequence) null);
            this.mSerializationFlag = BitUtils.bitAt(sStandardActions.size());
            sStandardActions.add(this);
        }

        public int getId() {
            return this.mActionId;
        }

        public CharSequence getLabel() {
            return this.mLabel;
        }

        public int hashCode() {
            return this.mActionId;
        }

        public boolean equals(Object other) {
            if (other == null) {
                return false;
            }
            if (other == this) {
                return true;
            }
            if (getClass() != other.getClass() || this.mActionId != ((AccessibilityAction) other).mActionId) {
                return false;
            }
            return true;
        }

        public String toString() {
            return "AccessibilityAction: " + AccessibilityNodeInfo.getActionSymbolicName(this.mActionId) + " - " + ((Object) this.mLabel);
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel out, int flags) {
            out.writeInt(this.mActionId);
            out.writeCharSequence(this.mLabel);
        }

        private AccessibilityAction(Parcel in) {
            this.mSerializationFlag = -1L;
            this.mActionId = in.readInt();
            this.mLabel = in.readCharSequence();
        }
    }

    public static final class RangeInfo {
        public static final int RANGE_TYPE_FLOAT = 1;
        public static final int RANGE_TYPE_INT = 0;
        public static final int RANGE_TYPE_PERCENT = 2;
        private float mCurrent;
        private float mMax;
        private float mMin;
        private int mType;

        @Deprecated
        public static RangeInfo obtain(int type, float min, float max, float current) {
            return new RangeInfo(type, min, max, current);
        }

        public RangeInfo(int type, float min, float max, float current) {
            this.mType = type;
            this.mMin = min;
            this.mMax = max;
            this.mCurrent = current;
        }

        public int getType() {
            return this.mType;
        }

        public float getMin() {
            return this.mMin;
        }

        public float getMax() {
            return this.mMax;
        }

        public float getCurrent() {
            return this.mCurrent;
        }

        @Deprecated
        void recycle() {
        }

        private void clear() {
            this.mType = 0;
            this.mMin = 0.0f;
            this.mMax = 0.0f;
            this.mCurrent = 0.0f;
        }
    }

    public static final class CollectionInfo {
        public static final int SELECTION_MODE_MULTIPLE = 2;
        public static final int SELECTION_MODE_NONE = 0;
        public static final int SELECTION_MODE_SINGLE = 1;
        public static final int UNDEFINED = -1;
        private int mColumnCount;
        private boolean mHierarchical;
        private int mImportantForAccessibilityItemCount;
        private int mItemCount;
        private int mRowCount;
        private int mSelectionMode;

        public static CollectionInfo obtain(CollectionInfo other) {
            return new CollectionInfo(other.mRowCount, other.mColumnCount, other.mHierarchical, other.mSelectionMode, other.mItemCount, other.mImportantForAccessibilityItemCount);
        }

        public static CollectionInfo obtain(int rowCount, int columnCount, boolean hierarchical) {
            return new CollectionInfo(rowCount, columnCount, hierarchical, 0);
        }

        public static CollectionInfo obtain(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            return new CollectionInfo(rowCount, columnCount, hierarchical, selectionMode);
        }

        public CollectionInfo(int rowCount, int columnCount, boolean hierarchical) {
            this(rowCount, columnCount, hierarchical, 0);
        }

        public CollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode) {
            this.mRowCount = rowCount;
            this.mColumnCount = columnCount;
            this.mHierarchical = hierarchical;
            this.mSelectionMode = selectionMode;
            this.mItemCount = -1;
            this.mImportantForAccessibilityItemCount = -1;
        }

        public CollectionInfo(int rowCount, int columnCount, boolean hierarchical, int selectionMode, int itemCount, int importantForAccessibilityItemCount) {
            this.mRowCount = rowCount;
            this.mColumnCount = columnCount;
            this.mHierarchical = hierarchical;
            this.mSelectionMode = selectionMode;
            this.mItemCount = itemCount;
            this.mImportantForAccessibilityItemCount = importantForAccessibilityItemCount;
        }

        public int getRowCount() {
            return this.mRowCount;
        }

        public int getColumnCount() {
            return this.mColumnCount;
        }

        public boolean isHierarchical() {
            return this.mHierarchical;
        }

        public int getSelectionMode() {
            return this.mSelectionMode;
        }

        public int getItemCount() {
            return this.mItemCount;
        }

        public int getImportantForAccessibilityItemCount() {
            return this.mImportantForAccessibilityItemCount;
        }

        @Deprecated
        void recycle() {
        }

        private void clear() {
            this.mRowCount = 0;
            this.mColumnCount = 0;
            this.mHierarchical = false;
            this.mSelectionMode = 0;
            this.mItemCount = -1;
            this.mImportantForAccessibilityItemCount = -1;
        }

        public static final class Builder {
            private int mSelectionMode;
            private int mRowCount = 0;
            private int mColumnCount = 0;
            private boolean mHierarchical = false;
            private int mItemCount = -1;
            private int mImportantForAccessibilityItemCount = -1;

            public Builder setRowCount(int rowCount) {
                this.mRowCount = rowCount;
                return this;
            }

            public Builder setColumnCount(int columnCount) {
                this.mColumnCount = columnCount;
                return this;
            }

            public Builder setHierarchical(boolean hierarchical) {
                this.mHierarchical = hierarchical;
                return this;
            }

            public Builder setSelectionMode(int selectionMode) {
                this.mSelectionMode = selectionMode;
                return this;
            }

            public Builder setItemCount(int itemCount) {
                this.mItemCount = itemCount;
                return this;
            }

            public Builder setImportantForAccessibilityItemCount(int importantForAccessibilityItemCount) {
                this.mImportantForAccessibilityItemCount = importantForAccessibilityItemCount;
                return this;
            }

            public CollectionInfo build() {
                CollectionInfo collectionInfo = new CollectionInfo(this.mRowCount, this.mColumnCount, this.mHierarchical);
                collectionInfo.mSelectionMode = this.mSelectionMode;
                collectionInfo.mItemCount = this.mItemCount;
                collectionInfo.mImportantForAccessibilityItemCount = this.mImportantForAccessibilityItemCount;
                return collectionInfo;
            }
        }
    }

    public static final class CollectionItemInfo {
        private int mColumnIndex;
        private int mColumnSpan;
        private String mColumnTitle;
        private boolean mHeading;
        private int mRowIndex;
        private int mRowSpan;
        private String mRowTitle;
        private boolean mSelected;

        @Deprecated
        public static CollectionItemInfo obtain(CollectionItemInfo other) {
            return new CollectionItemInfo(other.mRowTitle, other.mRowIndex, other.mRowSpan, other.mColumnTitle, other.mColumnIndex, other.mColumnSpan, other.mHeading, other.mSelected);
        }

        @Deprecated
        public static CollectionItemInfo obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            return new CollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, heading, false);
        }

        @Deprecated
        public static CollectionItemInfo obtain(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            return new CollectionItemInfo(rowIndex, rowSpan, columnIndex, columnSpan, heading, selected);
        }

        @Deprecated
        public static CollectionItemInfo obtain(String rowTitle, int rowIndex, int rowSpan, String columnTitle, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            return new CollectionItemInfo(rowTitle, rowIndex, rowSpan, columnTitle, columnIndex, columnSpan, heading, selected);
        }

        private CollectionItemInfo() {
        }

        public CollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading) {
            this(rowIndex, rowSpan, columnIndex, columnSpan, heading, false);
        }

        public CollectionItemInfo(int rowIndex, int rowSpan, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            this(null, rowIndex, rowSpan, null, columnIndex, columnSpan, heading, selected);
        }

        public CollectionItemInfo(String rowTitle, int rowIndex, int rowSpan, String columnTitle, int columnIndex, int columnSpan, boolean heading, boolean selected) {
            this.mRowIndex = rowIndex;
            this.mRowSpan = rowSpan;
            this.mColumnIndex = columnIndex;
            this.mColumnSpan = columnSpan;
            this.mHeading = heading;
            this.mSelected = selected;
            this.mRowTitle = rowTitle;
            this.mColumnTitle = columnTitle;
        }

        public int getColumnIndex() {
            return this.mColumnIndex;
        }

        public int getRowIndex() {
            return this.mRowIndex;
        }

        public int getColumnSpan() {
            return this.mColumnSpan;
        }

        public int getRowSpan() {
            return this.mRowSpan;
        }

        public boolean isHeading() {
            return this.mHeading;
        }

        public boolean isSelected() {
            return this.mSelected;
        }

        public String getRowTitle() {
            return this.mRowTitle;
        }

        public String getColumnTitle() {
            return this.mColumnTitle;
        }

        @Deprecated
        void recycle() {
        }

        private void clear() {
            this.mColumnIndex = 0;
            this.mColumnSpan = 0;
            this.mRowIndex = 0;
            this.mRowSpan = 0;
            this.mHeading = false;
            this.mSelected = false;
            this.mRowTitle = null;
            this.mColumnTitle = null;
        }

        public static final class Builder {
            private int mColumnIndex;
            private int mColumnSpan;
            private String mColumnTitle;
            private boolean mHeading;
            private int mRowIndex;
            private int mRowSpan;
            private String mRowTitle;
            private boolean mSelected;

            public Builder setHeading(boolean heading) {
                this.mHeading = heading;
                return this;
            }

            public Builder setColumnIndex(int columnIndex) {
                this.mColumnIndex = columnIndex;
                return this;
            }

            public Builder setRowIndex(int rowIndex) {
                this.mRowIndex = rowIndex;
                return this;
            }

            public Builder setColumnSpan(int columnSpan) {
                this.mColumnSpan = columnSpan;
                return this;
            }

            public Builder setRowSpan(int rowSpan) {
                this.mRowSpan = rowSpan;
                return this;
            }

            public Builder setSelected(boolean selected) {
                this.mSelected = selected;
                return this;
            }

            public Builder setRowTitle(String rowTitle) {
                this.mRowTitle = rowTitle;
                return this;
            }

            public Builder setColumnTitle(String columnTitle) {
                this.mColumnTitle = columnTitle;
                return this;
            }

            public CollectionItemInfo build() {
                CollectionItemInfo collectionItemInfo = new CollectionItemInfo();
                collectionItemInfo.mHeading = this.mHeading;
                collectionItemInfo.mColumnIndex = this.mColumnIndex;
                collectionItemInfo.mRowIndex = this.mRowIndex;
                collectionItemInfo.mColumnSpan = this.mColumnSpan;
                collectionItemInfo.mRowSpan = this.mRowSpan;
                collectionItemInfo.mSelected = this.mSelected;
                collectionItemInfo.mRowTitle = this.mRowTitle;
                collectionItemInfo.mColumnTitle = this.mColumnTitle;
                return collectionItemInfo;
            }
        }
    }

    public static final class TouchDelegateInfo implements Parcelable {
        public static final Parcelable.Creator<TouchDelegateInfo> CREATOR = new Parcelable.Creator<TouchDelegateInfo>() { // from class: android.view.accessibility.AccessibilityNodeInfo.TouchDelegateInfo.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TouchDelegateInfo createFromParcel(Parcel parcel) {
                int size = parcel.readInt();
                if (size == 0) {
                    return null;
                }
                ArrayMap<Region, Long> targetMap = new ArrayMap<>(size);
                for (int i = 0; i < size; i++) {
                    Region region = Region.CREATOR.createFromParcel(parcel);
                    long accessibilityId = parcel.readLong();
                    targetMap.put(region, Long.valueOf(accessibilityId));
                }
                TouchDelegateInfo touchDelegateInfo = new TouchDelegateInfo(targetMap, false);
                return touchDelegateInfo;
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public TouchDelegateInfo[] newArray(int size) {
                return new TouchDelegateInfo[size];
            }
        };
        private int mConnectionId;
        private ArrayMap<Region, Long> mTargetMap;
        private int mWindowId;

        public TouchDelegateInfo(Map<Region, View> targetMap) {
            Preconditions.checkArgument((targetMap.isEmpty() || targetMap.containsKey(null) || targetMap.containsValue(null)) ? false : true);
            this.mTargetMap = new ArrayMap<>(targetMap.size());
            for (Region region : targetMap.keySet()) {
                View view = targetMap.get(region);
                this.mTargetMap.put(region, Long.valueOf(view.getAccessibilityViewId()));
            }
        }

        TouchDelegateInfo(ArrayMap<Region, Long> targetMap, boolean doCopy) {
            Preconditions.checkArgument((targetMap.isEmpty() || targetMap.containsKey(null) || targetMap.containsValue(null)) ? false : true);
            if (doCopy) {
                this.mTargetMap = new ArrayMap<>(targetMap.size());
                this.mTargetMap.putAll((ArrayMap<? extends Region, ? extends Long>) targetMap);
            } else {
                this.mTargetMap = targetMap;
            }
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setConnectionId(int connectionId) {
            this.mConnectionId = connectionId;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setWindowId(int windowId) {
            this.mWindowId = windowId;
        }

        public int getRegionCount() {
            return this.mTargetMap.size();
        }

        public Region getRegionAt(int index) {
            return this.mTargetMap.keyAt(index);
        }

        public AccessibilityNodeInfo getTargetForRegion(Region region) {
            return AccessibilityNodeInfo.getNodeForAccessibilityId(this.mConnectionId, this.mWindowId, this.mTargetMap.get(region).longValue());
        }

        public long getAccessibilityIdForRegion(Region region) {
            return this.mTargetMap.get(region).longValue();
        }

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.mTargetMap.size());
            for (int i = 0; i < this.mTargetMap.size(); i++) {
                Region region = this.mTargetMap.keyAt(i);
                Long accessibilityId = this.mTargetMap.valueAt(i);
                region.writeToParcel(dest, flags);
                dest.writeLong(accessibilityId.longValue());
            }
        }
    }

    public static final class ExtraRenderingInfo {
        private static final int UNDEFINED_VALUE = -1;
        private Size mLayoutSize;
        private float mTextSizeInPx;
        private int mTextSizeUnit;

        @Deprecated
        public static ExtraRenderingInfo obtain() {
            return new ExtraRenderingInfo(null);
        }

        @Deprecated
        private static ExtraRenderingInfo obtain(ExtraRenderingInfo other) {
            return new ExtraRenderingInfo(other);
        }

        private ExtraRenderingInfo(ExtraRenderingInfo other) {
            this.mTextSizeInPx = -1.0f;
            this.mTextSizeUnit = -1;
            if (other != null) {
                this.mLayoutSize = other.mLayoutSize;
                this.mTextSizeInPx = other.mTextSizeInPx;
                this.mTextSizeUnit = other.mTextSizeUnit;
            }
        }

        public Size getLayoutSize() {
            return this.mLayoutSize;
        }

        public void setLayoutSize(int width, int height) {
            this.mLayoutSize = new Size(width, height);
        }

        public float getTextSizeInPx() {
            return this.mTextSizeInPx;
        }

        public void setTextSizeInPx(float textSizeInPx) {
            this.mTextSizeInPx = textSizeInPx;
        }

        public int getTextSizeUnit() {
            return this.mTextSizeUnit;
        }

        public void setTextSizeUnit(int textSizeUnit) {
            this.mTextSizeUnit = textSizeUnit;
        }

        @Deprecated
        void recycle() {
        }

        private void clear() {
            this.mLayoutSize = null;
            this.mTextSizeInPx = -1.0f;
            this.mTextSizeUnit = -1;
        }
    }
}
