package com.ritesh.customfieldviews.validators;

import android.support.annotation.IdRes;

import com.ritesh.customfieldviews.CustomDateView;
import com.ritesh.customfieldviews.CustomSpinnerView;
import com.ritesh.customfieldviews.CustomTextView;
import com.ritesh.customfieldviews.models.BaseSpinner;

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
