package com.android.server.devicepolicy;

import android.app.admin.SystemUpdateInfo;
import android.app.admin.SystemUpdatePolicy;
import android.content.ComponentName;
import android.util.ArrayMap;
import android.util.AtomicFile;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.Xml;
import com.android.modules.utils.TypedXmlPullParser;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import libcore.io.IoUtils;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes2.dex */
public class OwnersData {
    public OwnerInfo mDeviceOwner;
    public ArrayMap mDeviceOwnerProtectedPackages;
    public final PolicyPathProvider mPathProvider;
    public LocalDate mSystemUpdateFreezeEnd;
    public LocalDate mSystemUpdateFreezeStart;
    public SystemUpdateInfo mSystemUpdateInfo;
    public SystemUpdatePolicy mSystemUpdatePolicy;
    public int mDeviceOwnerUserId = -10000;
    public final ArrayMap mDeviceOwnerTypes = new ArrayMap();
    public final ArrayMap mProfileOwners = new ArrayMap();
    public boolean mMigratedToPolicyEngine = false;
    public boolean mPoliciesMigratedPostUpdate = false;

    public OwnersData(PolicyPathProvider policyPathProvider) {
        this.mPathProvider = policyPathProvider;
    }

    public void load(int[] iArr) {
        new DeviceOwnerReadWriter().readFromFileLocked();
        for (int i : iArr) {
            new ProfileOwnerReadWriter(i).readFromFileLocked();
        }
        OwnerInfo ownerInfo = (OwnerInfo) this.mProfileOwners.get(Integer.valueOf(this.mDeviceOwnerUserId));
        ComponentName componentName = ownerInfo != null ? ownerInfo.admin : null;
        if (this.mDeviceOwner == null || componentName == null) {
            return;
        }
        Slog.w("DevicePolicyManagerService", String.format("User %d has both DO and PO, which is not supported", Integer.valueOf(this.mDeviceOwnerUserId)));
    }

    public boolean writeDeviceOwner() {
        return new DeviceOwnerReadWriter().writeToFileLocked();
    }

    public boolean writeProfileOwner(int i) {
        return new ProfileOwnerReadWriter(i).writeToFileLocked();
    }

    public void dump(IndentingPrintWriter indentingPrintWriter) {
        boolean z;
        boolean z2 = true;
        if (this.mDeviceOwner != null) {
            indentingPrintWriter.println("Device Owner: ");
            indentingPrintWriter.increaseIndent();
            this.mDeviceOwner.dump(indentingPrintWriter);
            indentingPrintWriter.println("User ID: " + this.mDeviceOwnerUserId);
            indentingPrintWriter.decreaseIndent();
            z = true;
        } else {
            z = false;
        }
        if (this.mSystemUpdatePolicy != null) {
            if (z) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("System Update Policy: " + this.mSystemUpdatePolicy);
            z = true;
        }
        ArrayMap arrayMap = this.mProfileOwners;
        if (arrayMap != null) {
            for (Map.Entry entry : arrayMap.entrySet()) {
                if (z) {
                    indentingPrintWriter.println();
                }
                indentingPrintWriter.println("Profile Owner (User " + entry.getKey() + "): ");
                indentingPrintWriter.increaseIndent();
                ((OwnerInfo) entry.getValue()).dump(indentingPrintWriter);
                indentingPrintWriter.decreaseIndent();
                z = true;
            }
        }
        if (this.mSystemUpdateInfo != null) {
            if (z) {
                indentingPrintWriter.println();
            }
            indentingPrintWriter.println("Pending System Update: " + this.mSystemUpdateInfo);
        } else {
            z2 = z;
        }
        if (this.mSystemUpdateFreezeStart == null && this.mSystemUpdateFreezeEnd == null) {
            return;
        }
        if (z2) {
            indentingPrintWriter.println();
        }
        indentingPrintWriter.println("System update freeze record: " + getSystemUpdateFreezePeriodRecordAsString());
    }

    public String getSystemUpdateFreezePeriodRecordAsString() {
        StringBuilder sb = new StringBuilder();
        sb.append("start: ");
        LocalDate localDate = this.mSystemUpdateFreezeStart;
        if (localDate != null) {
            sb.append(localDate.toString());
        } else {
            sb.append("null");
        }
        sb.append("; end: ");
        LocalDate localDate2 = this.mSystemUpdateFreezeEnd;
        if (localDate2 != null) {
            sb.append(localDate2.toString());
        } else {
            sb.append("null");
        }
        return sb.toString();
    }

    public File getDeviceOwnerFile() {
        return new File(this.mPathProvider.getDataSystemDirectory(), "device_owner_2.xml");
    }

    public File getProfileOwnerFile(int i) {
        return new File(this.mPathProvider.getUserSystemDirectory(i), "profile_owner.xml");
    }

    /* loaded from: classes2.dex */
    public abstract class FileReadWriter {
        public final File mFile;

        public abstract boolean readInner(TypedXmlPullParser typedXmlPullParser, int i, String str);

        public abstract boolean shouldWrite();

        public abstract void writeInner(TypedXmlSerializer typedXmlSerializer);

        public FileReadWriter(File file) {
            this.mFile = file;
        }

        public boolean writeToFileLocked() {
            if (!shouldWrite()) {
                if (this.mFile.exists() && !this.mFile.delete()) {
                    Slog.e("DevicePolicyManagerService", "Failed to remove " + this.mFile.getPath());
                }
                return true;
            }
            AtomicFile atomicFile = new AtomicFile(this.mFile);
            FileOutputStream fileOutputStream = null;
            try {
                FileOutputStream startWrite = atomicFile.startWrite();
                try {
                    TypedXmlSerializer resolveSerializer = Xml.resolveSerializer(startWrite);
                    resolveSerializer.startDocument((String) null, Boolean.TRUE);
                    resolveSerializer.startTag((String) null, "root");
                    writeInner(resolveSerializer);
                    resolveSerializer.endTag((String) null, "root");
                    resolveSerializer.endDocument();
                    resolveSerializer.flush();
                    atomicFile.finishWrite(startWrite);
                    return true;
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = startWrite;
                    Slog.e("DevicePolicyManagerService", "Exception when writing", e);
                    if (fileOutputStream == null) {
                        return false;
                    }
                    atomicFile.failWrite(fileOutputStream);
                    return false;
                }
            } catch (IOException e2) {
                e = e2;
            }
        }

        public void readFromFileLocked() {
            if (!this.mFile.exists()) {
                return;
            }
            FileInputStream fileInputStream = null;
            try {
                try {
                    fileInputStream = new AtomicFile(this.mFile).openRead();
                    TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(fileInputStream);
                    int i = 0;
                    while (true) {
                        int next = resolvePullParser.next();
                        if (next == 1) {
                            break;
                        }
                        if (next == 2) {
                            i++;
                            String name = resolvePullParser.getName();
                            if (i == 1) {
                                if (!"root".equals(name)) {
                                    Slog.e("DevicePolicyManagerService", "Invalid root tag: " + name);
                                    return;
                                }
                            } else if (!readInner(resolvePullParser, i, name)) {
                                return;
                            }
                        } else if (next == 3) {
                            i--;
                        }
                    }
                } catch (IOException | XmlPullParserException e) {
                    Slog.e("DevicePolicyManagerService", "Error parsing owners information file", e);
                }
            } finally {
                IoUtils.closeQuietly(fileInputStream);
            }
        }
    }

    /* loaded from: classes2.dex */
    public class DeviceOwnerReadWriter extends FileReadWriter {
        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public boolean shouldWrite() {
            return true;
        }

        public DeviceOwnerReadWriter() {
            super(OwnersData.this.getDeviceOwnerFile());
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public void writeInner(TypedXmlSerializer typedXmlSerializer) {
            OwnerInfo ownerInfo = OwnersData.this.mDeviceOwner;
            if (ownerInfo != null) {
                ownerInfo.writeToXml(typedXmlSerializer, "device-owner");
                typedXmlSerializer.startTag((String) null, "device-owner-context");
                typedXmlSerializer.attributeInt((String) null, "userId", OwnersData.this.mDeviceOwnerUserId);
                typedXmlSerializer.endTag((String) null, "device-owner-context");
            }
            if (!OwnersData.this.mDeviceOwnerTypes.isEmpty()) {
                for (Map.Entry entry : OwnersData.this.mDeviceOwnerTypes.entrySet()) {
                    typedXmlSerializer.startTag((String) null, "device-owner-type");
                    typedXmlSerializer.attribute((String) null, "package", (String) entry.getKey());
                    typedXmlSerializer.attributeInt((String) null, "value", ((Integer) entry.getValue()).intValue());
                    typedXmlSerializer.endTag((String) null, "device-owner-type");
                }
            }
            if (OwnersData.this.mSystemUpdatePolicy != null) {
                typedXmlSerializer.startTag((String) null, "system-update-policy");
                OwnersData.this.mSystemUpdatePolicy.saveToXml(typedXmlSerializer);
                typedXmlSerializer.endTag((String) null, "system-update-policy");
            }
            SystemUpdateInfo systemUpdateInfo = OwnersData.this.mSystemUpdateInfo;
            if (systemUpdateInfo != null) {
                systemUpdateInfo.writeToXml(typedXmlSerializer, "pending-ota-info");
            }
            OwnersData ownersData = OwnersData.this;
            if (ownersData.mSystemUpdateFreezeStart != null || ownersData.mSystemUpdateFreezeEnd != null) {
                typedXmlSerializer.startTag((String) null, "freeze-record");
                LocalDate localDate = OwnersData.this.mSystemUpdateFreezeStart;
                if (localDate != null) {
                    typedXmlSerializer.attribute((String) null, "start", localDate.toString());
                }
                LocalDate localDate2 = OwnersData.this.mSystemUpdateFreezeEnd;
                if (localDate2 != null) {
                    typedXmlSerializer.attribute((String) null, "end", localDate2.toString());
                }
                typedXmlSerializer.endTag((String) null, "freeze-record");
            }
            typedXmlSerializer.startTag((String) null, "policy-engine-migration");
            typedXmlSerializer.attributeBoolean((String) null, "migratedToPolicyEngine", OwnersData.this.mMigratedToPolicyEngine);
            typedXmlSerializer.attributeBoolean((String) null, "migratedPostUpgrade", OwnersData.this.mPoliciesMigratedPostUpdate);
            typedXmlSerializer.endTag((String) null, "policy-engine-migration");
        }

        /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x0053, code lost:
        
            if (r9.equals("device-owner-context") == false) goto L7;
         */
        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public boolean readInner(com.android.modules.utils.TypedXmlPullParser r7, int r8, java.lang.String r9) {
            /*
                Method dump skipped, instructions count: 410
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.devicepolicy.OwnersData.DeviceOwnerReadWriter.readInner(com.android.modules.utils.TypedXmlPullParser, int, java.lang.String):boolean");
        }
    }

    /* loaded from: classes2.dex */
    public class ProfileOwnerReadWriter extends FileReadWriter {
        public final int mUserId;

        public ProfileOwnerReadWriter(int i) {
            super(OwnersData.this.getProfileOwnerFile(i));
            this.mUserId = i;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public boolean shouldWrite() {
            return OwnersData.this.mProfileOwners.get(Integer.valueOf(this.mUserId)) != null;
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public void writeInner(TypedXmlSerializer typedXmlSerializer) {
            OwnerInfo ownerInfo = (OwnerInfo) OwnersData.this.mProfileOwners.get(Integer.valueOf(this.mUserId));
            if (ownerInfo != null) {
                ownerInfo.writeToXml(typedXmlSerializer, "profile-owner");
            }
        }

        @Override // com.android.server.devicepolicy.OwnersData.FileReadWriter
        public boolean readInner(TypedXmlPullParser typedXmlPullParser, int i, String str) {
            if (i > 2) {
                return true;
            }
            str.hashCode();
            if (str.equals("profile-owner")) {
                OwnersData.this.mProfileOwners.put(Integer.valueOf(this.mUserId), OwnerInfo.readFromXml(typedXmlPullParser));
                return true;
            }
            Slog.e("DevicePolicyManagerService", "Unexpected tag: " + str);
            return false;
        }
    }

    /* loaded from: classes2.dex */
    public class OwnerInfo {
        public final ComponentName admin;
        public boolean isOrganizationOwnedDevice;
        public final String packageName;
        public String remoteBugreportHash;
        public String remoteBugreportUri;

        public OwnerInfo(ComponentName componentName, String str, String str2, boolean z) {
            this.admin = componentName;
            this.packageName = componentName.getPackageName();
            this.remoteBugreportUri = str;
            this.remoteBugreportHash = str2;
            this.isOrganizationOwnedDevice = z;
        }

        public void writeToXml(TypedXmlSerializer typedXmlSerializer, String str) {
            typedXmlSerializer.startTag((String) null, str);
            ComponentName componentName = this.admin;
            if (componentName != null) {
                typedXmlSerializer.attribute((String) null, "component", componentName.flattenToString());
            }
            String str2 = this.remoteBugreportUri;
            if (str2 != null) {
                typedXmlSerializer.attribute((String) null, "remoteBugreportUri", str2);
            }
            String str3 = this.remoteBugreportHash;
            if (str3 != null) {
                typedXmlSerializer.attribute((String) null, "remoteBugreportHash", str3);
            }
            boolean z = this.isOrganizationOwnedDevice;
            if (z) {
                typedXmlSerializer.attributeBoolean((String) null, "isPoOrganizationOwnedDevice", z);
            }
            typedXmlSerializer.endTag((String) null, str);
        }

        public static OwnerInfo readFromXml(TypedXmlPullParser typedXmlPullParser) {
            String attributeValue = typedXmlPullParser.getAttributeValue((String) null, "component");
            String attributeValue2 = typedXmlPullParser.getAttributeValue((String) null, "remoteBugreportUri");
            String attributeValue3 = typedXmlPullParser.getAttributeValue((String) null, "remoteBugreportHash");
            boolean equals = "true".equals(typedXmlPullParser.getAttributeValue((String) null, "isPoOrganizationOwnedDevice")) | "true".equals(typedXmlPullParser.getAttributeValue((String) null, "canAccessDeviceIds"));
            if (attributeValue == null) {
                Slog.e("DevicePolicyManagerService", "Owner component not found");
                return null;
            }
            ComponentName unflattenFromString = ComponentName.unflattenFromString(attributeValue);
            if (unflattenFromString == null) {
                Slog.e("DevicePolicyManagerService", "Owner component not parsable: " + attributeValue);
                return null;
            }
            return new OwnerInfo(unflattenFromString, attributeValue2, attributeValue3, equals);
        }

        public void dump(IndentingPrintWriter indentingPrintWriter) {
            indentingPrintWriter.println("admin=" + this.admin);
            indentingPrintWriter.println("package=" + this.packageName);
            indentingPrintWriter.println("isOrganizationOwnedDevice=" + this.isOrganizationOwnedDevice);
        }
    }
}
