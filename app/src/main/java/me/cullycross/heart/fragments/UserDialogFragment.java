package me.cullycross.heart.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.heart.R;

/**
 * Created by: cullycross
 * Date: 7/23/15
 * For my shining stars!
 */
public class UserDialogFragment extends DialogFragment implements DialogInterface.OnClickListener {

    @Bind(R.id.dialog_title)
    protected TextView mTitle;

    @Bind({R.id.username, R.id.email, R.id.password, R.id.password2})
    protected List<EditText> mControls;

    private static final int USERNAME = 0;
    private static final int EMAIL = 1;
    private static final int PASSWORD = 2;
    private static final int PASSWORD_2 = 3;

    public static UserDialogFragment newInstance() {
        UserDialogFragment dialog = new UserDialogFragment();

        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View dialogView = inflater.inflate(R.layout.dialog_user_layout, null);

        ButterKnife.bind(this, dialogView);

        mTitle.setTypeface(
                Typeface.createFromAsset(
                        getActivity().getAssets(),
                        "fonts/Lobster-Regular.ttf"
                ));

        builder.setView(dialogView)
                // Add action buttons
                .setPositiveButton("Register", this)
                .setNegativeButton("Cancel", this);
        return builder.create();
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

        switch (which) {

            case DialogInterface.BUTTON_POSITIVE:
                registerUser();
                break;
            case DialogInterface.BUTTON_NEGATIVE:
                UserDialogFragment.this.getDialog().cancel();
                break;
        }
    }

    ////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////

    private void registerUser() {

        if(matchPasswords()) {


        } else {
            resetPasswords();
            Toast.makeText(
                    getActivity(),
                    "Passwords don't match",
                    Toast.LENGTH_SHORT).show();
        }
    }

    private boolean matchPasswords() {
        return mControls.get(PASSWORD)
                .getText().toString()
                .equals(
                        mControls.get(PASSWORD_2)
                                .getText().toString()
                );
    }

    private void resetPasswords() {
        mControls.get(PASSWORD).setText("");
        mControls.get(PASSWORD_2).setText("");
    }

    private void checkEmail() {

    }
}
