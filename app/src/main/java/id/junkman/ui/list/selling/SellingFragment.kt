package id.junkman.ui.list.selling

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import id.junkman.databinding.FragmentSellingBinding
import id.junkman.ui.list.buying.BottomDialogBuyingFragment

class SellingFragment : Fragment() {
  private var _binding: FragmentSellingBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentSellingBinding.inflate(inflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    //show bottom dialog harusnya pas nge klik item nya
    showBottomDialog()
    binding.progressBar.visibility = View.INVISIBLE
  }

  private fun showBottomDialog() {
    val myDialog = BottomDialogSellingFragment()
    val fm: FragmentManager? = fragmentManager
    if (fm != null) {
      myDialog.show(fm, "buying")
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

}