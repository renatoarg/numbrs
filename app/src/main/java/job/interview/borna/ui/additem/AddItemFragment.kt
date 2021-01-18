package job.interview.borna.ui.additem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import job.interview.borna.Constants.INCOME
import job.interview.borna.Constants.OUTCOME
import job.interview.borna.R
import job.interview.borna.base.BaseFragment
import job.interview.borna.convertToDate
import job.interview.borna.data.Item
import job.interview.borna.databinding.FragmentAddItemBinding

@AndroidEntryPoint
@ExperimentalUnsignedTypes
class AddItemFragment : BaseFragment() {

    private var _binding: FragmentAddItemBinding? = null
    private val binding get() = _binding!!

    override val viewModel: AddItemViewModel by activityViewModels()

    private var type: Int = -1
    private var date: Long = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        date = System.currentTimeMillis()
        setupBindings()
        observeAddItemState()
    }

    private fun setupBindings() {
        binding.apply {
            btnSave.setOnClickListener {
                saveItem()
            }
            edtDate.setText(System.currentTimeMillis().convertToDate("dd/MM/yyyy"))
            dateSurface.setOnClickListener {
                showDateTimePicker()
            }
            toggleButtonGroup.apply {
                isSingleSelection = true
                addOnButtonCheckedListener { _, checkedId, _ ->
                    binding.toggleButtonGroupError.isVisible = false
                    type = when (checkedId) {
                        R.id.catIncome -> INCOME
                        R.id.catOutcome -> OUTCOME
                        else -> -1
                    }
                }
            }
        }
    }

    private fun showDateTimePicker() {
        val dateBuilder = MaterialDatePicker.Builder.datePicker()
        val datePicker = dateBuilder.build()
        datePicker.addOnPositiveButtonClickListener {
            datePicker.selection?.let {
                date = it
                binding.edtDate.setText(
                    getString(
                        R.string.date,
                        date.convertToDate("dd/MM/yyyy")
                    )
                )
            }
        }
        datePicker.show(childFragmentManager, datePicker.toString())
    }

    private fun FragmentAddItemBinding.saveItem() {
        if (validateInputs()) {
            viewModel.addItem(
                Item(
                    id = null,
                    date = date,
                    name = edtName.text.toString(),
                    value = edtValue.text.toString().toDouble(),
                    type = type
                )
            )
        }
    }

    private fun observeAddItemState() {
        viewModel.getItemState().observe(viewLifecycleOwner, { addItemState ->
            when (addItemState) {
                is AddItemState.AddSuccess -> setupForAddSuccess()
                is AddItemState.Idle -> setupForIdle()
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

    private fun setupForAddSuccess() {
        Toast.makeText(
            requireContext(),
            getString(R.string.item_added_successfully),
            Toast.LENGTH_SHORT
        ).show()
        viewModel.setIdle()
        findNavController().navigateUp()
    }

    private fun validateInputs(): Boolean {
        var isValid = true
        if (!binding.tilName.validate()) isValid = false
        if (!binding.tilValue.validate()) isValid = false
        if (type == -1) {
            isValid = false
            binding.toggleButtonGroupError.isVisible = true
        }
        return isValid
    }
}