package com.ritesh.customformviews;

import com.ritesh.customfieldviews.validators.OutputListener;
import com.ritesh.customfieldviews.validators.ValidityListener;

/**
 * Created by Ritesh on 0029, May 29, 2017.
 */

interface MainPresenter extends OutputListener, ValidityListener {
    void setData();
}
