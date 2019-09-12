package layout

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appnavview.R
import kotlinx.android.synthetic.main.fragment_fragment1.*
import kotlinx.android.synthetic.main.fragment_fragment1.view.*


class Fragmento1 : Fragment() {


   override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,
                     savedInstanceState:Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fragment1, container, false)
        view.btnprecionar.setOnClickListener {
            lblInformacion.setText("Hola como estas?")
        }
       return view
    }









}
