package com.fatonhoti.flags

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatDialogFragment

class IntegrityDialog(private val messageType: String) : AppCompatDialogFragment() {

    private val integrityMessage =
            "The application \"Flags\" and its creator does not store nor send any data to the creator or associated third parties in any shape or form.\n" +
            "\n" +
            "If you have any questions you can contact the creator/developer at hotifaton@outlook.com, specify 'Flags' in the title."
    private val ackMessage = "The API used to fetch all the information of the different continents and countries is \"worldCountryData\".\n" +
            "\n" +
            "Check it out at \"https://github.com/blongho/worldCountryData\"."

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val title: String
        val dialogMessage: String
        if(messageType == "INTEGRITY") {
            title = "Data Integrity Policy"
            dialogMessage = integrityMessage
        } else {
            title = "Acknowledgements"
            dialogMessage = ackMessage
        }
        return builder.setTitle(title)
            .setMessage(dialogMessage)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create()
    }

}