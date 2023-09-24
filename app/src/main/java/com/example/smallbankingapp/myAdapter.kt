package com.example.smallbankingapp

import android.app.AlertDialog
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class myAdapter (private val peopleList:ArrayList<Person>,private val context: Context):RecyclerView.Adapter<myAdapter.MyViewHolder>() {
    val SenderID=1

    class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val Customer_Name:TextView=itemView.findViewById(R.id.CustomerName)
        val TransferButton:Button=itemView.findViewById(R.id.Transferbutton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val ItemView=LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(ItemView)
    }

    override fun getItemCount(): Int {
        return peopleList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        var currentItem=peopleList[position]
        holder.Customer_Name.setText(currentItem.name)
        holder.TransferButton.setOnClickListener {
            val builder=AlertDialog.Builder(context)
            val inflater= LayoutInflater.from(context)
            val dialogLayout=inflater.inflate(R.layout.edit_text_layout,null)
            val editText=dialogLayout.findViewById<EditText>(R.id.et_editText)
            with(builder)
            {
                setTitle("Amount of Trasnfer to ${currentItem.name}")
                setPositiveButton("OK"){dialog,which->
                    var databaseHelper:SqlDatabase
                    databaseHelper= SqlDatabase(context)
                    var id:Int
                    var name:String
                    var email:String
                     var balance:Int=0
                    val cursor=databaseHelper.readableDatabase.rawQuery("SELECT * FROM Persons where id=1",
                        arrayOf<String>()
                    )
                    while (cursor.moveToNext())
                    {
                    id=cursor.getInt(0)
                     name=cursor.getString(1)
                     email=cursor.getString(2)
                     balance=cursor.getInt(3)

                    }
                    cursor.close()
                     val newBalanceSender=balance-editText.text.toString().toInt()
                     val newBalanceReciever=editText.text.toString().toInt()+currentItem.Balance.toInt()
                    //MainActivity.updateBalance(newBalanceSender)
                   //  var Amount:TextView
                 //   Amount=MainActivity().findViewById(R.id.Amount)
                   // Amount.text=newBalanceSender.toString()
                    val SqlClass=SqlDatabase(context)
                    Log.d("Main","Current id=> ${currentItem.id}")
                    SqlClass.updateData(currentItem.id,newBalanceReciever)
                    SqlClass.updateData(1,newBalanceSender)

                    SqlClass.AddTransactionRecord("Youssef",currentItem.name.toString(),editText.text.toString().toInt())
                }
                setNegativeButton("Cancel"){dialog,which->
                    Log.d("Main","Negative Button Clicked")
                }
                setView(dialogLayout)
                show()
            }
        }
    }
}