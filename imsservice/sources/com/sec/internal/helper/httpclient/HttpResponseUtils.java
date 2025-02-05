package com.sec.internal.helper.httpclient;

import com.sec.internal.log.IMSLog;
import com.stanfy.gsonxml.GsonXmlBuilder;
import com.stanfy.gsonxml.XmlParserCreator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes.dex */
public class HttpResponseUtils {
    public static <T> T parseXmlResponse(HttpResponseParams httpResponseParams, Class<T> cls, boolean z) {
        String dataString = httpResponseParams.getDataString();
        if (dataString == null) {
            return null;
        }
        try {
            return (T) new GsonXmlBuilder().setXmlParserCreator(new XmlParserCreator() { // from class: com.sec.internal.helper.httpclient.HttpResponseUtils.1
                public XmlPullParser createParser() {
                    try {
                        return XmlPullParserFactory.newInstance().newPullParser();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }).setTreatNamespaces(z).create().fromXml(dataString, cls);
        } catch (Exception e) {
            IMSLog.e("parseXmlResponse()", "cannot parse result");
            e.printStackTrace();
            return null;
        }
    }
}
