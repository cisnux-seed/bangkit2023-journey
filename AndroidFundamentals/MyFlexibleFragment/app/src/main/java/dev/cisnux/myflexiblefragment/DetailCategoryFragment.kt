package dev.cisnux.myflexiblefragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class DetailCategoryFragment : Fragment() {
    private lateinit var tvCategoryName: TextView
    private lateinit var tvCategoryDescription: TextView
    private lateinit var btnProfile: Button
    private lateinit var btnShowDialog: Button
    var description: String? = null
    internal var optionDialogListener: OptionDialogFragment.OnOptionDialogListener = object: OptionDialogFragment.OnOptionDialogListener{
        override fun onOptionChosen(text: String?) {
            Toast.makeText(requireContext(), text, Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_DESCRIPTION = "extra_description"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment.
        // the difference between attachToRoot false and true is when child view or fragment
        // created if that is true then you cannot use view parameter in the onViewCreated method
        // because the inflater not send the view argument after
        // layout fragment initialized.
        // otherwise you can use the view parameter to listen or
        // do configuration in the view object component.
        return inflater.inflate(R.layout.fragment_detail_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(view) {
            tvCategoryName = findViewById(R.id.tv_category_name)
            tvCategoryDescription = findViewById(R.id.tv_category_description)
            btnProfile = findViewById(R.id.btn_profile)
            btnShowDialog = findViewById(R.id.btn_show_dialog)
        }

        savedInstanceState?.let {
            val descFromBundle = savedInstanceState.getString(EXTRA_DESCRIPTION)
            description = descFromBundle
        }

        arguments?.let {
            val categoryName = it.getString(EXTRA_NAME)
            tvCategoryName.text = categoryName
            tvCategoryDescription.text = description
        }

        btnShowDialog.setOnClickListener {
            val mOptionDialogFragment = OptionDialogFragment()

            // to get better performance we need to use childFragmentManger to display custom dialog
            // with DialogFragment because the fragment
            // object is already exist that is DetailCategoryFragment
            val mFragmentManager = childFragmentManager
            // to display the custom dialog by using DialogFragment
            mOptionDialogFragment.show(mFragmentManager, OptionDialogFragment::class.java.javaClass.simpleName)
        }

        btnProfile.setOnClickListener {
            val mIntent = Intent(requireActivity(), ProfileActivity::class.java)
            startActivity(mIntent)
        }
    }
}