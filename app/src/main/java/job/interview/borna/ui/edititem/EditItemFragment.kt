package job.interview.borna.ui.edititem

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import job.interview.borna.Constants.INCOME
import job.interview.borna.Constants.ITEM
import job.interview.borna.Constants.OUTCOME
import job.interview.borna.R
import job.interview.borna.base.BaseFragment
import job.interview.borna.data.Item
import job.interview.borna.databinding.FragmentEditItemBinding
import kotlin.math.absoluteValue

@AndroidEntryPoint
@ExperimentalUnsignedTypes
class EditItemFragment : BaseFragment() {

    private var _binding: FragmentEditItemBinding? = null
    private val binding get() = _binding!!

    override val viewModel: EditItemViewModel by activityViewModels()

    lateinit var originalItem: Item
    private var updatedItem = Item()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupBindings()
        observeEditItemState()
        activity?.intent?.getParcelableExtra<Item>(ITEM)?.let { item ->
            originalItem = item
            binding.apply {
                edtName.setText(item.name)
                edtValue.setText(item.value.toString())
                toggleButtonGroup.check(
                    when (item.type) {
                        INCOME -> R.id.catIncome
                        else -> R.id.catOutcome
                    }
                )
            }
        }
    }

    private fun setupBindings() {
        binding.apply {
            btnUpdate.setOnClickListener {
                updateItem()
            }
            ivDelete.setOnClickListener {
                activity?.let {
                    MaterialAlertDialogBuilder(
                        requireActivity(),
                        R.style.Theme_MaterialComponents_Light_Dialog_Alert
                    ).setTitle(getString(R.string.delete))
                        .setMessage(R.string.are_you_sure)
                        .setPositiveButton(R.string.yes) { dialog, i ->
                            viewModel.deleteItem(originalItem)
                            dialog.cancel()
                        }
                        .setNegativeButton(R.string.no) { dialog, i ->
                            dialog.cancel()
                        }
                        .show()
                }
            }
            toggleButtonGroup.apply {
                isSingleSelection = true
                addOnButtonCheckedListener { _, checkedId, _ ->
                    binding.toggleButtonGroupError.isVisible = false
                    updatedItem.type = when (checkedId) {
                        R.id.catIncome -> INCOME
                        R.id.catOutcome -> OUTCOME
                        else -> -1
                    }
                }
            }
        }
    }

    private fun FragmentEditItemBinding.updateItem() {
        if (validateInputs()) {
            viewModel.editItem(
                Item(
                    originalItem.id,
                    originalItem.date,
                    edtName.text.toString(),
                    if (updatedItem.type == INCOME) edtValue.text.toString()
                        .toDouble().absoluteValue
                    else -1 * edtValue.text.toString().toDouble().absoluteValue,
                    updatedItem.type
                )
            )
        }
    }

    private fun observeEditItemState() {
        viewModel.getEditItemState().observe(viewLifecycleOwner, { addItemState ->
            when (addItemState) {
                is EditItemState.AddSuccess -> setupForEditSuccess()
                is EditItemState.Idle -> setupForIdle()
                else -> {
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        })
    }

    private fun setupForIdle() {
        // do nothing
    }

    private fun setupForEditSuccess() {
        viewModel.setIdle()
        val data = Intent()
        activity?.apply {
            setResult(RESULT_OK, data);
            finish();
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        if (!binding.tilName.validate()) isValid = false
        if (!binding.tilValue.validate()) isValid = false
        if (updatedItem.type == -1) {
            isValid = false
            binding.toggleButtonGroupError.isVisible = true
        }
        return isValid
    }
}