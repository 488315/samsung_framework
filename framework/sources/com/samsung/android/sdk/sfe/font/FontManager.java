package com.samsung.android.sdk.sfe.font;

import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.FontListParser;
import android.media.MediaMetrics;
import android.net.Uri;
import android.os.SystemProperties;
import android.sec.enterprise.content.SecContentProviderURI;
import android.text.FontConfig;
import android.text.TextUtils;
import android.util.Log;
import com.samsung.android.feature.SemCscFeature;
import com.samsung.android.sdk.sfe.SFEffect;
import com.samsung.android.sdk.sfe.util.SFError;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/* loaded from: classes5.dex */
public class FontManager {
    private static final String DROIDSANS = "DroidSans.ttf";
    private static final String DROIDSANS_BOLD = "DroidSans-Bold.ttf";
    private static final String FONTS_XML = "system/etc/fonts.xml";
    private static final String FONT_DIRECTORY = "fonts/";
    private static final String FONT_PACKAGE = "com.monotype.android.font.";
    private static final String OVERRIDE_TB = "ThomBrowne";
    private static final String OWNER_SANS_LOC_PATH = "/data/app_fonts/0/sans.loc";
    private static final String SANS_LOC_POST = "/sans.loc";
    private static final String SANS_LOC_PRE = "/data/app_fonts/";
    private static final String SYSTEM_FONT_DIRECTORY = "/system/fonts/";
    private static final String TAG = "SFFontManager";
    private static String mFlipFontPath;
    private static final boolean DEBUG = SFEffect.DEBUG;
    private static String sOverrideFont = SemCscFeature.getInstance().getString("CscFeature_SetupWizard_ConfigStepSequenceType");
    private static FontConfig mParser = null;
    private static boolean mSetFontConfigFinished = false;
    private static long mLastSystemFontChangedTime = 0;
    private static final Object mMutex = new Object();

    private static native boolean SFFontManager_InsertFontData(String str, byte[] bArr);

    private static native boolean SFFontManager_SetFontConfig(FontConfig fontConfig);

    public FontManager() {
        synchronized (mMutex) {
            mSetFontConfigFinished = false;
            mParser = getFontConfig();
            boolean z = DEBUG;
            if (z) {
                Log.d(TAG, "setFontConfig start");
            }
            setFontConfig(mParser);
            mSetFontConfigFinished = true;
            if (z) {
                Log.d(TAG, "setFontConfig done");
            }
        }
    }

    public static boolean isSetConfigFinished() {
        return mSetFontConfigFinished;
    }

    private FontConfig getFontConfig() {
        String fontXmlName = "/fonts.xml";
        String sales_code = SystemProperties.get("ro.csc.sales_code");
        if (sales_code.equals("MYM") || sales_code.equals("BKD") || sales_code.equals("BNG") || sales_code.equals("BCK")) {
            fontXmlName = "/fonts_additional.xml";
        }
        String fontXmlPath = "/system/etc" + fontXmlName;
        try {
            FontConfig parser = FontListParser.parse(fontXmlPath, "/system/fonts/", null, null, null, 0L, 0);
            return parser;
        } catch (Exception e) {
            Log.e(TAG, fontXmlPath + " does not exist on this system");
            return null;
        }
    }

    public String getSystemFontName(String fontFamily, boolean isBold, boolean isItalic) {
        synchronized (mMutex) {
            if (mParser == null) {
                if (DEBUG) {
                    Log.d(TAG, "getSystemFontName() - Parser is null");
                }
                return null;
            }
            Log.d(TAG, "getSystemFontName fontFamily = " + fontFamily + ", isItalic = " + isItalic + ", isBold = " + isBold);
            int weight = isBold ? 700 : 400;
            int italic = isItalic ? 1 : 0;
            for (FontConfig.NamedFamilyList namedFamilyList : mParser.getNamedFamilyLists()) {
                if (namedFamilyList.getName() != null) {
                    if (fontFamily.equals(namedFamilyList.getName())) {
                        for (FontConfig.FontFamily fontfamily : namedFamilyList.getFamilies()) {
                            for (FontConfig.Font font : fontfamily.getFontList()) {
                                if (font.getStyle().getWeight() == weight && font.getStyle().getSlant() == italic) {
                                    return font.getFile().getAbsolutePath();
                                }
                            }
                        }
                    }
                } else if (DEBUG) {
                    Log.w(TAG, "getSystemFontName - family.getName() is NULL - Skip.");
                }
            }
            for (FontConfig.Alias alias : mParser.getAliases()) {
                if (alias.getName() != null && alias.getOriginal() != null) {
                    if (alias.getWeight() != 0) {
                        for (FontConfig.NamedFamilyList namedFamilyList2 : mParser.getNamedFamilyLists()) {
                            if (alias.getName().equals(namedFamilyList2.getName())) {
                                for (FontConfig.FontFamily fontfamily2 : namedFamilyList2.getFamilies()) {
                                    for (FontConfig.Font font2 : fontfamily2.getFontList()) {
                                        if (font2.getStyle().getWeight() == alias.getWeight() && font2.getStyle().getSlant() == italic) {
                                            return font2.getFile().getAbsolutePath();
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return null;
        }
    }

    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x005a -> B:19:0x007b). Please report as a decompilation issue!!! */
    public String getFullFlipFont(Context context) {
        if (context == null) {
            return null;
        }
        File mtFontsDir = new File("/data/app_fonts/");
        if (mtFontsDir.isDirectory() && mtFontsDir.list() != null && mtFontsDir.list().length == 0) {
            return "default";
        }
        String systemFont = "empty";
        if ("empty".equals("empty")) {
            File file = new File(OWNER_SANS_LOC_PATH);
            FileInputStream fis = null;
            String string = null;
            try {
                try {
                    try {
                        try {
                            fis = new FileInputStream(file);
                            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
                            string = br.readLine();
                            fis.close();
                            br.close();
                            fis.close();
                        } catch (IOException e) {
                            string = "default";
                            e.printStackTrace();
                            if (fis != null) {
                                fis.close();
                            }
                        }
                    } catch (FileNotFoundException e2) {
                        string = "default";
                        if (fis != null) {
                            fis.close();
                        }
                    }
                } catch (IOException e3) {
                }
                systemFont = string;
            } catch (Throwable th) {
                if (fis != null) {
                    try {
                        fis.close();
                    } catch (IOException e4) {
                    }
                }
                throw th;
            }
        }
        return systemFont == null ? "default" : systemFont;
    }

    public String getFontNameFlipFont(Context context) {
        if (context == null) {
            return null;
        }
        String sx = getFullFlipFont(context);
        String[] parts = sx.split("#");
        if (parts.length < 2) {
            if (parts[0].endsWith("default")) {
                return "default";
            }
            return null;
        }
        return parts[1];
    }

    public String getFontPathFlipFont(Context context) {
        String sx = getFullFlipFont(context);
        String[] parts = sx.split("#");
        return parts[0];
    }

    public String getFlipFontPath(Context context) {
        File file = new File(OWNER_SANS_LOC_PATH);
        if (!file.exists()) {
            if (TextUtils.isEmpty(sOverrideFont) || !sOverrideFont.contains(OVERRIDE_TB)) {
                return null;
            }
            mFlipFontPath = "/system/fonts/ArialNarrow-Regular.ttf";
            return "/system/fonts/ArialNarrow-Regular.ttf";
        }
        long timeSansLocFile = file.lastModified();
        if (timeSansLocFile == mLastSystemFontChangedTime) {
            if (DEBUG) {
                Log.d(TAG, "System font not changed. -> flipFontPath = " + mFlipFontPath);
            }
            return mFlipFontPath;
        }
        String strFontPath = getFontPathFlipFont(context);
        String strPackageName = strFontPath.substring(strFontPath.lastIndexOf("/") + 1);
        Log.d(TAG, "getFlipFontPath - strFontPath = " + strFontPath + ", strPackageName = " + strPackageName);
        if (!strFontPath.endsWith("default")) {
            String strFontPath2 = strFontPath + "/" + DROIDSANS;
            if (DEBUG) {
                Log.d(TAG, "getFlipFontPath - DroidSans path: " + strFontPath2);
            }
            String strPackageName2 = FONT_PACKAGE + strPackageName;
            File fontFile = new File(strFontPath2);
            if (!fontFile.exists()) {
                String strFontName = getFontNameFlipFont(context);
                String flipFontPath = getFlipFontFromPakage(context, strPackageName2, strFontName);
                if (flipFontPath == null) {
                    return null;
                }
                mFlipFontPath = flipFontPath;
                mLastSystemFontChangedTime = timeSansLocFile;
                return flipFontPath;
            }
            String fontName = strPackageName2.toLowerCase() + MediaMetrics.SEPARATOR + getFontNameFlipFont(context) + ".ttf";
            insertFontData(fontName, readFile(fontFile));
            mFlipFontPath = fontName;
            mLastSystemFontChangedTime = timeSansLocFile;
            return fontName;
        }
        if (!TextUtils.isEmpty(sOverrideFont) && sOverrideFont.contains(OVERRIDE_TB)) {
            mFlipFontPath = "/system/fonts/ArialNarrow-Regular.ttf";
            return "/system/fonts/ArialNarrow-Regular.ttf";
        }
        mFlipFontPath = null;
        return null;
    }

    private String getFlipFontFromPakage(Context context, String strPackageName, String strFontName) {
        String pakageName = strPackageName.toLowerCase();
        String assetFontPath = FONT_DIRECTORY + strFontName + ".ttf";
        String fontName = pakageName + MediaMetrics.SEPARATOR + strFontName + ".ttf";
        if (DEBUG) {
            Log.d(TAG, "getFlipFontFromPakage : Application pakage name = " + pakageName + " , font name = " + strFontName);
        }
        try {
            PackageManager mPackageManager = context.getPackageManager();
            ApplicationInfo appInfo = mPackageManager.getApplicationInfo(pakageName, 128);
            appInfo.publicSourceDir = appInfo.sourceDir;
            Resources res = mPackageManager.getResourcesForApplication(appInfo);
            AssetManager assetManager = res.getAssets();
            InputStream in = assetManager.open(assetFontPath);
            byte[] fileBytes = new byte[in.available()];
            in.read(fileBytes);
            in.close();
            insertFontData(fontName, fileBytes);
            return fontName;
        } catch (Exception ex) {
            ex.printStackTrace();
            try {
                ContentResolver cr = context.getContentResolver();
                Uri uriFont = Uri.parse(SecContentProviderURI.CONTENT + pakageName + "/fonts/" + strFontName + ".ttf");
                InputStream isFont = null;
                try {
                    try {
                        isFont = cr.openInputStream(uriFont);
                        byte[] fileBytes2 = new byte[isFont.available()];
                        isFont.read(fileBytes2);
                        insertFontData(fontName, fileBytes2);
                        if (isFont != null) {
                            try {
                                isFont.close();
                            } catch (IOException e) {
                            }
                        }
                        return fontName;
                    } catch (Throwable th) {
                        if (isFont != null) {
                            try {
                                isFont.close();
                            } catch (IOException e2) {
                            }
                        }
                        throw th;
                    }
                } catch (Exception e3) {
                    e3.printStackTrace();
                    if (isFont != null) {
                        try {
                            isFont.close();
                        } catch (IOException e4) {
                        }
                    }
                    return null;
                }
            } catch (Exception e5) {
                return null;
            }
        }
    }

    private byte[] readFile(File file) {
        FileInputStream fis = null;
        try {
            try {
                try {
                    fis = new FileInputStream(file);
                    ByteArrayOutputStream output = new ByteArrayOutputStream();
                    byte[] buffer = new byte[4096];
                    while (true) {
                        int read = fis.read(buffer);
                        if (read == -1) {
                            break;
                        }
                        output.write(buffer, 0, read);
                    }
                    byte[] byteArray = output.toByteArray();
                    try {
                        fis.close();
                    } catch (IOException e) {
                    }
                    return byteArray;
                } catch (FileNotFoundException e2) {
                    Log.d(TAG, "File not found: " + e2.toString());
                    if (fis == null) {
                        return null;
                    }
                    fis.close();
                    return null;
                } catch (IOException e3) {
                    Log.d(TAG, "Exception reading file: " + e3.toString());
                    if (fis == null) {
                        return null;
                    }
                    fis.close();
                    return null;
                }
            } catch (IOException e4) {
                return null;
            }
        } catch (Throwable th) {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e5) {
                }
            }
            throw th;
        }
    }

    private static void insertFontData(String path, byte[] buf) {
        boolean rtn = SFFontManager_InsertFontData(path, buf);
        if (!rtn) {
            throwUncheckedException(SFError.getError());
        }
    }

    private static void setFontConfig(FontConfig fc) {
        boolean rtn = SFFontManager_SetFontConfig(fc);
        if (!rtn) {
            throwUncheckedException(SFError.getError());
        }
    }

    private static void throwUncheckedException(int errno) {
        SFError.ThrowUncheckedException(errno);
    }
}
