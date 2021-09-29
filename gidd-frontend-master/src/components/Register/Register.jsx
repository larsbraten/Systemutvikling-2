import React, { useState } from "react";
import { useForm } from "react-hook-form";
import "./Register.css";
import { Link, Redirect } from "react-router-dom";
import Facebook from "../Login/Facebook 1.svg";
import Google from "../Login/Google.svg";
import Instagram from "../Login/Instagram.svg";
import Logo from "./Logo.svg";
import { useMediaQuery } from "react-responsive";

function Register(props) {
  const {
    register,
    handleSubmit,
    watch,
    formState: { errors, isValid, isSubmitting },
  } = useForm({
    mode: "onBlur",
    criteriaMode: "firstError",
    shouldFocusError: true,
    reValidateMode: "onSubmit",
  });
  const onSubmit = (data, e) => {
    e.preventDefault();
    console.log(data);

    props.onSubmitClicked();

    props.saveEmail(data.email);

    props.onRegister({
      persona: {
        firstName: data.firstName,
        surName: data.lastName,

        // "birthday": "2021-04-21"
      },
      contactInfo: {
        email: data.email,
        phoneNumber: data.phoneNumber,
      },
      credentials: {
        email: data.email,
        password: data.password,
      },
      activityLevel: data.activityLevel,
    });

    if (props.isRegistered) {
      //console.log("RESETTING FORM")
      props.saveEmail(data.email);
      e.target.reset();
    }
  };

  const onErrors = (errors) => {
    console.log("ERRORS:", errors);
  };

  const prepareSubmitBtnValue = () => {
    if (props.isLoading) {
      // todo replace with loaderWheel
      return <progress className="Register-progress" max="100" value="50" />;
    } else {
      return "Register";
    }
  };

  const registerOptions = {
    firstName: {
      required: "Fornavn er påkrevd",
    },
    lastName: {
      required: "Etternavn er påkrevd",
      minLength: 2,
    },
    email: {
      required: "Epost er påkrevd",
    },
    phoneNumber: {
      required: "Telefonnummer er påkrevd",
      pattern: {
        value: /[0-9]{8}/,
        message: "Feil format",
      },
      minLength: {
        value: 8,
        message: "For kort",
      },
      maxLength: {
        value: 8,
        message: "For langt",
      },
    },
    password: {
      required: "Passord er påkrevd",
      minLength: {
        value: 8,
        message: "Passord må ha minst 8 tegn",
      },
      pattern: {
        // value: /^(?=.*\d)(?=.*[a-z])(?=.*[A-Z])[0-9a-zA-Z]{8,}$/,
        value: /[\w.]*/,
        message: "Ugyldig passord format",
      },
    },
    repeatPassword: {
      validate: (value) =>
        value === watch("password") || "Passord matcher ikke",
    },
  };
  const isTabletOrMobile = useMediaQuery({ query: "(max-width: 1024px)" });

  return (
    <div className="Base">
      <div className="RegistrationAuthenticationContainer">
        <div className="RegistrationLoginContainer">
          <div className="RegistrationLogoContainer">
            <img className="LoginLogo" src={Logo} alt="GIDD logo" />
          </div>
          <h1>Velkommen tilbake!</h1>
          <h3>Har du allerede en bruker?</h3>
          <Link className="loginFormRegister" to="/login">
            <h3>Logg inn!</h3>
          </Link>
        </div>
        <div className="RegistrationRegistrationContainer">
          <h1>
            <b>Registrer deg på GIDD!</b>
          </h1>

          <div>
            <div>
              <form
                id="registerForm"
                className="Register-form-Container"
                onSubmit={handleSubmit(onSubmit, onErrors)}
              >
                {/* register your input into the hook by invoking the "register" function */}
                <input
                  className="Register-input loginFormInputNames "
                  placeholder="Fornavn"
                  type="text"
                  defaultValue=""
                  {...register("firstName", registerOptions.firstName)}
                />
                {errors.firstName && (
                  <span className="Register-input error">
                    {errors.firstName.message}
                  </span>
                )}
                <input
                  className="Register-input loginFormInputNames "
                  id="lastName"
                  type="text"
                  placeholder="Etternavn"
                  {...register("lastName", registerOptions.lastName)}
                />
                {errors.lastName && (
                  <span className="Register-input error">
                    {errors.lastName.message}
                  </span>
                )}
                <input
                  className="Register-input loginFormInputEmail"
                  id="email"
                  placeholder="eksempel@mail.com"
                  type="email"
                  {...register("email", registerOptions.email)}
                />
                {errors.email && (
                  <span className="Register-input error">
                    {errors.email.message}
                  </span>
                )}
                {/*TODO legg til norsk flagg i css for å tydeliggjøre at bare norske numre er støttet*/}
                <input
                  className="Register-input loginFormInputPhoneNumber"
                  id="phoneNumber"
                  placeholder="12345678"
                  type="tel"
                  {...register("phoneNumber", registerOptions.phoneNumber)}
                />
                {errors.phoneNumber && (
                  <span className="Register-input error">
                    {errors.phoneNumber.message}
                  </span>
                )}
                <input
                  className="Register-input loginFormInputPassword"
                  id="password"
                  placeholder="Passord"
                  type="password"
                  {...register("password", registerOptions.password)}
                />
                {errors.password && (
                  <span className="Register-input error">
                    {errors.password.message}
                  </span>
                )}
                <input
                  className="Register-input loginFormInputPassword"
                  id="repeat-password"
                  placeholder="Gjenta passord"
                  type="password"
                  {...register(
                    "repeatPassword",
                    registerOptions.repeatPassword
                  )}
                />
                {errors.repeatPassword && (
                  <span className="Register-input error">
                    {errors.repeatPassword.message}
                  </span>
                )}

                <div>
                  <h3>Aktivitetsnivå</h3>
                  <div className="activity-level-registration ">
                    <div>
                      <input
                        {...register("activityLevel", { required: "Påkrevd" })}
                        type="radio"
                        value="LOW"
                      />
                      Lav
                    </div>
                    <div>
                      <input
                        {...register("activityLevel", { required: "Påkrevd" })}
                        type="radio"
                        value="MEDIUM"
                      />
                      Middels
                    </div>
                    <div>
                      <input
                        {...register("activityLevel", { required: "Påkrevd" })}
                        type="radio"
                        value="HIGH"
                      />
                      Høy
                    </div>
                  </div>
                </div>
                {errors.activityLevel && (
                  <span className="Register-input error">
                    {errors.activityLevel.message}
                  </span>
                )}
                {props.regError && (
                  <div className="Register-error">
                    <p>Kunne ikke opprette konto:</p>
                    {props.regError}
                  </div>
                )}
                <button className="loginFormSubmit" type="submit">
                  {prepareSubmitBtnValue()}
                </button>
                {/*<input*/}
                {/*  className="loginFormSubmit"*/}
                {/*  type="submit"*/}
                {/*  value="Register"*/}
                {/*/>*/}
              </form>
              {isTabletOrMobile ? (
                <>
                  <div>
                    <hr></hr>
                    <h3>Allerede bruker?</h3>
                  </div>
                  <Link className="loginFormLogin-mobile" to="/login">
                    <h3>Logg inn</h3>
                  </Link>
                </>
              ) : (
                <></>
              )}
              {props.isLoggedIn ? <Redirect to="/" /> : ""}
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Register;
