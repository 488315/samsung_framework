package android.filterfw.core;

/* loaded from: classes.dex */
public abstract class Scheduler {
    private FilterGraph mGraph;

    abstract void reset();

    abstract Filter scheduleNextNode();

    Scheduler(FilterGraph graph) {
        this.mGraph = graph;
    }

    FilterGraph getGraph() {
        return this.mGraph;
    }

    boolean finished() {
        return true;
    }
}
