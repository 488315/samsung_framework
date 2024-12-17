package com.android.server.pm;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public class DynamicCodeLoggingService extends JobService {
    public static final String TAG = DynamicCodeLoggingService.class.getName();
    public static final long IDLE_LOGGING_PERIOD_MILLIS = TimeUnit.DAYS.toMillis(1);
    public static final long AUDIT_WATCHING_PERIOD_MILLIS = TimeUnit.HOURS.toMillis(2);
    public static final Pattern EXECUTE_NATIVE_AUDIT_PATTERN = Pattern.compile(".*\\bavc: +granted +\\{ execute(?:_no_trans|) \\} .*\\bpath=(?:\"([^\" ]*)\"|([0-9A-F]+)) .*\\bscontext=u:r:untrusted_app(?:_25|_27)?:.*\\btcontext=u:object_r:app_data_file:.*\\btclass=file\\b.*");
    public volatile boolean mIdleLoggingStopRequested = false;
    public volatile boolean mAuditWatchingStopRequested = false;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class IdleLoggingThread extends Thread {
        public final /* synthetic */ int $r8$classId;
        public final JobParameters mParams;
        public final /* synthetic */ DynamicCodeLoggingService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public IdleLoggingThread(DynamicCodeLoggingService dynamicCodeLoggingService, JobParameters jobParameters, int i) {
            super("DynamicCodeLoggingService_IdleLoggingJob");
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = dynamicCodeLoggingService;
                    super("DynamicCodeLoggingService_AuditWatchingJob");
                    this.mParams = jobParameters;
                    break;
                default:
                    this.this$0 = dynamicCodeLoggingService;
                    this.mParams = jobParameters;
                    break;
            }
        }

        /* JADX WARN: Can't wrap try/catch for region: R(16:124|125|(1:127)(2:191|(1:193)(3:194|165|166))|128|129|(1:131)(6:170|171|172|173|174|175)|132|(1:134)(1:169)|135|(1:168)(1:139)|140|(6:143|(1:145)(5:153|154|155|156|157)|146|(3:148|149|150)(1:152)|151|141)|163|164|165|166) */
        /* JADX WARN: Code restructure failed: missing block: B:189:0x0284, code lost:
        
            r0 = e;
         */
        /* JADX WARN: Code restructure failed: missing block: B:190:0x0285, code lost:
        
            r21 = r8;
            r5 = "DynamicCodeLogger";
            r8 = r11;
            r1 = r12;
            r6 = r13;
            r2 = r14;
         */
        /* JADX WARN: Removed duplicated region for block: B:134:0x02a9  */
        /* JADX WARN: Removed duplicated region for block: B:143:0x02f8  */
        /* JADX WARN: Removed duplicated region for block: B:148:0x031b A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:152:0x02f2 A[SYNTHETIC] */
        /* JADX WARN: Removed duplicated region for block: B:169:0x02ac  */
        /* JADX WARN: Removed duplicated region for block: B:205:0x0205  */
        @Override // java.lang.Thread, java.lang.Runnable
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void run() {
            /*
                Method dump skipped, instructions count: 892
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.pm.DynamicCodeLoggingService.IdleLoggingThread.run():void");
        }
    }

    public static void schedule(Context context) {
        ComponentName componentName = new ComponentName("android", DynamicCodeLoggingService.class.getName());
        JobScheduler jobScheduler = (JobScheduler) context.getSystemService("jobscheduler");
        jobScheduler.schedule(new JobInfo.Builder(2030028, componentName).setRequiresDeviceIdle(true).setRequiresCharging(true).setPeriodic(IDLE_LOGGING_PERIOD_MILLIS).build());
        jobScheduler.schedule(new JobInfo.Builder(203142925, componentName).setRequiresDeviceIdle(true).setRequiresBatteryNotLow(true).setPeriodic(AUDIT_WATCHING_PERIOD_MILLIS).build());
    }

    @Override // android.app.job.JobService
    public final boolean onStartJob(JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        if (jobId == 2030028) {
            this.mIdleLoggingStopRequested = false;
            new IdleLoggingThread(this, jobParameters, 0).start();
            return true;
        }
        if (jobId != 203142925) {
            return false;
        }
        this.mAuditWatchingStopRequested = false;
        new IdleLoggingThread(this, jobParameters, 1).start();
        return true;
    }

    @Override // android.app.job.JobService
    public final boolean onStopJob(JobParameters jobParameters) {
        int jobId = jobParameters.getJobId();
        if (jobId == 2030028) {
            this.mIdleLoggingStopRequested = true;
            return true;
        }
        if (jobId != 203142925) {
            return false;
        }
        this.mAuditWatchingStopRequested = true;
        return true;
    }
}
