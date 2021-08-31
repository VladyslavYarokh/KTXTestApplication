package kz.beeline.cars.adapterdelegatewithdagger.startfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.button.MaterialButton
import kz.beeline.cars.adapterdelegatewithdagger.App
import kz.beeline.cars.adapterdelegatewithdagger.R
import kz.beeline.cars.adapterdelegatewithdagger.data.MessageState
import kz.beeline.cars.adapterdelegatewithdagger.routes.Screens
import moxy.MvpAppCompatFragment
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class StartFragment: MvpAppCompatFragment(), StartFragmentView {

    @Inject
    @InjectPresenter
    lateinit var presenter: StartFragmentPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        injectDependencies()
        super.onCreate(savedInstanceState)
    }

    private fun injectDependencies(){
        (requireActivity().application as App).appComponent.getStartComponent().inject(this@StartFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.start_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)
    }

    private fun initViews(view: View) {
        view.findViewById<MaterialButton>(R.id.btn_next).setOnClickListener {
            presenter.showNextScreen()
        }
    }

    override fun showNextScreen() {
        (requireActivity().application as App).router.navigateTo(Screens.End())
    }

    override fun showMessage(state: MessageState) {
        Toast.makeText(requireContext(), state.state, Toast.LENGTH_LONG).show()
    }
}