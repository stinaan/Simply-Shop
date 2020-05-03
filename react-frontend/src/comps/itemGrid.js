import React from 'react';

/**
 * Represents an entire island that contains all of the items in the database.
 */
const ItemGridcomp = () => {
    let items = [];
    for(var i = 0; i < 5; i++) {
        items.push(<ItemGridElem/>);
    }

    return (
        <div className="island">
            {items}
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