package com.book.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.book.exception.InvalidBookingRequestException;
import com.book.exception.ResourceNotFoundException;
import com.book.model.BookedRoom;
import com.book.model.Room;
import com.book.repository.BookedRoomRepository;
import com.book.service.BookedRoomService;
import com.book.service.RoomService;

@Service
public class BookedRoomServiceImpl implements BookedRoomService {

	@Autowired
	private BookedRoomRepository bookedRoomRepository;

	@Autowired
	private RoomService roomService;

	@Override
	public List<BookedRoom> getAllBookingsByRoomId(Long id) {

		return bookedRoomRepository.findByRoomId(id);
	}

	@Override
	public void canselBooking(Long bookingId) {
		// TODO Auto-generated method stub
		bookedRoomRepository.deleteById(bookingId);

	}

	@Override
	public String saveBooking(Long roomId, BookedRoom bookingRequest) {
		if (bookingRequest.getCheckOutDate().isBefore(bookingRequest.getCheckInDate())) {
			throw new InvalidBookingRequestException("Check-in data must come before check-out date");

		}
		Room room = roomService.getRoomById(roomId).get();
		List<BookedRoom> bookings = room.getBookings();
		boolean roomIsAvailable = roomIsAvailable(bookingRequest, bookings);
		if (roomIsAvailable) {
			room.addBooking(bookingRequest);
			bookedRoomRepository.save(bookingRequest);

		} else {
			throw new InvalidBookingRequestException("Sorry, This room is not available for the selected dates");
		}
		return bookingRequest.getBookingConfirmationCode();
	}

	@Override
	public BookedRoom findByBookingConfirmationCode(String confirmationCode) {
		// TODO Auto-generated method stub
		return bookedRoomRepository.findByBookingConfirmationCode(confirmationCode)
				.orElseThrow(() -> new ResourceNotFoundException("No booking found with the booking code :"+ confirmationCode));
	}

	@Override
	public List<BookedRoom> getAllBookings() {
		// TODO Auto-generated method stub
		return bookedRoomRepository.findAll();
	}

	private boolean roomIsAvailable(BookedRoom bookingRequest, List<BookedRoom> bookings) {

		return bookings.stream()
				.noneMatch(existingBooking -> bookingRequest.getCheckInDate().equals(existingBooking.getCheckInDate())
						|| bookingRequest.getCheckOutDate().isBefore(existingBooking.getCheckOutDate())
						|| (bookingRequest.getCheckInDate().isAfter(existingBooking.getCheckInDate())
								&& bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckOutDate()))
						|| (bookingRequest.getCheckInDate().isBefore(existingBooking.getCheckInDate())

								&& bookingRequest.getCheckOutDate().isAfter(existingBooking.getCheckOutDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(existingBooking.getCheckInDate()))

						|| (bookingRequest.getCheckInDate().equals(existingBooking.getCheckOutDate())
								&& bookingRequest.getCheckOutDate().equals(bookingRequest.getCheckInDate())));
	}

}
