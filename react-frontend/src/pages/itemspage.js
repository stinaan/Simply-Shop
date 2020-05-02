
import React from 'react';
import ItemGridcomp from '../comps/itemGrid';

/**
 * Represents the home page of the website.
 */
const Itemspage = () => {
    return (
        <div>
            <island>
                <h1>Available Items</h1>
                <p>Here's a list of all of the items currently available at our store!</p>
            </island>
            <ItemGridcomp/>
        </div>
    );
}

export default Itemspage;