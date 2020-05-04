import React, {useState, useEffect} from 'react';

/**
 * Represents a single item in the grid.
 * Be careful: the names of each parameter must match the name of each property
 * in the schema belonging to the item. Double check the JSON response to see
 * what the proper names of each property should be.
 */
const ItemGridElem = ({id, name, description, price, quantity, imageURL}) => {
    return (
        <div className="item" key={"item" + id}>
            <img src={imageURL} alt={"image of " + name}></img>
            <h3>{name}</h3>
            <p>{description}</p>
            <p>${price}</p>
            <p>Quantity: {quantity}</p>
        </div>
    );
}

/**
 * Represents an entire island that contains all of the items in the database.
 */
const ItemGridcomp = () => {
    const [allItemsHtml, setAllItemsHtml] = useState([<h3>Loading items...</h3>]);

    useEffect(() => {
        fetch('/api/items', {
            accept: "application/json"
        })
        .then(res => {
            if(!res.ok) throw new Error(res);
            return res.json();
        })
        .then(res => {
            res = res.map(ItemGridElem);
            setAllItemsHtml(res);
        })
        .catch((error) => {
        setAllItemsHtml([<p>{error}</p>]);
        });
    },[]);

    return (
        <div id="itemContainer" className="island">
            {allItemsHtml}
        </div>
    );
}

export default ItemGridcomp;