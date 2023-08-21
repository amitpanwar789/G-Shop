import React from "react";
import { useDispatch, useSelector } from "react-redux";
import { Navbar, Nav, Container, NavDropdown } from "react-bootstrap";
import { Link, Route } from "react-router-dom";
import { logout } from "../actions/userActions";
import SearchBox from './SearchBox'

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
      <Navbar bg="dark" variant="dark" expand="lg" collapseOnSelect>
        <Container fluid>
          <Link class = "text-decoration-none" to="/">
            <Navbar.Brand >G-Shop</Navbar.Brand>
          </Link>
          <Container class="d-flex justify-content-end bot" fluid>
          <Navbar.Toggle aria-controls="basic-navbar-nav" />
          <Navbar.Collapse id="basic-navbar-nav">
          <Route render={({ history }) => <SearchBox history={history} />} />
          <Nav
            className="ml-auto my-2 my-lg-0 "
            style={{ maxHeight: '80px' }}
            navbarScroll
          >
              <Link class = "text-decoration-none" to="/cart">
                <Nav.Link  href="/cart">
                  <i className="fas fa-shopping-cart" ></i> Cart
                </Nav.Link>
              </Link>
              {userInfo ? (
                <NavDropdown title={userInfo.name} id="username">
                  <NavDropdown.Item href="/profile">Profile</NavDropdown.Item>
                  <NavDropdown.Item onClick={logoutHandler}>
                    Logout
                  </NavDropdown.Item>
                </NavDropdown>
              ) : (
                <Link class = "text-decoration-none"  to="/login">
                  <Nav.Link href="/login">
                    <i className="fas fa-user"></i> Sign In
                  </Nav.Link>
                </Link>
              )}
              {userInfo && userInfo.isAdmin && (
                <NavDropdown title='Admin' id='adminmenu'>
                  <Link class = "text-decoration-none" to='/admin/userlist'>
                    <NavDropdown.Item class="ml-3" href='/admin/userList'>Users</NavDropdown.Item>
                  </Link>
                  <Link class = "text-decoration-none" to='/admin/productlist'>
                    <NavDropdown.Item href='/admin/productlist'>Products</NavDropdown.Item>
                  </Link>
                  <Link class = "text-decoration-none" to='/admin/orderlist'>
                    <NavDropdown.Item href='/admin/productlist'>Orders</NavDropdown.Item>
                  </Link>
                </NavDropdown>
              )}
            </Nav>
          </Navbar.Collapse>
          </Container>
        </Container>
      </Navbar>
    </header>
  );
};

export default Header;
