package com.example.pocketlibray;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Objects;

public class RespondFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        View view = getLayoutInflater().inflate(R.layout.fragment_respond, null);
        TextView title = view.getRootView().findViewById(R.id.leave_respond);
        title.setText(requireArguments().getString("title", "Leave your respond!"));
        EditText emailText = view.getRootView().findViewById(R.id.emailET);
        EditText messageText = view.getRootView().findViewById(R.id.messageET);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setCustomTitle(view)
                .setPositiveButton("SEND", null)
                .setNegativeButton("CANCEL", null);
        AlertDialog alertDialog = builder.create();
        alertDialog.setOnShowListener(dialogInterface -> {
            Button emailButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
            emailButton.setOnClickListener(buttonView -> {
                if (TextUtils.isEmpty(emailText.getText().toString()) || TextUtils.isEmpty(messageText.getText().toString())) {
                    if (TextUtils.isEmpty(emailText.getText().toString())) {
                        emailText.setError("Enter data!");
                    }
                    if (TextUtils.isEmpty(messageText.getText().toString())) {
                        messageText.setError("Enter data!");
                    }
                } else {
                    alertDialog.dismiss();
                }
            });
        });
        // Create the AlertDialog object and return it
        return alertDialog;
    }

    public static RespondFragment newInstance(String title) {
        RespondFragment respondFragment = new RespondFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        respondFragment.setArguments(args);
        return respondFragment;
    }
}