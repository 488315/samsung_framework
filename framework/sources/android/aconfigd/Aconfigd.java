package android.aconfigd;

/* loaded from: classes.dex */
public final class Aconfigd {

    public final class PersistStorageRecord {
        public static final long CONTAINER = 1138166333442L;
        public static final long DIGEST = 1138166333446L;
        public static final long FLAG_MAP = 1138166333444L;
        public static final long FLAG_VAL = 1138166333445L;
        public static final long PACKAGE_MAP = 1138166333443L;
        public static final long VERSION = 1155346202625L;

        public PersistStorageRecord() {
        }
    }

    public final class PersistStorageRecords {
        public static final long RECORDS = 2246267895809L;

        public PersistStorageRecords() {
        }
    }

    public final class FlagOverride {
        public static final long FLAG_NAME = 1138166333442L;
        public static final long FLAG_VALUE = 1138166333443L;
        public static final long PACKAGE_NAME = 1138166333441L;

        public FlagOverride() {
        }
    }

    public final class LocalFlagOverrides {
        public static final long OVERRIDES = 2246267895809L;

        public LocalFlagOverrides() {
        }
    }

    public final class StorageRequestMessage {
        public static final long FLAG_OVERRIDE_MESSAGE = 1146756268034L;
        public static final long FLAG_QUERY_MESSAGE = 1146756268036L;
        public static final long LIST_STORAGE_MESSAGE = 1146756268039L;
        public static final long NEW_STORAGE_MESSAGE = 1146756268033L;
        public static final long OTA_STAGING_MESSAGE = 1146756268035L;
        public static final long REMOVE_LOCAL_OVERRIDE_MESSAGE = 1146756268037L;
        public static final long RESET_STORAGE_MESSAGE = 1146756268038L;

        public StorageRequestMessage() {
        }

        public final class NewStorageMessage {
            public static final long CONTAINER = 1138166333441L;
            public static final long FLAG_MAP = 1138166333443L;
            public static final long FLAG_VALUE = 1138166333444L;
            public static final long PACKAGE_MAP = 1138166333442L;

            public NewStorageMessage() {
            }
        }

        public final class FlagOverrideMessage {
            public static final long FLAG_NAME = 1138166333442L;
            public static final long FLAG_VALUE = 1138166333443L;
            public static final long IS_LOCAL = 1133871366148L;
            public static final long PACKAGE_NAME = 1138166333441L;

            public FlagOverrideMessage() {
            }
        }

        public final class OTAFlagStagingMessage {
            public static final long BUILD_ID = 1138166333441L;
            public static final long OVERRIDES = 2246267895810L;

            public OTAFlagStagingMessage() {
            }
        }

        public final class RemoveLocalOverrideMessage {
            public static final long FLAG_NAME = 1138166333443L;
            public static final long PACKAGE_NAME = 1138166333442L;
            public static final long REMOVE_ALL = 1133871366145L;

            public RemoveLocalOverrideMessage() {
            }
        }

        public final class FlagQueryMessage {
            public static final long FLAG_NAME = 1138166333442L;
            public static final long PACKAGE_NAME = 1138166333441L;

            public FlagQueryMessage() {
            }
        }

        public final class ResetStorageMessage {
            public static final long ALL = 1133871366145L;
            public static final long CONTAINER = 1138166333442L;

            public ResetStorageMessage() {
            }
        }

        public final class ListStorageMessage {
            public static final long ALL = 1133871366145L;
            public static final long CONTAINER = 1138166333442L;
            public static final long PACKAGE_NAME = 1138166333443L;

            public ListStorageMessage() {
            }
        }
    }

    public final class StorageRequestMessages {
        public static final long MSGS = 2246267895809L;

        public StorageRequestMessages() {
        }
    }

    public final class StorageReturnMessage {
        public static final long ERROR_MESSAGE = 1138166333448L;
        public static final long FLAG_OVERRIDE_MESSAGE = 1146756268034L;
        public static final long FLAG_QUERY_MESSAGE = 1146756268036L;
        public static final long LIST_STORAGE_MESSAGE = 1146756268039L;
        public static final long NEW_STORAGE_MESSAGE = 1146756268033L;
        public static final long OTA_STAGING_MESSAGE = 1146756268035L;
        public static final long REMOVE_LOCAL_OVERRIDE_MESSAGE = 1146756268037L;
        public static final long RESET_STORAGE_MESSAGE = 1146756268038L;

        public StorageReturnMessage() {
        }

        public final class NewStorageReturnMessage {
            public static final long STORAGE_UPDATED = 1133871366145L;

            public NewStorageReturnMessage() {
            }
        }

        public final class FlagOverrideReturnMessage {
            public FlagOverrideReturnMessage() {
            }
        }

        public final class OTAFlagStagingReturnMessage {
            public OTAFlagStagingReturnMessage() {
            }
        }

        public final class FlagQueryReturnMessage {
            public static final long BOOT_FLAG_VALUE = 1138166333445L;
            public static final long DEFAULT_FLAG_VALUE = 1138166333446L;
            public static final long FLAG_NAME = 1138166333442L;
            public static final long HAS_LOCAL_OVERRIDE = 1133871366153L;
            public static final long HAS_SERVER_OVERRIDE = 1133871366151L;
            public static final long IS_READWRITE = 1133871366152L;
            public static final long LOCAL_FLAG_VALUE = 1138166333444L;
            public static final long PACKAGE_NAME = 1138166333441L;
            public static final long SERVER_FLAG_VALUE = 1138166333443L;

            public FlagQueryReturnMessage() {
            }
        }

        public final class RemoveLocalOverrideReturnMessage {
            public RemoveLocalOverrideReturnMessage() {
            }
        }

        public final class ResetStorageReturnMessage {
            public ResetStorageReturnMessage() {
            }
        }

        public final class ListStorageReturnMessage {
            public static final long FLAGS = 2246267895809L;

            public ListStorageReturnMessage() {
            }
        }
    }

    public final class StorageReturnMessages {
        public static final long MSGS = 2246267895809L;

        public StorageReturnMessages() {
        }
    }
}
