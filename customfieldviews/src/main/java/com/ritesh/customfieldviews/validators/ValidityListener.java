package com.ritesh.customfieldviews.validators;

import android.view.View;

/**
 * @author Ritesh Shakya
 */
public interface ValidityListener {
    void checkValidity();

    void addValidators(OutputListener validityListener, ValidityBase... validityBase);

    void bind(OutputListener validityListener);

    void bind(OutputListener validityListener, View target);

    boolean validateAll();
}
