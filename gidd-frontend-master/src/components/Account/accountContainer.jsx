import { connect } from "react-redux";
import { withRouter } from "react-router";
import Account from "./account";
import EditAccountForm from "./EditAccountForm";
import LoaderWheel from "../loaderWheel/loaderWheel";
import { refreshUser, authUser } from "../../data/actions/auth";
import { putUser } from "../../data/actions/user";
import { useEffect } from "react";

const mapStateToProps = (state) => ({
  user: state.auth.user,
  userIsLoading: state.auth.userIsLoading,
  refreshUserError: state.auth.refreshUserError,
  putUserIsLoading: state.user.putUserIsLoading,
  putUserError: state.user.putUserError,
  putUserSuccess: state.user.putUserSuccess,
  redirectToLogin: state.user.redirectToLogin,
});

const AccountContainer = (props) => {
  useEffect(() => {
    console.log("USE EFFECT");
    console.log(props.user);
    props.authUser();
  }, [props.putUserSuccess]);

  const renderAccountView = () => {
    if (props.user) {
      if (props.redirectToLogin) {
        console.log("redirected to login");
        window.location.href = "http://localhost:3000/login";
      }

      return (
        <div className="account-base">
          <div className="account-grid">
            <Account
              user={props.user}
              onEditButtonClicked={props.disableEditButton}
            />
            <EditAccountForm
              formError={props.putUserError}
              putUserIsLoading={props.putUserIsLoading}
              isPutUserSuccess={props.putUserSuccess}
              onUpdateButtonClick={props.putUser}
              onPutUserSuccess={props.refreshUser}
              user={props.user}
            />
          </div>
        </div>
      );
    } else {
      console.log("LOADING REFRESH USER");
      return <LoaderWheel />;
    }
  };
  return renderAccountView();
};

export default withRouter(
  connect(mapStateToProps, { authUser, refreshUser, putUser })(AccountContainer)
);
