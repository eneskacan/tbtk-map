import {useState} from "react";
import {Button, Form} from "react-bootstrap";
import ApiHelper from "../helper/ApiHelper";

let apiHelper = ApiHelper.getInstance();

const Login = () => {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();

    const login = async event => {
        event.preventDefault(); // don't refresh on submit

        // login
        const response = await apiHelper.confirmUser(username, password);
        console.log(response.data);

        if (response.statusText === "OK") {
            // redirect to map page

        } else if (response.statusText === "UNAUTHORIZED") {
            // wrong password

        } else if (response.statusText === "NOT_FOUND") {
            // user not exist

        }
    }

    const signup = async event => {
        event.preventDefault(); // don't refresh on submit

        // login
        const response = await apiHelper.createUser(username, password);
        console.log(response.data);

        if (response.statusText === "CREATED") {
            // successful

        } else if (response.statusText === "NOT_ACCEPTABLE") {
            // user name already exist

        }
    }

    return (
        <div>
            <Form>
                <Form.Group controlId="formBasicEmail">
                    <Form.Label>Username</Form.Label>
                    <Form.Control
                        type="username"
                        placeholder="Enter username"
                        onChange={event => setUsername(event.target.value)}
                    />
                </Form.Group>

                <Form.Group controlId="formBasicPassword">
                    <Form.Label>Password</Form.Label>
                    <Form.Control
                        type="password"
                        placeholder="Password"
                        onChange={event => setPassword(event.target.value)}
                    />
                </Form.Group>

                <Button variant="primary" onClick={login} type="submit">
                    Login
                </Button>

                <Button variant="primary" onClick={signup} type="submit">
                    Signup
                </Button>
            </Form>
        </div>
    );
}

export default Login;