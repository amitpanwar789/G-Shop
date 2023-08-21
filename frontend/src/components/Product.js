import React from "react";
import { Card } from "react-bootstrap";
import Rating from './Rating'
import { Link } from "react-router-dom";

function Product({ product }) {
  return (
    <Card className="my-3 p-3 rounded">
      <Link class = "text-decoration-none" to={`/product/${product._id}`}>
        <Card.Img src={product.image} varient="top" />
      </Link>
      <Card.Body>
        <Link class = "text-decoration-none" to={`/product/${product._id}`}>
          <Card.Title as="div">
            <strong class = "text-dark font-size-4">{product.name}</strong>
          </Card.Title>
        </Link>
        <Card.Text as="div">
          <Rating value={product.rating} text={`${product.numReviews} reviews`}/>
        </Card.Text>
        <Card.Text class="font-weight-bold" as="h6" >
          ${product.price}
        </Card.Text>
      </Card.Body>
    </Card>
  );
}

export default Product;
