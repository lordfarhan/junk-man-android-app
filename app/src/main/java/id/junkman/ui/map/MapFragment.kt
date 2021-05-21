package id.junkman.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import id.junkman.R
import id.junkman.databinding.FragmentMapBinding

class MapFragment : Fragment(), OnMapReadyCallback {

  companion object {
    fun newInstance() = MapFragment()
  }

  private lateinit var binding: FragmentMapBinding
  private lateinit var viewModel: MapViewModel

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentMapBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (activity != null) {
      viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
      // Get the SupportMapFragment and request notification when the map is ready to be used.
      val mapFragment =
        childFragmentManager.findFragmentById(R.id.fragment_map) as? SupportMapFragment
      mapFragment?.getMapAsync(this)
    }
  }

  override fun onMapReady(googleMap: GoogleMap) {
    val sydney = LatLng(-33.852, 151.211)
    googleMap.addMarker(
      MarkerOptions()
        .position(sydney)
        .title("Marker in Sydney")
    )
    googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
    googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
  }
}