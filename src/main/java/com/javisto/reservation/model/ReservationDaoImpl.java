package com.javisto.reservation.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class ReservationDaoImpl extends NamedParameterJdbcTemplate implements ReservationDAO
{
    private static final String QUERY_NEXT_APPOINTMENT     = "SELECT * FROM appointment a LEFT OUTER JOIN reservation r ON a.id = r.appointment_id WHERE r.id is null ORDER by a.time LIMIT 1";

    private static final String QUERY_USER_ID              = "SELECT id FROM users WHERE email=?";

    private static final String UPDATE_INSERT_RESERVATION  = String
                                                               .format("INSERT INTO reservation(appointment_id, users_id, confirmed) VALUES(?, (%s), false)",
                                                                       QUERY_USER_ID);

    private static final String UPDATE_CONFIRM_RESERVATION = "UPDATE reservation SET confirmed=true WHERE appointment_id=?";

    @Autowired
    public ReservationDaoImpl(DataSource dataSource)
    {
        super(dataSource);
    }

    @Override
    public synchronized Appointment reserveNextAppointment(String email)
    {
        List<Appointment> appointments = getJdbcOperations().query(QUERY_NEXT_APPOINTMENT, new RowMapper<Appointment>()
        {
            @Override
            public Appointment mapRow(ResultSet rs, int num) throws SQLException
            {
                return new Appointment(rs.getInt("id"), rs.getTimestamp("time"), rs.getInt("doctor_id"));
            }
        });

        if (appointments.isEmpty())
        {
            // No more appointment available. Return blank object.
            return ReservationDAO.BLANK_APPOINTMENT;
        }

        if (appointments.size() > 1)
        {
            throw new IllegalStateException("Should never return more than 1 appointment.");
        }

        Appointment appointment = appointments.get(0);
        getJdbcOperations().update(UPDATE_INSERT_RESERVATION, appointment.getId(), email);
        return appointment;
    }

    @Override
    public void confirm(long appointmentId)
    {
        getJdbcOperations().update(UPDATE_CONFIRM_RESERVATION, appointmentId);
    }
}
