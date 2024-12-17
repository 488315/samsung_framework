package com.android.server.bridge.operations;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import java.io.PrintWriter;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class RCPDumpState {
    public static RCPDumpState mRCPDumpState;
    public Context mContext;

    public final void dumpStateFileOpsTable(int i, PrintWriter printWriter) {
        if (i == 0) {
            Cursor query = this.mContext.getContentResolver().query(Uri.parse("content://com.samsung.knox.securefolder.rcpcomponents.move.provider.knoxcontentmgrdbprovider/"), new String[]{"timestamp", "tag", "message"}, null, null, null);
            if (query != null) {
                if (query.moveToFirst()) {
                    do {
                        printWriter.print(query.getString(0));
                        printWriter.print(" (");
                        printWriter.print(query.getString(1));
                        printWriter.print(")");
                        printWriter.print(" :");
                        printWriter.println(query.getString(2));
                    } while (query.moveToNext());
                }
                query.close();
                return;
            }
            return;
        }
        Cursor query2 = this.mContext.getContentResolver().query(Uri.parse("content://" + Integer.toString(i) + "@com.samsung.android.knox.containercore.rcpcomponents.move.provider.knoxcontentmgrdbprovider/"), null, null, null, null);
        if (query2 != null) {
            if (query2.moveToFirst()) {
                do {
                    printWriter.print(query2.getString(query2.getColumnIndex("timeStamp")));
                    printWriter.print(" (");
                    printWriter.print(query2.getString(query2.getColumnIndex("operation")));
                    printWriter.print(")");
                    printWriter.print(" ret:");
                    printWriter.print(query2.getInt(query2.getColumnIndex("resultCode")));
                    printWriter.print(" srcUri:");
                    printWriter.print(query2.getString(query2.getColumnIndex("srcUri")));
                    printWriter.print(" destUri:");
                    printWriter.print(query2.getString(query2.getColumnIndex("destUri")));
                    printWriter.print(" src:");
                    printWriter.print(query2.getString(query2.getColumnIndex("source")));
                    printWriter.print(" dest:");
                    printWriter.println(query2.getString(query2.getColumnIndex("destination")));
                } while (query2.moveToNext());
            }
            query2.close();
        }
    }
}
