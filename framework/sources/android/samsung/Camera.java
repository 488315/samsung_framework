package android.samsung;

/* loaded from: classes3.dex */
public final class Camera {
    public static final int CONTROL_DS_MODE_AEB_HDR_MERGE = 200;
    public static final int CONTROL_DS_MODE_AEB_HYBRID_HDR_MERGE = 201;
    public static final int CONTROL_DS_MODE_AIF_MERGE = 110;
    public static final int CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_OIS_ANCHOR_6 = 271;
    public static final int CONTROL_DS_MODE_AI_CLEAR_ZOOM_MERGE_ZSL_ANCHOR_6 = 270;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_HDR = 183;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_LLS = 185;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_MAX_RESOLUTION_HDR = 181;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_MAX_RESOLUTION_LLS = 184;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_MAX_RESOLUTION_SINGLE = 180;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_MAX_RESOLUTION_SINGLE_FLASH = 186;
    public static final int CONTROL_DS_MODE_AI_HIGHRES_SINGLE = 182;
    public static final int CONTROL_DS_MODE_ASTRO = 220;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_HDR = 41;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_HDR_QM_LT = 48;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_LLHDR = 42;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_LLS = 40;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_NIGHT = 45;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_NIGHT_REMOSAIC = 280;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_QM_LT = 47;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_SINGLE = 46;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_SR = 43;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_SR_HDR = 44;
    public static final int CONTROL_DS_MODE_DUAL_BOKEH_SUB_NIGHT = 49;
    public static final int CONTROL_DS_MODE_EXECUTOR_NIGHT = 1000;
    public static final int CONTROL_DS_MODE_EXECUTOR_TETRA_SR = 1001;
    public static final int CONTROL_DS_MODE_EXPERT_RAW_MFRP_MERGE = 170;
    public static final int CONTROL_DS_MODE_FUSION_HIGHRES_LLHDR_MERGE = 211;
    public static final int CONTROL_DS_MODE_FUSION_HIGHRES_MERGE = 210;
    public static final int CONTROL_DS_MODE_HIFI_MERGE_DEBLUR = 11;
    public static final int CONTROL_DS_MODE_HIFI_MERGE_FLASH = 15;
    public static final int CONTROL_DS_MODE_HIFI_MERGE_OIS = 12;
    public static final int CONTROL_DS_MODE_HIFI_MERGE_SR = 13;
    public static final int CONTROL_DS_MODE_HIFI_MERGE_ZSL = 14;
    public static final int CONTROL_DS_MODE_HIFI_PICK = 10;
    public static final int CONTROL_DS_MODE_HIGHRES_LLHDR_MERGE = 91;
    public static final int CONTROL_DS_MODE_HIGHRES_MERGE = 90;
    public static final int CONTROL_DS_MODE_HYBRID_LLHDR_MERGE_LOIS = 196;
    public static final int CONTROL_DS_MODE_HYBRID_LLHDR_MERGE_LZ = 195;
    public static final int CONTROL_DS_MODE_HYBRID_LLHDR_MERGE_QOIS = 198;
    public static final int CONTROL_DS_MODE_HYBRID_LLHDR_MERGE_QZ = 197;
    public static final int CONTROL_DS_MODE_HYBRID_MFHDR_MERGE_LOIS = 191;
    public static final int CONTROL_DS_MODE_HYBRID_MFHDR_MERGE_LZ = 190;
    public static final int CONTROL_DS_MODE_HYBRID_MFHDR_MERGE_QOIS = 193;
    public static final int CONTROL_DS_MODE_HYBRID_MFHDR_MERGE_QZ = 192;
    public static final int CONTROL_DS_MODE_HYBRID_NNHDR_MERGE_LOIS = 242;
    public static final int CONTROL_DS_MODE_HYBRID_NNHDR_MERGE_LZ = 240;
    public static final int CONTROL_DS_MODE_HYBRID_NNHDR_MERGE_QOIS = 243;
    public static final int CONTROL_DS_MODE_HYBRID_NNHDR_MERGE_QZ = 241;
    public static final int CONTROL_DS_MODE_LITE_LLHDR_MERGE = 101;
    public static final int CONTROL_DS_MODE_LITE_MERGE = 100;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_LDEBLURVEHDR_QZM = 39;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_LDEBLURVENR = 60;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_LZT = 30;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QOISM3_LOIST = 31;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QOISM4 = 32;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QOISM5_LCREMOSAIC = 33;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QOISM6 = 34;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QOISM7 = 35;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QZM5 = 37;
    public static final int CONTROL_DS_MODE_LLHDR_MERGE_QZM7 = 38;
    public static final int CONTROL_DS_MODE_MACRO_RAW_SR_MERGE = 250;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_LZT = 20;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QOISM3_LOIST = 22;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QOISM4_LCREMOSAIC = 23;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QOISM5 = 24;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QOISM6 = 25;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QOISM7 = 26;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QZM5 = 28;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_QZM7 = 29;
    public static final int CONTROL_DS_MODE_MFHDR_MERGE_REMOSAIC = 21;
    public static final int CONTROL_DS_MODE_MF_SR_MERGE = 70;
    public static final int CONTROL_DS_MODE_MF_SR_MERGE_HDR = 71;
    public static final int CONTROL_DS_MODE_MF_SR_MERGE_REMOSAIC = 75;
    public static final int CONTROL_DS_MODE_MF_UW_SR_MERGE = 130;
    public static final int CONTROL_DS_MODE_PRO_RGB_CONVERSION = 230;
    public static final int CONTROL_DS_MODE_RAW_SR_MERGE = 160;
    public static final int CONTROL_DS_MODE_RAW_SR_MERGE_BRIGHT_SKY_MOON_HYBRID = 161;
    public static final int CONTROL_DS_MODE_RAW_SR_MERGE_HYBRID = 162;
    public static final int CONTROL_DS_MODE_SIE_LLHDR_MERGE = 121;
    public static final int CONTROL_DS_MODE_SIE_MERGE = 120;
    public static final int CONTROL_DS_MODE_SIE_MFHDR_MERGE = 122;
    public static final int CONTROL_DS_MODE_SINGLE = 0;
    public static final int CONTROL_DS_MODE_SSHDR_MERGE = 150;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_EXTREME_DARK = 83;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_EXTREME_DARK_MAX = 86;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HANDHELD = 80;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HANDHELD_MAX = 84;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HANDHELD_MAX_REMOSAIC = 88;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HANDHELD_REMOSAIC = 87;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HIGHRES_EXTREME_DARK = 261;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HIGHRES_HANDHELD = 260;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_HIGHRES_HANDHELD_REMOSAIC = 262;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_TRIPOD = 81;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_TRIPOD_LE = 82;
    public static final int CONTROL_DS_MODE_SUPER_NIGHT_TRIPOD_MAX = 85;
    public static final int CONTROL_DS_MODE_SW_ND_FILTER = 221;
    public static final int CONTROL_DS_MODE_TETRA_SR_MERGE = 290;
    public static final int CONTROL_DS_MODE_UDC_RAW_AI_HIGHRES_MAX_RESOLUTION_HDR_MERGE = 145;
    public static final int CONTROL_DS_MODE_UDC_RAW_AI_HIGHRES_MAX_RESOLUTION_MERGE = 144;
    public static final int CONTROL_DS_MODE_UDC_RAW_HDR_MERGE = 141;
    public static final int CONTROL_DS_MODE_UDC_RAW_MERGE = 140;
    public static final int CONTROL_DS_MODE_UDC_SAQE = 142;
    public static final int CONTROL_DS_MODE_UDC_SAQE_QMLT = 143;
}
