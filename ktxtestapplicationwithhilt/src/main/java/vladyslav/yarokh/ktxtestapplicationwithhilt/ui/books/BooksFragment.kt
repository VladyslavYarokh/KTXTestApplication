package vladyslav.yarokh.ktxtestapplicationwithhilt.ui.books

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import vladyslav.yarokh.ktxtestapplicationwithhilt.R
import vladyslav.yarokh.ktxtestapplicationwithhilt.data.*
import vladyslav.yarokh.ktxtestapplicationwithhilt.databinding.BooksFragmentBinding

@AndroidEntryPoint
class BooksFragment: Fragment(), BookItemListener {

    private lateinit var binding: BooksFragmentBinding
    private val viewModel by viewModels<BooksViewModel>()
    private val args: BooksFragmentArgs by navArgs()
    private val bookAdapter: BooksAdapter = BooksAdapter(this)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BooksFragmentBinding.inflate(inflater, container, false)
        return  binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews()
    }

    private fun initViews() {
        binding.apply {
            if(args.query.isNullOrEmpty()) {
                viewModel.getBooks("").observe(viewLifecycleOwner, observer)
            } else {
                viewModel.getBooks(args.query.toString()).observe(viewLifecycleOwner, observer)
            }

            ivBack.setOnClickListener {
                findNavController().popBackStack()
            }

            rvBooks.apply {
                adapter = bookAdapter
                layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            }
        }
    }

    private val observer = Observer<BooksState> {
        binding.pbLoading.visibility = if(it == LoadingState) View.VISIBLE else View.GONE
        binding.rvBooks.visibility = if(it == LoadingState) View.GONE else View.VISIBLE
        when(it) {
            is ErrorState -> Toast.makeText(requireContext(), R.string.smth_went_wrong, Toast.LENGTH_LONG).show()
            is SuccessState -> {
                bookAdapter.updateListItems(it.data)
            }
        }
    }

    override fun onBookClick(bookModel: BookModel) {
        Toast.makeText(requireContext(), "${bookModel.name}, Author: ${bookModel.author}", Toast.LENGTH_LONG).show()
    }
}