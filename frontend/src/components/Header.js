import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navbar, Nav, Container, NavDropdown } from "react-bootstrap";
import { Link, Route } from "react-router-dom";
import { logout } from "../actions/userActions";
import SearchBox from "./SearchBox";
import "./Header.css"

const Header = ({ history }) => {
  const dispatch = useDispatch();

  const userLogin = useSelector((state) => state.userLogin);
  const { userInfo } = userLogin;

  const logoutHandler = () => {
    dispatch(logout());
    history.push("/login");
  };
  return (
    <header>
      <Navbar bg="primary" data-bs-theme="light" expand="lg" collapseOnSelect>
        <Container fluid class="d-flex">
          <Link class="text-decoration-none " to="/">
            <Navbar.Brand className="text-light fs-1 mx-4 fw-bolder">
              G-Shop
            </Navbar.Brand>
          </Link>
          <Navbar.Toggle aria-controls="basic-navbar-nav" style={{color: "transparent",border: "1px solid black"}}/>
          <Navbar.Collapse id="basic-navbar-nav" style={{ justifyContent: "flex-end" }}>
            <Route render={({ history }) => <SearchBox history={history} />} />
            <Nav
              className="ml-auto my-2 my-lg-0"
              style={{ maxHeight: "80px" }}
              navbarScroll
            >
              <Link class="text-decoration-none" to="/cart">
                <Nav.Link href="/cart" className="text-light">
                  <i
                    className="fas fa-shopping-cart"
                    style={{ margin: "2px", color: "#FFF" }}
                  ></i>{" "}
                  Cart
                </Nav.Link>
              </Link>
              {userInfo ? (
                <NavDropdown title={userInfo.name} id="username" >
                  <NavDropdown.Item href="/profile" >Profile</NavDropdown.Item>
                  <NavDropdown.Item onClick={logoutHandler}>
                    Logout
                  </NavDropdown.Item>
                </NavDropdown>
              ) : (
                <Link class="text-decoration-none " to="/login">
                  <Nav.Link href="/login" style={{color:"#FFF"}}>
                    <i
                      className="fas fa-user"
                      style={{ margin: "2px", color: "#FFF" }}
                    ></i>{" "}
                    Sign In
                  </Nav.Link>
                </Link>
              )}
              {userInfo && userInfo.isAdmin && (
                <NavDropdown title="Admin" id="adminmenu">
                  <Link class="text-decoration-none" to="/admin/userlist">
                    <NavDropdown.Item class="ml-3" href="/admin/userList">
                      Users
                    </NavDropdown.Item>
                  </Link>
                  <Link class="text-decoration-none" to="/admin/productlist">
                    <NavDropdown.Item href="/admin/productlist">
                      Products
                    </NavDropdown.Item>
                  </Link>
                  <Link class="text-decoration-none" to="/admin/orderlist">
                    <NavDropdown.Item href="/admin/productlist">
                      Orders
                    </NavDropdown.Item>
                  </Link>
                </NavDropdown>
              )}
            </Nav>
          </Navbar.Collapse>

          {/* </Container> */}
        </Container>
      </Navbar>
    </header>
  );
};

export default Header;
