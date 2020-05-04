
import React, {useState, useEffect}  from 'react';

/**
 * Represents the home page of the website.
 */
const Testpage = () => {
    const [message, setMessage] = useState([]);

    // get a single item by id from a form submit event
    let getItemById = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        let itemId = parseInt(data.get('itemId'));
        if(isNaN(itemId)) itemId = "";

        fetch('api/items/' + itemId, {
            accept: "application/json"
        })
        .then(res => res.text())
        .then(setMessage)
        .catch((error) => {
            setMessage([<p>{error}</p>]);
        });
    };

    // delete a single item from the database by id
    // from a form submit event
    let deleteItemByID = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        let itemId = parseInt(data.get('itemId'));

        fetch('api/items/' + itemId, {
            method: "DELETE"
        })
        .then(res => res.text())
        .then(setMessage)
        .catch((error) => {
            setMessage([<p>{error}</p>]);
        });
    };

    // create a single item from the database
    // from a form submit event
    let createItem = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
    };

    return (
        <div>
            <div className="island">
                <h1>testing stuff here</h1>
                <p>an assortment of buttons to test various endpoints</p>
                <p>{message}</p>

                <h5>display item data by item ID</h5>
                <form action="" onSubmit={getItemById}>
                    <label>itemId</label><input name="itemId"></input><br></br>
                    <button>display item</button>
                </form>

                <h5>delete an item by item ID</h5>
                <form action="" onSubmit={deleteItemByID}>
                    <label>itemId</label><input name="itemId"></input><br></br>
                    <button>delete item</button>
                </form>

                <h5>create a new item</h5>
                <form action="" onSubmit={createItem}>
                    <label>name</label><input name="name"></input><br></br>
                    <label>description</label><textarea name="description"></textarea><br></br>
                    <label>price</label><input name="price"></input><br></br>
                    <label>quantity</label><input name="quantity"></input><br></br>
                    <button>create item</button>
                </form>
            </div>
        </div>
    );
}

export default Testpage;