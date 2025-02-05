package com.sec.internal.constants.ims;

import android.util.Log;
import com.sec.internal.constants.ims.XmlElement;
import com.sec.internal.ims.core.cmc.CmcConstants;
import java.util.Iterator;

/* loaded from: classes.dex */
public final class XmlCreator {
    private static final String LOG_TAG = "XmlCreator";

    private XmlCreator() {
    }

    public static String toXml(XmlElement xmlElement) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + getElementDfs(xmlElement);
    }

    public static String toXml(XmlElement xmlElement, String str, String str2) {
        return ("<?xml version=\"" + str + "\" encoding=\"" + str2 + "\"?>") + getElementDfs(xmlElement);
    }

    public static String toXcapXml(XmlElement xmlElement) {
        return "" + getElementDfs(xmlElement);
    }

    public static String getElementDfs(XmlElement xmlElement) {
        StringBuilder sb = new StringBuilder();
        String str = xmlElement.mNamespace;
        String str2 = xmlElement.mName;
        if (str2 != null) {
            if (str != null) {
                str2 = str + ":" + xmlElement.mName;
            }
            sb.append("<");
            sb.append(str2);
            for (XmlElement.Attribute attribute : xmlElement.mAttributes) {
                sb.append(" ");
                String str3 = attribute.mNamespace;
                if (str3 != null) {
                    sb.append(str3);
                    sb.append(":");
                }
                sb.append(attribute.mName);
                sb.append("=\"");
                sb.append(attribute.mValue);
                sb.append(CmcConstants.E_NUM_STR_QUOTE);
            }
            if (xmlElement.mValue == null && xmlElement.mChildElements.size() == 0) {
                sb.append("/>");
                return sb.toString();
            }
            sb.append(">");
            String str4 = xmlElement.mValue;
            if (str4 != null) {
                sb.append(str4);
            }
            if (xmlElement.mChildElements.size() != 0) {
                Iterator<XmlElement> it = xmlElement.mChildElements.iterator();
                while (it.hasNext()) {
                    sb.append(getElementDfs(it.next()));
                }
            }
            sb.append("</");
            sb.append(str2);
            sb.append(">");
            return sb.toString();
        }
        Log.e(LOG_TAG, "getElementDfs: element name is required ");
        return sb.toString();
    }
}
