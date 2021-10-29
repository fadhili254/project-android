package com.moo.mytab.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.JsonHttpResponseHandler
import com.moo.mytab.R
import com.moo.mytab.databinding.FragmentMainBinding
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject

/**
 * A placeholder fragment containing a simple view.
 */
class MenuFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root =inflater.inflate(R.layout.fragment_menu, container, false)
        val client = AsyncHttpClient(true,80,433)
        val progress = root.findViewById<ProgressBar>(R.id.progress)
        progress.visibility = View.VISIBLE
        client.get("http://foatreckser.pythonanywhere.com/api/posts",null,
            object :JsonHttpResponseHandler(){
                override fun onSuccess(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    response: JSONArray
                ) {
                    //super.onSuccess(statusCode, headers, response)
                    for (i in 0 until response.length()){
                        val json = response.getJSONObject(1)
                        val firstname = json.optString("firstname").toString()
                        val lasttname = json.optString("lastname").toString()
                        val phone = json.optString("phone").toString()
                        val request= json.optString("request").toString()
                        val residence = json.optString("residence").toString()
                        val textView = root.findViewById<TextView>(R.id.data)
                        textView.append("$firstname\n $lasttname\n $phone\n $request\n $residence\n")
                        textView.append("\n\n")
                    }
                    progress.visibility = View.GONE
                }

                override fun onFailure(
                    statusCode: Int,
                    headers: Array<out Header>?,
                    throwable: Throwable?,
                    responseString: JSONObject?
                ) {
                    //super.onFailure(statusCode, headers, throwable, errorResponse)
                    Toast.makeText(activity, ""+responseString, Toast.LENGTH_LONG ).show()
                }
            })


        return root
    }

}