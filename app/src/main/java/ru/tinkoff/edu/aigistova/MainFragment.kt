package ru.tinkoff.edu.aigistova;

import android.os.Bundle
import android.telecom.Call
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.tinkoff.edu.aigistova.network.ImageApi
import ru.tinkoff.edu.aigistova.network.JSONData

class MainFragment : Fragment(R.layout.fragment_main) {
    var description: String = "";
    var url: String = "";
    val isLoading: Boolean = false;


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val image: ImageView = view.findViewById(R.id.image);
        val descriptionText: TextView = view.findViewById(R.id.description)
        loadNext(image, descriptionText)
        val btnForward: Button = view.findViewById(R.id.btn_forward)
        btnForward.setOnClickListener {
            loadNext(image, descriptionText)
        }
    }
    fun loadNext(image: ImageView, descriptionText: TextView){
        ImageApi.retrofitService.getRandom().enqueue(object: Callback<JSONData> {
            override fun onResponse(call: Call<JSONData>, response: Response<JSONData>) {
                val body = response.body()
                if (body != null) {
                    description = body.description
                    descriptionText.text = description
                    Log.d(description, body.type)
                    url = if (body.type == "gif") {
                        body.gifURL
                    } else {
                        body.previewURL
                    }
                    context?.let {
                        Glide.with(it)
                            .load(url)
                            .into(image)
                    };
                }

                //TODO("Not yet implemented")
            }

            override fun onFailure(call: Call<JSONData>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
                //TODO("Not yet implemented")
            }

        })
    }
}
