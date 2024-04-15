package com.book.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.book.exception.PhotoRetrivealException;
import com.book.exception.ResourceNotFoundException;
import com.book.model.BookedRoom;
import com.book.model.Room;
import com.book.response.BookingResponse;
import com.book.response.RoomResponse;
import com.book.service.BookedRoomService;
import com.book.service.RoomService;
import com.book.service.impl.BookedRoomServiceImpl;

@CrossOrigin("http://localhost:5173")
@RestController
@RequestMapping("/rooms")
public class RoomController {

	@Autowired
	private RoomService roomService;
	@Autowired
	private BookedRoomService bookedRoomService;

	@PostMapping("/add/new-room")
	public ResponseEntity<RoomResponse> addNewRoom(@RequestParam("photo") MultipartFile photo,
			@RequestParam("roomType") String roomType, @RequestParam("roomPrice") BigDecimal roomPrice)
			throws SQLException, IOException {

//		Room savedRoom= roomService.addNewRoom(photo,roomType,roomPrice);
		Room savedRoom = roomService.addNewRoom(photo, roomType, roomPrice);

		RoomResponse response = new RoomResponse(savedRoom.getId(), savedRoom.getRoomType(), savedRoom.getRoomPrice());

		return ResponseEntity.ok(response);

	}

	@GetMapping("/room-types")
	public List<String> getRoomTypes() {
		return roomService.getAllRoomTypes();
	}

	@GetMapping("/all-rooms")
	public ResponseEntity<List<RoomResponse>> getAllRooms() throws SQLException {
		List<Room> rooms = roomService.getAllRooms();
		List<RoomResponse> roomResponses = new ArrayList<>();
		for (Room room : rooms) {
			byte[] photoBytes = roomService.getRoomPhotoByRoomId(room.getId());
			if (photoBytes != null && photoBytes.length > 0) {
				String base64Photo = Base64.encodeBase64String(photoBytes);
				RoomResponse roomResponse = getRoomResponse(room);
				roomResponse.setPhoto(base64Photo);
				roomResponses.add(roomResponse);
			}

		}
		return ResponseEntity.ok(roomResponses);
	}

	@DeleteMapping("/delete/room/{roomId}")
	public ResponseEntity<Void> deleteRoom(@PathVariable Long roomId) {
		roomService.deleteRoom(roomId);
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}

	@PutMapping("/update/{roomId}")
	public ResponseEntity<RoomResponse> updateRoom(@PathVariable Long roomId,
			@RequestParam(required = false) String roomType, @RequestParam(required = false) BigDecimal roomPrice,
			@RequestParam(required = false) MultipartFile photo) throws IOException, SQLException {

		byte[] photoBytes = (photo != null && !photo.isEmpty()) ? photo.getBytes()
				: roomService.getRoomPhotoByRoomId(roomId);
		Blob photoBlob = photoBytes != null && photoBytes.length > 0 ? new SerialBlob(photoBytes) : null;
		Room theRoom = roomService.updateRoom(roomId, roomType, roomPrice, photoBytes);

		theRoom.setPhoto(photoBlob);
		RoomResponse roomResponse = getRoomResponse(theRoom);
		return ResponseEntity.ok(roomResponse);
	}

	@GetMapping("/room/{roomId}")
	public ResponseEntity<Optional<RoomResponse>> getRoomById(@PathVariable Long roomId) {
		Optional<Room> theRoom = roomService.getRoomById(roomId);

		return theRoom.map(room -> {
			RoomResponse roomResponse = getRoomResponse(theRoom.get());
			return ResponseEntity.ok(Optional.of(roomResponse));
		}).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
	}

	@GetMapping("/available-rooms")
	public ResponseEntity<List<RoomResponse>> getAvailableRooms(
			@RequestParam("checkInDate") @DateTimeFormat(iso = ISO.DATE_TIME) LocalDate checkInDate,
			@RequestParam("checkOutDate")@DateTimeFormat(iso = ISO.DATE_TIME) LocalDate checkOutDate,
			@RequestParam("roomType")String roomType) throws IOException, SQLException{
	
		List<Room> availableRooms=roomService.getAvailableRooms(checkInDate,checkOutDate,roomType);
		List<RoomResponse> roomResponses=new ArrayList<>();
		for(Room room : availableRooms) {
			byte[] photoBytes=roomService.getRoomPhotoByRoomId(room.getId());
			if(photoBytes!=null && photoBytes.length>0) {
				String photoBase64=Base64.encodeBase64String(photoBytes);
				RoomResponse roomResponse=getRoomResponse(room);
				roomResponse.setPhoto(photoBase64);
				roomResponses.add(roomResponse);
			}
		}
		if(roomResponses.isEmpty()) {
			return ResponseEntity.noContent().build();
		}
		else {
			return ResponseEntity.ok(roomResponses);
		}
	}

	private RoomResponse getRoomResponse(Room room) {
		List<BookedRoom> booking = getAllBookingByRoomId(room.getId());
//		List<BookingResponse> bookingResponses=booking.stream().
//				map(book->
//				new BookingResponse
//				(book.getBookingId(),book.getCheckInDate(),book.getCheckOutDate(),book.getBookingConfirmationCode()))
//				.toList();

		byte[] photoByte = null;
		Blob photoBlop = room.getPhoto();
		if (photoBlop != null) {
			try {
				photoByte = photoBlop.getBytes(1, (int) photoBlop.length());
			} catch (SQLException e) {
				throw new PhotoRetrivealException("Error retriving photo");
			}
		}
		return new RoomResponse(room.getId(), room.getRoomType(), room.getRoomPrice(), room.isBooked(), photoByte);

	}

	private List<BookedRoom> getAllBookingByRoomId(Long id) {
		// TODO Auto-generated method stub
		return bookedRoomService.getAllBookingsByRoomId(id);
	}
}
