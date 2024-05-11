package com.example.to_do_app

import android.app.Activity
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import Model.appModel
import com.example.to_do_app.R
import Utils.DataBaseHelper
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddNewTask : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "AddNewTask"
        fun newInstance(): AddNewTask {
            return AddNewTask()
        }
    }

    private lateinit var mEditText: EditText
    private lateinit var mSaveButton: Button
    private lateinit var myDb: DataBaseHelper

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.add_task_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mEditText = view.findViewById(R.id.editTxt)
        mSaveButton = view.findViewById(R.id.save_btn)

        myDb = DataBaseHelper(requireActivity())

        var isUpdate = false

        arguments?.let { bundle ->
            isUpdate = true
            val task = bundle.getString("task")
            mEditText.setText(task)

            if (task?.length ?: 0 > 0) {
                mSaveButton.isEnabled = false
            }
        }

        mEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().isEmpty()) {
                    mSaveButton.isEnabled = false
                    mSaveButton.setBackgroundColor(Color.GRAY)
                } else {
                    mSaveButton.isEnabled = true
                    mSaveButton.setBackgroundColor(resources.getColor(R.color.primary))
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        val finalIsUpdate = isUpdate
        mSaveButton.setOnClickListener {
            val text = mEditText.text.toString()

            if (finalIsUpdate) {
                arguments?.getInt("id")?.let { id ->
                    myDb.updateTask(id, text)
                }
            } else {
                val item = appModel().apply {
                    task = text
                    status = 0
                }
                myDb.insertTask(item)
            }
            dismiss()
        }
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        val activity = requireActivity()
        if (activity is OnDialogCloseListener) {
            (activity as OnDialogCloseListener).onDialogClose(dialog)
        }
    }
}