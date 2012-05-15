package com.javisto.reservation.model;

import java.sql.Timestamp;

/**
 * TODO is it completely immutable?
 */
public class Appointment
{
    private final long      id;

    private final Timestamp timestamp;

    private final long      doctorId;

    public Appointment(long id, Timestamp timestamp, long doctorId)
    {
        this.id = id;
        this.timestamp = timestamp;
        this.doctorId = doctorId;
    }

    public long getId()
    {
        return id;
    }

    public Timestamp getTimestamp()
    {
        return timestamp;
    }

    public long getDoctorId()
    {
        return doctorId;
    }

    @Override
    public String toString()
    {
        return "Appointment [id=" + id + ", timestamp=" + timestamp + ", doctorId=" + doctorId + "]";
    }
}
