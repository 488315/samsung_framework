package com.sec.internal.omanetapi.common.data;

import java.util.Arrays;

/* loaded from: classes.dex */
public class ServiceException {
    public String messageId;
    public String text;
    public String[] variables;

    public String toString() {
        if (this.variables != null) {
            int i = 0;
            while (i < this.variables.length) {
                String str = this.text;
                StringBuilder sb = new StringBuilder();
                sb.append("%");
                int i2 = i + 1;
                sb.append(i2);
                this.text = str.replaceAll(sb.toString(), this.variables[i]);
                i = i2;
            }
        }
        return "ServiceException{ messageId: " + this.messageId + " text: " + this.text + " variables: " + Arrays.toString(this.variables) + " }";
    }
}
