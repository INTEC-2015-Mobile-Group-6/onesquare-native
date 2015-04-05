package com.example.onesquare;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by francis on 03/30/15.
 */
public class FieldIsRequiredValidator implements TextWatcher {
    private static final String ERROR_FIELD_IS_EMPTY = "Field cannot be empty";

    private EditText mEditText;

    public FieldIsRequiredValidator(EditText editText) {
        mEditText = editText;

        setUpValidationTrigger();
    }

    private void setUpValidationTrigger() {
        mEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (!hasFocus && mEditText.isDirty()) {
                    afterTextChanged(mEditText.getText());
                }
            }
        });
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        String text = editable.toString();

        if (!isValid(text)) {
            mEditText.setError(ERROR_FIELD_IS_EMPTY);
        }
    }

    private boolean isValid(String text) {
        return !text.isEmpty();
    }
}
