import {useState} from "react";
import {Button, Form} from "react-bootstrap";
import ApiHelper from "../helper/ApiHelper";
import {useHistory} from "react-router-dom";

let apiHelper = ApiHelper.getInstance();

const Login = () => {
    const [username, setUsername] = useState();
    const [password, setPassword] = useState();
    const history = useHistory();

    const login = async event => {
        event.preventDefault(); // don't refresh on submit

        // login
        const response = await apiHelper.confirmUser(username, password);

        if (response.statusText === "OK") {
            history.push("/map");

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

        if (response.statusText === "CREATED") {
            // successful

        } else if (response.statusText === "NOT_ACCEPTABLE") {
            // user name already exist

        }
    }

    return (
        <Form className='form'>
            <div>
                <div style={{marginTop: 10}}></div>
                <div style={{marginRight: 1500}}>
                    <div style={{marginLeft: 10}}>
                        <Form>
                            <Form.Group controlId="formBasicEmail">
                                <Form.Label style={{marginWidth: '5px'}}>Username</Form.Label>
                                <Form.Control
                                    type="username"
                                    placeholder="Enter username"
                                    onChange={event => setUsername(event.target.value)}
                                />
                            </Form.Group>
                            <div style={{marginTop: 10}}></div>

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
                            <div style={{marginTop: 5}}>
                                <Button variant="primary" onClick={signup} type="submit">
                                    Signup
                                </Button>
                            </div>
                        </Form>
                    </div>
                </div>
            </div>
        </Form>

    );
}

export default Login;