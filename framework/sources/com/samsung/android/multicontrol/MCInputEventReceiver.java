package com.samsung.android.multicontrol;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.InputChannel;
import android.view.InputEvent;
import android.view.InputEventReceiver;
import android.view.InputMonitor;
import android.view.MotionEvent;
import android.view.WindowManager;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class MCInputEventReceiver extends InputEventReceiver {
    private final String ACTION_TRIGGERED;
    private final int DEFAULT_TRIGGER_THRESHOLD;
    private final String EXTRA_TRIGGER_DIRECTION;
    private final String EXTRA_TRIGGER_DISPLAY_ID;
    private final String EXTRA_TRIGGER_X;
    private final String EXTRA_TRIGGER_Y;
    private final String RECEIVER_PERMISSION;
    private final String TAG;
    public final String TAG_PREFIX;
    private float countX;
    private float countY;
    private ArrayList<Direction> directionList;
    private boolean firstInput;
    Context mContext;
    private int mDisplayId;
    InputMonitor mInputMonitor;
    private WindowManager mWindowManager;
    private int triggerThreshold;

    /* JADX INFO: Access modifiers changed from: private */
    /* loaded from: classes5.dex */
    public enum Direction {
        RIGHT,
        LEFT,
        TOP,
        BOTTOM
    }

    public MCInputEventReceiver(Context context, int displayId, InputMonitor inputMonitor, InputChannel inputChannel, Looper looper) {
        super(inputChannel, looper);
        this.TAG_PREFIX = SemMultiControlManager.TAG_PREFIX;
        this.TAG = SemMultiControlManager.TAG_PREFIX + MCInputEventReceiver.class.getSimpleName();
        this.ACTION_TRIGGERED = "com.samsung.android.inputshare.action.ACTION_TRIGGERED";
        this.EXTRA_TRIGGER_X = "x";
        this.EXTRA_TRIGGER_Y = "y";
        this.EXTRA_TRIGGER_DISPLAY_ID = "displayId";
        this.EXTRA_TRIGGER_DIRECTION = "direction";
        this.RECEIVER_PERMISSION = Manifest.permission.MULTI_CONTROL_RECEIVER_PERMISSION;
        this.DEFAULT_TRIGGER_THRESHOLD = 150;
        this.triggerThreshold = 150;
        this.mDisplayId = 0;
        this.directionList = new ArrayList<>();
        this.countX = 0.0f;
        this.countY = 0.0f;
        this.firstInput = true;
        this.mContext = context;
        this.mInputMonitor = inputMonitor;
        this.mWindowManager = (WindowManager) context.getSystemService(WindowManager.class);
        this.directionList.add(Direction.RIGHT);
        this.directionList.add(Direction.LEFT);
        this.directionList.add(Direction.TOP);
        this.directionList.add(Direction.BOTTOM);
        this.mDisplayId = displayId;
    }

    @Override // android.view.InputEventReceiver
    public void onInputEvent(InputEvent inputEvent) {
        try {
            if (this.firstInput) {
                Rect displayRect = this.mWindowManager.getMaximumWindowMetrics().getBounds();
                Log.i(this.TAG, "[onInputEvent] displayRect : " + displayRect);
                this.firstInput = false;
            }
            if (MotionEvent.class.isInstance(inputEvent)) {
                handleInput((MotionEvent) inputEvent);
            }
        } catch (Exception e) {
        }
        finishInputEvent(inputEvent, true);
    }

    public void setTriggerThreshold(int threshold) {
        try {
            this.triggerThreshold = threshold;
        } catch (Exception e) {
            Log.e(this.TAG, "[setTriggerThreshold]", e);
        }
    }

    private boolean isValidMove(float x, float y, Direction direction) {
        Rect displayRect = this.mWindowManager.getMaximumWindowMetrics().getBounds();
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction[direction.ordinal()]) {
            case 1:
                return y <= ((float) displayRect.top);
            case 2:
                return x <= ((float) displayRect.left);
            case 3:
                return x >= ((float) (displayRect.right + (-5)));
            case 4:
                return y >= ((float) (displayRect.bottom + (-5)));
            default:
                return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    /* renamed from: com.samsung.android.multicontrol.MCInputEventReceiver$1, reason: invalid class name */
    /* loaded from: classes5.dex */
    public static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction;

        static {
            int[] iArr = new int[Direction.values().length];
            $SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction = iArr;
            try {
                iArr[Direction.TOP.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction[Direction.LEFT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction[Direction.RIGHT.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction[Direction.BOTTOM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private boolean isValidTrigger(float x, float y, Direction direction) {
        switch (AnonymousClass1.$SwitchMap$com$samsung$android$multicontrol$MCInputEventReceiver$Direction[direction.ordinal()]) {
            case 1:
                if (y < 0.0f) {
                    this.countY += 1.0f;
                    break;
                }
                break;
            case 2:
                if (x < 0.0f) {
                    this.countX += 1.0f;
                    break;
                }
                break;
            case 3:
                if (x > 0.0f) {
                    this.countX += 1.0f;
                    break;
                }
                break;
            case 4:
                if (y > 0.0f) {
                    this.countY += 1.0f;
                    break;
                }
                break;
        }
        float f = this.countX;
        int i = this.triggerThreshold;
        return f > ((float) i) || this.countY > ((float) i);
    }

    private void handleInput(MotionEvent motionEvent) {
        try {
            float x = motionEvent.getX();
            float y = motionEvent.getY();
            float relX = motionEvent.getAxisValue(27);
            float relY = motionEvent.getAxisValue(28);
            Iterator<Direction> it = this.directionList.iterator();
            while (it.hasNext()) {
                Direction direction = it.next();
                if (isValidMove(x, y, direction)) {
                    if (isValidTrigger(relX, relY, direction)) {
                        this.countX = 0.0f;
                        this.countY = 0.0f;
                        try {
                            Intent intent = new Intent("com.samsung.android.inputshare.action.ACTION_TRIGGERED");
                            intent.putExtra("displayId", this.mDisplayId);
                            intent.putExtra("direction", direction.name());
                            intent.putExtra("y", y);
                            intent.putExtra("x", x);
                            intent.setPackage("com.samsung.android.inputshare");
                            this.mContext.sendBroadcastAsUser(intent, UserHandle.SEM_CURRENT, Manifest.permission.MULTI_CONTROL_RECEIVER_PERMISSION);
                            Log.i(this.TAG, "sendBroadcast - ACTION_TRIGGERED");
                            return;
                        } catch (Exception e) {
                            Log.e(this.TAG, "sendBroadcast - ACTION_TRIGGERED", e);
                            return;
                        }
                    }
                    return;
                }
            }
            this.countX = 0.0f;
            this.countY = 0.0f;
        } catch (Exception e2) {
            Log.e(this.TAG, "[handleInput]", e2);
        }
    }
}
