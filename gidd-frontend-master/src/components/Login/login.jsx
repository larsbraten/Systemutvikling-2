import { useForm } from "react-hook-form";
import { Link, Redirect } from "react-router-dom";
import "./login.css";
import Facebook from "./Facebook 1.svg";
import Google from "./Google.svg";
import Instagram from "./Instagram.svg";
import Logo from "./Logo.svg";
import { useHistory } from "react-router-dom";
import { useMediaQuery } from "react-responsive";

function Login(props) {
  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  const isTabletOrMobile = useMediaQuery({ query: "(max-width: 1024px)" });

  let history = useHistory();
  const onSubmit = async (data) => {
    console.log("I submittet");
    await props.login(data);
  };

  const redirect = () => {
    props.authUser();
    console.log(history);

    return <Redirect to={props.history.location.pathName} />;
  };

  return (
    <div className="Base">
      <div className="AuthenticationContainer1">
        <div className="RegistrationContainer">
          <h1>Ingen bruker?</h1>
          <h3>Begynn å bruk GIDD i dag!</h3>
          <Link className="loginFormRegister" to="/register">
            <h3>Registrer deg!</h3>
          </Link>
        </div>
        <div className="LoginContainer">
          <div className="LoginLogoContainer">
            <img className="LoginLogo" src={Logo} alt="GIDD logo" />
          </div>
          <h1>
            <b>Logg inn på GIDD!</b>
          </h1>

          <div>
            <div>
              <form className="formContainer" onSubmit={handleSubmit(onSubmit)}>
                {/* register your input into the hook by invoking the "register" function */}{" "}
                <input
                  className="loginFormInput loginFormInputEmail"
                  placeholder="Epost@Epost.no"
                  {...register("email")}
                />
                {/* include validation with required or other standard HTML validation rules */}
                <input
                  className="loginFormInput loginFormInputPassword"
                  type="password"
                  id="password"
                  placeholder="Password"
                  {...register("password", { required: true })}
                />
                {/* errors will return when field validation fails  */}
                {errors.exampleRequired && <span>This field is required</span>}
                <input
                  className="loginFormSubmit"
                  type="submit"
                  value="Logg inn"
                />
              </form>
              {props.isLoggedIn ? redirect() : ""}{" "}
            </div>
          </div>
          <Link to="/">
            <h3>Glemt passord?</h3>
          </Link>
          {isTabletOrMobile ? (
            <>
              <div>
                <hr></hr>
                <h3>Eller</h3>
              </div>
              <Link className="loginFormRegister-mobile" to="/register">
                <h3>Registrer deg!</h3>
              </Link>
            </>
          ) : (
            <>
              <div></div>
              <div></div>
              <div></div>
            </>
          )}

          <div></div>
        </div>
      </div>
    </div>
  );
}

export default Login;
