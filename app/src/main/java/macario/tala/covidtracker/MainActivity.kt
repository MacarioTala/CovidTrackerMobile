package macario.tala.covidtracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.room.Room

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn_add_contact = findViewById<Button>(R.id.btnAddContact)

        val db = CovidDB.getDatabase(this)

        btn_add_contact.setOnClickListener(){
            Toast.makeText(this,"Contact Added",Toast.LENGTH_SHORT).show()
            if (db != null) {
                db.useridDao().createUniqueUser(firstUUID)
                            }
        }

    }
}