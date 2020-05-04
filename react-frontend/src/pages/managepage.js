
import React, {useState}  from 'react';

/**
 * Represents the home page of the website.
 */
const Adminpage = () => {
    const [message, setMessage] = useState(["Submission result appears here!"]);

    // get a single item by item id from a form submit event
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

    // delete a single item from the database by item id
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

        fetch('api/items/', {
            method: "POST",
            body: data
        })
        .then(res => res.text())
        .then(setMessage)
        .catch((error) => {
            setMessage([<p>{error.toString()}</p>]);
        });
    };

    // edit a single item from the database by item id
    let editItemById = (event) => {
        event.preventDefault();
        const data = new FormData(event.target);
        const itemId = parseInt(data.get('itemId'));

        fetch('api/edit/' + itemId, {
            method: "POST",
            body: data
        })
        .then(res => res.text())
        .then(setMessage)
        .catch((error) => {
            setMessage([<p>{error.toString()}</p>]);
        });
    };

    return (
        <div>
            <div className="island">
                <h1>Manage</h1>
                <p>Use this page to manage Simply Shop's data!</p>
                <pre><code>{message}</code></pre><br></br><br></br>

                <h5>display item data by item ID</h5>
                <form onSubmit={getItemById}>
                    <label>itemId</label><input name="itemId"></input><br></br>
                    <button>display item</button>
                </form>

                <h5>delete an item by item ID</h5>
                <form action="" onSubmit={deleteItemByID}>
                    <label>itemId</label><input name="itemId"></input><br></br>
                    <button>delete item</button>
                </form>

                <h5>create a new item</h5>
                <form onSubmit={createItem} encType="multipart/form-data">
                    <label>category</label><input name="category"></input><br></br>
                    <label>name</label><input name="name"></input><br></br>
                    <label>description</label><textarea name="description"></textarea><br></br>
                    <label>price</label><input name="price"></input><br></br>
                    <label>quantity</label><input name="quantity"></input><br></br>
                    <label>image</label><input name="image" type="file" accept="image/*"></input><br></br>
                    <button>create item</button>
                </form>

                <h5>edit an item by item ID</h5>
                <form onSubmit={editItemById} encType="multipart/form-data">
                    <label>itemID</label><input name="itemId"></input><br></br>
                    <label>category</label><input name="category"></input><br></br>
                    <label>name</label><input name="name"></input><br></br>
                    <label>description</label><textarea name="description"></textarea><br></br>
                    <label>price</label><input name="price"></input><br></br>
                    <label>quantity</label><input name="quantity"></input><br></br>
                    <label>image</label><input name="image" type="file" accept="image/*"></input><br></br>
                    <button>edit item</button>
                </form>
            </div>
        </div>
    );
}

export default Adminpage;