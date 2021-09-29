import "./account.css";
import Avatar from "@material-ui/core/Avatar";

function Account(props) {
  const { user } = props;
  return (
    <div className="account-info">
      <div className="account-display">
        <Avatar className="account-display-avatar">
          {user.persona.firstName[0]}
        </Avatar>
      </div>

      <div className="user-info">
        <h2>Hei {user.persona.firstName}!</h2>
        <p>
          Navn: {user.persona.firstName} {user.persona.surName}
        </p>
        <p>E-post: {user.contactInfo.email}</p>
        <p>Telefonnummer: {user.contactInfo.phoneNumber}</p>
        <p>Treningsnivå: {user.activityLevel}</p>
      </div>
    </div>
  );
}

export default Account;
