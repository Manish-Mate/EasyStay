package com.book.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class BookedRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long bookingId;
	@Column(name = "Chech_In")
	private LocalDate checkInDate;
	@Column(name = "Chech_Out")
	private LocalDate checkOutDate;
	@Column(name = "Guest_FullName")
	private String guestFullName;
	@Column(name = "Guest_Email")
	private String guestEmail;
	@Column(name = "Adults")
	private int NumOfAdults;
	@Column(name = "Childrens")
	private int NumOfChildren;
	@Column(name = "Total_Guest")
	private int totalNumOfGuest;
	@Column(name = "Confirmation_Code")
	private String bookingConfirmationCode;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "Room_Id")
	private Room room;
	
	public void calulateTotalNumberOfGuest() {
		this.totalNumOfGuest=this.NumOfAdults+this.NumOfChildren;
	}



	public void setNumOfAdults(int numOfAdults) {
		NumOfAdults = numOfAdults;
		calulateTotalNumberOfGuest();
	}

	

	public void setNumOfChildren(int numOfChildren) {
		NumOfChildren = numOfChildren;
		calulateTotalNumberOfGuest();
	}

	public void setBookingConfirmationCode(String bookingConfirmationCode) {
		this.bookingConfirmationCode = bookingConfirmationCode;
	}

	

	
	
	
}
