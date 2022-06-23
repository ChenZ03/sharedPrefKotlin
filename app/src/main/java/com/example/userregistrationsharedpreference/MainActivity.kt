package com.example.userregistrationsharedpreference
import android.app.DatePickerDialog
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.userregistrationsharedpreference.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var sharedpreferences: SharedPreferences
    lateinit var binding : ActivityMainBinding;

    //ViewModel
    lateinit var viewModel : ViewModalAct
    //EditText dob;
    lateinit var picker: DatePickerDialog
    var genders = arrayOf("Male", "Female")
    var gender_selected = ""
    var gender_pos_selected = 0
    lateinit var adapter: ArrayAdapter<CharSequence>

    companion object {
        const val mypreference = "com.example.userregistrationsharedpreferenceapp.regpref"
        const val Name = "nameKey"
        const val Email = "emailKey"
        const val Age = "ageKey"
        const val Gender = "genderKey"
        const val Gender2 = "genderKeyPos"
        const val DOB = "DOBKey"
    }



    override fun onCreate(savedInstanceState: Bundle?) {w
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater) //initializing the binding class
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(ViewModalAct::class.java)

        //Initialize NumberPicker for age
        if(binding.age != null) {
            binding.age.minValue = 0
            binding.age.maxValue = 120
            binding.age.wrapSelectorWheel = true
        }

        binding.genderspinner.setOnItemSelectedListener(this)
        // Create an ArrayAdapter using the string array and a default spinner layout
        adapter = ArrayAdapter.createFromResource(this,
            R.array.gender_array, android.R.layout.simple_spinner_item)
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // Apply the adapter to the spinner
        binding.genderspinner.setAdapter(adapter)

        // Datepicker for DOB
        // https://www.tutlane.com/tutorial/android/android-datepicker-with-examples
        binding.DOBdatepicker.setOnClickListener(View.OnClickListener {
            val cldr = Calendar.getInstance()
            val day = cldr[Calendar.DAY_OF_MONTH]
            val month = cldr[Calendar.MONTH]
            val year = cldr[Calendar.YEAR]
            // date picker dialog
            picker = DatePickerDialog(this@MainActivity,
                { view, year, monthOfYear, dayOfMonth -> binding.DOBdatepicker.setText(dayOfMonth.toString() + "/" + (monthOfYear + 1) + "/" + year) },
                year,
                month,
                day)
            picker.show()
        })
        retrieveFromSharedPreference()
    }

    override fun onItemSelected(
        parent: AdapterView<*>?, view: View,
        pos: Int, id: Long,
    ) {
        gender_selected = genders[pos]
        gender_pos_selected = pos
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
    }

    fun retrieve(view: View?) {
        retrieveFromSharedPreference()
    }

    fun retrieveFromSharedPreference() {
        //Create SharedPreference object and access the shared preference file
        // Open and retrieve the shared preference file

        //Get data from viewModel
        sharedpreferences = getSharedPreferences(mypreference,
            MODE_PRIVATE)
        //check if the preference by the key "nameKey" exist
        if (sharedpreferences.contains(Name)) {
            // Set the edit text content to the value of "nameKey"
            viewModel.name.observe(this) {
                binding.etName.setText(it)
            }
        }
        //check if the preference by the key "emailKey" exist
        if (sharedpreferences.contains(Email)) {
            // Set the edit text content to the value of "emailKey"
            viewModel.email.observe(this) {
                binding.etEmail.setText(it)
            }
        }
        if (sharedpreferences.contains(DOB)) {
            // Set the edit text content to the value of "DOBKey"
            viewModel.dob.observe(this) {
                binding.DOBdatepicker.setText(it)
            }
        }

        //check if the preference by the key "ageKey" exist
        if (sharedpreferences.contains(Age)) {
            // Set the number picker content to the value of "ageKey"
            viewModel.age.observe(this) {
                binding.age.value = it
            }
        }

        //check if the preference by the key "genderKey" exist
        if (sharedpreferences.contains(Gender)) {
            viewModel.gender.observe(this){
                val spinnerPosition = adapter.getPosition(it)
                binding.genderspinner.setSelection(spinnerPosition)
            }
        }
    }

    fun save(view: View?) {
        // get data from inputs
        val n = binding.etName.text.toString()
        val e = binding.etEmail.text.toString()
        val a = binding.age.value
        val d = binding.DOBdatepicker.text.toString()

        //set the data to the viewModel
        viewModel.save(n, e, a, d, gender_selected)
    }

    fun clear(view: View?) {
        // Clear all value
        binding.etName.setText("")
        binding.etEmail.setText("")
        binding.age.value = 0
        binding.DOBdatepicker.setText("")
    }
}