package com.seyf.hairdresserappointment.model

import android.os.Parcel
import android.os.Parcelable

data class Service(
    val serviceName: String,
    val serviceTime: String,
    val serviceId: String,
    val hairDresserId: String,
    val employeeId: String,
    val hairDresserName: String,
    val customerName: String,
    val customerId: String,
    val employeeName: String,
    val employeeContact: String,
    val customerContact: String,
    val hairDresserWk: String,
    val servicePrice:String,
    val hairDresserAddress:String
)