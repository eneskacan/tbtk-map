import {useState} from "react";
import {Button, Container, Form} from "react-bootstrap";
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
        <Container>
            <Form className='form'>
                <Form>
                    <Form.Group controlId="formBasicEmail">
                        <Form.Label style={{marginWidth: '5px'}}>Username</Form.Label>
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
                    <Form.Row>
                        <Button variant="primary" onClick={login} type="submit">
                            Login
                        </Button>
                        &nbsp;&nbsp;
                        <Button variant="primary" onClick={signup} type="submit">
                            Signup
                        </Button>
                    </Form.Row>
                </Form>
            </Form>
        </Container>
    );
}

export default Login;