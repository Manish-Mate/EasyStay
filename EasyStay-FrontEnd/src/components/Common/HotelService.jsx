import React from "react";
import {
  Card,
  CardBody,
  CardText,
  CardTitle,
  Col,
  Container,
  Row,
} from "react-bootstrap";
import Header from "./Header";
import {
  FaClock,
  FaCocktail,
  FaParking,
  FaSnowflake,
  FaTshirt,
  FaUtensils,
  FaWifi,
} from "react-icons/fa";
function HotelService(props) {
  return (
    <>
      <Container className="mb-2">
        <Header title={"Our Services"} />
        <Row>
          <h4 className="text-center">
            Services at <span className="hotel-color">EasyStay - </span> Hotel
            <span className="gap-2">
              <FaClock /> - 24-Hour Front Desk
            </span>
          </h4>
        </Row>
        <hr />
        <Row xs={1} md={2} lg={3} className="g-4 mt-2">
          <Col>
            <Card>
              <CardBody>
                <CardTitle className="hotel-color">
                  <FaWifi /> WiFi
                </CardTitle>
                <CardText>
                  Stay connected with high-speed internet access.
                </CardText>
              </CardBody>
            </Card>
          </Col>

          <Col>
            <Card>
              <CardBody>
                <CardTitle className="hotel-color">
                  <FaUtensils /> Breakfast
                </CardTitle>
                <CardText>
                  Start your day with a delicious breakfast buffet.
                </CardText>
              </CardBody>
            </Card>
          </Col>
          <Col>
            <Card>
              <CardBody>
                <CardTitle className="hotel-color">
                  <FaTshirt /> Laundry
                </CardTitle>
                <CardText>
                  Keep your clothes clean and fresh with our laundry service.
                </CardText>
              </CardBody>
            </Card>
          </Col>
          <Col>
            <Card>
              <CardBody>
                <CardTitle className="hotel-color">
                  <FaCocktail /> Mini-bar
                </CardTitle>
                <CardText>
                  Enjoy a refreshing drink or snack from our in-room mini-bar.
                </CardText>
              </CardBody>
            </Card>
          </Col>
          <Col>
            <Card>
              <CardBody>
                <CardTitle className="hotel-color">
                  <FaParking /> Parking
                </CardTitle>
                <CardText>
                  Park your car conveniently in our on-site parking lot.
                </CardText>
              </CardBody>
            </Card>
          </Col>
          <Col>
            <Card>
              <CardBody>
                <CardTitle className="hotel-color">
                  <FaSnowflake /> Air conditioning
                </CardTitle>
                <CardText>
                  Stay cool and comfortable with our air conditioning system.
                </CardText>
              </CardBody>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  );
}

export default HotelService;
