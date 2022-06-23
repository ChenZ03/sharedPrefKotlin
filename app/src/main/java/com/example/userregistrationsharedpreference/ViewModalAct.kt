package com.example.userregistrationsharedpreference

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class ViewModalAct(application: Application) : AndroidViewModel(application) {
    var sharedPreferences: SharedPreferences = application.getSharedPreferences("com.example.userregistrationsharedpreferenceapp.regpref", 0)

    val NameKey = "nameKey"
    val EmailKey = "emailKey"
    val AgeKey = "ageKey"
    val GenderKey = "genderKey"
    val DOB = "DOBKey"

    val name: LiveData<String>
        get() = MutableLiveData<String>().apply {
            value = if(sharedPreferences.contains(NameKey)){
                sharedPreferences.getString(NameKey, "")
            }else{
                ""
            }
        }

    val email: LiveData<String>
        get() = MutableLiveData<String>().apply {
            value = if(sharedPreferences.contains(EmailKey)){
                sharedPreferences.getString(EmailKey, "")
            }else{
                ""
            }
        }

    val dob: LiveData<String>
        get() = MutableLiveData<String>().apply {
            value = if(sharedPreferences.contains(DOB)){
                sharedPreferences.getString(DOB, "")
            }else{
                ""
            }
        }

    val age : LiveData<Int>
        get() = MutableLiveData<Int>().apply {
            value = if(sharedPreferences.contains(AgeKey)){
                sharedPreferences.getInt(AgeKey, 0)
            }else{
                0
            }
        }

    val gender : LiveData<String>
        get() = MutableLiveData<String>().apply {
            value = if(sharedPreferences.contains(AgeKey)){
                sharedPreferences.getString(GenderKey, "MALE")
            }else{
                ""
            }
        }

    fun save(n : String, e : String, a : Int, d : String, g : String){
        val editor = sharedPreferences.edit()
        editor.putString(NameKey, n) //store string into the shared preference with nameKey
        editor.putString(EmailKey, e) //store string into the shared preference with emailKey
        editor.putInt(AgeKey, a)
        editor.putString(GenderKey, g)
        editor.putString(DOB, d)
        editor.commit()
    }
}