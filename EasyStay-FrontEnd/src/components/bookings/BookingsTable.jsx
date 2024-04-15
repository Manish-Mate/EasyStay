import { parseISO } from "date-fns";
import React, { useEffect, useState } from "react";
import DateSlider from "../Common/DateSlider";

function BookingsTable({ bookingInfo, handleBookingCancellation }) {
  const [filteredBookings, setFilteredeBookings] = useState(bookingInfo);
  const filterBooknigs = (startDate, endDate) => {
    let filtered = bookingInfo;
    if (startDate && endDate) {
      filtered = bookingInfo.filter((booking) => {
        const bookingStartDate = parseISO(booking.checkInDate);
        const bookingEndDate = parseISO(booking.checkOutDate);
        return (
          bookingStartDate >= startDate &&
          bookingEndDate <= endDate &&
          bookingEndDate > startDate
        );
      });
      setFilteredeBookings(filtered);
    } else {
      setFilteredeBookings(bookingInfo);
    }
  };
  useEffect(() => {
    setFilteredeBookings(bookingInfo);
  }, [bookingInfo]);
  return (
    <section className="p-4">
      <DateSlider
        onDateChange={filterBooknigs}
        onFilterChange={filterBooknigs}
      />
      <table className="table table-bordered table-hover shadow">
        <thead>
          <tr>
            <th>S/N</th>
            <th>Booking ID</th>
            <th>Room ID</th>
            <th>Room Type</th>
            <th>Check-In Date</th>
            <th>Check-Out Date</th>
            <th>Guest Name</th>
            <th>Guest Email</th>
            <th>Adults</th>
            <th>Children</th>
            <th>Total Guest</th>
            <th>Confirmation Code</th>
            <th colSpan={2}>Actions</th>
          </tr>
        </thead>
        <tbody className="text-center">
          {filteredBookings.map((booking, index) => (
            <tr key={booking.id}>
              <td style={{ whiteSpace: "nowrap" }}>{index + 1}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.id}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.room.id}</td>
              <td>{booking.room.roomType}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.checkInDate}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.checkOutDate}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.guestName}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.guestEmail}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.numOfAdults}</td>
              <td style={{ whiteSpace: "nowrap" }}>{booking.numOfChildren}</td>
              <td style={{ whiteSpace: "nowrap" }}>
                {booking.totalNumOfGuests}
              </td>
              <td style={{ whiteSpace: "nowrap" }}>
                {booking.bookingConfirmationCode}
              </td>

              <td>
                <button
                  className="btn btn-danger btn-sm"
                  onClick={() => handleBookingCancellation(booking.id)}
                >
                  Cancel
                </button>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
      {filterBooknigs.length === 0 && (
        <p> No booking found for the selected dates</p>
      )}
    </section>
  );
}

export default BookingsTable;
