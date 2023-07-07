package com.seyf.hairdresserappointment.model

data class CustAppointment(
    val hairDresserName: String,
    val employeeName: String,
    val serviceName: String,
    val appointmentTime: String,
    val appointmentStatus: String,
    val hairDresserAddress: String,
    val employeeContact: String,
    val servicePrice: String,
    val serviceTime: String,
    val appointmentDocId:String,
    val hairDresserId: String,
    val employeeId: String
)