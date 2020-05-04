
import React, {useState, useEffect}  from 'react';
import ItemGridcomp from '../comps/itemGrid';

/**
 * Represents the home page of the website.
 */
const Itemspage = () => {
    const [message, setMessage] = useState([]);

    let sayItem = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        let itemId = parseInt(data.get('itemId'));

        fetch('api/items/' + itemId, {
            accept: "application/json"
        })
        .then(res => res.text())
        .then(setMessage)
        .catch((error) => {
            setMessage([<p>{error}</p>]);
        });
    };

    return (
        <div>
            <div className="island">
                <h1>testing stuff here</h1>
                <p>an assortment of buttons to test various endpoints</p>
                <form action="" onSubmit={sayItem}>
                    <label>type the item you want to get</label>
                    <input name="itemId"></input>
                    <button>console log item</button>
                </form>
                <p>{message}</p>
            </div>
            <div className="island">
                <h1>Available Items</h1>
                <p>Here's a list of all of the items currently available at our store!</p>
            </div>
            <ItemGridcomp/>
        </div>
    );
}

export default Itemspage;