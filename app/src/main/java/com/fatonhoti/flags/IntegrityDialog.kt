/*
MIT License

Copyright (c) 2022 Faton Hoti

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NON-INFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

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