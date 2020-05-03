import React, {useState, useEffect} from 'react';

/**
 * Represents an entire island that contains all of the items in the database.
 */
const ItemGridcomp = () => {
    const [message, setMessage] = useState("");

    useEffect(() => {
        fetch('/api/items', {
            accept: "application/json"
        })
        .then(response => response.text())
        .then(message => { setMessage(message); });
    },[]);

    return (
        <div className="island">
            {message}
        </div>
    );
}

const ItemGridElem = () => {
    return (
        <div className="item">
            <p>awesome</p>
        </div>
    );
}

export default ItemGridcomp;