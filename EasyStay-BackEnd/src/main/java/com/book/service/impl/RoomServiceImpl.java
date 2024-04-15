package com.book.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.book.exception.InternalServalException;
import com.book.exception.ResourceNotFoundException;
import com.book.model.Room;
import com.book.repository.RoomRepository;
import com.book.service.RoomService;

@Service
public class RoomServiceImpl implements RoomService {

	@Autowired
	private RoomRepository roomRepository;

	@Override
	public Room addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice)
			throws SQLException, IOException {

		Room room = new Room();
		room.setRoomType(roomType);
		room.setRoomPrice(roomPrice);
		if (!photo.isEmpty()) {
			byte[] photoBytes = photo.getBytes();
			Blob photoBlob = new SerialBlob(photoBytes);

			room.setPhoto(photoBlob);

		}

		return roomRepository.save(room);

	}

	@Override
	public List<String> getAllRoomTypes() {

		return roomRepository.findDistinctRoomTypes();
	}

	@Override
	public List<Room> getAllRooms() {
		// TODO Auto-generated method stub
		return roomRepository.findAll();
	}

	@Override
	public byte[] getRoomPhotoByRoomId(Long roomId) throws SQLException {
		// TODO Auto-generated method stub
		Optional<Room> theRoom = roomRepository.findById(roomId);
		if (theRoom.isEmpty()) {
			throw new ResourceNotFoundException("Sorry, Room not found");
		}
		Blob photo = theRoom.get().getPhoto();
		if (photo != null) {
			return photo.getBytes(1, (int) photo.length());
		}
		return null;
	}

	@Override
	public void deleteRoom(Long roomId) {
		// TODO Auto-generated method stub
		Optional<Room> byId = roomRepository.findById(roomId);
		if (byId.isPresent()) {
			roomRepository.deleteById(roomId);
		}
	}

	@Override
	public Room updateRoom(Long roomId, String roomType, BigDecimal roomPrice, byte[] photoBytes) {
		// TODO Auto-generated method stub
		Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room Not Found"));
		if(roomType!=null) room.setRoomType(roomType);
		if(roomPrice!=null)room.setRoomPrice(roomPrice);
		if(photoBytes!=null &&photoBytes.length>0) {
			try {
				room.setPhoto(new SerialBlob(photoBytes));
			} catch (Exception e) {
				throw new InternalServalException("Error Updating room");
			}
		}
		return roomRepository.save(room);
	}

	@Override
	public Optional<Room> getRoomById(Long roomId) {
		
		return roomRepository.findById(roomId);
	}

	@Override
	public List<Room> getAvailableRooms(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
		// TODO Auto-generated method stub
		return roomRepository.findAvailableRoomsByDatesAndType(checkInDate,checkOutDate,roomType);
	}

}
