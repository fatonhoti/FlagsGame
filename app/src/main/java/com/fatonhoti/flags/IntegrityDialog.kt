package com.fatonhoti.flags

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class IntegrityDialog : AppCompatDialogFragment() {

    private val dialogMessage =
            "The application \"Flags\" and its creator does not store nor send any data to the creator or associated third parties in any shape or form.\n" +
            "\n" +
            "If you have any questions you can contact the creator/developer at hotifaton@outlook.com, specify 'Flags' in the title."

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Data Integrity Policy")
            .setMessage(dialogMessage)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
        return builder.create()
    }

}