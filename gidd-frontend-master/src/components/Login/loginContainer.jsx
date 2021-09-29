
import { connect } from 'react-redux';
import { withRouter } from 'react-router';
import { login,authUser } from '../../data/actions/auth';
import Login from './login';

const mapStateToProps = (state) => ({
  isLoggedIn: state.auth.isLoggedIn,
});

// const mapDispatchToProps = (dispatch) => ({ getCourses });

const LoginContainer = connect(mapStateToProps, { login, authUser }, null, {
  forwardRef: true,
})(Login);

export default withRouter(LoginContainer);