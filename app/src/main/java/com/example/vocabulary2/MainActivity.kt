package com.example.vocabulary2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.example.vocabulary2.module.VocabularyModel
import com.example.vocabulary2.service.APIService
import com.google.gson.Gson
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var textInput: EditText;
    private lateinit var button: ImageButton;
    private lateinit var textOutput: TextView;
    private lateinit var textDescription : TextView;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.i("work", "workkkk")
        readJSONFromAssets()

        //getReqJson()

        //click event
        button = findViewById(R.id.button)
        button.setOnClickListener { buttonCLick() }
    }

    fun readJSONFromAssets() {
        //read json file in raw folder
        val jsonData = applicationContext.resources.openRawResource(
            applicationContext.resources.getIdentifier(
                "work",
                "raw", applicationContext.packageName
            )
        ).bufferedReader().use { it.readText() }

        //print json obj
        val jsonArray = JSONArray(jsonData)
        Log.d("JSON", "" + jsonArray)

        //Mapping json obj
        val jsonArrayEl = jsonArray.getJSONObject(0) as JSONObject
        val word = jsonArrayEl.get("word")
        Log.i("word: ", "" + word)
    }

    fun getReqJson(word: CharSequence?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIService::class.java)
        //Kotlin’s Coroutines allow the use of suspend functions // async flow
        //A CoroutineScope defines a lifecycle
        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getWord(word)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()

                    Log.i("JSON2: ", "" + Gson().toJson(response.body()))

                    if (items != null) {
                        for (i in 0 until items.count()) {
                            val word = items[i].text
                            val description = items[0]?.meanings?.get(0)?.definitions?.get(0)?.definition
                            Log.i("description", ""+description)
                            textDescription = findViewById(R.id.description)
                            textDescription.text = description.toString();
                        }
                    }
                } else {
                    Log.e("Retrofit error", response.code().toString())
                }
            }

        }
    }

    //Set word in textView from searchBar by click button
    fun buttonCLick() {
        textInput = findViewById(R.id.SearchBar);
        textOutput = findViewById(R.id.word)
         //println(textInput.text)
        textOutput.text = textInput.text.substring(0,1).uppercase()+textInput.text.substring(1);
        getReqJson(textOutput.text)
    }
}