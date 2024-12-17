package com.android.server;

import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.function.Predicate;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class HeimdAllFsService$$ExternalSyntheticLambda2 implements Predicate {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Predicate
    public final boolean test(Object obj) {
        Path path = (Path) obj;
        switch (this.$r8$classId) {
            case 0:
                return Files.isRegularFile(path, new LinkOption[0]);
            case 1:
                return path.getFileName().toString().matches(".*\\.[ov]dex");
            case 2:
                return path.getFileName().toString().matches(".*\\.[ov]dex");
            case 3:
                return Files.isRegularFile(path, new LinkOption[0]);
            case 4:
                return !path.getFileName().toString().matches(".*\\.(zip|gz)$");
            case 5:
                return Files.isRegularFile(path, new LinkOption[0]);
            case 6:
                return path.getFileName().toString().matches(".*\\.[ov]dex");
            default:
                return Files.isRegularFile(path, new LinkOption[0]);
        }
    }
}
