import React, { useEffect, useState } from 'react';
import Alert from '@material-ui/lab/Alert';
import "./feedbackMessage.css"
import { render } from '@testing-library/react';

const Message = (props) => {
  // the alert is displayed by default
  const [alert, setAlert] = useState(true);
      
  useEffect(() => {
    // when the component is mounted, the alert is displayed for 3 seconds
    console.log("messagen blir kjørt")
    const timeout = setTimeout(() => {
      setAlert(false);
    }, props.time);
    return () => {        
        clearTimeout(timeout); // Clears timer in case you close your alert somewhere else.      
    }
  }, []);    
  
  const renderMessage = () => {
    if(props.message.includes("venteliste")) return <Alert severity="warning" className="alertMessage">{props.message}</Alert>
    else return <Alert severity="success" className="alertMessage">{props.message}</Alert>
  }
    
  return (
    alert?renderMessage():""
  );
}

export default Message;