package com.sec.internal.constants.ims.servicemodules.im;

import com.sec.internal.constants.ims.cmstore.data.MessageContextValues;
import com.sec.internal.constants.ims.os.PhoneConstants;

/* loaded from: classes.dex */
public interface ImConstants {
    public static final String IM_READ_PERMISSION = "com.samsung.rcs.im.READ_PERMISSION";

    public interface AutoAcceptFt {
        public static final int ALLOWED_HOME = 1;
        public static final int ALLOWED_ROAMING = 2;
        public static final int NOT_ALLOWED = 0;
    }

    public interface ChatDirection {
        public static final int INCOMING = 0;
        public static final int IRRELEVANT = 2;
        public static final int OUTGOING = 1;
    }

    public interface ChatState {
        public static final int ACTIVE = 1;
        public static final int CLOSED_BY_USER = 2;
        public static final int CLOSED_INVOLUNTARILY = 3;
        public static final int CLOSED_VOLUNTARILY = 4;
        public static final int INACTIVE = 0;
        public static final int NONE = -1;
    }

    public enum ChatbotMessagingTech {
        UNKNOWN,
        NONE,
        NOT_AVAILABLE,
        SESSION,
        STANDALONE_MESSAGING
    }

    public enum ChatbotMsgTechConfig {
        DISABLED,
        SESSION_ONLY,
        BOTH_SESSION_AND_SLM,
        SLM_ONLY
    }

    public interface ErrorReason {
        public static final int NO_SESSION = 4;
    }

    public enum FtMech {
        MSRP,
        HTTP
    }

    public enum ImSessionStart {
        WHEN_OPENS_CHAT_WINDOW,
        WHEN_STARTS_TYPING,
        WHEN_PRESSES_SEND_BUTTON
    }

    public interface KnoxBlockState {
        public static final int REQUIRED = 1;
        public static final int UNKNOWN = 0;
        public static final int UNREQUIRED = 2;
    }

    public interface MessageCreatorTag {
        public static final String SD = "SD";
    }

    public interface MessageNotificationStatus {
        public static final int CANCELED = 5;
        public static final int DELIVERED = 1;
        public static final int DISPLAYED = 2;
        public static final int INTERWORKING_MMS = 4;
        public static final int INTERWORKING_SMS = 3;
        public static final int NONE = 0;
    }

    public interface MessageStatus {
        public static final int BLOCKED = 6;
        public static final int CANCELLATION = 11;
        public static final int CANCELLATION_UNREAD = 12;
        public static final int FAILED = 4;
        public static final int IRRELEVANT = 8;
        public static final int QUEUED = 7;
        public static final int READ = 1;
        public static final int SENDING = 2;
        public static final int SENT = 3;
        public static final int TO_SEND = 5;
        public static final int UNREAD = 0;
    }

    public interface MessageType {
        public static final int LOCATION = 2;
        public static final int MULTIMEDIA = 0;

        @Deprecated
        public static final int MULTIMEDIA_BURN = 9;
        public static final int MULTIMEDIA_PUBLICACCOUNT = 11;
        public static final int SYSTEM = 3;
        public static final int SYSTEM_LEADER_CHANGED = 8;
        public static final int SYSTEM_LEADER_INFORMED = 13;
        public static final int SYSTEM_USER_JOINED = 6;
        public static final int SYSTEM_USER_KICKOUT = 14;
        public static final int SYSTEM_USER_LEFT = 4;
        public static final int TEXT = 1;

        @Deprecated
        public static final int TEXT_BURN = 10;
        public static final int TEXT_PUBLICACCOUNT = 12;
    }

    public enum MessagingUX {
        SEAMLESS,
        INTEGRATED
    }

    public interface ParticipantStatus {
        public static final int ACCEPTED = 2;
        public static final int DECLINED = 3;
        public static final int FAILED = 7;
        public static final int GONE = 5;
        public static final int INITIAL = 0;
        public static final int INVITED = 1;
        public static final int PENDING = 8;
        public static final int TIMEOUT = 4;
        public static final int TO_INVITE = 6;
    }

    public interface RcsConfigConstants {
        public static final int CLIENT_VENDOR = 2;
        public static final int CLIENT_VERSION = 3;
        public static final int RCS_PROFILE = 1;
        public static final int RCS_VERSION = 0;
    }

    public interface ReferenceType {
        public static final String REACTION = "2";
        public static final String REPLY = "1";
    }

    public interface RequiredAction {
        public static final int DISPLAY_ERROR = 0;
        public static final int DISPLAY_ERROR_CFS = 3;
        public static final int FALLBACK_TO_LEGACY = 1;
        public static final int FALLBACK_TO_LEGACY_CFS = 2;
    }

    public interface ServiceTag {
        public static final String SERVICE_FT = "FT";
        public static final String SERVICE_IM = "IM";
    }

    public enum SlmAuth {
        DISABLED,
        ENABLED,
        RECEIVING_ONLY
    }

    public interface TransferMech {
        public static final int HTTP = 1;
        public static final int MSRP = 0;
    }

    public interface TransferState {
        public static final int ATTACHED = 6;
        public static final int BLOCKED = 8;
        public static final int CANCELED = 4;
        public static final int CANCELED_NEED_TO_NOTIFY = 10;
        public static final int CANCELLING = 7;
        public static final int COMPLETED = 3;
        public static final int CREATED = 0;
        public static final int IN_PROGRESS = 2;
        public static final int PENDING = 1;
        public static final int QUEUED = 5;
        public static final int SENDING = 9;
    }

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public static class ImMsgTech {
        public static final ImMsgTech SIMPLE_IM = new AnonymousClass1("SIMPLE_IM", 0);
        public static final ImMsgTech CPM = new ImMsgTech("CPM", 1);
        private static final /* synthetic */ ImMsgTech[] $VALUES = $values();

        /* renamed from: com.sec.internal.constants.ims.servicemodules.im.ImConstants$ImMsgTech$1, reason: invalid class name */
        enum AnonymousClass1 extends ImMsgTech {
            @Override // java.lang.Enum
            public String toString() {
                return "SIMPLE";
            }

            private AnonymousClass1(String str, int i) {
                super(str, i);
            }
        }

        private static /* synthetic */ ImMsgTech[] $values() {
            return new ImMsgTech[]{SIMPLE_IM, CPM};
        }

        private ImMsgTech(String str, int i) {
        }

        public static ImMsgTech valueOf(String str) {
            return (ImMsgTech) Enum.valueOf(ImMsgTech.class, str);
        }

        public static ImMsgTech[] values() {
            return (ImMsgTech[]) $VALUES.clone();
        }
    }

    public enum Status implements IEnumerationWithId<Status> {
        UNREAD(0),
        READ(1),
        SENDING(2),
        SENT(3),
        FAILED(4),
        TO_SEND(5),
        BLOCKED(6),
        QUEUED(7),
        IRRELEVANT(8),
        CANCELLATION(11),
        CANCELLATION_UNREAD(12);

        private static final ReverseEnumMap<Status> map = new ReverseEnumMap<>(Status.class);
        private final int id;

        Status(int i) {
            this.id = i;
        }

        public static Status fromId(int i) {
            return (Status) map.get(Integer.valueOf(i));
        }

        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public int getId() {
            return this.id;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public Status getFromId(int i) {
            return (Status) map.get(Integer.valueOf(i));
        }
    }

    public enum RevocationStatus implements IEnumerationWithId<RevocationStatus> {
        NONE(0),
        AVAILABLE(1),
        PENDING(2),
        SENDING(3),
        SENT(4),
        SUCCESS(5),
        FAILED(6);

        private static final ReverseEnumMap<RevocationStatus> map = new ReverseEnumMap<>(RevocationStatus.class);
        private final int id;

        RevocationStatus(int i) {
            this.id = i;
        }

        public static RevocationStatus fromId(int i) {
            RevocationStatus revocationStatus = NONE;
            try {
                return (RevocationStatus) map.get(Integer.valueOf(i));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return revocationStatus;
            }
        }

        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public int getId() {
            return this.id;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public RevocationStatus getFromId(int i) {
            return fromId(i);
        }
    }

    public enum MessagingTech implements IEnumerationWithId<MessagingTech> {
        NORMAL(0),
        SLM_PAGER_MODE(1),
        SLM_LARGE_MODE(2);

        private static final ReverseEnumMap<MessagingTech> map = new ReverseEnumMap<>(MessagingTech.class);
        private final int id;

        MessagingTech(int i) {
            this.id = i;
        }

        public static MessagingTech fromId(int i) {
            MessagingTech messagingTech = NORMAL;
            try {
                return (MessagingTech) map.get(Integer.valueOf(i));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                return messagingTech;
            }
        }

        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public int getId() {
            return this.id;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public MessagingTech getFromId(int i) {
            return fromId(i);
        }
    }

    public enum Type implements IEnumerationWithId<Type> {
        MULTIMEDIA(0),
        TEXT(1),
        LOCATION(2),
        SYSTEM(3),
        SYSTEM_USER_LEFT(4),
        SYSTEM_USER_KICKOUT(14),
        SYSTEM_USER_JOINED(6),
        SYSTEM_LEADER_CHANGED(8),
        MULTIMEDIA_PUBLICACCOUNT(11),
        TEXT_PUBLICACCOUNT(12),
        SYSTEM_LEADER_INFORMED(13);

        private static final ReverseEnumMap<Type> map = new ReverseEnumMap<>(Type.class);
        private final int id;

        Type(int i) {
            this.id = i;
        }

        public static Type fromId(int i) {
            return (Type) map.get(Integer.valueOf(i));
        }

        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public int getId() {
            return this.id;
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // com.sec.internal.constants.ims.servicemodules.im.IEnumerationWithId
        public Type getFromId(int i) {
            return (Type) map.get(Integer.valueOf(i));
        }
    }

    public enum ChatbotTrafficType {
        NONE(MessageContextValues.none),
        ADVERTISEMENT("advertisement"),
        PAYMENT("payment"),
        PREMIUM("premium"),
        SUBSCRIPTION(PhoneConstants.SUBSCRIPTION_KEY),
        UNKNOWN("unknown");

        private String trafficType;

        ChatbotTrafficType(String str) {
            this.trafficType = str;
        }

        public static ChatbotTrafficType fromString(String str) {
            if (str == null) {
                return NONE;
            }
            for (ChatbotTrafficType chatbotTrafficType : values()) {
                if (chatbotTrafficType.trafficType.equalsIgnoreCase(str)) {
                    return chatbotTrafficType;
                }
            }
            return UNKNOWN;
        }
    }
}
