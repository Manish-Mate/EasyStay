import React, { useEffect, useState } from "react";
import { getAllRooms } from "../utils/ApiFunctions";
import { Link } from "react-router-dom";
import {
  Card,
  CardBody,
  CardImg,
  CardTitle,
  Carousel,
  CarouselItem,
  Col,
  Container,
  Row,
} from "react-bootstrap";

function RoomCariusel(props) {
  const [rooms, setRooms] = useState([
    {
      id: "",
      roomType: "",
      roomPrice: "",
      photo: "",
    },
  ]);
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoading, setIsLoading] = useState(false);

  useEffect(() => {
    setIsLoading(true);
    getAllRooms()
      .then((data) => {
        setRooms(data);
        setIsLoading(false);
      })
      .catch(
        (error) => {
          setErrorMessage(error.message);
          setIsLoading(false);
        },
        [false]
      );
  }, []);

  if (isLoading) {
    return <div className="mt-5">Loading rooms....</div>;
  }
  if (errorMessage) {
    return <div className="text-danger mb-5 mt-5">Error : {errorMessage}</div>;
  }
  return (
    <section className="bg-light mb-5 mt-5 shadow">
      <Link to={"/browse-all-rooms"} className="hotel-color text-center">
        Browse all rooms
      </Link>
      <Container>
        <Carousel indicators={false}>
          {[...Array(Math.ceil(rooms.length / 4))].map((_, index) => {
            return (
              <CarouselItem key={index}>
                <Row>
                  {rooms.slice(index * 4, index * 4 + 4).map((room) => {
                    return (
                      <Col key={room.id} className="mb-4" xs={12} md={5} lg={3}>
                        <Card>
                          {/* <Link to={`/book-room/${room.id}`}> */}
                          <Link to={`/book-room/${room.id}`}>
                            <CardImg
                              variant="top"
                              src={`data:/image/png;base64, ${room.photo}`}
                              alt="Room Photo"
                              className="w-100"
                              style={{ height: "200px" }}
                            />
                          </Link>
                          <CardBody>
                            <CardTitle className="hotel-color">
                              {room.roomType}
                            </CardTitle>
                            <CardTitle className="room-price">
                              {room.roomPrice}/night
                            </CardTitle>

                            <div className="flex-shrink-0">
                              <Link
                                to={`/book-room/${room.id}`}
                                className="btn btn-hotel btn-sm"
                              >
                                Book Now
                              </Link>
                            </div>
                          </CardBody>
                        </Card>
                      </Col>
                    );
                  })}
                </Row>
              </CarouselItem>
            );
          })}
        </Carousel>
      </Container>
    </section>
  );
}

export default RoomCariusel;
