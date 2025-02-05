package android.database.sqlite;

import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Pair;
import java.util.ArrayList;
import java.util.Locale;
import java.util.function.BinaryOperator;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public final class SQLiteDatabaseConfiguration {
    private static final long DEFAULT_BUSY_TIMEOUT = 2500;
    private static final Pattern EMAIL_IN_DB_PATTERN = Pattern.compile("[\\w\\.\\-]+@[\\w\\.\\-]+");
    public static final String MEMORY_DB_PATH = ":memory:";
    public boolean automaticIndexEnabled;
    public long busyTimeout;
    public int cacheSize;
    public boolean caseSensitiveLikeEnabled;
    public boolean foreignKeyConstraintsEnabled;
    public String journalMode;
    public final String label;
    public Locale locale;
    public int maxSqlCacheSize;
    public int openFlags;
    public final String path;
    public final SQLiteDatabaseSharedConfiguration sharedConfig;
    public boolean shouldTruncateWalFile;
    public String syncMode;
    public final ArrayMap<String, UnaryOperator<String>> customScalarFunctions = new ArrayMap<>();
    public final ArrayMap<String, BinaryOperator<String>> customAggregateFunctions = new ArrayMap<>();
    public final ArrayList<Pair<String, Object[]>> perConnectionSql = new ArrayList<>();
    public int lookasideSlotSize = -1;
    public int lookasideSlotCount = -1;
    public long idleConnectionTimeoutMs = Long.MAX_VALUE;
    public long idleConnectionShrinkTimeoutMs = Long.MAX_VALUE;

    public SQLiteDatabaseConfiguration(String path, int openFlags) {
        if (path == null) {
            throw new IllegalArgumentException("path must not be null.");
        }
        this.path = path;
        this.label = stripPathForLogs(path);
        this.openFlags = openFlags;
        this.maxSqlCacheSize = 25;
        this.locale = Locale.getDefault();
        this.automaticIndexEnabled = true;
        this.caseSensitiveLikeEnabled = false;
        this.busyTimeout = DEFAULT_BUSY_TIMEOUT;
        this.sharedConfig = new SQLiteDatabaseSharedConfiguration(this);
        if (this.sharedConfig.useWalModeByDefault) {
            this.openFlags |= 536870912;
        }
        if (isInMemoryDb()) {
            this.openFlags &= -4097;
        }
    }

    public SQLiteDatabaseConfiguration(SQLiteDatabaseConfiguration other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null.");
        }
        this.path = other.path;
        this.label = other.label;
        this.sharedConfig = other.sharedConfig;
        updateParametersFrom(other);
    }

    public void updateParametersFrom(SQLiteDatabaseConfiguration other) {
        if (other == null) {
            throw new IllegalArgumentException("other must not be null.");
        }
        if (!this.path.equals(other.path)) {
            throw new IllegalArgumentException("other configuration must refer to the same database.");
        }
        this.openFlags = other.openFlags;
        this.maxSqlCacheSize = other.maxSqlCacheSize;
        this.locale = other.locale;
        this.foreignKeyConstraintsEnabled = other.foreignKeyConstraintsEnabled;
        this.customScalarFunctions.clear();
        this.customScalarFunctions.putAll((ArrayMap<? extends String, ? extends UnaryOperator<String>>) other.customScalarFunctions);
        this.customAggregateFunctions.clear();
        this.customAggregateFunctions.putAll((ArrayMap<? extends String, ? extends BinaryOperator<String>>) other.customAggregateFunctions);
        this.perConnectionSql.clear();
        this.perConnectionSql.addAll(other.perConnectionSql);
        this.lookasideSlotSize = other.lookasideSlotSize;
        this.lookasideSlotCount = other.lookasideSlotCount;
        this.idleConnectionTimeoutMs = other.idleConnectionTimeoutMs;
        this.idleConnectionShrinkTimeoutMs = other.idleConnectionShrinkTimeoutMs;
        this.automaticIndexEnabled = other.automaticIndexEnabled;
        this.caseSensitiveLikeEnabled = other.caseSensitiveLikeEnabled;
        this.busyTimeout = other.busyTimeout;
        this.cacheSize = other.cacheSize;
        this.journalMode = other.journalMode;
        this.syncMode = other.syncMode;
    }

    public boolean isInMemoryDb() {
        return this.path.equalsIgnoreCase(MEMORY_DB_PATH);
    }

    public boolean isReadOnlyDatabase() {
        return (this.openFlags & 1) != 0;
    }

    boolean isLegacyCompatibilityWalEnabled() {
        return this.journalMode == null && this.syncMode == null && (this.openFlags & Integer.MIN_VALUE) != 0;
    }

    private static String stripPathForLogs(String path) {
        if (path.indexOf(64) == -1) {
            return path;
        }
        return EMAIL_IN_DB_PATTERN.matcher(path).replaceAll("XX@YY");
    }

    boolean isLookasideConfigSet() {
        return this.lookasideSlotCount >= 0 && this.lookasideSlotSize >= 0;
    }

    public String resolveJournalMode() {
        if (isReadOnlyDatabase()) {
            return "";
        }
        if (isInMemoryDb()) {
            if (this.journalMode != null && this.journalMode.equalsIgnoreCase("OFF")) {
                return "OFF";
            }
            return SQLiteDatabase.JOURNAL_MODE_MEMORY;
        }
        this.shouldTruncateWalFile = false;
        if (!isWalEnabledInternal()) {
            return this.journalMode != null ? this.journalMode : SQLiteGlobal.getDefaultJournalMode();
        }
        this.shouldTruncateWalFile = true;
        return SQLiteDatabase.JOURNAL_MODE_WAL;
    }

    public String resolveSyncMode() {
        if (isReadOnlyDatabase() || isInMemoryDb()) {
            return "";
        }
        if (!TextUtils.isEmpty(this.syncMode)) {
            return this.syncMode;
        }
        if (isWalEnabledInternal()) {
            if (this.sharedConfig.useWalModeByDefault) {
                return SQLiteDatabase.SYNC_MODE_NORMAL;
            }
            if (this.sharedConfig.isSecureDb) {
                return "FULL";
            }
            if (isLegacyCompatibilityWalEnabled()) {
                return SQLiteCompatibilityWalFlags.getWALSyncMode();
            }
            return SQLiteGlobal.getWALSyncMode();
        }
        return this.sharedConfig.getDefaultSyncMode();
    }

    private boolean isWalEnabledInternal() {
        boolean walEnabled = (this.openFlags & 536870912) != 0;
        boolean isCompatibilityWalEnabled = isLegacyCompatibilityWalEnabled();
        if (walEnabled || isCompatibilityWalEnabled) {
            return true;
        }
        return this.journalMode != null && this.journalMode.equalsIgnoreCase(SQLiteDatabase.JOURNAL_MODE_WAL);
    }

    public boolean isQueryCollectDb() {
        return this.sharedConfig.isQueryCollectDb(this.path);
    }
}
