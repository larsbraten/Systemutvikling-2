import { useForm } from "react-hook-form";
import React, { useState } from "react";
import LoaderWheel from "../loaderWheel/loaderWheel";

function EditAccountForm(props) {
  const {
    register,
    handleSubmit,
    watch,
    reset,
    formState: { errors, isValid, isSubmitting },
  } = useForm({
    mode: "onBlur",
    criteriaMode: "firstError",
    shouldFocusError: true,
    reValidateMode: "onSubmit",
    shouldUnregister: true,
  });

  const onSubmit = (data, event) => {
    event.preventDefault();
    console.log(data);
    props.onUpdateButtonClick(props.user.id, data);
  };

  const onErrors = (errors) => {
    console.log("ERRORS:", errors);
  };

  const onCancleClicked = () => {
    reset({});
  };

  const registerOptions = {
    phoneNumber: {
      // required: "Telefonnummer er påkrevd",
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
      // required: "Passord er påkrevd",
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

  return (
    <form onSubmit={handleSubmit(onSubmit, onErrors)}>
      <div className="edit-account-form">
        <div className="edit-account-top">
          <hr />
          <h2>Rediger info</h2>
        </div>

        <div className="edit-account-info">
          <div className="edit-phone-number">
            <h3>Telefonnummer: </h3>
            <input
              className="Register-input loginFormInputPhoneNumber"
              id="phoneNumber"
              placeholder="12345678"
              type="tel"
              defaultValue={props.user.contactInfo.phoneNumber}
              {...register("phoneNumber", registerOptions.phoneNumber)}
            />
            {errors.phoneNumber && (
              <span className="Register-input error">
                {errors.phoneNumber.message}
              </span>
            )}
          </div>
          <div className="edit-password">
            <h3>Passord: </h3>
            <input
              className="Register-input editFormInputPassword"
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
              className="Register-input editFormInputPassword"
              id="repeat-password"
              placeholder="Gjenta passord"
              type="password"
              {...register("repeatPassword", registerOptions.repeatPassword)}
            />
            {errors.repeatPassword && (
              <span className="">{errors.repeatPassword.message}</span>
            )}
          </div>
        </div>
        <div className="edit-account-level">
          <h3>Aktivitetsnivå:</h3>
          <input {...register("activityLevel")} type="radio" value="LOW" />
          Lav
          <input {...register("activityLevel")} type="radio" value="MEDIUM" />
          Middels
          <input {...register("activityLevel")} type="radio" value="HIGH" />
          Høy
          {errors.activityLevel && (
            <span className="Register-input error">
              {errors.activityLevel.message}
            </span>
          )}
        </div>
        <div className="edit-account-errors">{props.formError}</div>
        <button className="edit-account-submit" type="submit">
          {props.putUserIsLoading ? <LoaderWheel /> : "Oppdater"}
        </button>
        <button
          className="edit-account-cancel"
          type="button"
          onClick={onCancleClicked}
        >
          Avbryt
        </button>
      </div>
    </form>
  );
}

export default EditAccountForm;
