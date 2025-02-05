package com.sec.internal.log;

import com.sec.internal.imscr.LogClass;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* loaded from: classes.dex */
public class CmcPingTestLogger {
    private static final String GOOGLE_PUBLIC_NAMESERVER = "8.8.8.8";
    private static String LOG_TAG = "CmcPingTestLogger";
    private static final int MAX_PING_COUNT = 3;
    private static final int PING_TIMEOUT_SECONDS = 5;
    private static final Map<String, String> PingServer = new HashMap<String, String>() { // from class: com.sec.internal.log.CmcPingTestLogger.1
        {
            put("ec1", "3.127.55.209");
            put("ase1", "18.140.41.245");
            put("ue1", "3.89.177.225");
            put("ane2", "13.124.244.70");
        }
    };

    public interface OnFinishListener {
        void OnFinish(int i);
    }

    public static void ping(List<String> list) {
        ping(list, null);
    }

    public static void ping(final List<String> list, final OnFinishListener onFinishListener) {
        new Thread(new Runnable() { // from class: com.sec.internal.log.CmcPingTestLogger$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CmcPingTestLogger.lambda$ping$0(list, onFinishListener);
            }
        }).start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ void lambda$ping$0(List list, OnFinishListener onFinishListener) {
        try {
            CountDownLatch countDownLatch = new CountDownLatch(2);
            StringBuilder[] sbArr = new StringBuilder[2];
            for (int i = 0; i < 2; i++) {
                sbArr[i] = new StringBuilder();
            }
            new PingThread(GOOGLE_PUBLIC_NAMESERVER, sbArr[0], countDownLatch).start();
            if (list != null && list.size() > 0) {
                String[] split = ((String) list.get(0)).split("[-\\.]");
                if (split.length == 5) {
                    new PingThread(PingServer.get(split[2]), sbArr[1], countDownLatch).start();
                }
            }
            countDownLatch.await(10L, TimeUnit.SECONDS);
            for (int i2 = 0; i2 < 2; i2++) {
                IMSLog.c(LogClass.CMC_PCSCF_PING_TEST, makePingLog(sbArr[i2].toString()));
            }
            if (onFinishListener != null) {
                onFinishListener.OnFinish(2);
            }
        } catch (Exception e) {
            IMSLog.e(LOG_TAG, e.getMessage());
        }
    }

    private static String makePingLog(String str) {
        Scanner scanner = new Scanner(str);
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            String nextLine = scanner.nextLine();
            if (nextLine.startsWith("PING")) {
                sb.append(nextLine.split(" ")[1]);
            } else {
                if (nextLine.contains("packets transmitted")) {
                    String[] split = nextLine.split(" ");
                    sb.append(" ");
                    sb.append(split[0]);
                    sb.append("/");
                    sb.append(split[3]);
                } else if (nextLine.startsWith("rtt")) {
                    String[] split2 = nextLine.split(" ")[3].split("/");
                    for (int i = 0; i < 3; i++) {
                        sb.append("/");
                        sb.append(split2[i]);
                    }
                }
            }
        }
        return sb.toString();
    }

    private static class PingThread extends Thread {
        CountDownLatch countDownLatch;
        String inetAddr;
        StringBuilder outputBuffer;

        public PingThread(String str, StringBuilder sb, CountDownLatch countDownLatch) {
            this.inetAddr = str;
            this.outputBuffer = sb;
            this.countDownLatch = countDownLatch;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            try {
                Process exec = Runtime.getRuntime().exec(String.format("ping -c %d -W %d %s", 3, 5, this.inetAddr));
                try {
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(exec.getInputStream(), "UTF-8"));
                    try {
                        exec.waitFor();
                        while (true) {
                            String readLine = bufferedReader.readLine();
                            if (readLine == null) {
                                break;
                            }
                            if (readLine.length() > 0) {
                                StringBuilder sb = this.outputBuffer;
                                sb.append(readLine);
                                sb.append("\n");
                            }
                        }
                        bufferedReader.close();
                    } finally {
                    }
                } catch (Exception e) {
                    IMSLog.e(CmcPingTestLogger.LOG_TAG, e.getMessage());
                }
            } catch (Exception e2) {
                IMSLog.e(CmcPingTestLogger.LOG_TAG, e2.getMessage());
            }
            this.countDownLatch.countDown();
        }
    }
}
