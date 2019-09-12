package layout


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.appnavview.R


class Fragmento3 : Fragment() {

    override fun onCreateView(inflater:LayoutInflater, container:ViewGroup?,
                              savedInstanceState:Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_fragment3, container, false)

        return view
    }
}
