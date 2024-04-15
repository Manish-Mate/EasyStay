package com.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.book.model.BookedRoom;

public interface BookedRoomRepository extends JpaRepository<BookedRoom, Long>{

	List<BookedRoom> findByRoomId(Long roomID);
	
	Optional<BookedRoom> findByBookingConfirmationCode(String confirmationCode);
}
