import { useState, useEffect } from "react";
import logo from "./avail.png";
import "./App.css";
import reactDom from "react-dom";

function App() {
  const initVals = {
    fName: "",
    lName: "",
    npiNum: "",
    busAddr: "",
    telNum: "",
    email: "",
  };

  const [formVals, setFormVals] = useState(initVals);
  const [formErrs, setFormErrs] = useState({});
  const [isSubmit, setIsSubmit] = useState(false);

  const iimmg = document.getElementById("Applogo");

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormVals({ ...formVals, [name]: value });
  };

  const handleSubmit = (e) => {
    e.preventDefault(); // Suppressing default response
    // of page reloading
    setFormErrs(validate(formVals));

    setIsSubmit(true);
  }; //console.log("Acting...");

  useEffect(() => {
    document.title = "Availity";
    //console.log("Starting up...");
    console.log(formErrs);
    if (Object.keys(formErrs).length === 0 && isSubmit) {
      console.log(formVals);
    }
  }, [formErrs]);

  const validate = (vals) => {
    const errs = {};
    //email regex:
    const regex = /^[^\s@]+@[^\s@]+\.[^\s@]{2,}$/i;
    if (!vals.fName) {
      errs.fName = "Full name required.";
    }
    if (!vals.lName) {
      errs.lName = "Last name required.";
    }
    if (!vals.npiNum) {
      errs.npiNum = "NPI number required.";
    } else if (isNaN(vals.npiNum)) {
      errs.npiNum = "Value input for NPI code is not a number.";
    } else if (vals.npiNum.toString().length !== 10) {
      errs.npiNum = "NPI num must be exactly ten digits.";
    }
    if (!vals.busAddr) {
      errs.busAddr = "Business address required.";
    }
    if (!vals.telNum) {
      errs.telNum = "Phone number required.";
    } else if (isNaN(vals.telNum)) {
      errs.telNum = "Does not fit format for a phone number.";
    } else if (
      vals.telNum.toString().length < 10 ||
      vals.telNum.toString().length > 11
    ) {
      errs.telNum = "Phone num must be 10-11 digits.";
    }

    if (!vals.email) {
      errs.email = "Email address required.";
    } else if (!regex.test(vals.email)) {
      errs.email = "Invalid email format.";
    }

    return errs;
  };

  return (
    <div className="App">
      <img id="Applogo" src={logo} className="App-logo" alt="logo" />
      {Object.keys(formErrs).length === 0 && isSubmit ? (
        <div className="ui message success" style={{ color: "blue" }}>
          Registration successful.
        </div>
      ) : (
        <pre></pre>
      )}

      <form onSubmit={handleSubmit}>
        <h1>New user registration form</h1>
        <div className="ui divider"></div>
        <div className="ui form">
          <div className="field">
            <label>First name</label>
            <br />
            <input
              type="text"
              name="fName"
              placeholder="First name..."
              value={formVals.fName}
              onChange={handleChange}
            />
          </div>
          <p style={{ color: "red" }}>{formErrs.fName}</p>

          <div className="field">
            <label>Last name</label>
            <br />
            <input
              type="text"
              name="lName"
              value={formVals.lName}
              onChange={handleChange}
              placeholder="Last name..."
            />
          </div>
          <p style={{ color: "red" }}>{formErrs.lName}</p>

          <div className="field">
            <label>NPI number</label>
            <br />
            <input
              type="number"
              name="npiNum"
              value={formVals.npiNum}
              onChange={handleChange}
              placeholder="NPI number"
            />
          </div>
          <p style={{ color: "red" }}>{formErrs.npiNum}</p>

          <div className="field">
            <label>Business address</label>
            <br />
            <input
              type="text"
              name="busAddr"
              value={formVals.busAddr}
              onChange={handleChange}
              placeholder="Business address"
            />
          </div>
          <p style={{ color: "red" }}>{formErrs.busAddr}</p>

          <div className="field">
            <label>Phone number</label>
            <br />
            <input
              type="number"
              name="telNum"
              onChange={handleChange}
              placeholder="Phone number"
            />
          </div>
          <p style={{ color: "red" }}>{formErrs.telNum}</p>

          <div className="field">
            <label>Email address</label>
            <br />
            <input
              type="email"
              name="email"
              onChange={handleChange}
              placeholder="Email address"
            />
          </div>
          <p style={{ color: "red" }}>{formErrs.email}</p>

          <button>Submit...</button>
        </div>
      </form>
    </div>
  );
}

export default App;
