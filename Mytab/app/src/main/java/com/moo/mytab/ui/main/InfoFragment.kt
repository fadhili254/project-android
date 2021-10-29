package com.moo.mytab.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
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
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONArray
import org.json.JSONObject

/**
 * A placeholder fragment containing a simple view.
 */
class InfoFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root =inflater.inflate(R.layout.fragment_info, container, false)
        val firstname = root.findViewById<EditText>(R.id.firstname)
        val lastname = root.findViewById<EditText>(R.id.lastname)
        val residence = root.findViewById<EditText>(R.id.residence)
        val phone= root.findViewById<EditText>(R.id.phone)
        val request = root.findViewById<EditText>(R.id.request)
        val submit = root.findViewById<EditText>(R.id.submit)
        val progress = root.findViewById<ProgressBar>(R.id.progress)
        progress.visibility = View.GONE //make the progress go untill button is pressed

        submit.setOnClickListener{
            //show progress
            progress.visibility = View.VISIBLE

            val client = AsyncHttpClient(true, 80, 433)
            val jsonParams = JSONObject()
            jsonParams.put("firstname", firstname.text.toString())
            jsonParams.put("lastname", lastname.text.toString())
            jsonParams.put("residence", residence.text.toString())
            jsonParams.put("phone", phone.text.toString())
            jsonParams.put("request", request.text.toString())
            //post the data to your API
            //convert above json to strings
            val data = StringEntity(jsonParams.toString())
            client.post(activity, "http://foatreckser.pythonanywhere.com/post",
            data, "application/json", object : JsonHttpResponseHandler(){

                    override fun onSuccess(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        response: JSONObject?
                    ) {
                        if (statusCode==200){
                            Toast.makeText(activity, ""+response,
                                Toast.LENGTH_LONG).show()
                            progress.visibility = View.GONE
                        }
                        else{
                            Toast.makeText(activity, "something went wrong",
                                Toast.LENGTH_LONG).show()
                            progress.visibility = View.GONE
                        }

                        //super.onSuccess(statusCode, headers, response)
                    }//end success

                    override fun onFailure(
                        statusCode: Int,
                        headers: Array<out Header>?,
                        throwable: Throwable?,
                        errorResponse: JSONObject?
                    ) {
                        Toast.makeText(activity,"code $statusCode try again",
                        Toast.LENGTH_LONG).show()
                        progress.visibility = View.GONE

                        //super.onFailure(statusCode, headers, throwable, errorResponse)
                    }//end failure

            })

        }

        return root
    }
}