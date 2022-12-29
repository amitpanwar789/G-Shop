import React from "react";
import { Container, Row, Col } from "react-bootstrap";
import "./Footer.css"

function Footer() {
  return (
    <div className="Footer">
      <Container >
        <Row>
          <Col className="text-center py-3">Copyright &copy;G-Shop</Col>
        </Row>
      </Container>
    </div>
  );
}

export default Footer;
