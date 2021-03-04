import React, { Dispatch, FormEvent, useState } from "react";
import { connect, shallowEqual, useDispatch, useSelector } from "react-redux";
import { Link } from "react-router-dom";
import { AuthenticationUserState } from "../../interfaces/User/authenticationUserState";
import { loginRequest } from "../../redux/actions/actionLogin";
import { ApplicationState } from "../../redux/reducers";

const LoginPage: React.FC<AuthenticationUserState> = ({ loggingIn }) => {
  const [email, setEmail] = useState<string>();
  const [password, setPassword] = useState<string>();
  const [submitted, setSubmitted] = useState(false);

  
  const dispatch: Dispatch<any> = useDispatch();

  const handleSubmit = async (e: FormEvent) => {
    e.preventDefault();

    console.log(email, password, e);

    if (email && password) {
      dispatch(loginRequest(email, password));
    }
  };

  return (
    <div className="col-md-6 col-md-offset-3">
      <h2>Login</h2>
      <form name="form" onSubmit={handleSubmit}>
        <div className={"form-group" + (submitted && !email ? " has-error" : "")}>
          <label htmlFor="username">Username</label>
          <input
            type="text"
            className="form-control"
            name="username"
            value={email || ""}
            onChange={(event) => setEmail(event.currentTarget.value)}
          />
          {submitted && !email && <div className="help-block">Username is required</div>}
        </div>
        <div className={"form-group" + (submitted && !password ? " has-error" : "")}>
          <label htmlFor="password">Password</label>
          <input
            type="password"
            className="form-control"
            name="password"
            value={password || ""}
            onChange={(event) => setPassword(event.currentTarget.value)}
          />
          {submitted && !password && <div className="help-block">Password is required</div>}
        </div>
        <div className="form-group">
          <button className="btn btn-primary">Login</button>
          {loggingIn && (
            <img src="data:image/gif;base64,R0lGODlhEAAQAPIAAP///wAAAMLCwkJCQgAAAGJiYoKCgpKSkiH/C05FVFNDQVBFMi4wAwEAAAAh/hpDcmVhdGVkIHdpdGggYWpheGxvYWQuaW5mbwAh+QQJCgAAACwAAAAAEAAQAAADMwi63P4wyklrE2MIOggZnAdOmGYJRbExwroUmcG2LmDEwnHQLVsYOd2mBzkYDAdKa+dIAAAh+QQJCgAAACwAAAAAEAAQAAADNAi63P5OjCEgG4QMu7DmikRxQlFUYDEZIGBMRVsaqHwctXXf7WEYB4Ag1xjihkMZsiUkKhIAIfkECQoAAAAsAAAAABAAEAAAAzYIujIjK8pByJDMlFYvBoVjHA70GU7xSUJhmKtwHPAKzLO9HMaoKwJZ7Rf8AYPDDzKpZBqfvwQAIfkECQoAAAAsAAAAABAAEAAAAzMIumIlK8oyhpHsnFZfhYumCYUhDAQxRIdhHBGqRoKw0R8DYlJd8z0fMDgsGo/IpHI5TAAAIfkECQoAAAAsAAAAABAAEAAAAzIIunInK0rnZBTwGPNMgQwmdsNgXGJUlIWEuR5oWUIpz8pAEAMe6TwfwyYsGo/IpFKSAAAh+QQJCgAAACwAAAAAEAAQAAADMwi6IMKQORfjdOe82p4wGccc4CEuQradylesojEMBgsUc2G7sDX3lQGBMLAJibufbSlKAAAh+QQJCgAAACwAAAAAEAAQAAADMgi63P7wCRHZnFVdmgHu2nFwlWCI3WGc3TSWhUFGxTAUkGCbtgENBMJAEJsxgMLWzpEAACH5BAkKAAAALAAAAAAQABAAAAMyCLrc/jDKSatlQtScKdceCAjDII7HcQ4EMTCpyrCuUBjCYRgHVtqlAiB1YhiCnlsRkAAAOwAAAAAAAAAAAA==" />
          )}
          <Link to="/register" className="btn btn-link">
            Register
          </Link>
        </div>
      </form>
    </div>
  );
};

const mapStateToProps = ({ authentication }: ApplicationState): AuthenticationUserState => ({
  ...authentication,
});

export default connect(mapStateToProps)(LoginPage);
