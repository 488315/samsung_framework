package android.service.settings.suggestions;

import android.annotation.SystemApi;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.service.settings.suggestions.ISuggestionService;
import java.util.List;

@SystemApi
/* loaded from: classes3.dex */
public abstract class SuggestionService extends Service {
    private static final boolean DEBUG = false;
    private static final String TAG = "SuggestionService";

    public abstract List<Suggestion> onGetSuggestions();

    public abstract void onSuggestionDismissed(Suggestion suggestion);

    public abstract void onSuggestionLaunched(Suggestion suggestion);

    /* renamed from: android.service.settings.suggestions.SuggestionService$1 */
    /* loaded from: classes3.dex */
    class AnonymousClass1 extends ISuggestionService.Stub {
        AnonymousClass1() {
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public List<Suggestion> getSuggestions() {
            return SuggestionService.this.onGetSuggestions();
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void dismissSuggestion(Suggestion suggestion) {
            SuggestionService.this.onSuggestionDismissed(suggestion);
        }

        @Override // android.service.settings.suggestions.ISuggestionService
        public void launchSuggestion(Suggestion suggestion) {
            SuggestionService.this.onSuggestionLaunched(suggestion);
        }
    }

    @Override // android.app.Service
    public IBinder onBind(Intent intent) {
        return new ISuggestionService.Stub() { // from class: android.service.settings.suggestions.SuggestionService.1
            AnonymousClass1() {
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public List<Suggestion> getSuggestions() {
                return SuggestionService.this.onGetSuggestions();
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void dismissSuggestion(Suggestion suggestion) {
                SuggestionService.this.onSuggestionDismissed(suggestion);
            }

            @Override // android.service.settings.suggestions.ISuggestionService
            public void launchSuggestion(Suggestion suggestion) {
                SuggestionService.this.onSuggestionLaunched(suggestion);
            }
        };
    }
}
