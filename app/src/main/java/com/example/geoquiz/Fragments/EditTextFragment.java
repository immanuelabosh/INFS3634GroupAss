package com.example.geoquiz.Fragments;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.geoquiz.R;


//This is my edit text fragment
//This is the dialog i use to get your search query
public class EditTextFragment extends DialogFragment {
    private EditText mEditText;

    // Defines the listener interface
    public interface EditTextFragmentListener {
        void onFinishEditDialog(String inputText);
    }
    public EditTextFragment() {
        // Empty constructor is required for EditTextFragment
        // Make sure not to add arguments to the constructor
        // Use `newInstance` instead as shown below
    }

    public static EditTextFragment newInstance(String title) {
        EditTextFragment frag = new EditTextFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_text, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Get field from view
        mEditText = (EditText) view.findViewById(R.id.txt_your_name);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Breed");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        mEditText.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        //this is to detect when the user clicks enter on the keyboard
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                boolean handled = false;
                //if the button they click is enter
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    handled = true;
                    //call send back result
                    sendBackResult();
                }
                return handled;
            }
        });
    }

    //on dismiss of the fragment, send the data back
    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        sendBackResult();
        super.onDismiss(dialog);
    }


    // Call this method to send the data back to the parent fragment
    public void sendBackResult() {
        // Notice the use of `getTargetFragment` which will be set when the dialog is displayed
        EditTextFragmentListener listener = (EditTextFragmentListener) getTargetFragment();
        listener.onFinishEditDialog(mEditText.getText().toString());
        dismiss();
    }
}