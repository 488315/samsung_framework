package android.media;

import android.app.admin.DevicePolicyResources;
import android.bluetooth.hci.BluetoothHciProtoEnums;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.gnss.GnssSignalType;
import android.hardware.scontext.SContextConstants;
import android.hardware.usb.UsbManager;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.opengl.GLES30;
import android.os.FileUtils;
import android.os.ParcelFileDescriptor;
import android.system.ErrnoException;
import android.system.Os;
import android.system.OsConstants;
import android.text.format.Time;
import android.util.Log;
import android.util.Pair;
import com.android.internal.telephony.gsm.SmsCbConstants;
import com.google.android.mms.ContentType;
import com.samsung.android.graphics.spr.document.animator.SprAnimatorBase;
import com.samsung.android.graphics.spr.document.attribute.SprAttributeBase;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInput;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.CRC32;

/* loaded from: classes2.dex */
public class ExifInterface {
    private static final short BYTE_ALIGN_II = 18761;
    private static final short BYTE_ALIGN_MM = 19789;
    private static final int DATA_DEFLATE_ZIP = 8;
    private static final int DATA_HUFFMAN_COMPRESSED = 2;
    private static final int DATA_JPEG = 6;
    private static final int DATA_JPEG_COMPRESSED = 7;
    private static final int DATA_LOSSY_JPEG = 34892;
    private static final int DATA_PACK_BITS_COMPRESSED = 32773;
    private static final int DATA_UNCOMPRESSED = 1;
    private static final ExifTag[] EXIF_POINTER_TAGS;
    private static final ExifTag[][] EXIF_TAGS;
    private static final ExifTag[] IFD_EXIF_TAGS;
    private static final int IFD_FORMAT_BYTE = 1;
    private static final int IFD_FORMAT_DOUBLE = 12;
    private static final int IFD_FORMAT_IFD = 13;
    private static final int IFD_FORMAT_SBYTE = 6;
    private static final int IFD_FORMAT_SINGLE = 11;
    private static final int IFD_FORMAT_SLONG = 9;
    private static final int IFD_FORMAT_SRATIONAL = 10;
    private static final int IFD_FORMAT_SSHORT = 8;
    private static final int IFD_FORMAT_STRING = 2;
    private static final int IFD_FORMAT_ULONG = 4;
    private static final int IFD_FORMAT_UNDEFINED = 7;
    private static final int IFD_FORMAT_URATIONAL = 5;
    private static final int IFD_FORMAT_USHORT = 3;
    private static final ExifTag[] IFD_GPS_TAGS;
    private static final ExifTag[] IFD_INTEROPERABILITY_TAGS;
    private static final int IFD_OFFSET = 8;
    private static final ExifTag[] IFD_THUMBNAIL_TAGS;
    private static final ExifTag[] IFD_TIFF_TAGS;
    private static final int IFD_TYPE_EXIF = 1;
    private static final int IFD_TYPE_GPS = 2;
    private static final int IFD_TYPE_INTEROPERABILITY = 3;
    private static final int IFD_TYPE_ORF_CAMERA_SETTINGS = 7;
    private static final int IFD_TYPE_ORF_IMAGE_PROCESSING = 8;
    private static final int IFD_TYPE_ORF_MAKER_NOTE = 6;
    private static final int IFD_TYPE_PEF = 9;
    private static final int IFD_TYPE_PREVIEW = 5;
    private static final int IFD_TYPE_PRIMARY = 0;
    private static final int IFD_TYPE_THUMBNAIL = 4;
    private static final int IMAGE_TYPE_ARW = 1;
    private static final int IMAGE_TYPE_CR2 = 2;
    private static final int IMAGE_TYPE_DNG = 3;
    private static final int IMAGE_TYPE_HEIF = 12;
    private static final int IMAGE_TYPE_JPEG = 4;
    private static final int IMAGE_TYPE_NEF = 5;
    private static final int IMAGE_TYPE_NRW = 6;
    private static final int IMAGE_TYPE_ORF = 7;
    private static final int IMAGE_TYPE_PEF = 8;
    private static final int IMAGE_TYPE_PNG = 13;
    private static final int IMAGE_TYPE_RAF = 9;
    private static final int IMAGE_TYPE_RW2 = 10;
    private static final int IMAGE_TYPE_SRW = 11;
    private static final int IMAGE_TYPE_UNKNOWN = 0;
    private static final int IMAGE_TYPE_WEBP = 14;
    private static final byte MARKER = -1;
    private static final byte MARKER_APP1 = -31;
    private static final byte MARKER_COM = -2;
    private static final byte MARKER_EOI = -39;
    private static final byte MARKER_SOF0 = -64;
    private static final byte MARKER_SOF1 = -63;
    private static final byte MARKER_SOF10 = -54;
    private static final byte MARKER_SOF11 = -53;
    private static final byte MARKER_SOF13 = -51;
    private static final byte MARKER_SOF14 = -50;
    private static final byte MARKER_SOF15 = -49;
    private static final byte MARKER_SOF2 = -62;
    private static final byte MARKER_SOF3 = -61;
    private static final byte MARKER_SOF5 = -59;
    private static final byte MARKER_SOF6 = -58;
    private static final byte MARKER_SOF7 = -57;
    private static final byte MARKER_SOF9 = -55;
    private static final byte MARKER_SOS = -38;
    private static final int MAX_THUMBNAIL_SIZE = 512;
    private static final ExifTag[] ORF_CAMERA_SETTINGS_TAGS;
    private static final ExifTag[] ORF_IMAGE_PROCESSING_TAGS;
    private static final int ORF_MAKER_NOTE_HEADER_1_SIZE = 8;
    private static final int ORF_MAKER_NOTE_HEADER_2_SIZE = 12;
    private static final ExifTag[] ORF_MAKER_NOTE_TAGS;
    private static final short ORF_SIGNATURE_1 = 20306;
    private static final short ORF_SIGNATURE_2 = 21330;
    public static final int ORIENTATION_FLIP_HORIZONTAL = 2;
    public static final int ORIENTATION_FLIP_VERTICAL = 4;
    public static final int ORIENTATION_NORMAL = 1;
    public static final int ORIENTATION_ROTATE_180 = 3;
    public static final int ORIENTATION_ROTATE_270 = 8;
    public static final int ORIENTATION_ROTATE_90 = 6;
    public static final int ORIENTATION_TRANSPOSE = 5;
    public static final int ORIENTATION_TRANSVERSE = 7;
    public static final int ORIENTATION_UNDEFINED = 0;
    private static final int ORIGINAL_RESOLUTION_IMAGE = 0;
    private static final int PEF_MAKER_NOTE_SKIP_SIZE = 6;
    private static final String PEF_SIGNATURE = "PENTAX";
    private static final ExifTag[] PEF_TAGS;
    private static final int PHOTOMETRIC_INTERPRETATION_BLACK_IS_ZERO = 1;
    private static final int PHOTOMETRIC_INTERPRETATION_RGB = 2;
    private static final int PHOTOMETRIC_INTERPRETATION_WHITE_IS_ZERO = 0;
    private static final int PHOTOMETRIC_INTERPRETATION_YCBCR = 6;
    private static final int PNG_CHUNK_CRC_BYTE_LENGTH = 4;
    private static final int PNG_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int RAF_INFO_SIZE = 160;
    private static final int RAF_JPEG_LENGTH_VALUE_SIZE = 4;
    private static final int RAF_OFFSET_TO_JPEG_IMAGE_OFFSET = 84;
    private static final String RAF_SIGNATURE = "FUJIFILMCCD-RAW";
    private static final int REDUCED_RESOLUTION_IMAGE = 1;
    private static final short RW2_SIGNATURE = 85;
    private static final int SIGNATURE_CHECK_SIZE = 5000;
    private static final byte START_CODE = 42;
    public static final int STREAM_TYPE_EXIF_DATA_ONLY = 1;
    public static final int STREAM_TYPE_FULL_IMAGE_DATA = 0;

    @Deprecated
    public static final String TAG_APERTURE = "FNumber";
    public static final String TAG_APERTURE_VALUE = "ApertureValue";
    public static final String TAG_ARTIST = "Artist";
    public static final String TAG_BITS_PER_SAMPLE = "BitsPerSample";
    public static final String TAG_BRIGHTNESS_VALUE = "BrightnessValue";
    public static final String TAG_CFA_PATTERN = "CFAPattern";
    public static final String TAG_COLOR_SPACE = "ColorSpace";
    public static final String TAG_COMPONENTS_CONFIGURATION = "ComponentsConfiguration";
    public static final String TAG_COMPRESSED_BITS_PER_PIXEL = "CompressedBitsPerPixel";
    public static final String TAG_COMPRESSION = "Compression";
    public static final String TAG_CONTRAST = "Contrast";
    public static final String TAG_COPYRIGHT = "Copyright";
    public static final String TAG_CUSTOM_RENDERED = "CustomRendered";
    public static final String TAG_DATETIME = "DateTime";
    public static final String TAG_DATETIME_DIGITIZED = "DateTimeDigitized";
    public static final String TAG_DATETIME_ORIGINAL = "DateTimeOriginal";
    public static final String TAG_DEFAULT_CROP_SIZE = "DefaultCropSize";
    public static final String TAG_DEVICE_SETTING_DESCRIPTION = "DeviceSettingDescription";
    public static final String TAG_DNG_VERSION = "DNGVersion";
    private static final String TAG_EXIF_IFD_POINTER = "ExifIFDPointer";
    public static final String TAG_EXIF_VERSION = "ExifVersion";
    public static final String TAG_EXPOSURE_BIAS_VALUE = "ExposureBiasValue";
    public static final String TAG_EXPOSURE_INDEX = "ExposureIndex";
    public static final String TAG_EXPOSURE_MODE = "ExposureMode";
    public static final String TAG_EXPOSURE_PROGRAM = "ExposureProgram";
    public static final String TAG_FILE_SOURCE = "FileSource";
    public static final String TAG_FLASH = "Flash";
    public static final String TAG_FLASHPIX_VERSION = "FlashpixVersion";
    public static final String TAG_FLASH_ENERGY = "FlashEnergy";
    public static final String TAG_FOCAL_LENGTH = "FocalLength";
    public static final String TAG_FOCAL_LENGTH_IN_35MM_FILM = "FocalLengthIn35mmFilm";
    public static final String TAG_FOCAL_PLANE_RESOLUTION_UNIT = "FocalPlaneResolutionUnit";
    public static final String TAG_FOCAL_PLANE_X_RESOLUTION = "FocalPlaneXResolution";
    public static final String TAG_FOCAL_PLANE_Y_RESOLUTION = "FocalPlaneYResolution";
    public static final String TAG_F_NUMBER = "FNumber";
    public static final String TAG_GAIN_CONTROL = "GainControl";
    public static final String TAG_GPS_ALTITUDE = "GPSAltitude";
    public static final String TAG_GPS_ALTITUDE_REF = "GPSAltitudeRef";
    public static final String TAG_GPS_AREA_INFORMATION = "GPSAreaInformation";
    public static final String TAG_GPS_DATESTAMP = "GPSDateStamp";
    public static final String TAG_GPS_DEST_BEARING = "GPSDestBearing";
    public static final String TAG_GPS_DEST_BEARING_REF = "GPSDestBearingRef";
    public static final String TAG_GPS_DEST_DISTANCE = "GPSDestDistance";
    public static final String TAG_GPS_DEST_DISTANCE_REF = "GPSDestDistanceRef";
    public static final String TAG_GPS_DEST_LATITUDE = "GPSDestLatitude";
    public static final String TAG_GPS_DEST_LATITUDE_REF = "GPSDestLatitudeRef";
    public static final String TAG_GPS_DEST_LONGITUDE = "GPSDestLongitude";
    public static final String TAG_GPS_DEST_LONGITUDE_REF = "GPSDestLongitudeRef";
    public static final String TAG_GPS_DIFFERENTIAL = "GPSDifferential";
    public static final String TAG_GPS_DOP = "GPSDOP";
    public static final String TAG_GPS_IMG_DIRECTION = "GPSImgDirection";
    public static final String TAG_GPS_IMG_DIRECTION_REF = "GPSImgDirectionRef";
    private static final String TAG_GPS_INFO_IFD_POINTER = "GPSInfoIFDPointer";
    public static final String TAG_GPS_LATITUDE = "GPSLatitude";
    public static final String TAG_GPS_LATITUDE_REF = "GPSLatitudeRef";
    public static final String TAG_GPS_LONGITUDE = "GPSLongitude";
    public static final String TAG_GPS_LONGITUDE_REF = "GPSLongitudeRef";
    public static final String TAG_GPS_MAP_DATUM = "GPSMapDatum";
    public static final String TAG_GPS_MEASURE_MODE = "GPSMeasureMode";
    public static final String TAG_GPS_PROCESSING_METHOD = "GPSProcessingMethod";
    public static final String TAG_GPS_SATELLITES = "GPSSatellites";
    public static final String TAG_GPS_SPEED = "GPSSpeed";
    public static final String TAG_GPS_SPEED_REF = "GPSSpeedRef";
    public static final String TAG_GPS_STATUS = "GPSStatus";
    public static final String TAG_GPS_TRACK = "GPSTrack";
    public static final String TAG_GPS_TRACK_REF = "GPSTrackRef";
    public static final String TAG_GPS_VERSION_ID = "GPSVersionID";
    private static final String TAG_HAS_THUMBNAIL = "HasThumbnail";
    public static final String TAG_IMAGE_DESCRIPTION = "ImageDescription";
    public static final String TAG_IMAGE_LENGTH = "ImageLength";
    public static final String TAG_IMAGE_UNIQUE_ID = "ImageUniqueID";
    public static final String TAG_IMAGE_WIDTH = "ImageWidth";
    private static final String TAG_INTEROPERABILITY_IFD_POINTER = "InteroperabilityIFDPointer";
    public static final String TAG_INTEROPERABILITY_INDEX = "InteroperabilityIndex";

    @Deprecated
    public static final String TAG_ISO = "ISOSpeedRatings";
    public static final String TAG_ISO_SPEED_RATINGS = "ISOSpeedRatings";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT = "JPEGInterchangeFormat";
    public static final String TAG_JPEG_INTERCHANGE_FORMAT_LENGTH = "JPEGInterchangeFormatLength";
    public static final String TAG_LIGHT_SOURCE = "LightSource";
    public static final String TAG_MAKE = "Make";
    public static final String TAG_MAKER_NOTE = "MakerNote";
    public static final String TAG_MAX_APERTURE_VALUE = "MaxApertureValue";
    public static final String TAG_METERING_MODE = "MeteringMode";
    public static final String TAG_MODEL = "Model";
    public static final String TAG_NEW_SUBFILE_TYPE = "NewSubfileType";
    public static final String TAG_OECF = "OECF";
    public static final String TAG_OFFSET_TIME = "OffsetTime";
    public static final String TAG_OFFSET_TIME_DIGITIZED = "OffsetTimeDigitized";
    public static final String TAG_OFFSET_TIME_ORIGINAL = "OffsetTimeOriginal";
    public static final String TAG_ORF_ASPECT_FRAME = "AspectFrame";
    private static final String TAG_ORF_CAMERA_SETTINGS_IFD_POINTER = "CameraSettingsIFDPointer";
    private static final String TAG_ORF_IMAGE_PROCESSING_IFD_POINTER = "ImageProcessingIFDPointer";
    public static final String TAG_ORF_PREVIEW_IMAGE_LENGTH = "PreviewImageLength";
    public static final String TAG_ORF_PREVIEW_IMAGE_START = "PreviewImageStart";
    public static final String TAG_ORF_THUMBNAIL_IMAGE = "ThumbnailImage";
    public static final String TAG_ORIENTATION = "Orientation";
    public static final String TAG_PHOTOMETRIC_INTERPRETATION = "PhotometricInterpretation";
    public static final String TAG_PIXEL_X_DIMENSION = "PixelXDimension";
    public static final String TAG_PIXEL_Y_DIMENSION = "PixelYDimension";
    public static final String TAG_PLANAR_CONFIGURATION = "PlanarConfiguration";
    public static final String TAG_PRIMARY_CHROMATICITIES = "PrimaryChromaticities";
    private static final ExifTag TAG_RAF_IMAGE_SIZE;
    public static final String TAG_REFERENCE_BLACK_WHITE = "ReferenceBlackWhite";
    public static final String TAG_RELATED_SOUND_FILE = "RelatedSoundFile";
    public static final String TAG_RESOLUTION_UNIT = "ResolutionUnit";
    public static final String TAG_ROWS_PER_STRIP = "RowsPerStrip";
    public static final String TAG_RW2_ISO = "ISO";
    public static final String TAG_RW2_JPG_FROM_RAW = "JpgFromRaw";
    public static final String TAG_RW2_SENSOR_BOTTOM_BORDER = "SensorBottomBorder";
    public static final String TAG_RW2_SENSOR_LEFT_BORDER = "SensorLeftBorder";
    public static final String TAG_RW2_SENSOR_RIGHT_BORDER = "SensorRightBorder";
    public static final String TAG_RW2_SENSOR_TOP_BORDER = "SensorTopBorder";
    public static final String TAG_SAMPLES_PER_PIXEL = "SamplesPerPixel";
    public static final String TAG_SATURATION = "Saturation";
    public static final String TAG_SCENE_CAPTURE_TYPE = "SceneCaptureType";
    public static final String TAG_SCENE_TYPE = "SceneType";
    public static final String TAG_SENSING_METHOD = "SensingMethod";
    public static final String TAG_SHARPNESS = "Sharpness";
    public static final String TAG_SHUTTER_SPEED_VALUE = "ShutterSpeedValue";
    public static final String TAG_SOFTWARE = "Software";
    public static final String TAG_SPATIAL_FREQUENCY_RESPONSE = "SpatialFrequencyResponse";
    public static final String TAG_SPECTRAL_SENSITIVITY = "SpectralSensitivity";
    public static final String TAG_STRIP_BYTE_COUNTS = "StripByteCounts";
    public static final String TAG_STRIP_OFFSETS = "StripOffsets";
    public static final String TAG_SUBFILE_TYPE = "SubfileType";
    public static final String TAG_SUBJECT_AREA = "SubjectArea";
    public static final String TAG_SUBJECT_DISTANCE_RANGE = "SubjectDistanceRange";
    public static final String TAG_SUBJECT_LOCATION = "SubjectLocation";
    public static final String TAG_SUBSEC_TIME = "SubSecTime";
    public static final String TAG_SUBSEC_TIME_DIG = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_DIGITIZED = "SubSecTimeDigitized";
    public static final String TAG_SUBSEC_TIME_ORIG = "SubSecTimeOriginal";
    public static final String TAG_SUBSEC_TIME_ORIGINAL = "SubSecTimeOriginal";
    private static final String TAG_SUB_IFD_POINTER = "SubIFDPointer";
    private static final String TAG_THUMBNAIL_DATA = "ThumbnailData";
    public static final String TAG_THUMBNAIL_IMAGE_LENGTH = "ThumbnailImageLength";
    public static final String TAG_THUMBNAIL_IMAGE_WIDTH = "ThumbnailImageWidth";
    private static final String TAG_THUMBNAIL_LENGTH = "ThumbnailLength";
    private static final String TAG_THUMBNAIL_OFFSET = "ThumbnailOffset";
    public static final String TAG_THUMBNAIL_ORIENTATION = "ThumbnailOrientation";
    public static final String TAG_TRANSFER_FUNCTION = "TransferFunction";
    public static final String TAG_USER_COMMENT = "UserComment";
    public static final String TAG_WHITE_BALANCE = "WhiteBalance";
    public static final String TAG_WHITE_POINT = "WhitePoint";
    public static final String TAG_XMP = "Xmp";
    public static final String TAG_X_RESOLUTION = "XResolution";
    public static final String TAG_Y_CB_CR_COEFFICIENTS = "YCbCrCoefficients";
    public static final String TAG_Y_CB_CR_POSITIONING = "YCbCrPositioning";
    public static final String TAG_Y_CB_CR_SUB_SAMPLING = "YCbCrSubSampling";
    public static final String TAG_Y_RESOLUTION = "YResolution";
    private static final int WEBP_CHUNK_SIZE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_BYTE_LENGTH = 4;
    private static final int WEBP_CHUNK_TYPE_VP8X_DEFAULT_LENGTH = 10;
    private static final int WEBP_FILE_SIZE_BYTE_LENGTH = 4;
    private static final byte WEBP_VP8L_SIGNATURE = 47;
    public static final int WHITEBALANCE_AUTO = 0;
    public static final int WHITEBALANCE_MANUAL = 1;
    private static final HashMap[] sExifTagMapsForReading;
    private static final HashMap[] sExifTagMapsForWriting;
    private static SimpleDateFormat sFormatterTz;
    private static final Pattern sGpsTimestampPattern;
    private static final Pattern sNonZeroTimePattern;
    private boolean mAreThumbnailStripsConsecutive;
    private AssetManager.AssetInputStream mAssetInputStream;
    private final HashMap[] mAttributes;
    private ByteOrder mExifByteOrder;
    private boolean mExifHasLength;
    private boolean mExifHasOrientation;
    private boolean mExifHasWidth;
    private int mExifOffset;
    private String mFilename;
    private Set<Integer> mHandledIfdOffsets;
    private boolean mHasThumbnail;
    private boolean mHasThumbnailStrips;
    private boolean mIsExifDataOnly;
    private boolean mIsInputStream;
    private boolean mIsSupportedFile;
    private int mMimeType;
    private boolean mModified;
    private int mOrfMakerNoteOffset;
    private int mOrfThumbnailLength;
    private int mOrfThumbnailOffset;
    private int mRw2JpgFromRawOffset;
    private FileDescriptor mSeekableFileDescriptor;
    private byte[] mThumbnailBytes;
    private int mThumbnailCompression;
    private int mThumbnailLength;
    private int mThumbnailOffset;
    private boolean mXmpIsFromSeparateMarker;
    private static final String TAG = "ExifInterface";
    private static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final byte MARKER_SOI = -40;
    private static final byte[] JPEG_SIGNATURE = {-1, MARKER_SOI, -1};
    private static final byte[] HEIF_TYPE_FTYP = {102, 116, 121, SprAttributeBase.TYPE_SHADOW};
    private static final byte[] HEIF_BRAND_MIF1 = {109, 105, 102, SprAnimatorBase.INTERPOLATOR_TYPE_SINEOUT33};
    private static final byte[] HEIF_BRAND_HEIC = {104, 101, 105, 99};
    private static final byte[] HEIF_BRAND_AVIF = {SprAttributeBase.TYPE_ANIMATOR_SET, 118, 105, 102};
    private static final byte[] HEIF_BRAND_AVIS = {SprAttributeBase.TYPE_ANIMATOR_SET, 118, 105, 115};
    private static final byte[] ORF_MAKER_NOTE_HEADER_1 = {79, 76, 89, 77, 80, 0};
    private static final byte[] ORF_MAKER_NOTE_HEADER_2 = {79, 76, 89, 77, 80, 85, 83, 0, 73, 73};
    private static final byte[] PNG_SIGNATURE = {-119, 80, 78, 71, 13, 10, 26, 10};
    private static final byte[] PNG_CHUNK_TYPE_EXIF = {101, 88, 73, 102};
    private static final byte[] PNG_CHUNK_TYPE_IHDR = {73, 72, 68, 82};
    private static final byte[] PNG_CHUNK_TYPE_IEND = {73, 69, 78, 68};
    private static final byte[] WEBP_SIGNATURE_1 = {82, 73, 70, 70};
    private static final byte[] WEBP_SIGNATURE_2 = {87, 69, 66, 80};
    private static final byte[] WEBP_CHUNK_TYPE_EXIF = {69, 88, 73, 70};
    private static final byte[] WEBP_VP8_SIGNATURE = {-99, 1, 42};
    private static final byte[] WEBP_CHUNK_TYPE_VP8X = "VP8X".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8L = "VP8L".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_VP8 = "VP8 ".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANIM = "ANIM".getBytes(Charset.defaultCharset());
    private static final byte[] WEBP_CHUNK_TYPE_ANMF = "ANMF".getBytes(Charset.defaultCharset());
    private static final String[] IFD_FORMAT_NAMES = {"", "BYTE", "STRING", "USHORT", "ULONG", "URATIONAL", "SBYTE", DevicePolicyResources.UNDEFINED, "SSHORT", "SLONG", "SRATIONAL", "SINGLE", "DOUBLE", "IFD"};
    private static final int[] IFD_FORMAT_BYTES_PER_FORMAT = {0, 1, 1, 2, 4, 8, 1, 1, 2, 4, 8, 4, 8, 1};
    private static final byte[] EXIF_ASCII_PREFIX = {65, 83, 67, 73, 73, 0, 0, 0};
    private static final int[] BITS_PER_SAMPLE_RGB = {8, 8, 8};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_1 = {4};
    private static final int[] BITS_PER_SAMPLE_GREYSCALE_2 = {8};
    public static final String TAG_DIGITAL_ZOOM_RATIO = "DigitalZoomRatio";
    public static final String TAG_EXPOSURE_TIME = "ExposureTime";
    public static final String TAG_SUBJECT_DISTANCE = "SubjectDistance";
    public static final String TAG_GPS_TIMESTAMP = "GPSTimeStamp";
    private static final HashSet<String> sTagSetForCompatibility = new HashSet<>(Arrays.asList("FNumber", TAG_DIGITAL_ZOOM_RATIO, TAG_EXPOSURE_TIME, TAG_SUBJECT_DISTANCE, TAG_GPS_TIMESTAMP));
    private static final HashMap<Integer, Integer> sExifPointerTagMap = new HashMap<>();
    private static final Charset ASCII = Charset.forName("US-ASCII");
    private static final byte[] IDENTIFIER_EXIF_APP1 = "Exif\u0000\u0000".getBytes(ASCII);
    private static final byte[] IDENTIFIER_XMP_APP1 = "http://ns.adobe.com/xap/1.0/\u0000".getBytes(ASCII);
    private static SimpleDateFormat sFormatter = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss", Locale.US);

    @Retention(RetentionPolicy.SOURCE)
    public @interface ExifStreamType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface IfdType {
    }

    static {
        int i = 3;
        int i2 = 4;
        int i3 = 6;
        int i4 = 1;
        int i5 = 2;
        int i6 = 5;
        int i7 = 7;
        int i8 = 4;
        int i9 = 3;
        int i10 = 23;
        IFD_TIFF_TAGS = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, i2), new ExifTag(TAG_SUBFILE_TYPE, 255, i2), new ExifTag(TAG_IMAGE_WIDTH, 256, 3, 4), new ExifTag(TAG_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, i), new ExifTag(TAG_COMPRESSION, 259, i), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, i), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, i5), new ExifTag(TAG_MAKE, 271, i5), new ExifTag(TAG_MODEL, 272, i5), new ExifTag(TAG_STRIP_OFFSETS, 273, 3, 4), new ExifTag(TAG_ORIENTATION, 274, i), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, i), new ExifTag(TAG_ROWS_PER_STRIP, 278, i9, i8), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, i9, i8), new ExifTag(TAG_X_RESOLUTION, 282, i6), new ExifTag(TAG_Y_RESOLUTION, 283, i6), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, i), new ExifTag(TAG_RESOLUTION_UNIT, 296, i), new ExifTag(TAG_TRANSFER_FUNCTION, 301, i), new ExifTag(TAG_SOFTWARE, 305, i5), new ExifTag(TAG_DATETIME, 306, i5), new ExifTag(TAG_ARTIST, 315, i5), new ExifTag(TAG_WHITE_POINT, 318, i6), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, i6), new ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i2), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, i6), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, i), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, i), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, i6), new ExifTag(TAG_COPYRIGHT, 33432, i5), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new ExifTag(TAG_GPS_INFO_IFD_POINTER, GLES30.GL_DRAW_BUFFER0, i2), new ExifTag(TAG_RW2_SENSOR_TOP_BORDER, i2, i2), new ExifTag(TAG_RW2_SENSOR_LEFT_BORDER, i6, i2), new ExifTag(TAG_RW2_SENSOR_BOTTOM_BORDER, i3, i2), new ExifTag(TAG_RW2_SENSOR_RIGHT_BORDER, i7, i2), new ExifTag(TAG_RW2_ISO, i10, i), new ExifTag(TAG_RW2_JPG_FROM_RAW, 46, i7), new ExifTag(TAG_XMP, 700, i4)};
        int i11 = 10;
        int i12 = 4;
        int i13 = 3;
        IFD_EXIF_TAGS = new ExifTag[]{new ExifTag(TAG_EXPOSURE_TIME, 33434, i6), new ExifTag("FNumber", 33437, i6), new ExifTag(TAG_EXPOSURE_PROGRAM, 34850, i), new ExifTag(TAG_SPECTRAL_SENSITIVITY, GLES30.GL_MAX_DRAW_BUFFERS, i5), new ExifTag("ISOSpeedRatings", GLES30.GL_DRAW_BUFFER2, i), new ExifTag(TAG_OECF, GLES30.GL_DRAW_BUFFER3, i7), new ExifTag(TAG_EXIF_VERSION, 36864, i5), new ExifTag(TAG_DATETIME_ORIGINAL, 36867, i5), new ExifTag(TAG_DATETIME_DIGITIZED, 36868, i5), new ExifTag(TAG_OFFSET_TIME, 36880, i5), new ExifTag(TAG_OFFSET_TIME_ORIGINAL, 36881, i5), new ExifTag(TAG_OFFSET_TIME_DIGITIZED, 36882, i5), new ExifTag(TAG_COMPONENTS_CONFIGURATION, 37121, i7), new ExifTag(TAG_COMPRESSED_BITS_PER_PIXEL, 37122, i6), new ExifTag(TAG_SHUTTER_SPEED_VALUE, 37377, i11), new ExifTag(TAG_APERTURE_VALUE, 37378, i6), new ExifTag(TAG_BRIGHTNESS_VALUE, 37379, i11), new ExifTag(TAG_EXPOSURE_BIAS_VALUE, 37380, i11), new ExifTag(TAG_MAX_APERTURE_VALUE, 37381, i6), new ExifTag(TAG_SUBJECT_DISTANCE, 37382, i6), new ExifTag(TAG_METERING_MODE, 37383, i), new ExifTag(TAG_LIGHT_SOURCE, 37384, i), new ExifTag(TAG_FLASH, 37385, i), new ExifTag(TAG_FOCAL_LENGTH, 37386, i6), new ExifTag(TAG_SUBJECT_AREA, 37396, i), new ExifTag(TAG_MAKER_NOTE, 37500, i7), new ExifTag(TAG_USER_COMMENT, 37510, i7), new ExifTag(TAG_SUBSEC_TIME, 37520, i5), new ExifTag("SubSecTimeOriginal", 37521, i5), new ExifTag("SubSecTimeDigitized", 37522, i5), new ExifTag(TAG_FLASHPIX_VERSION, UsbManager.USB_DATA_TRANSFER_RATE_40G, i7), new ExifTag(TAG_COLOR_SPACE, 40961, i), new ExifTag(TAG_PIXEL_X_DIMENSION, 40962, i13, i12), new ExifTag(TAG_PIXEL_Y_DIMENSION, 40963, i13, i12), new ExifTag(TAG_RELATED_SOUND_FILE, 40964, i5), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, i2), new ExifTag(TAG_FLASH_ENERGY, 41483, i6), new ExifTag(TAG_SPATIAL_FREQUENCY_RESPONSE, 41484, i7), new ExifTag(TAG_FOCAL_PLANE_X_RESOLUTION, 41486, i6), new ExifTag(TAG_FOCAL_PLANE_Y_RESOLUTION, 41487, i6), new ExifTag(TAG_FOCAL_PLANE_RESOLUTION_UNIT, 41488, i), new ExifTag(TAG_SUBJECT_LOCATION, 41492, i), new ExifTag(TAG_EXPOSURE_INDEX, 41493, i6), new ExifTag(TAG_SENSING_METHOD, 41495, i), new ExifTag(TAG_FILE_SOURCE, 41728, i7), new ExifTag(TAG_SCENE_TYPE, 41729, i7), new ExifTag(TAG_CFA_PATTERN, 41730, i7), new ExifTag(TAG_CUSTOM_RENDERED, 41985, i), new ExifTag(TAG_EXPOSURE_MODE, 41986, i), new ExifTag(TAG_WHITE_BALANCE, 41987, i), new ExifTag(TAG_DIGITAL_ZOOM_RATIO, 41988, i6), new ExifTag(TAG_FOCAL_LENGTH_IN_35MM_FILM, 41989, i), new ExifTag(TAG_SCENE_CAPTURE_TYPE, 41990, i), new ExifTag(TAG_GAIN_CONTROL, 41991, i), new ExifTag(TAG_CONTRAST, 41992, i), new ExifTag(TAG_SATURATION, 41993, i), new ExifTag(TAG_SHARPNESS, 41994, i), new ExifTag(TAG_DEVICE_SETTING_DESCRIPTION, 41995, i7), new ExifTag(TAG_SUBJECT_DISTANCE_RANGE, 41996, i), new ExifTag(TAG_IMAGE_UNIQUE_ID, 42016, i5), new ExifTag(TAG_DNG_VERSION, 50706, i4), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, i13, i12)};
        IFD_GPS_TAGS = new ExifTag[]{new ExifTag(TAG_GPS_VERSION_ID, 0, i4), new ExifTag(TAG_GPS_LATITUDE_REF, i4, i5), new ExifTag(TAG_GPS_LATITUDE, i5, i6), new ExifTag(TAG_GPS_LONGITUDE_REF, i, i5), new ExifTag(TAG_GPS_LONGITUDE, i2, i6), new ExifTag(TAG_GPS_ALTITUDE_REF, i6, i4), new ExifTag(TAG_GPS_ALTITUDE, i3, i6), new ExifTag(TAG_GPS_TIMESTAMP, i7, i6), new ExifTag(TAG_GPS_SATELLITES, 8, i5), new ExifTag(TAG_GPS_STATUS, 9, i5), new ExifTag(TAG_GPS_MEASURE_MODE, 10, i5), new ExifTag(TAG_GPS_DOP, 11, i6), new ExifTag(TAG_GPS_SPEED_REF, 12, i5), new ExifTag(TAG_GPS_SPEED, 13, i6), new ExifTag(TAG_GPS_TRACK_REF, 14, i5), new ExifTag(TAG_GPS_TRACK, 15, i6), new ExifTag(TAG_GPS_IMG_DIRECTION_REF, 16, i5), new ExifTag(TAG_GPS_IMG_DIRECTION, 17, i6), new ExifTag(TAG_GPS_MAP_DATUM, 18, i5), new ExifTag(TAG_GPS_DEST_LATITUDE_REF, 19, i5), new ExifTag(TAG_GPS_DEST_LATITUDE, 20, i6), new ExifTag(TAG_GPS_DEST_LONGITUDE_REF, 21, i5), new ExifTag(TAG_GPS_DEST_LONGITUDE, 22, i6), new ExifTag(TAG_GPS_DEST_BEARING_REF, i10, i5), new ExifTag(TAG_GPS_DEST_BEARING, 24, i6), new ExifTag(TAG_GPS_DEST_DISTANCE_REF, 25, i5), new ExifTag(TAG_GPS_DEST_DISTANCE, 26, i6), new ExifTag(TAG_GPS_PROCESSING_METHOD, 27, i7), new ExifTag(TAG_GPS_AREA_INFORMATION, 28, i7), new ExifTag(TAG_GPS_DATESTAMP, 29, i5), new ExifTag(TAG_GPS_DIFFERENTIAL, 30, i)};
        IFD_INTEROPERABILITY_TAGS = new ExifTag[]{new ExifTag(TAG_INTEROPERABILITY_INDEX, i4, i5)};
        IFD_THUMBNAIL_TAGS = new ExifTag[]{new ExifTag(TAG_NEW_SUBFILE_TYPE, 254, i2), new ExifTag(TAG_SUBFILE_TYPE, 255, i2), new ExifTag(TAG_THUMBNAIL_IMAGE_WIDTH, 256, i13, i12), new ExifTag(TAG_THUMBNAIL_IMAGE_LENGTH, 257, 3, 4), new ExifTag(TAG_BITS_PER_SAMPLE, 258, i), new ExifTag(TAG_COMPRESSION, 259, i), new ExifTag(TAG_PHOTOMETRIC_INTERPRETATION, 262, i), new ExifTag(TAG_IMAGE_DESCRIPTION, 270, i5), new ExifTag(TAG_MAKE, 271, i5), new ExifTag(TAG_MODEL, 272, i5), new ExifTag(TAG_STRIP_OFFSETS, 273, i13, i12), new ExifTag(TAG_THUMBNAIL_ORIENTATION, 274, i), new ExifTag(TAG_SAMPLES_PER_PIXEL, 277, i), new ExifTag(TAG_ROWS_PER_STRIP, 278, i13, i12), new ExifTag(TAG_STRIP_BYTE_COUNTS, 279, i13, i12), new ExifTag(TAG_X_RESOLUTION, 282, i6), new ExifTag(TAG_Y_RESOLUTION, 283, i6), new ExifTag(TAG_PLANAR_CONFIGURATION, 284, i), new ExifTag(TAG_RESOLUTION_UNIT, 296, i), new ExifTag(TAG_TRANSFER_FUNCTION, 301, i), new ExifTag(TAG_SOFTWARE, 305, i5), new ExifTag(TAG_DATETIME, 306, i5), new ExifTag(TAG_ARTIST, 315, i5), new ExifTag(TAG_WHITE_POINT, 318, i6), new ExifTag(TAG_PRIMARY_CHROMATICITIES, 319, i6), new ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT, 513, i2), new ExifTag(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, 514, i2), new ExifTag(TAG_Y_CB_CR_COEFFICIENTS, 529, i6), new ExifTag(TAG_Y_CB_CR_SUB_SAMPLING, 530, i), new ExifTag(TAG_Y_CB_CR_POSITIONING, 531, i), new ExifTag(TAG_REFERENCE_BLACK_WHITE, 532, i6), new ExifTag(TAG_COPYRIGHT, 33432, i5), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new ExifTag(TAG_GPS_INFO_IFD_POINTER, GLES30.GL_DRAW_BUFFER0, i2), new ExifTag(TAG_DNG_VERSION, 50706, i4), new ExifTag(TAG_DEFAULT_CROP_SIZE, 50720, 3, 4)};
        TAG_RAF_IMAGE_SIZE = new ExifTag(TAG_STRIP_OFFSETS, 273, i);
        ORF_MAKER_NOTE_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_THUMBNAIL_IMAGE, 256, i7), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, i2), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, BluetoothHciProtoEnums.CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE, i2)};
        ORF_CAMERA_SETTINGS_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_PREVIEW_IMAGE_START, 257, i2), new ExifTag(TAG_ORF_PREVIEW_IMAGE_LENGTH, 258, i2)};
        ORF_IMAGE_PROCESSING_TAGS = new ExifTag[]{new ExifTag(TAG_ORF_ASPECT_FRAME, SmsCbConstants.MESSAGE_ID_CMAS_ALERT_EXTREME_IMMEDIATE_OBSERVED, i)};
        PEF_TAGS = new ExifTag[]{new ExifTag(TAG_COLOR_SPACE, 55, i)};
        EXIF_TAGS = new ExifTag[][]{IFD_TIFF_TAGS, IFD_EXIF_TAGS, IFD_GPS_TAGS, IFD_INTEROPERABILITY_TAGS, IFD_THUMBNAIL_TAGS, IFD_TIFF_TAGS, ORF_MAKER_NOTE_TAGS, ORF_CAMERA_SETTINGS_TAGS, ORF_IMAGE_PROCESSING_TAGS, PEF_TAGS};
        EXIF_POINTER_TAGS = new ExifTag[]{new ExifTag(TAG_SUB_IFD_POINTER, 330, i2), new ExifTag(TAG_EXIF_IFD_POINTER, 34665, i2), new ExifTag(TAG_GPS_INFO_IFD_POINTER, GLES30.GL_DRAW_BUFFER0, i2), new ExifTag(TAG_INTEROPERABILITY_IFD_POINTER, 40965, i2), new ExifTag(TAG_ORF_CAMERA_SETTINGS_IFD_POINTER, 8224, i4), new ExifTag(TAG_ORF_IMAGE_PROCESSING_IFD_POINTER, BluetoothHciProtoEnums.CMD_BLE_SET_PERIODIC_ADVERTISING_ENABLE, i4)};
        sExifTagMapsForReading = new HashMap[EXIF_TAGS.length];
        sExifTagMapsForWriting = new HashMap[EXIF_TAGS.length];
        sFormatter.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        sFormatterTz = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss XXX", Locale.US);
        sFormatterTz.setTimeZone(TimeZone.getTimeZone(Time.TIMEZONE_UTC));
        for (int ifdType = 0; ifdType < EXIF_TAGS.length; ifdType++) {
            sExifTagMapsForReading[ifdType] = new HashMap();
            sExifTagMapsForWriting[ifdType] = new HashMap();
            for (ExifTag tag : EXIF_TAGS[ifdType]) {
                sExifTagMapsForReading[ifdType].put(Integer.valueOf(tag.number), tag);
                sExifTagMapsForWriting[ifdType].put(tag.name, tag);
            }
        }
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[0].number), 5);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[1].number), 1);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[2].number), 2);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[3].number), 3);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[4].number), 7);
        sExifPointerTagMap.put(Integer.valueOf(EXIF_POINTER_TAGS[5].number), 8);
        sNonZeroTimePattern = Pattern.compile(".*[1-9].*");
        sGpsTimestampPattern = Pattern.compile("^([0-9][0-9]):([0-9][0-9]):([0-9][0-9])$");
    }

    private static class Rational {
        public final long denominator;
        public final long numerator;

        private Rational(long numerator, long denominator) {
            if (denominator == 0) {
                this.numerator = 0L;
                this.denominator = 1L;
            } else {
                this.numerator = numerator;
                this.denominator = denominator;
            }
        }

        public String toString() {
            return this.numerator + "/" + this.denominator;
        }

        public double calculate() {
            return this.numerator / this.denominator;
        }
    }

    private static class ExifAttribute {
        public static final long BYTES_OFFSET_UNKNOWN = -1;
        public final byte[] bytes;
        public final long bytesOffset;
        public final int format;
        public final int numberOfComponents;

        private ExifAttribute(int format, int numberOfComponents, byte[] bytes) {
            this(format, numberOfComponents, -1L, bytes);
        }

        private ExifAttribute(int format, int numberOfComponents, long bytesOffset, byte[] bytes) {
            this.format = format;
            this.numberOfComponents = numberOfComponents;
            this.bytesOffset = bytesOffset;
            this.bytes = bytes;
        }

        public static ExifAttribute createUShort(int[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[3] * values.length]);
            buffer.order(byteOrder);
            for (int value : values) {
                buffer.putShort((short) value);
            }
            return new ExifAttribute(3, values.length, buffer.array());
        }

        public static ExifAttribute createUShort(int value, ByteOrder byteOrder) {
            return createUShort(new int[]{value}, byteOrder);
        }

        public static ExifAttribute createULong(long[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[4] * values.length]);
            buffer.order(byteOrder);
            for (long value : values) {
                buffer.putInt((int) value);
            }
            return new ExifAttribute(4, values.length, buffer.array());
        }

        public static ExifAttribute createULong(long value, ByteOrder byteOrder) {
            return createULong(new long[]{value}, byteOrder);
        }

        public static ExifAttribute createSLong(int[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[9] * values.length]);
            buffer.order(byteOrder);
            for (int value : values) {
                buffer.putInt(value);
            }
            return new ExifAttribute(9, values.length, buffer.array());
        }

        public static ExifAttribute createSLong(int value, ByteOrder byteOrder) {
            return createSLong(new int[]{value}, byteOrder);
        }

        public static ExifAttribute createByte(String value) {
            if (value.length() == 1 && value.charAt(0) >= '0' && value.charAt(0) <= '1') {
                byte[] bytes = {(byte) (value.charAt(0) - '0')};
                return new ExifAttribute(1, bytes.length, bytes);
            }
            byte[] ascii = value.getBytes(ExifInterface.ASCII);
            return new ExifAttribute(1, ascii.length, ascii);
        }

        public static ExifAttribute createString(String value) {
            byte[] ascii = (value + (char) 0).getBytes(ExifInterface.ASCII);
            return new ExifAttribute(2, ascii.length, ascii);
        }

        public static ExifAttribute createURational(Rational[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[5] * values.length]);
            buffer.order(byteOrder);
            for (Rational value : values) {
                buffer.putInt((int) value.numerator);
                buffer.putInt((int) value.denominator);
            }
            return new ExifAttribute(5, values.length, buffer.array());
        }

        public static ExifAttribute createURational(Rational value, ByteOrder byteOrder) {
            return createURational(new Rational[]{value}, byteOrder);
        }

        public static ExifAttribute createSRational(Rational[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[10] * values.length]);
            buffer.order(byteOrder);
            for (Rational value : values) {
                buffer.putInt((int) value.numerator);
                buffer.putInt((int) value.denominator);
            }
            return new ExifAttribute(10, values.length, buffer.array());
        }

        public static ExifAttribute createSRational(Rational value, ByteOrder byteOrder) {
            return createSRational(new Rational[]{value}, byteOrder);
        }

        public static ExifAttribute createDouble(double[] values, ByteOrder byteOrder) {
            ByteBuffer buffer = ByteBuffer.wrap(new byte[ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[12] * values.length]);
            buffer.order(byteOrder);
            for (double value : values) {
                buffer.putDouble(value);
            }
            return new ExifAttribute(12, values.length, buffer.array());
        }

        public static ExifAttribute createDouble(double value, ByteOrder byteOrder) {
            return createDouble(new double[]{value}, byteOrder);
        }

        public String toString() {
            return NavigationBarInflaterView.KEY_CODE_START + ExifInterface.IFD_FORMAT_NAMES[this.format] + ", data length:" + this.bytes.length + NavigationBarInflaterView.KEY_CODE_END;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Object getValue(ByteOrder byteOrder) {
            int ch;
            try {
                ByteOrderedDataInputStream inputStream = new ByteOrderedDataInputStream(this.bytes);
                try {
                    inputStream.setByteOrder(byteOrder);
                    switch (this.format) {
                        case 1:
                        case 6:
                            if (this.bytes.length == 1 && this.bytes[0] >= 0 && this.bytes[0] <= 1) {
                                return new String(new char[]{(char) (this.bytes[0] + SprAnimatorBase.INTERPOLATOR_TYPE_SINEINOUT90)});
                            }
                            return new String(this.bytes, ExifInterface.ASCII);
                        case 2:
                        case 7:
                            int index = 0;
                            if (this.numberOfComponents >= ExifInterface.EXIF_ASCII_PREFIX.length) {
                                boolean same = true;
                                int i = 0;
                                while (true) {
                                    if (i < ExifInterface.EXIF_ASCII_PREFIX.length) {
                                        if (this.bytes[i] == ExifInterface.EXIF_ASCII_PREFIX[i]) {
                                            i++;
                                        } else {
                                            same = false;
                                        }
                                    }
                                }
                                if (same) {
                                    index = ExifInterface.EXIF_ASCII_PREFIX.length;
                                }
                            }
                            StringBuilder stringBuilder = new StringBuilder();
                            while (index < this.numberOfComponents && (ch = this.bytes[index]) != 0) {
                                if (ch >= 32) {
                                    stringBuilder.append((char) ch);
                                } else {
                                    stringBuilder.append('?');
                                }
                                index++;
                            }
                            return stringBuilder.toString();
                        case 3:
                            int[] values = new int[this.numberOfComponents];
                            for (int i2 = 0; i2 < this.numberOfComponents; i2++) {
                                values[i2] = inputStream.readUnsignedShort();
                            }
                            return values;
                        case 4:
                            long[] values2 = new long[this.numberOfComponents];
                            for (int i3 = 0; i3 < this.numberOfComponents; i3++) {
                                values2[i3] = inputStream.readUnsignedInt();
                            }
                            return values2;
                        case 5:
                            Rational[] values3 = new Rational[this.numberOfComponents];
                            for (int i4 = 0; i4 < this.numberOfComponents; i4++) {
                                long numerator = inputStream.readUnsignedInt();
                                long denominator = inputStream.readUnsignedInt();
                                values3[i4] = new Rational(numerator, denominator);
                            }
                            return values3;
                        case 8:
                            int[] values4 = new int[this.numberOfComponents];
                            for (int i5 = 0; i5 < this.numberOfComponents; i5++) {
                                values4[i5] = inputStream.readShort();
                            }
                            return values4;
                        case 9:
                            int[] values5 = new int[this.numberOfComponents];
                            for (int i6 = 0; i6 < this.numberOfComponents; i6++) {
                                values5[i6] = inputStream.readInt();
                            }
                            return values5;
                        case 10:
                            Rational[] values6 = new Rational[this.numberOfComponents];
                            for (int i7 = 0; i7 < this.numberOfComponents; i7++) {
                                long numerator2 = inputStream.readInt();
                                long denominator2 = inputStream.readInt();
                                values6[i7] = new Rational(numerator2, denominator2);
                            }
                            return values6;
                        case 11:
                            double[] values7 = new double[this.numberOfComponents];
                            for (int i8 = 0; i8 < this.numberOfComponents; i8++) {
                                values7[i8] = inputStream.readFloat();
                            }
                            return values7;
                        case 12:
                            double[] values8 = new double[this.numberOfComponents];
                            for (int i9 = 0; i9 < this.numberOfComponents; i9++) {
                                values8[i9] = inputStream.readDouble();
                            }
                            return values8;
                        default:
                            return null;
                    }
                } catch (IOException e) {
                    e = e;
                    Log.w(ExifInterface.TAG, "IOException occurred during reading a value", e);
                    return null;
                }
            } catch (IOException e2) {
                e = e2;
            }
        }

        public double getDoubleValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a double value");
            }
            if (value instanceof String) {
                return Double.parseDouble((String) value);
            }
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                if (array.length == 1) {
                    return array[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                if (array2.length == 1) {
                    return array2[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (value instanceof double[]) {
                double[] array3 = (double[]) value;
                if (array3.length == 1) {
                    return array3[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (value instanceof Rational[]) {
                Rational[] array4 = (Rational[]) value;
                if (array4.length == 1) {
                    return array4[0].calculate();
                }
                throw new NumberFormatException("There are more than one component");
            }
            throw new NumberFormatException("Couldn't find a double value");
        }

        public int getIntValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                throw new NumberFormatException("NULL can't be converted to a integer value");
            }
            if (value instanceof String) {
                return Integer.parseInt((String) value);
            }
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                if (array.length == 1) {
                    return (int) array[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                if (array2.length == 1) {
                    return array2[0];
                }
                throw new NumberFormatException("There are more than one component");
            }
            throw new NumberFormatException("Couldn't find a integer value");
        }

        public String getStringValue(ByteOrder byteOrder) {
            Object value = getValue(byteOrder);
            if (value == null) {
                return null;
            }
            if (value instanceof String) {
                return (String) value;
            }
            StringBuilder stringBuilder = new StringBuilder();
            if (value instanceof long[]) {
                long[] array = (long[]) value;
                for (int i = 0; i < array.length; i++) {
                    stringBuilder.append(array[i]);
                    if (i + 1 != array.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            }
            if (value instanceof int[]) {
                int[] array2 = (int[]) value;
                for (int i2 = 0; i2 < array2.length; i2++) {
                    stringBuilder.append(array2[i2]);
                    if (i2 + 1 != array2.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            }
            if (value instanceof double[]) {
                double[] array3 = (double[]) value;
                for (int i3 = 0; i3 < array3.length; i3++) {
                    stringBuilder.append(array3[i3]);
                    if (i3 + 1 != array3.length) {
                        stringBuilder.append(",");
                    }
                }
                return stringBuilder.toString();
            }
            if (!(value instanceof Rational[])) {
                return null;
            }
            Rational[] array4 = (Rational[]) value;
            for (int i4 = 0; i4 < array4.length; i4++) {
                stringBuilder.append(array4[i4].numerator);
                stringBuilder.append('/');
                stringBuilder.append(array4[i4].denominator);
                if (i4 + 1 != array4.length) {
                    stringBuilder.append(",");
                }
            }
            return stringBuilder.toString();
        }

        public int size() {
            return ExifInterface.IFD_FORMAT_BYTES_PER_FORMAT[this.format] * this.numberOfComponents;
        }
    }

    private static class ExifTag {
        public final String name;
        public final int number;
        public final int primaryFormat;
        public final int secondaryFormat;

        private ExifTag(String name, int number, int format) {
            this.name = name;
            this.number = number;
            this.primaryFormat = format;
            this.secondaryFormat = -1;
        }

        private ExifTag(String name, int number, int primaryFormat, int secondaryFormat) {
            this.name = name;
            this.number = number;
            this.primaryFormat = primaryFormat;
            this.secondaryFormat = secondaryFormat;
        }
    }

    public ExifInterface(File file) throws IOException {
        this.mAttributes = new HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (file == null) {
            throw new NullPointerException("file cannot be null");
        }
        initForFilename(file.getAbsolutePath());
    }

    public ExifInterface(String filename) throws IOException {
        this.mAttributes = new HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (filename == null) {
            throw new NullPointerException("filename cannot be null");
        }
        initForFilename(filename);
    }

    public ExifInterface(FileDescriptor fileDescriptor) throws IOException {
        this.mAttributes = new HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (fileDescriptor == null) {
            throw new NullPointerException("fileDescriptor cannot be null");
        }
        ParcelFileDescriptor modernFd = FileUtils.convertToModernFd(fileDescriptor);
        fileDescriptor = modernFd != null ? modernFd.getFileDescriptor() : fileDescriptor;
        this.mAssetInputStream = null;
        this.mFilename = null;
        boolean isFdDuped = false;
        if (isSeekableFD(fileDescriptor) && modernFd == null) {
            this.mSeekableFileDescriptor = fileDescriptor;
            try {
                fileDescriptor = Os.dup(fileDescriptor);
                isFdDuped = true;
            } catch (ErrnoException e) {
                throw e.rethrowAsIOException();
            }
        } else {
            this.mSeekableFileDescriptor = null;
        }
        this.mIsInputStream = false;
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileDescriptor);
            loadAttributes(in);
        } finally {
            ExifInterfaceUtils.closeQuietly(in);
            if (isFdDuped) {
                ExifInterfaceUtils.closeFileDescriptor(fileDescriptor);
            }
            if (modernFd != null) {
                modernFd.close();
            }
        }
    }

    public ExifInterface(InputStream inputStream) throws IOException {
        this(inputStream, false);
    }

    public ExifInterface(InputStream inputStream, int streamType) throws IOException {
        this(inputStream, streamType == 1);
    }

    private ExifInterface(InputStream inputStream, boolean shouldBeExifDataOnly) throws IOException {
        this.mAttributes = new HashMap[EXIF_TAGS.length];
        this.mHandledIfdOffsets = new HashSet(EXIF_TAGS.length);
        this.mExifByteOrder = ByteOrder.BIG_ENDIAN;
        this.mExifHasLength = false;
        this.mExifHasWidth = false;
        this.mExifHasOrientation = false;
        if (inputStream == null) {
            throw new NullPointerException("inputStream cannot be null");
        }
        this.mFilename = null;
        if (shouldBeExifDataOnly) {
            inputStream = new BufferedInputStream(inputStream, 5000);
            if (!isExifDataOnly((BufferedInputStream) inputStream)) {
                Log.w(TAG, "Given data does not follow the structure of an Exif-only data.");
                return;
            } else {
                this.mIsExifDataOnly = true;
                this.mAssetInputStream = null;
                this.mSeekableFileDescriptor = null;
            }
        } else if (inputStream instanceof AssetManager.AssetInputStream) {
            this.mAssetInputStream = (AssetManager.AssetInputStream) inputStream;
            this.mSeekableFileDescriptor = null;
        } else if ((inputStream instanceof FileInputStream) && isSeekableFD(((FileInputStream) inputStream).getFD())) {
            this.mAssetInputStream = null;
            this.mSeekableFileDescriptor = ((FileInputStream) inputStream).getFD();
        } else {
            this.mAssetInputStream = null;
            this.mSeekableFileDescriptor = null;
        }
        loadAttributes(inputStream);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public static boolean isSupportedMimeType(String mimeType) {
        char c;
        if (mimeType == null) {
            throw new NullPointerException("mimeType shouldn't be null");
        }
        String lowerCase = mimeType.toLowerCase(Locale.ROOT);
        switch (lowerCase.hashCode()) {
            case -1875291391:
                if (lowerCase.equals("image/x-fuji-raf")) {
                    c = '\n';
                    break;
                }
                c = 65535;
                break;
            case -1635437028:
                if (lowerCase.equals("image/x-samsung-srw")) {
                    c = '\t';
                    break;
                }
                c = 65535;
                break;
            case -1594371159:
                if (lowerCase.equals("image/x-sony-arw")) {
                    c = 5;
                    break;
                }
                c = 65535;
                break;
            case -1487464693:
                if (lowerCase.equals("image/heic")) {
                    c = 11;
                    break;
                }
                c = 65535;
                break;
            case -1487464690:
                if (lowerCase.equals("image/heif")) {
                    c = '\f';
                    break;
                }
                c = 65535;
                break;
            case -1487394660:
                if (lowerCase.equals(ContentType.IMAGE_JPEG)) {
                    c = 0;
                    break;
                }
                c = 65535;
                break;
            case -1487018032:
                if (lowerCase.equals("image/webp")) {
                    c = 14;
                    break;
                }
                c = 65535;
                break;
            case -1423313290:
                if (lowerCase.equals("image/x-adobe-dng")) {
                    c = 1;
                    break;
                }
                c = 65535;
                break;
            case -985160897:
                if (lowerCase.equals("image/x-panasonic-rw2")) {
                    c = 6;
                    break;
                }
                c = 65535;
                break;
            case -879258763:
                if (lowerCase.equals(ContentType.IMAGE_PNG)) {
                    c = '\r';
                    break;
                }
                c = 65535;
                break;
            case -332763809:
                if (lowerCase.equals("image/x-pentax-pef")) {
                    c = '\b';
                    break;
                }
                c = 65535;
                break;
            case 1378106698:
                if (lowerCase.equals("image/x-olympus-orf")) {
                    c = 7;
                    break;
                }
                c = 65535;
                break;
            case 2099152104:
                if (lowerCase.equals("image/x-nikon-nef")) {
                    c = 3;
                    break;
                }
                c = 65535;
                break;
            case 2099152524:
                if (lowerCase.equals("image/x-nikon-nrw")) {
                    c = 4;
                    break;
                }
                c = 65535;
                break;
            case 2111234748:
                if (lowerCase.equals("image/x-canon-cr2")) {
                    c = 2;
                    break;
                }
                c = 65535;
                break;
            default:
                c = 65535;
                break;
        }
        switch (c) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case '\b':
            case '\t':
            case '\n':
            case 11:
            case '\f':
            case '\r':
            case 14:
                return true;
            default:
                return false;
        }
    }

    private ExifAttribute getExifAttribute(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            Object value = this.mAttributes[i].get(tag);
            if (value != null) {
                return (ExifAttribute) value;
            }
        }
        return null;
    }

    public String getAttribute(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute attribute = getExifAttribute(tag);
        if (attribute == null) {
            return null;
        }
        if (!sTagSetForCompatibility.contains(tag)) {
            if (this.mMimeType == 12 && this.mExifByteOrder == ByteOrder.LITTLE_ENDIAN) {
                if (tag.equals(TAG_IMAGE_LENGTH)) {
                    if (!this.mExifHasLength) {
                        return attribute.getStringValue(ByteOrder.BIG_ENDIAN);
                    }
                } else if (tag.equals(TAG_IMAGE_WIDTH)) {
                    if (!this.mExifHasWidth) {
                        return attribute.getStringValue(ByteOrder.BIG_ENDIAN);
                    }
                } else if (tag.equals(TAG_ORIENTATION) && !this.mExifHasOrientation) {
                    return attribute.getStringValue(ByteOrder.BIG_ENDIAN);
                }
            }
            return attribute.getStringValue(this.mExifByteOrder);
        }
        if (tag.equals(TAG_GPS_TIMESTAMP)) {
            if (attribute.format != 5 && attribute.format != 10) {
                return null;
            }
            Rational[] array = (Rational[]) attribute.getValue(this.mExifByteOrder);
            if (array.length != 3) {
                return null;
            }
            return String.format("%02d:%02d:%02d", Integer.valueOf((int) (array[0].numerator / array[0].denominator)), Integer.valueOf((int) (array[1].numerator / array[1].denominator)), Integer.valueOf((int) (array[2].numerator / array[2].denominator)));
        }
        try {
            return Double.toString(attribute.getDoubleValue(this.mExifByteOrder));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public int getAttributeInt(String tag, int defaultValue) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(tag);
        if (exifAttribute == null) {
            return defaultValue;
        }
        try {
            return exifAttribute.getIntValue(this.mExifByteOrder);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public double getAttributeDouble(String tag, double defaultValue) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute exifAttribute = getExifAttribute(tag);
        if (exifAttribute == null) {
            return defaultValue;
        }
        try {
            return exifAttribute.getDoubleValue(this.mExifByteOrder);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    public void setAttribute(String tag, String value) {
        int i;
        int i2;
        int dataFormat;
        ExifInterface exifInterface = this;
        String value2 = value;
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        int i3 = 2;
        int i4 = 1;
        if (value2 != null && sTagSetForCompatibility.contains(tag)) {
            if (tag.equals(TAG_GPS_TIMESTAMP)) {
                Matcher m = sGpsTimestampPattern.matcher(value2);
                if (!m.find()) {
                    Log.w(TAG, "Invalid value for " + tag + " : " + value2);
                    return;
                }
                value2 = Integer.parseInt(m.group(1)) + "/1," + Integer.parseInt(m.group(2)) + "/1," + Integer.parseInt(m.group(3)) + "/1";
            } else {
                try {
                    double doubleValue = Double.parseDouble(value);
                    value2 = ((long) (10000.0d * doubleValue)) + "/10000";
                } catch (NumberFormatException e) {
                    Log.w(TAG, "Invalid value for " + tag + " : " + value2);
                    return;
                }
            }
        }
        int i5 = 0;
        while (i5 < EXIF_TAGS.length) {
            if (i5 == 4 && !exifInterface.mHasThumbnail) {
                i = i4;
                i2 = i5;
            } else {
                Object obj = sExifTagMapsForWriting[i5].get(tag);
                if (obj == null) {
                    i = i4;
                    i2 = i5;
                } else if (value2 == null) {
                    exifInterface.mAttributes[i5].remove(tag);
                    i = i4;
                    i2 = i5;
                } else {
                    ExifTag exifTag = (ExifTag) obj;
                    Pair<Integer, Integer> guess = guessDataFormat(value2);
                    if (exifTag.primaryFormat == guess.first.intValue() || exifTag.primaryFormat == guess.second.intValue()) {
                        dataFormat = exifTag.primaryFormat;
                    } else if (exifTag.secondaryFormat != -1 && (exifTag.secondaryFormat == guess.first.intValue() || exifTag.secondaryFormat == guess.second.intValue())) {
                        dataFormat = exifTag.secondaryFormat;
                    } else {
                        int dataFormat2 = exifTag.primaryFormat;
                        if (dataFormat2 == i4 || exifTag.primaryFormat == 7 || exifTag.primaryFormat == i3) {
                            dataFormat = exifTag.primaryFormat;
                        } else if (!DEBUG) {
                            i = i4;
                            i2 = i5;
                        } else {
                            Log.d(TAG, "Given tag (" + tag + ") value didn't match with one of expected formats: " + IFD_FORMAT_NAMES[exifTag.primaryFormat] + (exifTag.secondaryFormat == -1 ? "" : ", " + IFD_FORMAT_NAMES[exifTag.secondaryFormat]) + " (guess: " + IFD_FORMAT_NAMES[guess.first.intValue()] + (guess.second.intValue() != -1 ? ", " + IFD_FORMAT_NAMES[guess.second.intValue()] : "") + NavigationBarInflaterView.KEY_CODE_END);
                            i = i4;
                            i2 = i5;
                        }
                    }
                    char c = 0;
                    switch (dataFormat) {
                        case 1:
                            i = i4;
                            i2 = i5;
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createByte(value2));
                            break;
                        case 2:
                        case 7:
                            i = i4;
                            i2 = i5;
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createString(value2));
                            break;
                        case 3:
                            i = i4;
                            i2 = i5;
                            String[] values = value2.split(",");
                            int[] intArray = new int[values.length];
                            for (int j = 0; j < values.length; j++) {
                                intArray[j] = Integer.parseInt(values[j]);
                            }
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createUShort(intArray, exifInterface.mExifByteOrder));
                            break;
                        case 4:
                            i = i4;
                            i2 = i5;
                            String[] values2 = value2.split(",");
                            long[] longArray = new long[values2.length];
                            for (int j2 = 0; j2 < values2.length; j2++) {
                                longArray[j2] = Long.parseLong(values2[j2]);
                            }
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createULong(longArray, exifInterface.mExifByteOrder));
                            break;
                        case 5:
                            i2 = i5;
                            String[] values3 = value2.split(",");
                            Rational[] rationalArray = new Rational[values3.length];
                            int j3 = 0;
                            while (j3 < values3.length) {
                                String[] numbers = values3[j3].split("/");
                                rationalArray[j3] = new Rational((long) Double.parseDouble(numbers[0]), (long) Double.parseDouble(numbers[1]));
                                j3++;
                                obj = obj;
                                exifTag = exifTag;
                            }
                            i = 1;
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createURational(rationalArray, exifInterface.mExifByteOrder));
                            break;
                        case 6:
                        case 8:
                        case 11:
                        default:
                            i = i4;
                            i2 = i5;
                            if (DEBUG) {
                                Log.d(TAG, "Data format isn't one of expected formats: " + dataFormat);
                                break;
                            } else {
                                break;
                            }
                        case 9:
                            i2 = i5;
                            String[] values4 = value2.split(",");
                            int[] intArray2 = new int[values4.length];
                            for (int j4 = 0; j4 < values4.length; j4++) {
                                intArray2[j4] = Integer.parseInt(values4[j4]);
                            }
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createSLong(intArray2, exifInterface.mExifByteOrder));
                            i = 1;
                            break;
                        case 10:
                            String[] values5 = value2.split(",");
                            Rational[] rationalArray2 = new Rational[values5.length];
                            int j5 = 0;
                            while (j5 < values5.length) {
                                String[] numbers2 = values5[j5].split("/");
                                rationalArray2[j5] = new Rational((long) Double.parseDouble(numbers2[c]), (long) Double.parseDouble(numbers2[i4]));
                                j5++;
                                c = 0;
                                i4 = 1;
                                i5 = i5;
                            }
                            i2 = i5;
                            exifInterface = this;
                            exifInterface.mAttributes[i2].put(tag, ExifAttribute.createSRational(rationalArray2, exifInterface.mExifByteOrder));
                            i = 1;
                            break;
                        case 12:
                            String[] values6 = value2.split(",");
                            double[] doubleArray = new double[values6.length];
                            for (int j6 = 0; j6 < values6.length; j6++) {
                                doubleArray[j6] = Double.parseDouble(values6[j6]);
                            }
                            exifInterface.mAttributes[i5].put(tag, ExifAttribute.createDouble(doubleArray, exifInterface.mExifByteOrder));
                            i = i4;
                            i2 = i5;
                            break;
                    }
                }
            }
            i5 = i2 + 1;
            i4 = i;
            i3 = 2;
        }
    }

    private boolean updateAttribute(String tag, ExifAttribute value) {
        boolean updated = false;
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            if (this.mAttributes[i].containsKey(tag)) {
                this.mAttributes[i].put(tag, value);
                updated = true;
            }
        }
        return updated;
    }

    private void removeAttribute(String tag) {
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            this.mAttributes[i].remove(tag);
        }
    }

    private void loadAttributes(InputStream in) {
        if (in == null) {
            throw new NullPointerException("inputstream shouldn't be null");
        }
        for (int i = 0; i < EXIF_TAGS.length; i++) {
            try {
                try {
                    this.mAttributes[i] = new HashMap();
                } catch (IOException | OutOfMemoryError e) {
                    this.mIsSupportedFile = false;
                    Log.w(TAG, "Invalid image: ExifInterface got an unsupported image format file(ExifInterface supports JPEG and some RAW image formats only) or a corrupted JPEG file to ExifInterface.", e);
                    addDefaultValuesForCompatibility();
                    if (!DEBUG) {
                        return;
                    }
                }
            } catch (Throwable th) {
                addDefaultValuesForCompatibility();
                if (DEBUG) {
                    printAttributes();
                }
                throw th;
            }
        }
        if (!this.mIsExifDataOnly) {
            in = new BufferedInputStream(in, 5000);
            this.mMimeType = getMimeType((BufferedInputStream) in);
        }
        ByteOrderedDataInputStream inputStream = new ByteOrderedDataInputStream(in);
        if (!this.mIsExifDataOnly) {
            switch (this.mMimeType) {
                case 0:
                case 1:
                case 2:
                case 3:
                case 5:
                case 6:
                case 8:
                case 11:
                    getRawAttributes(inputStream);
                    break;
                case 4:
                    getJpegAttributes(inputStream, 0, 0);
                    break;
                case 7:
                    getOrfAttributes(inputStream);
                    break;
                case 9:
                    getRafAttributes(inputStream);
                    break;
                case 10:
                    getRw2Attributes(inputStream);
                    break;
                case 12:
                    getHeifAttributes(inputStream);
                    break;
                case 13:
                    getPngAttributes(inputStream);
                    break;
                case 14:
                    getWebpAttributes(inputStream);
                    break;
            }
        } else {
            getStandaloneAttributes(inputStream);
        }
        setThumbnailData(inputStream);
        this.mIsSupportedFile = true;
        addDefaultValuesForCompatibility();
        if (!DEBUG) {
            return;
        }
        printAttributes();
    }

    private static boolean isSeekableFD(FileDescriptor fd) {
        try {
            Os.lseek(fd, 0L, OsConstants.SEEK_CUR);
            return true;
        } catch (ErrnoException e) {
            if (DEBUG) {
                Log.d(TAG, "The file descriptor for the given input is not seekable");
                return false;
            }
            return false;
        }
    }

    private void printAttributes() {
        for (int i = 0; i < this.mAttributes.length; i++) {
            Log.d(TAG, "The size of tag group[" + i + "]: " + this.mAttributes[i].size());
            for (Map.Entry entry : this.mAttributes[i].entrySet()) {
                ExifAttribute tagValue = (ExifAttribute) entry.getValue();
                Log.d(TAG, "tagName: " + entry.getKey() + ", tagType: " + tagValue.toString() + ", tagValue: '" + tagValue.getStringValue(this.mExifByteOrder) + "'");
            }
        }
    }

    /* JADX WARN: Finally extract failed */
    public void saveAttributes() throws IOException {
        if (!isSupportedFormatForSavingAttributes()) {
            throw new IOException("ExifInterface only supports saving attributes for JPEG, PNG, and WebP formats.");
        }
        if (this.mIsInputStream || (this.mSeekableFileDescriptor == null && this.mFilename == null)) {
            throw new IOException("ExifInterface does not support saving attributes for the current input.");
        }
        if (this.mHasThumbnail && this.mHasThumbnailStrips && !this.mAreThumbnailStripsConsecutive) {
            throw new IOException("ExifInterface does not support saving attributes when the image file has non-consecutive thumbnail strips");
        }
        this.mModified = true;
        this.mThumbnailBytes = getThumbnail();
        FileInputStream in = null;
        FileOutputStream out = null;
        try {
            try {
                File tempFile = File.createTempFile("temp", "tmp");
                if (this.mFilename != null) {
                    in = new FileInputStream(this.mFilename);
                } else if (this.mSeekableFileDescriptor != null) {
                    Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                    in = new FileInputStream(this.mSeekableFileDescriptor);
                }
                out = new FileOutputStream(tempFile);
                ExifInterfaceUtils.copy(in, out);
                ExifInterfaceUtils.closeQuietly(in);
                ExifInterfaceUtils.closeQuietly(out);
                FileInputStream in2 = null;
                FileOutputStream out2 = null;
                try {
                    try {
                        FileInputStream in3 = new FileInputStream(tempFile);
                        if (this.mFilename != null) {
                            out2 = new FileOutputStream(this.mFilename);
                        } else if (this.mSeekableFileDescriptor != null) {
                            Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                            out2 = new FileOutputStream(this.mSeekableFileDescriptor);
                        }
                        BufferedInputStream bufferedIn = new BufferedInputStream(in3);
                        try {
                            BufferedOutputStream bufferedOut = new BufferedOutputStream(out2);
                            try {
                                if (this.mMimeType == 4) {
                                    saveJpegAttributes(bufferedIn, bufferedOut);
                                } else if (this.mMimeType == 13) {
                                    savePngAttributes(bufferedIn, bufferedOut);
                                } else if (this.mMimeType == 14) {
                                    saveWebpAttributes(bufferedIn, bufferedOut);
                                }
                                bufferedOut.close();
                                bufferedIn.close();
                                ExifInterfaceUtils.closeQuietly(in3);
                                ExifInterfaceUtils.closeQuietly(out2);
                                tempFile.delete();
                                this.mThumbnailBytes = null;
                            } finally {
                            }
                        } catch (Throwable th) {
                            try {
                                bufferedIn.close();
                            } catch (Throwable th2) {
                                th.addSuppressed(th2);
                            }
                            throw th;
                        }
                    } catch (Exception e) {
                        in2 = new FileInputStream(tempFile);
                        if (this.mFilename != null) {
                            out2 = new FileOutputStream(this.mFilename);
                        } else if (this.mSeekableFileDescriptor != null) {
                            try {
                                Os.lseek(this.mSeekableFileDescriptor, 0L, OsConstants.SEEK_SET);
                                out2 = new FileOutputStream(this.mSeekableFileDescriptor);
                            } catch (ErrnoException exception) {
                                throw new IOException("Failed to save new file. Original file may be corrupted since error occurred while trying to restore it.", exception);
                            }
                        }
                        ExifInterfaceUtils.copy(in2, out2);
                        ExifInterfaceUtils.closeQuietly(in2);
                        ExifInterfaceUtils.closeQuietly(out2);
                        throw new IOException("Failed to save new file", e);
                    }
                } catch (Throwable th3) {
                    ExifInterfaceUtils.closeQuietly(in2);
                    ExifInterfaceUtils.closeQuietly(null);
                    tempFile.delete();
                    throw th3;
                }
            } catch (Exception e2) {
                throw new IOException("Failed to copy original file to temp file", e2);
            }
        } catch (Throwable th4) {
            ExifInterfaceUtils.closeQuietly(in);
            ExifInterfaceUtils.closeQuietly(out);
            throw th4;
        }
    }

    public boolean hasThumbnail() {
        return this.mHasThumbnail;
    }

    public boolean hasAttribute(String tag) {
        return getExifAttribute(tag) != null;
    }

    public byte[] getThumbnail() {
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return getThumbnailBytes();
        }
        return null;
    }

    public byte[] getThumbnailBytes() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes != null) {
            return this.mThumbnailBytes;
        }
        InputStream in = null;
        FileDescriptor newFileDescriptor = null;
        try {
            try {
                if (this.mAssetInputStream != null) {
                    in = this.mAssetInputStream;
                    if (!in.markSupported()) {
                        Log.d(TAG, "Cannot read thumbnail from inputstream without mark/reset support");
                        ExifInterfaceUtils.closeQuietly(in);
                        if (0 != 0) {
                            ExifInterfaceUtils.closeFileDescriptor(null);
                        }
                        return null;
                    }
                    in.reset();
                } else if (this.mFilename != null) {
                    in = new FileInputStream(this.mFilename);
                } else if (this.mSeekableFileDescriptor != null) {
                    newFileDescriptor = Os.dup(this.mSeekableFileDescriptor);
                    Os.lseek(newFileDescriptor, 0L, OsConstants.SEEK_SET);
                    in = new FileInputStream(newFileDescriptor);
                }
                if (in == null) {
                    throw new FileNotFoundException();
                }
                if (in.skip(this.mThumbnailOffset) != this.mThumbnailOffset) {
                    throw new IOException("Corrupted image");
                }
                byte[] buffer = new byte[this.mThumbnailLength];
                if (in.read(buffer) != this.mThumbnailLength) {
                    throw new IOException("Corrupted image");
                }
                this.mThumbnailBytes = buffer;
                ExifInterfaceUtils.closeQuietly(in);
                if (newFileDescriptor != null) {
                    ExifInterfaceUtils.closeFileDescriptor(newFileDescriptor);
                }
                return buffer;
            } catch (ErrnoException | IOException e) {
                Log.d(TAG, "Encountered exception while getting thumbnail", e);
                ExifInterfaceUtils.closeQuietly(null);
                if (0 != 0) {
                    ExifInterfaceUtils.closeFileDescriptor(null);
                }
                return null;
            }
        } catch (Throwable th) {
            ExifInterfaceUtils.closeQuietly(null);
            if (0 != 0) {
                ExifInterfaceUtils.closeFileDescriptor(null);
            }
            throw th;
        }
    }

    public Bitmap getThumbnailBitmap() {
        if (!this.mHasThumbnail) {
            return null;
        }
        if (this.mThumbnailBytes == null) {
            this.mThumbnailBytes = getThumbnailBytes();
        }
        if (this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7) {
            return BitmapFactory.decodeByteArray(this.mThumbnailBytes, 0, this.mThumbnailLength);
        }
        if (this.mThumbnailCompression == 1) {
            int[] rgbValues = new int[this.mThumbnailBytes.length / 3];
            for (int i = 0; i < rgbValues.length; i++) {
                rgbValues[i] = (this.mThumbnailBytes[i * 3] << 16) + 0 + (this.mThumbnailBytes[(i * 3) + 1] << 8) + this.mThumbnailBytes[(i * 3) + 2];
            }
            ExifAttribute imageLengthAttribute = (ExifAttribute) this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_LENGTH);
            ExifAttribute imageWidthAttribute = (ExifAttribute) this.mAttributes[4].get(TAG_THUMBNAIL_IMAGE_WIDTH);
            if (imageLengthAttribute != null && imageWidthAttribute != null) {
                int imageLength = imageLengthAttribute.getIntValue(this.mExifByteOrder);
                int imageWidth = imageWidthAttribute.getIntValue(this.mExifByteOrder);
                return Bitmap.createBitmap(rgbValues, imageWidth, imageLength, Bitmap.Config.ARGB_8888);
            }
        }
        return null;
    }

    public boolean isThumbnailCompressed() {
        if (this.mHasThumbnail) {
            return this.mThumbnailCompression == 6 || this.mThumbnailCompression == 7;
        }
        return false;
    }

    public long[] getThumbnailRange() {
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        if (!this.mHasThumbnail) {
            return null;
        }
        if (!this.mHasThumbnailStrips || this.mAreThumbnailStripsConsecutive) {
            return new long[]{this.mThumbnailOffset, this.mThumbnailLength};
        }
        return null;
    }

    public long[] getAttributeRange(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        if (this.mModified) {
            throw new IllegalStateException("The underlying file has been modified since being parsed");
        }
        ExifAttribute attribute = getExifAttribute(tag);
        if (attribute != null) {
            return new long[]{attribute.bytesOffset, attribute.bytes.length};
        }
        return null;
    }

    public byte[] getAttributeBytes(String tag) {
        if (tag == null) {
            throw new NullPointerException("tag shouldn't be null");
        }
        ExifAttribute attribute = getExifAttribute(tag);
        if (attribute != null) {
            return attribute.bytes;
        }
        return null;
    }

    public boolean getLatLong(float[] output) {
        String latValue = getAttribute(TAG_GPS_LATITUDE);
        String latRef = getAttribute(TAG_GPS_LATITUDE_REF);
        String lngValue = getAttribute(TAG_GPS_LONGITUDE);
        String lngRef = getAttribute(TAG_GPS_LONGITUDE_REF);
        if (latValue != null && latRef != null && lngValue != null && lngRef != null) {
            try {
                output[0] = convertRationalLatLonToFloat(latValue, latRef);
                output[1] = convertRationalLatLonToFloat(lngValue, lngRef);
                return true;
            } catch (IllegalArgumentException e) {
            }
        }
        return false;
    }

    public double getAltitude(double defaultValue) {
        double altitude = getAttributeDouble(TAG_GPS_ALTITUDE, -1.0d);
        int ref = getAttributeInt(TAG_GPS_ALTITUDE_REF, -1);
        if (altitude < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || ref < 0) {
            return defaultValue;
        }
        return (ref != 1 ? 1 : -1) * altitude;
    }

    public long getDateTime() {
        return parseDateTime(getAttribute(TAG_DATETIME), getAttribute(TAG_SUBSEC_TIME), getAttribute(TAG_OFFSET_TIME));
    }

    public long getDateTimeDigitized() {
        return parseDateTime(getAttribute(TAG_DATETIME_DIGITIZED), getAttribute("SubSecTimeDigitized"), getAttribute(TAG_OFFSET_TIME_DIGITIZED));
    }

    public long getDateTimeOriginal() {
        return parseDateTime(getAttribute(TAG_DATETIME_ORIGINAL), getAttribute("SubSecTimeOriginal"), getAttribute(TAG_OFFSET_TIME_ORIGINAL));
    }

    private static long parseDateTime(String dateTimeString, String subSecs, String offsetString) {
        Date datetime;
        if (dateTimeString == null || !sNonZeroTimePattern.matcher(dateTimeString).matches()) {
            return -1L;
        }
        ParsePosition pos = new ParsePosition(0);
        try {
            synchronized (sFormatter) {
                datetime = sFormatter.parse(dateTimeString, pos);
            }
            if (offsetString != null) {
                String dateTimeString2 = dateTimeString + " " + offsetString;
                ParsePosition position = new ParsePosition(0);
                synchronized (sFormatterTz) {
                    datetime = sFormatterTz.parse(dateTimeString2, position);
                }
            }
            if (datetime == null) {
                return -1L;
            }
            long msecs = datetime.getTime();
            if (subSecs != null) {
                try {
                    long sub = Long.parseLong(subSecs);
                    while (sub > 1000) {
                        sub /= 10;
                    }
                    return msecs + sub;
                } catch (NumberFormatException e) {
                    return msecs;
                }
            }
            return msecs;
        } catch (IllegalArgumentException e2) {
            return -1L;
        }
    }

    public long getGpsDateTime() {
        Date datetime;
        String date = getAttribute(TAG_GPS_DATESTAMP);
        String time = getAttribute(TAG_GPS_TIMESTAMP);
        if (date == null || time == null || (!sNonZeroTimePattern.matcher(date).matches() && !sNonZeroTimePattern.matcher(time).matches())) {
            return -1L;
        }
        String dateTimeString = date + ' ' + time;
        ParsePosition pos = new ParsePosition(0);
        try {
            synchronized (sFormatter) {
                datetime = sFormatter.parse(dateTimeString, pos);
            }
            if (datetime == null) {
                return -1L;
            }
            return datetime.getTime();
        } catch (ArrayIndexOutOfBoundsException | IllegalArgumentException e) {
            return -1L;
        }
    }

    public static float convertRationalLatLonToFloat(String rationalString, String ref) {
        try {
            String[] parts = rationalString.split(",");
            String[] pair = parts[0].split("/");
            double degrees = Double.parseDouble(pair[0].trim()) / Double.parseDouble(pair[1].trim());
            String[] pair2 = parts[1].split("/");
            double minutes = Double.parseDouble(pair2[0].trim()) / Double.parseDouble(pair2[1].trim());
            String[] pair3 = parts[2].split("/");
            double seconds = Double.parseDouble(pair3[0].trim()) / Double.parseDouble(pair3[1].trim());
            double result = (minutes / 60.0d) + degrees + (seconds / 3600.0d);
            if (!ref.equals(GnssSignalType.CODE_TYPE_S)) {
                if (!ref.equals(GnssSignalType.CODE_TYPE_W)) {
                    return (float) result;
                }
            }
            return (float) (-result);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new IllegalArgumentException();
        }
    }

    private void initForFilename(String filename) throws IOException {
        FileInputStream in = null;
        ParcelFileDescriptor modernFd = null;
        this.mAssetInputStream = null;
        this.mFilename = filename;
        this.mIsInputStream = false;
        try {
            in = new FileInputStream(filename);
            modernFd = FileUtils.convertToModernFd(in.getFD());
            if (modernFd != null) {
                ExifInterfaceUtils.closeQuietly(in);
                in = new FileInputStream(modernFd.getFileDescriptor());
                this.mSeekableFileDescriptor = null;
            } else if (isSeekableFD(in.getFD())) {
                this.mSeekableFileDescriptor = in.getFD();
            }
            loadAttributes(in);
        } finally {
            ExifInterfaceUtils.closeQuietly(in);
            if (modernFd != null) {
                modernFd.close();
            }
        }
    }

    private int getMimeType(BufferedInputStream in) throws IOException {
        in.mark(5000);
        byte[] signatureCheckBytes = new byte[5000];
        in.read(signatureCheckBytes);
        in.reset();
        if (isJpegFormat(signatureCheckBytes)) {
            return 4;
        }
        if (isRafFormat(signatureCheckBytes)) {
            return 9;
        }
        if (isHeifFormat(signatureCheckBytes)) {
            return 12;
        }
        if (isOrfFormat(signatureCheckBytes)) {
            return 7;
        }
        if (isRw2Format(signatureCheckBytes)) {
            return 10;
        }
        if (isPngFormat(signatureCheckBytes)) {
            return 13;
        }
        if (isWebpFormat(signatureCheckBytes)) {
            return 14;
        }
        return 0;
    }

    private static boolean isJpegFormat(byte[] signatureCheckBytes) throws IOException {
        for (int i = 0; i < JPEG_SIGNATURE.length; i++) {
            if (signatureCheckBytes[i] != JPEG_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isRafFormat(byte[] signatureCheckBytes) throws IOException {
        byte[] rafSignatureBytes = RAF_SIGNATURE.getBytes();
        for (int i = 0; i < rafSignatureBytes.length; i++) {
            if (signatureCheckBytes[i] != rafSignatureBytes[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isHeifFormat(byte[] signatureCheckBytes) throws IOException {
        long chunkSize;
        byte[] chunkType;
        ByteOrderedDataInputStream signatureInputStream = null;
        try {
            try {
                signatureInputStream = new ByteOrderedDataInputStream(signatureCheckBytes);
                chunkSize = signatureInputStream.readInt();
                chunkType = new byte[4];
                signatureInputStream.read(chunkType);
            } catch (Exception e) {
                if (DEBUG) {
                    Log.d(TAG, "Exception parsing HEIF file type box.", e);
                }
                if (signatureInputStream == null) {
                }
            }
            if (!Arrays.equals(chunkType, HEIF_TYPE_FTYP)) {
                signatureInputStream.close();
                return false;
            }
            long chunkDataOffset = 8;
            if (chunkSize == 1) {
                chunkSize = signatureInputStream.readLong();
                if (chunkSize < 16) {
                    signatureInputStream.close();
                    return false;
                }
                chunkDataOffset = 8 + 8;
            }
            if (chunkSize > signatureCheckBytes.length) {
                chunkSize = signatureCheckBytes.length;
            }
            long chunkDataSize = chunkSize - chunkDataOffset;
            if (chunkDataSize < 8) {
                signatureInputStream.close();
                return false;
            }
            byte[] brand = new byte[4];
            boolean isMif1 = false;
            boolean isHeic = false;
            boolean isAvif = false;
            for (long i = 0; i < chunkDataSize / 4; i++) {
                if (signatureInputStream.read(brand) != brand.length) {
                    signatureInputStream.close();
                    return false;
                }
                if (i != 1) {
                    if (Arrays.equals(brand, HEIF_BRAND_MIF1)) {
                        isMif1 = true;
                    } else if (Arrays.equals(brand, HEIF_BRAND_HEIC)) {
                        isHeic = true;
                    } else if (Arrays.equals(brand, HEIF_BRAND_AVIF) || Arrays.equals(brand, HEIF_BRAND_AVIS)) {
                        isAvif = true;
                    }
                    if (isMif1 && (isHeic || isAvif)) {
                        signatureInputStream.close();
                        return true;
                    }
                }
            }
            signatureInputStream.close();
            return false;
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isOrfFormat(byte[] signatureCheckBytes) throws IOException {
        ByteOrderedDataInputStream signatureInputStream = null;
        try {
            signatureInputStream = new ByteOrderedDataInputStream(signatureCheckBytes);
            this.mExifByteOrder = readByteOrder(signatureInputStream);
            signatureInputStream.setByteOrder(this.mExifByteOrder);
            short orfSignature = signatureInputStream.readShort();
            boolean z = orfSignature == 20306 || orfSignature == 21330;
            signatureInputStream.close();
            return z;
        } catch (Exception e) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            return false;
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isRw2Format(byte[] signatureCheckBytes) throws IOException {
        ByteOrderedDataInputStream signatureInputStream = null;
        try {
            signatureInputStream = new ByteOrderedDataInputStream(signatureCheckBytes);
            this.mExifByteOrder = readByteOrder(signatureInputStream);
            signatureInputStream.setByteOrder(this.mExifByteOrder);
            short signatureByte = signatureInputStream.readShort();
            signatureInputStream.close();
            boolean z = signatureByte == 85;
            signatureInputStream.close();
            return z;
        } catch (Exception e) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            return false;
        } catch (Throwable th) {
            if (signatureInputStream != null) {
                signatureInputStream.close();
            }
            throw th;
        }
    }

    private boolean isPngFormat(byte[] signatureCheckBytes) throws IOException {
        for (int i = 0; i < PNG_SIGNATURE.length; i++) {
            if (signatureCheckBytes[i] != PNG_SIGNATURE[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isWebpFormat(byte[] signatureCheckBytes) throws IOException {
        for (int i = 0; i < WEBP_SIGNATURE_1.length; i++) {
            if (signatureCheckBytes[i] != WEBP_SIGNATURE_1[i]) {
                return false;
            }
        }
        for (int i2 = 0; i2 < WEBP_SIGNATURE_2.length; i2++) {
            if (signatureCheckBytes[WEBP_SIGNATURE_1.length + i2 + 4] != WEBP_SIGNATURE_2[i2]) {
                return false;
            }
        }
        return true;
    }

    private static boolean isExifDataOnly(BufferedInputStream in) throws IOException {
        in.mark(IDENTIFIER_EXIF_APP1.length);
        byte[] signatureCheckBytes = new byte[IDENTIFIER_EXIF_APP1.length];
        in.read(signatureCheckBytes);
        in.reset();
        for (int i = 0; i < IDENTIFIER_EXIF_APP1.length; i++) {
            if (signatureCheckBytes[i] != IDENTIFIER_EXIF_APP1[i]) {
                return false;
            }
        }
        return true;
    }

    private void getJpegAttributes(ByteOrderedDataInputStream in, int jpegOffset, int imageType) throws IOException {
        String str;
        int i;
        boolean z = DEBUG;
        String str2 = TAG;
        if (z) {
            Log.d(TAG, "getJpegAttributes starting with: " + in);
        }
        in.setByteOrder(ByteOrder.BIG_ENDIAN);
        in.seek(jpegOffset);
        byte marker = in.readByte();
        byte b = -1;
        if (marker != -1) {
            throw new IOException("Invalid marker: " + Integer.toHexString(marker & 255));
        }
        int i2 = 1;
        int bytesRead = jpegOffset + 1;
        if (in.readByte() != -40) {
            throw new IOException("Invalid marker: " + Integer.toHexString(marker & 255));
        }
        int bytesRead2 = bytesRead + 1;
        while (true) {
            byte marker2 = in.readByte();
            if (marker2 != b) {
                throw new IOException("Invalid marker:" + Integer.toHexString(marker2 & 255));
            }
            int bytesRead3 = bytesRead2 + 1;
            byte marker3 = in.readByte();
            if (DEBUG) {
                Log.d(str2, "Found JPEG segment indicator: " + Integer.toHexString(marker3 & 255));
            }
            int bytesRead4 = bytesRead3 + i2;
            if (marker3 != -39 && marker3 != -38) {
                int length = in.readUnsignedShort() - 2;
                int bytesRead5 = bytesRead4 + 2;
                if (DEBUG) {
                    Log.d(str2, "JPEG segment: " + Integer.toHexString(marker3 & 255) + " (length: " + (length + 2) + NavigationBarInflaterView.KEY_CODE_END);
                }
                if (length < 0) {
                    throw new IOException("Invalid length");
                }
                switch (marker3) {
                    case -64:
                    case -63:
                    case -62:
                    case -61:
                    case -59:
                    case -58:
                    case -57:
                    case -55:
                    case -54:
                    case -53:
                    case -51:
                    case -50:
                    case -49:
                        str = str2;
                        i = i2;
                        if (in.skipBytes(i) != i) {
                            throw new IOException("Invalid SOFx");
                        }
                        this.mAttributes[imageType].put(imageType != 4 ? TAG_IMAGE_LENGTH : TAG_THUMBNAIL_IMAGE_LENGTH, ExifAttribute.createULong(in.readUnsignedShort(), this.mExifByteOrder));
                        this.mAttributes[imageType].put(imageType != 4 ? TAG_IMAGE_WIDTH : TAG_THUMBNAIL_IMAGE_WIDTH, ExifAttribute.createULong(in.readUnsignedShort(), this.mExifByteOrder));
                        length -= 5;
                        break;
                    case -31:
                        byte[] bytes = new byte[length];
                        in.readFully(bytes);
                        bytesRead5 += length;
                        length = 0;
                        if (!ExifInterfaceUtils.startsWith(bytes, IDENTIFIER_EXIF_APP1)) {
                            if (ExifInterfaceUtils.startsWith(bytes, IDENTIFIER_XMP_APP1)) {
                                long offset = IDENTIFIER_XMP_APP1.length + bytesRead5;
                                byte[] value = Arrays.copyOfRange(bytes, IDENTIFIER_XMP_APP1.length, bytes.length);
                                if (getAttribute(TAG_XMP) == null) {
                                    str = str2;
                                    this.mAttributes[0].put(TAG_XMP, new ExifAttribute(1, value.length, offset, value));
                                    i = 1;
                                    this.mXmpIsFromSeparateMarker = true;
                                    break;
                                } else {
                                    str = str2;
                                    i = i2;
                                    break;
                                }
                            } else {
                                str = str2;
                                i = i2;
                                break;
                            }
                        } else {
                            long offset2 = IDENTIFIER_EXIF_APP1.length + bytesRead5;
                            byte[] value2 = Arrays.copyOfRange(bytes, IDENTIFIER_EXIF_APP1.length, bytes.length);
                            this.mExifOffset = (int) offset2;
                            readExifSegment(value2, imageType);
                            str = str2;
                            i = i2;
                            break;
                        }
                    case -2:
                        byte[] bytes2 = new byte[length];
                        if (in.read(bytes2) != length) {
                            throw new IOException("Invalid exif");
                        }
                        length = 0;
                        if (getAttribute(TAG_USER_COMMENT) == null) {
                            this.mAttributes[i2].put(TAG_USER_COMMENT, ExifAttribute.createString(new String(bytes2, ASCII)));
                        }
                        str = str2;
                        i = i2;
                        break;
                    default:
                        str = str2;
                        i = i2;
                        break;
                }
                if (length < 0) {
                    throw new IOException("Invalid length");
                }
                if (in.skipBytes(length) != length) {
                    throw new IOException("Invalid JPEG segment");
                }
                bytesRead2 = bytesRead5 + length;
                i2 = i;
                str2 = str;
                b = -1;
            }
        }
        in.setByteOrder(this.mExifByteOrder);
    }

    private void getRawAttributes(ByteOrderedDataInputStream in) throws IOException {
        ExifAttribute makerNoteAttribute;
        parseTiffHeaders(in, in.available());
        readImageFileDirectory(in, 0);
        updateImageSizeValues(in, 0);
        updateImageSizeValues(in, 5);
        updateImageSizeValues(in, 4);
        validateImages();
        if (this.mMimeType == 8 && (makerNoteAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE)) != null) {
            ByteOrderedDataInputStream makerNoteDataInputStream = new ByteOrderedDataInputStream(makerNoteAttribute.bytes);
            makerNoteDataInputStream.setByteOrder(this.mExifByteOrder);
            makerNoteDataInputStream.seek(6L);
            readImageFileDirectory(makerNoteDataInputStream, 9);
            ExifAttribute colorSpaceAttribute = (ExifAttribute) this.mAttributes[9].get(TAG_COLOR_SPACE);
            if (colorSpaceAttribute != null) {
                this.mAttributes[1].put(TAG_COLOR_SPACE, colorSpaceAttribute);
            }
        }
    }

    private void getRafAttributes(ByteOrderedDataInputStream in) throws IOException {
        in.skipBytes(84);
        byte[] jpegOffsetBytes = new byte[4];
        byte[] cfaHeaderOffsetBytes = new byte[4];
        in.read(jpegOffsetBytes);
        in.skipBytes(4);
        in.read(cfaHeaderOffsetBytes);
        int rafJpegOffset = ByteBuffer.wrap(jpegOffsetBytes).getInt();
        int rafCfaHeaderOffset = ByteBuffer.wrap(cfaHeaderOffsetBytes).getInt();
        getJpegAttributes(in, rafJpegOffset, 5);
        in.seek(rafCfaHeaderOffset);
        in.setByteOrder(ByteOrder.BIG_ENDIAN);
        int numberOfDirectoryEntry = in.readInt();
        if (DEBUG) {
            Log.d(TAG, "numberOfDirectoryEntry: " + numberOfDirectoryEntry);
        }
        for (int i = 0; i < numberOfDirectoryEntry; i++) {
            int tagNumber = in.readUnsignedShort();
            int numberOfBytes = in.readUnsignedShort();
            if (tagNumber != TAG_RAF_IMAGE_SIZE.number) {
                in.skipBytes(numberOfBytes);
            } else {
                int imageLength = in.readShort();
                int imageWidth = in.readShort();
                ExifAttribute imageLengthAttribute = ExifAttribute.createUShort(imageLength, this.mExifByteOrder);
                ExifAttribute imageWidthAttribute = ExifAttribute.createUShort(imageWidth, this.mExifByteOrder);
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, imageLengthAttribute);
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, imageWidthAttribute);
                if (DEBUG) {
                    Log.d(TAG, "Updated to length: " + imageLength + ", width: " + imageWidth);
                    return;
                }
                return;
            }
        }
    }

    private void getHeifAttributes(final ByteOrderedDataInputStream in) throws IOException {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(new MediaDataSource() { // from class: android.media.ExifInterface.1
                long mPosition;

                @Override // java.io.Closeable, java.lang.AutoCloseable
                public void close() throws IOException {
                }

                @Override // android.media.MediaDataSource
                public int readAt(long position, byte[] buffer, int offset, int size) throws IOException {
                    if (size == 0) {
                        return 0;
                    }
                    if (position < 0) {
                        return -1;
                    }
                    try {
                        if (this.mPosition != position) {
                            if (this.mPosition >= 0 && position >= this.mPosition + in.available()) {
                                return -1;
                            }
                            in.seek(position);
                            this.mPosition = position;
                        }
                        if (size > in.available()) {
                            size = in.available();
                        }
                        int bytesRead = in.read(buffer, offset, size);
                        if (bytesRead >= 0) {
                            this.mPosition += bytesRead;
                            return bytesRead;
                        }
                    } catch (IOException e) {
                    }
                    this.mPosition = -1L;
                    return -1;
                }

                @Override // android.media.MediaDataSource
                public long getSize() throws IOException {
                    return -1L;
                }
            });
            String exifOffsetStr = retriever.extractMetadata(33);
            String exifLengthStr = retriever.extractMetadata(34);
            String hasImage = retriever.extractMetadata(26);
            String hasVideo = retriever.extractMetadata(17);
            String width = null;
            String height = null;
            String rotation = null;
            if ("yes".equals(hasImage)) {
                width = retriever.extractMetadata(29);
                height = retriever.extractMetadata(30);
                rotation = retriever.extractMetadata(31);
            } else if ("yes".equals(hasVideo)) {
                width = retriever.extractMetadata(18);
                height = retriever.extractMetadata(19);
                rotation = retriever.extractMetadata(24);
            }
            if (width != null) {
                this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createUShort(Integer.parseInt(width), this.mExifByteOrder));
            }
            if (height != null) {
                this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createUShort(Integer.parseInt(height), this.mExifByteOrder));
            }
            if (rotation != null) {
                int orientation = 1;
                switch (Integer.parseInt(rotation)) {
                    case 90:
                        orientation = 6;
                        break;
                    case 180:
                        orientation = 3;
                        break;
                    case 270:
                        orientation = 8;
                        break;
                }
                this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(orientation, this.mExifByteOrder));
            }
            if (exifOffsetStr != null && exifLengthStr != null) {
                int offset = Integer.parseInt(exifOffsetStr);
                int length = Integer.parseInt(exifLengthStr);
                if (length <= 6) {
                    throw new IOException("Invalid exif length");
                }
                in.seek(offset);
                byte[] identifier = new byte[6];
                if (in.read(identifier) != 6) {
                    throw new IOException("Can't read identifier");
                }
                int offset2 = offset + 6;
                int length2 = length - 6;
                if (!Arrays.equals(identifier, IDENTIFIER_EXIF_APP1)) {
                    throw new IOException("Invalid identifier");
                }
                byte[] bytes = new byte[length2];
                if (in.read(bytes) != length2) {
                    throw new IOException("Can't read exif");
                }
                this.mExifOffset = offset2;
                readExifSegment(bytes, 0);
            }
            String xmpOffsetStr = retriever.extractMetadata(41);
            String xmpLengthStr = retriever.extractMetadata(42);
            if (xmpOffsetStr != null && xmpLengthStr != null) {
                int offset3 = Integer.parseInt(xmpOffsetStr);
                int length3 = Integer.parseInt(xmpLengthStr);
                in.seek(offset3);
                byte[] xmpBytes = new byte[length3];
                if (in.read(xmpBytes) != length3) {
                    throw new IOException("Failed to read XMP from HEIF");
                }
                if (getAttribute(TAG_XMP) == null) {
                    this.mAttributes[0].put(TAG_XMP, new ExifAttribute(1, xmpBytes.length, offset3, xmpBytes));
                }
            }
            if (DEBUG) {
                Log.d(TAG, "Heif meta: " + width + "x" + height + ", rotation " + rotation);
            }
        } finally {
            retriever.release();
        }
    }

    private void getStandaloneAttributes(ByteOrderedDataInputStream in) throws IOException {
        in.skipBytes(IDENTIFIER_EXIF_APP1.length);
        byte[] data = new byte[in.available()];
        in.readFully(data);
        this.mExifOffset = IDENTIFIER_EXIF_APP1.length;
        readExifSegment(data, 0);
    }

    private void getOrfAttributes(ByteOrderedDataInputStream in) throws IOException {
        getRawAttributes(in);
        ExifAttribute makerNoteAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_MAKER_NOTE);
        if (makerNoteAttribute != null) {
            ByteOrderedDataInputStream makerNoteDataInputStream = new ByteOrderedDataInputStream(makerNoteAttribute.bytes);
            makerNoteDataInputStream.setByteOrder(this.mExifByteOrder);
            byte[] makerNoteHeader1Bytes = new byte[ORF_MAKER_NOTE_HEADER_1.length];
            makerNoteDataInputStream.readFully(makerNoteHeader1Bytes);
            makerNoteDataInputStream.seek(0L);
            byte[] makerNoteHeader2Bytes = new byte[ORF_MAKER_NOTE_HEADER_2.length];
            makerNoteDataInputStream.readFully(makerNoteHeader2Bytes);
            if (Arrays.equals(makerNoteHeader1Bytes, ORF_MAKER_NOTE_HEADER_1)) {
                makerNoteDataInputStream.seek(8L);
            } else if (Arrays.equals(makerNoteHeader2Bytes, ORF_MAKER_NOTE_HEADER_2)) {
                makerNoteDataInputStream.seek(12L);
            }
            readImageFileDirectory(makerNoteDataInputStream, 6);
            ExifAttribute imageLengthAttribute = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_START);
            ExifAttribute bitsPerSampleAttribute = (ExifAttribute) this.mAttributes[7].get(TAG_ORF_PREVIEW_IMAGE_LENGTH);
            if (imageLengthAttribute != null && bitsPerSampleAttribute != null) {
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT, imageLengthAttribute);
                this.mAttributes[5].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, bitsPerSampleAttribute);
            }
            ExifAttribute aspectFrameAttribute = (ExifAttribute) this.mAttributes[8].get(TAG_ORF_ASPECT_FRAME);
            if (aspectFrameAttribute != null) {
                int[] iArr = new int[4];
                int[] aspectFrameValues = (int[]) aspectFrameAttribute.getValue(this.mExifByteOrder);
                if (aspectFrameValues[2] > aspectFrameValues[0] && aspectFrameValues[3] > aspectFrameValues[1]) {
                    int primaryImageWidth = (aspectFrameValues[2] - aspectFrameValues[0]) + 1;
                    int primaryImageLength = (aspectFrameValues[3] - aspectFrameValues[1]) + 1;
                    if (primaryImageWidth < primaryImageLength) {
                        int primaryImageWidth2 = primaryImageWidth + primaryImageLength;
                        primaryImageLength = primaryImageWidth2 - primaryImageLength;
                        primaryImageWidth = primaryImageWidth2 - primaryImageLength;
                    }
                    ExifAttribute primaryImageWidthAttribute = ExifAttribute.createUShort(primaryImageWidth, this.mExifByteOrder);
                    ExifAttribute primaryImageLengthAttribute = ExifAttribute.createUShort(primaryImageLength, this.mExifByteOrder);
                    this.mAttributes[0].put(TAG_IMAGE_WIDTH, primaryImageWidthAttribute);
                    this.mAttributes[0].put(TAG_IMAGE_LENGTH, primaryImageLengthAttribute);
                }
            }
        }
    }

    private void getRw2Attributes(ByteOrderedDataInputStream in) throws IOException {
        getRawAttributes(in);
        ExifAttribute jpgFromRawAttribute = (ExifAttribute) this.mAttributes[0].get(TAG_RW2_JPG_FROM_RAW);
        if (jpgFromRawAttribute != null) {
            getJpegAttributes(in, this.mRw2JpgFromRawOffset, 5);
        }
        ExifAttribute rw2IsoAttribute = (ExifAttribute) this.mAttributes[0].get(TAG_RW2_ISO);
        ExifAttribute exifIsoAttribute = (ExifAttribute) this.mAttributes[1].get("ISOSpeedRatings");
        if (rw2IsoAttribute != null && exifIsoAttribute == null) {
            this.mAttributes[1].put("ISOSpeedRatings", rw2IsoAttribute);
        }
    }

    private void getPngAttributes(ByteOrderedDataInputStream in) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "getPngAttributes starting with: " + in);
        }
        in.setByteOrder(ByteOrder.BIG_ENDIAN);
        in.skipBytes(PNG_SIGNATURE.length);
        int bytesRead = 0 + PNG_SIGNATURE.length;
        while (true) {
            try {
                int length = in.readInt();
                int bytesRead2 = bytesRead + 4;
                byte[] type = new byte[4];
                if (in.read(type) != type.length) {
                    throw new IOException("Encountered invalid length while parsing PNG chunktype");
                }
                int bytesRead3 = bytesRead2 + 4;
                if (bytesRead3 == 16 && !Arrays.equals(type, PNG_CHUNK_TYPE_IHDR)) {
                    throw new IOException("Encountered invalid PNG file--IHDR chunk should appearas the first chunk");
                }
                if (!Arrays.equals(type, PNG_CHUNK_TYPE_IEND)) {
                    if (Arrays.equals(type, PNG_CHUNK_TYPE_EXIF)) {
                        byte[] data = new byte[length];
                        if (in.read(data) != length) {
                            throw new IOException("Failed to read given length for given PNG chunk type: " + ExifInterfaceUtils.byteArrayToHexString(type));
                        }
                        int dataCrcValue = in.readInt();
                        CRC32 crc = new CRC32();
                        crc.update(type);
                        crc.update(data);
                        if (((int) crc.getValue()) != dataCrcValue) {
                            throw new IOException("Encountered invalid CRC value for PNG-EXIF chunk.\n recorded CRC value: " + dataCrcValue + ", calculated CRC value: " + crc.getValue());
                        }
                        this.mExifOffset = bytesRead3;
                        readExifSegment(data, 0);
                        validateImages();
                        return;
                    }
                    in.skipBytes(length + 4);
                    bytesRead = bytesRead3 + length + 4;
                } else {
                    return;
                }
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt PNG file.");
            }
        }
    }

    private void getWebpAttributes(ByteOrderedDataInputStream in) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "getWebpAttributes starting with: " + in);
        }
        in.setByteOrder(ByteOrder.LITTLE_ENDIAN);
        in.skipBytes(WEBP_SIGNATURE_1.length);
        int fileSize = in.readInt() + 8;
        int bytesRead = 8 + in.skipBytes(WEBP_SIGNATURE_2.length);
        while (true) {
            try {
                byte[] code = new byte[4];
                if (in.read(code) != code.length) {
                    throw new IOException("Encountered invalid length while parsing WebP chunktype");
                }
                int chunkSize = in.readInt();
                int bytesRead2 = bytesRead + 4 + 4;
                if (Arrays.equals(WEBP_CHUNK_TYPE_EXIF, code)) {
                    byte[] payload = new byte[chunkSize];
                    if (in.read(payload) != chunkSize) {
                        throw new IOException("Failed to read given length for given PNG chunk type: " + ExifInterfaceUtils.byteArrayToHexString(code));
                    }
                    this.mExifOffset = bytesRead2;
                    readExifSegment(payload, 0);
                    this.mExifOffset = bytesRead2;
                    return;
                }
                int chunkSize2 = chunkSize % 2 == 1 ? chunkSize + 1 : chunkSize;
                if (bytesRead2 + chunkSize2 != fileSize) {
                    if (bytesRead2 + chunkSize2 > fileSize) {
                        throw new IOException("Encountered WebP file with invalid chunk size");
                    }
                    int skipped = in.skipBytes(chunkSize2);
                    if (skipped != chunkSize2) {
                        throw new IOException("Encountered WebP file with invalid chunk size");
                    }
                    bytesRead = bytesRead2 + skipped;
                } else {
                    return;
                }
            } catch (EOFException e) {
                throw new IOException("Encountered corrupt WebP file.");
            }
        }
    }

    private void saveJpegAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "saveJpegAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + NavigationBarInflaterView.KEY_CODE_END);
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream dataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        if (dataInputStream.readByte() != -1) {
            throw new IOException("Invalid marker");
        }
        dataOutputStream.writeByte(-1);
        if (dataInputStream.readByte() != -40) {
            throw new IOException("Invalid marker");
        }
        dataOutputStream.writeByte(-40);
        ExifAttribute xmpAttribute = null;
        if (getAttribute(TAG_XMP) != null && this.mXmpIsFromSeparateMarker) {
            xmpAttribute = (ExifAttribute) this.mAttributes[0].remove(TAG_XMP);
        }
        dataOutputStream.writeByte(-1);
        dataOutputStream.writeByte(-31);
        writeExifSegment(dataOutputStream);
        if (xmpAttribute != null) {
            this.mAttributes[0].put(TAG_XMP, xmpAttribute);
        }
        byte[] bytes = new byte[4096];
        while (dataInputStream.readByte() == -1) {
            byte marker = dataInputStream.readByte();
            switch (marker) {
                case -39:
                case -38:
                    dataOutputStream.writeByte(-1);
                    dataOutputStream.writeByte(marker);
                    ExifInterfaceUtils.copy(dataInputStream, dataOutputStream);
                    return;
                case -31:
                    int length = dataInputStream.readUnsignedShort() - 2;
                    if (length < 0) {
                        throw new IOException("Invalid length");
                    }
                    byte[] identifier = new byte[6];
                    if (length >= 6) {
                        if (dataInputStream.read(identifier) != 6) {
                            throw new IOException("Invalid exif");
                        }
                        if (Arrays.equals(identifier, IDENTIFIER_EXIF_APP1)) {
                            if (dataInputStream.skipBytes(length - 6) != length - 6) {
                                throw new IOException("Invalid length");
                            }
                            break;
                        }
                    }
                    dataOutputStream.writeByte(-1);
                    dataOutputStream.writeByte(marker);
                    dataOutputStream.writeUnsignedShort(length + 2);
                    if (length >= 6) {
                        length -= 6;
                        dataOutputStream.write(identifier);
                    }
                    while (length > 0) {
                        int read = dataInputStream.read(bytes, 0, Math.min(length, bytes.length));
                        if (read >= 0) {
                            dataOutputStream.write(bytes, 0, read);
                            length -= read;
                        }
                    }
                    break;
                default:
                    dataOutputStream.writeByte(-1);
                    dataOutputStream.writeByte(marker);
                    int length2 = dataInputStream.readUnsignedShort();
                    dataOutputStream.writeUnsignedShort(length2);
                    int length3 = length2 - 2;
                    if (length3 < 0) {
                        throw new IOException("Invalid length");
                    }
                    while (length3 > 0) {
                        int read2 = dataInputStream.read(bytes, 0, Math.min(length3, bytes.length));
                        if (read2 >= 0) {
                            dataOutputStream.write(bytes, 0, read2);
                            length3 -= read2;
                        }
                    }
                    break;
            }
        }
        throw new IOException("Invalid marker");
    }

    private void savePngAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "savePngAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + NavigationBarInflaterView.KEY_CODE_END);
        }
        DataInputStream dataInputStream = new DataInputStream(inputStream);
        ByteOrderedDataOutputStream dataOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.BIG_ENDIAN);
        ExifInterfaceUtils.copy(dataInputStream, dataOutputStream, PNG_SIGNATURE.length);
        if (this.mExifOffset == 0) {
            int ihdrChunkLength = dataInputStream.readInt();
            dataOutputStream.writeInt(ihdrChunkLength);
            ExifInterfaceUtils.copy(dataInputStream, dataOutputStream, ihdrChunkLength + 4 + 4);
        } else {
            int copyLength = ((this.mExifOffset - PNG_SIGNATURE.length) - 4) - 4;
            ExifInterfaceUtils.copy(dataInputStream, dataOutputStream, copyLength);
            int exifChunkLength = dataInputStream.readInt();
            dataInputStream.skipBytes(exifChunkLength + 4 + 4);
        }
        ByteArrayOutputStream exifByteArrayOutputStream = new ByteArrayOutputStream();
        try {
            ByteOrderedDataOutputStream exifDataOutputStream = new ByteOrderedDataOutputStream(exifByteArrayOutputStream, ByteOrder.BIG_ENDIAN);
            writeExifSegment(exifDataOutputStream);
            byte[] exifBytes = ((ByteArrayOutputStream) exifDataOutputStream.mOutputStream).toByteArray();
            dataOutputStream.write(exifBytes);
            CRC32 crc = new CRC32();
            crc.update(exifBytes, 4, exifBytes.length - 4);
            dataOutputStream.writeInt((int) crc.getValue());
            exifByteArrayOutputStream.close();
            ExifInterfaceUtils.copy(dataInputStream, dataOutputStream);
        } catch (Throwable th) {
            try {
                exifByteArrayOutputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    private void saveWebpAttributes(InputStream inputStream, OutputStream outputStream) throws IOException {
        if (DEBUG) {
            Log.d(TAG, "saveWebpAttributes starting with (inputStream: " + inputStream + ", outputStream: " + outputStream + NavigationBarInflaterView.KEY_CODE_END);
        }
        ByteOrderedDataInputStream totalInputStream = new ByteOrderedDataInputStream(inputStream, ByteOrder.LITTLE_ENDIAN);
        ByteOrderedDataOutputStream totalOutputStream = new ByteOrderedDataOutputStream(outputStream, ByteOrder.LITTLE_ENDIAN);
        ExifInterfaceUtils.copy(totalInputStream, totalOutputStream, WEBP_SIGNATURE_1.length);
        totalInputStream.skipBytes(WEBP_SIGNATURE_2.length + 4);
        ByteArrayOutputStream nonHeaderByteArrayOutputStream = null;
        try {
            try {
                nonHeaderByteArrayOutputStream = new ByteArrayOutputStream();
                ByteOrderedDataOutputStream nonHeaderOutputStream = new ByteOrderedDataOutputStream(nonHeaderByteArrayOutputStream, ByteOrder.LITTLE_ENDIAN);
                if (this.mExifOffset != 0) {
                    int bytesRead = WEBP_SIGNATURE_1.length + 4 + WEBP_SIGNATURE_2.length;
                    ExifInterfaceUtils.copy(totalInputStream, nonHeaderOutputStream, ((this.mExifOffset - bytesRead) - 4) - 4);
                    totalInputStream.skipBytes(4);
                    int exifChunkLength = totalInputStream.readInt();
                    if (exifChunkLength % 2 != 0) {
                        exifChunkLength++;
                    }
                    totalInputStream.skipBytes(exifChunkLength);
                    writeExifSegment(nonHeaderOutputStream);
                } else {
                    byte[] firstChunkType = new byte[4];
                    if (totalInputStream.read(firstChunkType) != firstChunkType.length) {
                        throw new IOException("Encountered invalid length while parsing WebP chunk type");
                    }
                    if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8X)) {
                        int size = totalInputStream.readInt();
                        byte[] data = new byte[size % 2 == 1 ? size + 1 : size];
                        totalInputStream.read(data);
                        data[0] = (byte) (8 | data[0]);
                        boolean containsAnimation = ((data[0] >> 1) & 1) == 1;
                        nonHeaderOutputStream.write(WEBP_CHUNK_TYPE_VP8X);
                        nonHeaderOutputStream.writeInt(size);
                        nonHeaderOutputStream.write(data);
                        if (containsAnimation) {
                            copyChunksUpToGivenChunkType(totalInputStream, nonHeaderOutputStream, WEBP_CHUNK_TYPE_ANIM, null);
                            while (true) {
                                byte[] type = new byte[4];
                                inputStream.read(type);
                                if (!Arrays.equals(type, WEBP_CHUNK_TYPE_ANMF)) {
                                    break;
                                } else {
                                    copyWebPChunk(totalInputStream, nonHeaderOutputStream, type);
                                }
                            }
                            writeExifSegment(nonHeaderOutputStream);
                        } else {
                            copyChunksUpToGivenChunkType(totalInputStream, nonHeaderOutputStream, WEBP_CHUNK_TYPE_VP8, WEBP_CHUNK_TYPE_VP8L);
                            writeExifSegment(nonHeaderOutputStream);
                        }
                    } else if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8) || Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8L)) {
                        int size2 = totalInputStream.readInt();
                        int bytesToRead = size2;
                        if (size2 % 2 == 1) {
                            bytesToRead++;
                        }
                        int widthAndHeight = 0;
                        int width = 0;
                        int height = 0;
                        boolean alpha = false;
                        byte[] vp8Frame = new byte[3];
                        if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8)) {
                            totalInputStream.read(vp8Frame);
                            byte[] vp8Signature = new byte[3];
                            if (totalInputStream.read(vp8Signature) != vp8Signature.length || !Arrays.equals(WEBP_VP8_SIGNATURE, vp8Signature)) {
                                throw new IOException("Encountered error while checking VP8 signature");
                            }
                            widthAndHeight = totalInputStream.readInt();
                            width = (widthAndHeight << 18) >> 18;
                            height = (widthAndHeight << 2) >> 18;
                            bytesToRead -= (vp8Frame.length + vp8Signature.length) + 4;
                        } else if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8L)) {
                            byte vp8lSignature = totalInputStream.readByte();
                            if (vp8lSignature != 47) {
                                throw new IOException("Encountered error while checking VP8L signature");
                            }
                            widthAndHeight = totalInputStream.readInt();
                            boolean z = true;
                            width = ((widthAndHeight << 18) >> 18) + 1;
                            height = ((widthAndHeight << 4) >> 18) + 1;
                            if ((268435456 & widthAndHeight) == 0) {
                                z = false;
                            }
                            alpha = z;
                            bytesToRead -= 5;
                        }
                        nonHeaderOutputStream.write(WEBP_CHUNK_TYPE_VP8X);
                        nonHeaderOutputStream.writeInt(10);
                        byte[] data2 = new byte[10];
                        if (alpha) {
                            data2[0] = (byte) (data2[0] | 16);
                        }
                        data2[0] = (byte) (data2[0] | 8);
                        int width2 = width - 1;
                        int height2 = height - 1;
                        data2[4] = (byte) width2;
                        data2[5] = (byte) (width2 >> 8);
                        data2[6] = (byte) (width2 >> 16);
                        data2[7] = (byte) height2;
                        data2[8] = (byte) (height2 >> 8);
                        data2[9] = (byte) (height2 >> 16);
                        nonHeaderOutputStream.write(data2);
                        nonHeaderOutputStream.write(firstChunkType);
                        nonHeaderOutputStream.writeInt(size2);
                        if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8)) {
                            nonHeaderOutputStream.write(vp8Frame);
                            nonHeaderOutputStream.write(WEBP_VP8_SIGNATURE);
                            nonHeaderOutputStream.writeInt(widthAndHeight);
                        } else if (Arrays.equals(firstChunkType, WEBP_CHUNK_TYPE_VP8L)) {
                            nonHeaderOutputStream.write(47);
                            nonHeaderOutputStream.writeInt(widthAndHeight);
                        }
                        ExifInterfaceUtils.copy(totalInputStream, nonHeaderOutputStream, bytesToRead);
                        writeExifSegment(nonHeaderOutputStream);
                    }
                }
                ExifInterfaceUtils.copy(totalInputStream, nonHeaderOutputStream);
                totalOutputStream.writeInt(nonHeaderByteArrayOutputStream.size() + WEBP_SIGNATURE_2.length);
                totalOutputStream.write(WEBP_SIGNATURE_2);
                nonHeaderByteArrayOutputStream.writeTo(totalOutputStream);
            } catch (Exception e) {
                throw new IOException("Failed to save WebP file", e);
            }
        } finally {
            ExifInterfaceUtils.closeQuietly(nonHeaderByteArrayOutputStream);
        }
    }

    private void copyChunksUpToGivenChunkType(ByteOrderedDataInputStream inputStream, ByteOrderedDataOutputStream outputStream, byte[] firstGivenType, byte[] secondGivenType) throws IOException {
        while (true) {
            byte[] type = new byte[4];
            if (inputStream.read(type) != type.length) {
                throw new IOException("Encountered invalid length while copying WebP chunks up tochunk type " + new String(firstGivenType, ASCII) + (secondGivenType == null ? "" : " or " + new String(secondGivenType, ASCII)));
            }
            copyWebPChunk(inputStream, outputStream, type);
            if (!Arrays.equals(type, firstGivenType)) {
                if (secondGivenType != null && Arrays.equals(type, secondGivenType)) {
                    return;
                }
            } else {
                return;
            }
        }
    }

    private void copyWebPChunk(ByteOrderedDataInputStream inputStream, ByteOrderedDataOutputStream outputStream, byte[] type) throws IOException {
        int size = inputStream.readInt();
        outputStream.write(type);
        outputStream.writeInt(size);
        ExifInterfaceUtils.copy(inputStream, outputStream, size % 2 == 1 ? size + 1 : size);
    }

    private void readExifSegment(byte[] exifBytes, int imageType) throws IOException {
        ByteOrderedDataInputStream dataInputStream = new ByteOrderedDataInputStream(exifBytes);
        parseTiffHeaders(dataInputStream, exifBytes.length);
        readImageFileDirectory(dataInputStream, imageType);
    }

    private void addDefaultValuesForCompatibility() {
        String valueOfDateTimeOriginal = getAttribute(TAG_DATETIME_ORIGINAL);
        if (valueOfDateTimeOriginal != null && getAttribute(TAG_DATETIME) == null) {
            this.mAttributes[0].put(TAG_DATETIME, ExifAttribute.createString(valueOfDateTimeOriginal));
        }
        if (getAttribute(TAG_IMAGE_WIDTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_IMAGE_LENGTH) == null) {
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (getAttribute(TAG_ORIENTATION) == null) {
            this.mAttributes[0].put(TAG_ORIENTATION, ExifAttribute.createUShort(0, this.mExifByteOrder));
        }
        if (getAttribute(TAG_LIGHT_SOURCE) == null) {
            this.mAttributes[1].put(TAG_LIGHT_SOURCE, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
    }

    private ByteOrder readByteOrder(ByteOrderedDataInputStream dataInputStream) throws IOException {
        short byteOrder = dataInputStream.readShort();
        switch (byteOrder) {
            case 18761:
                if (DEBUG) {
                    Log.d(TAG, "readExifSegment: Byte Align II");
                }
                return ByteOrder.LITTLE_ENDIAN;
            case 19789:
                if (DEBUG) {
                    Log.d(TAG, "readExifSegment: Byte Align MM");
                }
                return ByteOrder.BIG_ENDIAN;
            default:
                throw new IOException("Invalid byte order: " + Integer.toHexString(byteOrder));
        }
    }

    private void parseTiffHeaders(ByteOrderedDataInputStream dataInputStream, int exifBytesLength) throws IOException {
        this.mExifByteOrder = readByteOrder(dataInputStream);
        dataInputStream.setByteOrder(this.mExifByteOrder);
        int startCode = dataInputStream.readUnsignedShort();
        if (this.mMimeType != 7 && this.mMimeType != 10 && startCode != 42) {
            throw new IOException("Invalid start code: " + Integer.toHexString(startCode));
        }
        int firstIfdOffset = dataInputStream.readInt();
        if (firstIfdOffset < 8 || firstIfdOffset >= exifBytesLength) {
            throw new IOException("Invalid first Ifd offset: " + firstIfdOffset);
        }
        int firstIfdOffset2 = firstIfdOffset - 8;
        if (firstIfdOffset2 > 0 && dataInputStream.skipBytes(firstIfdOffset2) != firstIfdOffset2) {
            throw new IOException("Couldn't jump to first Ifd: " + firstIfdOffset2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:32:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0155  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void readImageFileDirectory(android.media.ExifInterface.ByteOrderedDataInputStream r27, int r28) throws java.io.IOException {
        /*
            Method dump skipped, instructions count: 1064
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: android.media.ExifInterface.readImageFileDirectory(android.media.ExifInterface$ByteOrderedDataInputStream, int):void");
    }

    private void retrieveJpegImageSize(ByteOrderedDataInputStream in, int imageType) throws IOException {
        ExifAttribute jpegInterchangeFormatAttribute;
        ExifAttribute imageLengthAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_IMAGE_LENGTH);
        ExifAttribute imageWidthAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_IMAGE_WIDTH);
        if ((imageLengthAttribute == null || imageWidthAttribute == null) && (jpegInterchangeFormatAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_JPEG_INTERCHANGE_FORMAT)) != null) {
            int jpegInterchangeFormat = jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder);
            getJpegAttributes(in, jpegInterchangeFormat, imageType);
        }
    }

    private void setThumbnailData(ByteOrderedDataInputStream in) throws IOException {
        HashMap thumbnailData = this.mAttributes[4];
        ExifAttribute compressionAttribute = (ExifAttribute) thumbnailData.get(TAG_COMPRESSION);
        if (compressionAttribute != null) {
            this.mThumbnailCompression = compressionAttribute.getIntValue(this.mExifByteOrder);
            switch (this.mThumbnailCompression) {
                case 1:
                case 7:
                    if (isSupportedDataType(thumbnailData)) {
                        handleThumbnailFromStrips(in, thumbnailData);
                        break;
                    }
                    break;
                case 6:
                    handleThumbnailFromJfif(in, thumbnailData);
                    break;
            }
        }
        handleThumbnailFromJfif(in, thumbnailData);
    }

    private void handleThumbnailFromJfif(ByteOrderedDataInputStream in, HashMap thumbnailData) throws IOException {
        ExifAttribute jpegInterchangeFormatAttribute = (ExifAttribute) thumbnailData.get(TAG_JPEG_INTERCHANGE_FORMAT);
        ExifAttribute jpegInterchangeFormatLengthAttribute = (ExifAttribute) thumbnailData.get(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
        if (jpegInterchangeFormatAttribute != null && jpegInterchangeFormatLengthAttribute != null) {
            int thumbnailOffset = jpegInterchangeFormatAttribute.getIntValue(this.mExifByteOrder);
            int thumbnailLength = jpegInterchangeFormatLengthAttribute.getIntValue(this.mExifByteOrder);
            if (this.mMimeType == 7) {
                thumbnailOffset += this.mOrfMakerNoteOffset;
            }
            int thumbnailLength2 = Math.min(thumbnailLength, in.getLength() - thumbnailOffset);
            if (thumbnailOffset > 0 && thumbnailLength2 > 0) {
                this.mHasThumbnail = true;
                this.mThumbnailOffset = this.mExifOffset + thumbnailOffset;
                this.mThumbnailLength = thumbnailLength2;
                this.mThumbnailCompression = 6;
                if (this.mFilename == null && this.mAssetInputStream == null && this.mSeekableFileDescriptor == null) {
                    byte[] thumbnailBytes = new byte[this.mThumbnailLength];
                    in.seek(this.mThumbnailOffset);
                    in.readFully(thumbnailBytes);
                    this.mThumbnailBytes = thumbnailBytes;
                }
            }
            if (DEBUG) {
                Log.d(TAG, "Setting thumbnail attributes with offset: " + thumbnailOffset + ", length: " + thumbnailLength2);
            }
        }
    }

    private void handleThumbnailFromStrips(ByteOrderedDataInputStream in, HashMap thumbnailData) throws IOException {
        ExifAttribute stripOffsetsAttribute;
        ExifAttribute stripOffsetsAttribute2 = (ExifAttribute) thumbnailData.get(TAG_STRIP_OFFSETS);
        ExifAttribute stripByteCountsAttribute = (ExifAttribute) thumbnailData.get(TAG_STRIP_BYTE_COUNTS);
        if (stripOffsetsAttribute2 != null && stripByteCountsAttribute != null) {
            long[] stripOffsets = ExifInterfaceUtils.convertToLongArray(stripOffsetsAttribute2.getValue(this.mExifByteOrder));
            long[] stripByteCounts = ExifInterfaceUtils.convertToLongArray(stripByteCountsAttribute.getValue(this.mExifByteOrder));
            if (stripOffsets != null && stripOffsets.length != 0) {
                if (stripByteCounts != null && stripByteCounts.length != 0) {
                    if (stripOffsets.length != stripByteCounts.length) {
                        Log.w(TAG, "stripOffsets and stripByteCounts should have same length.");
                        return;
                    }
                    byte[] totalStripBytes = new byte[(int) Arrays.stream(stripByteCounts).sum()];
                    int bytesRead = 0;
                    int bytesAdded = 0;
                    int i = 1;
                    this.mAreThumbnailStripsConsecutive = true;
                    this.mHasThumbnailStrips = true;
                    this.mHasThumbnail = true;
                    int i2 = 0;
                    while (i2 < stripOffsets.length) {
                        int stripOffset = (int) stripOffsets[i2];
                        int stripByteCount = (int) stripByteCounts[i2];
                        if (i2 < stripOffsets.length - i) {
                            stripOffsetsAttribute = stripOffsetsAttribute2;
                            if (stripOffset + stripByteCount != stripOffsets[i2 + 1]) {
                                this.mAreThumbnailStripsConsecutive = false;
                            }
                        } else {
                            stripOffsetsAttribute = stripOffsetsAttribute2;
                        }
                        int skipBytes = stripOffset - bytesRead;
                        if (skipBytes < 0) {
                            Log.d(TAG, "Invalid strip offset value");
                        }
                        in.seek(skipBytes);
                        byte[] stripBytes = new byte[stripByteCount];
                        in.read(stripBytes);
                        bytesRead = bytesRead + skipBytes + stripByteCount;
                        System.arraycopy(stripBytes, 0, totalStripBytes, bytesAdded, stripBytes.length);
                        bytesAdded += stripBytes.length;
                        i2++;
                        stripOffsetsAttribute2 = stripOffsetsAttribute;
                        i = 1;
                    }
                    this.mThumbnailBytes = totalStripBytes;
                    if (this.mAreThumbnailStripsConsecutive) {
                        this.mThumbnailOffset = ((int) stripOffsets[0]) + this.mExifOffset;
                        this.mThumbnailLength = totalStripBytes.length;
                        return;
                    }
                    return;
                }
                Log.w(TAG, "stripByteCounts should not be null or have zero length.");
                return;
            }
            Log.w(TAG, "stripOffsets should not be null or have zero length.");
        }
    }

    private boolean isSupportedDataType(HashMap thumbnailData) throws IOException {
        ExifAttribute photometricInterpretationAttribute;
        int photometricInterpretationValue;
        ExifAttribute bitsPerSampleAttribute = (ExifAttribute) thumbnailData.get(TAG_BITS_PER_SAMPLE);
        if (bitsPerSampleAttribute != null) {
            int[] bitsPerSampleValue = (int[]) bitsPerSampleAttribute.getValue(this.mExifByteOrder);
            if (Arrays.equals(BITS_PER_SAMPLE_RGB, bitsPerSampleValue)) {
                return true;
            }
            if (this.mMimeType == 3 && (photometricInterpretationAttribute = (ExifAttribute) thumbnailData.get(TAG_PHOTOMETRIC_INTERPRETATION)) != null && (((photometricInterpretationValue = photometricInterpretationAttribute.getIntValue(this.mExifByteOrder)) == 1 && Arrays.equals(bitsPerSampleValue, BITS_PER_SAMPLE_GREYSCALE_2)) || (photometricInterpretationValue == 6 && Arrays.equals(bitsPerSampleValue, BITS_PER_SAMPLE_RGB)))) {
                return true;
            }
        }
        if (DEBUG) {
            Log.d(TAG, "Unsupported data type value");
            return false;
        }
        return false;
    }

    private boolean isThumbnail(HashMap map) throws IOException {
        ExifAttribute imageLengthAttribute = (ExifAttribute) map.get(TAG_IMAGE_LENGTH);
        ExifAttribute imageWidthAttribute = (ExifAttribute) map.get(TAG_IMAGE_WIDTH);
        if (imageLengthAttribute != null && imageWidthAttribute != null) {
            int imageLengthValue = imageLengthAttribute.getIntValue(this.mExifByteOrder);
            int imageWidthValue = imageWidthAttribute.getIntValue(this.mExifByteOrder);
            if (imageLengthValue <= 512 && imageWidthValue <= 512) {
                return true;
            }
            return false;
        }
        return false;
    }

    private void validateImages() throws IOException {
        swapBasedOnImageSize(0, 5);
        swapBasedOnImageSize(0, 4);
        swapBasedOnImageSize(5, 4);
        ExifAttribute pixelXDimAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_X_DIMENSION);
        ExifAttribute pixelYDimAttribute = (ExifAttribute) this.mAttributes[1].get(TAG_PIXEL_Y_DIMENSION);
        if (pixelXDimAttribute != null && pixelYDimAttribute != null) {
            this.mAttributes[0].put(TAG_IMAGE_WIDTH, pixelXDimAttribute);
            this.mAttributes[0].put(TAG_IMAGE_LENGTH, pixelYDimAttribute);
        }
        if (this.mAttributes[4].isEmpty() && isThumbnail(this.mAttributes[5])) {
            this.mAttributes[4] = this.mAttributes[5];
            this.mAttributes[5] = new HashMap();
        }
        if (!isThumbnail(this.mAttributes[4])) {
            Log.d(TAG, "No image meets the size requirements of a thumbnail image.");
        }
        replaceInvalidTags(0, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(0, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_ORIENTATION, TAG_ORIENTATION);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_LENGTH, TAG_IMAGE_LENGTH);
        replaceInvalidTags(5, TAG_THUMBNAIL_IMAGE_WIDTH, TAG_IMAGE_WIDTH);
        replaceInvalidTags(4, TAG_ORIENTATION, TAG_THUMBNAIL_ORIENTATION);
        replaceInvalidTags(4, TAG_IMAGE_LENGTH, TAG_THUMBNAIL_IMAGE_LENGTH);
        replaceInvalidTags(4, TAG_IMAGE_WIDTH, TAG_THUMBNAIL_IMAGE_WIDTH);
    }

    private void updateImageSizeValues(ByteOrderedDataInputStream in, int imageType) throws IOException {
        ExifAttribute defaultCropSizeXAttribute;
        ExifAttribute defaultCropSizeYAttribute;
        ExifAttribute defaultCropSizeAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_DEFAULT_CROP_SIZE);
        ExifAttribute topBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_TOP_BORDER);
        ExifAttribute leftBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_LEFT_BORDER);
        ExifAttribute bottomBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_BOTTOM_BORDER);
        ExifAttribute rightBorderAttribute = (ExifAttribute) this.mAttributes[imageType].get(TAG_RW2_SENSOR_RIGHT_BORDER);
        if (defaultCropSizeAttribute != null) {
            if (defaultCropSizeAttribute.format == 5) {
                Rational[] defaultCropSizeValue = (Rational[]) defaultCropSizeAttribute.getValue(this.mExifByteOrder);
                defaultCropSizeXAttribute = ExifAttribute.createURational(defaultCropSizeValue[0], this.mExifByteOrder);
                defaultCropSizeYAttribute = ExifAttribute.createURational(defaultCropSizeValue[1], this.mExifByteOrder);
            } else {
                int[] defaultCropSizeValue2 = (int[]) defaultCropSizeAttribute.getValue(this.mExifByteOrder);
                defaultCropSizeXAttribute = ExifAttribute.createUShort(defaultCropSizeValue2[0], this.mExifByteOrder);
                defaultCropSizeYAttribute = ExifAttribute.createUShort(defaultCropSizeValue2[1], this.mExifByteOrder);
            }
            this.mAttributes[imageType].put(TAG_IMAGE_WIDTH, defaultCropSizeXAttribute);
            this.mAttributes[imageType].put(TAG_IMAGE_LENGTH, defaultCropSizeYAttribute);
            return;
        }
        if (topBorderAttribute != null && leftBorderAttribute != null && bottomBorderAttribute != null && rightBorderAttribute != null) {
            int topBorderValue = topBorderAttribute.getIntValue(this.mExifByteOrder);
            int bottomBorderValue = bottomBorderAttribute.getIntValue(this.mExifByteOrder);
            int rightBorderValue = rightBorderAttribute.getIntValue(this.mExifByteOrder);
            int leftBorderValue = leftBorderAttribute.getIntValue(this.mExifByteOrder);
            if (bottomBorderValue > topBorderValue && rightBorderValue > leftBorderValue) {
                int length = bottomBorderValue - topBorderValue;
                int width = rightBorderValue - leftBorderValue;
                ExifAttribute imageLengthAttribute = ExifAttribute.createUShort(length, this.mExifByteOrder);
                ExifAttribute imageWidthAttribute = ExifAttribute.createUShort(width, this.mExifByteOrder);
                this.mAttributes[imageType].put(TAG_IMAGE_LENGTH, imageLengthAttribute);
                this.mAttributes[imageType].put(TAG_IMAGE_WIDTH, imageWidthAttribute);
                return;
            }
            return;
        }
        retrieveJpegImageSize(in, imageType);
    }

    private int writeExifSegment(ByteOrderedDataOutputStream dataOutputStream) throws IOException {
        int[] ifdOffsets = new int[EXIF_TAGS.length];
        int[] ifdDataSizes = new int[EXIF_TAGS.length];
        for (ExifTag tag : EXIF_POINTER_TAGS) {
            removeAttribute(tag.name);
        }
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                removeAttribute(TAG_STRIP_OFFSETS);
                removeAttribute(TAG_STRIP_BYTE_COUNTS);
            } else {
                removeAttribute(TAG_JPEG_INTERCHANGE_FORMAT);
                removeAttribute(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH);
            }
        }
        for (int ifdType = 0; ifdType < EXIF_TAGS.length; ifdType++) {
            for (Object obj : this.mAttributes[ifdType].entrySet().toArray()) {
                Map.Entry entry = (Map.Entry) obj;
                if (entry.getValue() == null) {
                    this.mAttributes[ifdType].remove(entry.getKey());
                }
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        int i = 2;
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(0L, this.mExifByteOrder));
        }
        int i2 = 4;
        if (this.mHasThumbnail) {
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put(TAG_STRIP_OFFSETS, ExifAttribute.createUShort(0, this.mExifByteOrder));
                this.mAttributes[4].put(TAG_STRIP_BYTE_COUNTS, ExifAttribute.createUShort(this.mThumbnailLength, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, ExifAttribute.createULong(0L, this.mExifByteOrder));
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT_LENGTH, ExifAttribute.createULong(this.mThumbnailLength, this.mExifByteOrder));
            }
        }
        for (int i3 = 0; i3 < EXIF_TAGS.length; i3++) {
            int sum = 0;
            Iterator it = this.mAttributes[i3].entrySet().iterator();
            while (it.hasNext()) {
                ExifAttribute exifAttribute = (ExifAttribute) ((Map.Entry) it.next()).getValue();
                int size = exifAttribute.size();
                if (size > 4) {
                    sum += size;
                }
            }
            ifdDataSizes[i3] = ifdDataSizes[i3] + sum;
        }
        int position = 8;
        for (int ifdType2 = 0; ifdType2 < EXIF_TAGS.length; ifdType2++) {
            if (!this.mAttributes[ifdType2].isEmpty()) {
                ifdOffsets[ifdType2] = position;
                position += (this.mAttributes[ifdType2].size() * 12) + 2 + 4 + ifdDataSizes[ifdType2];
            }
        }
        if (this.mHasThumbnail) {
            int thumbnailOffset = position;
            if (this.mHasThumbnailStrips) {
                this.mAttributes[4].put(TAG_STRIP_OFFSETS, ExifAttribute.createUShort(thumbnailOffset, this.mExifByteOrder));
            } else {
                this.mAttributes[4].put(TAG_JPEG_INTERCHANGE_FORMAT, ExifAttribute.createULong(thumbnailOffset, this.mExifByteOrder));
            }
            this.mThumbnailOffset = this.mExifOffset + thumbnailOffset;
            position += this.mThumbnailLength;
        }
        int totalSize = position;
        if (this.mMimeType == 4) {
            totalSize += 8;
        }
        if (DEBUG) {
            for (int i4 = 0; i4 < EXIF_TAGS.length; i4++) {
                Log.d(TAG, String.format("index: %d, offsets: %d, tag count: %d, data sizes: %d, total size: %d", Integer.valueOf(i4), Integer.valueOf(ifdOffsets[i4]), Integer.valueOf(this.mAttributes[i4].size()), Integer.valueOf(ifdDataSizes[i4]), Integer.valueOf(totalSize)));
            }
        }
        if (!this.mAttributes[1].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[1].name, ExifAttribute.createULong(ifdOffsets[1], this.mExifByteOrder));
        }
        if (!this.mAttributes[2].isEmpty()) {
            this.mAttributes[0].put(EXIF_POINTER_TAGS[2].name, ExifAttribute.createULong(ifdOffsets[2], this.mExifByteOrder));
        }
        if (!this.mAttributes[3].isEmpty()) {
            this.mAttributes[1].put(EXIF_POINTER_TAGS[3].name, ExifAttribute.createULong(ifdOffsets[3], this.mExifByteOrder));
        }
        switch (this.mMimeType) {
            case 4:
                dataOutputStream.writeUnsignedShort(totalSize);
                dataOutputStream.write(IDENTIFIER_EXIF_APP1);
                break;
            case 13:
                dataOutputStream.writeInt(totalSize);
                dataOutputStream.write(PNG_CHUNK_TYPE_EXIF);
                break;
            case 14:
                dataOutputStream.write(WEBP_CHUNK_TYPE_EXIF);
                dataOutputStream.writeInt(totalSize);
                break;
        }
        dataOutputStream.writeShort(this.mExifByteOrder == ByteOrder.BIG_ENDIAN ? BYTE_ALIGN_MM : BYTE_ALIGN_II);
        dataOutputStream.setByteOrder(this.mExifByteOrder);
        dataOutputStream.writeUnsignedShort(42);
        dataOutputStream.writeUnsignedInt(8L);
        int ifdType3 = 0;
        while (ifdType3 < EXIF_TAGS.length) {
            if (!this.mAttributes[ifdType3].isEmpty()) {
                dataOutputStream.writeUnsignedShort(this.mAttributes[ifdType3].size());
                int dataOffset = ifdOffsets[ifdType3] + i + (this.mAttributes[ifdType3].size() * 12) + i2;
                for (Map.Entry entry2 : this.mAttributes[ifdType3].entrySet()) {
                    ExifTag tag2 = (ExifTag) sExifTagMapsForWriting[ifdType3].get(entry2.getKey());
                    int tagNumber = tag2.number;
                    ExifAttribute attribute = (ExifAttribute) entry2.getValue();
                    int size2 = attribute.size();
                    dataOutputStream.writeUnsignedShort(tagNumber);
                    dataOutputStream.writeUnsignedShort(attribute.format);
                    dataOutputStream.writeInt(attribute.numberOfComponents);
                    if (size2 > i2) {
                        dataOutputStream.writeUnsignedInt(dataOffset);
                        dataOffset += size2;
                    } else {
                        dataOutputStream.write(attribute.bytes);
                        if (size2 < 4) {
                            int i5 = size2;
                            for (int i6 = 4; i5 < i6; i6 = 4) {
                                dataOutputStream.writeByte(0);
                                i5++;
                            }
                        }
                    }
                    i2 = 4;
                }
                if (ifdType3 == 0 && !this.mAttributes[4].isEmpty()) {
                    dataOutputStream.writeUnsignedInt(ifdOffsets[4]);
                } else {
                    dataOutputStream.writeUnsignedInt(0L);
                }
                Iterator it2 = this.mAttributes[ifdType3].entrySet().iterator();
                while (it2.hasNext()) {
                    ExifAttribute attribute2 = (ExifAttribute) ((Map.Entry) it2.next()).getValue();
                    if (attribute2.bytes.length > 4) {
                        dataOutputStream.write(attribute2.bytes, 0, attribute2.bytes.length);
                    }
                }
            }
            ifdType3++;
            i = 2;
            i2 = 4;
        }
        if (this.mHasThumbnail) {
            dataOutputStream.write(getThumbnailBytes());
        }
        if (this.mMimeType == 14 && totalSize % 2 == 1) {
            dataOutputStream.writeByte(0);
        }
        dataOutputStream.setByteOrder(ByteOrder.BIG_ENDIAN);
        return totalSize;
    }

    private static Pair<Integer, Integer> guessDataFormat(String entryValue) {
        if (entryValue.contains(",")) {
            String[] entryValues = entryValue.split(",");
            Pair<Integer, Integer> dataFormat = guessDataFormat(entryValues[0]);
            if (dataFormat.first.intValue() == 2) {
                return dataFormat;
            }
            for (int i = 1; i < entryValues.length; i++) {
                Pair<Integer, Integer> guessDataFormat = guessDataFormat(entryValues[i]);
                int first = -1;
                int second = -1;
                if (Objects.equals(guessDataFormat.first, dataFormat.first) || Objects.equals(guessDataFormat.second, dataFormat.first)) {
                    first = dataFormat.first.intValue();
                }
                if (dataFormat.second.intValue() != -1 && (Objects.equals(guessDataFormat.first, dataFormat.second) || Objects.equals(guessDataFormat.second, dataFormat.second))) {
                    second = dataFormat.second.intValue();
                }
                if (first == -1 && second == -1) {
                    return new Pair<>(2, -1);
                }
                if (first == -1) {
                    dataFormat = new Pair<>(Integer.valueOf(second), -1);
                } else if (second == -1) {
                    dataFormat = new Pair<>(Integer.valueOf(first), -1);
                }
            }
            return dataFormat;
        }
        if (entryValue.contains("/")) {
            String[] rationalNumber = entryValue.split("/");
            if (rationalNumber.length == 2) {
                try {
                    long numerator = (long) Double.parseDouble(rationalNumber[0]);
                    long denominator = (long) Double.parseDouble(rationalNumber[1]);
                    if (numerator >= 0 && denominator >= 0) {
                        if (numerator <= 2147483647L && denominator <= 2147483647L) {
                            return new Pair<>(10, 5);
                        }
                        return new Pair<>(5, -1);
                    }
                    return new Pair<>(10, -1);
                } catch (NumberFormatException e) {
                }
            }
            return new Pair<>(2, -1);
        }
        try {
            Long longValue = Long.valueOf(Long.parseLong(entryValue));
            if (longValue.longValue() >= 0 && longValue.longValue() <= 65535) {
                return new Pair<>(3, 4);
            }
            if (longValue.longValue() < 0) {
                return new Pair<>(9, -1);
            }
            return new Pair<>(4, -1);
        } catch (NumberFormatException e2) {
            try {
                Double.parseDouble(entryValue);
                return new Pair<>(12, -1);
            } catch (NumberFormatException e3) {
                return new Pair<>(2, -1);
            }
        }
    }

    private static class ByteOrderedDataInputStream extends InputStream implements DataInput {
        private ByteOrder mByteOrder;
        private DataInputStream mDataInputStream;
        private InputStream mInputStream;
        private final int mLength;
        private int mPosition;
        private static final ByteOrder LITTLE_ENDIAN = ByteOrder.LITTLE_ENDIAN;
        private static final ByteOrder BIG_ENDIAN = ByteOrder.BIG_ENDIAN;

        public ByteOrderedDataInputStream(InputStream in) throws IOException {
            this(in, ByteOrder.BIG_ENDIAN);
        }

        ByteOrderedDataInputStream(InputStream in, ByteOrder byteOrder) throws IOException {
            this.mByteOrder = ByteOrder.BIG_ENDIAN;
            this.mInputStream = in;
            this.mDataInputStream = new DataInputStream(in);
            this.mLength = this.mDataInputStream.available();
            this.mPosition = 0;
            this.mDataInputStream.mark(this.mLength);
            this.mByteOrder = byteOrder;
        }

        public ByteOrderedDataInputStream(byte[] bytes) throws IOException {
            this(new ByteArrayInputStream(bytes));
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        public void seek(long byteCount) throws IOException {
            if (this.mPosition > byteCount) {
                this.mPosition = 0;
                this.mDataInputStream.reset();
                this.mDataInputStream.mark(this.mLength);
            } else {
                byteCount -= this.mPosition;
            }
            if (skipBytes((int) byteCount) != ((int) byteCount)) {
                throw new IOException("Couldn't seek up to the byteCount");
            }
        }

        public int peek() {
            return this.mPosition;
        }

        @Override // java.io.InputStream
        public int available() throws IOException {
            return this.mDataInputStream.available();
        }

        @Override // java.io.InputStream
        public int read() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.read();
        }

        @Override // java.io.DataInput
        public int readUnsignedByte() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readUnsignedByte();
        }

        @Override // java.io.DataInput
        public String readLine() throws IOException {
            Log.d(ExifInterface.TAG, "Currently unsupported");
            return null;
        }

        @Override // java.io.DataInput
        public boolean readBoolean() throws IOException {
            this.mPosition++;
            return this.mDataInputStream.readBoolean();
        }

        @Override // java.io.DataInput
        public char readChar() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readChar();
        }

        @Override // java.io.DataInput
        public String readUTF() throws IOException {
            this.mPosition += 2;
            return this.mDataInputStream.readUTF();
        }

        @Override // java.io.DataInput
        public void readFully(byte[] buffer, int offset, int length) throws IOException {
            this.mPosition += length;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            if (this.mDataInputStream.read(buffer, offset, length) != length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public void readFully(byte[] buffer) throws IOException {
            this.mPosition += buffer.length;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            if (this.mDataInputStream.read(buffer, 0, buffer.length) != buffer.length) {
                throw new IOException("Couldn't read up to the length of buffer");
            }
        }

        @Override // java.io.DataInput
        public byte readByte() throws IOException {
            this.mPosition++;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            int ch = this.mDataInputStream.read();
            if (ch < 0) {
                throw new EOFException();
            }
            return (byte) ch;
        }

        @Override // java.io.DataInput
        public short readShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            int ch1 = this.mDataInputStream.read();
            int ch2 = this.mDataInputStream.read();
            if ((ch1 | ch2) < 0) {
                throw new EOFException();
            }
            if (this.mByteOrder == LITTLE_ENDIAN) {
                return (short) ((ch2 << 8) + ch1);
            }
            if (this.mByteOrder == BIG_ENDIAN) {
                return (short) ((ch1 << 8) + ch2);
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public int readInt() throws IOException {
            this.mPosition += 4;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            int ch1 = this.mDataInputStream.read();
            int ch2 = this.mDataInputStream.read();
            int ch3 = this.mDataInputStream.read();
            int ch4 = this.mDataInputStream.read();
            if ((ch1 | ch2 | ch3 | ch4) < 0) {
                throw new EOFException();
            }
            if (this.mByteOrder == LITTLE_ENDIAN) {
                return (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
            }
            if (this.mByteOrder == BIG_ENDIAN) {
                return (ch1 << 24) + (ch2 << 16) + (ch3 << 8) + ch4;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        @Override // java.io.DataInput
        public int skipBytes(int byteCount) throws IOException {
            int totalBytesToSkip = Math.min(byteCount, this.mLength - this.mPosition);
            int totalSkipped = 0;
            while (totalSkipped < totalBytesToSkip) {
                int skipped = this.mDataInputStream.skipBytes(totalBytesToSkip - totalSkipped);
                if (skipped <= 0) {
                    break;
                }
                totalSkipped += skipped;
            }
            this.mPosition += totalSkipped;
            return totalSkipped;
        }

        @Override // java.io.DataInput
        public int readUnsignedShort() throws IOException {
            this.mPosition += 2;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            int ch1 = this.mDataInputStream.read();
            int ch2 = this.mDataInputStream.read();
            if ((ch1 | ch2) < 0) {
                throw new EOFException();
            }
            if (this.mByteOrder == LITTLE_ENDIAN) {
                return (ch2 << 8) + ch1;
            }
            if (this.mByteOrder == BIG_ENDIAN) {
                return (ch1 << 8) + ch2;
            }
            throw new IOException("Invalid byte order: " + this.mByteOrder);
        }

        public long readUnsignedInt() throws IOException {
            return readInt() & 4294967295L;
        }

        @Override // java.io.DataInput
        public long readLong() throws IOException {
            this.mPosition += 8;
            if (this.mPosition > this.mLength) {
                throw new EOFException();
            }
            int ch1 = this.mDataInputStream.read();
            int ch2 = this.mDataInputStream.read();
            int ch3 = this.mDataInputStream.read();
            int ch4 = this.mDataInputStream.read();
            int ch5 = this.mDataInputStream.read();
            int ch6 = this.mDataInputStream.read();
            int ch7 = this.mDataInputStream.read();
            int ch8 = this.mDataInputStream.read();
            if ((ch1 | ch2 | ch3 | ch4 | ch5 | ch6 | ch7 | ch8) < 0) {
                throw new EOFException();
            }
            if (this.mByteOrder != LITTLE_ENDIAN) {
                if (this.mByteOrder == BIG_ENDIAN) {
                    return (ch1 << 56) + (ch2 << 48) + (ch3 << 40) + (ch4 << 32) + (ch5 << 24) + (ch6 << 16) + (ch7 << 8) + ch8;
                }
                throw new IOException("Invalid byte order: " + this.mByteOrder);
            }
            return (ch8 << 56) + (ch7 << 48) + (ch6 << 40) + (ch5 << 32) + (ch4 << 24) + (ch3 << 16) + (ch2 << 8) + ch1;
        }

        @Override // java.io.DataInput
        public float readFloat() throws IOException {
            return Float.intBitsToFloat(readInt());
        }

        @Override // java.io.DataInput
        public double readDouble() throws IOException {
            return Double.longBitsToDouble(readLong());
        }

        public int getLength() {
            return this.mLength;
        }
    }

    private static class ByteOrderedDataOutputStream extends FilterOutputStream {
        private ByteOrder mByteOrder;
        final OutputStream mOutputStream;

        public ByteOrderedDataOutputStream(OutputStream out, ByteOrder byteOrder) {
            super(out);
            this.mOutputStream = out;
            this.mByteOrder = byteOrder;
        }

        public void setByteOrder(ByteOrder byteOrder) {
            this.mByteOrder = byteOrder;
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bytes) throws IOException {
            this.mOutputStream.write(bytes);
        }

        @Override // java.io.FilterOutputStream, java.io.OutputStream
        public void write(byte[] bytes, int offset, int length) throws IOException {
            this.mOutputStream.write(bytes, offset, length);
        }

        public void writeByte(int val) throws IOException {
            this.mOutputStream.write(val);
        }

        public void writeShort(short val) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((val >>> 0) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
            } else if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 0) & 255);
            }
        }

        public void writeInt(int val) throws IOException {
            if (this.mByteOrder == ByteOrder.LITTLE_ENDIAN) {
                this.mOutputStream.write((val >>> 0) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 16) & 255);
                this.mOutputStream.write((val >>> 24) & 255);
                return;
            }
            if (this.mByteOrder == ByteOrder.BIG_ENDIAN) {
                this.mOutputStream.write((val >>> 24) & 255);
                this.mOutputStream.write((val >>> 16) & 255);
                this.mOutputStream.write((val >>> 8) & 255);
                this.mOutputStream.write((val >>> 0) & 255);
            }
        }

        public void writeUnsignedShort(int val) throws IOException {
            writeShort((short) val);
        }

        public void writeUnsignedInt(long val) throws IOException {
            writeInt((int) val);
        }
    }

    private void swapBasedOnImageSize(int firstIfdType, int secondIfdType) throws IOException {
        if (this.mAttributes[firstIfdType].isEmpty() || this.mAttributes[secondIfdType].isEmpty()) {
            if (DEBUG) {
                Log.d(TAG, "Cannot perform swap since only one image data exists");
                return;
            }
            return;
        }
        ExifAttribute firstImageLengthAttribute = (ExifAttribute) this.mAttributes[firstIfdType].get(TAG_IMAGE_LENGTH);
        ExifAttribute firstImageWidthAttribute = (ExifAttribute) this.mAttributes[firstIfdType].get(TAG_IMAGE_WIDTH);
        ExifAttribute secondImageLengthAttribute = (ExifAttribute) this.mAttributes[secondIfdType].get(TAG_IMAGE_LENGTH);
        ExifAttribute secondImageWidthAttribute = (ExifAttribute) this.mAttributes[secondIfdType].get(TAG_IMAGE_WIDTH);
        if (firstImageLengthAttribute == null || firstImageWidthAttribute == null) {
            if (DEBUG) {
                Log.d(TAG, "First image does not contain valid size information");
                return;
            }
            return;
        }
        if (secondImageLengthAttribute == null || secondImageWidthAttribute == null) {
            if (DEBUG) {
                Log.d(TAG, "Second image does not contain valid size information");
                return;
            }
            return;
        }
        int firstImageLengthValue = firstImageLengthAttribute.getIntValue(this.mExifByteOrder);
        int firstImageWidthValue = firstImageWidthAttribute.getIntValue(this.mExifByteOrder);
        int secondImageLengthValue = secondImageLengthAttribute.getIntValue(this.mExifByteOrder);
        int secondImageWidthValue = secondImageWidthAttribute.getIntValue(this.mExifByteOrder);
        if (firstImageLengthValue < secondImageLengthValue && firstImageWidthValue < secondImageWidthValue) {
            HashMap tempMap = this.mAttributes[firstIfdType];
            this.mAttributes[firstIfdType] = this.mAttributes[secondIfdType];
            this.mAttributes[secondIfdType] = tempMap;
        }
    }

    private void replaceInvalidTags(int ifdType, String invalidTag, String validTag) {
        if (!this.mAttributes[ifdType].isEmpty() && this.mAttributes[ifdType].get(invalidTag) != null) {
            this.mAttributes[ifdType].put(validTag, this.mAttributes[ifdType].get(invalidTag));
            this.mAttributes[ifdType].remove(invalidTag);
        }
    }

    private boolean isSupportedFormatForSavingAttributes() {
        if (!this.mIsSupportedFile) {
            return false;
        }
        if (this.mMimeType == 4 || this.mMimeType == 13 || this.mMimeType == 14) {
            return true;
        }
        return false;
    }
}
