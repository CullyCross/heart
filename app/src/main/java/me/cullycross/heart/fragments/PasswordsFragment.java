package me.cullycross.heart.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.cullycross.heart.R;
import me.cullycross.heart.adapters.PasswordsAdapter;

public class PasswordsFragment extends Fragment {

    @Bind(R.id.list_view_log_pass_pairs)
    protected RecyclerView mRecyclerView;

    private static final String PASSWORD = "password";

    //@BindArray(R.array.test_strings)
    private String[] mStrings;

    private OnFragmentInteractionListener mListener;

    private String mPassword;

    private LinearLayoutManager mLayoutManager;
    private PasswordsAdapter mAdapter;

    public static PasswordsFragment newInstance(String password) {
        PasswordsFragment fragment = new PasswordsFragment();
        Bundle args = new Bundle();
        args.putString(PASSWORD, password);

        fragment.setArguments(args);
        return fragment;
    }

    public PasswordsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mPassword = getArguments().getString(PASSWORD);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_passwords, container, false);
        ButterKnife.bind(this, view);

        initRecyclerView();

        return view;
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

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    ////////////////////////////////////////////////////////////
    // Private methods
    ////////////////////////////////////////////////////////////

    // Init methods

    private void initRecyclerView() {

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mStrings = getResources().getStringArray(R.array.test_strings);
        mAdapter = new PasswordsAdapter(mStrings, getActivity());
        mRecyclerView.setAdapter(mAdapter);
    }

    ////////////////////////////////////////////////////////////
    // Interfaces
    ////////////////////////////////////////////////////////////

    public interface OnFragmentInteractionListener {
        // if smth would be needed, I'll add here some methods
    }
}
