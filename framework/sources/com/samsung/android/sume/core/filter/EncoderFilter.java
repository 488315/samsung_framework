package com.samsung.android.sume.core.filter;

import android.app.job.JobInfo;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaCrypto;
import android.media.MediaFormat;
import android.util.Log;
import android.util.Pair;
import android.view.Surface;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.channel.BufferChannel;
import com.samsung.android.sume.core.channel.SurfaceChannel;
import com.samsung.android.sume.core.descriptor.CodecDescriptor;
import com.samsung.android.sume.core.exception.StreamFilterExitException;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.types.MediaType;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
public class EncoderFilter extends MediaCodecFilter {
    private static final String TAG = Def.tagOf((Class<?>) EncoderFilter.class);
    private int orientation;

    public EncoderFilter(CodecDescriptor codecDescriptor) {
        super(codecDescriptor);
        this.orientation = 0;
    }

    @Override // com.samsung.android.sume.core.filter.MediaCodecFilter
    protected void configCodec(final Message configData) {
        String str;
        MediaFormat mediaFormat;
        String str2 = TAG;
        Log.d(str2, "configCodec: " + configData);
        CodecDescriptor descriptor = (CodecDescriptor) getDescriptor();
        String mimeType = (String) configData.get(MediaFormat.KEY_MIME);
        if (descriptor.getMimeType() != null) {
            mimeType = descriptor.getMimeType();
        }
        int bitrate = descriptor.getBitrate();
        if (bitrate != 0) {
            str = MediaFormat.KEY_BIT_RATE;
        } else {
            bitrate = ((Integer) configData.get(MediaFormat.KEY_BIT_RATE)).intValue();
            if (descriptor.getScale() == 0.0f) {
                str = MediaFormat.KEY_BIT_RATE;
            } else {
                double scale = descriptor.getScale();
                str = MediaFormat.KEY_BIT_RATE;
                bitrate = (int) (bitrate * Math.pow(10.0d, (int) Math.log10(Math.pow(scale, 2.0d))));
            }
        }
        MediaType mediaType = descriptor.getMediaType();
        try {
            if (mediaType.isVideo()) {
                Pair<Integer, Integer> dimension = (Pair) Optional.ofNullable(descriptor.getRectSize()).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.filter.EncoderFilter$$ExternalSyntheticLambda0
                    @Override // java.util.function.Supplier
                    public final Object get() {
                        return EncoderFilter.lambda$configCodec$0(Message.this);
                    }
                });
                int width = dimension.first.intValue();
                int height = dimension.second.intValue();
                if (descriptor.getScale() != 0.0f) {
                    width = (int) (width * descriptor.getScale());
                    height = (int) (height * descriptor.getScale());
                }
                mediaFormat = MediaFormat.createVideoFormat(mimeType, width, height);
                mediaFormat.setInteger(MediaFormat.KEY_COLOR_FORMAT, MediaCodecInfo.CodecCapabilities.COLOR_FormatSurface);
                mediaFormat.setInteger(MediaFormat.KEY_FRAME_RATE, ((Integer) configData.get(MediaFormat.KEY_FRAME_RATE)).intValue());
                mediaFormat.setInteger(MediaFormat.KEY_I_FRAME_INTERVAL, ((Integer) configData.get(MediaFormat.KEY_I_FRAME_INTERVAL)).intValue());
                mediaFormat.setInteger("vendor.qti-ext-enc-linear-color-format.value", 1);
                if (configData.contains("rotation-degrees")) {
                    int intValue = ((Integer) configData.get("rotation-degrees")).intValue();
                    this.orientation = intValue;
                    mediaFormat.setInteger("rotation-degrees", intValue);
                }
            } else if (mediaType.isAudio()) {
                int sampleRate = ((Integer) configData.get(MediaFormat.KEY_SAMPLE_RATE)).intValue();
                int channelCount = ((Integer) configData.get(MediaFormat.KEY_CHANNEL_COUNT)).intValue();
                mediaFormat = MediaFormat.createAudioFormat(mimeType, sampleRate, channelCount);
            } else {
                throw new UnsupportedOperationException("not supported type" + mediaType);
            }
            mediaFormat.setInteger(str, bitrate);
            Log.d(str2, "media-format=" + mediaFormat);
            this.mediaCodec = MediaCodec.createEncoderByType(mimeType);
            this.mediaCodec.configure(mediaFormat, (Surface) null, (MediaCrypto) null, 1);
            BufferChannel inputChannel = this.receiveChannelQuery.apply(mediaType);
            if (inputChannel instanceof SurfaceChannel) {
                ((SurfaceChannel) inputChannel).configure(this.mediaCodec.createInputSurface());
            }
            this.mediaCodec.start();
            signalCodecFromReady();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /* JADX INFO: Access modifiers changed from: package-private */
    public static /* synthetic */ Pair lambda$configCodec$0(Message configData) {
        return new Pair((Integer) configData.get("width"), (Integer) configData.get("height"));
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        CodecDescriptor descriptor;
        MediaType mediaType;
        CodecDescriptor descriptor2;
        Log.d(TAG, "run");
        awaitCodecToReady();
        if (this.mediaCodec == null) {
            ibuf.release();
            throw new StreamFilterExitException("no media-codec given, might be released");
        }
        CodecDescriptor descriptor3 = (CodecDescriptor) getDescriptor();
        MediaType mediaType2 = descriptor3.getMediaType();
        BufferChannel inputChannel = this.receiveChannelQuery.apply(mediaType2);
        BufferChannel outputChannel = this.sendChannelQuery.apply(mediaType2);
        AtomicInteger trackIndex = new AtomicInteger();
        this.reachedInputEos = inputChannel instanceof SurfaceChannel;
        this.reachedOutputEos = false;
        this.processedFrames = 0;
        String tag = "[enc: " + this.mediaCodec.getCodecInfo().getCanonicalName() + NavigationBarInflaterView.SIZE_MOD_END;
        MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
        boolean ready = false;
        while (true) {
            boolean ready2 = this.reachedInputEos;
            if (!ready2 || !this.reachedOutputEos) {
                this.cvPause.block();
                if (!this.reachedInputEos && ready) {
                    MediaBuffer mediaBuffer = inputChannel.receive();
                    String str = TAG;
                    Log.d(str, "[bhko] buffer=" + mediaBuffer);
                    int bufferIdx = this.mediaCodec.dequeueInputBuffer(JobInfo.MIN_BACKOFF_MILLIS);
                    Log.d(str, tag + "dequeue input buffer: " + bufferIdx);
                    if (bufferIdx >= 0) {
                        if (mediaBuffer.containsExtra("reached-eos")) {
                            this.mediaCodec.queueInputBuffer(bufferIdx, 0, 0, 0L, 4);
                            this.reachedInputEos = true;
                        } else {
                            try {
                                Thread.sleep(30L);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            if (inputChannel.isClosedForReceive()) {
                                throw new CancellationException("input channel is already closed");
                            }
                            ByteBuffer inputBuffer = this.mediaCodec.getInputBuffer(bufferIdx);
                            ByteBuffer data = (ByteBuffer) mediaBuffer.getTypedData(ByteBuffer.class);
                            data.rewind();
                            inputBuffer.put(data);
                            long timeUs = ((Long) mediaBuffer.getExtra("timestampUs", 0L)).longValue();
                            this.mediaCodec.queueInputBuffer(bufferIdx, 0, inputBuffer.limit(), timeUs, 0);
                        }
                    }
                    mediaBuffer.release();
                }
                String str2 = TAG;
                Log.d(str2, tag + "dequeue output buffer");
                int status = this.mediaCodec.dequeueOutputBuffer(bufferInfo, JobInfo.MIN_BACKOFF_MILLIS);
                Log.d(str2, tag + "buffer st=" + status + ", info=" + bufferInfo);
                if (status == -1) {
                    Log.d(str2, tag + "retry dequeue output buffer");
                    descriptor = descriptor3;
                    mediaType = mediaType2;
                    descriptor2 = null;
                } else if (status == -2) {
                    Log.d(str2, tag + "track format = " + this.mediaCodec.getOutputFormat());
                    Map<String, Object> data2 = new HashMap<>();
                    data2.put(Message.KEY_MEDIA_TYPE, mediaType2);
                    MediaFormat mediaFormat = this.mediaCodec.getOutputFormat();
                    int i = this.orientation;
                    if (i != 0) {
                        mediaFormat.setInteger("rotation-degrees", i);
                    }
                    data2.put("media-format", mediaFormat);
                    this.messageProducer.newMessage(3, data2).post();
                    Log.d(str2, tag + "now ready to start encode");
                    descriptor = descriptor3;
                    mediaType = mediaType2;
                    descriptor2 = null;
                } else if (status < 0) {
                    descriptor = descriptor3;
                    mediaType = mediaType2;
                    descriptor2 = null;
                } else {
                    ByteBuffer outputBuffer = this.mediaCodec.getOutputBuffer(status);
                    MediaBuffer mediaBuffer2 = MediaBuffer.of(mediaType2, outputBuffer);
                    mediaBuffer2.setExtra("track-idx", Integer.valueOf(trackIndex.get()));
                    mediaBuffer2.setExtra("buffer-info", bufferInfo);
                    Log.d(str2, "flag=" + Integer.toHexString(bufferInfo.flags));
                    if ((bufferInfo.flags & 2) != 0) {
                        ready = true;
                        bufferInfo.size = 0;
                        mediaBuffer2.release();
                    }
                    Log.d(str2, "size=" + bufferInfo.size);
                    if (bufferInfo.size != 0) {
                        this.processedFrames++;
                        descriptor = descriptor3;
                        mediaType = mediaType2;
                        Log.d(str2, tag + "# of encoded frames: " + this.processedFrames + NavigationBarInflaterView.SIZE_MOD_START + bufferInfo.presentationTimeUs + "](" + Integer.toHexString(bufferInfo.flags) + NavigationBarInflaterView.KEY_CODE_END);
                        Log.d(str2, tag + "total # :" + this.numWholeFrames.get() + ", last ts: " + this.lastTimestampUs.get());
                        if ((inputChannel instanceof SurfaceChannel) && (isReachedLastFrame(this.processedFrames) || isReachedLastTimestamp(bufferInfo.presentationTimeUs))) {
                            bufferInfo.flags |= 4;
                            this.lastTimestampUs.set(Long.MAX_VALUE);
                        }
                        outputBuffer.position(bufferInfo.offset);
                        outputBuffer.limit(bufferInfo.offset + bufferInfo.size);
                        outputChannel.send(mediaBuffer2);
                    } else {
                        descriptor = descriptor3;
                        mediaType = mediaType2;
                    }
                    if ((bufferInfo.flags & 4) != 0) {
                        Log.i(str2, tag + "encoder reached eos");
                        this.reachedOutputEos = true;
                        if (!(inputChannel instanceof SurfaceChannel)) {
                            outputChannel.send(mediaBuffer2);
                        }
                    }
                    descriptor2 = null;
                    this.mediaCodec.releaseOutputBuffer(status, false);
                }
                descriptor3 = descriptor;
                mediaType2 = mediaType;
            } else {
                if (descriptor3.isRunInstant()) {
                    release();
                }
                return obuf;
            }
        }
    }

    private boolean isReachedLastFrame(int processedFrames) {
        return this.numWholeFrames.get() == processedFrames;
    }

    private boolean isReachedLastTimestamp(long timestampUs) {
        return this.lastTimestampUs.get() <= timestampUs;
    }
}