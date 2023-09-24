package com.example.smallbankingapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class SqlDatabase(context: Context) :
    SQLiteOpenHelper(context,DBName,null,DBVersion) {
        companion object{
            private const val DBName="BankingDatabase"
            private const val DBVersion=1
        }

    override fun onCreate(database: SQLiteDatabase?) {
       val personsTableName="Persons"
        val person_id_column="id"
        val person_name_column="name"
        val person_email_column="email"
        val person_balance_column="balance"
        val sql1="CREATE TABLE ${personsTableName} ("+"${person_id_column} INTEGER PRIMARY KEY,${person_name_column} TEXT,${person_email_column} TEXT,${person_balance_column} INTEGER)"
        database?.execSQL(sql1)

        val TransactionsTableName="Transactions"
        val Transactions_id_column="id"
        val Transactions_sender_id="sender"
        val Transactions_reciever_id="reciever"
        val Transactions_amount="amount"
        val sql2="CREATE TABLE ${TransactionsTableName} ("+"${Transactions_id_column} INTEGER PRIMARY KEY  AUTOINCREMENT,${Transactions_sender_id} TEXT,${Transactions_reciever_id} TEXT,${Transactions_amount} INTEGER)"
        database?.execSQL(sql2)




    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
    public fun updateData(recieverID:Int,newRecieverBalance:Int)
    {
        var database: SQLiteDatabase?
        database= writableDatabase
        val sqlQuery="update Persons set balance=${newRecieverBalance} where id=${recieverID}"
        database?.execSQL(sqlQuery)

    }
    public fun AddTransactionRecord(senderName:String,receiverName:String,amount:Int)
    {
        var database: SQLiteDatabase
        database=writableDatabase
       val newEntry= ContentValues().apply {
            put("sender",senderName)
            put("reciever",receiverName)
            put("amount",amount)
        }
        database.insert("Trans",null,newEntry)
    }
    fun createTable()
    {
        var database: SQLiteDatabase
        database=writableDatabase
        val TransactionsTableName="Trans"
        val Transactions_id_column="id"
        val Transactions_sender_id="sender"
        val Transactions_reciever_id="reciever"
        val Transactions_amount="amount"
        val sql2="CREATE TABLE ${TransactionsTableName} ("+"${Transactions_id_column} INTEGER PRIMARY KEY  AUTOINCREMENT NOT NULL,${Transactions_sender_id} TEXT,${Transactions_reciever_id} TEXT,${Transactions_amount} INTEGER)"
        database?.execSQL(sql2)
    }
}