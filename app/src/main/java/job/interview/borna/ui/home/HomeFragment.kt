package job.interview.borna.ui.home

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import job.interview.borna.Constants.ITEM
import job.interview.borna.base.BaseFragment
import job.interview.borna.databinding.FragmentMainBinding
import job.interview.borna.ui.edititem.EditActivity
import job.interview.borna.ui.home.adapter.ItemsAdapter

@AndroidEntryPoint
@ExperimentalUnsignedTypes
class HomeFragment : BaseFragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override val viewModel: HomeViewModel by activityViewModels()

    private val itemsAdapter = ItemsAdapter { item ->
        val intent = Intent(requireContext(), EditActivity::class.java)
        intent.putExtra(ITEM, item)
        openEditActivity.launch(intent)
    }

    private val openEditActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                // do something
            } else {
                // do something
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
        observeItems()
    }

    private fun observeItems() {
        viewModel.items.observe(viewLifecycleOwner, {
            itemsAdapter.resetList(it)
        })
    }

    private fun setupBindings() {
        binding.apply {
            fabAddItem.setOnClickListener {
                navigateToAddItem()
            }
            rvItems.adapter = itemsAdapter
        }
    }

    private fun navigateToAddItem() {
        val direction = HomeFragmentDirections.actionMainFragmentToAddItemFragment()
        findNavController().navigate(direction)
    }
}