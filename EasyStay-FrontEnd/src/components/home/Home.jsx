import React from "react";
import HeaderMain from "../layouts/HeaderMain";
import HotelService from "../Common/HotelService";
import Parallax from "../Common/Parallax";
import RoomCariusel from "../Common/RoomCariusel";
import RoomSearch from "../Common/RoomSearch";

function Home(props) {
  return (
    <section>
      <HeaderMain />
      <section className="container">
        <RoomSearch />
        <RoomCariusel />
        <Parallax />
        <RoomCariusel />
        <HotelService />
        <Parallax />
        <RoomCariusel />
      </section>
    </section>
  );
}

export default Home;
