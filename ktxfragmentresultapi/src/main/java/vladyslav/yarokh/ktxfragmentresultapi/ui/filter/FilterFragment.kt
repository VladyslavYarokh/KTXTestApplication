package vladyslav.yarokh.ktxfragmentresultapi.ui.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.core.os.bundleOf
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import vladyslav.yarokh.ktxfragmentresultapi.databinding.FragmentFilterBinding
import vladyslav.yarokh.ktxfragmentresultapi.ui.data.PriceSort

private const val RESULT_KEY = "result_key_products"
private const val SORT_KEY = "price_sort_key"

class FilterFragment: Fragment() {
    private lateinit var filterViewModel: FilterViewModel
    private lateinit var binding: FragmentFilterBinding
    private var selectedResult: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        filterViewModel = ViewModelProvider(this).get(FilterViewModel::class.java)

        binding = FragmentFilterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            rgFilter.setOnCheckedChangeListener { group, checkedId ->
                requireActivity().findViewById<RadioButton>(checkedId).apply {
                    selectedResult = text.toString().toInt()
                }
            }
            btnApply.setOnClickListener {
                setFragmentResult(
                    RESULT_KEY,
                    bundleOf(SORT_KEY to PriceSort(selectedResult))
                )

                findNavController().popBackStack()
            }
        }
    }
}