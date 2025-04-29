import React from "react";
import { Card } from "react-bootstrap";
import Rating from "./Rating";
import { Link } from "react-router-dom";

function Product({ product }) {
  return (
    <Link class="text-decoration-none" to={`/product/${product._id}`}>
      <Card
        className="product-card my-2"
        style={{ width: "auto", height: "100%" }}
      >
        <Card.Img src={product.image} variant="top" />
        <Card.Body>
          <Card.Title as="div" style={{minHeight:"40%"}}>
            <strong class="text-dark font-size-4">{product.name}</strong>
          </Card.Title>
          <Card.Text as="div" class="text-muted">
            <Rating
              value={product.rating}
              text={`${product.numReviews} reviews`}
            />
          </Card.Text>
          <Card.Text style={{ color: "#1f2120", fontWeight: "400" }} as="h6">
            ₹{product.price}
          </Card.Text>
        </Card.Body>
      </Card>
    </Link>
  );
}

export default Product;
