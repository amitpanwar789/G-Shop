import React from "react";
import { Pagination } from "react-bootstrap";
import { Link } from "react-router-dom";

const Paginate = ({ pages, page, isAdmin = false, keyword = "" }) => {
  return (
    pages > 1 && (
      <Pagination className="p-4">
        {[...Array(pages).keys()].map((x) => (
          <Link class = "text-decoration-none mr-4 mb-5"
            key={x + 1}
            to={
              !isAdmin
                ? keyword
                  ? `/search/${keyword}/page/${x + 1}`
                  : `/page/${x + 1}`
                : `/admin/productlist/${x + 1}`
            }
          >
            <Pagination.Item 
              href={
                !isAdmin
                  ? keyword
                    ? `/search/${keyword}/page/${x + 1}`
                    : `/page/${x + 1}`
                  : `/admin/productlist/${x + 1}`
              }
              active={x + 1 === page}
              activeLabel={''}
            >
              {x + 1}
            </Pagination.Item>
          </Link>
        ))}
      </Pagination>
    )
  );
};

export default Paginate;
