package com.example.onesquare;

import android.content.ContentValues;
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
import android.widget.Toast;

import com.example.onesquare.model.CheckIn;
import com.example.onesquare.model.CheckInContract.CheckInEntry;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


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
            System.exit(0);
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
        private static final String STRING_EMPTY = "";
        private static final String STRING_DATE_FORMAT = "M/d/y";

        private EditText mPlaceEdit;
        private EditText mAddressEdit;
        private EditText mDateEdit;
        private EditText mURLEdit;
        private Button mPictureURLButton;
        private CheckBox mIsFavoriteCheckBox;
        private Button mClearButton;
        private Button mSaveButton;
        private Uri mPictureURL;

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
            mIsFavoriteCheckBox =
                    (CheckBox) rootView.findViewById(R.id.add_check_in_is_favorite);
            mClearButton = (Button) rootView.findViewById(R.id.add_check_in_clear_button);
            mSaveButton = (Button) rootView.findViewById(R.id.add_check_in_save_button);
            
            setUpInputValidation(mPlaceEdit, mAddressEdit, mDateEdit, mURLEdit);
            setUpPicturePicker(mPictureURLButton);
            setUpClearButton(mClearButton);
            setUpSaveButton(mSaveButton);

            return rootView;
        }

        private void setUpSaveButton(Button saveButton) {
            saveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String place = mPlaceEdit.getEditableText().toString();
                    String address = mAddressEdit.getEditableText().toString();
                    String dateString = mDateEdit.getEditableText().toString();
                    SimpleDateFormat dateFormat = new SimpleDateFormat(STRING_DATE_FORMAT);
                    Date date = null;
                    String urlString = mURLEdit.getEditableText().toString();
                    Uri url = Uri.parse(urlString);
                    Uri pictureUrl = mPictureURL;
                    boolean isFavorite = mIsFavoriteCheckBox.isChecked();

                    try {
                        date = dateFormat.parse(dateString);
                    } catch (ParseException e) {
                        Log.e(TAG, "Unable to parse date.", e);
                    }

                    CheckIn newCheckIn =
                            CheckIn.create(place, address, date, url, pictureUrl, isFavorite);

                    Log.d(TAG, "Check in created: " + newCheckIn);

                    Toast.makeText(
                            getActivity(),
                            R.string.message_check_in_created,
                            Toast.LENGTH_SHORT
                    ).show();

                    ContentValues values = new ContentValues();
                    values.put(CheckInEntry.PLACE, place);
                    values.put(CheckInEntry.ADDRESS, address);
                    values.put(CheckInEntry.DATE, date.toString());
                    values.put(CheckInEntry.URL, url.toString());
                    values.put(CheckInEntry.PICTURE_URL,
                            pictureUrl == null ? "http://placehold.it/500" : pictureUrl.toString());
                    values.put(CheckInEntry.IS_FAVORITE, isFavorite);

                    getActivity()
                            .getContentResolver()
                            .insert(
                                    CheckInEntry.CONTENT_URI,
                                    values
                            );

                    getActivity()
                            .onBackPressed();
                }
            });
        }

        private void setUpClearButton(Button clearButton) {
            clearButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPlaceEdit.setText(STRING_EMPTY);
                    mAddressEdit.setText(STRING_EMPTY);
                    mDateEdit.setText(STRING_EMPTY);
                    mURLEdit.setText(STRING_EMPTY);
                    mIsFavoriteCheckBox.setChecked(false);
                }
            });
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
                        mPictureURL = data.getData();

                        // TODO-francisbrito: Notify user picture was selected successfully.
                    }
            }
        }
    }
}
