import React, { useEffect, useState } from "react";
import { getRoomById, updateRoom } from "../utils/ApiFunctions";
import { Link, useParams } from "react-router-dom";
// import { RoomTypeSelector } from "../Common/RoomTypeSelector";

function EditRoom(props) {
  const [room, setRoom] = useState({
    photo: null,
    roomType: "",
    roomPrice: "",
  });

  const [imagePreview, setImagePreview] = useState("");
  const [successMessage, setSuccessMessage] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const { roomId } = useParams();

  const handleImageChange = (e) => {
    const selectedImage = e.target.files[0];
    setRoom({ ...room, photo: selectedImage });
    setImagePreview(URL.createObjectURL(selectedImage));
  };
  const handleInputChange = (e) => {
    const { name, value } = e.target;
    // let value = e.target.value;
    // if (name === "roomPrice") {
    //   if (!isNaN(value)) {
    //     value = parseInt(value);
    //   } else {
    //     value = "";
    //   }
    // }
    setRoom({ ...room, [name]: value });
  };

  useEffect(() => {
    const fetchRoom = async () => {
      try {
        const roomData = await getRoomById(roomId);

        setRoom(roomData);
        setImagePreview(roomData.photo);
      } catch (error) {
        console.error(error);
      }
    };
    fetchRoom();
  }, [roomId]);
  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const response = await updateRoom(roomId, room);
      console.log(response.status);
      if (response.status === 200) {
        setSuccessMessage("room Updated successfully!!");
        const updateRoomData = await getRoomById(roomId);
        setRoom(updateRoomData);
        setImagePreview(updateRoomData.photo);
        setErrorMessage("");
      } else {
        setErrorMessage("Error Updating roon");
      }
    } catch (error) {
      console.error(error);
      setErrorMessage(error.message);
    }
  };
  return (
    <>
      <section className="container mt-5 mb-5">
        <div className="row justify-content-center">
          <div className="col-md-8 col-lg-6">
            <h2 className="mt-5 text-start mb-2">Add a New Room</h2>

            {successMessage && (
              <div>
                <div className="alert alert-success fade show">
                  {successMessage}
                </div>
              </div>
            )}
            {errorMessage && (
              <div>
                <div className="alert alert-danger fade show">
                  {errorMessage}
                </div>
              </div>
            )}

            <form onSubmit={handleSubmit}>
              <div className="mb-3">
                <label className="form-label text-start" htmlFor="roomType">
                  Room Type
                </label>
                <input
                  type="text"
                  className="form-control"
                  id="roomType"
                  name="roomType"
                  value={room.roomType}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label htmlFor="roomPrice" className="form-label">
                  Room Price
                </label>
                <input
                  required
                  type="number"
                  className="form-control"
                  id="roomPrice"
                  name="roomPrice"
                  value={room.roomPrice}
                  onChange={handleInputChange}
                />
              </div>
              <div className="mb-3">
                <label className="form-label text-start" htmlFor="photo">
                  Room Photo
                </label>
                <input
                  type="file"
                  id="photo"
                  name="photo"
                  className="form-control"
                  onChange={handleImageChange}
                />
                {imagePreview && (
                  <img
                    src={`data:image/jpeg;base64,${imagePreview}`}
                    alt="Preview Room Photo"
                    style={{ maxWidth: "400px", maxHeight: "400px" }}
                    className="mb-3"
                  />
                )}
                <div className="d-grid d-flex mt-2 ">
                  <Link
                    to={"/existing-rooms"}
                    className="btn btn-outline-info ml-5"
                  >
                    Back
                  </Link>
                  <button className="btn btn-outline-warning ">
                    Edit Room
                  </button>
                </div>
              </div>
            </form>
          </div>
        </div>
      </section>
    </>
  );
}

export default EditRoom;
