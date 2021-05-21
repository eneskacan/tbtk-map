import React, {useState} from "react";
import {ButtonGroup, ToggleButton} from "react-bootstrap";
import 'bootstrap/dist/css/bootstrap.min.css';

// Map style selection menu
const Styles = (props) => {
    const [radioValue, setRadioValue] = useState('1');

    const radios = [
        { name: 'Streets', value: '1', style: 'streets-v11' },
        { name: 'Light', value: '2', style: 'light-v10' },
        { name: 'Satellite', value: '3', style: 'satellite-v9' },
    ];

    function updateMapStyle(styleId) {
        props.updateMapStyle("mapbox://styles/mapbox/" + styleId);
    }

    return (
        <div className={"styles-toolbar"}>
            <ButtonGroup toggle>
                {radios.map((radio, idx) => (
                    <ToggleButton
                        key={idx}
                        type="radio"
                        variant="secondary"
                        name="map-styles"
                        bsClass="custom-btn"
                        value={radio.value}
                        checked={radioValue === radio.value}
                        onChange={(e) => {
                            setRadioValue(e.currentTarget.value);
                            updateMapStyle(radio.style);
                        }}
                    >
                        {radio.name}
                    </ToggleButton>
                ))}
            </ButtonGroup>
        </div>
    );
}

export default Styles;