import {useState} from "react";
import {Button, Col, Form, Modal} from "react-bootstrap";
import ApiHelper from "../helper/ApiHelper";

let apiHelper = ApiHelper.getInstance();

const UpdateLocationDialog = ({show, setShow, marker, map}) => {
    const [name, setName] = useState();

    const handleDelete = async () => {
        console.log(marker);
        await apiHelper.deleteLocation(marker.id);
        setShow(false); // close dialog
        window.location.reload();
    }

    const handleSave = async () => {
        await apiHelper.updateLocation(marker.id, name);
        setShow(false); // close dialog
        window.location.reload();
    }

    return (
        <div>
            <Modal show={show} onHide={handleDelete}>
                <Modal.Body>
                    <Form>
                        <Form.Row className="align-items-center">
                            <Col xs={8}>
                                <Form.Control
                                    type="text"
                                    onChange={event => setName(event.target.value)}
                                />
                            </Col>
                            <Col>
                                <Button variant="secondary" onClick={handleDelete}>
                                    Delete
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

export default UpdateLocationDialog;