package com.book.service;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.model.BookedRoom;

public interface BookedRoomService {

	List<BookedRoom> getAllBookingsByRoomId(Long id);

	void canselBooking(Long bookingId);

	String saveBooking(Long roomId, BookedRoom bookingRequest);

	BookedRoom findByBookingConfirmationCode(String confirmationCode);

	List<BookedRoom> getAllBookings();

	
}
