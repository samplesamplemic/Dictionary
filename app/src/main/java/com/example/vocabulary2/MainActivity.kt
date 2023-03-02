package com.example.vocabulary2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import com.example.vocabulary2.service.APIService
import com.google.gson.Gson
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity() {
    private lateinit var textInput: EditText;
    private lateinit var button: ImageButton;
    private lateinit var textOutput: TextView;
    private lateinit var textDescription: TextView;
    private lateinit var definitions: MutableList<String?>;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //click event
        button = findViewById(R.id.button)
        button.setOnClickListener { fetchDataAndDisplayThem() }
    }

    private fun fetchData(){ //implement coroutines to fetch API
        val word = Job()
        val errorHandler = CoroutineExceptionHandler {coroutineContext, throwable ->
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }
        val scope = CoroutineScope(word + Dispatchers.Main)

        scope.launch(errorHandler) {

        }

    }

    private fun getReqJson(word: CharSequence?) {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.dictionaryapi.dev/api/v2/entries/en/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service = retrofit.create(APIService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            val response = service.getWord(word)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val items = response.body()

                    Log.i("JSON2: ", "" + Gson().toJson(response.body()))

                    if (items != null) {
                        // ?: Elvis operator
                        for (i in 0 until (items[0].meanings?.get(0)?.definitions?.count() ?: Log.e(
                            "Retrofit error",
                            response.code().toString()
                        ))) {
                            val word = items[0].text
                            definitions.add(items[0]?.meanings?.get(0)?.definitions?.get(i)?.definition)

                            Log.i("definition", "" + definitions)

                        }
                    }
                } else {
                    Log.e("Retrofit error", response.code().toString())
                }
                for (definition in definitions) {
                    textDescription = findViewById(R.id.definitions)
                    textDescription.text = definitions.toString().drop(6).dropLast(1)
                        .replace(Regex("""(\.,|;)"""), " \n");
                }
            }
        }
    }

    //Set word in textView from searchBar by click button
    private fun fetchDataAndDisplayThem() {
        textInput = findViewById(R.id.SearchBar);
        textOutput = findViewById(R.id.word)
        textOutput.text = textInput.text.substring(0, 1).uppercase() + textInput.text.substring(1);
        getReqJson(textOutput.text)
    }
}