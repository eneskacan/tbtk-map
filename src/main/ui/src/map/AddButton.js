import React from "react";
import { Button } from 'react-floating-action-button'

const AddButton = ({cursor, setCursor}) => {

    const onClick = () => {
        if(cursor === 'crosshair'){
            setCursor('cell');
        } else {
            setCursor('crosshair');
        }
    }

    return (
        <div className="add-button">
            <Button
                tooltip="Save a new location"
                icon="fas fa-plus"
                rotate={false}
                onClick={onClick} />
        </div>
    );
}

export default AddButton;