import React from 'react';

/**
 * Represents an entire island that contains all of the items in the database.
 */
const ItemGridcomp = () => {
    let itemsJson;
    fetch('/api/items/').then(res => {
        itemsJson = res.text();
    })

    return (
        <div className="island">
            {itemsJson}
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