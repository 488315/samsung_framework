package android.internal.perfetto.protos;

/* loaded from: classes2.dex */
public final class Inputmethodeditor {

    public final class InputMethodClientsTraceProto {
        public static final long CLIENT = 1146756268035L;
        public static final long ELAPSED_REALTIME_NANOS = 1125281431553L;
        public static final long WHERE = 1138166333442L;

        public InputMethodClientsTraceProto() {
        }

        public final class ClientSideProto {
            public static final long DISPLAY_ID = 1120986464257L;
            public static final long EDITOR_INFO = 1146756268038L;
            public static final long IME_FOCUS_CONTROLLER = 1146756268039L;
            public static final long IME_INSETS_SOURCE_CONSUMER = 1146756268037L;
            public static final long INPUT_CONNECTION = 1146756268040L;
            public static final long INPUT_CONNECTION_CALL = 1146756268041L;
            public static final long INPUT_METHOD_MANAGER = 1146756268034L;
            public static final long INSETS_CONTROLLER = 1146756268036L;
            public static final long VIEW_ROOT_IMPL = 1146756268035L;

            public ClientSideProto() {
            }
        }
    }

    public final class InputMethodServiceTraceProto {
        public static final long ELAPSED_REALTIME_NANOS = 1125281431553L;
        public static final long INPUT_METHOD_SERVICE = 1146756268035L;
        public static final long WHERE = 1138166333442L;

        public InputMethodServiceTraceProto() {
        }
    }

    public final class InputMethodManagerServiceTraceProto {
        public static final long ELAPSED_REALTIME_NANOS = 1125281431553L;
        public static final long INPUT_METHOD_MANAGER_SERVICE = 1146756268035L;
        public static final long WHERE = 1138166333442L;

        public InputMethodManagerServiceTraceProto() {
        }
    }
}
