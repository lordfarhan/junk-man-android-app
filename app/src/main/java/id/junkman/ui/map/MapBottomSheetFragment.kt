package id.junkman.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import id.junkman.databinding.FragmentMapBottomSheetBinding
import id.junkman.model.Office

/**
 * @author farhan
 * created at at 12:15 on 27/05/21.
 */
class MapBottomSheetFragment(private val offices: ArrayList<Office>) : BottomSheetDialogFragment() {

  private var _binding: FragmentMapBottomSheetBinding? = null
  private val binding get() = _binding!!

  private lateinit var adapter: MapAdapter

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    _binding = FragmentMapBottomSheetBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    adapter = MapAdapter()
    binding.recyclerViewOffices.adapter = adapter
    binding.recyclerViewOffices.layoutManager = LinearLayoutManager(requireContext())
    adapter.submitList(offices)

    binding.btnCloseDialog.setOnClickListener {
      dismiss()
    }
  }

}