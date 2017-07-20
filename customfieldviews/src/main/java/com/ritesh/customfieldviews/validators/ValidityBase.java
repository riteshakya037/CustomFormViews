package com.ritesh.customfieldviews.validators;

/**
 * @author Ritesh Shakya
 */
public interface ValidityBase {
    boolean getValidity();

    void setValidity(boolean validity);

    int getId();

    void reset();
}
