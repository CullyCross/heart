package me.cullycross.heart.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.cullycross.heart.R;
import me.cullycross.heart.users.UserProfile;

/**
 * Created by: cullycross
 * Date: 8/1/15
 * For my shining stars!
 */
public class WelcomeFragment extends Fragment {

    @Bind(R.id.text_view_welcome)
    protected TextView mWelcomeText;

    private OnFragmentInteractionListener mListener;

    public static WelcomeFragment newInstance() {
        WelcomeFragment fragment = new WelcomeFragment();
        Bundle args = new Bundle();

        // set arguments here

        fragment.setArguments(args);
        return fragment;
    }

    @OnClick(R.id.text_view_welcome)
    public void welcomeTextOnClick() {
        if (mListener != null) {
            mListener.onWelcomeTextClicked();
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_welcome, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mListener != null) {
            mListener.onFragmentStart();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mListener != null) {
            mListener.onFragmentPause();
        }
    }

    ////////////////////////////////////////////////////////////
    // Interfaces
    ////////////////////////////////////////////////////////////

    public interface OnFragmentInteractionListener {
        void onFragmentStart();
        void onWelcomeTextClicked();
        void onFragmentPause();
    }
}
