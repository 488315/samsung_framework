package com.sec.internal.omanetapi.common.data;

import java.net.URL;

/* loaded from: classes.dex */
public class Link {
    public URL href;
    public String rel;

    public String toString() {
        return "Link{ rel: " + this.rel + " href: " + this.href + " }";
    }
}
