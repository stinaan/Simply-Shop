import React from 'react';
const Stylepage = () => {
    return (
        <div className="island">
            <h1>Simply Shop Stylepage</h1>
            <p>
                This stylepage is intended for designing
                common HTML tags and  significant CSS
                classes used throughout the website.
            </p>

            <h1>header 1</h1>
            <h2>header 2</h2>
            <h3>header 3</h3>
            <h4>header 4</h4>
            <h5>header 5</h5>
            <h6>header 6</h6>

            <p>this is a test paragraph.</p>

            <blockquote>this is a block quote.</blockquote>
            <p>
                and <code>here is some inline code</code>. in fact here is <code>an even larger amount of inline code to test how the page decides to break it once it's too long.</code> did it work?
            </p>

            <pre>    but here
is just some preformatted code
with some new lines.</pre>

            <pre><code>this is a preformatted code block!
awesome.
here's another line.
            </code></pre>
        </div>
    );
}

export default Stylepage;