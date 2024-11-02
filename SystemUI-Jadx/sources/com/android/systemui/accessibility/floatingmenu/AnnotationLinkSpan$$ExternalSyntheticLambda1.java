package com.android.systemui.accessibility.floatingmenu;

import android.text.Annotation;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.view.View;
import com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

/* compiled from: qb/87000731 7862a37e62df4d72b2921859baacdc80ea0c935793521606c8e11db53cc87e4f */
/* loaded from: classes.dex */
public final /* synthetic */ class AnnotationLinkSpan$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ SpannableStringBuilder f$1;
    public final /* synthetic */ SpannableString f$2;

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda1(SpannableStringBuilder spannableStringBuilder, SpannableString spannableString, Annotation annotation) {
        this.f$1 = spannableStringBuilder;
        this.f$2 = spannableString;
        this.f$0 = annotation;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                AnnotationLinkSpan.LinkInfo[] linkInfoArr = (AnnotationLinkSpan.LinkInfo[]) this.f$0;
                SpannableStringBuilder spannableStringBuilder = this.f$1;
                SpannableString spannableString = this.f$2;
                Annotation annotation = (Annotation) obj;
                final String value = annotation.getValue();
                Arrays.asList(linkInfoArr).stream().filter(new Predicate() { // from class: com.android.systemui.accessibility.floatingmenu.AnnotationLinkSpan$$ExternalSyntheticLambda2
                    @Override // java.util.function.Predicate
                    public final boolean test(Object obj2) {
                        String str = value;
                        AnnotationLinkSpan.LinkInfo linkInfo = (AnnotationLinkSpan.LinkInfo) obj2;
                        if (linkInfo.mAnnotation.isPresent() && ((String) linkInfo.mAnnotation.get()).equals(str)) {
                            return true;
                        }
                        return false;
                    }
                }).findFirst().flatMap(new AnnotationLinkSpan$$ExternalSyntheticLambda3()).ifPresent(new AnnotationLinkSpan$$ExternalSyntheticLambda1(spannableStringBuilder, spannableString, annotation));
                return;
            default:
                AnnotationLinkSpan.$r8$lambda$BnvWY0UNioG2XHqMxIApkCrxXYQ(this.f$1, this.f$2, (Annotation) this.f$0, (View.OnClickListener) obj);
                return;
        }
    }

    public /* synthetic */ AnnotationLinkSpan$$ExternalSyntheticLambda1(AnnotationLinkSpan.LinkInfo[] linkInfoArr, SpannableStringBuilder spannableStringBuilder, SpannableString spannableString) {
        this.f$0 = linkInfoArr;
        this.f$1 = spannableStringBuilder;
        this.f$2 = spannableString;
    }
}
