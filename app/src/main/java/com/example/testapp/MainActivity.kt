package com.example.testapp


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var searchBox: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var pro1: TextView
    private lateinit var pro2: TextView
    private lateinit var pro3: TextView

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val collectionName = "patent_items"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        searchBox = findViewById(R.id.search_box)
        searchButton = findViewById(R.id.search_button)
        resultTextView = findViewById(R.id.text_view)
        pro1 = findViewById(R.id.pro1)
        pro2 = findViewById(R.id.pro2)
        pro3 = findViewById(R.id.pro3)

        searchButton.setOnClickListener {
            val searchText = searchBox.text.toString()
            searchInFirebase(searchText)
        }
    }

    private fun searchInFirebase(searchText: String) {
        firestore.collection(collectionName)
            .document(searchText)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documentSnapshot = task.result
                    if (documentSnapshot != null && documentSnapshot.exists()) {
                        val data = documentSnapshot.data
                        val textValue = data?.get("name") as? String
                        val textValue1 = data?.get("pro1") as? String
                        val textValue2 = data?.get("pro2") as? String
                        val textValue3 = data?.get("pro3") as? String
                        resultTextView.text = textValue ?: "No data found."
                        pro1.text = textValue1 ?: "No data found."
                        pro2.text = textValue2 ?: "No data found."
                        pro3.text = textValue3 ?: "No data found."
                    } else {
                        resultTextView.text = "No data found."
                        pro1.text = "No data found."
                        pro2.text = "No data found."
                        pro2.text = "No data found."

                    }
                } else {
                    resultTextView.text = "Error fetching data."
                    pro1.text = "Error."
                    pro2.text = "Error."
                    pro3.text = "Error."
                }
            }
    }
}