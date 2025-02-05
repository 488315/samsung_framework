package com.sec.internal.constants.ims.entitilement.softphone.responses;

import com.google.gson.annotations.SerializedName;

/* loaded from: classes.dex */
public class ServiceExceptionResponse {

    @SerializedName("RequestError")
    public RequestError mRequestError;

    public static class RequestError {

        @SerializedName("ServiceException")
        public ExceptionResponse mException;

        public String toString() {
            return "RequestError [mException = " + this.mException + "]";
        }
    }

    public String toString() {
        return "ServiceExceptionResponse [mRequestError = " + this.mRequestError + "]";
    }
}
