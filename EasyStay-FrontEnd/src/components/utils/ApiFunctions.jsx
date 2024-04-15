import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080",
});
// this function adds a new room in database
export async function addRoom(photo, roomType, roomPrice) {
  roomPrice = "" + roomPrice + "";
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomPrice", roomPrice);
  console.log(roomPrice);
  console.log(roomType);
  console.log(photo);
  const response = await api.post("/rooms/add/new-room", formData);
  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}
// This function gets all room types from database
export async function getRoomTypes() {
  try {
    const response = await api.get("/rooms/room-types");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}
// this functon get all rooms fron the database
export async function getAllRooms() {
  try {
    const response = await api.get("/rooms/all-rooms");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching rooms");
  }
}

//this function delete rooms by the ID
export async function deleteRoom(roomId) {
  try {
    const result = await api.delete(`/rooms/delete/room/${roomId}`);
    return result.data;
  } catch (error) {
    throw new Error(`Error deleting room ${error.message}`);
  }
}
//this function updates a room
export async function updateRoom(roomId, roomData) {
  const formData = new FormData();
  formData.append("roomType", roomData.roomType);
  formData.append("roomPrice", roomData.roomPrice);
  formData.append("photo", roomData.photo);
  const response = await api.put(`rooms/update/${roomId}`, formData);
  return response;
}
//this function gets a room by the id
export async function getRoomById(roomId) {
  try {
    console.log("id " + roomId);
    const result = await api.get(`/rooms/room/${roomId}`);
    return result.data;
  } catch (error) {
    throw new Error(`Error Fetching room ${error.message}`);
  }
}
// all the below functionn are for booking id not for the rooms
export async function bookRoom(roomId, booking) {
  try {
    console.log("api function");
    console.log(booking.NumOfAdults);
    console.log(booking.NumOfChildren);
    console.log(typeof booking.NumOfAdults);
    console.log(typeof booking.NumOfChildren);
    const response = await api.post(
      `/bookings/room/${roomId}/booking`,
      booking
    );
    return response.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error booking room : ${error.message}`);
    }
  }
}

export async function getAllBookings() {
  try {
    const result = await api.get(`/bookings/all-bookings`);
    return result.data;
  } catch (error) {
    throw new Error(`Error Fetching booking : ${error.message}`);
  }
}

export async function getBookingByConfirmationCode(confirmationCode) {
  try {
    const result = await api.get(`/bookings/confirmation/${confirmationCode}`);
    return result.data;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error find booking : ${error.message}`);
    }
  }
}

export async function cancelBooking(bookingId) {
  try {
    const result = await api.delete(`/bookings/${bookingId}/delete`);
    return result.data;
  } catch (error) {
    throw new Error(`Error cancelling booking : ${error.message}`);
  }
}

export async function getAvailableRooms(checkInDate, checkOutDate, roomType) {
  try {
    const encodedRoomType = encodeURIComponent(roomType.trim()); // Trim and encode roomType
    const result = await api.get(
      `rooms/available-rooms?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${encodedRoomType}`
    );
    return result;
  } catch (error) {
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`Error find booking : ${error.message}`);
    }
  }
}
