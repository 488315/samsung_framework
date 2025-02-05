package android.service.controls.templates;

import android.os.Bundle;
import android.util.Log;
import com.android.internal.util.Preconditions;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/* loaded from: classes3.dex */
public final class TemperatureControlTemplate extends ControlTemplate {
    private static final int ALL_FLAGS = 62;
    public static final int FLAG_MODE_COOL = 8;
    public static final int FLAG_MODE_ECO = 32;
    public static final int FLAG_MODE_HEAT = 4;
    public static final int FLAG_MODE_HEAT_COOL = 16;
    public static final int FLAG_MODE_OFF = 2;
    private static final String KEY_CURRENT_ACTIVE_MODE = "key_current_active_mode";
    private static final String KEY_CURRENT_MODE = "key_current_mode";
    private static final String KEY_MODES = "key_modes";
    private static final String KEY_TEMPLATE = "key_template";
    public static final int MODE_COOL = 3;
    public static final int MODE_ECO = 5;
    public static final int MODE_HEAT = 2;
    public static final int MODE_HEAT_COOL = 4;
    public static final int MODE_OFF = 1;
    public static final int MODE_UNKNOWN = 0;
    private static final int NUM_MODES = 6;
    private static final String TAG = "ThermostatTemplate";
    private static final int TYPE = 7;
    private static final int[] modeToFlag = {0, 2, 4, 8, 16, 32};
    private final int mCurrentActiveMode;
    private final int mCurrentMode;
    private final int mModes;
    private final ControlTemplate mTemplate;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Mode {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface ModeFlag {
    }

    public TemperatureControlTemplate(String templateId, ControlTemplate controlTemplate, int currentMode, int currentActiveMode, int modesFlag) {
        super(templateId);
        Preconditions.checkNotNull(controlTemplate);
        this.mTemplate = controlTemplate;
        if (currentMode < 0 || currentMode >= 6) {
            Log.e(TAG, "Invalid current mode:" + currentMode);
            this.mCurrentMode = 0;
        } else {
            this.mCurrentMode = currentMode;
        }
        if (currentActiveMode < 0 || currentActiveMode >= 6) {
            Log.e(TAG, "Invalid current active mode:" + currentActiveMode);
            this.mCurrentActiveMode = 0;
        } else {
            this.mCurrentActiveMode = currentActiveMode;
        }
        this.mModes = modesFlag & 62;
        if (this.mCurrentMode != 0 && (modeToFlag[this.mCurrentMode] & this.mModes) == 0) {
            throw new IllegalArgumentException("Mode " + this.mCurrentMode + " not supported in flag.");
        }
        if (this.mCurrentActiveMode != 0 && (modeToFlag[this.mCurrentActiveMode] & this.mModes) == 0) {
            throw new IllegalArgumentException("Mode " + currentActiveMode + " not supported in flag.");
        }
    }

    TemperatureControlTemplate(Bundle b) {
        super(b);
        this.mTemplate = ControlTemplate.createTemplateFromBundle(b.getBundle(KEY_TEMPLATE));
        this.mCurrentMode = b.getInt(KEY_CURRENT_MODE);
        this.mCurrentActiveMode = b.getInt(KEY_CURRENT_ACTIVE_MODE);
        this.mModes = b.getInt(KEY_MODES);
    }

    @Override // android.service.controls.templates.ControlTemplate
    Bundle getDataBundle() {
        Bundle b = super.getDataBundle();
        b.putBundle(KEY_TEMPLATE, this.mTemplate.getDataBundle());
        b.putInt(KEY_CURRENT_MODE, this.mCurrentMode);
        b.putInt(KEY_CURRENT_ACTIVE_MODE, this.mCurrentActiveMode);
        b.putInt(KEY_MODES, this.mModes);
        return b;
    }

    public ControlTemplate getTemplate() {
        return this.mTemplate;
    }

    public int getCurrentMode() {
        return this.mCurrentMode;
    }

    public int getCurrentActiveMode() {
        return this.mCurrentActiveMode;
    }

    public int getModes() {
        return this.mModes;
    }

    @Override // android.service.controls.templates.ControlTemplate
    public int getTemplateType() {
        return 7;
    }
}
