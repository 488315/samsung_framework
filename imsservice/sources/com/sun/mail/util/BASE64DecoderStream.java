package com.sun.mail.util;

import com.sec.internal.constants.ims.config.ConfigConstants;
import com.sec.internal.ims.core.cmc.CmcConstants;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;

/* loaded from: classes.dex */
public class BASE64DecoderStream extends FilterInputStream {
    private static final char[] pem_array = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '+', '/'};
    private static final byte[] pem_convert_array = new byte[256];
    private byte[] buffer;
    private int bufsize;
    private boolean ignoreErrors;
    private int index;
    private byte[] input_buffer;
    private int input_len;
    private int input_pos;

    @Override // java.io.FilterInputStream, java.io.InputStream
    public boolean markSupported() {
        return false;
    }

    public BASE64DecoderStream(InputStream inputStream) {
        super(inputStream);
        this.buffer = new byte[3];
        boolean z = false;
        this.bufsize = 0;
        this.index = 0;
        this.input_buffer = new byte[8190];
        this.input_pos = 0;
        this.input_len = 0;
        this.ignoreErrors = false;
        try {
            String property = System.getProperty("mail.mime.base64.ignoreerrors");
            if (property != null && !property.equalsIgnoreCase(ConfigConstants.VALUE.INFO_COMPLETED)) {
                z = true;
            }
            this.ignoreErrors = z;
        } catch (SecurityException unused) {
        }
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() throws IOException {
        if (this.index >= this.bufsize) {
            byte[] bArr = this.buffer;
            int decode = decode(bArr, 0, bArr.length);
            this.bufsize = decode;
            if (decode <= 0) {
                return -1;
            }
            this.index = 0;
        }
        byte[] bArr2 = this.buffer;
        int i = this.index;
        this.index = i + 1;
        return bArr2[i] & 255;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i, int i2) throws IOException {
        int i3;
        int i4;
        int i5 = i;
        while (true) {
            i3 = this.index;
            i4 = this.bufsize;
            if (i3 >= i4 || i2 <= 0) {
                break;
            }
            byte[] bArr2 = this.buffer;
            this.index = i3 + 1;
            bArr[i5] = bArr2[i3];
            i2--;
            i5++;
        }
        if (i3 >= i4) {
            this.index = 0;
            this.bufsize = 0;
        }
        int i6 = (i2 / 3) * 3;
        if (i6 > 0) {
            int decode = decode(bArr, i5, i6);
            i5 += decode;
            i2 -= decode;
            if (decode != i6) {
                if (i5 == i) {
                    return -1;
                }
                return i5 - i;
            }
        }
        while (i2 > 0) {
            int read = read();
            if (read == -1) {
                break;
            }
            bArr[i5] = (byte) read;
            i2--;
            i5++;
        }
        if (i5 == i) {
            return -1;
        }
        return i5 - i;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int available() throws IOException {
        return ((((FilterInputStream) this).in.available() * 3) / 4) + (this.bufsize - this.index);
    }

    static {
        int i = 0;
        for (int i2 = 0; i2 < 255; i2++) {
            pem_convert_array[i2] = -1;
        }
        while (true) {
            char[] cArr = pem_array;
            if (i >= cArr.length) {
                return;
            }
            pem_convert_array[cArr[i]] = (byte) i;
            i++;
        }
    }

    private int decode(byte[] bArr, int i, int i2) throws IOException {
        int i3 = i;
        while (i2 >= 3) {
            boolean z = false;
            int i4 = 0;
            int i5 = 0;
            while (i4 < 4) {
                int i6 = getByte();
                if (i6 == -1 || i6 == -2) {
                    if (i6 == -1) {
                        if (i4 == 0) {
                            return i3 - i;
                        }
                        if (!this.ignoreErrors) {
                            throw new IOException("Error in encoded stream: needed 4 valid base64 characters but only got " + i4 + " before EOF" + recentChars());
                        }
                        z = true;
                    } else {
                        if (i4 < 2 && !this.ignoreErrors) {
                            throw new IOException("Error in encoded stream: needed at least 2 valid base64 characters, but only got " + i4 + " before padding character (=)" + recentChars());
                        }
                        if (i4 == 0) {
                            return i3 - i;
                        }
                    }
                    int i7 = i4 - 1;
                    if (i7 == 0) {
                        i7 = 1;
                    }
                    int i8 = i5 << 6;
                    for (int i9 = i4 + 1; i9 < 4; i9++) {
                        if (!z) {
                            int i10 = getByte();
                            if (i10 == -1) {
                                if (!this.ignoreErrors) {
                                    throw new IOException("Error in encoded stream: hit EOF while looking for padding characters (=)" + recentChars());
                                }
                            } else if (i10 != -2 && !this.ignoreErrors) {
                                throw new IOException("Error in encoded stream: found valid base64 character after a padding character (=)" + recentChars());
                            }
                        }
                        i8 <<= 6;
                    }
                    int i11 = i8 >> 8;
                    if (i7 == 2) {
                        bArr[i3 + 1] = (byte) (i11 & 255);
                    }
                    bArr[i3] = (byte) ((i11 >> 8) & 255);
                    return (i3 + i7) - i;
                }
                i4++;
                i5 = (i5 << 6) | i6;
            }
            bArr[i3 + 2] = (byte) (i5 & 255);
            int i12 = i5 >> 8;
            bArr[i3 + 1] = (byte) (i12 & 255);
            bArr[i3] = (byte) ((i12 >> 8) & 255);
            i2 -= 3;
            i3 += 3;
        }
        return i3 - i;
    }

    private int getByte() throws IOException {
        byte b;
        do {
            if (this.input_pos >= this.input_len) {
                try {
                    int read = ((FilterInputStream) this).in.read(this.input_buffer);
                    this.input_len = read;
                    if (read <= 0) {
                        return -1;
                    }
                    this.input_pos = 0;
                } catch (EOFException unused) {
                    return -1;
                }
            }
            byte[] bArr = this.input_buffer;
            int i = this.input_pos;
            this.input_pos = i + 1;
            int i2 = bArr[i] & 255;
            if (i2 == 61) {
                return -2;
            }
            b = pem_convert_array[i2];
        } while (b == -1);
        return b;
    }

    private String recentChars() {
        String str;
        int i = this.input_pos;
        if (i > 10) {
            i = 10;
        }
        if (i <= 0) {
            return "";
        }
        String str2 = ", the " + i + " most recent characters were: \"";
        for (int i2 = this.input_pos - i; i2 < this.input_pos; i2++) {
            char c = (char) (this.input_buffer[i2] & 255);
            if (c == '\t') {
                str = String.valueOf(str2) + "\\t";
            } else if (c == '\n') {
                str = String.valueOf(str2) + "\\n";
            } else if (c == '\r') {
                str = String.valueOf(str2) + "\\r";
            } else if (c >= ' ' && c < 127) {
                str = String.valueOf(str2) + c;
            } else {
                str = String.valueOf(str2) + "\\" + ((int) c);
            }
            str2 = str;
        }
        return String.valueOf(str2) + CmcConstants.E_NUM_STR_QUOTE;
    }
}
