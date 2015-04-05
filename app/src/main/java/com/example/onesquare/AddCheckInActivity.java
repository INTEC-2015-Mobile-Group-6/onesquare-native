package com.example.onesquare;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.FileNotFoundException;
import java.io.InputStream;


public class AddCheckInActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_check_in);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new AddCheckInFormFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_check_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class AddCheckInFormFragment extends Fragment {

        private static final int SELECT_PHOTO = 1;
        private static final String TAG = AddCheckInFormFragment.class.getSimpleName();

        private EditText mPlaceEdit;
        private EditText mAddressEdit;
        private EditText mDateEdit;
        private EditText mURLEdit;
        private Button mPictureURLButton;
        private CheckBox mIsFavoriteCheckBox;

        public AddCheckInFormFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_add_check_in, container, false);

            mPlaceEdit = (EditText) rootView.findViewById(R.id.add_check_in_place_name);
            mAddressEdit = (EditText) rootView.findViewById(R.id.add_check_in_place_address);
            mDateEdit = (EditText) rootView.findViewById(R.id.add_check_in_date);
            mURLEdit = (EditText) rootView.findViewById(R.id.add_check_in_url);
            mPictureURLButton =
                    (Button) rootView.findViewById(R.id.add_check_in_add_picture_button);
            mIsFavoriteCheckBox = (CheckBox) rootView.findViewById(R.id.add_check_in_is_favorite);
            
            setUpInputValidation(mPlaceEdit, mAddressEdit, mDateEdit, mURLEdit);
            setUpPicturePicker(mPictureURLButton);

            return rootView;
        }

        private void setUpPicturePicker(Button pictureURLButton) {
            pictureURLButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent pickPictureIntent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPictureIntent, SELECT_PHOTO);
                }
            });
        }

        private void setUpInputValidation(EditText... editTexts) {
            for (int i = 0; i < editTexts.length; i++) {
                editTexts[i].addTextChangedListener(new FieldIsRequiredValidator(editTexts[i]));
            }
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            switch (requestCode) {
                case SELECT_PHOTO:
                    if (resultCode == RESULT_OK) {
                        Uri imageUri = data.getData();

                        // TODO-francisbrito: Set check-in's picture URL to imageUri.
                    }
            }
        }
    }
}
