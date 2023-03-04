package dev.cisnux.mynavigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import dev.cisnux.mynavigation.databinding.FragmentCategoryBinding

class CategoryFragment : Fragment() {
    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!

    companion object {
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_STOCK = "extra_stock"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            btnCategoryLifecycle.setOnClickListener { view ->
                // with bundle
//                val mBundle = Bundle().apply {
//                    putString(EXTRA_NAME, "Lifestyle")
//                    putLong(EXTRA_STOCK, 7)
//                }
//                view.findNavController()
//                    .navigate(
//                        R.id.action_categoryFragment_to_detailCategoryFragment,
//                        mBundle
//                    )
                // sending data with safeargs
                val toDetailCategoryFragment = CategoryFragmentDirections.actionCategoryFragmentToDetailCategoryFragment().apply {
                    name = "Lifestyle"
                    stock = 7
                }
                view.findNavController().navigate(toDetailCategoryFragment)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}