package android.media;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/* loaded from: classes2.dex */
public final class EncoderProfiles {
    private List<AudioProfile> audioProfiles;
    private int durationSecs;
    private int fileFormat;
    private List<VideoProfile> videoProfiles;

    public int getDefaultDurationSeconds() {
        return this.durationSecs;
    }

    public int getRecommendedFileFormat() {
        return this.fileFormat;
    }

    public static final class VideoProfile {
        public static final int HDR_DOLBY_VISION = 4;
        public static final int HDR_HDR10 = 2;
        public static final int HDR_HDR10PLUS = 3;
        public static final int HDR_HLG = 1;
        public static final int HDR_NONE = 0;
        public static final int YUV_420 = 0;
        public static final int YUV_422 = 1;
        public static final int YUV_444 = 2;
        private int bitDepth;
        private int bitrate;
        private int chromaSubsampling;
        private int codec;
        private int frameRate;
        private int hdrFormat;
        private int height;
        private int profile;
        private int width;

        @Retention(RetentionPolicy.SOURCE)
        public @interface ChromaSubsampling {
        }

        @Retention(RetentionPolicy.SOURCE)
        public @interface HdrFormat {
        }

        public int getCodec() {
            return this.codec;
        }

        public String getMediaType() {
            if (this.codec == 1) {
                return "video/3gpp";
            }
            if (this.codec == 2) {
                return "video/avc";
            }
            if (this.codec == 3) {
                return "video/mp4v-es";
            }
            if (this.codec == 4) {
                return MediaFormat.MIMETYPE_VIDEO_VP8;
            }
            if (this.codec == 5) {
                return "video/hevc";
            }
            if (this.codec == 6) {
                return MediaFormat.MIMETYPE_VIDEO_VP9;
            }
            if (this.codec == 7) {
                return MediaFormat.MIMETYPE_VIDEO_DOLBY_VISION;
            }
            if (this.codec == 8) {
                return MediaFormat.MIMETYPE_VIDEO_AV1;
            }
            throw new RuntimeException("Unknown codec");
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public int getFrameRate() {
            return this.frameRate;
        }

        public int getWidth() {
            return this.width;
        }

        public int getHeight() {
            return this.height;
        }

        public int getProfile() {
            return this.profile;
        }

        public int getBitDepth() {
            return this.bitDepth;
        }

        public int getChromaSubsampling() {
            return this.chromaSubsampling;
        }

        public int getHdrFormat() {
            return this.hdrFormat;
        }

        VideoProfile(int codec, int width, int height, int frameRate, int bitrate, int profile, int chromaSubsampling, int bitDepth, int hdrFormat) {
            this.codec = codec;
            this.width = width;
            this.height = height;
            this.frameRate = frameRate;
            this.bitrate = bitrate;
            this.profile = profile;
            this.chromaSubsampling = chromaSubsampling;
            this.bitDepth = bitDepth;
            this.hdrFormat = hdrFormat;
        }

        VideoProfile(int codec, int width, int height, int frameRate, int bitrate, int profile) {
            this(codec, width, height, frameRate, bitrate, profile, 0, 8, 0);
        }
    }

    public List<AudioProfile> getAudioProfiles() {
        return this.audioProfiles;
    }

    public List<VideoProfile> getVideoProfiles() {
        return this.videoProfiles;
    }

    public static final class AudioProfile {
        private int bitrate;
        private int channels;
        private int codec;
        private int profile;
        private int sampleRate;

        public int getCodec() {
            return this.codec;
        }

        public String getMediaType() {
            if (this.codec == 1) {
                return "audio/3gpp";
            }
            if (this.codec == 2) {
                return "audio/amr-wb";
            }
            if (this.codec == 3 || this.codec == 4 || this.codec == 5) {
                return "audio/mp4a-latm";
            }
            if (this.codec == 6) {
                return MediaFormat.MIMETYPE_AUDIO_VORBIS;
            }
            if (this.codec == 7) {
                return MediaFormat.MIMETYPE_AUDIO_OPUS;
            }
            throw new RuntimeException("Unknown codec");
        }

        public int getBitrate() {
            return this.bitrate;
        }

        public int getSampleRate() {
            return this.sampleRate;
        }

        public int getChannels() {
            return this.channels;
        }

        public int getProfile() {
            if (this.codec == 3) {
                return 1;
            }
            if (this.codec == 4) {
                return 5;
            }
            if (this.codec == 5) {
                return 39;
            }
            return this.profile;
        }

        AudioProfile(int codec, int channels, int sampleRate, int bitrate, int profile) {
            this.codec = codec;
            this.channels = channels;
            this.sampleRate = sampleRate;
            this.bitrate = bitrate;
            this.profile = profile;
        }
    }

    EncoderProfiles(int duration, int fileFormat, VideoProfile[] videoProfiles, AudioProfile[] audioProfiles) {
        this.durationSecs = duration;
        this.fileFormat = fileFormat;
        this.videoProfiles = Collections.unmodifiableList(Arrays.asList(videoProfiles));
        this.audioProfiles = Collections.unmodifiableList(Arrays.asList(audioProfiles));
    }
}
