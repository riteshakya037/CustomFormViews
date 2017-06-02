package com.ritesh.customfieldviews.validators;

import android.app.Activity;
import android.support.annotation.IdRes;
import android.view.View;
import android.view.ViewGroup;

import com.ritesh.customfieldviews.CustomDateView;
import com.ritesh.customfieldviews.CustomSpinnerView;
import com.ritesh.customfieldviews.CustomTextView;
import com.ritesh.customfieldviews.models.BaseSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class ValidityClassBase implements ValidityListener {

    private List<ValidityBase> validators;
    private OutputListener validityListener;

    @Override
    public void checkValidity() {
        validityListener.finalValidity(validateAll());
    }

    @Override
    public boolean validateAll() {
        for (ValidityBase base : validators) {
            if (!base.getValidity())
                return false;
        }
        return true;
    }

    @Deprecated
    @Override
    public void addValidators(OutputListener validityListener, ValidityBase... validityBase) {
        this.validityListener = validityListener;
        validators = Arrays.asList(validityBase);
        for (ValidityBase base : validators) {
            if (base instanceof CustomTextView) {
                ((CustomTextView) base).addFocusValidator(getFocusListener(), this, getServerValidator());
            } else if (base instanceof CustomDateView) {
                ((CustomDateView) base).addSelectionListener(getSelectionListener(), this);
            } else if (base instanceof CustomSpinnerView) {
                ((CustomSpinnerView) base).addListener(getSpinnerListener(), this);
            }
        }
    }

    @Override
    public void bind(OutputListener validityListener) {
        bind(validityListener, ((Activity) validityListener).getWindow().getDecorView());
    }

    @Override
    public void bind(OutputListener validityListener, View target) {
        if (target instanceof ViewGroup) {
            ArrayList<ValidityBase> arrayList = findAllCustomViews((ViewGroup) target);
            addValidators(validityListener, (ValidityBase[]) arrayList.toArray(new ValidityBase[arrayList.size()]));
        } else {
            addValidators(validityListener);
        }
    }

    private ArrayList<ValidityBase> findAllCustomViews(final View view) {
        if (view instanceof ValidityBase) {
            return new ArrayList<ValidityBase>() {{
                add((ValidityBase) view);
            }};
        } else if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            ArrayList<ValidityBase> result = new ArrayList<>();
            int count = viewGroup.getChildCount();
            for (int i = 0; i < count; i++) {
                result.addAll(findAllCustomViews(viewGroup.getChildAt(i)));
            }
            return result;
        }
        return new ArrayList<>();
    }

    protected CustomTextView.ServerValidator getServerValidator() {
        return null;
    }

    protected ValidityBase getView(@IdRes int viewId) {
        for (ValidityBase base : validators) {
            if (base.getId() == viewId)
                return base;
        }
        return null;
    }


    protected CustomSpinnerView.SpinnerListener<BaseSpinner> getSpinnerListener() {
        return null;
    }

    protected CustomDateView.SelectionListener getSelectionListener() {
        return null;
    }

    protected CustomTextView.FocusListener getFocusListener() {
        return null;
    }
}
