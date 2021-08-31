package kz.beeline.cars.adapterdelegatewithdagger.endfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import kz.beeline.cars.adapterdelegatewithdagger.App
import kz.beeline.cars.adapterdelegatewithdagger.R
import kz.beeline.cars.adapterdelegatewithdagger.data.DataType
import kz.beeline.cars.adapterdelegatewithdagger.data.FooterType
import kz.beeline.cars.adapterdelegatewithdagger.data.HeaderType
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import kz.beeline.cars.adapterdelegatewithdagger.endfragment.adapters.TypeAdapter
import kz.beeline.cars.adapterdelegatewithdagger.routes.Screens
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class EndFragment: MvpAppCompatFragment(), EndFragmentView, TypeAdapter.Callback {

    @Inject
    @InjectPresenter
    lateinit var presenter: EndFragmentPresenter

    @Inject
    lateinit var adapter: TypeAdapter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    private fun injectDependencies(){
        (requireActivity().application as App).appComponent.getEndComponent().inject(this@EndFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.end_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
        initRecyclerView(view)
    }

    private fun initViews(view: View){
        view.findViewById<MaterialButton>(R.id.btn_back).setOnClickListener {
            presenter.back()
        }
    }

    private fun initRecyclerView(view: View) {
        adapter.callback = this
        view.findViewById<RecyclerView>(R.id.rv_types).apply {
            val rvManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            layoutManager = rvManager
            adapter = this@EndFragment.adapter
        }
    }

    override fun showMessage(state: MessageState) {
        Toast.makeText(requireContext(), state.state, Toast.LENGTH_LONG).show()
    }

    override fun back() {
        (requireActivity().application as App).router.navigateTo(Screens.Main())
    }

    override fun showHeaderTitle(headerType: HeaderType) {
        Toast.makeText(requireContext(), headerType.title.plus(" ${headerType.type}"), Toast.LENGTH_LONG).show()
    }

    override fun updateTotal(dataType: DataType) {
        val item = (adapter.items.first { it is FooterType } as FooterType)
        item.total += dataType.price
        adapter.items[adapter.items.size - 1] = item
        adapter.notifyItemChanged(adapter.items.size - 1)
    }

    override fun releaseTotal(footerType: FooterType) {
        footerType.total = 0
        adapter.items[adapter.items.size - 1] = footerType
        adapter.notifyItemChanged(adapter.items.size - 1)
    }

    override fun onHeaderTypeClick(header: HeaderType) {
        presenter.onHeaderClick(header)
    }

    override fun onMainTypeClick(dataType: DataType) {
        presenter.onMainClick(dataType)
    }

    override fun onFooterTypeClick(footer: FooterType) {
        presenter.onFooterClick(footer)
    }
}