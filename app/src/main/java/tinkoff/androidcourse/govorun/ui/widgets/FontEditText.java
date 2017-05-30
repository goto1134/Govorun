package tinkoff.androidcourse.govorun.ui.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.util.AttributeSet;

import tinkoff.androidcourse.govorun.R;


public class FontEditText extends TextInputEditText {

    public FontEditText(Context context) {
        super(context);
    }

    public FontEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public FontEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.Fonts);

        if (typedArray != null) {
            boolean isUbuntu = typedArray.getBoolean(R.styleable.Fonts_ubuntuFont, false);
            if (isUbuntu) {
                Typeface typeFace = Typeface.createFromAsset(context.getAssets(),
                        "fonts/UbuntuMono-R.ttf");
                typedArray.recycle();
                super.setTypeface(typeFace);
            }
        }
    }

}

