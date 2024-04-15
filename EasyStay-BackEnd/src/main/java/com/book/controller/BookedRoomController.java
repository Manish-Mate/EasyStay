package com.book.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.book.exception.InvalidBookingRequestException;
import com.book.exception.ResourceNotFoundException;
import com.book.model.BookedRoom;
import com.book.model.Room;
import com.book.response.BookingResponse;
import com.book.response.RoomResponse;
import com.book.service.BookedRoomService;
import com.book.service.RoomService;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/bookings")
public class BookedRoomController {

	@Autowired
	private  BookedRoomService bookedRoomService;

	@Autowired
	private  RoomService roomService;

	@GetMapping("/all-bookings")
	public ResponseEntity<List<BookingResponse>> getAllBookings() {
		List<BookedRoom> bookings = bookedRoomService.getAllBookings();
		List<BookingResponse> bookingResponses = new ArrayList<>();
		for (BookedRoom room : bookings) {
			BookingResponse bookingResponse = getBookingResponse(room);
			bookingResponses.add(bookingResponse);
		}
		return ResponseEntity.ok(bookingResponses);
	}

	@GetMapping("/confirmation/{confirmationCode}")
	public ResponseEntity<?> getBookingByConfirmationCode(@PathVariable String confirmationCode) {
		try {
			BookedRoom booking = bookedRoomService.findByBookingConfirmationCode(confirmationCode);
			BookingResponse bookingResponse = getBookingResponse(booking);
			System.out.println(bookingResponse);
			return ResponseEntity.ok(bookingResponse);
		} catch (ResourceNotFoundException e) {
			// TODO: handle exception
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		}
	}

	@PostMapping("/room/{roomId}/booking")
	public ResponseEntity<?> saveBooking(@PathVariable Long roomId, @RequestBody BookedRoom bookingRequest) {
		System.out.println(bookingRequest.toString());
		try {
			String confirmationCode = bookedRoomService.saveBooking(roomId, bookingRequest);
			return ResponseEntity
					.ok("Room booked Successfully! Your booking confirmation code is :" + confirmationCode);
		} catch (InvalidBookingRequestException e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@DeleteMapping("/{bookingId}/delete")
	public void cancelBooking(@PathVariable Long bookingId) {
		bookedRoomService.canselBooking(bookingId);
	}

	private BookingResponse getBookingResponse(BookedRoom booking) {
		Room room = roomService.getRoomById(booking.getRoom().getId()).get();
		RoomResponse roomResponse = new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice());

		return new BookingResponse(booking.getBookingId(), booking.getCheckInDate(), booking.getCheckOutDate(),
				booking.getGuestFullName(), booking.getGuestEmail(), booking.getNumOfAdults(), booking.getNumOfChildren(),
				booking.getTotalNumOfGuest(), booking.getBookingConfirmationCode(), roomResponse);
	}

}
