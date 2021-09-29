import "./header.css";
import { Link } from "react-router-dom";
import React, { useState } from "react";
import { connect } from "react-redux";
import { logout } from "../../data/actions/auth";
import Logo from "../../assets/Logo.svg";
import SmallLogo from "../../assets/GIDD_LOGO.svg";
import Account from "../../assets/User.svg";
import Opprett from "../../assets/Oprett aktivitet.svg";
import { useMediaQuery } from "react-responsive";
import Menu from "@material-ui/core/Menu";
import MenuItem from "@material-ui/core/MenuItem";

const Header = (props) => {
  const handleLogOut = () => {
    props.logout();
  };
  const [anchorEl, setAnchorEl] = React.useState(null);

  const handleClick = (event) => {
    setAnchorEl(event.currentTarget);
  };

  const handleClose = () => {
    setAnchorEl(null);
  };

  const [navbarOpen, setNavbarOpen] = useState(false);
  const [userInfoOpen, setuserInfoOpen] = useState(false);

  const toggleNavBar = () => {
    setNavbarOpen((previous) => !previous);
  };
  const toggleUserInfo = () => {
    setuserInfoOpen((previous) => !previous);
  };

  const isTabletOrMobile = useMediaQuery({ query: "(max-width: 1024px)" });
  const isMobile = useMediaQuery({ query: "(max-width: 500px)" });

  const renderHeader = () => {
    if (!props.user) {
      return <></>;
    } else if (isTabletOrMobile) {
      return (
        <>
          <div className="topbar-container-mobile">
            <button onClick={toggleNavBar}>
              <div className={navbarOpen ? "bar1 change" : "bar1"}></div>
              <div className={navbarOpen ? "bar2 change" : "bar2"}></div>
              <div className={navbarOpen ? "bar3 change" : "bar3"}></div>
            </button>

            <Link to="/">
              <img
                className="topbar-nav-logo"
                src={isMobile ? SmallLogo : Logo}
                alt="GIDD logo"
              />
            </Link>

            <img
              onClick={handleClick}
              className="header-account-mobile"
              src={Account}
              alt="GIDD logo"
            />
          </div>
          <ul className={`topbar-navbar ${navbarOpen ? " showMenu" : ""}`}>
            <li>
              <Link to="/liste" onClick={toggleNavBar}>
                <h2>Aktiviteter</h2>
              </Link>
              <hr />
            </li>
            <li>
              <Link to="/kart" onClick={toggleNavBar}>
                <h2>Kart</h2>
              </Link>
              <hr />
            </li>
            <li>
              <Link to="/kalender" onClick={toggleNavBar}>
                <h2>Kalender</h2>
              </Link>
              <hr />
            </li>
            <li>
              <Link to="/" onClick={toggleNavBar}>
                <h2>Hjem</h2>
              </Link>
              <hr />
            </li>
            <li>
              <Link to="/createactivity" onClick={toggleNavBar}>
                <h2>Opprett aktivitet</h2>
              </Link>
              <hr />
            </li>
            <li>
              <Link to="/leaderboard" onClick={toggleNavBar}>
                <h2>Poengtavle</h2>
              </Link>
              <hr />
            </li>
          </ul>
          <Menu
            id="simple-menu"
            anchorEl={anchorEl}
            keepMounted
            open={Boolean(anchorEl)}
            onClose={handleClose}
          >
            <MenuItem onClick={handleClose}>
              <Link to="/">Hjem</Link>
            </MenuItem>
            <MenuItem onClick={handleClose}>
              <Link to="/account">Konto</Link>
            </MenuItem>
            <MenuItem
              onClick={() => {
                handleClose();
                handleLogOut();
              }}
            >
              <Link to="/login">Logg ut</Link>
            </MenuItem>
          </Menu>
        </>
      );
    } else {
      return (
        <>
          <div className="topbar-container">
            <div className="navigation-container">
              <Link to="/">
                <img src={Logo} alt="GIDD logo" />
              </Link>
              <Link to="/liste">
                <h2>Aktiviteter</h2>
              </Link>

              <Link className="header-create" to="/createactivity">
                <img src={Opprett} alt="GIDD logo" />
              </Link>
              <Link to="/leaderboard">
                <h2>Poengtavle</h2>
              </Link>
              <div className="header-account" onClick={handleClick}>
                <img src={Account} alt="GIDD logo" />
              </div>
            </div>
          </div>
          <Menu
            id="simple-menu"
            anchorEl={anchorEl}
            keepMounted
            open={Boolean(anchorEl)}
            onClose={handleClose}
          >
            <MenuItem onClick={handleClose}>
              <Link to="/">Hjem</Link>
            </MenuItem>
            <MenuItem onClick={handleClose}>
              <Link to="/account">Konto</Link>
            </MenuItem>
            <MenuItem
              onClick={() => {
                handleClose();
                handleLogOut();
              }}
            >
              <Link to="/login">Logg ut</Link>
            </MenuItem>
          </Menu>
        </>
      );
    }
  };
  return <div>{renderHeader()}</div>;
};

export default connect(null, { logout }, null, {
  forwardRef: true,
})(Header);
