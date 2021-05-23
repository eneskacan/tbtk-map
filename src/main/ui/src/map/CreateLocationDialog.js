import {useState} from "react";
import {Button, Col, Form, Modal} from "react-bootstrap";
import ApiHelper from "../helper/ApiHelper";
import MapMarker from "./MapMarker";

let apiHelper = ApiHelper.getInstance();

const CreateLocationDialog = ({show, setShow, lat, lng, map}) => {
    const [name, setName] = useState();

    const handleClose = () => {
        setShow(false); // close dialog
    }

    const handleSave = async (event) => {
        event.preventDefault(); // don't refresh on submit

        // save location to database
        const response = await apiHelper.createLocation(name, lng, lat);
        console.log(response.data);

        if(response.statusText === "Created") {
            new MapMarker(map).addMarkers().then();
        } else {
            console.log("failed to save new location");
        }

        setShow(false); // close dialog
    }

    return (
        <div>
            <Modal show={show} onHide={handleClose}>
                <Modal.Body>
                    <Form>
                        <Form.Row className="align-items-center">
                            <Col xs={8}>
                                <Form.Control
                                    type="text"
                                    placeholder="Location name"
                                    onChange={event => setName(event.target.value)}
                                />
                            </Col>
                            <Col xs={0.9}></Col>
                            <Col>
                                <Button variant="secondary" onClick={handleClose}>
                                    Close
                                </Button>
                            </Col>
                            <Col>
                                <Button variant="primary" onClick={handleSave} type={"submit"}>
                                    Save
                                </Button>
                            </Col>
                        </Form.Row>
                    </Form>
                </Modal.Body>
            </Modal>
        </div>
    );
}

export default CreateLocationDialog;