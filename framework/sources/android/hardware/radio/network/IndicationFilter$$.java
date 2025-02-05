package android.hardware.radio.network;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.security.keystore.KeyProperties;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import java.lang.reflect.Array;
import java.util.StringJoiner;

/* loaded from: classes2.dex */
public interface IndicationFilter$$ {
    static String toString(int _aidl_v) {
        return _aidl_v == 0 ? KeyProperties.DIGEST_NONE : _aidl_v == -1 ? SemInputDeviceManager.MOTION_CONTROL_TYPE_ALL : _aidl_v == 1 ? "SIGNAL_STRENGTH" : _aidl_v == 2 ? "FULL_NETWORK_STATE" : _aidl_v == 4 ? "DATA_CALL_DORMANCY_CHANGED" : _aidl_v == 8 ? "LINK_CAPACITY_ESTIMATE" : _aidl_v == 16 ? "PHYSICAL_CHANNEL_CONFIG" : _aidl_v == 32 ? "REGISTRATION_FAILURE" : _aidl_v == 64 ? "BARRING_INFO" : Integer.toString(_aidl_v);
    }

    static String arrayToString(Object _aidl_v) {
        if (_aidl_v == null) {
            return "null";
        }
        Class<?> _aidl_cls = _aidl_v.getClass();
        if (!_aidl_cls.isArray()) {
            throw new IllegalArgumentException("not an array: " + _aidl_v);
        }
        Class<?> comp = _aidl_cls.getComponentType();
        StringJoiner _aidl_sj = new StringJoiner(", ", NavigationBarInflaterView.SIZE_MOD_START, NavigationBarInflaterView.SIZE_MOD_END);
        if (comp.isArray()) {
            for (int _aidl_i = 0; _aidl_i < Array.getLength(_aidl_v); _aidl_i++) {
                _aidl_sj.add(arrayToString(Array.get(_aidl_v, _aidl_i)));
            }
        } else {
            if (_aidl_cls != int[].class) {
                throw new IllegalArgumentException("wrong type: " + _aidl_cls);
            }
            for (int e : (int[]) _aidl_v) {
                _aidl_sj.add(toString(e));
            }
        }
        return _aidl_sj.toString();
    }
}
