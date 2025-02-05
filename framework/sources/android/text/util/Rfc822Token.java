package android.text.util;

/* loaded from: classes4.dex */
public class Rfc822Token {
    private String mAddress;
    private String mComment;
    private String mName;

    public Rfc822Token(String name, String address, String comment) {
        this.mName = name;
        this.mAddress = address;
        this.mComment = comment;
    }

    public String getName() {
        return this.mName;
    }

    public String getAddress() {
        return this.mAddress;
    }

    public String getComment() {
        return this.mComment;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public void setAddress(String address) {
        this.mAddress = address;
    }

    public void setComment(String comment) {
        this.mComment = comment;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.mName != null && this.mName.length() != 0) {
            sb.append(quoteNameIfNecessary(this.mName));
            sb.append(' ');
        }
        if (this.mComment != null && this.mComment.length() != 0) {
            sb.append('(');
            sb.append(quoteComment(this.mComment));
            sb.append(") ");
        }
        if (this.mAddress != null && this.mAddress.length() != 0) {
            sb.append('<');
            sb.append(this.mAddress);
            sb.append('>');
        }
        return sb.toString();
    }

    public static String quoteNameIfNecessary(String name) {
        int len = name.length();
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if ((c < 'A' || c > 'Z') && ((c < 'a' || c > 'z') && c != ' ' && (c < '0' || c > '9'))) {
                return '\"' + quoteName(name) + '\"';
            }
        }
        return name;
    }

    public static String quoteName(String name) {
        StringBuilder sb = new StringBuilder();
        int len = name.length();
        for (int i = 0; i < len; i++) {
            char c = name.charAt(i);
            if (c == '\\' || c == '\"') {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public static String quoteComment(String comment) {
        int len = comment.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            char c = comment.charAt(i);
            if (c == '(' || c == ')' || c == '\\') {
                sb.append('\\');
            }
            sb.append(c);
        }
        return sb.toString();
    }

    public int hashCode() {
        int result = this.mName != null ? (17 * 31) + this.mName.hashCode() : 17;
        if (this.mAddress != null) {
            result = (result * 31) + this.mAddress.hashCode();
        }
        return this.mComment != null ? (result * 31) + this.mComment.hashCode() : result;
    }

    private static boolean stringEquals(String a, String b) {
        if (a == null) {
            return b == null;
        }
        return a.equals(b);
    }

    public boolean equals(Object o) {
        if (!(o instanceof Rfc822Token)) {
            return false;
        }
        Rfc822Token other = (Rfc822Token) o;
        return stringEquals(this.mName, other.mName) && stringEquals(this.mAddress, other.mAddress) && stringEquals(this.mComment, other.mComment);
    }
}
