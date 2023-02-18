package com.example.dbproject.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Contact (
    @PrimaryKey(autoGenerate = true)
        val id : Int = 0,
    val name: String,
    val phone: String
):Parcelable