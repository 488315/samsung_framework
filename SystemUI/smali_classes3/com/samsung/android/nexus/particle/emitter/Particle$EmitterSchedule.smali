.class public final Lcom/samsung/android/nexus/particle/emitter/Particle$EmitterSchedule;
.super Ljava/lang/Object;
.source "qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f"


# instance fields
.field public final emitter:Lcom/samsung/android/nexus/particle/emitter/Emitter;

.field public nextTime:J


# direct methods
.method public constructor <init>(Lcom/samsung/android/nexus/particle/emitter/Particle;Lcom/samsung/android/nexus/particle/emitter/Emitter;J)V
    .locals 0

    .line 1
    invoke-direct {p0}, Ljava/lang/Object;-><init>()V

    .line 2
    .line 3
    .line 4
    iput-object p2, p0, Lcom/samsung/android/nexus/particle/emitter/Particle$EmitterSchedule;->emitter:Lcom/samsung/android/nexus/particle/emitter/Emitter;

    .line 5
    .line 6
    iput-wide p3, p0, Lcom/samsung/android/nexus/particle/emitter/Particle$EmitterSchedule;->nextTime:J

    .line 7
    .line 8
    return-void
.end method