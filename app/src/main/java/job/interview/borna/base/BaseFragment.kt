package job.interview.borna.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import job.interview.borna.R

@ExperimentalUnsignedTypes
abstract class BaseFragment : Fragment() {

    protected abstract val viewModel: BaseViewModel?

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }
}