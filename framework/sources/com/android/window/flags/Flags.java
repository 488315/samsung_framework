package com.android.window.flags;

/* loaded from: classes5.dex */
public final class Flags {
    private static FeatureFlags FEATURE_FLAGS = new FeatureFlagsImpl();
    public static final String FLAG_ACTIVITY_EMBEDDING_ANIMATION_CUSTOMIZATION_FLAG = "com.android.window.flags.activity_embedding_animation_customization_flag";
    public static final String FLAG_ACTIVITY_EMBEDDING_INTERACTIVE_DIVIDER_FLAG = "com.android.window.flags.activity_embedding_interactive_divider_flag";
    public static final String FLAG_ACTIVITY_EMBEDDING_OVERLAY_PRESENTATION_FLAG = "com.android.window.flags.activity_embedding_overlay_presentation_flag";
    public static final String FLAG_ACTIVITY_SNAPSHOT_BY_DEFAULT = "com.android.window.flags.activity_snapshot_by_default";
    public static final String FLAG_ACTIVITY_WINDOW_INFO_FLAG = "com.android.window.flags.activity_window_info_flag";
    public static final String FLAG_ALLOWS_SCREEN_SIZE_DECOUPLED_FROM_STATUS_BAR_AND_CUTOUT = "com.android.window.flags.allows_screen_size_decoupled_from_status_bar_and_cutout";
    public static final String FLAG_ALLOW_DISABLE_ACTIVITY_RECORD_INPUT_SINK = "com.android.window.flags.allow_disable_activity_record_input_sink";
    public static final String FLAG_ALLOW_HIDE_SCM_BUTTON = "com.android.window.flags.allow_hide_scm_button";
    public static final String FLAG_ALWAYS_DEFER_TRANSITION_WHEN_APPLY_WCT = "com.android.window.flags.always_defer_transition_when_apply_wct";
    public static final String FLAG_ALWAYS_DRAW_MAGNIFICATION_FULLSCREEN_BORDER = "com.android.window.flags.always_draw_magnification_fullscreen_border";
    public static final String FLAG_ALWAYS_UPDATE_WALLPAPER_PERMISSION = "com.android.window.flags.always_update_wallpaper_permission";
    public static final String FLAG_APP_COMPAT_PROPERTIES_API = "com.android.window.flags.app_compat_properties_api";
    public static final String FLAG_APP_COMPAT_REFACTORING = "com.android.window.flags.app_compat_refactoring";
    public static final String FLAG_APP_COMPAT_UI_FRAMEWORK = "com.android.window.flags.app_compat_ui_framework";
    public static final String FLAG_BAL_DONT_BRING_EXISTING_BACKGROUND_TASK_STACK_TO_FG = "com.android.window.flags.bal_dont_bring_existing_background_task_stack_to_fg";
    public static final String FLAG_BAL_IMPROVED_METRICS = "com.android.window.flags.bal_improved_metrics";
    public static final String FLAG_BAL_IMPROVE_REAL_CALLER_VISIBILITY_CHECK = "com.android.window.flags.bal_improve_real_caller_visibility_check";
    public static final String FLAG_BAL_REQUIRE_OPT_IN_BY_PENDING_INTENT_CREATOR = "com.android.window.flags.bal_require_opt_in_by_pending_intent_creator";
    public static final String FLAG_BAL_REQUIRE_OPT_IN_SAME_UID = "com.android.window.flags.bal_require_opt_in_same_uid";
    public static final String FLAG_BAL_RESPECT_APP_SWITCH_STATE_WHEN_CHECK_BOUND_BY_FOREGROUND_UID = "com.android.window.flags.bal_respect_app_switch_state_when_check_bound_by_foreground_uid";
    public static final String FLAG_BAL_SHOW_TOASTS = "com.android.window.flags.bal_show_toasts";
    public static final String FLAG_BAL_SHOW_TOASTS_BLOCKED = "com.android.window.flags.bal_show_toasts_blocked";
    public static final String FLAG_BLAST_SYNC_NOTIFICATION_SHADE_ON_DISPLAY_SWITCH = "com.android.window.flags.blast_sync_notification_shade_on_display_switch";
    public static final String FLAG_BUNDLE_CLIENT_TRANSACTION_FLAG = "com.android.window.flags.bundle_client_transaction_flag";
    public static final String FLAG_CAMERA_COMPAT_FOR_FREEFORM = "com.android.window.flags.camera_compat_for_freeform";
    public static final String FLAG_CAMERA_COMPAT_FULLSCREEN_PICK_SAME_TASK_ACTIVITY = "com.android.window.flags.camera_compat_fullscreen_pick_same_task_activity";
    public static final String FLAG_CLOSE_TO_SQUARE_CONFIG_INCLUDES_STATUS_BAR = "com.android.window.flags.close_to_square_config_includes_status_bar";
    public static final String FLAG_CONFIGURABLE_FONT_SCALE_DEFAULT = "com.android.window.flags.configurable_font_scale_default";
    public static final String FLAG_COVER_DISPLAY_OPT_IN = "com.android.window.flags.cover_display_opt_in";
    public static final String FLAG_DEFER_DISPLAY_UPDATES = "com.android.window.flags.defer_display_updates";
    public static final String FLAG_DELAY_NOTIFICATION_TO_MAGNIFICATION_WHEN_RECENTS_WINDOW_TO_FRONT_TRANSITION = "com.android.window.flags.delay_notification_to_magnification_when_recents_window_to_front_transition";
    public static final String FLAG_DELEGATE_UNHANDLED_DRAGS = "com.android.window.flags.delegate_unhandled_drags";
    public static final String FLAG_DELETE_CAPTURE_DISPLAY = "com.android.window.flags.delete_capture_display";
    public static final String FLAG_DENSITY_390_API = "com.android.window.flags.density_390_api";
    public static final String FLAG_DISABLE_OBJECT_POOL = "com.android.window.flags.disable_object_pool";
    public static final String FLAG_DISABLE_THIN_LETTERBOXING_POLICY = "com.android.window.flags.disable_thin_letterboxing_policy";
    public static final String FLAG_DO_NOT_CHECK_INTERSECTION_WHEN_NON_MAGNIFIABLE_WINDOW_TRANSITIONS = "com.android.window.flags.do_not_check_intersection_when_non_magnifiable_window_transitions";
    public static final String FLAG_DRAW_SNAPSHOT_ASPECT_RATIO_MATCH = "com.android.window.flags.draw_snapshot_aspect_ratio_match";
    public static final String FLAG_EDGE_TO_EDGE_BY_DEFAULT = "com.android.window.flags.edge_to_edge_by_default";
    public static final String FLAG_EMBEDDED_ACTIVITY_BACK_NAV_FLAG = "com.android.window.flags.embedded_activity_back_nav_flag";
    public static final String FLAG_ENABLE_ADDITIONAL_WINDOWS_ABOVE_STATUS_BAR = "com.android.window.flags.enable_additional_windows_above_status_bar";
    public static final String FLAG_ENABLE_APP_HEADER_WITH_TASK_DENSITY = "com.android.window.flags.enable_app_header_with_task_density";
    public static final String FLAG_ENABLE_BUFFER_TRANSFORM_HINT_FROM_DISPLAY = "com.android.window.flags.enable_buffer_transform_hint_from_display";
    public static final String FLAG_ENABLE_CAMERA_COMPAT_FOR_DESKTOP_WINDOWING = "com.android.window.flags.enable_camera_compat_for_desktop_windowing";
    public static final String FLAG_ENABLE_COMPATUI_SYSUI_LAUNCHER = "com.android.window.flags.enable_compatui_sysui_launcher";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_IMMERSIVE_HANDLE_HIDING = "com.android.window.flags.enable_desktop_windowing_immersive_handle_hiding";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_MODALS_POLICY = "com.android.window.flags.enable_desktop_windowing_modals_policy";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_MODE = "com.android.window.flags.enable_desktop_windowing_mode";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_QUICK_SWITCH = "com.android.window.flags.enable_desktop_windowing_quick_switch";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_SCVH_CACHE = "com.android.window.flags.enable_desktop_windowing_scvh_cache";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_SIZE_CONSTRAINTS = "com.android.window.flags.enable_desktop_windowing_size_constraints";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_TASKBAR_RUNNING_APPS = "com.android.window.flags.enable_desktop_windowing_taskbar_running_apps";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_TASK_LIMIT = "com.android.window.flags.enable_desktop_windowing_task_limit";
    public static final String FLAG_ENABLE_DESKTOP_WINDOWING_WALLPAPER_ACTIVITY = "com.android.window.flags.enable_desktop_windowing_wallpaper_activity";
    public static final String FLAG_ENABLE_SCALED_RESIZING = "com.android.window.flags.enable_scaled_resizing";
    public static final String FLAG_ENABLE_TASK_STACK_OBSERVER_IN_SHELL = "com.android.window.flags.enable_task_stack_observer_in_shell";
    public static final String FLAG_ENABLE_THEMED_APP_HEADERS = "com.android.window.flags.enable_themed_app_headers";
    public static final String FLAG_ENABLE_WINDOWING_DYNAMIC_INITIAL_BOUNDS = "com.android.window.flags.enable_windowing_dynamic_initial_bounds";
    public static final String FLAG_ENABLE_WINDOWING_EDGE_DRAG_RESIZE = "com.android.window.flags.enable_windowing_edge_drag_resize";
    public static final String FLAG_ENABLE_WM_EXTENSIONS_FOR_ALL_FLAG = "com.android.window.flags.enable_wm_extensions_for_all_flag";
    public static final String FLAG_ENFORCE_EDGE_TO_EDGE = "com.android.window.flags.enforce_edge_to_edge";
    public static final String FLAG_ENSURE_WALLPAPER_IN_TRANSITIONS = "com.android.window.flags.ensure_wallpaper_in_transitions";
    public static final String FLAG_EXPLICIT_REFRESH_RATE_HINTS = "com.android.window.flags.explicit_refresh_rate_hints";
    public static final String FLAG_FIFO_PRIORITY_FOR_MAJOR_UI_PROCESSES = "com.android.window.flags.fifo_priority_for_major_ui_processes";
    public static final String FLAG_FIX_NO_CONTAINER_UPDATE_WITHOUT_RESIZE = "com.android.window.flags.fix_no_container_update_without_resize";
    public static final String FLAG_FIX_PIP_RESTORE_TO_OVERLAY = "com.android.window.flags.fix_pip_restore_to_overlay";
    public static final String FLAG_FULLSCREEN_DIM_FLAG = "com.android.window.flags.fullscreen_dim_flag";
    public static final String FLAG_GET_DIMMER_ON_CLOSING = "com.android.window.flags.get_dimmer_on_closing";
    public static final String FLAG_IMMERSIVE_APP_REPOSITIONING = "com.android.window.flags.immersive_app_repositioning";
    public static final String FLAG_INSETS_CONTROL_CHANGED_ITEM = "com.android.window.flags.insets_control_changed_item";
    public static final String FLAG_INSETS_CONTROL_SEQ = "com.android.window.flags.insets_control_seq";
    public static final String FLAG_INSETS_DECOUPLED_CONFIGURATION = "com.android.window.flags.insets_decoupled_configuration";
    public static final String FLAG_KEYGUARD_APPEAR_TRANSITION = "com.android.window.flags.keyguard_appear_transition";
    public static final String FLAG_LETTERBOX_BACKGROUND_WALLPAPER = "com.android.window.flags.letterbox_background_wallpaper";
    public static final String FLAG_MOVABLE_CUTOUT_CONFIGURATION = "com.android.window.flags.movable_cutout_configuration";
    public static final String FLAG_MOVE_ANIMATION_OPTIONS_TO_CHANGE = "com.android.window.flags.move_animation_options_to_change";
    public static final String FLAG_MULTI_CROP = "com.android.window.flags.multi_crop";
    public static final String FLAG_NAV_BAR_TRANSPARENT_BY_DEFAULT = "com.android.window.flags.nav_bar_transparent_by_default";
    public static final String FLAG_NO_CONSECUTIVE_VISIBILITY_EVENTS = "com.android.window.flags.no_consecutive_visibility_events";
    public static final String FLAG_NO_VISIBILITY_EVENT_ON_DISPLAY_STATE_CHANGE = "com.android.window.flags.no_visibility_event_on_display_state_change";
    public static final String FLAG_OFFLOAD_COLOR_EXTRACTION = "com.android.window.flags.offload_color_extraction";
    public static final String FLAG_PREDICTIVE_BACK_SYSTEM_ANIMS = "com.android.window.flags.predictive_back_system_anims";
    public static final String FLAG_REAR_DISPLAY_DISABLE_FORCE_DESKTOP_SYSTEM_DECORATIONS = "com.android.window.flags.rear_display_disable_force_desktop_system_decorations";
    public static final String FLAG_RELEASE_SNAPSHOT_AGGRESSIVELY = "com.android.window.flags.release_snapshot_aggressively";
    public static final String FLAG_REMOVE_PREPARE_SURFACE_IN_PLACEMENT = "com.android.window.flags.remove_prepare_surface_in_placement";
    public static final String FLAG_RESPECT_NON_TOP_VISIBLE_FIXED_ORIENTATION = "com.android.window.flags.respect_non_top_visible_fixed_orientation";
    public static final String FLAG_SCREEN_RECORDING_CALLBACKS = "com.android.window.flags.screen_recording_callbacks";
    public static final String FLAG_SDK_DESIRED_PRESENT_TIME = "com.android.window.flags.sdk_desired_present_time";
    public static final String FLAG_SECURE_WINDOW_STATE = "com.android.window.flags.secure_window_state";
    public static final String FLAG_SET_SC_PROPERTIES_IN_CLIENT = "com.android.window.flags.set_sc_properties_in_client";
    public static final String FLAG_SKIP_SLEEPING_WHEN_SWITCHING_DISPLAY = "com.android.window.flags.skip_sleeping_when_switching_display";
    public static final String FLAG_SUPPORTS_MULTI_INSTANCE_SYSTEM_UI = "com.android.window.flags.supports_multi_instance_system_ui";
    public static final String FLAG_SURFACE_CONTROL_INPUT_RECEIVER = "com.android.window.flags.surface_control_input_receiver";
    public static final String FLAG_SURFACE_TRUSTED_OVERLAY = "com.android.window.flags.surface_trusted_overlay";
    public static final String FLAG_SYNC_SCREEN_CAPTURE = "com.android.window.flags.sync_screen_capture";
    public static final String FLAG_TASK_FRAGMENT_SYSTEM_ORGANIZER_FLAG = "com.android.window.flags.task_fragment_system_organizer_flag";
    public static final String FLAG_TRANSIT_READY_TRACKING = "com.android.window.flags.transit_ready_tracking";
    public static final String FLAG_TRUSTED_PRESENTATION_LISTENER_FOR_WINDOW = "com.android.window.flags.trusted_presentation_listener_for_window";
    public static final String FLAG_UNTRUSTED_EMBEDDING_ANY_APP_PERMISSION = "com.android.window.flags.untrusted_embedding_any_app_permission";
    public static final String FLAG_UNTRUSTED_EMBEDDING_STATE_SHARING = "com.android.window.flags.untrusted_embedding_state_sharing";
    public static final String FLAG_USER_MIN_ASPECT_RATIO_APP_DEFAULT = "com.android.window.flags.user_min_aspect_ratio_app_default";
    public static final String FLAG_USE_TASKS_DIM_ONLY = "com.android.window.flags.use_tasks_dim_only";
    public static final String FLAG_USE_WINDOW_ORIGINAL_TOUCHABLE_REGION_WHEN_MAGNIFICATION_RECOMPUTE_BOUNDS = "com.android.window.flags.use_window_original_touchable_region_when_magnification_recompute_bounds";
    public static final String FLAG_WAIT_FOR_TRANSITION_ON_DISPLAY_SWITCH = "com.android.window.flags.wait_for_transition_on_display_switch";
    public static final String FLAG_WALLPAPER_OFFSET_ASYNC = "com.android.window.flags.wallpaper_offset_async";
    public static final String FLAG_WINDOW_SESSION_RELAYOUT_INFO = "com.android.window.flags.window_session_relayout_info";
    public static final String FLAG_WINDOW_TOKEN_CONFIG_THREAD_SAFE = "com.android.window.flags.window_token_config_thread_safe";

    public static boolean activityEmbeddingAnimationCustomizationFlag() {
        return FEATURE_FLAGS.activityEmbeddingAnimationCustomizationFlag();
    }

    public static boolean activityEmbeddingInteractiveDividerFlag() {
        return FEATURE_FLAGS.activityEmbeddingInteractiveDividerFlag();
    }

    public static boolean activityEmbeddingOverlayPresentationFlag() {
        return FEATURE_FLAGS.activityEmbeddingOverlayPresentationFlag();
    }

    public static boolean activitySnapshotByDefault() {
        return FEATURE_FLAGS.activitySnapshotByDefault();
    }

    public static boolean activityWindowInfoFlag() {
        return FEATURE_FLAGS.activityWindowInfoFlag();
    }

    public static boolean allowDisableActivityRecordInputSink() {
        return FEATURE_FLAGS.allowDisableActivityRecordInputSink();
    }

    public static boolean allowHideScmButton() {
        return FEATURE_FLAGS.allowHideScmButton();
    }

    public static boolean allowsScreenSizeDecoupledFromStatusBarAndCutout() {
        return FEATURE_FLAGS.allowsScreenSizeDecoupledFromStatusBarAndCutout();
    }

    public static boolean alwaysDeferTransitionWhenApplyWct() {
        return FEATURE_FLAGS.alwaysDeferTransitionWhenApplyWct();
    }

    public static boolean alwaysDrawMagnificationFullscreenBorder() {
        return FEATURE_FLAGS.alwaysDrawMagnificationFullscreenBorder();
    }

    public static boolean alwaysUpdateWallpaperPermission() {
        return FEATURE_FLAGS.alwaysUpdateWallpaperPermission();
    }

    public static boolean appCompatPropertiesApi() {
        return FEATURE_FLAGS.appCompatPropertiesApi();
    }

    public static boolean appCompatRefactoring() {
        return FEATURE_FLAGS.appCompatRefactoring();
    }

    public static boolean appCompatUiFramework() {
        return FEATURE_FLAGS.appCompatUiFramework();
    }

    public static boolean balDontBringExistingBackgroundTaskStackToFg() {
        return FEATURE_FLAGS.balDontBringExistingBackgroundTaskStackToFg();
    }

    public static boolean balImproveRealCallerVisibilityCheck() {
        return FEATURE_FLAGS.balImproveRealCallerVisibilityCheck();
    }

    public static boolean balImprovedMetrics() {
        return FEATURE_FLAGS.balImprovedMetrics();
    }

    public static boolean balRequireOptInByPendingIntentCreator() {
        return FEATURE_FLAGS.balRequireOptInByPendingIntentCreator();
    }

    public static boolean balRequireOptInSameUid() {
        return FEATURE_FLAGS.balRequireOptInSameUid();
    }

    public static boolean balRespectAppSwitchStateWhenCheckBoundByForegroundUid() {
        return FEATURE_FLAGS.balRespectAppSwitchStateWhenCheckBoundByForegroundUid();
    }

    public static boolean balShowToasts() {
        return FEATURE_FLAGS.balShowToasts();
    }

    public static boolean balShowToastsBlocked() {
        return FEATURE_FLAGS.balShowToastsBlocked();
    }

    public static boolean blastSyncNotificationShadeOnDisplaySwitch() {
        return FEATURE_FLAGS.blastSyncNotificationShadeOnDisplaySwitch();
    }

    public static boolean bundleClientTransactionFlag() {
        return FEATURE_FLAGS.bundleClientTransactionFlag();
    }

    public static boolean cameraCompatForFreeform() {
        return FEATURE_FLAGS.cameraCompatForFreeform();
    }

    public static boolean cameraCompatFullscreenPickSameTaskActivity() {
        return FEATURE_FLAGS.cameraCompatFullscreenPickSameTaskActivity();
    }

    public static boolean closeToSquareConfigIncludesStatusBar() {
        return FEATURE_FLAGS.closeToSquareConfigIncludesStatusBar();
    }

    public static boolean configurableFontScaleDefault() {
        return FEATURE_FLAGS.configurableFontScaleDefault();
    }

    public static boolean coverDisplayOptIn() {
        return FEATURE_FLAGS.coverDisplayOptIn();
    }

    public static boolean deferDisplayUpdates() {
        return FEATURE_FLAGS.deferDisplayUpdates();
    }

    public static boolean delayNotificationToMagnificationWhenRecentsWindowToFrontTransition() {
        return FEATURE_FLAGS.delayNotificationToMagnificationWhenRecentsWindowToFrontTransition();
    }

    public static boolean delegateUnhandledDrags() {
        return FEATURE_FLAGS.delegateUnhandledDrags();
    }

    public static boolean deleteCaptureDisplay() {
        return FEATURE_FLAGS.deleteCaptureDisplay();
    }

    public static boolean density390Api() {
        return FEATURE_FLAGS.density390Api();
    }

    public static boolean disableObjectPool() {
        return FEATURE_FLAGS.disableObjectPool();
    }

    public static boolean disableThinLetterboxingPolicy() {
        return FEATURE_FLAGS.disableThinLetterboxingPolicy();
    }

    public static boolean doNotCheckIntersectionWhenNonMagnifiableWindowTransitions() {
        return FEATURE_FLAGS.doNotCheckIntersectionWhenNonMagnifiableWindowTransitions();
    }

    public static boolean drawSnapshotAspectRatioMatch() {
        return FEATURE_FLAGS.drawSnapshotAspectRatioMatch();
    }

    public static boolean edgeToEdgeByDefault() {
        return FEATURE_FLAGS.edgeToEdgeByDefault();
    }

    public static boolean embeddedActivityBackNavFlag() {
        return FEATURE_FLAGS.embeddedActivityBackNavFlag();
    }

    public static boolean enableAdditionalWindowsAboveStatusBar() {
        return FEATURE_FLAGS.enableAdditionalWindowsAboveStatusBar();
    }

    public static boolean enableAppHeaderWithTaskDensity() {
        return FEATURE_FLAGS.enableAppHeaderWithTaskDensity();
    }

    public static boolean enableBufferTransformHintFromDisplay() {
        return FEATURE_FLAGS.enableBufferTransformHintFromDisplay();
    }

    public static boolean enableCameraCompatForDesktopWindowing() {
        return FEATURE_FLAGS.enableCameraCompatForDesktopWindowing();
    }

    public static boolean enableCompatuiSysuiLauncher() {
        return FEATURE_FLAGS.enableCompatuiSysuiLauncher();
    }

    public static boolean enableDesktopWindowingImmersiveHandleHiding() {
        return FEATURE_FLAGS.enableDesktopWindowingImmersiveHandleHiding();
    }

    public static boolean enableDesktopWindowingModalsPolicy() {
        return FEATURE_FLAGS.enableDesktopWindowingModalsPolicy();
    }

    public static boolean enableDesktopWindowingMode() {
        return FEATURE_FLAGS.enableDesktopWindowingMode();
    }

    public static boolean enableDesktopWindowingQuickSwitch() {
        return FEATURE_FLAGS.enableDesktopWindowingQuickSwitch();
    }

    public static boolean enableDesktopWindowingScvhCache() {
        return FEATURE_FLAGS.enableDesktopWindowingScvhCache();
    }

    public static boolean enableDesktopWindowingSizeConstraints() {
        return FEATURE_FLAGS.enableDesktopWindowingSizeConstraints();
    }

    public static boolean enableDesktopWindowingTaskLimit() {
        return FEATURE_FLAGS.enableDesktopWindowingTaskLimit();
    }

    public static boolean enableDesktopWindowingTaskbarRunningApps() {
        return FEATURE_FLAGS.enableDesktopWindowingTaskbarRunningApps();
    }

    public static boolean enableDesktopWindowingWallpaperActivity() {
        return FEATURE_FLAGS.enableDesktopWindowingWallpaperActivity();
    }

    public static boolean enableScaledResizing() {
        return FEATURE_FLAGS.enableScaledResizing();
    }

    public static boolean enableTaskStackObserverInShell() {
        return FEATURE_FLAGS.enableTaskStackObserverInShell();
    }

    public static boolean enableThemedAppHeaders() {
        return FEATURE_FLAGS.enableThemedAppHeaders();
    }

    public static boolean enableWindowingDynamicInitialBounds() {
        return FEATURE_FLAGS.enableWindowingDynamicInitialBounds();
    }

    public static boolean enableWindowingEdgeDragResize() {
        return FEATURE_FLAGS.enableWindowingEdgeDragResize();
    }

    public static boolean enableWmExtensionsForAllFlag() {
        return FEATURE_FLAGS.enableWmExtensionsForAllFlag();
    }

    public static boolean enforceEdgeToEdge() {
        return FEATURE_FLAGS.enforceEdgeToEdge();
    }

    public static boolean ensureWallpaperInTransitions() {
        return FEATURE_FLAGS.ensureWallpaperInTransitions();
    }

    public static boolean explicitRefreshRateHints() {
        return FEATURE_FLAGS.explicitRefreshRateHints();
    }

    public static boolean fifoPriorityForMajorUiProcesses() {
        return FEATURE_FLAGS.fifoPriorityForMajorUiProcesses();
    }

    public static boolean fixNoContainerUpdateWithoutResize() {
        return FEATURE_FLAGS.fixNoContainerUpdateWithoutResize();
    }

    public static boolean fixPipRestoreToOverlay() {
        return FEATURE_FLAGS.fixPipRestoreToOverlay();
    }

    public static boolean fullscreenDimFlag() {
        return FEATURE_FLAGS.fullscreenDimFlag();
    }

    public static boolean getDimmerOnClosing() {
        return FEATURE_FLAGS.getDimmerOnClosing();
    }

    public static boolean immersiveAppRepositioning() {
        return FEATURE_FLAGS.immersiveAppRepositioning();
    }

    public static boolean insetsControlChangedItem() {
        return FEATURE_FLAGS.insetsControlChangedItem();
    }

    public static boolean insetsControlSeq() {
        return FEATURE_FLAGS.insetsControlSeq();
    }

    public static boolean insetsDecoupledConfiguration() {
        return FEATURE_FLAGS.insetsDecoupledConfiguration();
    }

    public static boolean keyguardAppearTransition() {
        return FEATURE_FLAGS.keyguardAppearTransition();
    }

    public static boolean letterboxBackgroundWallpaper() {
        return FEATURE_FLAGS.letterboxBackgroundWallpaper();
    }

    public static boolean movableCutoutConfiguration() {
        return FEATURE_FLAGS.movableCutoutConfiguration();
    }

    public static boolean moveAnimationOptionsToChange() {
        return FEATURE_FLAGS.moveAnimationOptionsToChange();
    }

    public static boolean multiCrop() {
        return FEATURE_FLAGS.multiCrop();
    }

    public static boolean navBarTransparentByDefault() {
        return FEATURE_FLAGS.navBarTransparentByDefault();
    }

    public static boolean noConsecutiveVisibilityEvents() {
        return FEATURE_FLAGS.noConsecutiveVisibilityEvents();
    }

    public static boolean noVisibilityEventOnDisplayStateChange() {
        return FEATURE_FLAGS.noVisibilityEventOnDisplayStateChange();
    }

    public static boolean offloadColorExtraction() {
        return FEATURE_FLAGS.offloadColorExtraction();
    }

    public static boolean predictiveBackSystemAnims() {
        return FEATURE_FLAGS.predictiveBackSystemAnims();
    }

    public static boolean rearDisplayDisableForceDesktopSystemDecorations() {
        return FEATURE_FLAGS.rearDisplayDisableForceDesktopSystemDecorations();
    }

    public static boolean releaseSnapshotAggressively() {
        return FEATURE_FLAGS.releaseSnapshotAggressively();
    }

    public static boolean removePrepareSurfaceInPlacement() {
        return FEATURE_FLAGS.removePrepareSurfaceInPlacement();
    }

    public static boolean respectNonTopVisibleFixedOrientation() {
        return FEATURE_FLAGS.respectNonTopVisibleFixedOrientation();
    }

    public static boolean screenRecordingCallbacks() {
        return FEATURE_FLAGS.screenRecordingCallbacks();
    }

    public static boolean sdkDesiredPresentTime() {
        return FEATURE_FLAGS.sdkDesiredPresentTime();
    }

    public static boolean secureWindowState() {
        return FEATURE_FLAGS.secureWindowState();
    }

    public static boolean setScPropertiesInClient() {
        return FEATURE_FLAGS.setScPropertiesInClient();
    }

    public static boolean skipSleepingWhenSwitchingDisplay() {
        return FEATURE_FLAGS.skipSleepingWhenSwitchingDisplay();
    }

    public static boolean supportsMultiInstanceSystemUi() {
        return FEATURE_FLAGS.supportsMultiInstanceSystemUi();
    }

    public static boolean surfaceControlInputReceiver() {
        return FEATURE_FLAGS.surfaceControlInputReceiver();
    }

    public static boolean surfaceTrustedOverlay() {
        return FEATURE_FLAGS.surfaceTrustedOverlay();
    }

    public static boolean syncScreenCapture() {
        return FEATURE_FLAGS.syncScreenCapture();
    }

    public static boolean taskFragmentSystemOrganizerFlag() {
        return FEATURE_FLAGS.taskFragmentSystemOrganizerFlag();
    }

    public static boolean transitReadyTracking() {
        return FEATURE_FLAGS.transitReadyTracking();
    }

    public static boolean trustedPresentationListenerForWindow() {
        return FEATURE_FLAGS.trustedPresentationListenerForWindow();
    }

    public static boolean untrustedEmbeddingAnyAppPermission() {
        return FEATURE_FLAGS.untrustedEmbeddingAnyAppPermission();
    }

    public static boolean untrustedEmbeddingStateSharing() {
        return FEATURE_FLAGS.untrustedEmbeddingStateSharing();
    }

    public static boolean useTasksDimOnly() {
        return FEATURE_FLAGS.useTasksDimOnly();
    }

    public static boolean useWindowOriginalTouchableRegionWhenMagnificationRecomputeBounds() {
        return FEATURE_FLAGS.useWindowOriginalTouchableRegionWhenMagnificationRecomputeBounds();
    }

    public static boolean userMinAspectRatioAppDefault() {
        return FEATURE_FLAGS.userMinAspectRatioAppDefault();
    }

    public static boolean waitForTransitionOnDisplaySwitch() {
        return FEATURE_FLAGS.waitForTransitionOnDisplaySwitch();
    }

    public static boolean wallpaperOffsetAsync() {
        return FEATURE_FLAGS.wallpaperOffsetAsync();
    }

    public static boolean windowSessionRelayoutInfo() {
        return FEATURE_FLAGS.windowSessionRelayoutInfo();
    }

    public static boolean windowTokenConfigThreadSafe() {
        return FEATURE_FLAGS.windowTokenConfigThreadSafe();
    }
}
