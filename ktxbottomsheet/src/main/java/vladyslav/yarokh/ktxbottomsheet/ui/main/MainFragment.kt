package vladyslav.yarokh.ktxbottomsheet.ui.main

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import vladyslav.yarokh.ktxbottomsheet.R
import vladyslav.yarokh.ktxbottomsheet.databinding.MainFragmentBinding
import vladyslav.yarokh.ktxbottomsheet.ui.sheet.BottomSheetFragment

class MainFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var binding: MainFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            btnShowDialog.setOnClickListener {
                BottomSheetFragment().showNow(childFragmentManager, this@MainFragment.tag)
            }
        }
    }
}