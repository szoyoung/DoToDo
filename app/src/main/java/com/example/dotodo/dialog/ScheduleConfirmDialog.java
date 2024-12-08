package com.example.dotodo.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.annotation.NonNull;

import com.example.dotodo.R;

public class ScheduleConfirmDialog extends Dialog {
    private final DialogListener listener;

    public interface DialogListener {
        void onConfirm();
        void onCancel();
    }

    public ScheduleConfirmDialog(@NonNull Context context, DialogListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_schedule_confirm);

        Button confirmButton = findViewById(R.id.btn_confirm);
        Button cancelButton = findViewById(R.id.btn_cancel);

        confirmButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onConfirm();
            }
            dismiss();
        });

        cancelButton.setOnClickListener(v -> {
            if (listener != null) {
                listener.onCancel();
            }
            dismiss();
        });
    }
}