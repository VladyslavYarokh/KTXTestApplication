package vladyslav.yarokh.ktxtestapplicationwithhilt.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vladyslav.yarokh.ktxtestapplicationwithhilt.R
import vladyslav.yarokh.ktxtestapplicationwithhilt.databinding.SearchFragmentBinding

@AndroidEntryPoint
class SearchFragment : Fragment() {

    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var binding: SearchFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            btnOpenList.setOnClickListener {
                findNavController().navigate(SearchFragmentDirections.fromSearchFragmentToBooksFragment(null))
            }

            btnSearch.setOnClickListener {
                if(etSearch.text.isNullOrEmpty()) {
                    Toast.makeText(requireContext(), getString(R.string.field_is_empty), Toast.LENGTH_LONG).show()
                } else {
                    findNavController().navigate(SearchFragmentDirections.fromSearchFragmentToBooksFragment(etSearch.text.toString()))
                }
            }
        }
    }
}