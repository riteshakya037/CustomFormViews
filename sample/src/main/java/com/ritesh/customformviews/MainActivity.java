package com.ritesh.customformviews;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.ritesh.customfieldviews.CustomDateView;
import com.ritesh.customfieldviews.CustomSpinnerView;
import com.ritesh.customfieldviews.CustomTextView;
import com.ritesh.customfieldviews.validators.OutputListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements MainPresenterView, MainPresenterMapper, OutputListener {

    // Basic implementation
    @BindView(R.id.activity_main_text_email)
    protected CustomTextView emailView;
    // Implementation on Async Callbacks
    @BindView(R.id.activity_main_text_async_check)
    protected CustomTextView textAscync;
    // Implementation with use of resource file for list
    @BindView(R.id.activity_main_number_of_dependents)
    protected CustomSpinnerView noOdDependents;
    // Implementation with programmatically loaded data
    @BindView(R.id.activity_main_programmatically_load)
    protected CustomSpinnerView programmaticallyLoad;
    // Basic Implementation
    @BindView(R.id.activity_main_date_of_birth)
    protected CustomDateView dateOfBirth;
    // Next button to show everything is valid.
    @BindView(R.id.activity_main_text_validity_button)
    protected Button validityButton;
    private MainPresenter mPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mPresenter = new MainPresenterImpl(this, this);
        // Bind this to ValidityBase
        mPresenter.bind(this);
        // Add data to CustomSpinnerView programmatically
        mPresenter.setData();
    }

    @Override
    public Context getContext() {
        return this;
    }

    /**
     * @param allValid {@code true} if all the calidators are positive. {@code false} Otherwise.
     */
    @Override
    public void finalValidity(boolean allValid) {
        validityButton.setVisibility(allValid ? View.VISIBLE : View.GONE);

    }
}
