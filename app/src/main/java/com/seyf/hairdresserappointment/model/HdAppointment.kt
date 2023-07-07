package com.seyf.hairdresserappointment.model

data class HdAppointment(
    val custName: String,
    val employeeName: String,
    val appointmentDate: String,
    val custContact: String,
    val appointmentStatus: String,
    val serviceName: String,
    val serviceTime: String,
    val servicePrice: String,
    val appointmentDocId: String
)