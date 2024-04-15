import React from "react";
import { Container } from "react-bootstrap";

function Parallax(props) {
  return (
    <div className="parallax mb-5">
      <Container className="text-center px-5 py-5 justify-content-center">
        <div className="animated-text bounceIn">
          <h1>
            Welcome to <span className="hotel-color">EasyStay Hotel</span>
          </h1>
          <h3>We offer the best setvice for all you needs</h3>
        </div>
      </Container>
    </div>
  );
}

export default Parallax;
