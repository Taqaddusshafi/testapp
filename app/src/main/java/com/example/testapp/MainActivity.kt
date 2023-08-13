package com.example.testapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var searchBox: EditText
    private lateinit var searchButton: Button
    private lateinit var resultTextView: TextView
    private lateinit var pro1: TextView
    private lateinit var pro2: TextView
    private lateinit var pro3: TextView
    private lateinit var patent: CheckBox
    private lateinit var copyrightCheckbox: CheckBox
    private lateinit var trademarkCheckbox: CheckBox
    private lateinit var designCheckbox: CheckBox
    private lateinit var tradescret: CheckBox
    private lateinit var text0: TextView
    private lateinit var text1: TextView
    private lateinit var text2: TextView
    private lateinit var text3: TextView
    private lateinit var text4: TextView
    private lateinit var inst: TextView
    private lateinit var information: TextView
    private lateinit var data: RelativeLayout

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
        inst = findViewById(R.id.inst)
        text0 = findViewById(R.id.text0)
        text1 = findViewById(R.id.text1)
        text2 = findViewById(R.id.text2)
        text3 = findViewById(R.id.text3)
        text4 = findViewById(R.id.text4)
        information=findViewById(R.id.information)
        data=findViewById(R.id.data)
        patent = findViewById(R.id.patent)
        tradescret = findViewById(R.id.tradescret)
        copyrightCheckbox = findViewById(R.id.copyright_checkbox)
        trademarkCheckbox = findViewById(R.id.trademark_checkbox)
        designCheckbox = findViewById(R.id.design_checkbox)

        val checkBoxes = listOf(copyrightCheckbox, trademarkCheckbox, designCheckbox,patent,tradescret)
        for (checkBox in checkBoxes) {
            checkBox.setOnClickListener {

                for (otherCheckBox in checkBoxes) {
                    if (otherCheckBox != checkBox) {
                        otherCheckBox.isChecked = false
                    }
                }
            }
        }

        searchButton.setOnClickListener {
            val searchText = searchBox.text.toString()
            if (searchText.isEmpty()){
                Toast.makeText(this, "Search box is Empty", Toast.LENGTH_SHORT).show()
            }
            else {
                searchInFirebase(searchText)
                showSelectedTextViews()
                information.visibility=View.VISIBLE
                data.visibility=View.VISIBLE

            }
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
                        val final= "Example:-  $textValue"
                        resultTextView.text =  final ?: "No data found."
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

    private fun showSelectedTextViews() {
        val showCopyright = copyrightCheckbox.isChecked
        val showTrademark = trademarkCheckbox.isChecked
        val showDesign = designCheckbox.isChecked
        val showpatent = patent.isChecked
        val tradescret1 = tradescret.isChecked

        if (showCopyright || showTrademark || showDesign || showpatent || tradescret1) {
            inst.visibility = View.VISIBLE
        } else {
            inst.visibility = View.GONE
        }
        if (tradescret1) {
            text4.visibility = View.VISIBLE
        } else {
            text4.visibility = View.GONE
        }
        if (showpatent) {
            text0.visibility = View.VISIBLE
        } else {
            text0.visibility = View.GONE
        }

        if (showCopyright) {
            text1.visibility = View.VISIBLE
        } else {
            text1.visibility = View.GONE
        }

        if (showTrademark) {
            text2.visibility = View.VISIBLE
        } else {
            text2.visibility = View.GONE
        }

        if (showDesign) {
            text3.visibility = View.VISIBLE
        } else {
            text3.visibility = View.GONE
        }
    }
}
