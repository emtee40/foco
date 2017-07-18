package io.github.nfdz.foco.ui.dialogs;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.atomic.AtomicReference;

import io.github.nfdz.foco.R;

public class CreateDocDialog {

    public interface Callback {
        void onCreateDocument(String name);
    }

    public static void showDialog(Context context,
                                  final Callback callback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.dialog_create_doc_title);

        builder.setView(R.layout.dialog_create_doc);

        final AtomicReference<String> nameRef = new AtomicReference<>();
        builder.setPositiveButton(R.string.dialog_create_doc_ok_button, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                String name = nameRef.get();
                if (!TextUtils.isEmpty(name)) {
                    callback.onCreateDocument(name);
                    dialog.dismiss();
                }
            }
        });
        builder.setNegativeButton(android.R.string.cancel, null);
        final AlertDialog dialog = builder.create();
        dialog.show();

        final Button positiveButton = dialog.getButton(DialogInterface.BUTTON_POSITIVE);
        positiveButton.setEnabled(false);
        EditText nameInput = (EditText) dialog.findViewById(R.id.dialog_create_doc_name);
        nameInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // nothing to do
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nameRef.set(s.toString());
            }
            @Override
            public void afterTextChanged(Editable s) {
                positiveButton.setEnabled(s.length() > 0);
            }
        });

    }
}
