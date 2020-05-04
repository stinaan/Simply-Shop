import React, {useState, useEffect} from 'react';

const ItemGridElem = ({id, name, description, price, quantity}) => {
    return (
        <div className="item" key={"item" + id}>
            <img src="test_square.png"></img>
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
    const [allItemsHtml, setAllItemsHtml] = useState([]);

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
            setAllItemsHtml(<p>{error}</p>);
        });
    },[]);

    return (
        <div className="island">
            {allItemsHtml}
        </div>
    );
}

export default ItemGridcomp;