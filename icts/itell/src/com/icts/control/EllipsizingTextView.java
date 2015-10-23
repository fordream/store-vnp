package com.icts.control;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.StaticLayout;
import android.text.TextUtils.TruncateAt;
import android.util.AttributeSet;
import android.widget.TextView;

/** This class use to limit the number lines in text view 
 * if length of content insert to text view > 3, use "..."
 * @author Luong
 *
 */
public class EllipsizingTextView extends TextView {
    private static final String ELLIPSIS = "...";

    //private int color = getResources().getColor(R.drawable.line);
    //private int w = 0;
  	//private int h =0;
    public interface EllipsizeListener {
        void ellipsizeStateChanged(boolean ellipsized);
    } 

    private final List<EllipsizeListener> ellipsizeListeners = new ArrayList<EllipsizeListener>();
    private boolean isEllipsized;
    private boolean isStale;
    private boolean programmaticChange;
    private String fullText;
    private int maxLines = -1;
    private float lineSpacingMultiplier = 1.0f;
    private float lineAdditionalVerticalPadding = 0.0f;

    public EllipsizingTextView(Context context) {
        super(context);
    }

    public EllipsizingTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public EllipsizingTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void addEllipsizeListener(EllipsizeListener listener) {
        if (listener == null) {
            throw new NullPointerException();
        }
        ellipsizeListeners.add(listener);
    }

    public void removeEllipsizeListener(EllipsizeListener listener) {
        ellipsizeListeners.remove(listener);
    }

    public boolean isEllipsized() {
        return isEllipsized;
    }

    @Override
    public void setMaxLines(int maxLines) {
        super.setMaxLines(maxLines);
        this.maxLines = maxLines;
        isStale = true;
    }

    public int getMaxLine() {
        return maxLines;
    }

    @Override
    public void setLineSpacing(float add, float mult) {
        this.lineAdditionalVerticalPadding = add;
        this.lineSpacingMultiplier = mult;
        super.setLineSpacing(add, mult);
    }

    @Override
    protected void onTextChanged(CharSequence text, int start, int before, int after) {
        super.onTextChanged(text, start, before, after);
        if (!programmaticChange) {
            fullText = text.toString();
            isStale = true;
        }
    }
    
    @Override
    protected void onDraw(Canvas canvas) {
        if (isStale) {
            super.setEllipsize(null);
            resetText();
        }
        //draw line
       /* Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(1);
		paint.setAntiAlias(true);
		w = this.getMeasuredWidth();
		h = this.getMeasuredHeight();
		canvas.drawLine(0, h, w, h, paint);*/
        super.onDraw(canvas);
    }

    /**
     * If text is too long, it will be cut to decrease its length
     */
    private void resetText() {
        int maxLines = getMaxLine();
        String workingText = fullText;
        boolean ellipsized = false;
        if (maxLines != -1) {
            Layout layout = createWorkingLayout(workingText);
            if (layout.getLineCount() > maxLines) {
                workingText = fullText.substring(0, layout.getLineEnd(maxLines - 1)).trim();
                while (createWorkingLayout(workingText + ELLIPSIS).getLineCount() > maxLines) {
                	workingText = workingText.substring(0, workingText.length()-3);
                }
                workingText = workingText + ELLIPSIS;
                ellipsized = true;
            }
        }
        if (!workingText.equals(getText())) {
            programmaticChange = true;
            try {
                setText(workingText);
            } finally {
                programmaticChange = false;
            }
        }
        isStale = false;
        if (ellipsized != isEllipsized) {
            isEllipsized = ellipsized;
            for (EllipsizeListener listener : ellipsizeListeners) {
                listener.ellipsizeStateChanged(ellipsized);
            }
        }
    }

    private Layout createWorkingLayout(String workingText) {
        return new StaticLayout(workingText, getPaint(), 
        						getWidth() - getPaddingLeft() - getPaddingRight(),
        						Alignment.ALIGN_NORMAL, lineSpacingMultiplier, 
        						lineAdditionalVerticalPadding, false);
    }
}