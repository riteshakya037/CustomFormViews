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
import java.util.Calendar;
import java.util.List;

/**
 * @author Ritesh Shakya
 */
public class ValidityClassBase implements ValidityListener, CustomTextView.ServerValidator,
        CustomDateView.SelectionListener, CustomTextView.FocusListener,
        CustomSpinnerView.SpinnerListener<BaseSpinner> {

    private List<ValidityBase> validators;
    private OutputListener validityListener;

    @Override public void checkValidity() {
        validityListener.finalValidity(validateAll());
    }

    @Override public boolean validateAll() {
        for (ValidityBase base : validators) {
            if (!base.getValidity()) return false;
        }
        return true;
    }

    @Deprecated @Override
    public void addValidators(OutputListener validityListener, ValidityBase... validityBase) {
        this.validityListener = validityListener;
        validators = Arrays.asList(validityBase);
        for (ValidityBase base : validators) {
            if (base instanceof CustomTextView) {
                ((CustomTextView) base).addFocusValidator(getFocusListener(), this,
                        getServerValidator());
            } else if (base instanceof CustomDateView) {
                ((CustomDateView) base).addSelectionListener(getSelectionListener(), this);
            } else if (base instanceof CustomSpinnerView) {
                ((CustomSpinnerView) base).addListener(getSpinnerListener(), this);
            }
        }
    }

    @Override public void bind(OutputListener validityListener) {
        bind(validityListener, ((Activity) validityListener).getWindow().getDecorView());
    }

    @Override public void bind(OutputListener validityListener, View target) {
        if (target instanceof ViewGroup) {
            ArrayList<ValidityBase> arrayList = findAllCustomViews(target);
            addValidators(validityListener,
                    (ValidityBase[]) arrayList.toArray(new ValidityBase[arrayList.size()]));
        } else {
            addValidators(validityListener);
        }
    }

    private ArrayList<ValidityBase> findAllCustomViews(final View view) {
        if (view instanceof ValidityBase) {
            ArrayList<ValidityBase> result = new ArrayList<>();
            result.add((ValidityBase) view);
            return result;
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

    @SuppressWarnings("unchecked") protected <T> T getView(@IdRes int viewId, Class<T> aClass) {
        for (ValidityBase base : validators) {
            if (base.getId() == viewId && aClass.isInstance(base)) return (T) base;
        }
        return null;
    }

    /**
     * Enable TextChange listener for CustomTextViews*
     */
    private CustomTextView.FocusListener getFocusListener() {
        return this;
    }

    /**
     * Enable ServerValidator listener for CustomTextViews*
     */
    private CustomTextView.ServerValidator getServerValidator() {
        return this;
    }

    /**
     * Enable Spinner selection listener for CustomTextViews*
     */
    private CustomSpinnerView.SpinnerListener<BaseSpinner> getSpinnerListener() {
        return this;
    }

    /**
     * Enable DatePicker selection listener for CustomTextViews*
     */
    private CustomDateView.SelectionListener getSelectionListener() {
        return this;
    }

    @Override public void checkValidateFromServer(View view, String text, ServerListener listener) {
        throw new AssertionError("Override checkValidateFromServer in parent class.");
    }

    @Override public CustomTextView.Properties getTextErrorProperties(CustomTextView view) {
        throw new AssertionError("Override getTextErrorProperties in parent class.");
    }

    @Override public boolean getDateValidity(CustomDateView view, Calendar date) {
        throw new AssertionError("Override getDateValidity in parent class.");
    }

    @Override public long setMaxDate(CustomDateView view) {
        return 0;
    }

    @Override public long setMinDate(CustomDateView view) {
        return 0;
    }

    @Override public boolean getTextValidity(CustomTextView view, String text) {
        throw new AssertionError("Override getTextValidity in parent class.");
    }

    @Override public void getSpinnerValidity(CustomSpinnerView view, BaseSpinner baseSpinner) {
        throw new AssertionError("Override getSpinnerValidity in parent class.");
    }
}
