package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.icu.text.DateFormatSymbols;
import android.icu.text.DisplayContext;
import android.icu.text.RelativeDateTimeFormatter;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.text.TextPaint;
import android.text.format.DateFormat;
import android.util.AttributeSet;
import android.util.IntArray;
import android.util.MathUtils;
import android.util.StateSet;
import android.view.MotionEvent;
import android.view.PointerIcon;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.flags.Flags;
import com.android.internal.R;
import com.android.internal.widget.ExploreByTouchHelper;
import java.text.NumberFormat;
import java.util.Locale;

/* loaded from: classes4.dex */
class SimpleMonthView extends View {
    private static final int DAYS_IN_WEEK = 7;
    private static final int DEFAULT_SELECTED_DAY = -1;
    private static final int DEFAULT_WEEK_START = 1;
    private static final int MAX_WEEKS_IN_MONTH = 6;
    private static final String MONTH_YEAR_FORMAT = "MMMMy";
    private static final int SELECTED_HIGHLIGHT_ALPHA = 176;
    private int mActivatedDay;
    private final Calendar mCalendar;
    private int mCellWidth;
    private final NumberFormat mDayFormatter;
    private int mDayHeight;
    private final Paint mDayHighlightPaint;
    private final Paint mDayHighlightSelectorPaint;
    private int mDayOfWeekHeight;
    private final String[] mDayOfWeekLabels;
    private final TextPaint mDayOfWeekPaint;
    private int mDayOfWeekStart;
    private final TextPaint mDayPaint;
    private final Paint mDaySelectorPaint;
    private int mDaySelectorRadius;
    private ColorStateList mDayTextColor;
    private int mDaysInMonth;
    private final int mDesiredCellWidth;
    private final int mDesiredDayHeight;
    private final int mDesiredDayOfWeekHeight;
    private final int mDesiredDaySelectorRadius;
    private final int mDesiredMonthHeight;
    private int mEnabledDayEnd;
    private int mEnabledDayStart;
    private int mHighlightedDay;
    private boolean mIsTouchHighlighted;
    private final Locale mLocale;
    private int mMonth;
    private int mMonthHeight;
    private final TextPaint mMonthPaint;
    private String mMonthYearLabel;
    private OnDayClickListener mOnDayClickListener;
    private int mPaddedHeight;
    private int mPaddedWidth;
    private int mPreviouslyHighlightedDay;
    private int mToday;
    private final MonthViewTouchHelper mTouchHelper;
    private int mWeekStart;
    private int mYear;

    public interface OnDayClickListener {
        void onDayClick(SimpleMonthView simpleMonthView, Calendar calendar);
    }

    public SimpleMonthView(Context context) {
        this(context, null);
    }

    public SimpleMonthView(Context context, AttributeSet attrs) {
        this(context, attrs, 16843612);
    }

    public SimpleMonthView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SimpleMonthView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mMonthPaint = new TextPaint();
        this.mDayOfWeekPaint = new TextPaint();
        this.mDayPaint = new TextPaint();
        this.mDaySelectorPaint = new Paint();
        this.mDayHighlightPaint = new Paint();
        this.mDayHighlightSelectorPaint = new Paint();
        this.mDayOfWeekLabels = new String[7];
        this.mActivatedDay = -1;
        this.mToday = -1;
        this.mWeekStart = 1;
        this.mEnabledDayStart = 1;
        this.mEnabledDayEnd = 31;
        this.mHighlightedDay = -1;
        this.mPreviouslyHighlightedDay = -1;
        this.mIsTouchHighlighted = false;
        Resources res = context.getResources();
        this.mDesiredMonthHeight = res.getDimensionPixelSize(R.dimen.date_picker_month_height);
        this.mDesiredDayOfWeekHeight = res.getDimensionPixelSize(R.dimen.date_picker_day_of_week_height);
        this.mDesiredDayHeight = res.getDimensionPixelSize(R.dimen.date_picker_day_height);
        this.mDesiredCellWidth = res.getDimensionPixelSize(R.dimen.date_picker_day_width);
        this.mDesiredDaySelectorRadius = res.getDimensionPixelSize(R.dimen.date_picker_day_selector_radius);
        this.mTouchHelper = new MonthViewTouchHelper(this);
        setAccessibilityDelegate(this.mTouchHelper);
        setImportantForAccessibility(1);
        this.mLocale = res.getConfiguration().locale;
        this.mCalendar = Calendar.getInstance(this.mLocale);
        this.mDayFormatter = NumberFormat.getIntegerInstance(this.mLocale);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        initPaints(res);
    }

    private void updateMonthYearLabel() {
        String format = DateFormat.getBestDateTimePattern(this.mLocale, MONTH_YEAR_FORMAT);
        SimpleDateFormat formatter = new SimpleDateFormat(format, this.mLocale);
        formatter.setContext(DisplayContext.CAPITALIZATION_FOR_BEGINNING_OF_SENTENCE);
        this.mMonthYearLabel = formatter.format(this.mCalendar.getTime());
    }

    private void updateDayOfWeekLabels() {
        String[] tinyWeekdayNames = DateFormatSymbols.getInstance(this.mLocale).getWeekdays(0, 2);
        for (int i = 0; i < 7; i++) {
            this.mDayOfWeekLabels[i] = tinyWeekdayNames[(((this.mWeekStart + i) - 1) % 7) + 1];
        }
    }

    private ColorStateList applyTextAppearance(Paint p, int resId) {
        TypedArray ta = this.mContext.obtainStyledAttributes(null, R.styleable.TextAppearance, 0, resId);
        String fontFamily = ta.getString(12);
        if (fontFamily != null) {
            p.setTypeface(Typeface.create(fontFamily, 0));
        }
        p.setTextSize(ta.getDimensionPixelSize(0, (int) p.getTextSize()));
        ColorStateList textColor = ta.getColorStateList(3);
        if (textColor != null) {
            int enabledColor = textColor.getColorForState(ENABLED_STATE_SET, 0);
            p.setColor(enabledColor);
        }
        ta.recycle();
        return textColor;
    }

    public int getMonthHeight() {
        return this.mMonthHeight;
    }

    public int getCellWidth() {
        return this.mCellWidth;
    }

    public void setMonthTextAppearance(int resId) {
        applyTextAppearance(this.mMonthPaint, resId);
        invalidate();
    }

    public void setDayOfWeekTextAppearance(int resId) {
        applyTextAppearance(this.mDayOfWeekPaint, resId);
        invalidate();
    }

    public void setDayTextAppearance(int resId) {
        ColorStateList textColor = applyTextAppearance(this.mDayPaint, resId);
        if (textColor != null) {
            this.mDayTextColor = textColor;
        }
        invalidate();
    }

    private void initPaints(Resources res) {
        String monthTypeface = res.getString(R.string.date_picker_month_typeface);
        String dayOfWeekTypeface = res.getString(R.string.date_picker_day_of_week_typeface);
        String dayTypeface = res.getString(R.string.date_picker_day_typeface);
        int monthTextSize = res.getDimensionPixelSize(R.dimen.date_picker_month_text_size);
        int dayOfWeekTextSize = res.getDimensionPixelSize(R.dimen.date_picker_day_of_week_text_size);
        int dayTextSize = res.getDimensionPixelSize(R.dimen.date_picker_day_text_size);
        this.mMonthPaint.setAntiAlias(true);
        this.mMonthPaint.setTextSize(monthTextSize);
        this.mMonthPaint.setTypeface(Typeface.create(monthTypeface, 0));
        this.mMonthPaint.setTextAlign(Paint.Align.CENTER);
        this.mMonthPaint.setStyle(Paint.Style.FILL);
        this.mDayOfWeekPaint.setAntiAlias(true);
        this.mDayOfWeekPaint.setTextSize(dayOfWeekTextSize);
        this.mDayOfWeekPaint.setTypeface(Typeface.create(dayOfWeekTypeface, 0));
        this.mDayOfWeekPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayOfWeekPaint.setStyle(Paint.Style.FILL);
        this.mDaySelectorPaint.setAntiAlias(true);
        this.mDaySelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightPaint.setAntiAlias(true);
        this.mDayHighlightPaint.setStyle(Paint.Style.FILL);
        this.mDayHighlightSelectorPaint.setAntiAlias(true);
        this.mDayHighlightSelectorPaint.setStyle(Paint.Style.FILL);
        this.mDayPaint.setAntiAlias(true);
        this.mDayPaint.setTextSize(dayTextSize);
        this.mDayPaint.setTypeface(Typeface.create(dayTypeface, 0));
        this.mDayPaint.setTextAlign(Paint.Align.CENTER);
        this.mDayPaint.setStyle(Paint.Style.FILL);
    }

    void setMonthTextColor(ColorStateList monthTextColor) {
        int enabledColor = monthTextColor.getColorForState(ENABLED_STATE_SET, 0);
        this.mMonthPaint.setColor(enabledColor);
        invalidate();
    }

    void setDayOfWeekTextColor(ColorStateList dayOfWeekTextColor) {
        int enabledColor = dayOfWeekTextColor.getColorForState(ENABLED_STATE_SET, 0);
        this.mDayOfWeekPaint.setColor(enabledColor);
        invalidate();
    }

    void setDayTextColor(ColorStateList dayTextColor) {
        this.mDayTextColor = dayTextColor;
        invalidate();
    }

    void setDaySelectorColor(ColorStateList dayBackgroundColor) {
        int activatedColor = dayBackgroundColor.getColorForState(StateSet.get(40), 0);
        this.mDaySelectorPaint.setColor(activatedColor);
        this.mDayHighlightSelectorPaint.setColor(activatedColor);
        this.mDayHighlightSelectorPaint.setAlpha(176);
        invalidate();
    }

    void setDayHighlightColor(ColorStateList dayHighlightColor) {
        int pressedColor = dayHighlightColor.getColorForState(StateSet.get(24), 0);
        this.mDayHighlightPaint.setColor(pressedColor);
        invalidate();
    }

    public void setOnDayClickListener(OnDayClickListener listener) {
        this.mOnDayClickListener = listener;
    }

    @Override // android.view.View
    public boolean dispatchHoverEvent(MotionEvent event) {
        return this.mTouchHelper.dispatchHoverEvent(event) || super.dispatchHoverEvent(event);
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:12:0x003e A[RETURN] */
    @Override // android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onTouchEvent(android.view.MotionEvent r8) {
        /*
            r7 = this;
            float r0 = r8.getX()
            r1 = 1056964608(0x3f000000, float:0.5)
            float r0 = r0 + r1
            int r0 = (int) r0
            float r2 = r8.getY()
            float r2 = r2 + r1
            int r1 = (int) r2
            int r2 = r8.getAction()
            r3 = 0
            r4 = 1
            switch(r2) {
                case 0: goto L28;
                case 1: goto L18;
                case 2: goto L28;
                case 3: goto L1f;
                default: goto L17;
            }
        L17:
            goto L3e
        L18:
            int r5 = r7.getDayAtLocation(r0, r1)
            r7.onDayClicked(r5)
        L1f:
            r5 = -1
            r7.mHighlightedDay = r5
            r7.mIsTouchHighlighted = r3
            r7.invalidate()
            goto L3e
        L28:
            int r5 = r7.getDayAtLocation(r0, r1)
            r7.mIsTouchHighlighted = r4
            int r6 = r7.mHighlightedDay
            if (r6 == r5) goto L39
            r7.mHighlightedDay = r5
            r7.mPreviouslyHighlightedDay = r5
            r7.invalidate()
        L39:
            if (r2 != 0) goto L3e
            if (r5 >= 0) goto L3e
            return r3
        L3e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SimpleMonthView.onTouchEvent(android.view.MotionEvent):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0033  */
    @Override // android.view.View, android.view.KeyEvent.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean onKeyDown(int r7, android.view.KeyEvent r8) {
        /*
            r6 = this;
            r0 = 0
            int r1 = r8.getKeyCode()
            r2 = 7
            r3 = 1
            switch(r1) {
                case 19: goto L79;
                case 20: goto L62;
                case 21: goto L53;
                case 22: goto L43;
                case 23: goto L38;
                case 61: goto Lc;
                case 66: goto L38;
                case 160: goto L38;
                default: goto La;
            }
        La:
            goto L8c
        Lc:
            r1 = 0
            boolean r2 = r8.hasNoModifiers()
            if (r2 == 0) goto L15
            r1 = 2
            goto L1c
        L15:
            boolean r2 = r8.hasModifiers(r3)
            if (r2 == 0) goto L1c
            r1 = 1
        L1c:
            if (r1 == 0) goto L8c
            android.view.ViewParent r2 = r6.getParent()
            r4 = r6
        L23:
            android.view.View r4 = r4.focusSearch(r1)
            if (r4 == 0) goto L31
            if (r4 == r6) goto L31
            android.view.ViewParent r5 = r4.getParent()
            if (r5 == r2) goto L23
        L31:
            if (r4 == 0) goto L37
            r4.requestFocus()
            return r3
        L37:
            goto L8c
        L38:
            int r1 = r6.mHighlightedDay
            r2 = -1
            if (r1 == r2) goto L8c
            int r1 = r6.mHighlightedDay
            r6.onDayClicked(r1)
            return r3
        L43:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L8c
            boolean r1 = r6.isLayoutRtl()
            r1 = r1 ^ r3
            boolean r0 = r6.moveOneDay(r1)
            goto L8c
        L53:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L8c
            boolean r1 = r6.isLayoutRtl()
            boolean r0 = r6.moveOneDay(r1)
            goto L8c
        L62:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L8c
            r6.ensureFocusedDay()
            int r1 = r6.mHighlightedDay
            int r4 = r6.mDaysInMonth
            int r4 = r4 - r2
            if (r1 > r4) goto L8c
            int r1 = r6.mHighlightedDay
            int r1 = r1 + r2
            r6.mHighlightedDay = r1
            r0 = 1
            goto L8c
        L79:
            boolean r1 = r8.hasNoModifiers()
            if (r1 == 0) goto L8c
            r6.ensureFocusedDay()
            int r1 = r6.mHighlightedDay
            if (r1 <= r2) goto L8c
            int r1 = r6.mHighlightedDay
            int r1 = r1 - r2
            r6.mHighlightedDay = r1
            r0 = 1
        L8c:
            if (r0 == 0) goto L92
            r6.invalidate()
            return r3
        L92:
            boolean r1 = super.onKeyDown(r7, r8)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: android.widget.SimpleMonthView.onKeyDown(int, android.view.KeyEvent):boolean");
    }

    private boolean moveOneDay(boolean positive) {
        ensureFocusedDay();
        if (positive) {
            if (isLastDayOfWeek(this.mHighlightedDay) || this.mHighlightedDay >= this.mDaysInMonth) {
                return false;
            }
            this.mHighlightedDay++;
            return true;
        }
        if (isFirstDayOfWeek(this.mHighlightedDay) || this.mHighlightedDay <= 1) {
            return false;
        }
        this.mHighlightedDay--;
        return true;
    }

    @Override // android.view.View
    protected void onFocusChanged(boolean gainFocus, int direction, Rect previouslyFocusedRect) {
        if (gainFocus) {
            int offset = findDayOffset();
            switch (direction) {
                case 17:
                    int col = findClosestRow(previouslyFocusedRect);
                    this.mHighlightedDay = Math.min(this.mDaysInMonth, ((col + 1) * 7) - offset);
                    break;
                case 33:
                    int col2 = findClosestColumn(previouslyFocusedRect);
                    int maxWeeks = (this.mDaysInMonth + offset) / 7;
                    int day = (col2 - offset) + (maxWeeks * 7) + 1;
                    this.mHighlightedDay = day > this.mDaysInMonth ? day - 7 : day;
                    break;
                case 66:
                    int row = findClosestRow(previouslyFocusedRect);
                    this.mHighlightedDay = row != 0 ? 1 + ((row * 7) - offset) : 1;
                    break;
                case 130:
                    int col3 = findClosestColumn(previouslyFocusedRect);
                    int day2 = (col3 - offset) + 1;
                    this.mHighlightedDay = day2 < 1 ? day2 + 7 : day2;
                    break;
            }
            ensureFocusedDay();
            invalidate();
        }
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
    }

    private int findClosestRow(Rect previouslyFocusedRect) {
        if (previouslyFocusedRect == null) {
            return 3;
        }
        if (this.mDayHeight == 0) {
            return 0;
        }
        int centerY = previouslyFocusedRect.centerY();
        TextPaint p = this.mDayPaint;
        int headerHeight = this.mMonthHeight + this.mDayOfWeekHeight;
        int rowHeight = this.mDayHeight;
        float halfLineHeight = (p.ascent() + p.descent()) / 2.0f;
        int rowCenter = (rowHeight / 2) + headerHeight;
        int row = Math.round(((int) (centerY - (rowCenter - halfLineHeight))) / rowHeight);
        int maxDay = findDayOffset() + this.mDaysInMonth;
        int maxRows = (maxDay / 7) - (maxDay % 7 == 0 ? 1 : 0);
        return MathUtils.constrain(row, 0, maxRows);
    }

    private int findClosestColumn(Rect previouslyFocusedRect) {
        if (previouslyFocusedRect == null) {
            return 3;
        }
        if (this.mCellWidth == 0) {
            return 0;
        }
        int centerX = previouslyFocusedRect.centerX() - this.mPaddingLeft;
        int columnFromLeft = MathUtils.constrain(centerX / this.mCellWidth, 0, 6);
        return isLayoutRtl() ? (7 - columnFromLeft) - 1 : columnFromLeft;
    }

    @Override // android.view.View
    public void getFocusedRect(Rect r) {
        if (this.mHighlightedDay > 0) {
            getBoundsForDay(this.mHighlightedDay, r);
        } else {
            super.getFocusedRect(r);
        }
    }

    @Override // android.view.View
    protected void onFocusLost() {
        if (!this.mIsTouchHighlighted) {
            this.mPreviouslyHighlightedDay = this.mHighlightedDay;
            this.mHighlightedDay = -1;
            invalidate();
        }
        super.onFocusLost();
    }

    private void ensureFocusedDay() {
        if (this.mHighlightedDay != -1) {
            return;
        }
        if (this.mPreviouslyHighlightedDay != -1) {
            this.mHighlightedDay = this.mPreviouslyHighlightedDay;
        } else if (this.mActivatedDay != -1) {
            this.mHighlightedDay = this.mActivatedDay;
        } else {
            this.mHighlightedDay = 1;
        }
    }

    private boolean isFirstDayOfWeek(int day) {
        int offset = findDayOffset();
        return ((offset + day) - 1) % 7 == 0;
    }

    private boolean isLastDayOfWeek(int day) {
        int offset = findDayOffset();
        return (offset + day) % 7 == 0;
    }

    @Override // android.view.View
    protected void onDraw(Canvas canvas) {
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        canvas.translate(paddingLeft, paddingTop);
        drawMonth(canvas);
        drawDaysOfWeek(canvas);
        drawDays(canvas);
        canvas.translate(-paddingLeft, -paddingTop);
    }

    private void drawMonth(Canvas canvas) {
        float x = this.mPaddedWidth / 2.0f;
        float lineHeight = this.mMonthPaint.ascent() + this.mMonthPaint.descent();
        float y = (this.mMonthHeight - lineHeight) / 2.0f;
        canvas.drawText(this.mMonthYearLabel, x, y, this.mMonthPaint);
    }

    public String getMonthYearLabel() {
        return this.mMonthYearLabel;
    }

    private void drawDaysOfWeek(Canvas canvas) {
        int colCenterRtl;
        TextPaint p = this.mDayOfWeekPaint;
        int headerHeight = this.mMonthHeight;
        int rowHeight = this.mDayOfWeekHeight;
        int colWidth = this.mCellWidth;
        float halfLineHeight = (p.ascent() + p.descent()) / 2.0f;
        int rowCenter = (rowHeight / 2) + headerHeight;
        for (int col = 0; col < 7; col++) {
            int colCenter = (colWidth * col) + (colWidth / 2);
            if (isLayoutRtl()) {
                colCenterRtl = this.mPaddedWidth - colCenter;
            } else {
                colCenterRtl = colCenter;
            }
            String label = this.mDayOfWeekLabels[col];
            canvas.drawText(label, colCenterRtl, rowCenter - halfLineHeight, p);
        }
    }

    private void drawDays(Canvas canvas) {
        int colCenterRtl;
        int headerHeight;
        int colWidth;
        int stateMask;
        int dayTextColor;
        Paint paint;
        TextPaint p = this.mDayPaint;
        int headerHeight2 = this.mMonthHeight + this.mDayOfWeekHeight;
        int rowHeight = this.mDayHeight;
        int colWidth2 = this.mCellWidth;
        float halfLineHeight = (p.ascent() + p.descent()) / 2.0f;
        int rowCenter = (rowHeight / 2) + headerHeight2;
        int day = 1;
        int col = findDayOffset();
        while (day <= this.mDaysInMonth) {
            int colCenter = (colWidth2 * col) + (colWidth2 / 2);
            if (isLayoutRtl()) {
                colCenterRtl = this.mPaddedWidth - colCenter;
            } else {
                colCenterRtl = colCenter;
            }
            int stateMask2 = 0;
            boolean isDayEnabled = isDayEnabled(day);
            if (isDayEnabled) {
                stateMask2 = 0 | 8;
            }
            boolean isDayActivated = this.mActivatedDay == day;
            boolean isDayHighlighted = this.mHighlightedDay == day;
            if (isDayActivated) {
                int stateMask3 = stateMask2 | 32;
                if (isDayHighlighted) {
                    headerHeight = headerHeight2;
                    paint = this.mDayHighlightSelectorPaint;
                } else {
                    headerHeight = headerHeight2;
                    paint = this.mDaySelectorPaint;
                }
                colWidth = colWidth2;
                int stateMask4 = this.mDaySelectorRadius;
                canvas.drawCircle(colCenterRtl, rowCenter, stateMask4, paint);
                stateMask2 = stateMask3;
            } else {
                headerHeight = headerHeight2;
                colWidth = colWidth2;
                if (isDayHighlighted) {
                    int stateMask5 = stateMask2 | 16;
                    if (isDayEnabled) {
                        stateMask = stateMask5;
                        canvas.drawCircle(colCenterRtl, rowCenter, this.mDaySelectorRadius, this.mDayHighlightPaint);
                    } else {
                        stateMask = stateMask5;
                    }
                    stateMask2 = stateMask;
                }
            }
            boolean isDayToday = this.mToday == day;
            if (isDayToday && !isDayActivated) {
                dayTextColor = this.mDaySelectorPaint.getColor();
            } else {
                int[] stateSet = StateSet.get(stateMask2);
                dayTextColor = this.mDayTextColor.getColorForState(stateSet, 0);
            }
            p.setColor(dayTextColor);
            canvas.drawText(this.mDayFormatter.format(day), colCenterRtl, rowCenter - halfLineHeight, p);
            col++;
            if (col == 7) {
                rowCenter += rowHeight;
                col = 0;
            }
            day++;
            headerHeight2 = headerHeight;
            colWidth2 = colWidth;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isDayEnabled(int day) {
        return day >= this.mEnabledDayStart && day <= this.mEnabledDayEnd;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isValidDayOfMonth(int day) {
        return day >= 1 && day <= this.mDaysInMonth;
    }

    private static boolean isValidDayOfWeek(int day) {
        return day >= 1 && day <= 7;
    }

    private static boolean isValidMonth(int month) {
        return month >= 0 && month <= 11;
    }

    public void setSelectedDay(int dayOfMonth) {
        this.mActivatedDay = dayOfMonth;
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    public void setFirstDayOfWeek(int weekStart) {
        if (isValidDayOfWeek(weekStart)) {
            this.mWeekStart = weekStart;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    void setMonthParams(int selectedDay, int month, int year, int weekStart, int enabledDayStart, int enabledDayEnd) {
        this.mActivatedDay = selectedDay;
        if (isValidMonth(month)) {
            this.mMonth = month;
        }
        this.mYear = year;
        this.mCalendar.set(2, this.mMonth);
        this.mCalendar.set(1, this.mYear);
        this.mCalendar.set(5, 1);
        this.mDayOfWeekStart = this.mCalendar.get(7);
        if (isValidDayOfWeek(weekStart)) {
            this.mWeekStart = weekStart;
        } else {
            this.mWeekStart = this.mCalendar.getFirstDayOfWeek();
        }
        Calendar today = Calendar.getInstance();
        this.mToday = -1;
        this.mDaysInMonth = getDaysInMonth(this.mMonth, this.mYear);
        for (int i = 0; i < this.mDaysInMonth; i++) {
            int day = i + 1;
            if (sameDay(day, today)) {
                this.mToday = day;
            }
        }
        int i2 = this.mDaysInMonth;
        this.mEnabledDayStart = MathUtils.constrain(enabledDayStart, 1, i2);
        this.mEnabledDayEnd = MathUtils.constrain(enabledDayEnd, this.mEnabledDayStart, this.mDaysInMonth);
        updateMonthYearLabel();
        updateDayOfWeekLabels();
        this.mTouchHelper.invalidateRoot();
        invalidate();
    }

    private static int getDaysInMonth(int month, int year) {
        switch (month) {
            case 0:
            case 2:
            case 4:
            case 6:
            case 7:
            case 9:
            case 11:
                return 31;
            case 1:
                return (year % 4 != 0 || (year % 100 == 0 && year % 400 != 0)) ? 28 : 29;
            case 3:
            case 5:
            case 8:
            case 10:
                return 30;
            default:
                throw new IllegalArgumentException("Invalid Month");
        }
    }

    private boolean sameDay(int day, Calendar today) {
        return this.mYear == today.get(1) && this.mMonth == today.get(2) && day == today.get(5);
    }

    @Override // android.view.View
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int preferredHeight = (this.mDesiredDayHeight * 6) + this.mDesiredDayOfWeekHeight + this.mDesiredMonthHeight + getPaddingTop() + getPaddingBottom();
        int preferredWidth = (this.mDesiredCellWidth * 7) + getPaddingStart() + getPaddingEnd();
        int resolvedWidth = resolveSize(preferredWidth, widthMeasureSpec);
        int resolvedHeight = resolveSize(preferredHeight, heightMeasureSpec);
        setMeasuredDimension(resolvedWidth, resolvedHeight);
    }

    @Override // android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        requestLayout();
    }

    @Override // android.view.View
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        if (!changed) {
            return;
        }
        int w = right - left;
        int h = bottom - top;
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        int paddedRight = w - paddingRight;
        int paddedBottom = h - paddingBottom;
        int paddedWidth = paddedRight - paddingLeft;
        int paddedHeight = paddedBottom - paddingTop;
        if (paddedWidth != this.mPaddedWidth && paddedHeight != this.mPaddedHeight) {
            this.mPaddedWidth = paddedWidth;
            this.mPaddedHeight = paddedHeight;
            int measuredPaddedHeight = (getMeasuredHeight() - paddingTop) - paddingBottom;
            float scaleH = paddedHeight / measuredPaddedHeight;
            int monthHeight = (int) (this.mDesiredMonthHeight * scaleH);
            int cellWidth = this.mPaddedWidth / 7;
            this.mMonthHeight = monthHeight;
            this.mDayOfWeekHeight = (int) (this.mDesiredDayOfWeekHeight * scaleH);
            this.mDayHeight = (int) (this.mDesiredDayHeight * scaleH);
            this.mCellWidth = cellWidth;
            int maxSelectorWidth = (cellWidth / 2) + Math.min(paddingLeft, paddingRight);
            int w2 = this.mDayHeight;
            int maxSelectorHeight = (w2 / 2) + paddingBottom;
            int h2 = this.mDesiredDaySelectorRadius;
            this.mDaySelectorRadius = Math.min(h2, Math.min(maxSelectorWidth, maxSelectorHeight));
            this.mTouchHelper.invalidateRoot();
        }
    }

    private int findDayOffset() {
        int offset = this.mDayOfWeekStart - this.mWeekStart;
        if (this.mDayOfWeekStart < this.mWeekStart) {
            return offset + 7;
        }
        return offset;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public int getDayAtLocation(int x, int y) {
        int headerHeight;
        int paddedY;
        int paddedXRtl;
        int paddedX = x - getPaddingLeft();
        if (paddedX < 0 || paddedX >= this.mPaddedWidth || (paddedY = y - getPaddingTop()) < (headerHeight = this.mMonthHeight + this.mDayOfWeekHeight) || paddedY >= this.mPaddedHeight) {
            return -1;
        }
        if (isLayoutRtl()) {
            paddedXRtl = this.mPaddedWidth - paddedX;
        } else {
            paddedXRtl = paddedX;
        }
        int row = (paddedY - headerHeight) / this.mDayHeight;
        int col = (paddedXRtl * 7) / this.mPaddedWidth;
        int index = (row * 7) + col;
        int day = (index + 1) - findDayOffset();
        if (!isValidDayOfMonth(day)) {
            return -1;
        }
        return day;
    }

    public boolean getBoundsForDay(int id, Rect outBounds) {
        int left;
        if (!isValidDayOfMonth(id)) {
            return false;
        }
        int index = (id - 1) + findDayOffset();
        int col = index % 7;
        int colWidth = this.mCellWidth;
        if (isLayoutRtl()) {
            left = (getWidth() - getPaddingRight()) - ((col + 1) * colWidth);
        } else {
            int left2 = getPaddingLeft();
            left = left2 + (col * colWidth);
        }
        int row = index / 7;
        int rowHeight = this.mDayHeight;
        int headerHeight = this.mMonthHeight + this.mDayOfWeekHeight;
        int top = getPaddingTop() + headerHeight + (row * rowHeight);
        outBounds.set(left, top, left + colWidth, top + rowHeight);
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean onDayClicked(int day) {
        if (!isValidDayOfMonth(day) || !isDayEnabled(day)) {
            return false;
        }
        if (this.mOnDayClickListener != null) {
            Calendar date = Calendar.getInstance();
            date.set(this.mYear, this.mMonth, day);
            this.mOnDayClickListener.onDayClick(this, date);
        }
        this.mTouchHelper.sendEventForVirtualView(day, 1);
        return true;
    }

    @Override // android.view.View
    public PointerIcon onResolvePointerIcon(MotionEvent event, int pointerIndex) {
        int pointerIcon;
        if (!isEnabled()) {
            return null;
        }
        if (event.isFromSource(8194)) {
            int x = (int) (event.getX() + 0.5f);
            int y = (int) (event.getY() + 0.5f);
            int dayUnderPointer = getDayAtLocation(x, y);
            if (dayUnderPointer >= 0) {
                if (Flags.enableArrowIconOnHoverWhenClickable()) {
                    pointerIcon = 1000;
                } else {
                    pointerIcon = 1002;
                }
                return PointerIcon.getSystemIcon(getContext(), pointerIcon);
            }
        }
        return super.onResolvePointerIcon(event, pointerIndex);
    }

    private class MonthViewTouchHelper extends ExploreByTouchHelper {
        private static final String DATE_FORMAT = "dd MMMM yyyy";
        private final Calendar mTempCalendar;
        private final Rect mTempRect;

        public MonthViewTouchHelper(View host) {
            super(host);
            this.mTempRect = new Rect();
            this.mTempCalendar = Calendar.getInstance();
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected int getVirtualViewAt(float x, float y) {
            int day = SimpleMonthView.this.getDayAtLocation((int) (x + 0.5f), (int) (0.5f + y));
            if (day != -1) {
                return day;
            }
            return Integer.MIN_VALUE;
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void getVisibleVirtualViews(IntArray virtualViewIds) {
            for (int day = 1; day <= SimpleMonthView.this.mDaysInMonth; day++) {
                virtualViewIds.add(day);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateEventForVirtualView(int virtualViewId, AccessibilityEvent event) {
            event.setContentDescription(getDayDescription(virtualViewId));
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected void onPopulateNodeForVirtualView(int virtualViewId, AccessibilityNodeInfo node) {
            boolean hasBounds = SimpleMonthView.this.getBoundsForDay(virtualViewId, this.mTempRect);
            if (!hasBounds) {
                this.mTempRect.setEmpty();
                node.setContentDescription("");
                node.setBoundsInParent(this.mTempRect);
                node.setVisibleToUser(false);
                return;
            }
            node.setText(getDayText(virtualViewId));
            node.setContentDescription(getDayDescription(virtualViewId));
            if (virtualViewId == SimpleMonthView.this.mToday) {
                RelativeDateTimeFormatter fmt = RelativeDateTimeFormatter.getInstance();
                node.setStateDescription(fmt.format(RelativeDateTimeFormatter.Direction.THIS, RelativeDateTimeFormatter.AbsoluteUnit.DAY));
            }
            if (virtualViewId == SimpleMonthView.this.mActivatedDay) {
                node.setSelected(true);
            }
            node.setBoundsInParent(this.mTempRect);
            boolean isDayEnabled = SimpleMonthView.this.isDayEnabled(virtualViewId);
            if (isDayEnabled) {
                node.addAction(AccessibilityNodeInfo.AccessibilityAction.ACTION_CLICK);
            }
            node.setEnabled(isDayEnabled);
            node.setClickable(true);
            if (virtualViewId == SimpleMonthView.this.mActivatedDay) {
                node.setChecked(true);
            }
        }

        @Override // com.android.internal.widget.ExploreByTouchHelper
        protected boolean onPerformActionForVirtualView(int virtualViewId, int action, Bundle arguments) {
            switch (action) {
                case 16:
                    return SimpleMonthView.this.onDayClicked(virtualViewId);
                default:
                    return false;
            }
        }

        private CharSequence getDayDescription(int id) {
            if (SimpleMonthView.this.isValidDayOfMonth(id)) {
                this.mTempCalendar.set(SimpleMonthView.this.mYear, SimpleMonthView.this.mMonth, id);
                return DateFormat.format(DATE_FORMAT, this.mTempCalendar.getTimeInMillis());
            }
            return "";
        }

        private CharSequence getDayText(int id) {
            if (SimpleMonthView.this.isValidDayOfMonth(id)) {
                return SimpleMonthView.this.mDayFormatter.format(id);
            }
            return null;
        }
    }
}
