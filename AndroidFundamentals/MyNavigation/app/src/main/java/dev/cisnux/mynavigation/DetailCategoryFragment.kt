package dev.cisnux.mynavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import dev.cisnux.mynavigation.databinding.FragmentDetailCategoryBinding

class DetailCategoryFragment : Fragment() {
    private var _binding: FragmentDetailCategoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentDetailCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            // receiving with bundle
//            val dataName = arguments?.getString(CategoryFragment.EXTRA_NAME)
//            val dataDescription = arguments?.getLong(CategoryFragment.EXTRA_STOCK)

            val dataName = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).name
            val dataDescription = DetailCategoryFragmentArgs.fromBundle(arguments as Bundle).stock

            tvCategoryName.text = dataName
            tvCategoryDescription.text =
                getString(R.string.category_description_placeholder, dataDescription)
            btnProfile
                .setOnClickListener(Navigation.createNavigateOnClickListener(R.id.action_detailCategoryFragment_to_homeFragment))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
