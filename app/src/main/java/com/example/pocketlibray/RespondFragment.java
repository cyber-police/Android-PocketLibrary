package com.example.pocketlibray;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.DialogFragment;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class RespondFragment extends DialogFragment {

    private Context context;

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
                    if (NotificationManagerCompat.from(context).areNotificationsEnabled()) {
                        if (context instanceof MainActivity) {
                            fireNotification("Your responds help us make application better...");
                        } else if (context instanceof DocumentsDetails) {
                            fireNotification("Your message were successfully delivered...");
                        }
                    } else {
                        if (context instanceof MainActivity) {
                            Snackbar.make(((MainActivity) context).findViewById(R.id.idRV).getRootView(), R.string.respond_title, Snackbar.LENGTH_LONG).show();
                        } else if (context instanceof DocumentsDetails) {
                            Snackbar.make(((DocumentsDetails) context).findViewById(R.id.idBtnBuy).getRootView(), R.string.respond_title, Snackbar.LENGTH_LONG).show();
                        }
                    }
                }
            });
        });
        // Create the AlertDialog object and return it
        return alertDialog;
    }

    public void fireNotification(String subtext) {
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, getString(R.string.channel_id))
                .setSmallIcon(R.drawable.ic_stat_name)
                .setContentTitle(getString(R.string.respond_title))
                .setContentText(subtext)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        createNotificationChannel();
        notificationManager.notify(R.string.channel_id, notificationBuilder.build());
    }

    private void createNotificationChannel() {
        CharSequence name = getString(R.string.channel_name);
        String description = getString(R.string.channel_description);
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel(getString(R.string.channel_id), name, importance);
        channel.setDescription(description);
        NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

    public RespondFragment(Context context, String title) {
        this.context = context;
        Bundle args = new Bundle();
        args.putString("title", title);
        this.setArguments(args);
    }
}