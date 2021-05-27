package id.junkman.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import id.junkman.R
import id.junkman.databinding.FragmentMapBinding
import id.junkman.model.Office


class MapFragment : Fragment(), OnMapReadyCallback {

  companion object {
    fun newInstance() = MapFragment()
  }

  private var _binding: FragmentMapBinding? = null
  private val binding get() = _binding!!
  private lateinit var viewModel: MapViewModel
  private lateinit var store: FirebaseFirestore

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _binding = FragmentMapBinding.inflate(layoutInflater, container, false)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    if (activity != null) {
      viewModel = ViewModelProvider(this).get(MapViewModel::class.java)
      store = Firebase.firestore

      // Get the SupportMapFragment and request notification when the map is ready to be used.
      val mapFragment =
        childFragmentManager.findFragmentById(R.id.fragment_map) as? SupportMapFragment
      mapFragment?.getMapAsync(this)
    }
  }

  private fun showBottomDialog(offices: ArrayList<Office>) {
    val myDialog = MapBottomSheetFragment(offices)
    val fm: FragmentManager? = fragmentManager
    if (fm != null) {
      myDialog.show(fm, "map")
    }
  }

  override fun onDestroyView() {
    super.onDestroyView()
    _binding = null
  }

  override fun onMapReady(googleMap: GoogleMap) {
    val offices: ArrayList<Office> = ArrayList()
    store.collection("Office")
      .get()
      .addOnSuccessListener { documents ->
        for (document in documents) {
          if (document.exists()) {
            val office = document.toObject(Office::class.java)
            offices.add(office)

            office.coordinate?.let {
              val latLng = LatLng(it.latitude, it.longitude)
              googleMap.addMarker(
                MarkerOptions()
                  .position(latLng)
                  .title(office.name)
              )
              googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
              googleMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            }
          }
        }
        showBottomDialog(offices)
      }
  }
}