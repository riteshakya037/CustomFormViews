package com.ritesh.customfieldviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Ritesh Shakya
 */

public class FormButton extends RelativeLayout {

    @BindView(R2.id.custom_form_button_button_icon) protected ImageView separatorIcon;

    public FormButton(Context context) {
        super(context);
        init(context, null);
    }

    public FormButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.FormButton);
        init(context, typedArray.getDrawable(R.styleable.FormButton_buttonIcon));
        typedArray.recycle();
    }

    private void init(Context context, Drawable icon) {
        View rootView = inflate(context, R.layout.custom_form_button, this);
        ButterKnife.bind(this, rootView);
        if (icon != null && separatorIcon != null) separatorIcon.setImageDrawable(icon);
    }
}