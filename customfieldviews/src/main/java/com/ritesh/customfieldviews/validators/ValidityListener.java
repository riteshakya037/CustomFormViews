package com.ritesh.customfieldviews.validators;

/**
 * @author Ritesh Shakya
 */
public interface ValidityListener {
    void checkValidity();

    void addValidators(OutputListener validityListener, ValidityBase... validityBase);

    boolean validateAll();
}
