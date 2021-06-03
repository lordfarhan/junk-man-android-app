package id.junkman.ui.home

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import id.junkman.R
import id.junkman.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

  companion object {
    fun newInstance() = HomeFragment()
  }

  private lateinit var viewModel: HomeViewModel
  private var _binding: FragmentHomeBinding? = null
  private val binding get() = _binding!!

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
    _binding = FragmentHomeBinding.inflate(inflater, container, false)

    val poster = ArrayList<SlideModel>()
    poster.add(SlideModel(R.drawable.card1, getString(R.string.card1), ScaleTypes.CENTER_CROP))
    poster.add(SlideModel(R.drawable.card2, getString(R.string.card2), ScaleTypes.CENTER_CROP))
    poster.add(SlideModel(R.drawable.card3, getString(R.string.card3), ScaleTypes.CENTER_CROP))
    binding.imageSlider.setImageList(poster)

/*    binding.progressBar.max = 100
    binding.progressBar.progress = 0*/
    binding.txtTitle.text = getString(R.string.monthly_text)

    return binding.root
  }

}