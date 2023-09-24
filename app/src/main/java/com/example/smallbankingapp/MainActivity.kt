package com.example.smallbankingapp

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.drawable.GradientDrawable.Orientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text
import java.util.jar.Attributes.Name

class MainActivity : AppCompatActivity() {
    val personsTableName="Persons"
    val person_id_column="id"
    val person_name_column="name"
    val person_email_column="email"
    val person_balance_column="balance"
    lateinit var databaseHelper:SqlDatabase
    lateinit var db:SqlDatabase

    lateinit var Name:TextView
     lateinit var Amount:TextView
    private lateinit var RecyclerView:RecyclerView
    private  var  arraylist=ArrayList<Person>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Name=findViewById(R.id.Name)
        Amount=findViewById(R.id.Amount)

        RecyclerView=findViewById(R.id.recyclerView)

        RecyclerView.layoutManager=LinearLayoutManager(this)
        RecyclerView.setHasFixedSize(true)
        databaseHelper= SqlDatabase(this)
        readData()
        RecyclerView.adapter=myAdapter(arraylist,this)
        readAmount()
        db= SqlDatabase(this)
        //addDummyData()
    }



    private fun readAmount() {
        val cursor=databaseHelper.readableDatabase.rawQuery("SELECT * FROM ${personsTableName} where id=1",
            arrayOf<String>()
        )
        var name:String
        var balance:Int=0
        while (cursor.moveToNext())
        {
            val id=cursor.getInt(0)
             name=cursor.getString(1)
            val email=cursor.getString(2)
             balance=cursor.getInt(3)
        }
        cursor.close()
       Amount.text="Amount:"+balance.toString()
    }

    private fun readData() {
        val cursor=databaseHelper.readableDatabase.rawQuery("SELECT * FROM ${personsTableName}",
            arrayOf<String>()
        )
        while (cursor.moveToNext())
        {
            val id=cursor.getInt(0)
            val name=cursor.getString(1)
            val email=cursor.getString(2)
            val balance=cursor.getInt(3)
            arraylist.add(Person(id,name,email,balance))
        }
        cursor.close()
    }

    private fun addDummyData() {

        arraylist.add(Person(1,"Youssef","youssef@outlook.com",5000))
        arraylist.add(Person(2,"Amr","Amr@outlook.com",6000))
        arraylist.add(Person(3,"Nader","Nader@outlook.com",8000))
        arraylist.add(Person(4,"Yasser","Yasser@outlook.com",12000))
        arraylist.add(Person(5,"Mostafa","Mostafa@outlook.com",3000))
        arraylist.add(Person(6,"Muhammed","Muhammed@outlook.com",2000))
        arraylist.add(Person(7,"Khalid","Khalid@outlook.com",1000))
        arraylist.add(Person(8,"Mahmoud","Mahmoud@outlook.com",9000))
        arraylist.add(Person(9,"Samir","Samir@outlook.com",4000))
        arraylist.add(Person(10,"Omar","Omar@outlook.com",3000))
        for(i in arraylist)
        {
            val newEntry=ContentValues().apply {
                put(person_id_column,i.id)
                put(person_name_column,i.name)
                put(person_email_column,i.email)
                put(person_balance_column,i.Balance)
            }
            databaseHelper.writableDatabase.insert(personsTableName,null,newEntry)
        }
    }

    companion object {
        fun updateBalance(newBalanceSender: Int) {

                var instance: MainActivity? = MainActivity()

                var Amount:TextView
                Amount= instance?.findViewById(R.id.Amount)!!
                Amount.text =newBalanceSender.toString()


        }
    }
}