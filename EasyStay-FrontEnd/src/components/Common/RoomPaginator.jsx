import React from "react";

function RoomPaginator({ currentPage, totalPages, onPageChange }) {
  const pageNumbers = Array.from({ length: totalPages }, (_, i) => i + 1);
  return (
    <>
      <ul className="pagination justify-content-center">
        {pageNumbers.map((pageNumber) => {
          return (
            <li
              key={pageNumber}
              className={`page-item ${
                currentPage === pageNumber ? "active" : ""
              }`}
            >
              <button
                className="page-link"
                onClick={() => onPageChange(pageNumber)}
              >
                {pageNumber}
              </button>
            </li>
          );
        })}
      </ul>
    </>
  );
}

export default RoomPaginator;
